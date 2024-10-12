package com.microservices.otmp.notice.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * Email Receipent对象 email_receipent
 * 
 * @author lovefamily
 * @date 2022-11-10
 */
@Data
public class EmailReceipentDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Business Group */

    private String businessGroup;

    /** Geo */

    private String geoCode;

    /** Job Type */

    private String jobType;

    /** Email Receipent */

    private String emailReceipent;

    /** Delete Flag */

    private Boolean deleteFlag;

    private String remark;
}
