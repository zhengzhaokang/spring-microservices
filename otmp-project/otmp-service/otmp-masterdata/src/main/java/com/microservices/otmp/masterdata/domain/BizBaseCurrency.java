package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import com.microservices.otmp.common.core.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * customer对象 biz_base_customer
 *
 * @author lovefamily
 * @date 2022-04-08
 */
public class BizBaseCurrency extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * geo
     */
    @Excel(name = "Currency Code")
    private String currencyCode;

    /**
     * bu_code
     */
    @Excel(name = "Currency Desc")
    private String currencyDesc;

    /**
     * bu_name
     */
   // @Excel(name = "bu_name")
    private String digitsTag;

    /**
     * 状态：0：删除/作废， 1：正常
     */
   // @Excel(name = "状态：0：删除/作废， 1：正常")
    private String status;
    /**
     * 描述
     */
    @Excel(name = "Remark")
    private String remark;
    @Excel(name = "Decimals")
    private BigDecimal decimals;
    /** * 创建者 */
    @Excel(name = "Applicant", width = 25)
    private String createBy;
    /** * 创建时间 */
    private Date createTime;

    /** * 创建时间 */
    @Excel(name = "Application Date", width = 25, dateFormat = "MM/dd/yyyy")
    private Date createDate;

    /** * 创建时间 */
    @Excel(name = "Application Time", width = 25, dateFormat = "HH:mm:ss")
    private Date createSecond;
    private String ids;

    private List<Long> idsList;


    private String[] currencyCodeList;


    public List<Long> getIdsList() {
        return idsList;
    }

    public void setIdsList(List<Long> idsList) {
        this.idsList = idsList;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String[] getCurrencyCodeList() {
        return currencyCodeList;
    }

    public void setCurrencyCodeList(String[] currencyCodeList) {
        this.currencyCodeList = currencyCodeList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCurrencyCode(String currencyCode) {
        if(StringUtils.isNotBlank(currencyCode)){
            this.currencyCode = currencyCode.trim();
        }
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyDesc(String currencyDesc) {
        if(StringUtils.isNotBlank(currencyDesc)){
            this.currencyDesc = currencyDesc.trim();
        }
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
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        if(StringUtils.isNotBlank(createBy)){
            this.createBy = createBy.trim();
        }
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        if(StringUtils.isNotBlank(remark)){
            this.remark = remark.trim();
        }
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.createDate = createTime;
        this.createSecond = createTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateSecond() {
        return createSecond;
    }

    public void setCreateSecond(Date createSecond) {
        this.createSecond = createSecond;
    }



    @Override
    public String toString() {
        return "BizBaseCurrency{" +
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
