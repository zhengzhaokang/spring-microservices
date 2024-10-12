package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * ProfitCenter Table from LGAP对象 biz_base_profit_center_lgap
 * 
 * @author lovefamily
 * @date 2023-07-01
 */
@Data
public class BizBaseProfitCenterLgapDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Profit Center */

    private String profitCenterCode;

    /** Profit Center Name */

    private String profitCenterName;

    /** Status */

    private String status;


}
