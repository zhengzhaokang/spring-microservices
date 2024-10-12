package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOrg;

/**
 * BizBaseSalesOrgMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
public interface BizBaseSalesOrgMapper
{
    /**
     * 查询BizBaseSalesOrg
     * 
     * @param id BizBaseSalesOrg主键
     * @return BizBaseSalesOrg
     */
    public BizBaseSalesOrg selectBizBaseSalesOrgById(Long id);

    /**
     * 查询BizBaseSalesOrg列表
     * 
     * @param bizBaseSalesOrg BizBaseSalesOrg
     * @return BizBaseSalesOrg集合
     */
    @DataPermissions(tableName = "biz_base_sales_org")
    public List<BizBaseSalesOrg> selectBizBaseSalesOrgList(BizBaseSalesOrg bizBaseSalesOrg);

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseSalesOrg集合
     */
    @DataPermissions(tableName = "biz_base_org_office")
    public List<BizBaseSalesOrg> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);

    /**
     * 新增BizBaseSalesOrg
     * 
     * @param bizBaseSalesOrg BizBaseSalesOrg
     * @return 结果
     */
    public int insertBizBaseSalesOrg(BizBaseSalesOrg bizBaseSalesOrg);

    /**
     * 修改BizBaseSalesOrg
     * 
     * @param bizBaseSalesOrg BizBaseSalesOrg
     * @return 结果
     */
    public int updateBizBaseSalesOrg(BizBaseSalesOrg bizBaseSalesOrg);

    /**
     * 删除BizBaseSalesOrg
     * 
     * @param id BizBaseSalesOrg主键
     * @return 结果
     */
    public int deleteBizBaseSalesOrgById(Long id);

    /**
     * 批量删除BizBaseSalesOrg
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseSalesOrgByIds(Long[] ids);

    public List<BizBaseSalesOrg> selectBizBaseSalesOrgListCheck(BizBaseSalesOrg bizBaseSalesOrg);

    int updateBizBaseSalesOrgByIds(Long[] ids);

    /**
     *  下拉
     * @return
     */
    List<BizBaseSalesOrg> localCurrencyList();
}
