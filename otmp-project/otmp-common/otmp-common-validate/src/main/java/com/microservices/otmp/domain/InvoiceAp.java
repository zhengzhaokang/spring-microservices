package com.microservices.otmp.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceAp {
    private String id;
    private String companyCode;
    private String  invoiceUniqueId;
    private String  vendorCode;
    private String fiscalYear;
    private String invoiceAdditionalReference;
    private String eccInvoiceNumber;
    private String invoiceIssueDate;
    private String invoiceBaselineDate;
    private String invoiceDueDate;
    private String invoiceCurrency;
    private BigDecimal invoiceAmount;
    private String typeOfBilling;
    private String enterDate;
    private String invoicePayDate;
    private String invoiceType;
    private String invoiceReference;
    private Date maturityDate;
    private int days;
    List<String> invoiceReferences;

    private String entityCurrency;
    private String supplierCurrency;
    private Date submitDate;
    private Integer bankActualPayDate;
    private Double maxFinancingTenor;
    private List<BankLimit> limits;
}
