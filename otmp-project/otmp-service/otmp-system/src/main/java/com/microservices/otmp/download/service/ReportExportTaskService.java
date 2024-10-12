package com.microservices.otmp.download.service;

import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.download.domain.ReportExportTask;
import com.microservices.otmp.download.mapper.ReportExportTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 异步下载任务Service业务层处理
 * 
 * @author sdms
 * @date 2022-02-11
 */
@Service
public class ReportExportTaskService
{
    @Autowired
    private ReportExportTaskMapper reportExportTaskMapper;

    /**
     * 查询异步下载任务
     * 
     * @param id 异步下载任务ID
     * @return 异步下载任务
     */
    
    public ReportExportTask selectReportExportTaskById(Integer id)
    {
        return reportExportTaskMapper.selectReportExportTaskById(id);
    }

    /**
     * 查询异步下载任务列表
     * 
     * @param reportExportTask 异步下载任务
     * @return 异步下载任务
     */
    
    public List<ReportExportTask> selectReportExportTaskList(ReportExportTask reportExportTask)
    {
        return reportExportTaskMapper.selectReportExportTaskList(reportExportTask);
    }

    /**
     * 新增异步下载任务
     * 
     * @param reportExportTask 异步下载任务
     * @return 结果
     */
    
    public int insertReportExportTask(ReportExportTask reportExportTask)
    {
        return reportExportTaskMapper.insertReportExportTask(reportExportTask);
    }

    /**
     * 修改异步下载任务
     * 
     * @param reportExportTask 异步下载任务
     * @return 结果
     */
    
    public int updateReportExportTask(ReportExportTask reportExportTask)
    {
        return reportExportTaskMapper.updateReportExportTask(reportExportTask);
    }

    /**
     * 删除异步下载任务对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    
    public int deleteReportExportTaskByIds(String ids)
    {
        return reportExportTaskMapper.deleteReportExportTaskByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除异步下载任务信息
     * 
     * @param id 异步下载任务ID
     * @return 结果
     */
    
    public int deleteReportExportTaskById(Integer id)
    {
        return reportExportTaskMapper.deleteReportExportTaskById(id);
    }
}
