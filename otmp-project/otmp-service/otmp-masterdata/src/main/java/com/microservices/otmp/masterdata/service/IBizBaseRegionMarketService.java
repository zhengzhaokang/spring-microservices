package com.microservices.otmp.masterdata.service;

import java.util.List;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.domain.BizBaseRegionMarket;

/**
 * BizBaseRegionMarketService接口
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
public interface IBizBaseRegionMarketService
{
    /**
     * 查询BizBaseRegionMarket
     * 
     * @param id BizBaseRegionMarket主键
     * @return BizBaseRegionMarket
     */
    public BizBaseRegionMarket selectBizBaseRegionMarketById(Long id);

    /**
     * 查询BizBaseRegionMarket列表
     * 
     * @param bizBaseRegionMarket BizBaseRegionMarket
     * @return BizBaseRegionMarket集合
     */
    public List<BizBaseRegionMarket> selectBizBaseRegionMarketList(BizBaseRegionMarket bizBaseRegionMarket);

    public List<BizBaseDropDownList> getCurrencyForPool(BizBaseRegionMarket bizBaseRegionMarket);

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseRegionMarket集合
     */
    public List<BizBaseRegionMarket> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);
    /**
     * 新增BizBaseRegionMarket
     * 
     * @param bizBaseRegionMarket BizBaseRegionMarket
     * @return 结果
     */
    public int insertBizBaseRegionMarket(BizBaseRegionMarket bizBaseRegionMarket);

    /**
     * 修改BizBaseRegionMarket
     * 
     * @param bizBaseRegionMarket BizBaseRegionMarket
     * @return 结果
     */
    public int updateBizBaseRegionMarket(BizBaseRegionMarket bizBaseRegionMarket);

    /**
     * 批量删除BizBaseRegionMarket
     * 
     * @param ids 需要删除的BizBaseRegionMarket主键集合
     * @return 结果
     */
    public int deleteBizBaseRegionMarketByIds(Long[] ids);

    /**
     * 删除BizBaseRegionMarket信息
     * 
     * @param id BizBaseRegionMarket主键
     * @return 结果
     */
    public int deleteBizBaseRegionMarketById(Long id);

    public List<String> selectBizBaseRegionMarketListByGeoCode(String geoCode);

    public String importExcel(List<BizBaseRegionMarket> bizs, String loginName);

    BizBaseRegionMarket selectBizBaseRegionMarket(BizBaseRegionMarket bizBaseRegionMarket);
}
