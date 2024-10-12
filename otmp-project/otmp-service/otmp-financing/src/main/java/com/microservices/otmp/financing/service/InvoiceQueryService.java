package com.microservices.otmp.financing.service;

import com.microservices.otmp.financing.domain.dto.EntityDto;
import com.microservices.otmp.financing.domain.dto.FinancedExportDataDto;
import com.microservices.otmp.financing.domain.param.invoice.FinancedInvoiceExportParam;
import com.microservices.otmp.financing.domain.param.invoice.InvoiceQueryParam;
import com.microservices.otmp.financing.domain.vo.finance.FinancingRateDashboard;
import com.microservices.otmp.financing.domain.vo.finance.FinancingRateVO;
import com.microservices.otmp.financing.domain.vo.invoice.FinancedInvoiceBatchVo;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceBatchSummaryVo;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceQueryVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface InvoiceQueryService {

    Integer submittedCount(InvoiceQueryParam param);
    InvoiceQueryVO submittedList(InvoiceQueryParam param);

    Integer rejectedCount(InvoiceQueryParam param);
    InvoiceQueryVO rejectedList(InvoiceQueryParam param);

    Integer financedCount(InvoiceQueryParam param);
    InvoiceQueryVO financedList(InvoiceQueryParam param);

    List<EntityDto> getEntityInfo();

    FinancingRateDashboard getFinancingRate(InvoiceQueryParam param);

    /**
     * 查询完成融资的发票批次详情
     */
    InvoiceQueryVO financedDetailList(InvoiceQueryParam param);

    InvoiceBatchSummaryVo financedBatchSummary(FinancedInvoiceExportParam param);

    FinancedExportDataDto financedDetailExport(FinancedInvoiceExportParam param, HttpServletResponse response);
}
