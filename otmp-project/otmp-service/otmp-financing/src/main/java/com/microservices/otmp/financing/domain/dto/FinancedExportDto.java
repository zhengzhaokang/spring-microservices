package com.microservices.otmp.financing.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceQueryDto;
import com.microservices.otmp.financing.util.TimeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class FinancedExportDto extends ExportDto {

    @Excel(name = "Batch Number")
    private String batchNumber;
    @Excel(name = "Submitted Date")
    private String submittedDate;
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
    private String issueDate;
    @Excel(name = "Due Date")
    private String dueDate;
    @Excel(name = "Maturity Date")
    private String maturityDate;
    @Excel(name = "Discount Date")
    private String discountDateF;
    @Excel(name = "Discount Amount", align = Excel.Align.RIGHT)
    private BigDecimal discountAmount;
    @Excel(name = "Currency")
    private String currency;

    public FinancedExportDto() {
    }

    public FinancedExportDto(InvoiceQueryDto dto) {
        this.batchNumber = dto.getBatchNumber();
        this.submittedDate = TimeUtil.convert(dto.getSubmissionDateF());
        this.bankName = dto.getBankName();
        this.entityName = dto.getEntityName();
        this.companyCode = dto.getCompanyCode();
        this.invoiceNumber = dto.getInvoiceNumber();
        this.issueDate = TimeUtil.convert(dto.getIssueDateF());
        this.dueDate = TimeUtil.convert(dto.getDueDateF());
        this.maturityDate = TimeUtil.convert(dto.getMaturityDateF());
        this.discountDateF = TimeUtil.convert(dto.getDiscountDateF());
        this.discountAmount = dto.getDiscountAmount();
        this.currency = dto.getCurrency();
    }

    public FinancedExportDto(InvoiceQueryDaoDto dto){
        this.batchNumber = dto.getBatchNumber();
        this.bankName = dto.getBankName();
        this.entityName = dto.getEntityName();
        this.companyCode = dto.getCompanyCode();
        this.invoiceNumber = dto.getInvoiceNumber();
        this.discountAmount = dto.getDiscountAmount();
        this.currency = dto.getCurrency();
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public FinancedExportDto setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
        return this;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public FinancedExportDto setSubmittedDate(String submittedDate) {
        this.submittedDate = TimeUtil.convert(submittedDate);
        return this;
    }

    public String getBankName() {
        return bankName;
    }

    public FinancedExportDto setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public FinancedExportDto setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public FinancedExportDto setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public FinancedExportDto setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public FinancedExportDto setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
        return this;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public FinancedExportDto setIssueDate(String issueDate) {
        this.issueDate = TimeUtil.convert(issueDate);
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public FinancedExportDto setDueDate(String dueDate) {
        this.dueDate = TimeUtil.convert(dueDate);
        return this;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public FinancedExportDto setMaturityDate(String maturityDate) {
        this.maturityDate = TimeUtil.convert(maturityDate);
        return this;
    }

    public String getDiscountDateF() {
        return discountDateF;
    }

    public FinancedExportDto setDiscountDateF(String discountDateF) {
        this.discountDateF = TimeUtil.convert(discountDateF);
        return this;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public FinancedExportDto setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public FinancedExportDto setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}
