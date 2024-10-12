package com.microservices.otmp.disclosures.common.enums;

public enum DisclosureItemEnum {
    CONFIRMED("Confirmed"),

    DATA_COLLECTED("Data Collected");

    public final String code;

    DisclosureItemEnum(String code) {
        this.code = code;
    }
}