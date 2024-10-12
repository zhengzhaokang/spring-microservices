package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSegment;
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;

/**
 * BaseTerritoryRelationMapper接口
 *
 * @author lovefamily
 * @date 2022-04-26
 */
public interface BizBaseTerritoryRelationMapper
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
    @DataPermissions(tableName = "biz_base_territory_relation")
    public List<BizBaseTerritoryRelation> selectBizBaseTerritoryRelationList(BizBaseTerritoryRelation bizBaseTerritoryRelation);

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseTerritoryRelation集合
     */
    @DataPermissions(tableName = "biz_base_territory_relation")
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
     * 删除BaseTerritoryRelation
     *
     * @param id BaseTerritoryRelation主键
     * @return 结果
     */
    public int deleteBizBaseTerritoryRelationById(Long id);

    /**
     * 批量删除BaseTerritoryRelation
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseTerritoryRelationByIds(Long[] ids);

    List<BizBaseSegment> selectbizBaseTerritoryRelationListCheck(BizBaseTerritoryRelation bizBaseTerritoryRelation);

}
