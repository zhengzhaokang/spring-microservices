package com.microservices.otmp.masterdata.domain;

/**
 * bizBaseRegionMarket对象 biz_base_region_market
 *
 * @author lovefamily
 * @date 2022-04-18
 */

public class BizBaseDropDownCondition
{
    private static final long serialVersionUID = 1L;

    private String geoCode;
    private String regionMarketCode;

    private String businessGroup;

    private String paymentModeCode;
    private String salesOrgCode;
    private String gtnTypeCode;
    private String gtnCategoryCode;

    private String segmentCode;

    private String[] salesOrgs;

    //临时公共字段
    private String tempField;
    private String salesOfficeCode;
    private String territory;

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public String getBusinessGroup() {
        return businessGroup;
    }

    public void setBusinessGroup(String businessGroup) {
        this.businessGroup = businessGroup;
    }

    public String getRegionMarketCode() {
        return regionMarketCode;
    }

    public void setRegionMarketCode(String regionMarketCode) {
        this.regionMarketCode = regionMarketCode;
    }

    public String getSalesOrgCode() {
        return salesOrgCode;
    }

    public void setSalesOrgCode(String salesOrgCode) {
        this.salesOrgCode = salesOrgCode;
    }

    public String[] getSalesOrgs() {
        return salesOrgs;
    }

    public void setSalesOrgs(String[] salesOrgs) {
        this.salesOrgs = salesOrgs;
    }

    public String getTempField() {
        return tempField;
    }

    public void setTempField(String tempField) {
        this.tempField = tempField;
    }

    public String getPaymentModeCode() {
        return paymentModeCode;
    }

    public void setPaymentModeCode(String paymentModeCode) {
        this.paymentModeCode = paymentModeCode;
    }

    public String getGtnTypeCode() {
        return gtnTypeCode;
    }

    public void setGtnTypeCode(String gtnTypeCode) {
        this.gtnTypeCode = gtnTypeCode;
    }

    public String getSalesOfficeCode() {
        return salesOfficeCode;
    }

    public void setSalesOfficeCode(String salesOfficeCode) {
        this.salesOfficeCode = salesOfficeCode;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getGtnCategoryCode() {
        return gtnCategoryCode;
    }

    public void setGtnCategoryCode(String gtnCategoryCode) {
        this.gtnCategoryCode = gtnCategoryCode;
    }
}
