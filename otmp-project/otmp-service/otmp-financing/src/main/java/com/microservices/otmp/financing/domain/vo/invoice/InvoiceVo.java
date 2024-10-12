package com.microservices.otmp.financing.domain.vo.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class InvoiceVo {

    private String id;
    private Double amount;
    private Double amountInUsd;
    private String companyCode;
    private String currency;
    private String entityName;
    private String invoiceNumber;
    private String issueDate;
    private String dueDate;
    private String maturityDate;
    private String vendorCode;

    private Integer maxFinanceTenor;
    private Integer financeTenorInterval;

    private String bankId;

    private boolean selectable;
    private int forbiddenReason;

    private String confirmedMaturityDate;
}
