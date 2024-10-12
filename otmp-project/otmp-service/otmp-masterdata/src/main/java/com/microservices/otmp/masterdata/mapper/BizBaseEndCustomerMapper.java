package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseEndCustomer;

/**
 * BaseEndCustomerMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public interface BizBaseEndCustomerMapper
{
    /**
     * 查询BaseEndCustomer
     * 
     * @param id BaseEndCustomer主键
     * @return BaseEndCustomer
     */
    public BizBaseEndCustomer selectBizBaseEndCustomerById(Long id);

    /**
     * 查询BaseEndCustomer列表
     * 
     * @param bizBaseEndCustomer BaseEndCustomer
     * @return BaseEndCustomer集合
     */
    @DataPermissions(tableName = "biz_base_end_customer")
    public List<BizBaseEndCustomer> selectBizBaseEndCustomerList(BizBaseEndCustomer bizBaseEndCustomer);

    /**
     * 新增BaseEndCustomer
     * 
     * @param bizBaseEndCustomer BaseEndCustomer
     * @return 结果
     */
    public int insertBizBaseEndCustomer(BizBaseEndCustomer bizBaseEndCustomer);

    /**
     * 修改BaseEndCustomer
     * 
     * @param bizBaseEndCustomer BaseEndCustomer
     * @return 结果
     */
    public int updateBizBaseEndCustomer(BizBaseEndCustomer bizBaseEndCustomer);

    /**
     * 删除BaseEndCustomer
     * 
     * @param id BaseEndCustomer主键
     * @return 结果
     */
    public int deleteBizBaseEndCustomerById(Long id);

    /**
     * 批量删除BaseEndCustomer
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseEndCustomerByIds(Long[] ids);

    List<BizBaseEndCustomer> selectBizBaseEndCustomerListCheck(BizBaseEndCustomer bizBaseComBiz);

    int updateBizBaseEndCustomerByIds(Long[] ids);

    @DataPermissions(tableName = "biz_base_end_customer")
    List<BizBaseEndCustomer> getDropDownList(BizBaseEndCustomer bizBaseEndCustomer);

}
