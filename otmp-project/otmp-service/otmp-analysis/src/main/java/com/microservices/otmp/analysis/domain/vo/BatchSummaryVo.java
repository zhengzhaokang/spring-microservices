package com.microservices.otmp.analysis.domain.vo;

import lombok.Data;

@Data
public class BatchSummaryVo {

    private String batchNumber;
    private String supplierName;
    private String entityName;
    private String currency;
    private Integer invoiceCount;
    private String invoiceAmount;
    private Integer creditCount;
    private String creditAmount;
    private String netFinancedAmount;
    private String discountDate;
    private String adjustedDueDate;
    private String noOfTenorDays;
    private String confirmedDiscountRate;
    private String confirmedDiscountCharge;

}
