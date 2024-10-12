package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * bizBaseRegionMarket对象 biz_base_region_market
 * 
 * @author lovefamily
 * @date 2022-04-18
 */

public class BizBaseDropDownList
{
    private static final long serialVersionUID = 1L;

    private String label;

    private String value;

    public String getLabel()
    {
        return label;
    }
    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
}
