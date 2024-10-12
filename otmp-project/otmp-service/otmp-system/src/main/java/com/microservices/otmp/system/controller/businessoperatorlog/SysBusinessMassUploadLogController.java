package com.microservices.otmp.system.controller.businessoperatorlog;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.dto.SysBusinessMassUploadLogDTO;
import com.microservices.otmp.system.service.ISysBusinessMassUploadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * SysBusinessMassUploadLogController
 *
 * @author lovefamily
 * @date 2022-08-19
 */
@RestController
@RequestMapping("/sysBusinessMassUploadLog")
public class SysBusinessMassUploadLogController extends BaseController {
    @Autowired
    private ISysBusinessMassUploadLogService sysBusinessMassUploadLogService;

    /**
     * 查询SysBusinessMassUploadLog列表
     */
    @RequiresPermissions("system:sysBusinessMassUploadLog:list")
    @GetMapping("/list")
    public TableDataInfo list(SysBusinessMassUploadLogDTO sysBusinessMassUploadLog) {
        startPage();
        return getDataTable(sysBusinessMassUploadLogService.selectSysBusinessMassUploadLogList(sysBusinessMassUploadLog));
    }

    /**
     * 导出SysBusinessMassUploadLog列表
     */
    @RequiresPermissions("system:sysBusinessMassUploadLog:export")
    @Log(title = "SysBusinessMassUploadLog", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysBusinessMassUploadLogDTO sysBusinessMassUploadLog) {
        List<SysBusinessMassUploadLogDTO> list = sysBusinessMassUploadLogService.selectSysBusinessMassUploadLogList(sysBusinessMassUploadLog).getList();
        ExcelUtil<SysBusinessMassUploadLogDTO> util = new ExcelUtil<>(SysBusinessMassUploadLogDTO.class);
        util.exportExcel(response, list, "SysBusinessMassUploadLog数据");
    }

    /**
     * 获取SysBusinessMassUploadLog详细信息
     */
    @RequiresPermissions("system:sysBusinessMassUploadLog:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<SysBusinessMassUploadLogDTO> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(sysBusinessMassUploadLogService.selectSysBusinessMassUploadLogById(id));
    }

    /**
     * 新增SysBusinessMassUploadLog
     */
    @RequiresPermissions("system:sysBusinessMassUploadLog:add")
    @Log(title = "SysBusinessMassUploadLog", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public ResultDTO<Object> add(@RequestBody SysBusinessMassUploadLogDTO sysBusinessMassUploadLog) {
       int num= sysBusinessMassUploadLogService.insertSysBusinessMassUploadLog(sysBusinessMassUploadLog);
        return num > 0 ? ResultDTO.success(sysBusinessMassUploadLog.getId()) : ResultDTO.error();
    }

    /**
     * 修改SysBusinessMassUploadLog
     */
    @RequiresPermissions("system:sysBusinessMassUploadLog:edit")
    @Log(title = "SysBusinessMassUploadLog", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody SysBusinessMassUploadLogDTO sysBusinessMassUploadLog) {
        return toResultDTO(sysBusinessMassUploadLogService.updateSysBusinessMassUploadLog(sysBusinessMassUploadLog), true);
    }

    /**
     * 删除SysBusinessMassUploadLog
     */
    @RequiresPermissions("system:sysBusinessMassUploadLog:remove")
    @Log(title = "SysBusinessMassUploadLog", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(sysBusinessMassUploadLogService.deleteSysBusinessMassUploadLogByIds(ids), true);
    }
}
