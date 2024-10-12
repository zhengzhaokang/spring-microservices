package com.microservices.otmp.masterdata.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseSalesOfficeMapper;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOffice;
import com.microservices.otmp.masterdata.service.IBizBaseSalesOfficeService;

/**
 * BizBaseSalesOfficeService业务层处理
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
@Service
public class BizBaseSalesOfficeServiceImpl implements IBizBaseSalesOfficeService
{
    @Autowired
    private BizBaseSalesOfficeMapper bizBaseSalesOfficeMapper;
    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";

    public void setSalesOfficeToRedis() {
        List<BizBaseSalesOffice> bizBaseSalesOffices = this.selectBizBaseSalesOfficeList(new BizBaseSalesOffice());
        redisUtils.set(REDIS_NAME_PREFIX+"sales_office_code",bizBaseSalesOffices, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    /**
     * 查询BizBaseSalesOffice
     * 
     * @param id BizBaseSalesOffice主键
     * @return BizBaseSalesOffice
     */
    @Override
    public BizBaseSalesOffice selectBizBaseSalesOfficeById(Long id)
    {
        return bizBaseSalesOfficeMapper.selectBizBaseSalesOfficeById(id);
    }

    /**
     * 查询BizBaseSalesOffice列表
     * 
     * @param bizBaseSalesOffice BizBaseSalesOffice
     * @return BizBaseSalesOffice
     */
    @Override
    public List<BizBaseSalesOffice> selectBizBaseSalesOfficeList(BizBaseSalesOffice bizBaseSalesOffice)
    {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseSalesOffice.getIds());
        bizBaseSalesOffice.setIdsList(longIdsList);
        return bizBaseSalesOfficeMapper.selectBizBaseSalesOfficeList(bizBaseSalesOffice);
    }

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseSalesOffice集合
     */
    @Override
    public List<BizBaseSalesOffice> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        return bizBaseSalesOfficeMapper.getDropDownList(bizBaseDropDownCondition);
    }

    /**
     * 新增BizBaseSalesOffice
     * 
     * @param bizBaseSalesOffice BizBaseSalesOffice
     * @return 结果
     */
    @Override
    public int insertBizBaseSalesOffice(BizBaseSalesOffice bizBaseSalesOffice)
    {
        bizBaseSalesOffice.setCreateTime(DateUtils.getNowDate());
        int i= bizBaseSalesOfficeMapper.insertBizBaseSalesOffice(bizBaseSalesOffice);
        setSalesOfficeToRedis();
        return i;
    }

    /**
     * 修改BizBaseSalesOffice
     * 
     * @param bizBaseSalesOffice BizBaseSalesOffice
     * @return 结果
     */
    @Override
    public int updateBizBaseSalesOffice(BizBaseSalesOffice bizBaseSalesOffice)
    {
        bizBaseSalesOffice.setUpdateTime(DateUtils.getNowDate());
        int i= bizBaseSalesOfficeMapper.updateBizBaseSalesOffice(bizBaseSalesOffice);
        setSalesOfficeToRedis();
        return i;
    }

    /**
     * 批量删除BizBaseSalesOffice
     * 
     * @param ids 需要删除的BizBaseSalesOffice主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseSalesOfficeByIds(Long[] ids)
    {
        int i= bizBaseSalesOfficeMapper.updateBizBaseSalesOfficeByIds(ids);
        setSalesOfficeToRedis();
        return i;
    }

    /**
     * 删除BizBaseSalesOffice信息
     * 
     * @param id BizBaseSalesOffice主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseSalesOfficeById(Long id)
    {
        int i= bizBaseSalesOfficeMapper.deleteBizBaseSalesOfficeById(id);
        setSalesOfficeToRedis();
        return i;
    }
    @Override
    public String importExcel(List<BizBaseSalesOffice> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBaseSalesOffice bizBaseSalesOffice : bizs) {
            try {

                List<BizBaseSalesOffice> buList = bizBaseSalesOfficeMapper.selectBizBaseSalesOfficeListCheck(bizBaseSalesOffice);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseSalesOffice.setId(buList.get(0).getId());
                    bizBaseSalesOffice.setUpdateTime(DateUtils.getNowDate());
                    bizBaseSalesOfficeMapper.updateBizBaseSalesOffice(bizBaseSalesOffice);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseSalesOffice.setCreateTime(DateUtils.getNowDate());
                bizBaseSalesOffice.setUpdateTime(DateUtils.getNowDate());
                bizBaseSalesOffice.setCreateBy(loginName);
                bizBaseSalesOffice.setStatus("Y");
                bizBaseSalesOfficeMapper.insertBizBaseSalesOffice(bizBaseSalesOffice);

                successNum.getAndIncrement();
            } catch (Exception e) {
                failureNum.getAndIncrement();
                String msg = "<br/>" + failureNum + "MasterData导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum.intValue() > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
