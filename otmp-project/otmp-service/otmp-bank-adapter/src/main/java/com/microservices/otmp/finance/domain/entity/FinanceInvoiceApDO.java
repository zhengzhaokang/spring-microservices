package com.microservices.otmp.finance.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Finance Invoice Ap对象 finance_invoice_ap
 *
 * @author lovefamily
 * @date 2023-10-12
 */
@Data
public class FinanceInvoiceApDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * Company Code
     */

    private String companyCode;

    /**
     * 数据状态，1：可用，2：删除
     */

    private Boolean deleteFlag;

    /**
     * Invoice Unique Id
     */

    private String invoiceUniqueId;

    /**
     * Vendor Code
     */

    private String vendorCode;

    /**
     * Fiscal Year
     */

    private String fiscalYear;

    /**
     * Invoice Additional Reference
     */

    private String invoiceAdditionalReference;

    /**
     * Ecc Invoice Number
     */

    private BigDecimal eccInvoiceNumber;

    /**
     * Invoice Issue Date
     */

    private String invoiceIssueDate;

    /**
     * Invoice Baseline Date
     */

    private String invoiceBaselineDate;

    /**
     * Invoice Due Date
     */

    private String invoiceDueDate;

    /**
     * Invoice Currency
     */

    private String invoiceCurrency;

    /**
     * Invoice Amount
     */

    private BigDecimal invoiceAmount;

    /**
     * Type Of Billing
     */

    private String typeOfBilling;

    /**
     * Enter Date
     */

    private String enterDate;

    /**
     * Invoice Pay Date
     */

    private String invoicePayDate;

    /**
     * Invoice Type
     */

    private String invoiceType;

    /**
     * Invoice Reference
     */

    private String invoiceReference;

    /**
     * Maturity Date
     */

    private String maturityDate;

    private String confirmedMaturityDate;

    /**
     * Status
     */
    private String status;
    /**
     * Status Update Date
     */
    private Date statusUpdateDate;
    /**
     * Interest rate
     */
    private BigDecimal interestRate;
    private String statusDescription;


}
