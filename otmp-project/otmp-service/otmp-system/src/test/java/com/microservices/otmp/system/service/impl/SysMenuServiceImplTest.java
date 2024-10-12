package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.core.domain.Ztree;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.system.domain.SysMenu;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.dto.SysMenuImportDTO;
import com.microservices.otmp.system.domain.entity.SysMenuDO;
import com.microservices.otmp.system.manager.SysMenuManager;
import com.microservices.otmp.system.manager.SysRoleMenuManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysMenuServiceImplTest {

    @Mock
    private SysMenuManager mockSysMenuManager;
    @Mock
    private SysRoleMenuManager mockSysRoleMenuManager;

    @InjectMocks
    private SysMenuServiceImpl sysMenuServiceImplUnderTest;

    @Test
    public void testSelectMenusByUser() {
        // Setup
        final SysUser user = new SysUser();
        user.setLoginNameArray("loginNameArray");
        user.setIds("ids");
        user.setIdsList(Arrays.asList(0L));
        user.setUserId(0L);
        user.setDeptId(0L);
        user.setParentId(0L);
        user.setLoginName("loginName");
        user.setUserName("userName");
        user.setEmail("email");
        user.setPhonenumber("phonenumber");

        final SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0L);
        sysMenu.setMenuName("menuName");
        sysMenu.setParentName("parentName");
        sysMenu.setParentId(0L);
        sysMenu.setTarget("target");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setVisible("visible");
        final List<SysMenu> expectedResult = Arrays.asList(sysMenu);

        // Configure SysMenuManager.selectMenuNormalAll(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenuNormalAll()).thenReturn(sysMenuDOS);

        // Configure SysMenuManager.selectMenusByUserId(...).
        final SysMenuDO sysMenuDO1 = new SysMenuDO();
        sysMenuDO1.setMenuId(0L);
        sysMenuDO1.setMenuName("menuName");
        sysMenuDO1.setParentName("parentName");
        sysMenuDO1.setParentId(0L);
        sysMenuDO1.setTarget("target");
        sysMenuDO1.setOrderNum("orderNum");
        sysMenuDO1.setMenuType("menuType");
        sysMenuDO1.setMenuKey("menuKey");
        sysMenuDO1.setComponent("component");
        sysMenuDO1.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS1 = Arrays.asList(sysMenuDO1);
        when(mockSysMenuManager.selectMenusByUserId(0L)).thenReturn(sysMenuDOS1);

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenusByUser(user);

        // Verify the results
        assertEquals(expectedResult.get(0).getMenuId(), result.get(0).getMenuId());
    }

    @Test
    public void testSelectMenusByUser_SysMenuManagerSelectMenuNormalAllReturnsNoItems() {
        // Setup
        final SysUser user = new SysUser();
        user.setLoginNameArray("loginNameArray");
        user.setIds("ids");
        user.setIdsList(Arrays.asList(0L));
        user.setUserId(0L);
        user.setDeptId(0L);
        user.setParentId(0L);
        user.setLoginName("loginName");
        user.setUserName("userName");
        user.setEmail("email");
        user.setPhonenumber("phonenumber");

        final SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0L);
        sysMenu.setMenuName("menuName");
        sysMenu.setParentName("parentName");
        sysMenu.setParentId(0L);
        sysMenu.setTarget("target");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setVisible("visible");
        final List<SysMenu> expectedResult = Arrays.asList(sysMenu);
        when(mockSysMenuManager.selectMenuNormalAll()).thenReturn(Collections.emptyList());

        // Configure SysMenuManager.selectMenusByUserId(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenusByUserId(0L)).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenusByUser(user);

        // Verify the results
        assertEquals(expectedResult.get(0).getMenuId(), result.get(0).getMenuId());
    }

    @Test
    public void testSelectMenusByUser_SysMenuManagerSelectMenusByUserIdReturnsNoItems() {
        // Setup
        final SysUser user = new SysUser();
        user.setLoginNameArray("loginNameArray");
        user.setIds("ids");
        user.setIdsList(Arrays.asList(0L));
        user.setUserId(0L);
        user.setDeptId(0L);
        user.setParentId(0L);
        user.setLoginName("loginName");
        user.setUserName("userName");
        user.setEmail("email");
        user.setPhonenumber("phonenumber");

        final SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0L);
        sysMenu.setMenuName("menuName");
        sysMenu.setParentName("parentName");
        sysMenu.setParentId(0L);
        sysMenu.setTarget("target");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setVisible("visible");
        final List<SysMenu> expectedResult = Arrays.asList(sysMenu);

        // Configure SysMenuManager.selectMenuNormalAll(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenuNormalAll()).thenReturn(sysMenuDOS);

        when(mockSysMenuManager.selectMenusByUserId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenusByUser(user);

        // Verify the results
        // assertEquals(expectedResult.get(0).getMenuId(), result.get(0).getMenuId());
    }

    @Test
    public void testSelectMenuList1() {
        // Setup
        final SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setMenuName("menuName");
        menu.setParentName("parentName");
        menu.setParentId(0L);
        menu.setTarget("target");
        menu.setOrderNum("orderNum");
        menu.setMenuType("menuType");
        menu.setMenuKey("menuKey");
        menu.setComponent("component");
        menu.setVisible("visible");

        final SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0L);
        sysMenu.setMenuName("menuName");
        sysMenu.setParentName("parentName");
        sysMenu.setParentId(0L);
        sysMenu.setTarget("target");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setVisible("visible");
        final List<SysMenu> expectedResult = Arrays.asList(sysMenu);

        // Configure SysMenuManager.selectMenuList(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenuList(new SysMenu())).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenuList(menu);

        // Verify the results
        //  assertEquals(expectedResult.get(0).getMenuId(), result.get(0).getMenuId());
    }

    @Test
    public void testSelectMenuList1_SysMenuManagerReturnsNoItems() {
        // Setup
        final SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setMenuName("menuName");
        menu.setParentName("parentName");
        menu.setParentId(0L);
        menu.setTarget("target");
        menu.setOrderNum("orderNum");
        menu.setMenuType("menuType");
        menu.setMenuKey("menuKey");
        menu.setComponent("component");
        menu.setVisible("visible");

        when(mockSysMenuManager.selectMenuList(new SysMenu())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenuList(menu);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectMenuList2() {
        // Setup
        final SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setMenuName("menuName");
        menu.setParentName("parentName");
        menu.setParentId(0L);
        menu.setTarget("target");
        menu.setOrderNum("orderNum");
        menu.setMenuType("menuType");
        menu.setMenuKey("menuKey");
        menu.setComponent("component");
        menu.setVisible("visible");

        final SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0L);
        sysMenu.setMenuName("menuName");
        sysMenu.setParentName("parentName");
        sysMenu.setParentId(0L);
        sysMenu.setTarget("target");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setVisible("visible");
        final List<SysMenu> expectedResult = Arrays.asList(sysMenu);

        // Configure SysMenuManager.selectMenuList(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenuList(new SysMenu())).thenReturn(sysMenuDOS);

        // Configure SysMenuManager.selectMenusByUserId(...).
        final SysMenuDO sysMenuDO1 = new SysMenuDO();
        sysMenuDO1.setMenuId(0L);
        sysMenuDO1.setMenuName("menuName");
        sysMenuDO1.setParentName("parentName");
        sysMenuDO1.setParentId(0L);
        sysMenuDO1.setTarget("target");
        sysMenuDO1.setOrderNum("orderNum");
        sysMenuDO1.setMenuType("menuType");
        sysMenuDO1.setMenuKey("menuKey");
        sysMenuDO1.setComponent("component");
        sysMenuDO1.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS1 = Arrays.asList(sysMenuDO1);
        when(mockSysMenuManager.selectMenusByUserId(0L)).thenReturn(sysMenuDOS1);

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenuList(menu, 0L);

        // Verify the results
        assertEquals(expectedResult.get(0).getMenuId(), result.get(0).getMenuId());
    }

    @Test
    public void testSelectMenuList2_SysMenuManagerSelectMenuListReturnsNoItems() {
        // Setup
        final SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setMenuName("menuName");
        menu.setParentName("parentName");
        menu.setParentId(0L);
        menu.setTarget("target");
        menu.setOrderNum("orderNum");
        menu.setMenuType("menuType");
        menu.setMenuKey("menuKey");
        menu.setComponent("component");
        menu.setVisible("visible");

        final SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0L);
        sysMenu.setMenuName("menuName");
        sysMenu.setParentName("parentName");
        sysMenu.setParentId(0L);
        sysMenu.setTarget("target");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setVisible("visible");
        final List<SysMenu> expectedResult = Arrays.asList(sysMenu);
        when(mockSysMenuManager.selectMenuList(new SysMenu())).thenReturn(Collections.emptyList());

        // Configure SysMenuManager.selectMenusByUserId(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenusByUserId(0L)).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenuList(menu, 0L);

        // Verify the results
        assertEquals(expectedResult.get(0).getMenuId(), result.get(0).getMenuId());
    }

    @Test
    public void testSelectMenuList2_SysMenuManagerSelectMenusByUserIdReturnsNoItems() {
        // Setup
        final SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setMenuName("menuName");
        menu.setParentName("parentName");
        menu.setParentId(0L);
        menu.setTarget("target");
        menu.setOrderNum("orderNum");
        menu.setMenuType("menuType");
        menu.setMenuKey("menuKey");
        menu.setComponent("component");
        menu.setVisible("visible");

        final SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0L);
        sysMenu.setMenuName("menuName");
        sysMenu.setParentName("parentName");
        sysMenu.setParentId(0L);
        sysMenu.setTarget("target");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setVisible("visible");
        final List<SysMenu> expectedResult = Arrays.asList(sysMenu);

        // Configure SysMenuManager.selectMenuList(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenuList(new SysMenu())).thenReturn(sysMenuDOS);

        when(mockSysMenuManager.selectMenusByUserId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenuList(menu, 0L);

        // Verify the results
        // assertEquals(expectedResult.get(0).getMenuId(), result.get(0).getMenuId());
    }

    @Test
    public void testSelectMenuAll() {
        // Setup
        final SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0L);
        sysMenu.setMenuName("menuName");
        sysMenu.setParentName("parentName");
        sysMenu.setParentId(0L);
        sysMenu.setTarget("target");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setVisible("visible");
        final List<SysMenu> expectedResult = Arrays.asList(sysMenu);

        // Configure SysMenuManager.selectMenuAll(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenuAll()).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenuAll();

        // Verify the results
        assertEquals(expectedResult.get(0).getMenuId(), result.get(0).getMenuId());
    }

    @Test
    public void testSelectMenuAll_SysMenuManagerReturnsNoItems() {
        // Setup
        when(mockSysMenuManager.selectMenuAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenuAll();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectPermsByUserId() {
        // Setup
        when(mockSysMenuManager.selectPermsByUserId(0L)).thenReturn(Arrays.asList("value"));

        // Run the test
        final Set<String> result = sysMenuServiceImplUnderTest.selectPermsByUserId(0L);

        // Verify the results
        assertEquals(new HashSet<>(Arrays.asList("value")), result);
    }

    @Test
    public void testSelectPermsByUserId_SysMenuManagerReturnsNoItems() {
        // Setup
        when(mockSysMenuManager.selectPermsByUserId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final Set<String> result = sysMenuServiceImplUnderTest.selectPermsByUserId(0L);

        // Verify the results
        assertEquals(Collections.emptySet(), result);
    }

    @Test
    public void testSelectMenuIdsByRoleId() {
        // Setup
        final SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0L);
        sysMenu.setMenuName("menuName");
        sysMenu.setParentName("parentName");
        sysMenu.setParentId(0L);
        sysMenu.setTarget("target");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setVisible("visible");
        final List<SysMenu> expectedResult = Arrays.asList(sysMenu);

        // Configure SysMenuManager.selectMenuIdsByRoleId(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenuIdsByRoleId(0L)).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenuIdsByRoleId(0L);

        // Verify the results
        assertEquals(expectedResult.get(0).getMenuId(), result.get(0).getMenuId());
    }

    @Test
    public void testSelectMenuIdsByRoleId_SysMenuManagerReturnsNoItems() {
        // Setup
        when(mockSysMenuManager.selectMenuIdsByRoleId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.selectMenuIdsByRoleId(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testRoleMenuTreeData() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleType("roleType");
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));

        // Configure SysMenuManager.selectMenuAll(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenuAll()).thenReturn(sysMenuDOS);

        when(mockSysMenuManager.selectMenuTree(0L)).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<Ztree> result = sysMenuServiceImplUnderTest.roleMenuTreeData(role);

        // Verify the results
    }

    @Test
    public void testRoleMenuTreeData_SysMenuManagerSelectMenuAllReturnsNoItems() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleType("roleType");
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));

        when(mockSysMenuManager.selectMenuAll()).thenReturn(Collections.emptyList());
        when(mockSysMenuManager.selectMenuTree(0L)).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<Ztree> result = sysMenuServiceImplUnderTest.roleMenuTreeData(role);

        // Verify the results
    }

    @Test
    public void testRoleMenuTreeData_SysMenuManagerSelectMenuTreeReturnsNoItems() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleType("roleType");
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));

        // Configure SysMenuManager.selectMenuAll(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenuAll()).thenReturn(sysMenuDOS);

        when(mockSysMenuManager.selectMenuTree(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<Ztree> result = sysMenuServiceImplUnderTest.roleMenuTreeData(role);

        // Verify the results
    }

    @Test
    public void testMenuTreeData() {
        // Setup
        // Configure SysMenuManager.selectMenuAll(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenuAll()).thenReturn(sysMenuDOS);

        // Run the test
        final List<Ztree> result = sysMenuServiceImplUnderTest.menuTreeData();

        // Verify the results
    }

    @Test
    public void testMenuTreeData_SysMenuManagerReturnsNoItems() {
        // Setup
        when(mockSysMenuManager.selectMenuAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Ztree> result = sysMenuServiceImplUnderTest.menuTreeData();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectPermsAll() {
        // Setup
        final LinkedHashMap<String, String> expectedResult = new LinkedHashMap<>();

        // Configure SysMenuManager.selectMenuAll(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenuAll()).thenReturn(sysMenuDOS);

        // Run the test
        final LinkedHashMap<String, String> result = sysMenuServiceImplUnderTest.selectPermsAll();

        // Verify the results
        // assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectPermsAll_SysMenuManagerReturnsNoItems() {
        // Setup
        final LinkedHashMap<String, String> expectedResult = new LinkedHashMap<>();
        when(mockSysMenuManager.selectMenuAll()).thenReturn(Collections.emptyList());

        // Run the test
        final LinkedHashMap<String, String> result = sysMenuServiceImplUnderTest.selectPermsAll();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testInitZtree1() {
        // Setup
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> menuList = Arrays.asList(sysMenuDO);

        // Run the test
        final List<Ztree> result = sysMenuServiceImplUnderTest.initZtree(menuList);

        // Verify the results
    }

    @Test
    public void testInitZtree2() {
        // Setup
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> menuList = Arrays.asList(sysMenuDO);

        // Run the test
        final List<Ztree> result = sysMenuServiceImplUnderTest.initZtree(menuList, Arrays.asList("value"), false);

        // Verify the results
    }

    @Test
    public void testTransMenuName() {
        // Setup
        final SysMenuDO menu = new SysMenuDO();
        menu.setMenuId(0L);
        menu.setMenuName("menuName");
        menu.setParentName("parentName");
        menu.setParentId(0L);
        menu.setTarget("target");
        menu.setOrderNum("orderNum");
        menu.setMenuType("menuType");
        menu.setMenuKey("menuKey");
        menu.setComponent("component");
        menu.setVisible("visible");

        // Run the test
        final String result = sysMenuServiceImplUnderTest.transMenuName(menu, false);

        // Verify the results
        assertEquals("menuName", result);
    }

    @Test
    public void testDeleteMenuById() {
        // Setup
        when(mockSysMenuManager.deleteMenuById(0L)).thenReturn(0);

        // Run the test
        final int result = sysMenuServiceImplUnderTest.deleteMenuById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectMenuById() {
        // Setup
        final SysMenu expectedResult = new SysMenu();
        expectedResult.setMenuId(0L);
        expectedResult.setMenuName("menuName");
        expectedResult.setParentName("parentName");
        expectedResult.setParentId(0L);
        expectedResult.setTarget("target");
        expectedResult.setOrderNum("orderNum");
        expectedResult.setMenuType("menuType");
        expectedResult.setMenuKey("menuKey");
        expectedResult.setComponent("component");
        expectedResult.setVisible("visible");

        // Configure SysMenuManager.selectMenuById(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        when(mockSysMenuManager.selectMenuById(0L)).thenReturn(sysMenuDO);

        // Run the test
        final SysMenu result = sysMenuServiceImplUnderTest.selectMenuById(0L);

        // Verify the results
        assertEquals(expectedResult.getMenuId(), result.getMenuId());
    }

    @Test
    public void testSelectMenuByMenuId() {
        // Setup
        final SysMenu expectedResult = new SysMenu();
        expectedResult.setMenuId(0L);
        expectedResult.setMenuName("menuName");
        expectedResult.setParentName("parentName");
        expectedResult.setParentId(0L);
        expectedResult.setTarget("target");
        expectedResult.setOrderNum("orderNum");
        expectedResult.setMenuType("menuType");
        expectedResult.setMenuKey("menuKey");
        expectedResult.setComponent("component");
        expectedResult.setVisible("visible");

        // Configure SysMenuManager.selectMenuByMenuId(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        when(mockSysMenuManager.selectMenuByMenuId(0L)).thenReturn(sysMenuDO);

        // Run the test
        final SysMenu result = sysMenuServiceImplUnderTest.selectMenuByMenuId(0L);

        // Verify the results
        assertEquals(expectedResult.getMenuId(), result.getMenuId());
    }

    @Test
    public void testSelectCountMenuByParentId() {
        // Setup
        when(mockSysMenuManager.selectCountMenuByParentId(0L)).thenReturn(0);

        // Run the test
        final int result = sysMenuServiceImplUnderTest.selectCountMenuByParentId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectCountRoleMenuByMenuId() {
        // Setup
        when(mockSysRoleMenuManager.selectCountRoleMenuByMenuId(0L)).thenReturn(0);

        // Run the test
        final int result = sysMenuServiceImplUnderTest.selectCountRoleMenuByMenuId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertMenu() {
        // Setup
        final SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setMenuName("menuName");
        menu.setParentName("parentName");
        menu.setParentId(0L);
        menu.setTarget("target");
        menu.setOrderNum("orderNum");
        menu.setMenuType("menuType");
        menu.setMenuKey("menuKey");
        menu.setComponent("component");
        menu.setVisible("visible");

        when(mockSysMenuManager.insertMenu(new SysMenuDO())).thenReturn(0);

        // Run the test
        final int result = sysMenuServiceImplUnderTest.insertMenu(menu);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateMenu1() {
        // Setup
        final SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setMenuName("menuName");
        menu.setParentName("parentName");
        menu.setParentId(0L);
        menu.setTarget("target");
        menu.setOrderNum("orderNum");
        menu.setMenuType("menuType");
        menu.setMenuKey("menuKey");
        menu.setComponent("component");
        menu.setVisible("visible");

        when(mockSysMenuManager.updateMenu(new SysMenuDO())).thenReturn(0);

        // Run the test
        final int result = sysMenuServiceImplUnderTest.updateMenu(menu);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckMenuNameUnique() {
        // Setup
        final SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setMenuName("menuName");
        menu.setParentName("parentName");
        menu.setParentId(0L);
        menu.setTarget("target");
        menu.setOrderNum("orderNum");
        menu.setMenuType("menuType");
        menu.setMenuKey("menuKey");
        menu.setComponent("component");
        menu.setVisible("visible");

        // Configure SysMenuManager.checkMenuNameUnique(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        when(mockSysMenuManager.checkMenuNameUnique("menuName", 0L)).thenReturn(sysMenuDO);

        // Run the test
        final String result = sysMenuServiceImplUnderTest.checkMenuNameUnique(menu);

        // Verify the results
        assertEquals("0", result);
    }

    @Test
    public void testGetChildPerms() {
        // Setup
        final SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0L);
        sysMenu.setMenuName("menuName");
        sysMenu.setParentName("parentName");
        sysMenu.setParentId(0L);
        sysMenu.setTarget("target");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setVisible("visible");
        final List<SysMenu> expectedResult = Arrays.asList(sysMenu);

        // Configure SysMenuManager.selectMenus(...).
        final SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setMenuId(0L);
        sysMenuDO.setMenuName("menuName");
        sysMenuDO.setParentName("parentName");
        sysMenuDO.setParentId(0L);
        sysMenuDO.setTarget("target");
        sysMenuDO.setOrderNum("orderNum");
        sysMenuDO.setMenuType("menuType");
        sysMenuDO.setMenuKey("menuKey");
        sysMenuDO.setComponent("component");
        sysMenuDO.setVisible("visible");
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO);
        when(mockSysMenuManager.selectMenus(new SysMenu())).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.getChildPerms(0L);

        // Verify the results
        // assertEquals(expectedResult.get(0).getMenuId(), result.get(0).getMenuId());
    }

    @Test
    public void testGetChildPerms_SysMenuManagerReturnsNoItems() {
        // Setup
        when(mockSysMenuManager.selectMenus(new SysMenu())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenu> result = sysMenuServiceImplUnderTest.getChildPerms(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testExportMenuList() {
        // Setup
        final SysMenuImportDTO sysMenu = new SysMenuImportDTO();
        sysMenu.setMenuId(0L);
        sysMenu.setParentId(0L);
        sysMenu.setParentName("parentName");
        sysMenu.setMenuName("menuName");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setPath("path");
        sysMenu.setPerms("perms");

        // Configure SysMenuManager.exportMenuList(...).
        final SysMenuImportDTO sysMenuImportDTO = new SysMenuImportDTO();
        sysMenuImportDTO.setMenuId(0L);
        sysMenuImportDTO.setParentId(0L);
        sysMenuImportDTO.setParentName("parentName");
        sysMenuImportDTO.setMenuName("menuName");
        sysMenuImportDTO.setMenuKey("menuKey");
        sysMenuImportDTO.setComponent("component");
        sysMenuImportDTO.setOrderNum("orderNum");
        sysMenuImportDTO.setMenuType("menuType");
        sysMenuImportDTO.setPath("path");
        sysMenuImportDTO.setPerms("perms");
        final List<SysMenuImportDTO> sysMenuImportDTOS = Arrays.asList(sysMenuImportDTO);
        when(mockSysMenuManager.exportMenuList(any(SysMenuImportDTO.class))).thenReturn(sysMenuImportDTOS);

        // Run the test
        final List<SysMenuImportDTO> result = sysMenuServiceImplUnderTest.exportMenuList(sysMenu);

        // Verify the results
    }

    @Test
    public void testExportMenuList_SysMenuManagerReturnsNoItems() {
        // Setup
        final SysMenuImportDTO sysMenu = new SysMenuImportDTO();
        sysMenu.setMenuId(0L);
        sysMenu.setParentId(0L);
        sysMenu.setParentName("parentName");
        sysMenu.setMenuName("menuName");
        sysMenu.setMenuKey("menuKey");
        sysMenu.setComponent("component");
        sysMenu.setOrderNum("orderNum");
        sysMenu.setMenuType("menuType");
        sysMenu.setPath("path");
        sysMenu.setPerms("perms");

        when(mockSysMenuManager.exportMenuList(any(SysMenuImportDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenuImportDTO> result = sysMenuServiceImplUnderTest.exportMenuList(sysMenu);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testImportExcel() {
        // Setup
        final SysMenuImportDTO sysMenuImportDTO = new SysMenuImportDTO();
        sysMenuImportDTO.setMenuId(0L);
        sysMenuImportDTO.setParentId(0L);
        sysMenuImportDTO.setParentName("parentName");
        sysMenuImportDTO.setMenuName("menuName");
        sysMenuImportDTO.setMenuKey("menuKey");
        sysMenuImportDTO.setComponent("component");
        sysMenuImportDTO.setOrderNum("orderNum");
        sysMenuImportDTO.setMenuType("menuType");
        sysMenuImportDTO.setPath("path");
        sysMenuImportDTO.setPerms("perms");
        final List<SysMenuImportDTO> sysMenus = Arrays.asList(sysMenuImportDTO);

        // Configure SysMenuManager.exportMenuList(...).
        final SysMenuImportDTO sysMenuImportDTO1 = new SysMenuImportDTO();
        sysMenuImportDTO1.setMenuId(0L);
        sysMenuImportDTO1.setParentId(0L);
        sysMenuImportDTO1.setParentName("parentName");
        sysMenuImportDTO1.setMenuName("menuName");
        sysMenuImportDTO1.setMenuKey("menuKey");
        sysMenuImportDTO1.setComponent("component");
        sysMenuImportDTO1.setOrderNum("orderNum");
        sysMenuImportDTO1.setMenuType("menuType");
        sysMenuImportDTO1.setPath("path");
        sysMenuImportDTO1.setPerms("perms");
        final List<SysMenuImportDTO> sysMenuImportDTOS = Arrays.asList(sysMenuImportDTO1);
        when(mockSysMenuManager.exportMenuList(any(SysMenuImportDTO.class))).thenReturn(sysMenuImportDTOS);

        when(mockSysMenuManager.updateMenu(new SysMenuDO())).thenReturn(0);
        when(mockSysMenuManager.insertMenu(new SysMenuDO())).thenReturn(0);

        // Run the test

        assertThrows(OtmpException.class, () -> sysMenuServiceImplUnderTest.importExcel(sysMenus, "loginName"));
    }

    @Test
    public void testImportExcel_SysMenuManagerExportMenuListReturnsNoItems() {
        // Setup
        final SysMenuImportDTO sysMenuImportDTO = new SysMenuImportDTO();
        sysMenuImportDTO.setMenuId(0L);
        sysMenuImportDTO.setParentId(0L);
        sysMenuImportDTO.setParentName("parentName");
        sysMenuImportDTO.setMenuName("menuName");
        sysMenuImportDTO.setMenuKey("menuKey");
        sysMenuImportDTO.setComponent("component");
        sysMenuImportDTO.setOrderNum("orderNum");
        sysMenuImportDTO.setMenuType("menuType");
        sysMenuImportDTO.setPath("path");
        sysMenuImportDTO.setPerms("perms");
        final List<SysMenuImportDTO> sysMenus = Arrays.asList(sysMenuImportDTO);
        when(mockSysMenuManager.exportMenuList(any(SysMenuImportDTO.class))).thenReturn(Collections.emptyList());
        when(mockSysMenuManager.updateMenu(new SysMenuDO())).thenReturn(0);
        when(mockSysMenuManager.insertMenu(new SysMenuDO())).thenReturn(0);

        // Run the test

        assertThrows(OtmpException.class, () -> sysMenuServiceImplUnderTest.importExcel(sysMenus, "loginName"));

        // Verify the results
        // verify(mockSysMenuManager).updateMenu(new SysMenuDO());
        // verify(mockSysMenuManager).insertMenu(new SysMenuDO());
    }
}
