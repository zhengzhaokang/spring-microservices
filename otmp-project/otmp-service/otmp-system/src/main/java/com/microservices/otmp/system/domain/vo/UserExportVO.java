package com.microservices.otmp.system.domain.vo;

import com.microservices.otmp.common.annotation.Excel;
import lombok.Data;

@Data
public class UserExportVO {
    @Excel(name = "IT Code", width = 25)
    private String itCode;
    @Excel(name = "User Name" ,width = 25)
    private String userName;
    @Excel(name = "Email" ,width = 25)
    private String email;
    @Excel(name = "Status" ,width = 25)
    private String status;
    @Excel(name = "Delete" ,width = 25)
    private String delFlag;
    @Excel(name = "User Role" ,width = 25)
    private String userRole;

    private Long roleId;
    private Long userId;
    @Excel(name = "Business Group" ,width = 25)
    private String businessGroup;
    @Excel(name = "Geo" ,width = 25)
    private String geo;
    @Excel(name = "Region/Market" ,width = 50)
    private String regionMarket;
    @Excel(name = "Segment" ,width = 50)
    private String segment;
    @Excel(name = "Sales Org" ,width = 50)
    private String salesOrg;
    @Excel(name = "Sales Office" ,width = 50)
    private String salesOffice;
    @Excel(name = "GTN Type" ,width = 50)
    private String gtnType;

}
