package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseProductHierarchyByLevel;

/**
 * BaseProductHierarchyByLevelMapper接口
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
public interface BizBaseProductHierarchyByLevelMapper
{
    /**
     * 查询BaseProductHierarchyByLevel
     * 
     * @param id BaseProductHierarchyByLevel主键
     * @return BaseProductHierarchyByLevel
     */
    public BizBaseProductHierarchyByLevel selectBizBaseProductHierarchyByLevelById(Long id);

    /**
     * 查询BaseProductHierarchyByLevel列表
     * 
     * @param bizBaseProductHierarchyByLevel BaseProductHierarchyByLevel
     * @return BaseProductHierarchyByLevel集合
     */
    @DataPermissions(tableName = "biz_base_product_hierarchy_by_level")
    public List<BizBaseProductHierarchyByLevel> selectBizBaseProductHierarchyByLevelList(BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel);

    /**
     * 新增BaseProductHierarchyByLevel
     * 
     * @param bizBaseProductHierarchyByLevel BaseProductHierarchyByLevel
     * @return 结果
     */
    public int insertBizBaseProductHierarchyByLevel(BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel);

    /**
     * 修改BaseProductHierarchyByLevel
     * 
     * @param bizBaseProductHierarchyByLevel BaseProductHierarchyByLevel
     * @return 结果
     */
    public int updateBizBaseProductHierarchyByLevel(BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel);

    /**
     * 删除BaseProductHierarchyByLevel
     * 
     * @param id BaseProductHierarchyByLevel主键
     * @return 结果
     */
    public int deleteBizBaseProductHierarchyByLevelById(Long id);

    /**
     * 批量删除BaseProductHierarchyByLevel
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseProductHierarchyByLevelByIds(Long[] ids);
}
