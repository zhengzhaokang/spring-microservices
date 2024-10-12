package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.masterdata.domain.BizBaseProfitCenter;
import com.microservices.otmp.masterdata.mapper.BizBaseProfitCenterMapper;
import com.microservices.otmp.masterdata.service.IBizBaseProfitCenterService;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * BaseProfitCenterService业务层处理
 *
 * @author lovefamily
 * @date 2022-04-22
 */
@Service
public class BizBaseProfitCenterServiceImpl implements IBizBaseProfitCenterService {
    @Autowired
    private BizBaseProfitCenterMapper bizBaseProfitCenterMapper;

    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";

    public void setProfitCenterToRedis() {
        List<BizBaseProfitCenter> bizBaseProfitCenters = this.selectBizBaseProfitCenterList(new BizBaseProfitCenter());
        List<String> centerList = bizBaseProfitCenters.stream().map(BizBaseProfitCenter::getProfitCenterCode).collect(Collectors.toList());
        redisUtils.set(REDIS_NAME_PREFIX + "profit_center_code", centerList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    /**
     * 查询BaseProfitCenter
     *
     * @param id BaseProfitCenter主键
     * @return BaseProfitCenter
     */
    @Override
    public BizBaseProfitCenter selectBizBaseProfitCenterById(Long id) {
        return bizBaseProfitCenterMapper.selectBizBaseProfitCenterById(id);
    }

    /**
     * 查询BaseProfitCenter列表
     *
     * @param bizBaseProfitCenter BaseProfitCenter
     * @return BaseProfitCenter
     */
    @Override
    public List<BizBaseProfitCenter> selectBizBaseProfitCenterList(BizBaseProfitCenter bizBaseProfitCenter) {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseProfitCenter.getIds());
        bizBaseProfitCenter.setIdsList(longIdsList);
        return bizBaseProfitCenterMapper.selectBizBaseProfitCenterList(bizBaseProfitCenter);
    }

    @Override
    public List<BizBaseProfitCenter> selectProfitCenterListPrecise(BizBaseProfitCenter bizBaseProfitCenter) {
        return bizBaseProfitCenterMapper.selectProfitCenterListPrecise(bizBaseProfitCenter);
    }

    @Override
    public List<BizBaseProfitCenter> selectProfitCenterlist(BizBaseProfitCenter bizBaseProfitCenter) {
        return bizBaseProfitCenterMapper.selectProfitCenterlist(bizBaseProfitCenter);
    }

    /**
     * 新增BaseProfitCenter
     *
     * @param bizBaseProfitCenter BaseProfitCenter
     * @return 结果
     */
    @Override
    public int insertBizBaseProfitCenter(BizBaseProfitCenter bizBaseProfitCenter) {
        bizBaseProfitCenter.setCreateTime(DateUtils.getNowDate());
        int i = bizBaseProfitCenterMapper.insertBizBaseProfitCenter(bizBaseProfitCenter);
        setProfitCenterToRedis();
        return i;
    }

    /**
     * 修改BaseProfitCenter
     *
     * @param bizBaseProfitCenter BaseProfitCenter
     * @return 结果
     */
    @Override
    public int updateBizBaseProfitCenter(BizBaseProfitCenter bizBaseProfitCenter) {
        bizBaseProfitCenter.setUpdateTime(DateUtils.getNowDate());
        int i = bizBaseProfitCenterMapper.updateBizBaseProfitCenter(bizBaseProfitCenter);
        setProfitCenterToRedis();
        return i;
    }

    /**
     * 批量删除BaseProfitCenter
     *
     * @param ids 需要删除的BaseProfitCenter主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseProfitCenterByIds(Long[] ids) {

        int i = bizBaseProfitCenterMapper.updateBizBaseProfitCenterByIds(ids);
        setProfitCenterToRedis();
        return i;
    }

    /**
     * 删除BaseProfitCenter信息
     *
     * @param id BaseProfitCenter主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseProfitCenterById(Long id) {
        int i = bizBaseProfitCenterMapper.deleteBizBaseProfitCenterById(id);
        setProfitCenterToRedis();
        return i;
    }

    @Override
    public String importExcel(List<BizBaseProfitCenter> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        BizBaseProfitCenter center = new BizBaseProfitCenter();
        for (BizBaseProfitCenter bizBaseProfitCenter : bizs) {
            try {
                center.setBusinessGroup(bizBaseProfitCenter.getBusinessGroup());
                center.setGeoCode(bizBaseProfitCenter.getGeoCode());
                center.setRegionMarketCode(bizBaseProfitCenter.getRegionMarketCode());
                center.setSalesOrgCode(bizBaseProfitCenter.getSalesOrgCode());
                center.setSalesOfficeCode(bizBaseProfitCenter.getSalesOfficeCode());
                center.setProfitCenterCode(bizBaseProfitCenter.getProfitCenterCode());
                center.setDummyGtnMtm(bizBaseProfitCenter.getDummyGtnMtm());
                center.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
                List<BizBaseProfitCenter> buList = bizBaseProfitCenterMapper.selectBizBaseProfitCenterListCheck(center);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseProfitCenter.setId(buList.get(0).getId());
                    bizBaseProfitCenter.setUpdateTime(DateUtils.getNowDate());
                    bizBaseProfitCenterMapper.updateBizBaseProfitCenter(bizBaseProfitCenter);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseProfitCenter.setCreateTime(DateUtils.getNowDate());
                bizBaseProfitCenter.setUpdateTime(DateUtils.getNowDate());
                bizBaseProfitCenter.setCreateBy(loginName);
                bizBaseProfitCenter.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
                bizBaseProfitCenterMapper.insertBizBaseProfitCenter(bizBaseProfitCenter);

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

    @Override
    public List<BizBaseProfitCenter> getDropDownList(BizBaseProfitCenter bizBaseProfitCenter) {
        List<BizBaseProfitCenter> list;
        if (bizBaseProfitCenter.getTempField().equals("bwbu")) {
            list = bizBaseProfitCenterMapper.getDropDownListByBwbu(bizBaseProfitCenter);
        } else if (bizBaseProfitCenter.getTempField().equals("dummy_gtn_mtm")) {
            list = bizBaseProfitCenterMapper.getDropDownListByMtm(bizBaseProfitCenter);
        } else {
            list = bizBaseProfitCenterMapper.getDropDownList(bizBaseProfitCenter);
        }
        return list;
    }

}
