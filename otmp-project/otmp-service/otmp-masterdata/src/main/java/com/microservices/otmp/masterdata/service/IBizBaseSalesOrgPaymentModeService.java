package com.microservices.otmp.masterdata.service;

import java.util.List;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOrgPaymentMode;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOrgPaymentMode;

/**
 * BaseSalesOrgPaymentModeService接口
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public interface IBizBaseSalesOrgPaymentModeService
{
    /**
     * 查询BaseSalesOrgPaymentMode
     * 
     * @param id BaseSalesOrgPaymentMode主键
     * @return BaseSalesOrgPaymentMode
     */
    public BizBaseSalesOrgPaymentMode selectBizBaseSalesOrgPaymentModeById(Long id);

    /**
     * 查询BaseSalesOrgPaymentMode列表
     * 
     * @param bizBaseSalesOrgPaymentMode BaseSalesOrgPaymentMode
     * @return BaseSalesOrgPaymentMode集合
     */
    public List<BizBaseSalesOrgPaymentMode> selectBizBaseSalesOrgPaymentModeList(BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode);

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseGtnType集合
     */
    public List<BizBaseSalesOrgPaymentMode> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);

    /**
     * 新增BaseSalesOrgPaymentMode
     * 
     * @param bizBaseSalesOrgPaymentMode BaseSalesOrgPaymentMode
     * @return 结果
     */
    public int insertBizBaseSalesOrgPaymentMode(BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode);

    /**
     * 修改BaseSalesOrgPaymentMode
     * 
     * @param bizBaseSalesOrgPaymentMode BaseSalesOrgPaymentMode
     * @return 结果
     */
    public int updateBizBaseSalesOrgPaymentMode(BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode);

    /**
     * 批量删除BaseSalesOrgPaymentMode
     * 
     * @param ids 需要删除的BaseSalesOrgPaymentMode主键集合
     * @return 结果
     */
    public int deleteBizBaseSalesOrgPaymentModeByIds(Long[] ids);

    /**
     * 删除BaseSalesOrgPaymentMode信息
     * 
     * @param id BaseSalesOrgPaymentMode主键
     * @return 结果
     */
    public int deleteBizBaseSalesOrgPaymentModeById(Long id);

    String importExcel(List<BizBaseSalesOrgPaymentMode> bizs, String loginName);

}
