package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.SysUserRole;
import com.microservices.otmp.system.domain.entity.SysUserRoleDO;
import com.microservices.otmp.system.mapper.SysUserRoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysUserRoleManagerImplTest {

    @Mock
    private SysUserRoleMapper mockSysUserRoleMapper;

    @InjectMocks
    private SysUserRoleManagerImpl sysUserRoleManagerImplUnderTest;

    @Test
    public void testDeleteUserRoleByUserId() {
        // Setup
        when(mockSysUserRoleMapper.deleteUserRoleByUserId(0L)).thenReturn(0);

        // Run the test
        final int result = sysUserRoleManagerImplUnderTest.deleteUserRoleByUserId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteUserRole() {
        // Setup
        when(mockSysUserRoleMapper.deleteUserRole(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysUserRoleManagerImplUnderTest.deleteUserRole(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCountUserRoleByRoleId() {
        // Setup
        when(mockSysUserRoleMapper.countUserRoleByRoleId(0L)).thenReturn(0);

        // Run the test
        final int result = sysUserRoleManagerImplUnderTest.countUserRoleByRoleId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectUserRoleListByRoleId() {
        // Setup
        // Configure SysUserRoleMapper.selectUserRoleListByRoleId(...).
        final SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
        sysUserRoleDO.setCreateBy("createBy");
        sysUserRoleDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserRoleDO.setUserId(0L);
        sysUserRoleDO.setRoleId(0L);
        final List<SysUserRoleDO> sysUserRoleDOS = Arrays.asList(sysUserRoleDO);
        when(mockSysUserRoleMapper.selectUserRoleListByRoleId(0L)).thenReturn(sysUserRoleDOS);

        // Run the test
        final List<SysUserRoleDO> result = sysUserRoleManagerImplUnderTest.selectUserRoleListByRoleId(0L);

        // Verify the results
    }

    @Test
    public void testSelectUserRoleListByRoleId_SysUserRoleMapperReturnsNoItems() {
        // Setup
        when(mockSysUserRoleMapper.selectUserRoleListByRoleId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUserRoleDO> result = sysUserRoleManagerImplUnderTest.selectUserRoleListByRoleId(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testBatchUserRole() {
        // Setup
        final SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
        sysUserRoleDO.setCreateBy("createBy");
        sysUserRoleDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserRoleDO.setUserId(0L);
        sysUserRoleDO.setRoleId(0L);
        final List<SysUserRoleDO> userRoleList = Arrays.asList(sysUserRoleDO);
        when(mockSysUserRoleMapper.batchUserRole(Arrays.asList(new SysUserRoleDO()))).thenReturn(0);

        // Run the test
        final int result = sysUserRoleManagerImplUnderTest.batchUserRole(userRoleList);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteUserRoleInfo() {
        // Setup
        final SysUserRoleDO userRole = new SysUserRoleDO();
        userRole.setCreateBy("createBy");
        userRole.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        userRole.setUserId(0L);
        userRole.setRoleId(0L);

        when(mockSysUserRoleMapper.deleteUserRoleInfo(any(SysUserRoleDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserRoleManagerImplUnderTest.deleteUserRoleInfo(userRole);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteUserRoleByRoleId() {
        // Setup
        // Run the test
        sysUserRoleManagerImplUnderTest.deleteUserRoleByRoleId(0L);

        // Verify the results
        verify(mockSysUserRoleMapper).deleteUserRoleByRoleId(0L);
    }

    @Test
    public void testDeleteUserRoleInfos() {
        // Setup
        when(mockSysUserRoleMapper.deleteUserRoleInfos(eq(0L), any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysUserRoleManagerImplUnderTest.deleteUserRoleInfos(0L, new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectUserRoleList() {
        // Setup
        final SysUserRole sysUserRole = new SysUserRole();
        final SysUser sysUser = new SysUser();
        sysUser.setLoginNameArray("loginNameArray");
        sysUser.setIds("ids");
        sysUser.setIdsList(Arrays.asList(0L));
        sysUser.setUserId(0L);
        sysUser.setDeptId(0L);
        sysUser.setParentId(0L);
        sysUser.setLoginName("loginName");
        sysUser.setUserName("userName");
        sysUser.setEmail("email");
        sysUser.setPhonenumber("phonenumber");
        sysUserRole.setSysUsers(Arrays.asList(sysUser));
        sysUserRole.setUserId(0L);
        sysUserRole.setRoleId(0L);
        sysUserRole.setCreateBy("createBy");
        sysUserRole.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserRole.setRoleName("roleName");
        sysUserRole.setRoleKey("roleKey");
        final List<SysUserRole> expectedResult = Arrays.asList(sysUserRole);

        // Configure SysUserRoleMapper.selectUserRoleList(...).
        final SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
        sysUserRoleDO.setCreateBy("createBy");
        sysUserRoleDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserRoleDO.setUserId(0L);
        sysUserRoleDO.setRoleId(0L);
        final List<SysUserRoleDO> sysUserRoleDOS = Arrays.asList(sysUserRoleDO);
        when(mockSysUserRoleMapper.selectUserRoleList(0)).thenReturn(sysUserRoleDOS);

        // Run the test
        final List<SysUserRole> result = sysUserRoleManagerImplUnderTest.selectUserRoleList(0);

        // Verify the results
        assertEquals(1, result.size());
    }

    @Test
    public void testSelectUserRoleList_SysUserRoleMapperReturnsNoItems() {
        // Setup
        when(mockSysUserRoleMapper.selectUserRoleList(0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUserRole> result = sysUserRoleManagerImplUnderTest.selectUserRoleList(0);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectUserRoleListByUserIds() {
        // Setup
        // Configure SysUserRoleMapper.selectUserRoleListByUserIds(...).
        final SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
        sysUserRoleDO.setCreateBy("createBy");
        sysUserRoleDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserRoleDO.setUserId(0L);
        sysUserRoleDO.setRoleId(0L);
        final List<SysUserRoleDO> sysUserRoleDOS = Arrays.asList(sysUserRoleDO);
        when(mockSysUserRoleMapper.selectUserRoleListByUserIds(Arrays.asList(0L))).thenReturn(sysUserRoleDOS);

        // Run the test
        final List<SysUserRoleDO> result = sysUserRoleManagerImplUnderTest.selectUserRoleListByUserIds(Arrays.asList(0L));

        // Verify the results
    }

    @Test
    public void testSelectUserRoleListByUserIds_SysUserRoleMapperReturnsNoItems() {
        // Setup
        when(mockSysUserRoleMapper.selectUserRoleListByUserIds(Arrays.asList(0L))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUserRoleDO> result = sysUserRoleManagerImplUnderTest.selectUserRoleListByUserIds(Arrays.asList(0L));

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
