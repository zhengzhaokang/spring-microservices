package com.microservices.otmp.analysis.manager.impl;

import java.util.List;

import com.microservices.otmp.analysis.domain.dto.InvoiceExtractionQueryVO;
import com.microservices.otmp.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.analysis.mapper.InvoiceExtractionStatisticsMapper;
import com.microservices.otmp.analysis.domain.entity.InvoiceExtractionStatisticsDO;
import com.microservices.otmp.analysis.domain.dto.InvoiceExtractionStatisticsDTO;
import com.microservices.otmp.analysis.manager.IInvoiceExtractionStatisticsManager;

/**
 * Invoice extraction statisticsManager业务层处理
 *
 * @author lovefamily
 * @date 2023-10-27
 */
@Service
public class InvoiceExtractionStatisticsManagerImpl implements IInvoiceExtractionStatisticsManager {
    private static final Logger log = LoggerFactory.getLogger(InvoiceExtractionStatisticsManagerImpl.class);

    @Autowired
    private InvoiceExtractionStatisticsMapper invoiceExtractionStatisticsMapper;

    /**
     * 查询Invoice extraction statistics
     *
     * @param id Invoice extraction statistics主键
     * @return Invoice extraction statisticsDO
     */
    @Override
    public InvoiceExtractionStatisticsDO selectInvoiceExtractionStatisticsById(Long id) {
        return invoiceExtractionStatisticsMapper.selectInvoiceExtractionStatisticsById(id);
    }

    /**
     * 查询Invoice extraction statistics列表
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return Invoice extraction statisticsDO
     */
    @Override
    public List<InvoiceExtractionStatisticsDO> selectInvoiceExtractionStatisticsList(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics) {
        return invoiceExtractionStatisticsMapper.selectInvoiceExtractionStatisticsList(invoiceExtractionStatistics);
    }

    @Override
    public List<InvoiceExtractionStatisticsDO> findInvoiceExtractionStatisticsList(InvoiceExtractionQueryVO invoiceExtractionQueryVO) {
        return invoiceExtractionStatisticsMapper.findInvoiceExtractionStatisticsList(invoiceExtractionQueryVO);
    }


    /**
     * 新增Invoice extraction statistics
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return 结果
     */
    @Override
    public int insertInvoiceExtractionStatistics(InvoiceExtractionStatisticsDO invoiceExtractionStatistics) {
        invoiceExtractionStatistics.setCreateTime(DateUtils.getNowDate());
        return invoiceExtractionStatisticsMapper.insertInvoiceExtractionStatistics(invoiceExtractionStatistics);
    }

    @Override
    public int insertInvoiceExtractionStatisticsOfJob(InvoiceExtractionStatisticsDO invoiceExtractionStatistics) {
        return invoiceExtractionStatisticsMapper.insertInvoiceExtractionStatisticsOfJob(invoiceExtractionStatistics);
    }

    /**
     * 修改Invoice extraction statistics
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return 结果
     */
    @Override
    public int updateInvoiceExtractionStatistics(InvoiceExtractionStatisticsDO invoiceExtractionStatistics) {
        invoiceExtractionStatistics.setUpdateTime(DateUtils.getNowDate());
        return invoiceExtractionStatisticsMapper.updateInvoiceExtractionStatistics(invoiceExtractionStatistics);
    }

    /**
     * 批量删除Invoice extraction statistics
     *
     * @param ids 需要删除的Invoice extraction statistics主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceExtractionStatisticsByIds(Long[] ids) {
        return invoiceExtractionStatisticsMapper.deleteInvoiceExtractionStatisticsByIds(ids);
    }

    /**
     * 删除Invoice extraction statistics信息
     *
     * @param id Invoice extraction statistics主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceExtractionStatisticsById(Long id) {
        return invoiceExtractionStatisticsMapper.deleteInvoiceExtractionStatisticsById(id);
    }
}
