package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.system.domain.SysKafkaLog;
import com.microservices.otmp.system.service.ISysKafkaLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志记录 提供者
 *
 * @Author lovefamily
 * @date 2019-05-20
 */
@RestController
@RequestMapping("kafkaLog")
public class SysKafkaLogController extends BaseController
{
    @Autowired
    private ISysKafkaLogService sysKafkaLogService;

    /**
     * 查询操作日志记录
     */
    @GetMapping("get/{operId}")
    public SysKafkaLog get(@PathVariable("operId") Long operId)
    {
        return sysKafkaLogService.selectKafkaLogById(operId);
    }

    /**
     * 查询操作日志记录列表
     */
    @HasPermissions("monitor:operlog:list")
    @RequestMapping("list")
    public TableDataInfo list(SysKafkaLog sysOperLog)
    {
        startPage();
        return getDataTable(sysKafkaLogService.selectKafkaLogPage(sysOperLog));
    }

    @OperLog(title = "操作日志", businessType = BusinessType.EXPORT)
    @HasPermissions("monitor:operlog:export")
    @PostMapping("/export")
    public ResultDTO<Object> export(@RequestBody SysKafkaLog operLog)
    {
        List<SysKafkaLog> list = sysKafkaLogService.selectKafkaLogList(operLog);
        ExcelUtil<SysKafkaLog> util = new ExcelUtil<>(SysKafkaLog.class);
        return ResultDTO.success(util.exportExcel(list, "操作日志"));
    }

    /**
     * 新增保存操作日志记录
     */
    @PostMapping("save")
    public void addSave(@RequestBody SysKafkaLog sysOperLog)
    {
        sysKafkaLogService.insertKafkalog(sysOperLog);
    }

    /**
     * 删除操作日志记录
     */
    @HasPermissions("monitor:operlog:remove")
    @OperLog(title = "操作日志", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public ResultDTO<Integer>  remove(String ids)
    {
        return ResultDTO.success(sysKafkaLogService.deleteKafkaLogByIds(ids));
    }

    @OperLog(title = "操作日志", businessType = BusinessType.CLEAN)
    @HasPermissions("monitor:operlog:remove")
    @PostMapping("/clean")
    public ResultDTO<Object> clean()
    {
        sysKafkaLogService.cleanKafkaLog();
        return ResultDTO.success();
    }
}
