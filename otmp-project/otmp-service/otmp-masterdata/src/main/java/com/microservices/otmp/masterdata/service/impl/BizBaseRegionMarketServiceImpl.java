package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.domain.BizBaseRegionMarket;
import com.microservices.otmp.masterdata.mapper.BizBaseRegionMarketMapper;
import com.microservices.otmp.masterdata.service.IBizBaseRegionMarketService;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BizBaseRegionMarketService业务层处理
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
@Service
public class BizBaseRegionMarketServiceImpl implements IBizBaseRegionMarketService
{
    @Autowired
    private BizBaseRegionMarketMapper bizBaseRegionMarketMapper;
    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";

    public void setMarketToRedis() {
        List<BizBaseRegionMarket> regionMarkets = this.selectBizBaseRegionMarketList(new BizBaseRegionMarket());
        redisUtils.set(REDIS_NAME_PREFIX + "region_market_code", regionMarkets, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    /**
     * 查询BizBaseRegionMarket
     * 
     * @param id BizBaseRegionMarket主键
     * @return BizBaseRegionMarket
     */
    @Override
    public BizBaseRegionMarket selectBizBaseRegionMarketById(Long id)
    {
        return bizBaseRegionMarketMapper.selectBizBaseRegionMarketById(id);
    }

    /**
     * 查询BizBaseRegionMarket列表
     * 
     * @param bizBaseRegionMarket BizBaseRegionMarket
     * @return BizBaseRegionMarket
     */
    @Override
    public List<BizBaseRegionMarket> selectBizBaseRegionMarketList(BizBaseRegionMarket bizBaseRegionMarket)
    {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseRegionMarket.getIds());
        bizBaseRegionMarket.setIdsList(longIdsList);
        return bizBaseRegionMarketMapper.selectBizBaseRegionMarketList(bizBaseRegionMarket);
    }

    @Override
    public List<BizBaseDropDownList> getCurrencyForPool(BizBaseRegionMarket bizBaseRegionMarket)
    {
        return bizBaseRegionMarketMapper.getCurrencyForPool(bizBaseRegionMarket);
    }

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseRegionMarket集合
     */
    @Override
    public List<BizBaseRegionMarket> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        return bizBaseRegionMarketMapper.getDropDownList(bizBaseDropDownCondition);
    }

    /**
     * 新增BizBaseRegionMarket
     * 
     * @param bizBaseRegionMarket BizBaseRegionMarket
     * @return 结果
     */


    @Override
    public int insertBizBaseRegionMarket(BizBaseRegionMarket bizBaseRegionMarket)
    {
        bizBaseRegionMarket.setCreateTime(DateUtils.getNowDate());
        int i = bizBaseRegionMarketMapper.insertBizBaseRegionMarket(bizBaseRegionMarket);
        setMarketToRedis();
        return i;
    }

    /**
     * 修改BizBaseRegionMarket
     * 
     * @param bizBaseRegionMarket BizBaseRegionMarket
     * @return 结果
     */
    @Override
    public int updateBizBaseRegionMarket(BizBaseRegionMarket bizBaseRegionMarket)
    {
        bizBaseRegionMarket.setUpdateTime(DateUtils.getNowDate());
         int i= bizBaseRegionMarketMapper.updateBizBaseRegionMarket(bizBaseRegionMarket);
        setMarketToRedis();
        return i;
    }

    /**
     * 批量删除BizBaseRegionMarket
     * 
     * @param ids 需要删除的BizBaseRegionMarket主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseRegionMarketByIds(Long[] ids)
    {
        int i= bizBaseRegionMarketMapper.updateBizBaseRegionMarketByIds(ids);
        setMarketToRedis();
        return i;
    }

    /**
     * 删除BizBaseRegionMarket信息
     * 
     * @param id BizBaseRegionMarket主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseRegionMarketById(Long id)
    {
        int i= bizBaseRegionMarketMapper.deleteBizBaseRegionMarketById(id);
        setMarketToRedis();
        return i;
    }

    @Override
    public List<String> selectBizBaseRegionMarketListByGeoCode(String geoCode) {
        return bizBaseRegionMarketMapper.selectBizBaseRegionMarketListByGeoCode(geoCode);
    }

    @Override
    public String importExcel(List<BizBaseRegionMarket> bizs, String loginName) {


        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("BizBaseRegionMarket 数据不能为空！");
        }
        for (BizBaseRegionMarket bizBaseRegionMarket : bizs) {
            try {

                List<BizBaseRegionMarket> buList = bizBaseRegionMarketMapper.selectBizBaseRegionMarketListCheck(bizBaseRegionMarket);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseRegionMarket.setId(buList.get(0).getId());
                    bizBaseRegionMarket.setUpdateTime(DateUtils.getNowDate());
                    bizBaseRegionMarketMapper.updateBizBaseRegionMarket(bizBaseRegionMarket);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseRegionMarket.setCreateTime(DateUtils.getNowDate());
                bizBaseRegionMarket.setUpdateTime(DateUtils.getNowDate());
                bizBaseRegionMarket.setCreateBy(loginName);
                bizBaseRegionMarket.setStatus("Y");
                bizBaseRegionMarketMapper.insertBizBaseRegionMarket(bizBaseRegionMarket);

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
    public BizBaseRegionMarket selectBizBaseRegionMarket(BizBaseRegionMarket bizBaseRegionMarket) {
        Assert.notNull(bizBaseRegionMarket, "bizBaseRegionMarket is null");
        Assert.hasText(bizBaseRegionMarket.getGeoCode(), "bizBaseRegionMarket.geoCode is blank");
        Assert.hasText(bizBaseRegionMarket.getRegionMarketCode(), "bizBaseRegionMarket.regionMarketCode is blank");
        List<BizBaseRegionMarket> bizBaseRegionMarkets = bizBaseRegionMarketMapper.selectBizBaseRegionMarketList(bizBaseRegionMarket);
        if (CollectionUtils.isNotEmpty(bizBaseRegionMarkets) ) {
            Optional<BizBaseRegionMarket> first = bizBaseRegionMarkets.stream().findFirst();
            if (first.isPresent()) {
                return first.get();
            }
        }
        return null;
    }
}
