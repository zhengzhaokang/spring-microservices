package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseEndCustomer;

/**
 * BaseEndCustomerService接口
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public interface IBizBaseEndCustomerService
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
     * 批量删除BaseEndCustomer
     * 
     * @param ids 需要删除的BaseEndCustomer主键集合
     * @return 结果
     */
    public int deleteBizBaseEndCustomerByIds(Long[] ids);

    /**
     * 删除BaseEndCustomer信息
     * 
     * @param id BaseEndCustomer主键
     * @return 结果
     */
    public int deleteBizBaseEndCustomerById(Long id);

    String importExcel(List<BizBaseEndCustomer> bizs, String loginName);

    List<BizBaseEndCustomer> getDropDownList(BizBaseEndCustomer bizBaseEndCustomer);

    public BizBaseEndCustomer removeCache(Long id);
}
