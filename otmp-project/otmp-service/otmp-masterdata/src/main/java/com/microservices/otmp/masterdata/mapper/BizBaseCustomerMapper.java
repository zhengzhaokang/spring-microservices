package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;

/**
 * BaseCustomerMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public interface BizBaseCustomerMapper
{
    /**
     * 查询BaseCustomer
     * 
     * @param id BaseCustomer主键
     * @return BaseCustomer
     */
    public BizBaseCustomer selectBizBaseCustomerById(Long id);

    /**
     * 查询BaseCustomer列表
     * 
     * @param bizBaseCustomer BaseCustomer
     * @return BaseCustomer集合
     */
    @DataPermissions(tableName = "biz_base_customer")
    public List<BizBaseCustomer> selectBizBaseCustomerList(BizBaseCustomer bizBaseCustomer);

    /**
     * 新增BaseCustomer
     * 
     * @param bizBaseCustomer BaseCustomer
     * @return 结果
     */
    public int insertBizBaseCustomer(BizBaseCustomer bizBaseCustomer);

    /**
     * 修改BaseCustomer
     * 
     * @param bizBaseCustomer BaseCustomer
     * @return 结果
     */
    public int updateBizBaseCustomer(BizBaseCustomer bizBaseCustomer);

    /**
     * 删除BaseCustomer
     * 
     * @param id BaseCustomer主键
     * @return 结果
     */
    public int deleteBizBaseCustomerById(Long id);

    /**
     * 批量删除BaseCustomer
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseCustomerByIds(Long[] ids);

    List<BizBaseCustomer> selectBizBaseCustomerListCheck(BizBaseCustomer bizBaseComBiz);

    int updateBizBaseCustomerByIds(Long[] ids);

    List<BizBaseCustomer> getDropDownList(BizBaseCustomer bizBaseCustomer);
}
