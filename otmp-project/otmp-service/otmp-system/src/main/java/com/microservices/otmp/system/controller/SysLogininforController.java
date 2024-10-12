package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.system.domain.SysLogininfor;
import com.microservices.otmp.system.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统访问记录 提供者
 * 
 * @Author lovefamily
 * @date 2019-05-20
 */
@RestController
@RequestMapping("logininfor")
public class SysLogininforController extends BaseController
{
    @Autowired
    private ISysLogininforService sysLogininforService;

    /**
     * 查询系统访问记录列表
     */
    @HasPermissions("monitor:logininfor:list")
    @GetMapping("list")
    public TableDataInfo list(SysLogininfor sysLogininfor)
    {
        startPage();
        return getDataTable(sysLogininforService.selectLogininforPage(sysLogininfor));
    }

    /**
     * 新增保存系统访问记录
     */
    @PostMapping("save")
    public void addSave(@RequestBody SysLogininfor sysLogininfor)
    {
        sysLogininforService.insertLogininfor(sysLogininfor);
    }

    
    /**
     * 删除系统访问记录
     */
    @OperLog(title = "访问日志", businessType = BusinessType.DELETE)
    @HasPermissions("monitor:logininfor:remove")
    @PostMapping("remove")
    public ResultDTO<Integer> remove(String ids)
    {
        return ResultDTO.success(sysLogininforService.deleteLogininforByIds(ids));
    }

    @OperLog(title = "访问日志", businessType = BusinessType.CLEAN)
    @HasPermissions("monitor:logininfor:remove")
    @PostMapping("/clean")
    public ResultDTO<Object> clean()
    {
        sysLogininforService.cleanLogininfor();
        return ResultDTO.success();
    }
    
}
