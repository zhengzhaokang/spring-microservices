package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * BizBaseSalesOrg对象 biz_base_sales_org
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
public class BizBaseSalesOrg extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** GEO */
    @Excel(name = "GEO")
    private String geoCode;

    /** Region/Market Code */
    //@Excel(name = "Region/Market")
    private String regionMarketCode;

    /** Region/Market Name */
    @Excel(name = "Region/Market")
    private String regionMarketName;

    /** SalesOrg */
    @Excel(name = "SalesOrg")
    private String salesOrgCode;

    /** SalesOrg Name */
    @Excel(name = "SalesOrg Name")
    private String salesOrgName;

    /** Local Currency */
    @Excel(name = "Local Currency")
    private String localCurrencyCode;

    /** Company Code */
    @Excel(name = "Company Code")
    private String companyCode;

    /** Accrual Company Code */
    @Excel(name = "Accrual Company Code")
    private String accrualCompanyCode;

    /** Status */
   // @Excel(name = "Status")
    private String status;


    /** Business Group */
    @Excel(name = "Business Group")
    private String businessGroup;

    private String ids;

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

    private String isDummy;

    public String getIsDummy() {
        return isDummy;
    }

    public void setIsDummy(String isDummy) {
        this.isDummy = isDummy;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    private List<Long> idsList;

    public List<Long> getIdsList() {
        return idsList;
    }

    public void setIdsList(List<Long> ids) {
        this.idsList = ids;
    }
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
    public void setRegionMarketCode(String regionMarketCode) 
    {
        this.regionMarketCode = regionMarketCode;
    }

    public String getRegionMarketCode() 
    {
        return regionMarketCode;
    }
    public void setRegionMarketName(String regionMarketName)
    {
        this.regionMarketName = regionMarketName;
    }

    public String getRegionMarketName()
    {
        return regionMarketName;
    }
    public void setSalesOrgCode(String salesOrgCode) 
    {
        this.salesOrgCode = salesOrgCode;
    }

    public String getSalesOrgCode() 
    {
        return salesOrgCode;
    }
    public void setSalesOrgName(String salesOrgName) 
    {
        this.salesOrgName = salesOrgName;
    }

    public String getSalesOrgName() 
    {
        return salesOrgName;
    }
    public void setLocalCurrencyCode(String localCurrencyCode) 
    {
        if(StringUtils.isNotBlank(localCurrencyCode)){
            this.localCurrencyCode = localCurrencyCode.trim();
        }
    }

    public String getLocalCurrencyCode() 
    {
        return localCurrencyCode;
    }
    public void setCompanyCode(String companyCode) 
    {
        if(StringUtils.isNotBlank(companyCode)){
            this.companyCode = companyCode.trim();
        }
    }

    public String getCompanyCode() 
    {
        return companyCode;
    }
    public void setAccrualCompanyCode(String accrualCompanyCode) 
    {
        if(StringUtils.isNotBlank(accrualCompanyCode)){
            this.accrualCompanyCode = accrualCompanyCode.trim();
        }
    }

    public String getAccrualCompanyCode() 
    {
        return accrualCompanyCode;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setBusinessGroup(String businessGroup)
    {
        this.businessGroup = businessGroup;
    }

    public String getBusinessGroup() 
    {
        return businessGroup;
    }
    @Override
    public void setCreateBy(String createBy) {
        if(StringUtils.isNotBlank(createBy)){
            this.createBy = createBy.trim();
        }
    }
    @Override
    public String getCreateBy() {
        return createBy;
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
    public void setRemark(String remark) {
        if(StringUtils.isNotBlank(remark)){
            super.setRemark(remark.trim());
        }
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("geoCode", getGeoCode())
            .append("regionMarketCode", getRegionMarketCode())
            .append("salesOrgCode", getSalesOrgCode())
            .append("salesOrgName", getSalesOrgName())
            .append("localCurrencyCode", getLocalCurrencyCode())
            .append("companyCode", getCompanyCode())
            .append("accrualCompanyCode", getAccrualCompanyCode())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
//            .append("wd3", getWd3())
            .append("businessGroup", getBusinessGroup())
            .toString();
    }
}
