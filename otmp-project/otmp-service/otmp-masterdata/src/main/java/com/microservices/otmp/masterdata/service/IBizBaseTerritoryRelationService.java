package com.microservices.otmp.masterdata.service;

import java.util.List;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;

/**
 * BaseTerritoryRelationService接口
 * 
 * @author lovefamily
 * @date 2022-04-26
 */
public interface IBizBaseTerritoryRelationService
{
    /**
     * 查询BaseTerritoryRelation
     * 
     * @param id BaseTerritoryRelation主键
     * @return BaseTerritoryRelation
     */
    public BizBaseTerritoryRelation selectBizBaseTerritoryRelationById(Long id);

    /**
     * 查询BaseTerritoryRelation列表
     * 
     * @param bizBaseTerritoryRelation BaseTerritoryRelation
     * @return BaseTerritoryRelation集合
     */
    public List<BizBaseTerritoryRelation> selectBizBaseTerritoryRelationList(BizBaseTerritoryRelation bizBaseTerritoryRelation);

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseTerritoryRelation集合
     */
    public List<BizBaseTerritoryRelation> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);
    /**
     * 新增BaseTerritoryRelation
     * 
     * @param bizBaseTerritoryRelation BaseTerritoryRelation
     * @return 结果
     */
    public int insertBizBaseTerritoryRelation(BizBaseTerritoryRelation bizBaseTerritoryRelation);

    /**
     * 修改BaseTerritoryRelation
     * 
     * @param bizBaseTerritoryRelation BaseTerritoryRelation
     * @return 结果
     */
    public int updateBizBaseTerritoryRelation(BizBaseTerritoryRelation bizBaseTerritoryRelation);

    /**
     * 批量删除BaseTerritoryRelation
     * 
     * @param ids 需要删除的BaseTerritoryRelation主键集合
     * @return 结果
     */
    public int deleteBizBaseTerritoryRelationByIds(Long[] ids);

    /**
     * 删除BaseTerritoryRelation信息
     * 
     * @param id BaseTerritoryRelation主键
     * @return 结果
     */
    public int deleteBizBaseTerritoryRelationById(Long id);

    String importExcel(List<BizBaseTerritoryRelation> bizs, String loginName);

}
