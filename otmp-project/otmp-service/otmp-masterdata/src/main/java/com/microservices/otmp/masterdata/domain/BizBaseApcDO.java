package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.core.domain.BaseDO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseEntity;

/**
 * BaseApc对象 biz_base_apc
 * 
 * @author lovefamily
 * @date 2022-07-15
 */
public class BizBaseApcDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** APC Code */
    @Excel(name = "APC Code")
    private String apcCode;

    /** Internal Name */
    @Excel(name = "Internal Name")
    private String internalName;

    /** External Name */
    @Excel(name = "External Name")
    private String externalName;

    /** MTM */
    @Excel(name = "MTM")
    private String mtm;

    /** Country Code */
    @Excel(name = "Country Code")
    private String countryCode;

    /** Status */
    @Excel(name = "Status")
    private String status;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String mtmDescription;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setApcCode(String apcCode) 
    {
        this.apcCode = apcCode;
    }

    public String getApcCode() 
    {
        return apcCode;
    }
    public void setInternalName(String internalName) 
    {
        this.internalName = internalName;
    }

    public String getInternalName() 
    {
        return internalName;
    }
    public void setExternalName(String externalName) 
    {
        this.externalName = externalName;
    }

    public String getExternalName() 
    {
        return externalName;
    }
    public void setMtm(String mtm) 
    {
        this.mtm = mtm;
    }

    public String getMtm() 
    {
        return mtm;
    }
    public void setCountryCode(String countryCode) 
    {
        this.countryCode = countryCode;
    }

    public String getCountryCode() 
    {
        return countryCode;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setMtmDescription(String mtmDescription) 
    {
        this.mtmDescription = mtmDescription;
    }

    public String getMtmDescription() 
    {
        return mtmDescription;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("apcCode", getApcCode())
            .append("internalName", getInternalName())
            .append("externalName", getExternalName())
            .append("mtm", getMtm())
            .append("countryCode", getCountryCode())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("mtmDescription", getMtmDescription())
            .toString();
    }
}
