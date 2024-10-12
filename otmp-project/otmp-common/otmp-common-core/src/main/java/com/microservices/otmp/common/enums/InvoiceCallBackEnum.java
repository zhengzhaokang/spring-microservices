package com.microservices.otmp.common.enums;

public enum InvoiceCallBackEnum {

    ACK("ACK"),
    BANK("Bank");

    public String code;

    InvoiceCallBackEnum(String code){
        this.code= code;
    }
}
