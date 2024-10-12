package com.microservices.otmp.system.domain.entity;

import com.microservices.otmp.system.domain.dto.DimensionDTO;
import lombok.Data;

import java.util.Date;

/**
 * SysBusinessMassUploadLog对象 sys_business_mass_upload_log
 * 
 * @author lovefamily
 * @date 2022-08-19
 */
@Data
public class SysBusinessMassUploadLogDO extends DimensionDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** File Name */

    private String fileName;

    /** Fail,Success */

    private String status;

    /** Record Count */

    private Integer recordCount;

    /** Error Count */

    private Integer errorCount;

    /** Operator */

    private String operator;

    /** Operation Date */

    private Date operatorDate;

    /** Url */

    private String url;

    /** Module Type */

    private String moduleType;

    private String geoCode;

    private String  accrualVersion;

    /**
     * Start Time
     */

    private Date startDateTime;

    /**
     * End Time
     */

    private Date endDateTime;



}
