package com.microservices.otmp.masterdata.service;

import com.microservices.otmp.masterdata.domain.BizBaseVendor;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorAndBanksDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorAndPerferBankDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorDTO;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Date;
import java.util.List;

/**
 * TestLgvmdsKafkaService接口
 * 
 * @author sdms
 * @date 2022-01-17
 */
public interface IBizBaseVendorService
{
    /**
     * 查询lgvm vendor
     *
     * @param id lgvm vendor主键
     * @return lgvm vendorDTO
     */
    public BizBaseVendorDTO selectBizBaseVendorById(Long id);

    /**
     * 查询lgvm vendor列表
     *
     * @param bizBaseVendor lgvm vendor
     * @return lgvm vendorDTO集合
     */
    public List<BizBaseVendorDTO> selectBizBaseVendorList(BizBaseVendorDTO bizBaseVendor);

    /**
     * 新增lgvm vendor
     *
     * @param bizBaseVendorDTO lgvm vendor
     * @return 结果
     */
    public int insertBizBaseVendor(BizBaseVendorDTO bizBaseVendor);

    /**
     * 修改lgvm vendor
     *
     * @param bizBaseVendorDTO lgvm vendor
     * @return 结果
     */
    public int updateBizBaseVendor(BizBaseVendorDTO bizBaseVendor);

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

    public int insertVendorLgvmKafka(BizBaseVendor testLgvmdsKafka);

    public int selectCountByVendorCode(String vendorCode, String errorInfo);

    public int updateVendor(BizBaseVendor testLgvmdsKafka);

    List<BizBaseVendorDTO> getVendorAndBankInfoList(BizBaseVendorDTO bizBaseVendor);

    /**
     * 删除updateTime ！= date的数据
     * @param date
     * @return
     */
    @CacheEvict(value = "MASTER_PERFER_BANK_INFO", key = "#vendorCode")
    public int deleteBizBaseVendorByUpdateTime(String vendorCode, Date date);

    /**
     * 更新errorInfo
     * @param vendorCode
     * @return
     */
    public int updateErrorInfoByVendor(String vendorCode);

    List<BizBaseVendorAndPerferBankDTO> getVendorAndPerferBank(List<String> customerIds);

    BizBaseVendorAndBanksDTO getVendorAndBanksByCode(String vendorCode);
}
