package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseBpTypeCustomer;

/**
 * BaseBpTypeCustomerMapper接口
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
public interface BizBaseBpTypeCustomerMapper
{
    /**
     * 查询BaseBpTypeCustomer
     * 
     * @param id BaseBpTypeCustomer主键
     * @return BaseBpTypeCustomer
     */
    public BizBaseBpTypeCustomer selectBizBaseBpTypeCustomerById(Long id);

    /**
     * 查询BaseBpTypeCustomer列表
     * 
     * @param bizBaseBpTypeCustomer BaseBpTypeCustomer
     * @return BaseBpTypeCustomer集合
     */
    @DataPermissions(tableName = "biz_base_bp_type_customer")
    public List<BizBaseBpTypeCustomer> selectBizBaseBpTypeCustomerList(BizBaseBpTypeCustomer bizBaseBpTypeCustomer);

    /**
     * 新增BaseBpTypeCustomer
     * 
     * @param bizBaseBpTypeCustomer BaseBpTypeCustomer
     * @return 结果
     */
    public int insertBizBaseBpTypeCustomer(BizBaseBpTypeCustomer bizBaseBpTypeCustomer);

    /**
     * 修改BaseBpTypeCustomer
     * 
     * @param bizBaseBpTypeCustomer BaseBpTypeCustomer
     * @return 结果
     */
    public int updateBizBaseBpTypeCustomer(BizBaseBpTypeCustomer bizBaseBpTypeCustomer);

    /**
     * 删除BaseBpTypeCustomer
     * 
     * @param id BaseBpTypeCustomer主键
     * @return 结果
     */
    public int deleteBizBaseBpTypeCustomerById(Long id);

    /**
     * 批量删除BaseBpTypeCustomer
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseBpTypeCustomerByIds(Long[] ids);
}
