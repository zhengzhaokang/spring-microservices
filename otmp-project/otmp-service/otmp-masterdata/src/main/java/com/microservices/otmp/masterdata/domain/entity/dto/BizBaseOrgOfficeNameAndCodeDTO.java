package com.microservices.otmp.masterdata.domain.entity.dto;

import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * code和name数据
 * 
 * @author lovefamily
 * @date 2022-12-23
 */
@Data
public class BizBaseOrgOfficeNameAndCodeDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;


    /** Region/Market Code */
    private String regionMarketCode;

    /** Region/Market Name */
    private String regionMarketName;

    /** Territory Code */
    private String territoryCode;

    /** Territory Name */
    private String territoryName;

    /** Country Code */
    private String countryCode;

    /** Country Name */
    private String countryName;

    /** BPC Country Code */
    private String bpcCountryCode;

    /** BPC Country Name */
    private String bpcCountryName;

    /** Sales Org Code */
    private String salesOrgCode;

    /** Sales Org Name */
    private String salesOrgName;

    /** Sales Office Code */
    private String salesOfficeCode;

    /** Sales Office Name */
    private String salesOfficeName;

    /** Distribution Channel Code */
    private String distributionChannelCode;

    /** Distribution Channel Name */
    private String distributionChannelName;



}
