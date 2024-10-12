package com.microservices.otmp.masterdata.mapper;

import java.util.Date;
import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseVendorBank;
import org.apache.ibatis.annotations.Param;

/**
 * biz_base_vendor_bank from M&SMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-25
 */
public interface BizBaseVendorBankMapper
{
    /**
     * 查询biz_base_vendor_bank from M&S
     * 
     * @param id biz_base_vendor_bank from M&S主键
     * @return biz_base_vendor_bank from M&S
     */
    public BizBaseVendorBank selectBizBaseVendorBankById(Long id);

    /**
     * 查询biz_base_vendor_bank from M&S列表
     * 
     * @param bizBaseVendorBank biz_base_vendor_bank from M&S
     * @return biz_base_vendor_bank from M&S集合
     */
    @DataPermissions(tableName = "biz_base_vendor_bank")
    public List<BizBaseVendorBank> selectBizBaseVendorBankList(BizBaseVendorBank bizBaseVendorBank);

    /**
     * 新增biz_base_vendor_bank from M&S
     * 
     * @param bizBaseVendorBank biz_base_vendor_bank from M&S
     * @return 结果
     */
    public int insertBizBaseVendorBank(BizBaseVendorBank bizBaseVendorBank);

    /**
     * 修改biz_base_vendor_bank from M&S
     * 
     * @param bizBaseVendorBank biz_base_vendor_bank from M&S
     * @return 结果
     */
    public int updateBizBaseVendorBank(BizBaseVendorBank bizBaseVendorBank);

    /**
     * 删除biz_base_vendor_bank from M&S
     * 
     * @param id biz_base_vendor_bank from M&S主键
     * @return 结果
     */
    public int deleteBizBaseVendorBankById(Long id);

    /**
     * 批量删除biz_base_vendor_bank from M&S
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseVendorBankByIds(Long[] ids);

    int deleteBank(@Param("vendorCode") String vendorCode, @Param("updateTime") Date updateTime);
}
