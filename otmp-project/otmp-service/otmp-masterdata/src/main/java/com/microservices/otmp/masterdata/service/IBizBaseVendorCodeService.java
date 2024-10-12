package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseVendorCode;

/**
 * biz_base_vendor_code from ECCService接口
 * 
 * @author lovefamily
 * @date 2022-04-25
 */
public interface IBizBaseVendorCodeService
{
    /**
     * 查询biz_base_vendor_code from ECC
     * 
     * @param id biz_base_vendor_code from ECC主键
     * @return biz_base_vendor_code from ECC
     */
    public BizBaseVendorCode selectBizBaseVendorCodeById(Long id);

    /**
     * 查询biz_base_vendor_code from ECC列表
     * 
     * @param bizBaseVendorCode biz_base_vendor_code from ECC
     * @return biz_base_vendor_code from ECC集合
     */
    public List<BizBaseVendorCode> selectBizBaseVendorCodeList(BizBaseVendorCode bizBaseVendorCode);

    /**
     * 新增biz_base_vendor_code from ECC
     * 
     * @param bizBaseVendorCode biz_base_vendor_code from ECC
     * @return 结果
     */
    public int insertBizBaseVendorCode(BizBaseVendorCode bizBaseVendorCode);

    /**
     * 修改biz_base_vendor_code from ECC
     * 
     * @param bizBaseVendorCode biz_base_vendor_code from ECC
     * @return 结果
     */
    public int updateBizBaseVendorCode(BizBaseVendorCode bizBaseVendorCode);

    /**
     * 批量删除biz_base_vendor_code from ECC
     * 
     * @param ids 需要删除的biz_base_vendor_code from ECC主键集合
     * @return 结果
     */
    public int deleteBizBaseVendorCodeByIds(Long[] ids);

    /**
     * 删除biz_base_vendor_code from ECC信息
     * 
     * @param id biz_base_vendor_code from ECC主键
     * @return 结果
     */
    public int deleteBizBaseVendorCodeById(Long id);
}
