package com.microservices.otmp.analysis.common.enums;

public enum InvoiceTypeEnum {

    INVOICE_TYPE_DEBIT("Debit Memo", "debits"),
    INVOICE_TYPE_CREDIT("Credit Memo", "credits");

    private String code;
    private String name;

    InvoiceTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
