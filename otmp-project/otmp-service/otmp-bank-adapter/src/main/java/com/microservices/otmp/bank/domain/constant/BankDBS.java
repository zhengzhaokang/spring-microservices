package com.microservices.otmp.bank.domain.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/24
 * time: 14:00
 * Description:
 */
public class BankDBS {

    public static final String BANK_TYPE = "DBS";
    public static final String SF = "SF";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final Integer BIGDECIMAL_FORMAT = 2;
    public static final String ONE_HEAD = "No.";
    public static final String TWO_HEAD = "Seller";
    public static final String THREE_HEAD = "Buyer";
    public static final String FOUR_HEAD = "Instrument Type";
    public static final String FIVE_HEAD = "Instrument Number";
    public static final String SIX_HEAD = "Instrument Date";
    public static final String SEVEN_HEAD = "Instrument Currency";
    public static final String EIGHT_HEAD = "Instrument Amount";
    public static final String NINE_HEAD = "Instrument Due Date";
    public static final String TEN_HEAD = "Payment Due Date";
    public static final String ELEVEN_HEAD = "Corresponding PO Number";
    public static final String TWELVE_HEAD = "Transport Document Reference Number";
    public static final String THIRTEEN_HEAD = "Transport Document Date";
    public static final String FOURTEEN_HEAD = "Port of Loading";
    public static final String FIFTEEN_HEAD = "Port of discharge";
    public static final String SIXTEEN_HEAD = "Goods";
    public static final String SEVENTEEN_HEAD = "Name of Shipper";
    public static final String EIGHTEEN_HEAD = "Name of Shipping Company";
    public static final String NINETEEN_HEAD = "Name of Vessel";
    public static final String TWENTY_HEAD = "Programs ID";
    public static final String TWENTY_ONE_HEAD = "Finance Amount";
    public static final String TWENTY_TWO_HEAD = "Seller Fees";
    public static final String TWENTY_THREE_HEAD = "Buyer Name";
    public static final String TWENTY_FOUR_HEAD = "Seller Name";
    public static final String TWENTY_FIVE_HEAD = "External Ref No";



    /***
     *response返回类型
     */
    public final static Map<String, String> RESPONSE_TYPE_MAP;

    static {
        RESPONSE_TYPE_MAP = new LinkedHashMap<>();
        RESPONSE_TYPE_MAP.put("ACK1A", "ACK1A");
        RESPONSE_TYPE_MAP.put("ACK2A", "ACK2A");
        RESPONSE_TYPE_MAP.put("ACK3A", "ACK3A");
        RESPONSE_TYPE_MAP.put("ACK1B", "ACK1B");
        RESPONSE_TYPE_MAP.put("ACK2B", "ACK2B");
        RESPONSE_TYPE_MAP.put("ACK3B", "ACK3B");
    }

    /***
     *response返回数据Body的title对应
     */
    public final static Map<String, String> RESPONSE_BODY_TITLE_MAPPING_MAP;

    static {
        RESPONSE_BODY_TITLE_MAPPING_MAP = new LinkedHashMap<>();
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("responseType", "Response type");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("financeReqNo", "Finance request reference no");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("txnDealRefNo", "Bank reference no");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("txnRefNo", "Instrument No");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("txnStatus", "Transaction Status");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("txnRejectCode", "Reject code");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("txnStatusDescription", "Status Description");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("partnerCode", "Partner Code");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("anchorOrgId", "Anchor Organization ID");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("anchorName", "Anchor Organization Name");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("spokeOrgId", "Spoke Organization ID");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("spokeName", "Spoke Organization Name");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("spokeErpId", "Spoke Organization ERP ID");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("financeDate", "Loan start date");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("txnDate", "Transaction Date");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("updateDate", "Status update date");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("dueDate", "Loan due date");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("financeFee", "Finance Fee ");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("loanAccount", "Loan Account");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("tenor", "Tenor");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("financeAmount", "Finance Amount");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("rateType", "Rate Type");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("currency", "Currency");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("paymentAmt", "Payment Amount");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("repaymentAmt", "Repayment Amount");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("loanorinterestRate", "Interest rate");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("interestAmt", "Interest amount");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("overdueInterest", "Overdue Interest");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("remark", "File Name");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("externalRefNo", "External Ref No");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("instrumentType", "Instrument Type");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("originalInstrumentAmt", "Original Instrument Amount");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("originalFinanceAmt", "Original Finance Amount");
    }



    /**
     * response返回Transaction 状态
     */
    public enum TransactionStatus {

        ACTC("ACTC"),
        ACCP("ACCP"),
        ACSP("ACSP"),
        RJCT("RJCT");

        private String value;

        private TransactionStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
