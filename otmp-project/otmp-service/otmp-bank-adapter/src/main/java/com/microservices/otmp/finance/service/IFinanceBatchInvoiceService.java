package com.microservices.otmp.finance.service;

import java.util.List;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;

/**
 * Finance Batch InvoiceService接口
 * 
 * @author lovefamily
 * @date 2023-10-12
 */
public interface IFinanceBatchInvoiceService
{
    /**
     * 查询Finance Batch Invoice
     * 
     * @param id Finance Batch Invoice主键
     * @return Finance Batch InvoiceDTO
     */
    public FinanceBatchInvoiceDTO selectFinanceBatchInvoiceById(Long id);

    /**
     * 查询Finance Batch Invoice列表
     *
     * @param financeBatchInvoice Finance Batch Invoice
     * @return Finance Batch InvoiceDTO集合
     */
    public List<FinanceBatchInvoiceDTO> selectFinanceBatchInvoiceList(FinanceBatchInvoiceDTO financeBatchInvoice);

    /**
     * 新增Finance Batch Invoice
     * 
     * @param financeBatchInvoice Finance Batch Invoice
     * @return 结果
     */
    public int insertFinanceBatchInvoice(FinanceBatchInvoiceDTO financeBatchInvoice);

    /**
     * 修改Finance Batch Invoice
     * 
     * @param financeBatchInvoice Finance Batch Invoice
     * @return 结果
     */
    public int updateFinanceBatchInvoice(FinanceBatchInvoiceDTO financeBatchInvoice);

    /**
     * 批量删除Finance Batch Invoice
     * 
     * @param ids 需要删除的Finance Batch Invoice主键集合
     * @return 结果
     */
    public int deleteFinanceBatchInvoiceByIds(Long[] ids);

    /**
     * 删除Finance Batch Invoice信息
     * 
     * @param id Finance Batch Invoice主键
     * @return 结果
     */
    public int deleteFinanceBatchInvoiceById(Long id);
}
