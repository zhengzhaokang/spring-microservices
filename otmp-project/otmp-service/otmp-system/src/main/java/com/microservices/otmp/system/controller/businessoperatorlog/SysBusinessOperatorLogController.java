package com.microservices.otmp.system.controller.businessoperatorlog;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.dto.SysBusinessOperatorLogDTO;
import com.microservices.otmp.system.service.ISysBusinessOperatorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * SysBusinessOperatorLogController
 * 
 * @author lovefamily
 * @date 2022-08-18
 */
@RestController
@RequestMapping("/sysBusinessOperatorLog")
public class SysBusinessOperatorLogController extends BaseController
{
    @Autowired
    private ISysBusinessOperatorLogService sysBusinessOperatorLogService;

    /**
     * 查询SysBusinessOperatorLog列表
     */
    @RequiresPermissions("system:sysBusinessOperatorLog:list")
    @GetMapping("/list")
    public TableDataInfo list(SysBusinessOperatorLogDTO sysBusinessOperatorLog)
    {
        startPage();
        return getDataTable(sysBusinessOperatorLogService.selectSysBusinessOperatorLogList(sysBusinessOperatorLog));
    }

    /**
     * 导出SysBusinessOperatorLog列表
     */
    @RequiresPermissions("system:sysBusinessOperatorLog:export")
    @Log(title = "SysBusinessOperatorLog", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysBusinessOperatorLogDTO sysBusinessOperatorLog)
    {
        List<SysBusinessOperatorLogDTO> list = sysBusinessOperatorLogService.selectSysBusinessOperatorLogList(sysBusinessOperatorLog).getList();
        ExcelUtil<SysBusinessOperatorLogDTO> util = new ExcelUtil<>(SysBusinessOperatorLogDTO.class);
        util.exportExcel(response, list, "SysBusinessOperatorLog数据");
    }

    /**
     * 获取SysBusinessOperatorLog详细信息
     */
    @RequiresPermissions("system:sysBusinessOperatorLog:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<SysBusinessOperatorLogDTO> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(sysBusinessOperatorLogService.selectSysBusinessOperatorLogById(id));
    }

    /**
     * 新增SysBusinessOperatorLog
     */
    @RequiresPermissions("system:sysBusinessOperatorLog:add")
    @Log(title = "SysBusinessOperatorLog", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public ResultDTO<Integer> add(@RequestBody SysBusinessOperatorLogDTO sysBusinessOperatorLog)
    {
        return toResultDTO(sysBusinessOperatorLogService.insertSysBusinessOperatorLog(sysBusinessOperatorLog),true);
    }

    /**
     * 修改SysBusinessOperatorLog
     */
    @RequiresPermissions("system:sysBusinessOperatorLog:edit")
    @Log(title = "SysBusinessOperatorLog", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody SysBusinessOperatorLogDTO sysBusinessOperatorLog)
    {
        return toResultDTO(sysBusinessOperatorLogService.updateSysBusinessOperatorLog(sysBusinessOperatorLog),true);
    }

    /**
     * 删除SysBusinessOperatorLog
     */
    @RequiresPermissions("system:sysBusinessOperatorLog:remove")
    @Log(title = "SysBusinessOperatorLog", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(sysBusinessOperatorLogService.deleteSysBusinessOperatorLogByIds(ids),true);
    }
}
