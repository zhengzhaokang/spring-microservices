package com.microservices.otmp.analysis.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.analysis.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.analysis.domain.dto.FinanceBatchInvoiceApDTO;
import com.microservices.otmp.analysis.domain.entity.FinanceBatchInvoiceApDO;

import java.util.List;

/**
 * Finance Batch InfoService接口
 *
 * @author lovefamily
 * @date 2023-11-10
 */
public interface IFinanceBatchService {
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

    public PageInfo<FinanceBatchDTO> selectFinanceBatchPageList(FinanceBatchDTO financeBatch);

    /**
     * 查询Finance BatchInvoiceAp Info列表
     *
     * @param financeBatchInvoiceApDTO Finance Batch Info
     * @return Finance Batch InvoiceApDTO集合
     */
    public List<FinanceBatchInvoiceApDTO> findFinanceBatchInvoiceApList(FinanceBatchInvoiceApDTO financeBatchInvoiceApDTO);


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
