package com.microservices.otmp.finance.service;

import java.util.List;
import com.microservices.otmp.finance.domain.dto.FinanceBatchDTO;

/**
 * Finance Batch InfoService接口
 * 
 * @author lovefamily
 * @date 2023-10-12
 */
public interface IFinanceBatchService
{
    /**
     * 查询Finance Batch Info
     * 
     * @param id Finance Batch Info主键
     * @return Finance Batch InfoDTO
     */
    public FinanceBatchDTO selectFinanceBatchById(Long id);

    /**
     * 查询Finance Batch Info列表
     *
     * @param financeBatch Finance Batch Info
     * @return Finance Batch InfoDTO集合
     */
    public List<FinanceBatchDTO> selectFinanceBatchList(FinanceBatchDTO financeBatch);

    /**
     * 新增Finance Batch Info
     * 
     * @param financeBatch Finance Batch Info
     * @return 结果
     */
    public int insertFinanceBatch(FinanceBatchDTO financeBatch);

    /**
     * 修改Finance Batch Info
     * 
     * @param financeBatch Finance Batch Info
     * @return 结果
     */
    public int updateFinanceBatch(FinanceBatchDTO financeBatch);

    public int updateFinanceBatchByBatchNumber(FinanceBatchDTO financeBatch);

    public int updateBatchInvoiceAndOtherInfo(FinanceBatchDTO financeBatch);

    /**
     * 批量删除Finance Batch Info
     * 
     * @param ids 需要删除的Finance Batch Info主键集合
     * @return 结果
     */
    public int deleteFinanceBatchByIds(Long[] ids);

    /**
     * 删除Finance Batch Info信息
     * 
     * @param id Finance Batch Info主键
     * @return 结果
     */
    public int deleteFinanceBatchById(Long id);
}
