package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.domain.BizBaseRegionMarket;
import com.microservices.otmp.masterdata.domain.entity.vo.TpMsCountryVo;
import org.apache.ibatis.annotations.Param;

/**
 * BizBaseRegionMarketMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
public interface BizBaseRegionMarketMapper
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
    @DataPermissions(tableName = "biz_base_region_market")
    public List<BizBaseRegionMarket> selectBizBaseRegionMarketList(BizBaseRegionMarket bizBaseRegionMarket);
    public List<BizBaseDropDownList> getCurrencyForPool(BizBaseRegionMarket bizBaseRegionMarket);
    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseRegionMarket集合
     */
    @DataPermissions(tableName = "biz_base_region_market")
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
     * 删除BizBaseRegionMarket
     * 
     * @param id BizBaseRegionMarket主键
     * @return 结果
     */
    public int deleteBizBaseRegionMarketById(Long id);

    /**
     * 批量删除BizBaseRegionMarket
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseRegionMarketByIds(Long[] ids);

    public List<String> selectBizBaseRegionMarketListByGeoCode(@Param("geoCode") String geoCode);

    public List<BizBaseRegionMarket> selectBizBaseRegionMarketListCheck(BizBaseRegionMarket bizBaseRegionMarket);

    int updateBizBaseRegionMarketByIds(Long[] ids);

    List<BizBaseRegionMarket> getMsDropDownList(@Param("geoCodeList")List<String> geoCodeList);

}
