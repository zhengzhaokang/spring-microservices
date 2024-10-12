package com.microservices.otmp.masterdata.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * microservices Entity Table From ECC对象 biz_base_microservices_entity
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
@Data
public class BizBasemicroservicesEntityDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Company Code */
    @Excel(name = "Company Code")
    private String companyCode;

    /** Country */
    @Excel(name = "Country")
    private String country;

    /** microservices Entity Name */
    @Excel(name = "microservices Entity Name")
    private String microservicesEntityName;

    /** microservices Entity Address */
    @Excel(name = "microservices Entity Address")
    private String microservicesEntityAddress;

    /** microservices Vat Id */
    @Excel(name = "microservices Vat Id")
    private String microservicesVatId;

    /** Status */
    @Excel(name = "Status")
    private String status;

    /** Data Version */
    @Excel(name = "Data Version")
    private Integer dataVersion;

    /** Delete Flag */
    @Excel(name = "Delete Flag")
    private Boolean deleteFlag;

}
