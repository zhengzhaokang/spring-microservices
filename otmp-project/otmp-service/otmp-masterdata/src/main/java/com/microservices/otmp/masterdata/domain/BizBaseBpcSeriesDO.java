package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.core.domain.BaseDO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseEntity;

/**
 * BaseBpcSeries对象 biz_base_bpc_series
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
public class BizBaseBpcSeriesDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String geoCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String bpcBuCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String bwBuCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String bpcSeriesCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String bpcSeriesName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGeoCode(String geoCode) 
    {
        this.geoCode = geoCode;
    }

    public String getGeoCode() 
    {
        return geoCode;
    }
    public void setBpcBuCode(String bpcBuCode) 
    {
        this.bpcBuCode = bpcBuCode;
    }

    public String getBpcBuCode() 
    {
        return bpcBuCode;
    }
    public void setBwBuCode(String bwBuCode) 
    {
        this.bwBuCode = bwBuCode;
    }

    public String getBwBuCode() 
    {
        return bwBuCode;
    }
    public void setBpcSeriesCode(String bpcSeriesCode) 
    {
        this.bpcSeriesCode = bpcSeriesCode;
    }

    public String getBpcSeriesCode() 
    {
        return bpcSeriesCode;
    }
    public void setBpcSeriesName(String bpcSeriesName) 
    {
        this.bpcSeriesName = bpcSeriesName;
    }

    public String getBpcSeriesName() 
    {
        return bpcSeriesName;
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
            .append("geoCode", getGeoCode())
            .append("bpcBuCode", getBpcBuCode())
            .append("bwBuCode", getBwBuCode())
            .append("bpcSeriesCode", getBpcSeriesCode())
            .append("bpcSeriesName", getBpcSeriesName())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
