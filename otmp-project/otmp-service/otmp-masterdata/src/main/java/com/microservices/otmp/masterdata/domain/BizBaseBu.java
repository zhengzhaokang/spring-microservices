package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * baseBu对象 biz_base_bu
 * 
 * @author sdms
 * @date 2022-01-15
 */
public class BizBaseBu extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** geo */
    private String geoCode;

    /** bu_code */
    private String buCode;

    /** bu_name */
    @Excel(name = "bu_name")
    private String buName;

    /** 状态：0：删除/作废， 1：正常 */
    private String delFlag;

    /** 描述 */
    @Excel(name = "描述")
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
    public void setBuCode(String buCode) 
    {
        this.buCode = buCode;
    }

    public String getBuCode() 
    {
        return buCode;
    }
    public void setBuName(String buName) 
    {
        this.buName = buName;
    }

    public String getBuName() 
    {
        return buName;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("geoCode", getGeoCode())
            .append("buCode", getBuCode())
            .append("buName", getBuName())
            .append("delFlag", getDelFlag())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
