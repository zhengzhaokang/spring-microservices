package com.microservices.otmp.erp.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author sunpan1
 */
@Data
public class SupplierInvoiceFeedbackInfoVo {
    private String id;
    private String invoiceUniqueId;

    private String companyCode;

    private String vendorCode;

    private String fiscalYear;

    private String invoiceReference;

    private String invoiceAdditionalReference;

    private BigDecimal eccInvoiceNumber;

    private String eccInvoiceItem;

    private String invoiceIssueDate;

    private String invoiceBaselineDate;

    private String invoiceDueDate;

    private String invoiceCurrency;

    private BigDecimal invoiceAmount;

    private String typeOfInvoice;

    private String purchaseOrder;

    private String purchaseItem;

    private String purchaseDate;

    private String goodsDescription;

    private String shippingCompany;

    private String shipperName;

    private String transportDocRefNumber;

    private String transportDocDate;

    private String portOfLoading;

    private String portOfDischarge;

    private String vesselName;

    private String vesselMoNumber;

    private String status;

    private BigDecimal financingAmount;

    private BigDecimal paymentAmount;

    private String discountDate;

    private String batchReference1;

    private String batchReference2;

    private String batchReference3;

    private String batchReference4;

    private String batchReference5;

    private String maturityDate;

    private String bankVendorCode;

    private String addReference1;

    private String addReference2;

    private String addReference3;

    private String addReference4;

    private String addReference5;

    private String addReference6;

    private String addReference7;

    private String addReference8;

    private String addReference9;

    private String addReference10;

    private String invoiceEntryDate;

    private Date receivingTime;

    private Date postingTime;

    private String statusDescription;

    private String accountingDocumentItem;
    private String typeOfBilling;
    private String documentNumber;
    private String message;
    private String postingStatus;
    private String eccReferenceNumber;
    private String financingPercentage;
    private String interestApplied;
    private String interestRate;
    private String interestAmount;
    private String enterDate;

}