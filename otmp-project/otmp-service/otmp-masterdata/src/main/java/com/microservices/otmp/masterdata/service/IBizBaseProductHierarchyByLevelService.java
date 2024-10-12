package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseProductHierarchyByLevel;

/**
 * BaseProductHierarchyByLevelService接口
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
public interface IBizBaseProductHierarchyByLevelService
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
     * 批量删除BaseProductHierarchyByLevel
     * 
     * @param ids 需要删除的BaseProductHierarchyByLevel主键集合
     * @return 结果
     */
    public int deleteBizBaseProductHierarchyByLevelByIds(Long[] ids);

    /**
     * 删除BaseProductHierarchyByLevel信息
     * 
     * @param id BaseProductHierarchyByLevel主键
     * @return 结果
     */
    public int deleteBizBaseProductHierarchyByLevelById(Long id);

    String importExcel(List<BizBaseProductHierarchyByLevel> bizs, String loginName);
}
