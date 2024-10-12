package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * bwBuBgMapping对象 biz_base_bw_bu_bg_mapping
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@Data
public class BizBaseBwBuBgMappingDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */

    private String bwBu;

    /** $column.columnComment */

    private String businessGroup;

    /** $column.columnComment */

    private String profitCenterCode;

    /** $column.columnComment */

    private String validateFrom;

    /** $column.columnComment */

    private String validateTo;

    private int delFlag;


}
