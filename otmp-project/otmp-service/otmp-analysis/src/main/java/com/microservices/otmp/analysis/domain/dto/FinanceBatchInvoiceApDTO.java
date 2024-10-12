package com.microservices.otmp.analysis.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import com.microservices.otmp.common.utils.DateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Finance Batch Invoice Ap
 *
 * @author lovefamily
 * @date 2023-11-12
 */
public class FinanceBatchInvoiceApDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * Batch Number
     */
    @Excel(name = "Batch Number", width = 20)
    private String batchNumber;

    /**
     * Status
     */
    @Excel(name = "Status", width = 10)
    private String status;

    /**
     * Company Code
     */
    @Excel(name = "Company Code", width = 15)
    private String companyCode;

    /**
     * 数据状态，1：可用，2：删除
     */
    //@Excel(name = "数据状态，1：可用，2：删除")
    private String deleteFlag;

    /**
     * Invoice Unique Id
     */
    @Excel(name = "Invoice Unique Id", width = 35)
    private String invoiceUniqueId;

    /**
     * Vendor Code
     */
    @Excel(name = "Vendor Code", width = 15)
    private String vendorCode;

    /**
     * Fiscal Year
     */
    @Excel(name = "Fiscal Year", width = 10)
    private String fiscalYear;

    /**
     * Invoice Additional Reference
     */
    @Excel(name = "Invoice Additional Reference")
    private String invoiceAdditionalReference;

    /**
     * Ecc Invoice Number
     */
    // @Excel(name = "Ecc Invoice Number")
    private BigDecimal eccInvoiceNumber;

    /**
     * Invoice Issue Date
     */

    private String invoiceIssueDate;

    @Excel(name = "Invoice Issue Date", dateFormat = "yyyy-MM-dd")
    private Date newInvoiceIssueDate;

    /**
     * Invoice Baseline Date
     */
    private String invoiceBaselineDate;

    @Excel(name = "Invoice Baseline Date", dateFormat = "yyyy-MM-dd")
    private Date newInvoiceBaselineDate;

    /**
     * Invoice Due Date
     */
    private String invoiceDueDate;
    @Excel(name = "Invoice Due Date", dateFormat = "yyyy-MM-dd")
    private Date newInvoiceDueDate;

    /**
     * Invoice Currency
     */
    @Excel(name = "Invoice Currency")
    private String invoiceCurrency;

    /**
     * Invoice Amount
     */
    @Excel(name = "Invoice Amount", align = Excel.Align.RIGHT)
    private BigDecimal invoiceAmount;

    /**
     * Type Of Billing
     */
    //  @Excel(name = "Type Of Billing")
    private String typeOfBilling;

    /**
     * Enter Date
     */
    private String enterDate;
    @Excel(name = "Enter Date", dateFormat = "yyyy-MM-dd")
    private Date newEnterDate;

    /**
     * Invoice Pay Date
     */
    private String invoicePayDate;
    @Excel(name = "Invoice Pay Date", dateFormat = "yyyy-MM-dd")
    private Date newInvoicePayDate;

    /**
     * Invoice Type
     */
    @Excel(name = "Invoice Type")
    private String invoiceType;

    /**
     * Invoice Reference
     */
    @Excel(name = "Invoice Reference")
    private String invoiceReference;

    /**
     * Maturity Date
     */
    //@Excel(name = "Maturity Date" , width = 20)
    private String maturityDate;

    @Excel(name = "Maturity Date", width = 20)
    private String confirmedMaturityDate;


    /**
     * Status Update Date
     */
    //@Excel(name = "Status Update Date", width = 20, dateFormat = "yyyy-MM-dd")
    private Date statusUpdateDate;
    /**
     * Interest Rate
     */
    //@Excel(name = "Interest Rate" ,align = Excel.Align.RIGHT)
    private BigDecimal interestRate;

    private String statusDescription;

    private String batchNumberList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public FinanceBatchInvoiceApDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public FinanceBatchInvoiceApDTO setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public FinanceBatchInvoiceApDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public FinanceBatchInvoiceApDTO setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public FinanceBatchInvoiceApDTO setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    public String getInvoiceUniqueId() {
        return invoiceUniqueId;
    }

    public FinanceBatchInvoiceApDTO setInvoiceUniqueId(String invoiceUniqueId) {
        this.invoiceUniqueId = invoiceUniqueId;
        return this;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public FinanceBatchInvoiceApDTO setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public FinanceBatchInvoiceApDTO setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
        return this;
    }

    public String getInvoiceAdditionalReference() {
        return invoiceAdditionalReference;
    }

    public FinanceBatchInvoiceApDTO setInvoiceAdditionalReference(String invoiceAdditionalReference) {
        this.invoiceAdditionalReference = invoiceAdditionalReference;
        return this;
    }

    public BigDecimal getEccInvoiceNumber() {
        return eccInvoiceNumber;
    }

    public FinanceBatchInvoiceApDTO setEccInvoiceNumber(BigDecimal eccInvoiceNumber) {
        this.eccInvoiceNumber = eccInvoiceNumber;
        return this;
    }

    public String getInvoiceIssueDate() {
        return invoiceIssueDate;
    }

    public FinanceBatchInvoiceApDTO setInvoiceIssueDate(String invoiceIssueDate) {
        this.invoiceIssueDate = invoiceIssueDate;
        this.newInvoiceIssueDate = DateUtils.parseDate(invoiceIssueDate);
        return this;
    }

    public Date getNewInvoiceIssueDate() {
        return newInvoiceIssueDate;
    }

    public FinanceBatchInvoiceApDTO setNewInvoiceIssueDate(Date newInvoiceIssueDate) {
        this.newInvoiceIssueDate = newInvoiceIssueDate;
        return this;
    }

    public String getInvoiceBaselineDate() {
        return invoiceBaselineDate;
    }

    public FinanceBatchInvoiceApDTO setInvoiceBaselineDate(String invoiceBaselineDate) {
        this.invoiceBaselineDate = invoiceBaselineDate;
        this.newInvoiceBaselineDate = DateUtils.parseDate(invoiceBaselineDate);
        return this;
    }


    public String getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public FinanceBatchInvoiceApDTO setInvoiceDueDate(String invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
        this.newInvoiceDueDate = DateUtils.parseDate(invoiceDueDate);
        return this;
    }

    public String getInvoiceCurrency() {
        return invoiceCurrency;
    }

    public FinanceBatchInvoiceApDTO setInvoiceCurrency(String invoiceCurrency) {
        this.invoiceCurrency = invoiceCurrency;
        return this;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public FinanceBatchInvoiceApDTO setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
        return this;
    }

    public String getTypeOfBilling() {
        return typeOfBilling;
    }

    public FinanceBatchInvoiceApDTO setTypeOfBilling(String typeOfBilling) {
        this.typeOfBilling = typeOfBilling;
        return this;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public FinanceBatchInvoiceApDTO setEnterDate(String enterDate) {
        this.enterDate = enterDate;
        this.newEnterDate = DateUtils.parseDate(enterDate);
        return this;
    }

    public String getInvoicePayDate() {
        return invoicePayDate;
    }

    public FinanceBatchInvoiceApDTO setInvoicePayDate(String invoicePayDate) {
        this.invoicePayDate = invoicePayDate;
        this.newInvoicePayDate = DateUtils.parseDate(invoicePayDate);
        return this;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public FinanceBatchInvoiceApDTO setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
        return this;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public FinanceBatchInvoiceApDTO setInvoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
        return this;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public FinanceBatchInvoiceApDTO setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
        return this;
    }

    public String getConfirmedMaturityDate() {
        return confirmedMaturityDate;
    }

    public FinanceBatchInvoiceApDTO setConfirmedMaturityDate(String confirmedMaturityDate) {
        this.confirmedMaturityDate = confirmedMaturityDate;
        return this;
    }

    public Date getStatusUpdateDate() {
        return statusUpdateDate;
    }

    public FinanceBatchInvoiceApDTO setStatusUpdateDate(Date statusUpdateDate) {
        this.statusUpdateDate = statusUpdateDate;
        return this;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public FinanceBatchInvoiceApDTO setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public FinanceBatchInvoiceApDTO setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }

    public String getBatchNumberList() {
        return batchNumberList;
    }

    public FinanceBatchInvoiceApDTO setBatchNumberList(String batchNumberList) {
        this.batchNumberList = batchNumberList;
        return this;
    }

    public Date getNewInvoiceBaselineDate() {
        return newInvoiceBaselineDate;
    }

    public FinanceBatchInvoiceApDTO setNewInvoiceBaselineDate(Date newInvoiceBaselineDate) {
        this.newInvoiceBaselineDate = newInvoiceBaselineDate;
        return this;
    }

    public Date getNewInvoiceDueDate() {
        return newInvoiceDueDate;
    }

    public FinanceBatchInvoiceApDTO setNewInvoiceDueDate(Date newInvoiceDueDate) {
        this.newInvoiceDueDate = newInvoiceDueDate;
        return this;
    }

    public Date getNewEnterDate() {
        return newEnterDate;
    }

    public FinanceBatchInvoiceApDTO setNewEnterDate(Date newEnterDate) {
        this.newEnterDate = newEnterDate;
        return this;
    }

    public Date getNewInvoicePayDate() {
        return newInvoicePayDate;
    }

    public FinanceBatchInvoiceApDTO setNewInvoicePayDate(Date newInvoicePayDate) {
        this.newInvoicePayDate = newInvoicePayDate;
        return this;
    }
}
