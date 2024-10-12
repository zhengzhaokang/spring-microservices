package com.microservices.otmp.erp.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunpan1
 */
@Data
public class SupplierInvoiceInfo {

    private Long id;

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

    private String typeOfBilling;

    private String purchaseOrder;

    private String purchaseItem;

    private String purchaseDate;

    private String goodsDescription;

    private String shippingCompany;

    private String shipperName;

    private String transportDocumentReferenceNumber;

    private String transportDocumentDate;

    private String portOfLoading;

    private String portOfDischarge;

    private String vesselName;

    private String vesselMoNumber;

    private String enterDate;

    private String typeOfInvoice;

    private String invoiceEntryDate;

    private String invoicePayDate;

    private Date maturityDate;

    private String status;

    private String statusDescription;

    private Date crateTime;

    private Date updateTime;

}