package com.microservices.otmp.masterdata.service.impl;

import java.util.List;
import com.microservices.otmp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseVendorCodeMapper;
import com.microservices.otmp.masterdata.domain.BizBaseVendorCode;
import com.microservices.otmp.masterdata.service.IBizBaseVendorCodeService;

/**
 * biz_base_vendor_code from ECCService业务层处理
 * 
 * @author lovefamily
 * @date 2022-04-25
 */
@Service
public class BizBaseVendorCodeServiceImpl implements IBizBaseVendorCodeService
{
    @Autowired
    private BizBaseVendorCodeMapper bizBaseVendorCodeMapper;

    /**
     * 查询biz_base_vendor_code from ECC
     * 
     * @param id biz_base_vendor_code from ECC主键
     * @return biz_base_vendor_code from ECC
     */
    @Override
    public BizBaseVendorCode selectBizBaseVendorCodeById(Long id)
    {
        return bizBaseVendorCodeMapper.selectBizBaseVendorCodeById(id);
    }

    /**
     * 查询biz_base_vendor_code from ECC列表
     * 
     * @param bizBaseVendorCode biz_base_vendor_code from ECC
     * @return biz_base_vendor_code from ECC
     */
    @Override
    public List<BizBaseVendorCode> selectBizBaseVendorCodeList(BizBaseVendorCode bizBaseVendorCode)
    {
        return bizBaseVendorCodeMapper.selectBizBaseVendorCodeList(bizBaseVendorCode);
    }

    /**
     * 新增biz_base_vendor_code from ECC
     * 
     * @param bizBaseVendorCode biz_base_vendor_code from ECC
     * @return 结果
     */
    @Override
    public int insertBizBaseVendorCode(BizBaseVendorCode bizBaseVendorCode)
    {
        bizBaseVendorCode.setCreateTime(DateUtils.getNowDate());
        return bizBaseVendorCodeMapper.insertBizBaseVendorCode(bizBaseVendorCode);
    }

    /**
     * 修改biz_base_vendor_code from ECC
     * 
     * @param bizBaseVendorCode biz_base_vendor_code from ECC
     * @return 结果
     */
    @Override
    public int updateBizBaseVendorCode(BizBaseVendorCode bizBaseVendorCode)
    {
        bizBaseVendorCode.setUpdateTime(DateUtils.getNowDate());
        return bizBaseVendorCodeMapper.updateBizBaseVendorCode(bizBaseVendorCode);
    }

    /**
     * 批量删除biz_base_vendor_code from ECC
     * 
     * @param ids 需要删除的biz_base_vendor_code from ECC主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseVendorCodeByIds(Long[] ids)
    {
        return bizBaseVendorCodeMapper.deleteBizBaseVendorCodeByIds(ids);
    }

    /**
     * 删除biz_base_vendor_code from ECC信息
     * 
     * @param id biz_base_vendor_code from ECC主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseVendorCodeById(Long id)
    {
        return bizBaseVendorCodeMapper.deleteBizBaseVendorCodeById(id);
    }
}
