package com.microservices.otmp.masterdata.service;

import java.util.Date;
import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseVendorBank;

/**
 * biz_base_vendor_bank from M&SService接口
 * 
 * @author lovefamily
 * @date 2022-04-25
 */
public interface IBizBaseVendorBankService
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
     * 批量删除biz_base_vendor_bank from M&S
     * 
     * @param ids 需要删除的biz_base_vendor_bank from M&S主键集合
     * @return 结果
     */
    public int deleteBizBaseVendorBankByIds(Long[] ids);

    /**
     * 删除biz_base_vendor_bank from M&S信息
     * 
     * @param id biz_base_vendor_bank from M&S主键
     * @return 结果
     */
    public int deleteBizBaseVendorBankById(Long id);

    int deleteBank(String vendorCode, Date updateTime);
}
