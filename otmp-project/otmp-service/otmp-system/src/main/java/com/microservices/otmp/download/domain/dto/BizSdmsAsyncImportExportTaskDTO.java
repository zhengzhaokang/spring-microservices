package com.microservices.otmp.download.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * Async Import Export Task对象 biz_sdms_async_import_export_task
 *
 * @author lovefamily
 * @date 2023-02-16
 */
@Data
public class BizSdmsAsyncImportExportTaskDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * Task ID
     */
    @Excel(name = "Task ID")
    private Long taskId;

    /**
     * Task Name
     */
    @Excel(name = "Task Name")
    private String taskName;

    /**
     * System Module
     */
    @Excel(name = "System Module")
    private String systemModule;

    /**
     * User It Code
     */
    @Excel(name = "User It Code")
    private String userItCode;

    /**
     * Priority
     */
    @Excel(name = "Priority")
    private Integer priority;

    /**
     * Request Param
     */
    @Excel(name = "Request Param")
    private String requestParam;

    /**
     * Action Handle
     */
    @Excel(name = "Action Handle")
    private String actionHandle;

    /**
     * Task Status
     */
    @Excel(name = "Task Status")
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
    @Excel(name = "Result")
    private String result;

    /**
     * File Path
     */
    @Excel(name = "File Path")
    private String filePath;

    /**
     * Task Code
     */
    @Excel(name = "Task Code")
    private String taskCode;

    /** Task Type */
    /*** 1 上传任务 11 上传结果 2下载任务 22下载结果**/
    @Excel(name = "Task Type")
    private Integer taskType;

    /**
     * Expect Deal Time
     */

    private Date expectDealTime;

    /**
     * Call Back Handle
     */
    @Excel(name = "Call Back Handle")
    private String callBackHandle;


    /**
     * File Md5
     */
    @Excel(name = "File Md5")
    private String fileMd5;

    /**
     * File Name
     */
    @Excel(name = "File Name")
    private String fileName;

}
