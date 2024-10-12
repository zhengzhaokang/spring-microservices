package com.microservices.otmp.common.enums;

public enum InvoiceStatusEnum {

    CONFIRM("Confirm"),
    EXPIRED("Expired"),
    REJECTED("Rejected"),
    INVALID("Invalid"),
    WAITING("Waiting"),
    FINANCING("Financing"),
    FINANCED("Financed");

    public String code;

    InvoiceStatusEnum(String code){
        this.code= code;
    }
}
