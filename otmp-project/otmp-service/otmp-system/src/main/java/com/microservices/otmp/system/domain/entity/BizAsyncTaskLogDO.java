package com.microservices.otmp.system.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

import java.util.Date;

/**
 * 记录文件上传下载信息对象 biz_async_task_log
 *
 * @author lovefamily
 * @date 2022-10-04
 */
@Data
public class BizAsyncTaskLogDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * $column.columnComment
     */

    private String asyncCode;

    /**
     * 模块
     */

    private String module;

    /**
     * 业务类型
     */

    private String businessType;

    /**
     * -1异常，0进行中，1完成
     */

    private Integer status;

    /**
     * $column.columnComment
     */

    private Date startDate;

    /**
     * $column.columnComment
     */

    private Date endDate;

    /**
     * 进度条
     */

    private Double progress;

    /**
     * 存储地址
     */

    private String url;

    /**
     * 使用时间
     */

    private Double useTime;

    /**
     * 错误信息
     */

    private String errorInfo;

    /**
     * 任务类型
     */

    private String taskType;

    /**
     * 文件名称
     */

    private String fileName;


}
