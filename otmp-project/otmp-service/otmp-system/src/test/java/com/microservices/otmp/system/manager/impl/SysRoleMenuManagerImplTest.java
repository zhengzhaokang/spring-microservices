package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.SysRoleMenuDO;
import com.microservices.otmp.system.mapper.SysRoleMenuMapper;
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
public class SysRoleMenuManagerImplTest {

    @Mock
    private SysRoleMenuMapper mockSysRoleMenuMapper;

    @InjectMocks
    private SysRoleMenuManagerImpl sysRoleMenuManagerImplUnderTest;

    @Test
    public void testDeleteRoleMenuByRoleId() {
        // Setup
        when(mockSysRoleMenuMapper.deleteRoleMenuByRoleId(0L)).thenReturn(0);

        // Run the test
        final int result = sysRoleMenuManagerImplUnderTest.deleteRoleMenuByRoleId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteRoleMenuByMenuId() {
        // Setup
        when(mockSysRoleMenuMapper.deleteRoleMenuByMenuId(0L)).thenReturn(0);

        // Run the test
        final int result = sysRoleMenuManagerImplUnderTest.deleteRoleMenuByMenuId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteRoleMenu() {
        // Setup
        when(mockSysRoleMenuMapper.deleteRoleMenu(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysRoleMenuManagerImplUnderTest.deleteRoleMenu(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectCountRoleMenuByMenuId() {
        // Setup
        when(mockSysRoleMenuMapper.selectCountRoleMenuByMenuId(0L)).thenReturn(0);

        // Run the test
        final int result = sysRoleMenuManagerImplUnderTest.selectCountRoleMenuByMenuId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectRoleMenuListByMenuId() {
        // Setup
        // Configure SysRoleMenuMapper.selectRoleMenuListByMenuId(...).
        final SysRoleMenuDO sysRoleMenuDO = new SysRoleMenuDO();
        sysRoleMenuDO.setRoleId(0L);
        sysRoleMenuDO.setMenuId(0L);
        final List<SysRoleMenuDO> sysRoleMenuDOS = Arrays.asList(sysRoleMenuDO);
        when(mockSysRoleMenuMapper.selectRoleMenuListByMenuId(0L)).thenReturn(sysRoleMenuDOS);

        // Run the test
        final List<SysRoleMenuDO> result = sysRoleMenuManagerImplUnderTest.selectRoleMenuListByMenuId(0L);

        // Verify the results
    }

    @Test
    public void testSelectRoleMenuListByMenuId_SysRoleMenuMapperReturnsNoItems() {
        // Setup
        when(mockSysRoleMenuMapper.selectRoleMenuListByMenuId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysRoleMenuDO> result = sysRoleMenuManagerImplUnderTest.selectRoleMenuListByMenuId(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testBatchRoleMenu() {
        // Setup
        final SysRoleMenuDO sysRoleMenuDO = new SysRoleMenuDO();
        sysRoleMenuDO.setRoleId(0L);
        sysRoleMenuDO.setMenuId(0L);
        final List<SysRoleMenuDO> roleMenuList = Arrays.asList(sysRoleMenuDO);
        when(mockSysRoleMenuMapper.batchRoleMenu(Arrays.asList(new SysRoleMenuDO()))).thenReturn(0);

        // Run the test
        final int result = sysRoleMenuManagerImplUnderTest.batchRoleMenu(roleMenuList);

        // Verify the results
        assertEquals(0, result);
    }
}
