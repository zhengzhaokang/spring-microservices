package com.microservices.otmp.financing.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceQueryDto;
import com.microservices.otmp.financing.util.TimeUtil;

import java.math.BigDecimal;

public class RejectExportDto {

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
    @Excel(name = "Rejection Reason Description")
    private String statusDescription;

    public RejectExportDto() {
    }

    public RejectExportDto(InvoiceQueryDto source) {
        this.entityName = source.getEntityName();
        this.companyCode = source.getCompanyCode();
        this.invoiceNumber = source.getInvoiceNumber();
        this.issueDateF = TimeUtil.convert(source.getIssueDateF());
        this.dueDateF = TimeUtil.convert(source.getDueDateF());
        this.maturityDateF = TimeUtil.convert(source.getMaturityDateF());
        this.amount = source.getAmount();
        this.currency = source.getCurrency();
        this.statusDescription = source.getStatusDescription();
    }

    public String getEntityName() {
        return entityName;
    }

    public RejectExportDto setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public RejectExportDto setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public RejectExportDto setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public RejectExportDto setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
        return this;
    }

    public String getIssueDateF() {
        return issueDateF;
    }

    public RejectExportDto setIssueDateF(String issueDateF) {
        this.issueDateF = TimeUtil.convert(issueDateF);
        return this;
    }

    public String getDueDateF() {
        return dueDateF;
    }

    public RejectExportDto setDueDateF(String dueDateF) {
        this.dueDateF = TimeUtil.convert(dueDateF);
        return this;
    }

    public String getMaturityDateF() {
        return maturityDateF;
    }

    public RejectExportDto setMaturityDateF(String maturityDateF) {
        this.maturityDateF = TimeUtil.convert(maturityDateF);
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public RejectExportDto setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public RejectExportDto setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public RejectExportDto setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }
}
