package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.dto.SysUserRoleListDTO;
import com.microservices.otmp.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色 提供者
 *
 * @Author lovefamily
 * @date 2019-05-20
 */
@RestController
@RequestMapping("role")
public class SysRoleController extends BaseController {
    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 查询角色
     */
    @GetMapping("get/{roleId}")
    public SysRole get(@PathVariable("roleId") Long roleId) {
        return sysRoleService.selectRoleById(roleId);
    }

    /**
     * 查询角色列表（分页）
     */
    @HasPermissions("system:role:list")
    @GetMapping("list")
    public TableDataInfo list(SysRole sysRole) {
        startPage();
        return getDataTable(sysRoleService.selectRolePage(sysRole));
    }

    /**
     * 查询角色列表
     */
    @GetMapping("rolelist")
    public List<SysRole> rolelist(SysRole sysRole) {
        return sysRoleService.selectRoleList(sysRole);
    }

    @GetMapping("all")
    public TableDataInfo all() {
        return getDataTable( sysRoleService.selectRoleAll());
    }

    @GetMapping("allList")
    public List<SysRole> allList() {
        return sysRoleService.selectRoleAll();
    }

    /**
     * 新增保存角色
     */
    @HasPermissions("system:role:add")
    @PostMapping("save")
    @OperLog(title = "角色管理", businessType = BusinessType.INSERT)
    public ResultDTO<Integer> addSave(@RequestBody SysRole sysRole) {
        return ResultDTO.success(sysRoleService.insertRole(sysRole));
    }

    /**
     * 修改保存角色
     */
    @HasPermissions("system:role:edit")
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public ResultDTO<Integer>  editSave(@RequestBody SysRole sysRole) {
        return ResultDTO.success(sysRoleService.updateRole(sysRole));
    }

    /**
     * 修改保存角色
     */
    @HasPermissions("system:role:edit")
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("status")
    public ResultDTO<Integer>  status(@RequestBody SysRole sysRole) {
        return ResultDTO.success(sysRoleService.changeStatus(sysRole));
    }

    /**
     * 保存角色分配数据权限
     */
    @HasPermissions("system:role:edit")
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/authDataScope")
    public ResultDTO<Object> authDataScopeSave(@RequestBody SysRole role) {
        role.setUpdateBy(getLoginName());
        if (sysRoleService.authDataScope(role) > 0) {
            return ResultDTO.success();
        }
        return ResultDTO.fail();
    }

    /**
     * 删除角色
     *
     */
    @OperLog(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public ResultDTO<Integer>  remove(String ids) {
        return ResultDTO.success(sysRoleService.deleteRoleByIds(ids));
    }


    /**
     * User List
     */
    @GetMapping("userList")
    public TableDataInfo userList(SysUserRoleListDTO sysRole) {
        startPage();
        return getDataTable(sysRoleService.userList(sysRole));
    }

    /**
     * User List delete
     */
    @PostMapping("deleteSysRole")
    public ResultDTO<Integer> deleteSysRole(@RequestBody SysUserRoleListDTO sysRole) {
        return ResultDTO.success(sysRoleService.deleteSysRole(sysRole));
    }

    /**
     * User List add list
     */
    @RequestMapping("addSysRoleList")
    public TableDataInfo addSysRoleList(SysUserRoleListDTO sysRole) {
        startPage();
        return getDataTable(sysRoleService.selectAddSysRoleList(sysRole,getLoginName()));
    }

    /**
     * User List add
     */
    @PostMapping("addSysRole")
    public ResultDTO<Integer> addSysRole(@RequestBody SysUserRoleListDTO sysRole) {
        return ResultDTO.success(sysRoleService.addSysRole(sysRole,getLoginName()));
    }

}
