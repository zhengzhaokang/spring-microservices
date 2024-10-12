package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import com.microservices.otmp.common.core.domain.BaseEntity;

/**
 * payment_mode对象 biz_base_payment_mode
 * 
 * @author sdms
 * @date 2022-01-20
 */
public class BizBasePaymentMode extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** $column.columnComment */
    private String geoCode;

    /** $column.columnComment */
    private String paymentModeCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String paymentModeName;

    /** 状态：0：删除/作废， 1：正常 */
    @Excel(name = "状态：0：删除/作废， 1：正常")
    private Integer status;

    /** $column.columnComment */
    @Excel(name = "状态：0：删除/作废， 1：正常")
    private String memo;

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
    public void setPaymentModeCode(String paymentModeCode) 
    {
        this.paymentModeCode = paymentModeCode;
    }

    public String getPaymentModeCode() 
    {
        return paymentModeCode;
    }
    public void setPaymentModeName(String paymentModeName) 
    {
        this.paymentModeName = paymentModeName;
    }

    public String getPaymentModeName() 
    {
        return paymentModeName;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setMemo(String memo) 
    {
        this.memo = memo;
    }

    public String getMemo() 
    {
        return memo;
    }

}
