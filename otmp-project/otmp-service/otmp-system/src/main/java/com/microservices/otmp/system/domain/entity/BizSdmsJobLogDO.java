package com.microservices.otmp.system.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * jobMonitor对象 biz_sdms_job_log
 * 
 * @author dhc
 * @date 2022-10-11
 */
@Data
public class BizSdmsJobLogDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    /** geo */

    private String geo;

    /** 执行时间 */

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private Date executionTime;

    /** 开始时间 */

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private Date jobStartTime;

    /** 结束时间 */
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private Date jobEndTime;

    /** 任务类型 */

    private String jobType;

    /** 任务状态 */

    private String status;

    /** 成功条数 */

    private Integer successCount;

    /** 失败条数 */

    private Integer failCount;

    /** 年度 */

    private String poolFy;

    /** 季度 */

    private String poolQ;

    /** 模块 */

    private String modules;

    /** $column.columnComment */

    private String logId;


}
