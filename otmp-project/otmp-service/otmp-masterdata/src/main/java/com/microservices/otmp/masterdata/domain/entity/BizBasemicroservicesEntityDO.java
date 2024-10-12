package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * microservices Entity Table From ECC对象 biz_base_microservices_entity
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
@Data
public class BizBasemicroservicesEntityDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Company Code */

    private String companyCode;

    /** Country */

    private String country;

    /** microservices Entity Name */

    private String microservicesEntityName;

    /** microservices Entity Address */

    private String microservicesEntityAddress;

    /** microservices Vat Id */

    private String microservicesVatId;

    /** Status */

    private String status;

    /** Data Version */

    private Integer dataVersion;

    /** Delete Flag */

    private Boolean deleteFlag;


}
