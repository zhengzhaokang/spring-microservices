package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.core.domain.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * BaseProfitCenter对象 biz_base_profit_center
 *
 * @author lovefamily
 * @date 2022-04-22
 */
public class BizBaseProfitCenter extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Region/Market Code */
    @Excel(name = "Region/Market")
    private String regionMarketCode;

    /** Region/Market Name */
    @Excel(name = "Region/Market")
    private String regionMarketName;

    /** Sales Org Code */
    //@Excel(name = "Sales Org")
    private String salesOrgCode;

    /** Sales Org Name */
    @Excel(name = "Sales Org")
    private String salesOrgName;

    /** Sales Office Code */
    //@Excel(name = "Sales Office")
    private String salesOfficeCode;

    /** Sales Office Name */
    @Excel(name = "Sales Office")
    private String salesOfficeName;

    /** Status */
    //@Excel(name = "Status")
    private String status;

    /** Profit Center */
    @Excel(name = "Profit Center")
    private String profitCenterCode;

    /** BWBU */
    @Excel(name = "BWBU")
    private String bwbu;

    /** Division */
    @Excel(name = "Geo")
    private String geoCode;

    /** TS Think Flag */
    @Excel(name = "TS Think Flag")
    private String tsThinkFlag;

    /** Dummy GTN MTM */
    @Excel(name = "Dummy GTN MTM")
    private String dummyGtnMtm;

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

    //临时公共字段
    private String tempField;


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
    public void setSalesOfficeCode(String salesOfficeCode) 
    {
        this.salesOfficeCode = salesOfficeCode;
    }

    public String getSalesOfficeCode() 
    {
        return salesOfficeCode;
    }
    public void setSalesOfficeName(String salesOfficeName)
    {
        this.salesOfficeName = salesOfficeName;
    }

    public String getSalesOfficeName()
    {
        return salesOfficeName;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setProfitCenterCode(String profitCenterCode) 
    {
        this.profitCenterCode = profitCenterCode;
    }

    public String getProfitCenterCode() 
    {
        return profitCenterCode;
    }
    public void setGeoCode(String geoCode)
    {
        this.geoCode = geoCode;
    }

    public String getBwbu() {
        return bwbu;
    }

    public void setBwbu(String bwbu) {
        this.bwbu = bwbu;
    }

    public String getGeoCode()
    {
        return geoCode;
    }
    public void setTsThinkFlag(String tsThinkFlag) 
    {
        this.tsThinkFlag = tsThinkFlag;
    }

    public String getTsThinkFlag() 
    {
        return tsThinkFlag;
    }
    public void setDummyGtnMtm(String dummyGtnMtm) 
    {
        this.dummyGtnMtm = dummyGtnMtm;
    }

    public String getDummyGtnMtm() 
    {
        return dummyGtnMtm;
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

    public String getTempField() {
        return tempField;
    }

    public void setTempField(String tempField) {
        this.tempField = tempField;
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
            .append("regionMarketCode", getRegionMarketCode())
            .append("salesOrgCode", getSalesOrgCode())
            .append("salesOfficeCode", getSalesOfficeCode())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("profitCenterCode", getProfitCenterCode())
            .append("geo", getGeoCode())
            .append("tsThinkFlag", getTsThinkFlag())
            .append("dummyGtnMtm", getDummyGtnMtm())
            .append("businessGroup", getBusinessGroup())
            .toString();
    }
}
