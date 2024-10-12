package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseBpTypeCustomer;

/**
 * BaseBpTypeCustomerService接口
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
public interface IBizBaseBpTypeCustomerService
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
     * 批量删除BaseBpTypeCustomer
     * 
     * @param ids 需要删除的BaseBpTypeCustomer主键集合
     * @return 结果
     */
    public int deleteBizBaseBpTypeCustomerByIds(Long[] ids);

    /**
     * 删除BaseBpTypeCustomer信息
     * 
     * @param id BaseBpTypeCustomer主键
     * @return 结果
     */
    public int deleteBizBaseBpTypeCustomerById(Long id);

    String importExcel(List<BizBaseBpTypeCustomer> bizs, String loginName);

    List<BizBaseBpTypeCustomer> selectBizBaseBpTypeCustomerListDistinctBpType(BizBaseBpTypeCustomer bizBaseBpTypeCustomer);
}
