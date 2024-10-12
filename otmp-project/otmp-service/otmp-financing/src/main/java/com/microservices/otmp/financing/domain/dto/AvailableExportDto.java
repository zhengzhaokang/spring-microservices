package com.microservices.otmp.financing.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceVo;
import com.microservices.otmp.financing.util.TimeUtil;

public class AvailableExportDto {

    @Excel(name = "Entity Name")
    private String entityName;
    @Excel(name = "Company Code")
    private String companyCode;
    @Excel(name = "Invoice Number")
    private String invoiceNumber;
    @Excel(name = "Invoice Type")
    private String invoiceType;
    @Excel(name = "Issue Date")
    private String issueDate;
    @Excel(name = "Due Date")
    private String dueDate;
    @Excel(name = "Maturity Date")
    private String maturityDate;
    @Excel(name = "Amount", align = Excel.Align.RIGHT)
    private Double amount;
    @Excel(name = "Currency")
    private String currency;

    public AvailableExportDto() {
    }

    public AvailableExportDto(InvoiceVo vo) {
        this.entityName = vo.getEntityName();
        this.companyCode = vo.getCompanyCode();
        this.invoiceNumber = vo.getInvoiceNumber();
        this.issueDate = TimeUtil.convert(vo.getIssueDate());
        this.dueDate = TimeUtil.convert(vo.getDueDate());
        this.maturityDate = vo.getMaturityDate();
        this.amount = vo.getAmount();
        this.currency = vo.getCurrency();
    }

    public String getEntityName() {
        return entityName;
    }

    public AvailableExportDto setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public AvailableExportDto setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public AvailableExportDto setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public AvailableExportDto setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
        return this;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public AvailableExportDto setIssueDate(String issueDate) {
        this.issueDate = TimeUtil.convert(issueDate);
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public AvailableExportDto setDueDate(String dueDate) {
        this.dueDate = TimeUtil.convert(dueDate);
        return this;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public AvailableExportDto setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public AvailableExportDto setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public AvailableExportDto setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}
