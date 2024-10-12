package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseDcDivisionMapping;
import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseDcDivisionMapping;

/**
 * BaseDcDivisionMappingMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-25
 */
public interface BizBaseDcDivisionMappingMapper
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
    @DataPermissions(tableName = "biz_base_dc_division_mapping")
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
     * 删除BaseDcDivisionMapping
     * 
     * @param id BaseDcDivisionMapping主键
     * @return 结果
     */
    public int deleteBizBaseDcDivisionMappingById(Long id);

    /**
     * 批量删除BaseDcDivisionMapping
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseDcDivisionMappingByIds(Long[] ids);

    List<BizBaseDcDivisionMapping> selectBizBaseProfitCenterListCheck(BizBaseDcDivisionMapping baseDcDivisionMapping);

    int updateBizBaseDcDivisionMappingByIds(Long[] ids);
}
