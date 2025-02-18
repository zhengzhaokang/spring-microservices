package com.microservices.otmp.system.service;

import com.microservices.otmp.common.core.domain.Ztree;
import com.microservices.otmp.system.domain.SysMenu;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.dto.SysMenuImportDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 菜单 业务层
 *
 * @author lovefamily
 */
public interface ISysMenuService {
    /**
     * 根据用户ID查询菜单
     *
     * @param user 用户信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenusByUser(SysUser user);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(SysMenu menu);

    /**
     * 查询系统菜单列表
     *
     * @param menu   菜单信息
     * @param userId 用户id
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId);


    /**
     * 查询菜单集合
     *
     * @return 所有菜单信息
     */
    public List<SysMenu> selectMenuAll();

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectPermsByUserId(Long userId);


    /**
     * 根据角色ID查询菜单ID
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    public List<SysMenu> selectMenuIdsByRoleId(Long roleId);

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    public List<Ztree> roleMenuTreeData(SysRole role);

    /**
     * 查询所有菜单信息
     *
     * @return 菜单列表
     */
    public List<Ztree> menuTreeData();

    /**
     * 查询系统所有权限
     *
     * @return 权限列表
     */
    public Map<String, String> selectPermsAll();

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int deleteMenuById(Long menuId);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    public SysMenu selectMenuById(Long menuId);
    public SysMenu selectMenuByMenuId(Long menuId);

    /**
     * 查询菜单数量
     *
     * @param parentId 菜单父ID
     * @return 结果
     */
    public int selectCountMenuByParentId(Long parentId);

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int selectCountRoleMenuByMenuId(Long menuId);

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int insertMenu(SysMenu menu);

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int updateMenu(SysMenu menu);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public String checkMenuNameUnique(SysMenu menu);


    public List<SysMenu> getChildPerms(Long parentId);

    List<SysMenuImportDTO> exportMenuList(SysMenuImportDTO sysMenu);

   void  importExcel(List<SysMenuImportDTO> sysMenus, String loginName);
}
