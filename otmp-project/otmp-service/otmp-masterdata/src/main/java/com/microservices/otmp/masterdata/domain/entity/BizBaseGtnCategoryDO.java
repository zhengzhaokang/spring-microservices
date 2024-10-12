package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * gtnCategory对象 biz_base_gtn_category
 * 
 * @author lovefamily
 * @date 2023-06-05
 */
@Data
public class BizBaseGtnCategoryDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** GTN Category L0 */

    private String gtnCategoryL0;

    /** GTN Category L1 */

    private String gtnCategoryL1;

    /** Order Reason For CN */

    private String orderReasonForCn;

    /** Order Reason Description In SAP */

    private String orderReasonDescriptionInSap;

    /** Status */

    private Boolean isActive;
    private Boolean deleteFlag;


}
