package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * BizBaseOrgOfficeDO 对象 biz_base_geography
 * 
 * @author lovefamily
 * @date 2022-12-23
 */
@Data
public class BizBaseOrgOfficeDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** Business Group */

    private String businessGroup;

    /** Geo */

    private String geoCode;

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

    /** Status */

    private String status;

    /** 数据版本 */

    private Integer dataVersion;

    /** 是否逻辑删除（0正常 1已删除） */

    private Boolean deleteFlag;

    private String isDummy;

    private String localCurrencyCode;

    private String companyCode;

    private String accrualCompanyCode;

}
