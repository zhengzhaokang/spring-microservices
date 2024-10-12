package com.microservices.otmp.financing.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceQueryDto;
import com.microservices.otmp.financing.util.TimeUtil;

import java.math.BigDecimal;

public class SubmitExportDto {

    @Excel(name = "Batch Number")
    private String batchNumber;

    @Excel(name = "Submitted Date")
    private String submissionDateF;

    @Excel(name = "Bank Name")
    private String bankName;

    @Excel(name = "Entity Name")
    private String entityName;

    @Excel(name = "Company Code")
    private String companyCode;

    @Excel(name = "Invoice Number")
    private String invoiceNumber;

    @Excel(name = "Invoice Type")
    private String invoiceType;

    @Excel(name = "Issue Date")
    private String issueDateF;

    @Excel(name = "Due Date")
    private String dueDateF;

    @Excel(name = "Maturity Date")
    private String maturityDateF;

    @Excel(name = "Amount", align = Excel.Align.RIGHT)
    private BigDecimal amount;

    @Excel(name = "Currency")
    private String currency;

    public SubmitExportDto() {
    }

    public SubmitExportDto(InvoiceQueryDto source) {
        this.amount = source.getAmount();
        this.companyCode = source.getCompanyCode();
        this.currency = source.getCurrency();
        this.bankName = source.getBankName();
        this.entityName = source.getEntityName();
        this.invoiceNumber = source.getInvoiceNumber();
        this.batchNumber = source.getBatchNumber();
        this.maturityDateF = TimeUtil.convert(source.getMaturityDateF());
        this.submissionDateF = TimeUtil.convert(source.getSubmissionDateF());
        this.issueDateF = TimeUtil.convert(source.getIssueDateF());
        this.dueDateF = TimeUtil.convert(source.getDueDateF());
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public SubmitExportDto setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
        return this;
    }

    public String getSubmissionDateF() {
        return submissionDateF;
    }

    public SubmitExportDto setSubmissionDateF(String submissionDateF) {
        this.submissionDateF = TimeUtil.convert(submissionDateF);
        return this;
    }

    public String getBankName() {
        return bankName;
    }

    public SubmitExportDto setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public SubmitExportDto setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public SubmitExportDto setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public SubmitExportDto setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public SubmitExportDto setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
        return this;
    }

    public String getIssueDateF() {
        return issueDateF;
    }

    public SubmitExportDto setIssueDateF(String issueDateF) {
        this.issueDateF = TimeUtil.convert(issueDateF);
        return this;
    }

    public String getDueDateF() {
        return dueDateF;
    }

    public SubmitExportDto setDueDateF(String dueDateF) {
        this.dueDateF = TimeUtil.convert(dueDateF);
        return this;
    }

    public String getMaturityDateF() {
        return maturityDateF;
    }

    public SubmitExportDto setMaturityDateF(String maturityDateF) {
        this.maturityDateF = TimeUtil.convert(maturityDateF);
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public SubmitExportDto setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public SubmitExportDto setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}
