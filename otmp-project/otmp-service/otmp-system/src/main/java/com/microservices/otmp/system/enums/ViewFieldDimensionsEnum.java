package com.microservices.otmp.system.enums;

import lombok.Getter;

@Getter
public enum ViewFieldDimensionsEnum {

    GEO_CODE("geoCode"),
    REGION_MARKET_CODE("regionMarketCode"),
    BUSINESS_GROUP("businessGroup"),
    PAYMENT_MODE("paymentMode"),
    SALES_ORG_CODE("salesOrgCode"),
    SALES_OFFICE_CODE("salesOfficeCode"),
    SEGMENT_CODE("segmentCode"),
    COMPANY_CODE("companyCode"),
    ;

    ViewFieldDimensionsEnum(String name) {
        this.name = name;
    }

    private String name;
}
