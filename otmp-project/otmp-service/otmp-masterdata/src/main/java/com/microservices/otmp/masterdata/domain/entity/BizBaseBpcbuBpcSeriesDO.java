package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * BizBaseBpcbuBpcSeries In SDMS对象 biz_base_bpcbu_bpc_series
 * 
 * @author lovefamily
 * @date 2022-10-31
 */
@Data
public class BizBaseBpcbuBpcSeriesDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;


    /** $column.columnComment */

    private String bpcBuCode;


    /** $column.columnComment */

    private String bpcSeriesCode;


    /** $column.columnComment */

    private String status;

    /** $column.columnComment */

    private String businessGroup;

    /** $column.columnComment */

    private String bpcBuDescription;

    /** $column.columnComment */

    private String bpcSeriesDescription;

    /** $column.columnComment */

    private String endMonth;


}
