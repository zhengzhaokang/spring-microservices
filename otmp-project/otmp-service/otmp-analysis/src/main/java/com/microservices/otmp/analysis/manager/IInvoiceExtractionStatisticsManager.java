package com.microservices.otmp.analysis.manager;

import java.util.List;

import com.microservices.otmp.analysis.domain.dto.InvoiceExtractionQueryVO;
import com.microservices.otmp.analysis.domain.entity.InvoiceExtractionStatisticsDO;
import com.microservices.otmp.analysis.domain.dto.InvoiceExtractionStatisticsDTO;


/**
 * Invoice extraction statisticsManager接口
 * 
 * @author lovefamily
 * @date 2023-10-27
 */
public interface IInvoiceExtractionStatisticsManager
{
    /**
     * 查询Invoice extraction statistics
     * 
     * @param id Invoice extraction statistics主键
     * @return Invoice extraction statistics
     */
    public InvoiceExtractionStatisticsDO selectInvoiceExtractionStatisticsById(Long id);

    /**
     * 查询Invoice extraction statistics列表
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return Invoice extraction statistics集合
     */
    public List<InvoiceExtractionStatisticsDO> selectInvoiceExtractionStatisticsList(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics);

    public List<InvoiceExtractionStatisticsDO> findInvoiceExtractionStatisticsList(InvoiceExtractionQueryVO invoiceExtractionQueryVO);


    /**
     * 新增Invoice extraction statistics
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return 结果
     */
    public int insertInvoiceExtractionStatistics(InvoiceExtractionStatisticsDO invoiceExtractionStatistics);
    public int insertInvoiceExtractionStatisticsOfJob(InvoiceExtractionStatisticsDO invoiceExtractionStatistics);

    /**
     * 修改Invoice extraction statistics
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return 结果
     */
    public int updateInvoiceExtractionStatistics(InvoiceExtractionStatisticsDO invoiceExtractionStatistics);

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
