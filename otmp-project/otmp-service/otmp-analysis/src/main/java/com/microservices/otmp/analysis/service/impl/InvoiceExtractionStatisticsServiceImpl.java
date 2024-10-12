package com.microservices.otmp.analysis.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.microservices.otmp.analysis.domain.dto.InvoiceExtractionQueryVO;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.analysis.manager.IInvoiceExtractionStatisticsManager;
import com.microservices.otmp.analysis.domain.dto.InvoiceExtractionStatisticsDTO;
import com.microservices.otmp.analysis.domain.entity.InvoiceExtractionStatisticsDO;
import com.microservices.otmp.analysis.service.IInvoiceExtractionStatisticsService;

/**
 * Invoice extraction statisticsService业务层处理
 *
 * @author lovefamily
 * @date 2023-10-27
 */
@Service
public class InvoiceExtractionStatisticsServiceImpl implements IInvoiceExtractionStatisticsService {
    private static final Logger log = LoggerFactory.getLogger(InvoiceExtractionStatisticsServiceImpl.class);

    @Autowired
    private IInvoiceExtractionStatisticsManager invoiceExtractionStatisticsManager;

    /**
     * 查询Invoice extraction statistics
     *
     * @param id Invoice extraction statistics主键
     * @return Invoice extraction statisticsDTO
     */
    @Override
    public InvoiceExtractionStatisticsDTO selectInvoiceExtractionStatisticsById(Long id) {
        InvoiceExtractionStatisticsDO invoiceExtractionStatisticsDO = invoiceExtractionStatisticsManager.selectInvoiceExtractionStatisticsById(id);
        InvoiceExtractionStatisticsDTO invoiceExtractionStatisticsDTO = new InvoiceExtractionStatisticsDTO();
        BeanUtils.copyProperties(invoiceExtractionStatisticsDO, invoiceExtractionStatisticsDTO);
        return invoiceExtractionStatisticsDTO;
    }

    /**
     * 查询Invoice extraction statistics列表
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return Invoice extraction statisticsDTO
     */
    @Override
    public List<InvoiceExtractionStatisticsDTO> selectInvoiceExtractionStatisticsList(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics) {

        List<InvoiceExtractionStatisticsDO> invoiceExtractionStatisticsDOS =
                invoiceExtractionStatisticsManager.selectInvoiceExtractionStatisticsList(invoiceExtractionStatistics);
        List<InvoiceExtractionStatisticsDTO> invoiceExtractionStatisticsList = new ArrayList<>(invoiceExtractionStatisticsDOS.size());
        BeanUtils.copyListProperties(invoiceExtractionStatisticsDOS, invoiceExtractionStatisticsList, InvoiceExtractionStatisticsDTO.class);
        return invoiceExtractionStatisticsList;

    }

    @Override
    public List<InvoiceExtractionStatisticsDTO> findInvoiceExtractionStatisticsList(InvoiceExtractionQueryVO invoiceExtractionQueryVO) {

        List<InvoiceExtractionStatisticsDO> invoiceExtractionStatisticsDOS =
                invoiceExtractionStatisticsManager.findInvoiceExtractionStatisticsList(invoiceExtractionQueryVO);
        List<InvoiceExtractionStatisticsDTO> invoiceExtractionStatisticsList = new ArrayList<>(invoiceExtractionStatisticsDOS.size());
        BeanUtils.copyListProperties(invoiceExtractionStatisticsDOS, invoiceExtractionStatisticsList, InvoiceExtractionStatisticsDTO.class);
        return invoiceExtractionStatisticsList;

    }

    /**
     * 新增Invoice extraction statistics
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return 结果
     */
    @Override
    public int insertInvoiceExtractionStatistics(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics) {
        invoiceExtractionStatistics.setCreateTime(DateUtils.getNowDate());
        InvoiceExtractionStatisticsDO invoiceExtractionStatisticsDO = new InvoiceExtractionStatisticsDO();
        BeanUtils.copyProperties(invoiceExtractionStatistics, invoiceExtractionStatisticsDO);
        return invoiceExtractionStatisticsManager.insertInvoiceExtractionStatistics(invoiceExtractionStatisticsDO);
    }

    @Override
    public int insertInvoiceExtractionStatisticsOfJob(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics) {
        InvoiceExtractionStatisticsDO invoiceExtractionStatisticsDO = new InvoiceExtractionStatisticsDO();
        BeanUtils.copyProperties(invoiceExtractionStatistics, invoiceExtractionStatisticsDO);
        return invoiceExtractionStatisticsManager.insertInvoiceExtractionStatisticsOfJob(invoiceExtractionStatisticsDO);
    }

    /**
     * 修改Invoice extraction statistics
     *
     * @param invoiceExtractionStatistics Invoice extraction statistics
     * @return 结果
     */
    @Override
    public int updateInvoiceExtractionStatistics(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics) {
        invoiceExtractionStatistics.setUpdateTime(DateUtils.getNowDate());
        InvoiceExtractionStatisticsDO invoiceExtractionStatisticsDO = new InvoiceExtractionStatisticsDO();
        BeanUtils.copyProperties(invoiceExtractionStatistics, invoiceExtractionStatisticsDO);
        return invoiceExtractionStatisticsManager.updateInvoiceExtractionStatistics(invoiceExtractionStatisticsDO);
    }

    /**
     * 批量删除Invoice extraction statistics
     *
     * @param ids 需要删除的Invoice extraction statistics主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceExtractionStatisticsByIds(Long[] ids) {
        return invoiceExtractionStatisticsManager.deleteInvoiceExtractionStatisticsByIds(ids);
    }

    /**
     * 删除Invoice extraction statistics信息
     *
     * @param id Invoice extraction statistics主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceExtractionStatisticsById(Long id) {
        return invoiceExtractionStatisticsManager.deleteInvoiceExtractionStatisticsById(id);
    }
}
