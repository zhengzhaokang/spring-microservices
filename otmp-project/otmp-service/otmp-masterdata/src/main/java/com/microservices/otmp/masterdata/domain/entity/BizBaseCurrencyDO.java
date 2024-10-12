package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDO;

import java.math.BigDecimal;

public class BizBaseCurrencyDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * geo
     */
    private String currencyCode;

    /**
     * bu_code
     */
    private String currencyDesc;

    /**
     * bu_name
     */
    @Excel(name = "bu_name")
    private String digitsTag;

    /**
     * 状态：0：删除/作废， 1：正常
     */
    @Excel(name = "状态：0：删除/作废， 1：正常")
    private String status;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String remark;
    @Excel(name = "decimals")
    private BigDecimal decimals;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyDesc(String currencyDesc) {
        this.currencyDesc = currencyDesc;
    }

    public String getCurrencyDesc() {
        return currencyDesc;
    }

    public void setDigitsTag(String digitsTag) {
        this.digitsTag = digitsTag;
    }

    public String getDigitsTag() {
        return digitsTag;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getDecimals() {
        return decimals;
    }

    public void setDecimals(BigDecimal decimals) {
        this.decimals = decimals;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "BizBaseCurrencyDO{" +
                "id='" + id + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyDesc='" + currencyDesc + '\'' +
                ", digitsTag='" + digitsTag + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", decimals=" + decimals +
                '}';
    }
}
