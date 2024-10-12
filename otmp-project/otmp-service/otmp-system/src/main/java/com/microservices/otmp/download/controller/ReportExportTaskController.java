package com.microservices.otmp.download.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.download.domain.ReportExportTask;
import com.microservices.otmp.download.service.ReportExportTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 异步下载任务 提供者
 * 
 * @author sdms
 * @date 2022-02-11
 */
@Api("download")
@RestController
@RequestMapping("reportExportTask")
public class ReportExportTaskController extends BaseController
{
	
	@Autowired
	private ReportExportTaskService reportExportTaskService;
	
	/**
	 * 查询${tableComment}
	 */
	@ApiOperation(value = "查询邮件模板详情")
	@GetMapping("get/{id}")
	public ReportExportTask get(@PathVariable("id") Integer id)
	{
		return reportExportTaskService.selectReportExportTaskById(id);
		
	}
	
	/**
	 * 查询异步下载任务列表
	 */
	@ApiOperation(value = "list")
	@GetMapping("list")
	public TableDataInfo list(ReportExportTask reportExportTask)
	{
		startPage();
        return getDataTable(reportExportTaskService.selectReportExportTaskList(reportExportTask));
	}
	
	
	/**
	 * 新增保存异步下载任务
	 */
	@ApiOperation(value = "save")
	@PostMapping("save")
	public ResultDTO<Integer> addSave(@RequestBody(required = false) ReportExportTask reportExportTask)
	{
		return ResultDTO.success(reportExportTaskService.insertReportExportTask(reportExportTask));
	}

	/**
	 * 修改保存异步下载任务
	 */
	@PostMapping("update")
	public ResultDTO<Integer> editSave(@RequestBody ReportExportTask reportExportTask)
	{		
		return ResultDTO.success(reportExportTaskService.updateReportExportTask(reportExportTask));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public ResultDTO<Integer>remove(String ids)
	{		
		return ResultDTO.success(reportExportTaskService.deleteReportExportTaskByIds(ids));
	}
	
}
