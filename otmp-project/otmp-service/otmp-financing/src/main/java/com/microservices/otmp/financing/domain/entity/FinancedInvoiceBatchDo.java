package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FinancedInvoiceBatchDo {

    private Long batchId;
    private String batchNumber;
    private String entityName;
    private Double discountAmount;
    private Date discountDate;
}
