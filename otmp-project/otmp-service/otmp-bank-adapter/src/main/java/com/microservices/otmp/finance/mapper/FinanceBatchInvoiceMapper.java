package com.microservices.otmp.finance.mapper;

import java.util.List;

import com.microservices.otmp.finance.domain.entity.FinanceBatchInvoiceDO;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import org.apache.ibatis.annotations.Param;


/**
 * Finance Batch InvoiceMapper接口
 * 
 * @author lovefamily
 * @date 2023-10-12
 */
public interface FinanceBatchInvoiceMapper
{
    /**
     * 查询Finance Batch Invoice
     * 
     * @param id Finance Batch Invoice主键
     * @return Finance Batch Invoice
     */
    public FinanceBatchInvoiceDO selectFinanceBatchInvoiceById(Long id);

    /**
     * 查询Finance Batch Invoice列表
     * 
     * @param financeBatchInvoiceDTO Finance Batch Invoice
     * @return Finance Batch Invoice集合
     */
    public List<FinanceBatchInvoiceDO> selectFinanceBatchInvoiceList(FinanceBatchInvoiceDTO financeBatchInvoice);

    /**
     * 新增Finance Batch Invoice
     * 
     * @param financeBatchInvoiceDO Finance Batch Invoice
     * @return 结果
     */
    public int insertFinanceBatchInvoice(FinanceBatchInvoiceDO financeBatchInvoice);

    /**
     * 修改Finance Batch Invoice
     * 
     * @param financeBatchInvoiceDO Finance Batch Invoice
     * @return 结果
     */
    public int updateFinanceBatchInvoice (FinanceBatchInvoiceDO financeBatchInvoice);

    /**
     * 删除Finance Batch Invoice
     * 
     * @param id Finance Batch Invoice主键
     * @return 结果
     */
    public int deleteFinanceBatchInvoiceById(Long id);

    /**
     * 批量删除Finance Batch Invoice
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceBatchInvoiceByIds(Long[] ids);

    /**
     * 根据编号查询中间表信息
     *
     * @param batchNo 编号
     * @return List<FinanceBatchInvoiceDO>
     */
    List<FinanceBatchInvoiceDO> getFinanceBatchInvoice(@Param("batchNo") String batchNo);
}
