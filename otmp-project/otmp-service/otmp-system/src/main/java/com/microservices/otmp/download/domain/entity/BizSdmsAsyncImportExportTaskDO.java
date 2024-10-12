package com.microservices.otmp.download.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

import java.util.Date;

/**
 * Async Import Export Task对象 biz_sdms_async_import_export_task
 *
 * @author lovefamily
 * @date 2023-02-16
 */
@Data
public class BizSdmsAsyncImportExportTaskDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * Task ID
     */

    private Long taskId;

    /**
     * Task Name
     */

    private String taskName;

    /**
     * System Module
     */

    private String systemModule;

    /**
     * User It Code
     */

    private String userItCode;

    /**
     * Priority
     */

    private Integer priority;

    /**
     * Request Param
     */

    private String requestParam;

    /**
     * Action Handle
     */

    private String actionHandle;

    /**
     * Task Status
     */

    private String status;

    /**
     * Deal Time
     */

    private Date dealTime;

    /**
     * Finish Time
     */

    private Date finishTime;

    /**
     * Result
     */

    private String result;

    /**
     * File Path
     */

    private String filePath;

    /**
     * Task Code
     */

    private String taskCode;

    /**
     * Task Type
     */

    private Integer taskType;

    /**
     * Expect Deal Time
     */

    private Date expectDealTime;

    /**
     * Call Back Handle
     */

    private String callBackHandle;


    /**
     * File Md5
     */

    private String fileMd5;

    /**
     * File Name
     */
    private String fileName;


    private Date lastActiveTime;
}
