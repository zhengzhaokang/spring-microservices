package com.microservices.otmp.download.mapper;

import com.microservices.otmp.download.domain.ReportExportTask;

import java.util.List;

/**
 * 异步下载任务Mapper接口
 * 
 * @author sdms
 * @date 2022-02-11
 */
public interface ReportExportTaskMapper 
{
    /**
     * 查询异步下载任务
     * 
     * @param id 异步下载任务ID
     * @return 异步下载任务
     */
    public ReportExportTask selectReportExportTaskById(Integer id);

    /**
     * 查询异步下载任务列表
     * 
     * @param reportExportTask 异步下载任务
     * @return 异步下载任务集合
     */
    public List<ReportExportTask> selectReportExportTaskList(ReportExportTask reportExportTask);

    /**
     * 新增异步下载任务
     * 
     * @param reportExportTask 异步下载任务
     * @return 结果
     */
    public int insertReportExportTask(ReportExportTask reportExportTask);

    /**
     * 修改异步下载任务
     * 
     * @param reportExportTask 异步下载任务
     * @return 结果
     */
    public int updateReportExportTask(ReportExportTask reportExportTask);

    /**
     * 删除异步下载任务
     * 
     * @param id 异步下载任务ID
     * @return 结果
     */
    public int deleteReportExportTaskById(Integer id);

    /**
     * 批量删除异步下载任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteReportExportTaskByIds(String[] ids);
}
