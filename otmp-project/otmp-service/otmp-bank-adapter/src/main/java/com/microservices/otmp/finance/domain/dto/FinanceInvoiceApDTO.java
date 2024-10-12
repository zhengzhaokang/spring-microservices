package com.microservices.otmp.finance.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * Finance Invoice Ap对象 finance_invoice_ap
 *
 * @author lovefamily
 * @date 2023-10-12
 */
@Data
public class FinanceInvoiceApDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * Company Code
     */
    @Excel(name = "Company Code")
    private String companyCode;

    /**
     * 数据状态，1：可用，2：删除
     */
    @Excel(name = "数据状态，1：可用，2：删除")
    private String deleteFlag;

    /**
     * Invoice Unique Id
     */
    @Excel(name = "Invoice Unique Id")
    private String invoiceUniqueId;

    /**
     * Vendor Code
     */
    @Excel(name = "Vendor Code")
    private String vendorCode;

    /**
     * Fiscal Year
     */
    @Excel(name = "Fiscal Year")
    private String fiscalYear;

    /**
     * Invoice Additional Reference
     */
    @Excel(name = "Invoice Additional Reference")
    private String invoiceAdditionalReference;

    /**
     * Ecc Invoice Number
     */
    @Excel(name = "Ecc Invoice Number")
    private BigDecimal eccInvoiceNumber;

    /**
     * Invoice Issue Date
     */
    @Excel(name = "Invoice Issue Date")
    private String invoiceIssueDate;

    /**
     * Invoice Baseline Date
     */
    @Excel(name = "Invoice Baseline Date")
    private String invoiceBaselineDate;

    /**
     * Invoice Due Date
     */
    @Excel(name = "Invoice Due Date")
    private String invoiceDueDate;

    /**
     * Invoice Currency
     */
    @Excel(name = "Invoice Currency")
    private String invoiceCurrency;

    /**
     * Invoice Amount
     */
    @Excel(name = "Invoice Amount")
    private BigDecimal invoiceAmount;

    /**
     * Type Of Billing
     */
    @Excel(name = "Type Of Billing")
    private String typeOfBilling;

    /**
     * Enter Date
     */
    @Excel(name = "Enter Date")
    private String enterDate;

    /**
     * Invoice Pay Date
     */
    @Excel(name = "Invoice Pay Date")
    private String invoicePayDate;

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
    @Excel(name = "Maturity Date")
    private String maturityDate;

    /**
     * Status
     */
    @Excel(name = "Status")
    private String status;


    /**
     * Status Update Date
     */
    @Excel(name = "Status Update Date", width = 25, dateFormat = "MM/dd/yyyy")
    private Date statusUpdateDate;
    /**
     * Interest Rate
     */
    @Excel(name = "Interest Rate")
    private BigDecimal interestRate;

    private String statusDescription;



}
