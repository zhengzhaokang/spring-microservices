package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysMenu;
import com.microservices.otmp.system.domain.dto.SysMenuImportDTO;
import com.microservices.otmp.system.domain.entity.SysMenuDO;
import com.microservices.otmp.system.mapper.SysMenuMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysMenuManagerImplTest {

    @Mock
    private SysMenuMapper mockSysMenuMapper;

    @InjectMocks
    private SysMenuManagerImpl sysMenuManagerImplUnderTest;

    @Test
    public void testSelectMenuAll() {
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
        final List<SysMenuDO> expectedResult = Arrays.asList(sysMenuDO);

        // Configure SysMenuMapper.selectMenuAll(...).
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
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO1);
        when(mockSysMenuMapper.selectMenuAll()).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenuAll();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectMenuAll_SysMenuMapperReturnsNoItems() {
        // Setup
        when(mockSysMenuMapper.selectMenuAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenuAll();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectMenuNormalAll() {
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
        final List<SysMenuDO> expectedResult = Arrays.asList(sysMenuDO);

        // Configure SysMenuMapper.selectMenuNormalAll(...).
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
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO1);
        when(mockSysMenuMapper.selectMenuNormalAll()).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenuNormalAll();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectMenuNormalAll_SysMenuMapperReturnsNoItems() {
        // Setup
        when(mockSysMenuMapper.selectMenuNormalAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenuNormalAll();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectMenusByUserId() {
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
        final List<SysMenuDO> expectedResult = Arrays.asList(sysMenuDO);

        // Configure SysMenuMapper.selectMenusByUserId(...).
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
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO1);
        when(mockSysMenuMapper.selectMenusByUserId(0L)).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenusByUserId(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectMenusByUserId_SysMenuMapperReturnsNoItems() {
        // Setup
        when(mockSysMenuMapper.selectMenusByUserId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenusByUserId(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectMenuByMenuId() {
        // Setup
        final SysMenuDO expectedResult = new SysMenuDO();
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

        // Configure SysMenuMapper.selectMenuByMenuId(...).
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
        when(mockSysMenuMapper.selectMenuByMenuId(0L)).thenReturn(sysMenuDO);

        // Run the test
        final SysMenuDO result = sysMenuManagerImplUnderTest.selectMenuByMenuId(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectPermsByUserId() {
        // Setup
        when(mockSysMenuMapper.selectPermsByUserId(0L)).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<String> result = sysMenuManagerImplUnderTest.selectPermsByUserId(0L);

        // Verify the results
        assertEquals(Arrays.asList("value"), result);
    }

    @Test
    public void testSelectPermsByUserId_SysMenuMapperReturnsNoItems() {
        // Setup
        when(mockSysMenuMapper.selectPermsByUserId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<String> result = sysMenuManagerImplUnderTest.selectPermsByUserId(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectMenuIdsByRoleId() {
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
        final List<SysMenuDO> expectedResult = Arrays.asList(sysMenuDO);

        // Configure SysMenuMapper.selectMenuIdsByRoleId(...).
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
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO1);
        when(mockSysMenuMapper.selectMenuIdsByRoleId(0L)).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenuIdsByRoleId(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectMenuIdsByRoleId_SysMenuMapperReturnsNoItems() {
        // Setup
        when(mockSysMenuMapper.selectMenuIdsByRoleId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenuIdsByRoleId(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectMenuTree() {
        // Setup
        when(mockSysMenuMapper.selectMenuTree(0L)).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<String> result = sysMenuManagerImplUnderTest.selectMenuTree(0L);

        // Verify the results
        assertEquals(Arrays.asList("value"), result);
    }

    @Test
    public void testSelectMenuTree_SysMenuMapperReturnsNoItems() {
        // Setup
        when(mockSysMenuMapper.selectMenuTree(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<String> result = sysMenuManagerImplUnderTest.selectMenuTree(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectMenuList() {
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
        final List<SysMenuDO> expectedResult = Arrays.asList(sysMenuDO);

        // Configure SysMenuMapper.selectMenuList(...).
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
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO1);
        when(mockSysMenuMapper.selectMenuList(new SysMenu())).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenuList(menu);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectMenuList_SysMenuMapperReturnsNoItems() {
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

        when(mockSysMenuMapper.selectMenuList(new SysMenu())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenuList(menu);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectMenus() {
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
        final List<SysMenuDO> expectedResult = Arrays.asList(sysMenuDO);

        // Configure SysMenuMapper.selectMenus(...).
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
        final List<SysMenuDO> sysMenuDOS = Arrays.asList(sysMenuDO1);
        when(mockSysMenuMapper.selectMenus(new SysMenu())).thenReturn(sysMenuDOS);

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenus(menu);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectMenus_SysMenuMapperReturnsNoItems() {
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

        when(mockSysMenuMapper.selectMenus(new SysMenu())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenuDO> result = sysMenuManagerImplUnderTest.selectMenus(menu);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDeleteMenuById() {
        // Setup
        when(mockSysMenuMapper.deleteMenuById(0L)).thenReturn(0);

        // Run the test
        final int result = sysMenuManagerImplUnderTest.deleteMenuById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectMenuById() {
        // Setup
        final SysMenuDO expectedResult = new SysMenuDO();
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

        // Configure SysMenuMapper.selectMenuById(...).
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
        when(mockSysMenuMapper.selectMenuById(0L)).thenReturn(sysMenuDO);

        // Run the test
        final SysMenuDO result = sysMenuManagerImplUnderTest.selectMenuById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectCountMenuByParentId() {
        // Setup
        when(mockSysMenuMapper.selectCountMenuByParentId(0L)).thenReturn(0);

        // Run the test
        final int result = sysMenuManagerImplUnderTest.selectCountMenuByParentId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertMenu() {
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

        when(mockSysMenuMapper.insertMenu(new SysMenuDO())).thenReturn(0);

        // Run the test
        final int result = sysMenuManagerImplUnderTest.insertMenu(menu);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateMenu() {
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

        when(mockSysMenuMapper.updateMenu(new SysMenuDO())).thenReturn(0);

        // Run the test
        final int result = sysMenuManagerImplUnderTest.updateMenu(menu);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckMenuNameUnique() {
        // Setup
        final SysMenuDO expectedResult = new SysMenuDO();
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

        // Configure SysMenuMapper.checkMenuNameUnique(...).
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
        when(mockSysMenuMapper.checkMenuNameUnique("menuName", 0L)).thenReturn(sysMenuDO);

        // Run the test
        final SysMenuDO result = sysMenuManagerImplUnderTest.checkMenuNameUnique("menuName", 0L);

        // Verify the results
        assertEquals(expectedResult, result);
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

        // Configure SysMenuMapper.exportMenuList(...).
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
        when(mockSysMenuMapper.exportMenuList(any(SysMenuImportDTO.class))).thenReturn(sysMenuImportDTOS);

        // Run the test
        final List<SysMenuImportDTO> result = sysMenuManagerImplUnderTest.exportMenuList(sysMenu);

        // Verify the results
    }

    @Test
    public void testExportMenuList_SysMenuMapperReturnsNoItems() {
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

        when(mockSysMenuMapper.exportMenuList(any(SysMenuImportDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysMenuImportDTO> result = sysMenuManagerImplUnderTest.exportMenuList(sysMenu);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testBatchInsert() {
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
        final List<SysMenuDO> dos = Arrays.asList(sysMenuDO);
        when(mockSysMenuMapper.batchInsert(Arrays.asList(new SysMenuDO()))).thenReturn(0);

        // Run the test
        final Integer result = sysMenuManagerImplUnderTest.batchInsert(dos);

        // Verify the results
        assertEquals(0, result.intValue());
    }
}
