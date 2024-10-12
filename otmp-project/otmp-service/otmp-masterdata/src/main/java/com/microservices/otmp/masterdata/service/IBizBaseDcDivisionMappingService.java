package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseDcDivisionMapping;

/**
 * BaseDcDivisionMappingService接口
 * 
 * @author lovefamily
 * @date 2022-04-25
 */
public interface IBizBaseDcDivisionMappingService
{
    /**
     * 查询BaseDcDivisionMapping
     * 
     * @param id BaseDcDivisionMapping主键
     * @return BaseDcDivisionMapping
     */
    public BizBaseDcDivisionMapping selectBizBaseDcDivisionMappingById(Long id);

    /**
     * 查询BaseDcDivisionMapping列表
     * 
     * @param bizBaseDcDivisionMapping BaseDcDivisionMapping
     * @return BaseDcDivisionMapping集合
     */
    public List<BizBaseDcDivisionMapping> selectBizBaseDcDivisionMappingList(BizBaseDcDivisionMapping bizBaseDcDivisionMapping);

    /**
     * 新增BaseDcDivisionMapping
     * 
     * @param bizBaseDcDivisionMapping BaseDcDivisionMapping
     * @return 结果
     */
    public int insertBizBaseDcDivisionMapping(BizBaseDcDivisionMapping bizBaseDcDivisionMapping);

    /**
     * 修改BaseDcDivisionMapping
     * 
     * @param bizBaseDcDivisionMapping BaseDcDivisionMapping
     * @return 结果
     */
    public int updateBizBaseDcDivisionMapping(BizBaseDcDivisionMapping bizBaseDcDivisionMapping);

    /**
     * 批量删除BaseDcDivisionMapping
     * 
     * @param ids 需要删除的BaseDcDivisionMapping主键集合
     * @return 结果
     */
    public int deleteBizBaseDcDivisionMappingByIds(Long[] ids);

    /**
     * 删除BaseDcDivisionMapping信息
     * 
     * @param id BaseDcDivisionMapping主键
     * @return 结果
     */
    public int deleteBizBaseDcDivisionMappingById(Long id);

    String importExcel(List<BizBaseDcDivisionMapping> bizs, String loginName);

    List<BizBaseDcDivisionMapping> dropDownList(BizBaseDcDivisionMapping bizBaseDcDivisionMapping);
}
