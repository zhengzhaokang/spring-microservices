package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseCountry;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseRegionMarket;
import com.microservices.otmp.masterdata.domain.entity.dto.MsDropDownDTO;
import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseCountry;
import org.apache.ibatis.annotations.Param;

/**
 * BaseCountryMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public interface BizBaseCountryMapper
{
    /**
     * 查询BaseCountry
     * 
     * @param id BaseCountry主键
     * @return BaseCountry
     */
    public BizBaseCountry selectBizBaseCountryById(Long id);

    /**
     * 查询BaseCountry列表
     * 
     * @param bizBaseCountry BaseCountry
     * @return BaseCountry集合
     */
    @DataPermissions(tableName = "biz_base_country")
    public List<BizBaseCountry> selectBizBaseCountryList(BizBaseCountry bizBaseCountry);


    /**
     * 查询BaseCountry列表 去重
     *
     * @param bizBaseCountry BaseCountry
     * @return BaseCountry集合
     */
    @DataPermissions(tableName = "biz_base_country")
    public List<BizBaseCountry> selectCountrylist(BizBaseCountry bizBaseCountry);

    /**
     * 新增BaseCountry
     * 
     * @param bizBaseCountry BaseCountry
     * @return 结果
     */
    public int insertBizBaseCountry(BizBaseCountry bizBaseCountry);

    /**
     * 修改BaseCountry
     * 
     * @param bizBaseCountry BaseCountry
     * @return 结果
     */
    public int updateBizBaseCountry(BizBaseCountry bizBaseCountry);

    /**
     * 删除BaseCountry
     * 
     * @param id BaseCountry主键
     * @return 结果
     */
    public int deleteBizBaseCountryById(Long id);

    /**
     * 批量删除BaseCountry
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseCountryByIds(Long[] ids);

    List<BizBaseCountry> selectBizBaseCountryListCheck(BizBaseCountry bizBaseComBiz);

    int updateBizBaseCountryByIds(Long[] ids);

    @DataPermissions(tableName = "biz_base_country")
    List<MsDropDownDTO> getMsDropDownList(@Param("geoCodeList") List<String> geoCodeList, @Param("regionCodeList") List<String> regionCodeList);

    List<BizBaseCountry> countrySelect(BizBaseCountry bizBaseCountry);
}
