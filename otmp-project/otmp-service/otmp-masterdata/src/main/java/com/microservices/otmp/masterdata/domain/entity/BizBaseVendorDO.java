package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * lgvm vendor对象 biz_base_vendor
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
@Data
public class BizBaseVendorDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    private String status;
    private String bankType;
    private String bankAccount;
    private String bankNumber;
    private String bankName;
    private String bankCountry;

    /** Id */
    private Long id;

    /** Vendor Code */

    private String vendorCode;

    /** Vendor Name */

    private String vendorName;

    /** Vendor Country */

    private String vendorCountry;

    /** State */

    private String state;

    /** City */

    private String city;

    /** VAT number */

    private String vatNumber;

    /** Customer ID */

    private String customerId;

    /** India GSTIN */

    private String indiaGstin;

    /** additionalVendorCategory */

    private String additionalVendorCategory;

    /** primaryVendorCategory */

    private String primaryVendorCategory;

    /** transactionCategories */

    private String transactionVendorCategories;

    /** taxNumber1 */

    private String taxNumber1;

    /** taxNumber2 */

    private String taxNumber2;

    /** taxNumber3 */

    private String taxNumber3;

    /** taxNumber4 */

    private String taxNumber4;

    /** taxNumber5 */

    private String taxNumber5;

    private String errorInfo;


}
