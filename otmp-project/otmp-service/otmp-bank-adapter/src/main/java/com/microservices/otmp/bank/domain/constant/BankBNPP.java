package com.microservices.otmp.bank.domain.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/24
 * time: 15:02
 * Description:
 */
public class BankBNPP {

    public static final String BANK_TYPE = "BNPP";
    public static final String FH = "FH";
    public static final String INV = "INV";
    public static final String CN = "CN";
    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final Integer BIGDECIMAL_FORMAT = 2;
    public static final String TRANSFER_HEADER = "HEADER";
    public static final String TRANSFER_BODY = "BODY";
    public static final String FILE_VALIDATION_FAILED = "File Validation Failed";
    public static final String FILE_VALIDATION_SUCCESSFUL = "File Validation Successful";
    /**
     * Customized Auto Discounting Failed Report (ADF)
     */
    public static final String FILE_TYPE_ADF = "ADF";
    /**
     * Customized Discounted Invoice
     */
    public static final String FILE_TYPE_DIR = "DIR";

    public static final String CELL_TITLE_BNP_BRANCH = "BNP Branch";
    public static final String CELL_TITLE_BUYER_ORG_ID = "Buyer Org ID";
    public static final String CELL_TITLE_BUYER_NAME = "Buyer Name";
    public static final String CELL_TITLE_SUPPLIER_ORG_ID = "Supplier Org ID";
    public static final String CELL_TITLE_SUPPLIER_NAME = "Supplier Name";
    public static final String CELL_TITLE_SUPPLIER_ERP_ID = "Supplier ERP ID";
    public static final String CELL_TITLE_CSC_FO_PAYMENT_DISC_REFERENCE = "CSC FO Payment Disc Reference";
    public static final String CELL_TITLE_CSC_FO_DISC_BATCH_REFERENCE = "CSC FO Disc Batch Reference";
    public static final String CELL_TITLE_CSC_BO_DISCOUNT_REFERENCE= "CSC BO Discount Reference";
    public static final String CELL_TITLE_DISCOUNT_CURRENCY = "Discount Currency";
    public static final String CELL_TITLE_FINANCING_AMOUNT = "Financing Amount";
    public static final String CELL_TITLE_DISCOUNT_DATE= "Discount Date";
    public static final String CELL_TITLE_ADJUSTED_DUE_DATE = "Adjusted Due Date";
    public static final String CELL_TITLE_NO_OF_TENOR_DAYS = "No of Tenor Days";
    public static final String CELL_TITLE_CONFIRMED_BASE_RATE = "Confirmed Base Rate";
    public static final String CELL_TITLE_SPREAD = "Spread";
    public static final String CELL_TITLE_CONFIRMED_INTEREST_RATE = "Confirmed Interest Rate";
    public static final String CELL_TITLE_CONFIRMED_INTEREST_AMOUNT = "Confirmed Interest Amount";
    public static final String CELL_TITLE_CONFIRMED_CHARGE_AMOUNT = "Confirmed Charge Amount";
    public static final String CELL_TITLE_CONFIRMED_NET_AMOUNT = "Confirmed Net Amount";
    public static final String CELL_TITLE_INTEREST_CHARGE_TYPE = "Interest Charge Type";
    public static final String CELL_TITLE_microservices_BATCH_NUMBER = "microservices Batch Number";


    /***
     *response返回数据Body的title对应
     */
    public final static Map<String, String> RESPONSE_BODY_TITLE_MAPPING_MAP;

    static {
        RESPONSE_BODY_TITLE_MAPPING_MAP = new LinkedHashMap<>();
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("", "BNP Branch");
        RESPONSE_BODY_TITLE_MAPPING_MAP.put("", "Buyer Org ID");
    }



}
