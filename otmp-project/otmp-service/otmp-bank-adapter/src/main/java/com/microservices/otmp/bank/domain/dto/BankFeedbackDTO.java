package com.microservices.otmp.bank.domain.dto;

import lombok.Data;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/17
 * time: 9:49
 * Description:
 */
@Data
public class BankFeedbackDTO {

    private String branch;
    private String buyerOrgID;
    private String buyerName;
    private String supplierOrgID;
    private String supplierName;
    private String supplierERPID;
    private String paymentReference;
    private String paymentAddRefCSCFO;
    private String paymentDiscReference;
    private String cSCFODiscBatchReference;
    private String cSCBODiscountReference;
    private String paymentDueDate;
    private String adjustedDueDate;
    private String paymentCurrency;
    private String paymentAmount;
    private String financingAmount;
    private String remainingAmount;
    private String microservicesBatchNumber;
    private String noTenorDays;
    private String confirmedBaseRate;
    private String spread;
    private String confirmedInterestRate;
    private String confirmedInterestAmount;
    private String interestChargeType;
    private String financeAmountMinusInterest;
    private String confirmedNetAmount;
    private String confirmed;
    private String chargeAmount;


}
