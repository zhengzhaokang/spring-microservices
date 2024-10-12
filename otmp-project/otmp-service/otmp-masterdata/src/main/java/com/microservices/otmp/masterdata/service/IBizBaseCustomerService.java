package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;

/**
 * BaseCustomerService接口
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public interface IBizBaseCustomerService
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
    public List<BizBaseCustomer> selectBizBaseCustomerList(BizBaseCustomer bizBaseCustomer);

    /**
     * 新增BaseCustomer
     * 
     * @param bizBaseCustomer BaseCustomer
     * @return 结果
     */
    public int insertBizBaseCustomer(BizBaseCustomer bizBaseCustomer);

    /**
     * ecc kafka新增BaseCustomer
     *
     * @param bizBaseCustomer BaseCustomer
     * @return 结果
     */
    public int insertBizBaseCustomerByEcc(BizBaseCustomer bizBaseCustomer);

    /**
     * 修改BaseCustomer
     * 
     * @param bizBaseCustomer BaseCustomer
     * @return 结果
     */
    public int updateBizBaseCustomer(BizBaseCustomer bizBaseCustomer);

    /**
     * ecc kafka 修改BaseCustomer
     *
     * @param bizBaseCustomer BaseCustomer
     * @return 结果
     */
    public int updateBizBaseCustomerByEcc(BizBaseCustomer bizBaseCustomer);

    /**
     * 批量删除BaseCustomer
     * 
     * @param ids 需要删除的BaseCustomer主键集合
     * @return 结果
     */
    public int deleteBizBaseCustomerByIds(Long[] ids);

    /**
     * 删除BaseCustomer信息
     * 
     * @param id BaseCustomer主键
     * @return 结果
     */
    public int deleteBizBaseCustomerById(Long id);

    String importExcel(List<BizBaseCustomer> bizs, String loginName);

    List<BizBaseCustomer> getDropDownList(BizBaseCustomer bizBaseCustomer);

    BizBaseCustomer getCustomerInfo(BizBaseCustomer bizBaseCustomer);

    BizBaseCustomer getCustomer(BizBaseCustomer bizBaseCustomer);

    public BizBaseCustomer removeCache(Long id);
}
