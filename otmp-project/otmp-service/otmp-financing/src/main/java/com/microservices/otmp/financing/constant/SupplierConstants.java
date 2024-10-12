package com.microservices.otmp.financing.constant;

public class SupplierConstants {

    public static final String REMOTE_STATUS_ACTIVE = "1";
    public static final String REMOTE_STATUS_SUSPEND = "2";
    public static final String REMOTE_STATUS_ON_HOLD = "3";
    public static final String REMOTE_STATUS_HOLD_CANCEL = "4";
    public static final String REMOTE_SUPPLIER_MODEL_BOE = "W";
    public static final String REMOTE_SUPPLIER_MODEL_AP = "";

    public static final String SUPPLIER_MODEL_BOE = "BOE";

    public static final String MATURITY_DATE_CHANGEABLE_TRUE = "1";
    public static final String MATURITY_DATE_CHANGEABLE_FALSE = "2";

    public static boolean convertMdChangeable(String changeableStr) {
        return MATURITY_DATE_CHANGEABLE_TRUE.equals(changeableStr);
    }

    public static String convertMdChangeableBool(Boolean changeable) {
        return (changeable != null && changeable)
                ? MATURITY_DATE_CHANGEABLE_TRUE
                : MATURITY_DATE_CHANGEABLE_FALSE;
    }
}
