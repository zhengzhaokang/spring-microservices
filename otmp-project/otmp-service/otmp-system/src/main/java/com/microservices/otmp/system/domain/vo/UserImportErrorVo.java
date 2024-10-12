package com.microservices.otmp.system.domain.vo;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.annotation.ImportValid;
import lombok.Data;

@Data
public class UserImportErrorVo {

    @Excel(name = "IT Code", width = 25)
    private String itCode;
    @Excel(name = "User Name", width = 25)
    private String userName;


    @Excel(name = "Status", width = 25)
    private String status;

    @ImportValid(excelName = "User Role")
    @Excel(name = "User Role", width = 25)
    private String userRole;

    @ImportValid(excelName = "Action")
    @Excel(name = "Action", width = 25)
    private String action;

    private Long roleId;
    private Long userId;


    @Excel(name = "Business Group", width = 25)
    @ImportValid(excelName = "Business Group")
    private String businessGroup;

    @Excel(name = "Geo", width = 25)
    @ImportValid(excelName = "Geo")
    private String geoCode;

    @ImportValid(excelName = "Region/Market")
    @Excel(name = "Region/Market", width = 50)
    private String regionMarketCode;

    @ImportValid(excelName = "Business Group")
    @Excel(name = "Segment", width = 50)
    private String segmentCode;

    @ImportValid(excelName = "Sales Org")
    @Excel(name = "Sales Org", width = 50)
    private String salesOrgCode;

    @ImportValid(excelName = "Sales Office")
    @Excel(name = "Sales Office", width = 50)
    private String salesOfficeCode;

    @ImportValid(excelName = "GTN Type")
    @Excel(name = "GTN Type", width = 50)
    private String gtnTypeCode;

    @Excel(name = "Error Msg", width = 200)
    private String errorMsg;
}
