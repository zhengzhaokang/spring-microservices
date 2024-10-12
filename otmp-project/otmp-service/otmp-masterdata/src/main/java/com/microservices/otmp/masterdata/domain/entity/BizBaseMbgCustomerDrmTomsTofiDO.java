package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * BizBaseMbgCustomerDrmTomsTofi对象 biz_base_mbg_customer_drm_toms_tofi
 * 
 * @author lovefamily
 * @date 2023-03-01
 */
@Data
public class BizBaseMbgCustomerDrmTomsTofiDO extends BaseDO
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

    /** Sales Org Code */

    private String salesOrgCode;

    /** Sales Org Name */

    private String salesOrgName;

    /** Sales Office Code */

    private String salesOfficeCode;

    /** Sales Office Name */

    private String salesOfficeName;

    /** Country Code */

    private String countryCode;

    /** Country Name */

    private String countryName;

    /** Customer Number */

    private String customerNumber;

    /** Customer Name */

    private String customerName;

    /** Key Account */

    private String keyAccount;

    /** Key Account Desc */

    private String keyAccountDesc;

    /** Customer Group */

    private String customerGroup;

    /** Customer Group Desc */

    private String customerGroupDesc;

    /** Active Status */

    private String activeStatus;


}
