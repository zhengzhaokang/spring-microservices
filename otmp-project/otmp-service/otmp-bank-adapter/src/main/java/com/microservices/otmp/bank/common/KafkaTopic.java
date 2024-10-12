package com.microservices.otmp.bank.common;

/**
 * @author lovefamily
 */
public class KafkaTopic {

    private KafkaTopic() {
        throw new IllegalStateException("Utility class");
    }

    public static final String MSG_BANK_INVOICE_TRANSFER = "otfp.bank-invoice-transfer.tst";
    public static final String MSG_BANK_INVOICE_SUBMISSION = "otfp.bank-invoice-submission.tst";

}
