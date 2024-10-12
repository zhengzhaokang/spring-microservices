package com.microservices.otmp.system.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * BizSdmsSystemAnnouncement对象 biz_sdms_system_announcement
 * 
 * @author lovefamily
 * @date 2023-02-28
 */
@Data
public class BizSdmsSystemAnnouncementDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** Category */

    private String category;

    /** Priority */

    private String priority;

    /** Title */

    private String title;

    /** Detail */

    private String detail;

    /** Business Group */

    private String businessGroup;

    /** Geo */

    private String geoCode;

    /** Status */

    private String status ;

    /** Delete Flag */
    private Integer delFlag;


}
