package com.microservices.otmp.bank.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

import java.util.Date;

/**
 * Bank Common Response Item Info对象 bank_common_response_item_info
 *
 * @author lovefamily
 * @date 2023-10-10
 */
@Data
public class BankCommonResponseItemInfoDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * Header Id
     */

    private Long headerId;

    /**
     * Bank Message ID
     */

    private String msgId;

    /**
     * Response Type
     */

    private String responseType;

    /**
     * Finance Request Reference No
     */

    private String financeReqNo;

    /**
     * Bank Reference No
     */

    private String txnDealRefNo;

    /**
     * Instrument No
     */

    private String txnRefNo;

    /**
     * Transaction Status
     */

    private String txnStatus;

    /**
     * Txn Reject Code
     */

    private String txnRejectCode;

    /**
     * Txn Status Description
     */

    private String txnStatusDescription;

    /**
     * Partner Code
     */

    private String partnerCode;

    /**
     * Anchor Organization ID
     */

    private String anchorOrgId;

    /**
     * Anchor Organization Name
     */

    private String anchorName;

    /**
     * Spoke Organization ID
     */

    private String spokeOrgId;

    /**
     * Spoke Organization Name
     */

    private String spokeName;

    /**
     * Spoke Organization ERP ID
     */

    private String spokeErpId;

    /**
     * Loan start date
     */

    private Date financeDate;

    /**
     * Transaction Date
     */

    private Date txnDate;

    /**
     * Status update date
     */

    private Date updateDate;

    /**
     * Loan due date
     */

    private Date dueDate;

    /**
     * Finance Fee
     */

    private String financeFee;

    /**
     * Loan Account
     */

    private String loanAccount;

    /**
     * Tenor
     */

    private String tenor;

    /**
     * Finance Amount
     */

    private String financeAmount;

    /**
     * Rate Type
     */

    private String rateType;

    /**
     * Currency
     */

    private String currency;

    /**
     * Payment Amount
     */

    private String paymentAmt;

    /**
     * Repayment Amount
     */

    private String repaymentAmt;

    /**
     * Interest rate
     */

    private String loanorinterestRate;

    /**
     * Interest amount
     */

    private String interestAmt;

    /**
     * Overdue Interest
     */

    private String overdueInterest;

    /**
     * External Ref No
     */

    private String externalRefNo;

    /**
     * Instrument Type
     */

    private String instrumentType;

    /**
     * Original Instrument Amount
     */

    private String originalInstrumentAmt;

    /**
     * Original Finance Amount
     */

    private String originalFinanceAmt;


}
