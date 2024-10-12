package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.SysMenu;
import com.microservices.otmp.system.domain.dto.SysMenuImportDTO;
import com.microservices.otmp.system.domain.entity.SysMenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:20
 */
public interface SysMenuManager {
    /**
     * 查询系统所有菜单（含按钮）
     *
     * @return 菜单列表
     */
    public List<SysMenuDO> selectMenuAll();

    /**
     * 查询系统正常显示菜单（不含按钮）
     *
     * @return 菜单列表
     */
    public List<SysMenuDO> selectMenuNormalAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenuDO> selectMenusByUserId(Long userId);

    public SysMenuDO selectMenuByMenuId(Long menuId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectPermsByUserId(Long userId);

    /**
     * 根据角色ID查询菜单
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    public List<SysMenuDO> selectMenuIdsByRoleId(Long roleId);

    /**
     * 根据角色ID查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    public List<String> selectMenuTree(Long roleId);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<SysMenuDO> selectMenuList(SysMenu menu);
    public List<SysMenuDO> selectMenus(SysMenu menu);

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
    public SysMenuDO selectMenuById(Long menuId);

    /**
     * 查询菜单数量
     *
     * @param parentId 菜单父ID
     * @return 结果
     */
    public int selectCountMenuByParentId(Long parentId);

    /**
     * 新增菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int insertMenu(SysMenuDO menu);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int updateMenu(SysMenuDO menu);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuName 菜单名称
     * @param parentId 父菜单ID
     * @return 结果
     */
    public SysMenuDO checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);

    List<SysMenuImportDTO> exportMenuList(SysMenuImportDTO sysMenu);

    Integer batchInsert(List<SysMenuDO> dos);

}
