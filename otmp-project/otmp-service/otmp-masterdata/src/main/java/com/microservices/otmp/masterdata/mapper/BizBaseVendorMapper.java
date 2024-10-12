package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.masterdata.domain.BizBaseVendor;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorAndPerferBankDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseVendorDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * baseComBizMapper接口
 * 
 * @author sdms
 * @date 2022-01-17
 */
public interface BizBaseVendorMapper
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
    public int updateBizBaseVendor (BizBaseVendorDO bizBaseVendor);

    /**
     * 删除lgvm vendor
     *
     * @param id lgvm vendor主键
     * @return 结果
     */
    public int deleteBizBaseVendorById(Long id);

    /**
     * 批量删除lgvm vendor
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseVendorByIds(Long[] ids);

    public int insertVendorLgvmKafka(BizBaseVendor testLgvmdsKafka);

    public int selectCountByVendorCode(String vendorCode, String companyCode);

    public int updateVendor(BizBaseVendor testLgvmdsKafka);
    List<BizBaseVendorDTO> getVendorAndBankInfoList(BizBaseVendorDTO bizBaseVendorDTO);
    public int deleteBizBaseVendorByUpdateTime(@Param("vendorCode") String vendorCode, @Param("date") Date date);

    public int updateErrorInfoByVendor(@Param("vendorCode")String vendorCode, @Param("errorInfo")String errorInfo);

    public BizBaseVendorAndPerferBankDTO getVendor(String vendorCode);

    public String getVendorCodeByCustomerId(String customerId);

    BizBaseVendorDO getVendorNameByCode(String vendorCode);
}
