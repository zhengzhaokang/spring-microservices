package com.microservices.otmp.financing.domain.vo.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceQueryDto {
    private String id;
    private BigDecimal amount;
    private String companyCode;
    private String currency;
    private String bankId;
    private String bankName;
    private String entityId;
    private String entityName;
    private String invoiceNumber;
    private String invoiceId;
    private String issueDate;
    private String dueDate;
    private Date maturityDate;
    private String batchNumber;
    private BigDecimal discountAmount;
    private Date discountDate;
    private Date submissionDate;
    private String statusDescription;
    private BigDecimal rate;
    private BigDecimal discount;
    private Date confirmedMaturityDate;

    private String maturityDateF;
    private String submissionDateF;
    private String issueDateF;
    private String dueDateF;
    private String discountDateF;
    private String confirmedMaturityDateF;
}
