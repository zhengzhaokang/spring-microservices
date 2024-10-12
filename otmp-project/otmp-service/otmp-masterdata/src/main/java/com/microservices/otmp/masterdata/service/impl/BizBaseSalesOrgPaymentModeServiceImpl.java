package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOrgPaymentMode;
import com.microservices.otmp.masterdata.mapper.BizBaseSalesOrgPaymentModeMapper;
import com.microservices.otmp.masterdata.service.IBizBaseSalesOrgPaymentModeService;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BaseSalesOrgPaymentModeService业务层处理
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
@Service
public class BizBaseSalesOrgPaymentModeServiceImpl implements IBizBaseSalesOrgPaymentModeService
{
    @Autowired
    private BizBaseSalesOrgPaymentModeMapper bizBaseSalesOrgPaymentModeMapper;

    /**
     * 查询BaseSalesOrgPaymentMode
     * 
     * @param id BaseSalesOrgPaymentMode主键
     * @return BaseSalesOrgPaymentMode
     */
    @Override
    public BizBaseSalesOrgPaymentMode selectBizBaseSalesOrgPaymentModeById(Long id)
    {
        return bizBaseSalesOrgPaymentModeMapper.selectBizBaseSalesOrgPaymentModeById(id);
    }

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseGtnType集合
     */
    @Override
    public List<BizBaseSalesOrgPaymentMode> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        return bizBaseSalesOrgPaymentModeMapper.getDropDownList(bizBaseDropDownCondition);
    }

    /**
     * 查询BaseSalesOrgPaymentMode列表
     * 
     * @param bizBaseSalesOrgPaymentMode BaseSalesOrgPaymentMode
     * @return BaseSalesOrgPaymentMode
     */
    @Override
    public List<BizBaseSalesOrgPaymentMode> selectBizBaseSalesOrgPaymentModeList(BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode)
    {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseSalesOrgPaymentMode.getIds());
        bizBaseSalesOrgPaymentMode.setIdsList(longIdsList);
        return bizBaseSalesOrgPaymentModeMapper.selectBizBaseSalesOrgPaymentModeList(bizBaseSalesOrgPaymentMode);
    }

    /**
     * 新增BaseSalesOrgPaymentMode
     * 
     * @param bizBaseSalesOrgPaymentMode BaseSalesOrgPaymentMode
     * @return 结果
     */
    @Override
    public int insertBizBaseSalesOrgPaymentMode(BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode)
    {
        bizBaseSalesOrgPaymentMode.setCreateTime(DateUtils.getNowDate());
        return bizBaseSalesOrgPaymentModeMapper.insertBizBaseSalesOrgPaymentMode(bizBaseSalesOrgPaymentMode);
    }

    /**
     * 修改BaseSalesOrgPaymentMode
     * 
     * @param bizBaseSalesOrgPaymentMode BaseSalesOrgPaymentMode
     * @return 结果
     */
    @Override
    public int updateBizBaseSalesOrgPaymentMode(BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode)
    {
        bizBaseSalesOrgPaymentMode.setUpdateTime(DateUtils.getNowDate());
        return bizBaseSalesOrgPaymentModeMapper.updateBizBaseSalesOrgPaymentMode(bizBaseSalesOrgPaymentMode);
    }

    /**
     * 批量删除BaseSalesOrgPaymentMode
     * 
     * @param ids 需要删除的BaseSalesOrgPaymentMode主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseSalesOrgPaymentModeByIds(Long[] ids)
    {
        return bizBaseSalesOrgPaymentModeMapper.updateBizBaseSalesOrgPaymentModeByIds(ids);
    }

    /**
     * 删除BaseSalesOrgPaymentMode信息
     * 
     * @param id BaseSalesOrgPaymentMode主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseSalesOrgPaymentModeById(Long id)
    {
        return bizBaseSalesOrgPaymentModeMapper.deleteBizBaseSalesOrgPaymentModeById(id);
    }

    @Override
    public String importExcel(List<BizBaseSalesOrgPaymentMode> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        BizBaseSalesOrgPaymentMode mode = new BizBaseSalesOrgPaymentMode();
        bizs.forEach(bizBaseSalesOrgPaymentMode -> {
            try {
                mode.setGeoCode(bizBaseSalesOrgPaymentMode.getGeoCode());
                mode.setRegionMarketCode(bizBaseSalesOrgPaymentMode.getRegionMarketCode());
                mode.setSalesOrgCode(bizBaseSalesOrgPaymentMode.getSalesOrgCode());
                mode.setPaymentModeCode(bizBaseSalesOrgPaymentMode.getPaymentModeCode());
                mode.setIntegrateMode(bizBaseSalesOrgPaymentMode.getIntegrateMode());
                mode.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
                List<BizBaseSalesOrgPaymentMode> buList = bizBaseSalesOrgPaymentModeMapper.selectBizBaseSalesOrgPaymentModeListCheck(mode);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseSalesOrgPaymentMode.setId(buList.get(0).getId());
                    bizBaseSalesOrgPaymentMode.setUpdateTime(DateUtils.getNowDate());
                    bizBaseSalesOrgPaymentModeMapper.updateBizBaseSalesOrgPaymentMode(bizBaseSalesOrgPaymentMode);
                }
                bizBaseSalesOrgPaymentMode.setCreateTime(DateUtils.getNowDate());
                bizBaseSalesOrgPaymentMode.setUpdateTime(DateUtils.getNowDate());
                bizBaseSalesOrgPaymentMode.setCreateBy(loginName);
                bizBaseSalesOrgPaymentMode.setStatus(BooleanStatusEnum.TRUE_Y.getCode());

                bizBaseSalesOrgPaymentModeMapper.insertBizBaseSalesOrgPaymentMode(bizBaseSalesOrgPaymentMode);

                successNum.getAndIncrement();
            } catch (Exception e) {
                failureNum.getAndIncrement();
                String msg = "<br/>" + failureNum + "MasterData导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        });
        if (failureNum.intValue() > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();

    }
}
