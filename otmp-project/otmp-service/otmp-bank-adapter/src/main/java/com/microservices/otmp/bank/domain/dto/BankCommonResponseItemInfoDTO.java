package com.microservices.otmp.bank.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Bank Common Response Item Info对象 bank_common_response_item_info
 *
 * @author lovefamily
 * @date 2023-10-10
 */
@Data
public class BankCommonResponseItemInfoDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * Header Id
     */
    @Excel(name = "Header Id")
    private Long headerId;

    /**
     * Bank Message ID
     */
    @Excel(name = "Bank Message ID ")
    private String msgId;

    /**
     * Response Type
     */
    @Excel(name = "Response Type")
    private String responseType;

    /**
     * Finance Request Reference No
     */
    @Excel(name = " Finance Request Reference No")
    private String financeReqNo;

    /**
     * Bank Reference No
     */
    @Excel(name = "Bank Reference No")
    private String txnDealRefNo;

    /**
     * Instrument No
     */
    @Excel(name = "Instrument No")
    private String txnRefNo;

    /**
     * Transaction Status
     */
    @Excel(name = "Transaction Status")
    private String txnStatus;

    /**
     * Txn Reject Code
     */
    @Excel(name = "Txn Reject Code ")
    private String txnRejectCode;

    /**
     * Txn Status Description
     */
    @Excel(name = "Txn Status Description ")
    private String txnStatusDescription;

    /**
     * Partner Code
     */
    @Excel(name = "Partner Code")
    private String partnerCode;

    /**
     * Anchor Organization ID
     */
    @Excel(name = "Anchor Organization ID")
    private String anchorOrgId;

    /**
     * Anchor Organization Name
     */
    @Excel(name = "Anchor Organization Name")
    private String anchorName;

    /**
     * Spoke Organization ID
     */
    @Excel(name = "Spoke Organization ID")
    private String spokeOrgId;

    /**
     * Spoke Organization Name
     */
    @Excel(name = "Spoke Organization Name")
    private String spokeName;

    /**
     * Spoke Organization ERP ID
     */
    @Excel(name = "Spoke Organization ERP ID")
    private String spokeErpId;

    /**
     * Loan start date
     */
    @JsonFormat(pattern = "MM/dd/yyyy")
    @Excel(name = "Loan start date", width = 30, dateFormat = "MM/dd/yyyy")
    private Date financeDate;

    /**
     * Transaction Date
     */
    @JsonFormat(pattern = "MM/dd/yyyy")
    @Excel(name = "Transaction Date", width = 30, dateFormat = "MM/dd/yyyy")
    private Date txnDate;

    /**
     * Status update date
     */
    @JsonFormat(pattern = "MM/dd/yyyy")
    @Excel(name = "Status update date	", width = 30, dateFormat = "MM/dd/yyyy")
    private Date updateDate;

    /**
     * Loan due date
     */
    @JsonFormat(pattern = "MM/dd/yyyy")
    @Excel(name = "Loan due date", width = 30, dateFormat = "MM/dd/yyyy")
    private Date dueDate;

    /**
     * Finance Fee
     */
    @Excel(name = "Finance Fee")
    private String financeFee;

    /**
     * Loan Account
     */
    @Excel(name = "Loan Account")
    private String loanAccount;

    /**
     * Tenor
     */
    @Excel(name = "Tenor")
    private String tenor;

    /**
     * Finance Amount
     */
    @Excel(name = "Finance Amount")
    private String financeAmount;
    private BigDecimal financeAmountNumber;

    /**
     * Rate Type
     */
    @Excel(name = "Rate Type")
    private String rateType;

    /**
     * Currency
     */
    @Excel(name = "Currency")
    private String currency;

    /**
     * Payment Amount
     */
    @Excel(name = "Payment Amount")
    private String paymentAmt;

    /**
     * Repayment Amount
     */
    @Excel(name = "Repayment Amount")
    private String repaymentAmt;

    /**
     * Interest rate
     */
    @Excel(name = "Interest rate")
    private String loanorinterestRate;

    /**
     * Interest amount
     */
    @Excel(name = "Interest amount")
    private String interestAmt;
    private BigDecimal interestAmtNumber;

    /**
     * Overdue Interest
     */
    @Excel(name = "Overdue Interest")
    private String overdueInterest;

    /**
     * External Ref No
     */
    @Excel(name = "External Ref No")
    private String externalRefNo;

    /**
     * Instrument Type
     */
    @Excel(name = "Instrument Type")
    private String instrumentType;

    /**
     * Original Instrument Amount
     */
    @Excel(name = "Original Instrument Amount	")
    private String originalInstrumentAmt;

    /**
     * Original Finance Amount
     */
    @Excel(name = "Original Finance Amount")
    private String originalFinanceAmt;

}
