package com.microservices.otmp.masterdata.domain.entity.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseOrgOfficeDTO;
import lombok.Data;

import java.util.List;

/**
 * code和name数据
 * 
 * @author lovefamily
 * @date 2022-12-23
 */
@Data
public class BizBaseOrgOfficeTreeDTO
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "Business Group")
    private String businessGroup;

    @Excel(name = "Geo")
    private String geoCode;

    @Excel(name = "Region/Market Code")
    private String regionMarketCode;

    @Excel(name = "Region/Market Name")
    private String regionMarketName;

    @Excel(name = "Territory Code")
    private String territoryCode;

    @Excel(name = "Territory Name")
    private String territoryName;

    @Excel(name = "Sales Org Code")
    private String salesOrgCode;

    @Excel(name = "Sales Org Name")
    private String salesOrgName;

    @Excel(name = "Is Dummy Org")
    private String isDummy;

    @Excel(name = "Local Currency")
    private String localCurrencyCode;

    @Excel(name = "Company Code")
    private String companyCode;

    @Excel(name = "Accrual Company Code")
    private String accrualCompanyCode;

    private List<BizBaseOrgOfficeDTO> children;

}
