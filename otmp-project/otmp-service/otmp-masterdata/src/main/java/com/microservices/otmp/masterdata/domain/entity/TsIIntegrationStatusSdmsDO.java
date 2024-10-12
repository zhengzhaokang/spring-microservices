package com.microservices.otmp.masterdata.domain.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.otmp.common.core.domain.BaseDO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * TsIIntegrationStatusSdms对象 ts_i_integration_status_sdms
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
@Data
public class TsIIntegrationStatusSdmsDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */

    private Date versionDate;

    /** $column.columnComment */

    private String targetTable;

    /** $column.columnComment */

    private Integer integrationType;

    /** $column.columnComment */

    private Date startTime;

    /** $column.columnComment */

    private Date finishTime;

    /** $column.columnComment */

    private Integer status;

    /** $column.columnComment */

    private Integer interrupt;

    /** $column.columnComment */

    private Date interruptTime;


}
