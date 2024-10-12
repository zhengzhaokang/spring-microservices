package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseSelfInvoice;

import java.util.List;

/**
 * BizBaseSelfInvoiceMapper接口
 *
 * @author xiaozy8
 * @date 2022-09-28
 */
public interface BizBaseSelfInvoiceMapper
{
    /**
     * 查询BizBaseSelfInvoice
     * 
     * @param id BizBaseSelfInvoice主键
     * @return BizBaseSelfInvoice
     */
    public BizBaseSelfInvoice selectBizBaseSelfInvoiceById(Long id);

    /**
     * 查询BizBaseSelfInvoice列表
     * 
     * @param bizBaseSelfInvoice BizBaseSelfInvoice
     * @return BizBaseSelfInvoice集合
     */
    @DataPermissions(tableName = "biz_base_self_invoice")
    public List<BizBaseSelfInvoice> selectBizBaseSelfInvoiceList(BizBaseSelfInvoice bizBaseSelfInvoice);


    /**
     * 新增BizBaseSelfInvoice
     * 
     * @param bizBaseSelfInvoice BizBaseSelfInvoice
     * @return 结果
     */
    public int insertBizBaseSelfInvoice(BizBaseSelfInvoice bizBaseSelfInvoice);

    /**
     * 修改BizBaseSelfInvoice
     * 
     * @param bizBaseSelfInvoice BizBaseSelfInvoice
     * @return 结果
     */
    public int updateBizBaseSelfInvoice(BizBaseSelfInvoice bizBaseSelfInvoice);

    /**
     * 删除BizBaseSelfInvoice
     * 
     * @param id BizBaseSelfInvoice主键
     * @return 结果
     */
    public int deleteBizBaseSelfInvoiceById(Long id);

    /**
     * 批量删除BizBaseSelfInvoice
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseSelfInvoiceByIds(Long[] ids);

    public BizBaseSelfInvoice selectBizBaseSelfInvoiceByPartnerSignedSbas(String partnerSignedSba);

    public int getCountByPartnerSignedSbas(BizBaseSelfInvoice[] bizs);
}
