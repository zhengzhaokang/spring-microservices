package com.microservices.otmp.analysis.service;

import java.util.List;

import com.microservices.otmp.analysis.domain.dto.InvoiceExtractionQueryVO;
import com.microservices.otmp.analysis.domain.dto.InvoiceExtractionStatisticsDTO;
import com.microservices.otmp.analysis.domain.entity.InvoiceExtractionStatisticsDO;

/**
 * Invoice extraction statisticsService接口
 *
 * @author lovefamily
 * @date 2023-10-27
 */
public interface IInvoiceExtractionStatisticsService {
    /**
     * 查询Invoice extraction statistics
     *
     * @param id Invoice extraction statistics主键
     * @return Invoice extraction statisticsDTO
     */
    public InvoiceExtractionStatisticsDTO selectInvoiceExtractionStatisticsById(Long id);

    /**
     * 查询Invoice extraction statistics列表
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return Invoice extraction statisticsDTO集合
     */
    public List<InvoiceExtractionStatisticsDTO> selectInvoiceExtractionStatisticsList(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics);

    public List<InvoiceExtractionStatisticsDTO> findInvoiceExtractionStatisticsList(InvoiceExtractionQueryVO invoiceExtractionQueryVO);

    /**
     * 新增Invoice extraction statistics
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return 结果
     */
    public int insertInvoiceExtractionStatistics(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics);

    public int insertInvoiceExtractionStatisticsOfJob(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics);

    /**
     * 修改Invoice extraction statistics
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return 结果
     */
    public int updateInvoiceExtractionStatistics(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics);

    /**
     * 批量删除Invoice extraction statistics
     *
     * @param ids 需要删除的Invoice extraction statistics主键集合
     * @return 结果
     */
    public int deleteInvoiceExtractionStatisticsByIds(Long[] ids);

    /**
     * 删除Invoice extraction statistics信息
     *
     * @param id Invoice extraction statistics主键
     * @return 结果
     */
    public int deleteInvoiceExtractionStatisticsById(Long id);
}
