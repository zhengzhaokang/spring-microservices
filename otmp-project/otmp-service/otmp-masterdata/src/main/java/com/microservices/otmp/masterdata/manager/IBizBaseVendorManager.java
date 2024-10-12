package com.microservices.otmp.masterdata.manager;

import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorAndPerferBankDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseVendorDO;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;
import java.util.List;


/**
 * lgvm vendorManager接口
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
public interface IBizBaseVendorManager
{
    /**
     * 查询lgvm vendor
     * 
     * @param id lgvm vendor主键
     * @return lgvm vendor
     */
    public BizBaseVendorDO selectBizBaseVendorById(Long id);

    /**
     * 查询lgvm vendor列表
     *
     * @param bizBaseVendor lgvm vendor
     * @return lgvm vendor集合
     */
    public List<BizBaseVendorDO> selectBizBaseVendorList(BizBaseVendorDTO bizBaseVendor);

    /**
     * 新增lgvm vendor
     *
     * @param bizBaseVendor lgvm vendor
     * @return 结果
     */
    public int insertBizBaseVendor(BizBaseVendorDO bizBaseVendor);

    /**
     * 修改lgvm vendor
     *
     * @param bizBaseVendor lgvm vendor
     * @return 结果
     */
    public int updateBizBaseVendor(BizBaseVendorDO bizBaseVendor);

    /**
     * 批量删除lgvm vendor
     * 
     * @param ids 需要删除的lgvm vendor主键集合
     * @return 结果
     */
    public int deleteBizBaseVendorByIds(Long[] ids);

    /**
     * 删除lgvm vendor信息
     * 
     * @param id lgvm vendor主键
     * @return 结果
     */
    public int deleteBizBaseVendorById(Long id);

    /**
     * 删除updateTime ！= date的数据
     * @param date
     * @return
     */
    public int deleteBizBaseVendorByUpdateTime(String vendorCode, Date date);

    /**
     * 更新errorInfo
     * @param vendorCode
     * @return
     */
    public int updateErrorInfoByVendor(String vendorCode, String errorInfo);

    /**
     * 查询vendor信息
     *
     * @paraString customerId
     * @return lgvm vendor集合
     */
    public BizBaseVendorAndPerferBankDTO getVendor(String customerId);

    @Cacheable(value = "MASTER_VENDOR_CODE", key = "#customerId")
    public String getVendorCodeByCustomerId(String customerId);

    BizBaseVendorDO getVendorNameByCode(String vendorCode);

    @Cacheable(value = "MASTER_PERFER_BANK_INFO", key = "#vendorCode")
    public BizBaseVendorAndPerferBankDTO getVendorAndPerferBankByCustomerId(String vendorCode);
}
