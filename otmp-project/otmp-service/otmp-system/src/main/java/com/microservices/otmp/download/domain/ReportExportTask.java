package com.microservices.otmp.download.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;

import java.util.Date;

/**
 * 异步下载任务对象 report_export_task
 *
 * @author sdms
 * @date 2022-02-11
 */
public class ReportExportTask extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 报表编号
     */
    private String reportingId;

    /**
     * 操作用户的ITCode
     */
    @Excel(name = "操作用户的ITCode")
    private String itCode;

    /**
     * 报表名称
     */
    @Excel(name = "报表名称")
    private String reportName;

    /**
     * 请求类型（0视图/表；1存储过程）
     */
    @Excel(name = "请求类型", readConverterExp = "0=视图/表；1存储过程")
    private String objectType;

    /**
     * 请求对象名称（视图名、表名、存储过程名、……）
     */
    @Excel(name = "请求对象名称", readConverterExp = "视=图名、表名、存储过程名、……")
    private String objectName;

    /**
     * 查询条件
     */
    @Excel(name = "查询条件")
    private String condition;

    /**
     * 参数信息集合
     */
    @Excel(name = "参数信息集合")
    private String parameters;

    /**
     * 处理结果数据规则
     */
    @Excel(name = "处理结果数据规则")
    private String rules;

    /**
     * 请求时间
     */
    @Excel(name = "请求时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date requestTime;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    private String reserve;

    /**
     * 报表生成状态（0等待，1进行中，2已完成）
     */
    @Excel(name = "报表生成状态", readConverterExp = "0=等待，1进行中，2已完成")
    private String status;

    /**
     * 执行开始时间
     */
    @Excel(name = "执行开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 执行结束时间
     */
    @Excel(name = "执行结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTime;

    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String filePath;

    /**
     * 是否中断
     */
    @Excel(name = "是否中断")
    private String interrupt;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setReportingId(String reportingId) {
        this.reportingId = reportingId;
    }

    public String getReportingId() {
        return reportingId;
    }

    public void setItCode(String itCode) {
        this.itCode = itCode;
    }

    public String getItCode() {
        return itCode;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportName() {
        return reportName;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getParameters() {
        return parameters;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getRules() {
        return rules;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    public String getReserve() {
        return reserve;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setInterrupt(String interrupt) {
        this.interrupt = interrupt;
    }

    public String getInterrupt() {
        return interrupt;
    }

}
