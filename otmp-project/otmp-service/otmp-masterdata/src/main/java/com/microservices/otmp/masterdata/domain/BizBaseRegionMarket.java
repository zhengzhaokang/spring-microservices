package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.core.domain.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * BizBaseRegionMarket对象 biz_base_region_market
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
public class BizBaseRegionMarket extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** GEO */
    @Excel(name = "GEO")
    private String geoCode;

    /** Region/Market */
    @Excel(name = "Region/Market")
    private String regionMarketCode;

    /** Region/Market Name */
    @Excel(name = "Region/Market Name")
    private String regionMarketName;

    /** Region Market Currency */
    @Excel(name = "Region Market Currency")
    private String regionMarketCurrency;

    /** Status */
   // @Excel(name = "Status")
    private String status;

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
        this.regionMarketCode = regionMarketCode.trim();
    }

    public String getRegionMarketCode() 
    {
        return regionMarketCode;
    }
    public void setRegionMarketName(String regionMarketName) 
    {
        if(StringUtils.isNotBlank(regionMarketName)){
            this.regionMarketName = regionMarketName.trim();
        }
    }

    public String getRegionMarketName() 
    {
        return regionMarketName;
    }
    public void setRegionMarketCurrency(String regionMarketCurrency)
    {
        this.regionMarketCurrency = regionMarketCurrency.trim();
    }

    public String getRegionMarketCurrency() 
    {
        return regionMarketCurrency;
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
            .append("regionMarketName", getRegionMarketName())
            .append("regionMarketCurrency", getRegionMarketCurrency())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
