package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * salesOffice与profitMapping映射对象 biz_base_sales_office_profit_center_mapping
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@Data
public class BizBaseSalesOfficeProfitCenterMappingDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** $column.columnComment */

    private String salesOfficeCode;

    /** $column.columnComment */

    private String profitCenterCode;

    /** $column.columnComment */
    private Integer delFlag;

    private String salesOrgCode;


}
