package com.microservices.otmp.financing.domain.dto;

import com.microservices.otmp.financing.domain.entity.BaseInvoiceInfo;

import java.time.LocalDateTime;


public class InvoiceDTO {

    private Long id;
    /**
     * 公司代码
     */
    private String companyCode;
    private String invoiceUniqueId;
    private String vendorCode;
    private String fiscalYear;
    private String invoiceAdditionalReference;
    private String eccInvoiceNumber;
    private String invoiceIssueDate;
    private String invoiceBaselineDate;
    private String invoiceDueDate;

    private String invoiceCurrency;
    private Double invoiceAmount;
    private String typeOfBilling;
    private String enterDate;
    private String invoicePayDate;
    private LocalDateTime maturityDate;
    private String invoiceType;
    private String invoiceReference;
    private String status;

    private String bankId;
    private String bankName;
    private Long entityId;

    public InvoiceDTO() {
    }

    public InvoiceDTO(BaseInvoiceInfo info) {
        this.id = info.getId();
        this.companyCode = info.getCompanyCode();
        this.vendorCode = info.getVendorCode();
        this.invoiceUniqueId = info.getInvoiceUniqueId();
        this.fiscalYear = info.getFiscalYear();
        this.invoiceAdditionalReference = info.getInvoiceAdditionalReference();
        this.eccInvoiceNumber = info.getEccInvoiceNumber();
        this.invoiceIssueDate = info.getInvoiceIssueDate();
        this.invoiceBaselineDate = info.getInvoiceBaselineDate();
        this.invoiceDueDate = info.getInvoiceDueDate();

        this.invoiceCurrency = info.getInvoiceCurrency();
        this.invoiceAmount = info.getInvoiceAmount();
        this.typeOfBilling = info.getTypeOfBilling();
        this.enterDate = info.getEnterDate();
        this.invoicePayDate = info.getInvoicePayDate();
        this.maturityDate = info.getMaturityDate();
        this.invoiceType = info.getInvoiceType();
        this.invoiceReference = info.getInvoiceReference();
        this.status = info.getStatus();
    }

    public Long getId() {
        return id;
    }

    public InvoiceDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public InvoiceDTO setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public String getInvoiceUniqueId() {
        return invoiceUniqueId;
    }

    public InvoiceDTO setInvoiceUniqueId(String invoiceUniqueId) {
        this.invoiceUniqueId = invoiceUniqueId;
        return this;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public InvoiceDTO setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public InvoiceDTO setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
        return this;
    }

    public String getInvoiceAdditionalReference() {
        return invoiceAdditionalReference;
    }

    public InvoiceDTO setInvoiceAdditionalReference(String invoiceAdditionalReference) {
        this.invoiceAdditionalReference = invoiceAdditionalReference;
        return this;
    }

    public String getEccInvoiceNumber() {
        return eccInvoiceNumber;
    }

    public InvoiceDTO setEccInvoiceNumber(String eccInvoiceNumber) {
        this.eccInvoiceNumber = eccInvoiceNumber;
        return this;
    }

    public String getInvoiceIssueDate() {
        return invoiceIssueDate;
    }

    public InvoiceDTO setInvoiceIssueDate(String invoiceIssueDate) {
        this.invoiceIssueDate = invoiceIssueDate;
        return this;
    }

    public String getInvoiceBaselineDate() {
        return invoiceBaselineDate;
    }

    public InvoiceDTO setInvoiceBaselineDate(String invoiceBaselineDate) {
        this.invoiceBaselineDate = invoiceBaselineDate;
        return this;
    }

    public String getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public InvoiceDTO setInvoiceDueDate(String invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
        return this;
    }

    public String getInvoiceCurrency() {
        return invoiceCurrency;
    }

    public InvoiceDTO setInvoiceCurrency(String invoiceCurrency) {
        this.invoiceCurrency = invoiceCurrency;
        return this;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public InvoiceDTO setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
        return this;
    }

    public String getTypeOfBilling() {
        return typeOfBilling;
    }

    public InvoiceDTO setTypeOfBilling(String typeOfBilling) {
        this.typeOfBilling = typeOfBilling;
        return this;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public InvoiceDTO setEnterDate(String enterDate) {
        this.enterDate = enterDate;
        return this;
    }

    public String getInvoicePayDate() {
        return invoicePayDate;
    }

    public InvoiceDTO setInvoicePayDate(String invoicePayDate) {
        this.invoicePayDate = invoicePayDate;
        return this;
    }

    public LocalDateTime getMaturityDate() {
        return maturityDate;
    }

    public InvoiceDTO setMaturityDate(LocalDateTime maturityDate) {
        this.maturityDate = maturityDate;
        return this;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public InvoiceDTO setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
        return this;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public InvoiceDTO setInvoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public InvoiceDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getBankId() {
        return bankId;
    }

    public InvoiceDTO setBankId(String bankId) {
        this.bankId = bankId;
        return this;
    }

    public Long getEntityId() {
        return entityId;
    }

    public InvoiceDTO setEntityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getBankName() {
        return bankName;
    }

    public InvoiceDTO setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }
}
