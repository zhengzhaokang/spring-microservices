package com.microservices.otmp.system.service.impl;

import com.google.common.collect.Lists;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.domain.Ztree;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.annotation.RedisCache;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysMenu;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.dto.SysMenuImportDTO;
import com.microservices.otmp.system.domain.entity.SysMenuDO;
import com.microservices.otmp.system.enums.DeleteFlagEnum;
import com.microservices.otmp.system.enums.MenuTypeEnum;
import com.microservices.otmp.system.enums.VisibleEnum;
import com.microservices.otmp.system.manager.SysMenuManager;
import com.microservices.otmp.system.manager.SysRoleMenuManager;
import com.microservices.otmp.system.service.ISysMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单 业务层处理
 *
 * @author lovefamily
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";
    public static final String ROW = "Row ";


    @Autowired
    private SysMenuManager sysMenuManager;

    @Autowired
    private SysRoleMenuManager sysRoleMenuManager;

    /**
     * 根据用户查询菜单
     *
     * @param user 用户信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenusByUser(SysUser user) {
        List<SysMenuDO> menus;
        // 管理员显示所有菜单信息
        if (user.isAdmin()) {
            menus = sysMenuManager.selectMenuNormalAll();
        } else {
            menus = sysMenuManager.selectMenusByUserId(user.getUserId());
        }
        List<SysMenu> menuList = new ArrayList<>(menus.size());
        BeanUtils.copyListProperties(menus, menuList, SysMenu.class);
        return menuList;

    }

    /**
     * 查询菜单集合
     *
     * @return 所有菜单信息
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu) {
        List<SysMenuDO> sysMenuDOS = sysMenuManager.selectMenuList(menu);
        List<SysMenu> sysMenus = new ArrayList<>(sysMenuDOS.size());
        BeanUtils.copyListProperties(sysMenuDOS, sysMenus, SysMenu.class);
        return sysMenus;
    }

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        List<SysMenuDO> menuDOList;
        // 管理员显示所有菜单信息
        if (SysUser.isAdmin(userId)) {
            menuDOList = sysMenuManager.selectMenuList(menu);
        } else {
            menuDOList = sysMenuManager.selectMenusByUserId(userId);
        }

        List<SysMenu> menuList = new ArrayList<>(menuDOList.size());
        BeanUtils.copyListProperties(menuDOList, menuList, SysMenu.class);
        return menuList;
    }


    /**
     * 查询菜单集合
     *
     * @return 所有菜单信息
     */
    @Override
    public List<SysMenu> selectMenuAll() {
        List<SysMenuDO> menuDOList = sysMenuManager.selectMenuAll();
        List<SysMenu> menuList = new ArrayList<>(menuDOList.size());
        BeanUtils.copyListProperties(menuDOList, menuList, SysMenu.class);
        return menuList;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    @RedisCache(key = "user_perms", fieldKey = "#userId")
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = sysMenuManager.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<SysMenu> selectMenuIdsByRoleId(Long roleId) {
        List<SysMenuDO> menuDOList = sysMenuManager.selectMenuIdsByRoleId(roleId);
        List<SysMenu> menuList = new ArrayList<>(menuDOList.size());
        BeanUtils.copyListProperties(menuDOList, menuList, SysMenu.class);
        return menuList;
    }

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    @Override
    public List<Ztree> roleMenuTreeData(SysRole role) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees;
        List<SysMenuDO> menuList = sysMenuManager.selectMenuAll();
        if (StringUtils.isNotNull(roleId)) {
            List<String> roleMenuList = sysMenuManager.selectMenuTree(roleId);
            ztrees = initZtree(menuList, roleMenuList, true);
        } else {
            ztrees = initZtree(menuList, null, true);
        }
        return ztrees;
    }

    /**
     * 查询所有菜单
     *
     * @return 菜单列表
     */
    @Override
    public List<Ztree> menuTreeData() {
        List<SysMenuDO> menuList = sysMenuManager.selectMenuAll();
        return initZtree(menuList);
    }

    /**
     * 查询系统所有权限
     *
     * @return 权限列表
     */
    @Override
    public LinkedHashMap<String, String> selectPermsAll() {
        LinkedHashMap<String, String> section = new LinkedHashMap<>();
        List<SysMenuDO> permissions = sysMenuManager.selectMenuAll();
        if (StringUtils.isNotEmpty(permissions)) {
            for (SysMenuDO menu : permissions) {
                section.put(menu.getMenuName(), MessageFormat.format(PREMISSION_STRING, menu.getPerms()));
            }
        }
        return section;
    }

    /**
     * 对象转菜单树
     *
     * @param menuList 菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysMenuDO> menuList) {
        return initZtree(menuList, null, false);
    }

    /**
     * 对象转菜单树
     *
     * @param menuList     菜单列表
     * @param roleMenuList 角色已存在菜单列表
     * @param permsFlag    是否需要显示权限标识
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysMenuDO> menuList, List<String> roleMenuList, boolean permsFlag) {
        List<Ztree> ztrees = new ArrayList<>();
        boolean isCheck = StringUtils.isNotNull(roleMenuList);
        for (SysMenuDO menu : menuList) {
            Ztree ztree = new Ztree();
            ztree.setId(menu.getMenuId());
            ztree.setpId(menu.getParentId());
            ztree.setName(transMenuName(menu, permsFlag));
            ztree.setTitle(menu.getMenuName());
            if (isCheck) {
                ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }

    public String transMenuName(SysMenuDO menu, boolean permsFlag) {
        StringBuilder sb = new StringBuilder();
        sb.append(menu.getMenuName());
        if (permsFlag) {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
        }
        return sb.toString();
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId) {
        return sysMenuManager.deleteMenuById(menuId);
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu selectMenuById(Long menuId) {
        SysMenuDO sysMenuDO = sysMenuManager.selectMenuById(menuId);
        SysMenu sysMenu = new SysMenu();
        if (null != sysMenuDO) {
            org.springframework.beans.BeanUtils.copyProperties(sysMenuDO, sysMenu);
            return sysMenu;
        } else {
            return null;
        }
    }

    @Override
    public SysMenu selectMenuByMenuId(Long menuId) {
        SysMenuDO sysMenuDO = sysMenuManager.selectMenuByMenuId(menuId);
        if (null == sysMenuDO) {
            return null;
        }
        SysMenu sysMenu = new SysMenu();
        org.springframework.beans.BeanUtils.copyProperties(sysMenuDO, sysMenu);
        return sysMenu;
    }

    /**
     * 查询子菜单数量
     *
     * @param parentId 父级菜单ID
     * @return 结果
     */
    @Override
    public int selectCountMenuByParentId(Long parentId) {
        return sysMenuManager.selectCountMenuByParentId(parentId);
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int selectCountRoleMenuByMenuId(Long menuId) {
        return sysRoleMenuManager.selectCountRoleMenuByMenuId(menuId);
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(SysMenu menu) {
        SysMenuDO sysMenuDO = new SysMenuDO();
        org.springframework.beans.BeanUtils.copyProperties(menu, sysMenuDO);
        return sysMenuManager.insertMenu(sysMenuDO);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu) {
        SysMenuDO sysMenuDO = new SysMenuDO();
        org.springframework.beans.BeanUtils.copyProperties(menu, sysMenuDO);
        return sysMenuManager.updateMenu(sysMenuDO);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        SysMenuDO info = sysMenuManager.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
            return UserConstants.MENU_NAME_NOT_UNIQUE;
        }
        return UserConstants.MENU_NAME_UNIQUE;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(Long parentId) {

        return getChildList(parentId);
    }

    @Override
    public List<SysMenuImportDTO> exportMenuList(SysMenuImportDTO sysMenu) {
        return sysMenuManager.exportMenuList(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExcel(List<SysMenuImportDTO> sysMenus, String loginName) {
        SysMenuImportDTO sysMenu = new SysMenuImportDTO();
        List<SysMenuImportDTO> sysMenusdbList = sysMenuManager.exportMenuList(sysMenu);
        List<String> menuTypeList = Arrays.asList(MenuTypeEnum.M.getCode(), MenuTypeEnum.C.getCode(), MenuTypeEnum.F.getCode());
        List<String> visibleList = Arrays.asList(VisibleEnum.DISPLAY.getCode(), VisibleEnum.HIDE.getCode());
        List<String> deleteFlagList = Arrays.asList(DeleteFlagEnum.DELETE.getCode(), DeleteFlagEnum.NOT_DELETE.getCode());
        List<String> sysMenuNameList = sysMenusdbList.stream().map(SysMenuImportDTO::getMenuName).collect(Collectors.toList());
        Map<Long, SysMenuImportDTO> groupByMenuId = sysMenusdbList.stream().collect(Collectors.toMap(SysMenuImportDTO::getMenuId, Function.identity(), (e1, e2) -> e1));
        List<String> menuIdErrorList = Lists.newArrayList();
        List<String> parentNameErrorList = Lists.newArrayList();
        List<String> menuTypeErrorList = Lists.newArrayList();
        List<String> visibleErrorList = Lists.newArrayList();
        List<String> delectionErrorList = Lists.newArrayList();
        List<String> missValueErrorList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(sysMenus)) {
            throw new ServiceException("导入SysMenu 数据不能为空！");
        }
        for (int i = 0; i < sysMenus.size(); i++) {
            if (null != sysMenus.get(i).getMenuId()) {
                updateCheck(sysMenus, menuTypeList, visibleList, sysMenuNameList, groupByMenuId, menuIdErrorList, parentNameErrorList, menuTypeErrorList, visibleErrorList, delectionErrorList, i, deleteFlagList);
            } else {
                insertCheck(sysMenus, menuTypeList, visibleList, parentNameErrorList, menuTypeErrorList, visibleErrorList, missValueErrorList, i);

            }
        }
        StringBuilder result = getErrorInfo(menuIdErrorList, parentNameErrorList, menuTypeErrorList, visibleErrorList, delectionErrorList, missValueErrorList);

        if (org.apache.commons.lang3.StringUtils.isNotBlank(result.toString())) {
            throw new OtmpException(result.toString());
        }
        List<SysMenuImportDTO> insertList = Lists.newArrayList();

        for (int i = 0; i < sysMenus.size(); i++) {
            if (null != sysMenus.get(i).getMenuId()) {
                updateMenu(sysMenus, groupByMenuId, i);
            } else {
                insertList.add(sysMenus.get(i));
            }
        }
        if (CollectionUtils.isNotEmpty(insertList)) {
            addMenuTree(insertList, sysMenusdbList);
        }

    }

    private void updateMenu(List<SysMenuImportDTO> sysMenus, Map<Long, SysMenuImportDTO> groupByMenuId, int i) {
        SysMenuImportDTO menuDb = groupByMenuId.get(sysMenus.get(i).getMenuId());
        if (org.apache.commons.lang3.StringUtils.isNotBlank(sysMenus.get(i).getParentName()) && !menuDb.getParentName().equals(sysMenus.get(i).getParentName())) {
            SysMenuDO sysMenuDO = new SysMenuDO();
            sysMenuDO.setMenuId(menuDb.getParentId());
            sysMenuDO.setMenuName(sysMenus.get(i).getParentName());
            //修改主菜单
            sysMenuManager.updateMenu(sysMenuDO);
        }
        SysMenuDO sysMenuDO = new SysMenuDO();
        SysMenuImportDTO sysMenu1 = sysMenus.get(i);
        org.springframework.beans.BeanUtils.copyProperties(sysMenu1, sysMenuDO);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(sysMenus.get(i).getDeleteFlag()) && sysMenus.get(i).getDeleteFlag().equals(DeleteFlagEnum.DELETE.getName())) {
            sysMenuDO.setDeleteFlag(DeleteFlagEnum.DELETE.getCode());
        }
        //修改菜单内容  添加删除字段  查询要过滤掉删除的菜单
        sysMenuManager.updateMenu(sysMenuDO);
    }


    @SuppressWarnings("java:S3776")
    private void addMenuTree(List<SysMenuImportDTO> insertList, List<SysMenuImportDTO> sysMenusdbList) {
        Map<Long, List<SysMenuImportDTO>> groupByParentId = insertList.stream().collect(Collectors.groupingBy(SysMenuImportDTO::getParentId));
        //获取所有待添加的菜单名，用来判断是否需要多次添加
        List<Long> menuIdList = sysMenusdbList.stream().map(SysMenuImportDTO::getMenuId).collect(Collectors.toList());

        for (Map.Entry<Long, List<SysMenuImportDTO>> entry : groupByParentId.entrySet()) {
            Long k = entry.getKey();
            List<SysMenuImportDTO> v = entry.getValue();
            //添加父级目录，不重复添加
            if (menuIdList.contains(k) || k == 0L) {
                //目录存在直接添加
                if (!k.equals(0L)) {
                    //菜单集合
                    Map<String, List<SysMenuImportDTO>> menuGroupByParentName = getImportMenuMap(v);
                    //按钮集合
                    Map<String, List<SysMenuImportDTO>> buttonGroupByParentName = getImportButtonMap(v);

                    v.forEach(menu -> addMenu(menuGroupByParentName, buttonGroupByParentName, menu, k));
                } else {
                    List<SysMenuImportDTO> catalogList = v.stream().filter(p -> p.getMenuType().equals(MenuTypeEnum.M.getCode())).collect(Collectors.toList());
                    //菜单集合
                    Map<String, List<SysMenuImportDTO>> menuGroupByParentName = getImportMenuMap(v);
                    //按钮集合
                    Map<String, List<SysMenuImportDTO>> buttonGroupByParentName = getImportButtonMap(v);
                    //目录
                    catalogList.forEach(cata -> {
                        SysMenuDO existSysMenuDO = new SysMenuDO();
                        org.springframework.beans.BeanUtils.copyProperties(cata, existSysMenuDO);
                        sysMenuManager.insertMenu(existSysMenuDO);
                        Long menuId = existSysMenuDO.getMenuId();
                        if (menuGroupByParentName.containsKey(cata.getMenuName())) {
                            menuGroupByParentName.get(cata.getMenuName()).forEach(menu -> {
                                SysMenuDO menuDO = new SysMenuDO();
                                org.springframework.beans.BeanUtils.copyProperties(menu, menuDO);
                                menuDO.setParentId(menuId);
                                sysMenuManager.insertMenu(menuDO);
                                Long menuDOMenuId = menuDO.getMenuId();
                                if (buttonGroupByParentName.containsKey(menu.getMenuName())) {
                                    buttonGroupByParentName.get(menu.getMenuName()).forEach(button -> {
                                        SysMenuDO buttonDO = new SysMenuDO();
                                        org.springframework.beans.BeanUtils.copyProperties(button, buttonDO);
                                        buttonDO.setParentId(menuDOMenuId);
                                        sysMenuManager.insertMenu(buttonDO);
                                    });
                                }
                            });
                        }
                    });
                }
            }

        }
    }

    private void addMenu(Map<String, List<SysMenuImportDTO>> menuGroupByParentName, Map<String, List<SysMenuImportDTO>> buttonGroupByParentName, SysMenuImportDTO cata, Long menuId) {
        //菜单
        if (menuGroupByParentName.containsKey(cata.getMenuName()) || menuGroupByParentName.containsKey(cata.getParentName())) {
            SysMenuDO menuDO = new SysMenuDO();
            menuDO.setParentId(menuId);
            org.springframework.beans.BeanUtils.copyProperties(cata, menuDO);
            sysMenuManager.insertMenu(menuDO);
            Long menuDOMenuId = menuDO.getMenuId();
            addButton(buttonGroupByParentName, cata, menuDOMenuId);
        } else {
            if (MapUtils.isEmpty(menuGroupByParentName)) {
                addButton(buttonGroupByParentName, cata, menuId);
            }
        }
    }

    private void addButton(Map<String, List<SysMenuImportDTO>> buttonGroupByParentName, SysMenuImportDTO menu, Long menuDOMenuId) {
        // 按钮
        if (buttonGroupByParentName.containsKey(menu.getMenuName()) || buttonGroupByParentName.containsKey(menu.getParentName())) {
            SysMenuDO buttonDO = new SysMenuDO();
            buttonDO.setParentId(menuDOMenuId);
            org.springframework.beans.BeanUtils.copyProperties(menu, buttonDO);
            sysMenuManager.insertMenu(buttonDO);
        }
    }

    private Map<String, List<SysMenuImportDTO>> getImportButtonMap(List<SysMenuImportDTO> v) {
        List<SysMenuImportDTO> buttonList = v.stream().filter(p -> p.getMenuType().equals(MenuTypeEnum.F.getCode())).collect(Collectors.toList());
        return buttonList.stream().collect(Collectors.groupingBy(SysMenuImportDTO::getParentName));
    }

    private Map<String, List<SysMenuImportDTO>> getImportMenuMap(List<SysMenuImportDTO> v) {
        List<SysMenuImportDTO> menuList = v.stream().filter(p -> p.getMenuType().equals(MenuTypeEnum.C.getCode())).collect(Collectors.toList());
        return menuList.stream().collect(Collectors.groupingBy(SysMenuImportDTO::getParentName));
    }

    @SuppressWarnings("java:S107")
    private void insertCheck(List<SysMenuImportDTO> sysMenus, List<String> menuTypeList, List<String> visibleList, List<String> parentNameErrorList, List<String> menuTypeErrorList, List<String> visibleErrorList, List<String> missValueErrorList, int i) {
        //添加校验
        insertFieldCheck(sysMenus, missValueErrorList, i);
        parentNameCheck(sysMenus, parentNameErrorList, i);
        menuTypeCheck(sysMenus, menuTypeList, menuTypeErrorList, i);
        visibleCheck(sysMenus, visibleList, visibleErrorList, i);
    }

    private void insertFieldCheck(List<SysMenuImportDTO> sysMenus, List<String> missValueErrorList, int i) {
        if (org.apache.commons.lang3.StringUtils.isBlank(sysMenus.get(i).getOrderNum()) || org.apache.commons.lang3.StringUtils.isBlank(sysMenus.get(i).getParentName()) ||
                org.apache.commons.lang3.StringUtils.isBlank(sysMenus.get(i).getMenuName()) || org.apache.commons.lang3.StringUtils.isBlank(sysMenus.get(i).getMenuType()) || org.apache.commons.lang3.StringUtils.isBlank(sysMenus.get(i).getVisible())) {
            //Missing value in mandatory attributes
            String str = ROW + i;
            missValueErrorList.add(str);
        }
    }

    private void parentNameCheck(List<SysMenuImportDTO> sysMenus, List<String> parentNameErrorList, int i) {
        if (org.apache.commons.lang3.StringUtils.isBlank(sysMenus.get(i).getMenuName())) {
            //Parent Menu Name不存在，则报错 Parent Menu not found
            String str = ROW + i;
            parentNameErrorList.add(str);
        }
    }

    private void visibleCheck(List<SysMenuImportDTO> sysMenus, List<String> visibleList, List<String> visibleErrorList, int i) {
        if (!visibleList.contains(sysMenus.get(i).getVisible())) {
//Visible不可识别（显示/隐藏），则报错 Invalid Visible
            String str = ROW + i;
            visibleErrorList.add(str);
        }
    }

    private void menuTypeCheck(List<SysMenuImportDTO> sysMenus, List<String> menuTypeList, List<String> menuTypeErrorList, int i) {
        if (!menuTypeList.contains(sysMenus.get(i).getMenuType())) {
            //Menu Type 不可识别（目录/菜单/按钮），则报错 Invalid Menu Type
            String str = ROW + i;
            menuTypeErrorList.add(str);
        }
    }

    @SuppressWarnings("java:S107")
    private void updateCheck(List<SysMenuImportDTO> sysMenus, List<String> menuTypeList, List<String> visibleList, List<String> sysMenuNameList, Map<Long, SysMenuImportDTO> groupByMenuId, List<String> menuIdErrorList, List<String> parentNameErrorList, List<String> menuTypeErrorList, List<String> visibleErrorList, List<String> delectionErrorList, int i, List<String> deleteFlagList) {
        //校验
        menuIdCheck(sysMenus, groupByMenuId, menuIdErrorList, i);
        menuNameCheck(sysMenus, sysMenuNameList, parentNameErrorList, i);
        menuTypeCheck(sysMenus, menuTypeList, menuTypeErrorList, i);
        visibleCheck(sysMenus, visibleList, visibleErrorList, i);
        deleteFlagCheck(sysMenus, delectionErrorList, i, deleteFlagList);
    }

    @SuppressWarnings("java:S1066")
    private void menuNameCheck(List<SysMenuImportDTO> sysMenus, List<String> sysMenuNameList, List<String> parentNameErrorList, int i) {
        if (!sysMenuNameList.contains(sysMenus.get(i).getMenuName())) {
            //Parent Menu Name不存在，则报错 Parent Menu not found 此处如果改父级菜单不做校验
            if (null != sysMenus.get(i).getParentId() && sysMenus.get(i).getParentId() != 0L) {
                String str = ROW + i;
                parentNameErrorList.add(str);
            }
        }
    }

    private void menuIdCheck(List<SysMenuImportDTO> sysMenus, Map<Long, SysMenuImportDTO> groupByMenuId, List<String> menuIdErrorList, int i) {
        if (!groupByMenuId.containsKey(sysMenus.get(i).getMenuId())) {
            // Menu ID不存在，则报错   Menu ID not found
            String str = ROW + i;
            menuIdErrorList.add(str);
        }
    }

    private void deleteFlagCheck(List<SysMenuImportDTO> sysMenus, List<String> delectionErrorList, int i, List<String> deleteFlagList) {
        if (!deleteFlagList.contains(sysMenus.get(i).getDeleteFlag()) || org.apache.commons.lang3.StringUtils.isBlank(sysMenus.get(i).getDeleteFlag())) {
            //Delection不可识别（Y/空值），则报错 Invalid Delection
            String str = ROW + i;
            delectionErrorList.add(str);
        }
    }


    private StringBuilder getErrorInfo(List<String> menuIdErrorList, List<String> parentNameErrorList, List<String> menuTypeErrorList, List<String> visibleErrorList, List<String> delectionErrorList, List<String> missValueErrorList) {

        StringBuilder result = new StringBuilder();
        if (CollectionUtils.isNotEmpty(menuIdErrorList)) {
            String join = org.apache.commons.lang3.StringUtils.join(menuIdErrorList, ",");
            String menuId = "Menu ID not found:" + join + "\n";
            result.append(menuId);

        }
        if (CollectionUtils.isNotEmpty(parentNameErrorList)) {

            String join = org.apache.commons.lang3.StringUtils.join(parentNameErrorList, ",");
            String parentName = "Parent Menu not found:" + join + "\n";
            result.append(parentName);
        }

        if (CollectionUtils.isNotEmpty(menuTypeErrorList)) {

            String join = org.apache.commons.lang3.StringUtils.join(menuTypeErrorList, ",");
            String menuType = "Invalid Menu Type:" + join + "\n";
            result.append(menuType);
        }
        if (CollectionUtils.isNotEmpty(visibleErrorList)) {

            String join = org.apache.commons.lang3.StringUtils.join(visibleErrorList, ",");
            String visible = "Invalid Visible:" + join + "\n";
            result.append(visible);
        }
        if (CollectionUtils.isNotEmpty(delectionErrorList)) {

            String join = org.apache.commons.lang3.StringUtils.join(delectionErrorList, ",");
            String delection = "Invalid Delection:" + join + "\n";
            result.append(delection);
        }
        if (CollectionUtils.isNotEmpty(missValueErrorList)) {

            String join = org.apache.commons.lang3.StringUtils.join(missValueErrorList, ",");
            String missValue = "Missing value in mandatory attributes:" + join + "\n";
            result.append(missValue);
        }
        return result;
    }

    /**
     * 递归列表
     *
     * @param parentId
     * @param
     */
    private List<SysMenu> getChildList(Long parentId) {
        //一级目录
        SysMenu sysMen = new SysMenu();
        sysMen.setParentId(parentId);
        List<SysMenuDO> sysMenus = sysMenuManager.selectMenus(sysMen);

        List<SysMenu> menus = new ArrayList<>(sysMenus.size());
        BeanUtils.copyListProperties(sysMenus, menus, SysMenu.class);
        return menus;
    }

}
