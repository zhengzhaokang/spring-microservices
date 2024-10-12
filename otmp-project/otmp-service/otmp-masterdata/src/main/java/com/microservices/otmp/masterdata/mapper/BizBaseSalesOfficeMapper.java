package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOffice;

/**
 * BizBaseSalesOfficeMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
public interface BizBaseSalesOfficeMapper
{
    /**
     * 查询BizBaseSalesOffice
     * 
     * @param id BizBaseSalesOffice主键
     * @return BizBaseSalesOffice
     */
    public BizBaseSalesOffice selectBizBaseSalesOfficeById(Long id);

    /**
     * 查询BizBaseSalesOffice列表
     * 
     * @param bizBaseSalesOffice BizBaseSalesOffice
     * @return BizBaseSalesOffice集合
     */
    @DataPermissions(tableName = "biz_base_sales_office")
    public List<BizBaseSalesOffice> selectBizBaseSalesOfficeList(BizBaseSalesOffice bizBaseSalesOffice);

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseSalesOffice集合
     */
    @DataPermissions(tableName = "biz_base_org_office")
    public List<BizBaseSalesOffice> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);
    /**
     * 新增BizBaseSalesOffice
     * 
     * @param bizBaseSalesOffice BizBaseSalesOffice
     * @return 结果
     */
    public int insertBizBaseSalesOffice(BizBaseSalesOffice bizBaseSalesOffice);

    /**
     * 修改BizBaseSalesOffice
     * 
     * @param bizBaseSalesOffice BizBaseSalesOffice
     * @return 结果
     */
    public int updateBizBaseSalesOffice(BizBaseSalesOffice bizBaseSalesOffice);

    /**
     * 删除BizBaseSalesOffice
     * 
     * @param id BizBaseSalesOffice主键
     * @return 结果
     */
    public int deleteBizBaseSalesOfficeById(Long id);

    /**
     * 批量删除BizBaseSalesOffice
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseSalesOfficeByIds(Long[] ids);

    public List<BizBaseSalesOffice> selectBizBaseSalesOfficeListCheck(BizBaseSalesOffice bizBaseSalesOffice);

    int updateBizBaseSalesOfficeByIds(Long[] ids);
}
