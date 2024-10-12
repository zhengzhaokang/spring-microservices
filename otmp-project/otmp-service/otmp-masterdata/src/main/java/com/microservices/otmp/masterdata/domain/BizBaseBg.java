package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;

/**
 * business_group对象 biz_base_business_group
 * 
 * @author sdms
 * @date 2022-01-19
 */
public class BizBaseBg extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** geo */
    private String geoCode;

    /** 业务组 */
    private String bgCode;

    /** 业务组全称 */
    @Excel(name = "业务组全称")
    private String bgName;

    /** 状态：0：删除/作废， 1：正常 */
    @Excel(name = "状态：0：删除/作废， 1：正常")
    private String status;

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
    public void setBgCode(String bgCode) 
    {
        this.bgCode = bgCode;
    }

    public String getBgCode() 
    {
        return bgCode;
    }
    public void setBgName(String bgName) 
    {
        this.bgName = bgName;
    }

    public String getBgName() 
    {
        return bgName;
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
