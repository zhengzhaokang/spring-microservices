package com.microservices.otmp.financing.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FinancedExportDataDto {

    private List<InvoiceQueryDaoDto> debits;
    private List<InvoiceQueryDaoDto> credits;
    private Map<String, List<InvoiceQueryDaoDto>> debitBatchGroup;
    private Map<String, List<InvoiceQueryDaoDto>> creditBatchGroup;
    private List<FinancedSummaryExportDto> summaryExportList;
    private List<FinancedExportDto> debitExportList;
    private List<FinancedExportDto> creditExportList;

}
