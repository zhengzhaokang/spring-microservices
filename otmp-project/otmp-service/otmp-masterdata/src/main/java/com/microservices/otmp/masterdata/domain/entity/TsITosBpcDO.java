package com.microservices.otmp.masterdata.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * TsITosBpc对象 ts_i_tos_bpc
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
@Data
public class TsITosBpcDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */

    private String memberId;

    /** $column.columnComment */

    private String evDescription;

    /** $column.columnComment */

    private String parentH1;

    /** $column.columnComment */

    private String status;


}
