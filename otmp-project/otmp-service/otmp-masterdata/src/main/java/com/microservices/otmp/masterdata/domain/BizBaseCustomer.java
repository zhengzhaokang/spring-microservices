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
 * BaseCustomer对象 biz_base_customer
 *
 * @author lovefamily
 * @date 2022-04-24
 */
public class BizBaseCustomer extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;


    /** Customer Number */
    @Excel(name = "Customer Number")
    private String customerNumber;

    /** Customer Name */
    @Excel(name = "Customer Name")
    private String customerName;

    /** Sales Org Code*/
    //@Excel(name = "Sales Org")
    private String salesOrgCode;

    /** Sales Org Name*/
    //@Excel(name = "Sales Org")
    private String salesOrgName;

    /** Sales Office Code*/
    //@Excel(name = "Sales Office")
    private String salesOfficeCode;

    /** Sales Office Name*/
    //@Excel(name = "Sales Office")
    private String salesOfficeName;

    /** Division Code */
    //@Excel(name = "Division")
    private String divisionCode;

    /** Division Name */
    @Excel(name = "Division")
    private String divisionName;

    /** Tier Type */
    @Excel(name = "Tier Type")
    private String tierType;

    /** Status */
    //@Excel(name = "Status")
    private String status;

    /** Customer Account Group */
    @Excel(name = "Customer Account Group")
    private String customerAccountGroup;

    /** Country Code*/
    //@Excel(name = "Country")
    private String countryCode;

    /** Country */
    @Excel(name = "CountryName")
    private String countryName;

    /** Block Flag */
    @Excel(name = "Block Flag")
    private String blockFlag;

    /** Archive Flag */
    @Excel(name = "Archive Flag")
    private String archiveFlag;

    private String client;
    private String distributionChannel;

    /** kafka数据系统时间 */
    private Date ibmsnapLogmarker;

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
    public void setCustomerNumber(String customerNumber)
    {
        if(StringUtils.isNotBlank(customerNumber)){
            this.customerNumber = customerNumber.trim();
        }
    }

    public String getCustomerNumber()
    {
        return customerNumber;
    }
    public void setCustomerName(String customerName)
    {
        if(StringUtils.isNotBlank(customerName)){
            this.customerName = customerName.trim();
        }
    }

    public String getCustomerName()
    {
        return customerName;
    }
    public void setSalesOrgCode(String salesOrgCode)
    {
        this.salesOrgCode = salesOrgCode;
    }

    public String getSalesOrgName()
    {
        return salesOrgName;
    }
    public void setSalesOrgName(String salesOrgName)
    {
        this.salesOrgName = salesOrgName;
    }

    public String getSalesOrgCode()
    {
        return salesOrgCode;
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
    public void setDivisionCode(String divisionCode)
    {
        if(StringUtils.isNotBlank(divisionCode)){
            this.divisionCode = divisionCode.trim();
        }
    }

    public String getDivisionCode()
    {
        return divisionCode;
    }
    public void setDivisionName(String divisionName)
    {
        if(StringUtils.isNotBlank(divisionName)){
            this.divisionName = divisionName.trim();
        }
    }

    public String getDivisionName()
    {
        return divisionName;
    }
    public void setTierType(String tierType)
    {
        if(StringUtils.isNotBlank(tierType)){
            this.tierType = tierType.trim();
        }
    }

    public String getTierType()
    {
        return tierType;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setCustomerAccountGroup(String customerAccountGroup)
    {
        if(StringUtils.isNotBlank(customerAccountGroup)){
            this.customerAccountGroup = customerAccountGroup.trim();
        }
    }

    public String getCustomerAccountGroup()
    {
        return customerAccountGroup;
    }
    public void setCountryCode(String countryCode)
    {
        if(StringUtils.isNotBlank(countryCode)){
            this.countryCode = countryCode.trim();
        }
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryName(String countryName)
    {
        if(StringUtils.isNotBlank(countryName)) {
            this.countryName = countryName.trim();
        }
    }

    public String getCountryName()
    {
        return countryName;
    }
    public void setBlockFlag(String blockFlag)
    {
        if(StringUtils.isNotBlank(blockFlag)){
            this.blockFlag = blockFlag.trim();
        }
    }

    public String getBlockFlag()
    {
        return blockFlag;
    }
    public void setArchiveFlag(String archiveFlag)
    {
        if(StringUtils.isNotBlank(archiveFlag)){
            this.archiveFlag = archiveFlag.trim();
        }
    }

    public String getArchiveFlag()
    {
        return archiveFlag;
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


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDistributionChannel() {
        return distributionChannel;
    }

    public void setDistributionChannel(String distributionChannel) {
        this.distributionChannel = distributionChannel;
    }

    public Date getIbmsnapLogmarker() {
        return ibmsnapLogmarker;
    }

    public void setIbmsnapLogmarker(Date ibmsnapLogmarker) {
        this.ibmsnapLogmarker = ibmsnapLogmarker;
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
                .append("customerNumber", getCustomerNumber())
                .append("customerName", getCustomerName())
                .append("salesOrgCode", getSalesOrgCode())
                .append("salesOfficeCode", getSalesOfficeCode())
                .append("divisionCode", getDivisionCode())
                .append("tierType", getTierType())
                .append("status", getStatus())
                .append("customerAccountGroup", getCustomerAccountGroup())
                .append("countryCode", getCountryCode())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("blockFlag", getBlockFlag())
                .append("archiveFlag", getArchiveFlag())
                .toString();
    }
}
