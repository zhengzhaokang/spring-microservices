package com.microservices.otmp.financing.domain.vo.invoice;

import com.microservices.otmp.financing.domain.dto.FinancedSummaryExportDto;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceBatchSummaryVo {

    private List<FinancedSummaryExportDto> summaryList;

}
