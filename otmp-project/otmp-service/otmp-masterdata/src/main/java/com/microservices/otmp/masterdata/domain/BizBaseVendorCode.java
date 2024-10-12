package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * biz_base_vendor_code from ECC对象 biz_base_vendor_code
 * 
 * @author lovefamily
 * @date 2022-04-25
 */
public class BizBaseVendorCode extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Business Group */
    @Excel(name = "Business Group")
    private String businessGroup;

    /** Vendor Code */
    private String vendorCode;

    /** Vendor Name */
    @Excel(name = "Vendor Name")
    private String vendorName;

    /** Company Code */
    @Excel(name = "Company Code")
    private String companyCode;

    /** Status */
    @Excel(name = "Status")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBusinessGroup(String businessGroup) 
    {
        this.businessGroup = businessGroup;
    }

    public String getBusinessGroup() 
    {
        return businessGroup;
    }
    public void setVendorCode(String vendorCode) 
    {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() 
    {
        return vendorCode;
    }
    public void setVendorName(String vendorName) 
    {
        this.vendorName = vendorName;
    }

    public String getVendorName() 
    {
        return vendorName;
    }
    public void setCompanyCode(String companyCode) 
    {
        this.companyCode = companyCode;
    }

    public String getCompanyCode() 
    {
        return companyCode;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("businessGroup", getBusinessGroup())
            .append("vendorCode", getVendorCode())
            .append("vendorName", getVendorName())
            .append("companyCode", getCompanyCode())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
