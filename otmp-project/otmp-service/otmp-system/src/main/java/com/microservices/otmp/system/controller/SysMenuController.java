package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.annotation.LoginUser;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.SecurityUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.system.domain.SysMenu;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.dto.SysMenuImportDTO;
import com.microservices.otmp.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 菜单权限 
 * 
 * @Author lovefamily
 * @date 2019-05-20
 */
@RestController
@RequestMapping("menu")
public class SysMenuController extends BaseController
{
    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 查询菜单权限
     */
    @GetMapping("get/{menuId}")
    public SysMenu get(@PathVariable("menuId") Long menuId)
    {
        return sysMenuService.selectMenuById(menuId);
    }    /**
     * 查询菜单权限
     */
    @GetMapping("selectMenuByMenuId/{menuId}")
    public SysMenu selectMenuByMenuId(@PathVariable("menuId") Long menuId)
    {
        return sysMenuService.selectMenuByMenuId(menuId);
    }

    @GetMapping("perms/{userId}")
    public Set<String> perms(@PathVariable("userId") Long userId)
    {
        return sysMenuService.selectPermsByUserId(userId);
    }

    /**
     * 查询菜单权限
     */
    @GetMapping("user")
    public List<SysMenu> user(@LoginUser SysUser sysUser)
    {
        return sysMenuService.selectMenusByUser(sysUser);
    }

    /**
     * 根据角色编号查询菜单编号（用于勾选）
     * @param roleId
     * @return

     */
    @GetMapping("role/{roleId}")
    public List<SysMenu> role(@PathVariable("roleId") Long roleId)
    {
        if (null == roleId || roleId <= 0) return Collections.emptyList();
        return sysMenuService.selectMenuIdsByRoleId(roleId);
    }

    /**
     * 查询菜单权限列表
     */
    /*@HasPermissions("system:menu:view")*/
    @GetMapping("list")
    public TableDataInfo list(SysMenu sysMenu)
    {
        return getDataTable(sysMenuService.selectMenuList(sysMenu));
    }
    @HasPermissions("system:menu:export")
    @PostMapping("/export")
     @OperLog(title = "菜单管理", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, SysMenuImportDTO sysMenu)
    {
        List<SysMenuImportDTO> sysMenus = sysMenuService.exportMenuList(sysMenu);
        NewExcelUtil<SysMenuImportDTO> util = new NewExcelUtil<>(SysMenuImportDTO.class);
        util.exportExcel(response, sysMenus, "Menu");
    }

    @PostMapping("/importExcel")
    @HasPermissions("system:menu:import")
    @OperLog(title = "菜单管理", businessType = BusinessType.IMPORT)
    public ResultDTO<Object> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<SysMenuImportDTO> util = new ExcelUtil<>(SysMenuImportDTO.class);
        List <SysMenuImportDTO> sysMenus = util.importExcel(file.getInputStream());
        sysMenuService.importExcel(sysMenus,getLoginName());
        return ResultDTO.success();
    }


    /**
     * 根据用户查询菜单权限列表
     */
    @HasPermissions("system:menu:view")
    @GetMapping("/list4User")
    public ResultDTO<List<SysMenu>> list4User(SysMenu menu)
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = sysMenuService.selectMenuList(menu, userId);
        return ResultDTO.success(menus);
    }

    /**
     * 查询所有菜单权限列表
     */
    @HasPermissions("system:menu:view")
    @GetMapping("all")
    public TableDataInfo all(SysMenu sysMenu)
    {
        return getDataTable(sysMenuService.selectMenuList(sysMenu));
    }

    /**
     * 新增保存菜单权限
     */
    @PostMapping("save")
    @OperLog(title = "菜单管理", businessType = BusinessType.INSERT)
    public ResultDTO<Integer> addSave(@RequestBody SysMenu sysMenu)
    {
        return ResultDTO.success(sysMenuService.insertMenu(sysMenu));
    }

    /**
     * 修改保存菜单权限
     */
    @OperLog(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public ResultDTO<Integer> editSave(@RequestBody SysMenu sysMenu)
    {
        return ResultDTO.success(sysMenuService.updateMenu(sysMenu));
    }

    /**
     * 删除菜单权限
     */
    @OperLog(title = "菜单管理", businessType = BusinessType.DELETE)
    @PostMapping("remove/{menuId}")
    public ResultDTO<Integer> remove(@PathVariable("menuId") Long menuId)
    {
        return ResultDTO.success(sysMenuService.deleteMenuById(menuId));
    }

    @GetMapping("menuChildData")
    public ResultDTO<Object> menuChildData(Long parentId)
    {
        return ResultDTO.success(sysMenuService.getChildPerms(parentId));
    }

}
