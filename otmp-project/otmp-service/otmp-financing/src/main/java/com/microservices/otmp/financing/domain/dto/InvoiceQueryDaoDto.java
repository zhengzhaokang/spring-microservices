package com.microservices.otmp.financing.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceQueryDaoDto extends ExportDto {
    private String id;
    private BigDecimal amount;
    private String companyCode;
    private String currency;
    private String bankId;
    private String bankName;
    private String entityId;
    private String entityName;
    private String invoiceNumber;
    private String issueDate;
    private String dueDate;
    private Date maturityDate;
    private String batchId;
    private String batchNumber;
    private BigDecimal discountAmount;
    private Date discountDate;
    private Date submissionDate;
    private String statusDescription;
    private Long invoiceId;
    private BigDecimal rate;
    private BigDecimal discount;
    private Date confirmedMaturityDate;
}
