package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.service.ISysUserDataScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * SysUserDataScopeController
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/sysUserDataScope")
public class SysUserDataScopeController extends BaseController
{
    @Autowired
    private ISysUserDataScopeService sysUserDataScopeService;

    /**
     * 查询SysUserDataScope列表
     */
    @RequiresPermissions("system:sysUserDataScope:list")
    @GetMapping("/list")
    public TableDataInfo list(SysUserDataScope sysUserDataScope)
    {
        startPage();
        return getDataTable(sysUserDataScopeService.selectSysUserDataScopePage(sysUserDataScope));
    }

    /**
     * 导出SysUserDataScope列表
     */
    @RequiresPermissions("system:sysUserDataScope:export")
    @OperLog(title = "SysUserDataScope", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUserDataScope sysUserDataScope)
    {
        List<SysUserDataScope> list = sysUserDataScopeService.selectSysUserDataScopeList(sysUserDataScope);
        NewExcelUtil<SysUserDataScope> util = new NewExcelUtil<>(SysUserDataScope.class);
        util.exportExcel(response, list, "SysUserDataScope数据");
    }

    /**
     * 获取SysUserDataScope详细信息
     */
    @RequiresPermissions("system:sysUserDataScope:query")
    @GetMapping(value = "/{userDataScopeId}")
    public ResultDTO<SysUserDataScope> getInfo(@PathVariable("userDataScopeId") Long userDataScopeId)
    {
        return ResultDTO.success(sysUserDataScopeService.selectSysUserDataScopeByUserDataScopeId(userDataScopeId));
    }

    /**
     * 新增SysUserDataScope
     */
    @RequiresPermissions("system:sysUserDataScope:add")
    @OperLog(title = "SysUserDataScope", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody SysUserDataScope sysUserDataScope)
    {
        return ResultDTO.success(sysUserDataScopeService.insertSysUserDataScope(sysUserDataScope));
    }

    /**
     * 修改SysUserDataScope
     */
    @RequiresPermissions("system:sysUserDataScope:edit")
    @OperLog(title = "SysUserDataScope", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody SysUserDataScope sysUserDataScope)
    {
        return ResultDTO.success(sysUserDataScopeService.updateSysUserDataScope(sysUserDataScope));
    }

    /**
     * 删除SysUserDataScope
     */
    @RequiresPermissions("system:sysUserDataScope:remove")
    @OperLog(title = "SysUserDataScope", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userDataScopeIds}")
    public ResultDTO<Integer> remove(@PathVariable Long[] userDataScopeIds)
    {
        return ResultDTO.success(sysUserDataScopeService.deleteSysUserDataScopeByUserDataScopeIds(userDataScopeIds));
    }

    /**
     * 获取用户数据权限
     * @param userId
     * @return
     */
    @GetMapping("getDataScopeByUserId")
    public TableDataInfo getDataScopeByUserId(@RequestParam("userId") Integer userId) {
        startPage();
        return getDataTable(sysUserDataScopeService.getDataScopeByUserId(userId));
    }
}
