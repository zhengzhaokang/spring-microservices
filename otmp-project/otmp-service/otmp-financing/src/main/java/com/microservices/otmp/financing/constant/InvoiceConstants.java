package com.microservices.otmp.financing.constant;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;

public class InvoiceConstants {

    public static final String INVOICE_TYPE_DEBIT_STR = "Debit Memo";
    public static final String INVOICE_TYPE_CREDIT_STR = "Credit Memo";
    public static final Integer INVOICE_TYPE_DEBIT = 1;
    public static final Integer INVOICE_TYPE_CREDIT = 2;

    public static String convertType(Integer invoiceType){
        if(INVOICE_TYPE_DEBIT.equals(invoiceType)){
            return INVOICE_TYPE_DEBIT_STR;
        }
        if(INVOICE_TYPE_CREDIT.equals(invoiceType)){
            return INVOICE_TYPE_CREDIT_STR;
        }
        throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.ILLEGAL_PARAM_INVOICE_TYPE),DefaultErrorMessage.ILLEGAL_PARAM_INVOICE_TYPE.intValue());
    }

}
