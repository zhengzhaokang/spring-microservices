package com.microservices.otmp.system.domain.vo;

import com.microservices.otmp.common.BaseImportDTO;
import com.microservices.otmp.common.NewRedisMasterDataKey;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.annotation.ImportValid;
import com.microservices.otmp.common.constant.DicKeyConstants;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.SysUserRole;
import com.microservices.otmp.system.domain.entity.SysUserDataScopeDO;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class UserImportVo extends BaseImportDTO {


    @Excel(name = "IT Code", width = 25)
    @ImportValid(excelName = "IT Code", required = true)
    private String itCode;
    @Excel(name = "User Name", width = 25)
    @ImportValid(excelName = "User Name", required = true)
    private String userName;


    @Excel(name = "Status", width = 25)
    @ImportValid(excelName = "Status", required = true)
    private String status;

    @ImportValid(excelName = "Action", required = true)
    @Excel(name = "Action", width = 25)
    private String action;

    @ImportValid(excelName = "User Role")
    @Excel(name = "User Role", width = 25)
    private String userRole;


    private Long roleId;
    private Long userId;


    @Excel(name = "Business Group", width = 25)
    @ImportValid(excelName = "Business Group", dicType = DicKeyConstants.B_G)
    private String businessGroup;

    @Excel(name = "Geo", width = 25)
    @ImportValid(excelName = "Geo", dicType = DicKeyConstants.GEO)
    private String geoCode;

    @ImportValid(excelName = "Region/Market", masterData = NewRedisMasterDataKey.REGION_MARKET_CODE)
    @Excel(name = "Region/Market", width = 50)
    private String regionMarketCode;

    @ImportValid(excelName = "Business Group", masterData = NewRedisMasterDataKey.SEGMENT)
    @Excel(name = "Segment", width = 50)
    private String segmentCode;

    @ImportValid(excelName = "Sales Org", masterData = NewRedisMasterDataKey.SALES_ORG)
    @Excel(name = "Sales Org", width = 50)
    private String salesOrgCode;

    @ImportValid(excelName = "Sales Office", masterData = NewRedisMasterDataKey.SALES_OFFICE)
    @Excel(name = "Sales Office", width = 50)
    private String salesOfficeCode;

    @ImportValid(excelName = "GTN Type", masterData = NewRedisMasterDataKey.GTN_TYPE)
    @Excel(name = "GTN Type", width = 50)
    private String gtnTypeCode;
    /**
     * * db查出来的
     */
    List<SysUserDataScopeDO> sysUserDataScopeList;
    /**
     * * 构建出来的
     */
    List<SysUserDataScope> scopes;

    /**
     * * db查出来的
     */
    List<SysUserRole> sysUserRoleList;
    /**
     * * 根据传入的roleName 集合查到的 roleId
     */
    Map<Long, String> roleMaps;

    /**
     * * db查出来的
     */
    SysUser sysUser;

}
