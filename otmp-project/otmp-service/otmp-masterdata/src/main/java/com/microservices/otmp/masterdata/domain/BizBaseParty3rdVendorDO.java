package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * bizBaseParty3rdVendor对象 biz_base_party_3rd_vendor
 * 
 * @author lovefamily
 * @date 2022-10-12
 */
@Data
public class BizBaseParty3rdVendorDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */

    private Long id;

    /** Geo Code */

    private String geoCode;

    /** Country Code */

    private String countryCode;

    /** Country Name */

    private String countryName;

    /** 3rd party Vendor code */

    private String vendorCode;

    /** Vendor Name */

    private String vendorName;

    /** Vendor Country */

    private String vendorCountry;

    /** Bank Type */

    private String bankType;

    /** Bank Account */

    private String bankAccount;

    /** Bank Name */

    private String bankName;

    /** Swift Code */

    private String swiftCode;


}
