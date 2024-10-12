package com.microservices.otmp.masterdata.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * combine_base_data
 *
 * @author sdms
 * @date 2022-01-15
 */
@Data
public class BizBaseVendor
{
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String vendorCode;

    private String vendorName;

    private String vendorCountry;

    private String state;

    private String city;

    private String vatNumber;

    private String additionalVendorCategory;

    private String primaryVendorCategory;

    private String transactionVendorCategories;

    private String customerId;

    private String companyCode;

    private String indiaGstin;

    private String taxNumber1;

    private String taxNumber2;

    private String taxNumber3;

    private String taxNumber4;

    private String taxNumber5;

    private Date createTime;

    private Date updateTime;

    private String status;

    private String errorInfo;

    public void setVendorName(String vendorName) {
        if (StringUtils.isNotBlank(vendorName)) {
            this.vendorName = vendorName.trim();
        }
    }
}
