package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseVendorCode;
import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseVendorCode;

/**
 * biz_base_vendor_code from ECCMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-25
 */
public interface BizBaseVendorCodeMapper
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
    @DataPermissions(tableName = "biz_base_vendor_code")
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
     * 删除biz_base_vendor_code from ECC
     * 
     * @param id biz_base_vendor_code from ECC主键
     * @return 结果
     */
    public int deleteBizBaseVendorCodeById(Long id);

    /**
     * 批量删除biz_base_vendor_code from ECC
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseVendorCodeByIds(Long[] ids);
}
