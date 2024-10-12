package com.microservices.otmp.masterdata.service.impl;

import java.util.Date;
import java.util.List;
import com.microservices.otmp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseVendorBankMapper;
import com.microservices.otmp.masterdata.domain.BizBaseVendorBank;
import com.microservices.otmp.masterdata.service.IBizBaseVendorBankService;

/**
 * biz_base_vendor_bank from M&SService业务层处理
 * 
 * @author lovefamily
 * @date 2022-04-25
 */
@Service
public class BizBaseVendorBankServiceImpl implements IBizBaseVendorBankService
{
    @Autowired
    private BizBaseVendorBankMapper bizBaseVendorBankMapper;

    /**
     * 查询biz_base_vendor_bank from M&S
     * 
     * @param id biz_base_vendor_bank from M&S主键
     * @return biz_base_vendor_bank from M&S
     */
    @Override
    public BizBaseVendorBank selectBizBaseVendorBankById(Long id)
    {
        return bizBaseVendorBankMapper.selectBizBaseVendorBankById(id);
    }

    /**
     * 查询biz_base_vendor_bank from M&S列表
     * 
     * @param bizBaseVendorBank biz_base_vendor_bank from M&S
     * @return biz_base_vendor_bank from M&S
     */
    @Override
    public List<BizBaseVendorBank> selectBizBaseVendorBankList(BizBaseVendorBank bizBaseVendorBank)
    {
        return bizBaseVendorBankMapper.selectBizBaseVendorBankList(bizBaseVendorBank);
    }

    /**
     * 新增biz_base_vendor_bank from M&S
     * 
     * @param bizBaseVendorBank biz_base_vendor_bank from M&S
     * @return 结果
     */
    @Override
    public int insertBizBaseVendorBank(BizBaseVendorBank bizBaseVendorBank)
    {
        return bizBaseVendorBankMapper.insertBizBaseVendorBank(bizBaseVendorBank);
    }

    /**
     * 修改biz_base_vendor_bank from M&S
     * 
     * @param bizBaseVendorBank biz_base_vendor_bank from M&S
     * @return 结果
     */
    @CacheEvict(value = "MASTER_PERFER_BANK_INFO", key = "#vendorCode")
    @Override
    public int updateBizBaseVendorBank(BizBaseVendorBank bizBaseVendorBank)
    {
        return bizBaseVendorBankMapper.updateBizBaseVendorBank(bizBaseVendorBank);
    }

    /**
     * 批量删除biz_base_vendor_bank from M&S
     * 
     * @param ids 需要删除的biz_base_vendor_bank from M&S主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseVendorBankByIds(Long[] ids)
    {
        return bizBaseVendorBankMapper.deleteBizBaseVendorBankByIds(ids);
    }

    /**
     * 删除biz_base_vendor_bank from M&S信息
     * 
     * @param id biz_base_vendor_bank from M&S主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseVendorBankById(Long id)
    {
        return bizBaseVendorBankMapper.deleteBizBaseVendorBankById(id);
    }

    @Override
    @CacheEvict(value = "MASTER_PERFER_BANK_INFO", key = "#vendorCode")
    public int deleteBank(String vendorCode, Date updateTime) {
        return bizBaseVendorBankMapper.deleteBank(vendorCode, updateTime);
    }
}
