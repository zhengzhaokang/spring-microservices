package com.microservices.otmp.masterdata.service;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.masterdata.domain.BizBaseSelfInvoice;
import com.microservices.otmp.masterdata.domain.BizBaseSelfInvoice;

import java.util.List;

/**
 * BizBaseSelfInvoiceService接口
 *
 * @author xiaozy8
 * @date 2022-09-28
 */
public interface IBizBaseSelfInvoiceService
{
    /**
     * 查询BizBaseSelfInvoice
     * 
     * @param id BizBaseSelfInvoice主键
     * @return BizBaseSelfInvoice
     */
    public BizBaseSelfInvoice selectBizBaseSelfInvoiceById(Long id);

    /**
     * 查询BizBaseSalesOrg列表
     * 
     * @param bizBaseSalesOrg BizBaseSelfInvoice
     * @return BizBaseSelfInvoice集合
     */
    public List<BizBaseSelfInvoice> selectBizBaseSelfInvoiceList(BizBaseSelfInvoice bizBaseSalesOrg);

    /**
     * 新增BizBaseSalesOrg
     * 
     * @param bizBaseSalesOrg BizBaseSelfInvoice
     * @return 结果
     */
    public int insertBizBaseSelfInvoice(BizBaseSelfInvoice bizBaseSalesOrg);

    /**
     * 修改BizBaseSelfInvoice
     * 
     * @param bizBaseSalesOrg BizBaseSelfInvoice
     * @return 结果
     */
    public int updateBizBaseSelfInvoice(BizBaseSelfInvoice bizBaseSalesOrg);

    /**
     * 批量删除BizBaseSelfInvoice
     * 
     * @param ids 需要删除的BizBaseSalesOrg主键集合
     * @return 结果
     */
    public int deleteBizBaseSelfInvoiceByIds(Long[] ids);

    /**
     * 删除BizBaseSelfInvoice信息
     * 
     * @param id BizBaseSelfInvoice主键
     * @return 结果
     */
    public int deleteBizBaseSelfInvoiceById(Long id);

}
