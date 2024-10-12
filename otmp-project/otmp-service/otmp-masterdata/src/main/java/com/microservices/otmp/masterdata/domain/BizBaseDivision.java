package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;

/**
 * division对象 biz_base_division
 * 
 * @author sdms
 * @date 2022-01-20
 */
public class BizBaseDivision extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** $column.columnComment */
    private String geoCode;

    /** $column.columnComment */
    private String divisionCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String divisionName;

    /** 状态：0：删除/作废， 1：正常 */
    @Excel(name = "状态：0：删除/作废， 1：正常")
    private String status;

    /** $column.columnComment */
    @Excel(name = "状态：0：删除/作废， 1：正常")
    private String remark;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
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
    public void setDivisionCode(String divisionCode) 
    {
        this.divisionCode = divisionCode;
    }

    public String getDivisionCode() 
    {
        return divisionCode;
    }
    public void setDivisionName(String divisionName) 
    {
        this.divisionName = divisionName;
    }

    public String getDivisionName() 
    {
        return divisionName;
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
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    @Override

    public String getRemark()
    {
        return remark;
    }

}
