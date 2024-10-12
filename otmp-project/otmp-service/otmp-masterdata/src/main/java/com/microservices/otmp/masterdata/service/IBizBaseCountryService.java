package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseCountry;

/**
 * BaseCountryService接口
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public interface IBizBaseCountryService
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
    public List<BizBaseCountry> selectBizBaseCountryList(BizBaseCountry bizBaseCountry);


    /**
     * 查询BaseCountry列表 去重
     * @param bizBaseCountry BaseCountry
     * @return BaseCountry集合
     */
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
     * 批量删除BaseCountry
     * 
     * @param ids 需要删除的BaseCountry主键集合
     * @return 结果
     */
    public int deleteBizBaseCountryByIds(Long[] ids);

    /**
     * 删除BaseCountry信息
     * 
     * @param id BaseCountry主键
     * @return 结果
     */
    public int deleteBizBaseCountryById(Long id);

    String importExcel(List<BizBaseCountry> bizs, String loginName);

    List<BizBaseCountry> countrySelect(BizBaseCountry bizBaseCountry);
}
