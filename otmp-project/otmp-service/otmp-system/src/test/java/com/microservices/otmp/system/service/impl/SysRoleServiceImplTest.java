package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.exception.BusinessException;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.SysUserRole;
import com.microservices.otmp.system.domain.entity.SysRoleDO;
import com.microservices.otmp.system.domain.entity.SysRoleDeptDO;
import com.microservices.otmp.system.domain.entity.SysRoleMenuDO;
import com.microservices.otmp.system.domain.entity.SysUserRoleDO;
import com.microservices.otmp.system.manager.SysRoleDeptManager;
import com.microservices.otmp.system.manager.SysRoleManager;
import com.microservices.otmp.system.manager.SysRoleMenuManager;
import com.microservices.otmp.system.manager.SysUserRoleManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysRoleServiceImplTest {

    @Mock
    private SysRoleManager mockSysRoleManager;
    @Mock
    private SysRoleMenuManager mockSysRoleMenuManager;
    @Mock
    private SysUserRoleManager mockSysUserRoleManager;
    @Mock
    private SysRoleDeptManager mockSysRoleDeptManager;

    @InjectMocks
    private SysRoleServiceImpl sysRoleServiceImplUnderTest;

    @Test
    public void testSelectRoleList() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        // Configure SysRoleManager.selectRoleList(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        final List<SysRoleDO> sysRoleDOS = Arrays.asList(sysRoleDO);
        when(mockSysRoleManager.selectRoleList(any(SysRole.class))).thenReturn(sysRoleDOS);

        // Run the test
        final List<SysRole> result = sysRoleServiceImplUnderTest.selectRoleList(role);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( 1,result.size());
        verify(mockSysRoleManager, times(1)).selectRoleList(any());
    }

    @Test
    public void testSelectRoleList_SysRoleManagerReturnsNoItems() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        when(mockSysRoleManager.selectRoleList(any(SysRole.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysRole> result = sysRoleServiceImplUnderTest.selectRoleList(role);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectRoleKeys() {
        // Setup
        // Configure SysRoleManager.selectRolesByUserId(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        final List<SysRoleDO> sysRoleDOS = Arrays.asList(sysRoleDO);
        when(mockSysRoleManager.selectRolesByUserId(0L)).thenReturn(sysRoleDOS);

        // Run the test
        final Set<String> result = sysRoleServiceImplUnderTest.selectRoleKeys(0L);

        // Verify the results
        assertEquals(new HashSet<>(Arrays.asList("roleKey")), result);
    }

    @Test
    public void testSelectRoleKeys_SysRoleManagerReturnsNoItems() {
        // Setup
        when(mockSysRoleManager.selectRolesByUserId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final Set<String> result = sysRoleServiceImplUnderTest.selectRoleKeys(0L);

        // Verify the results
        assertEquals(Collections.emptySet(), result);
    }

    @Test
    public void testSelectRolesByUserId() {
        // Setup
        // Configure SysRoleManager.selectRolesByUserId(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        final List<SysRoleDO> sysRoleDOS = Arrays.asList(sysRoleDO);
        when(mockSysRoleManager.selectRolesByUserId(0L)).thenReturn(sysRoleDOS);

        // Configure SysRoleManager.selectRoleList(...).
        final SysRoleDO sysRoleDO1 = new SysRoleDO();
        sysRoleDO1.setRoleId(0L);
        sysRoleDO1.setDataScope("dataScope");
        sysRoleDO1.setRoleName("roleName");
        sysRoleDO1.setRoleKey("roleKey");
        sysRoleDO1.setRoleSort("roleSort");
        sysRoleDO1.setDelFlag("delFlag");
        sysRoleDO1.setStatus("status");
        sysRoleDO1.setFlag(false);
        sysRoleDO1.setMenuIds(Arrays.asList(0L));
        sysRoleDO1.setDeptIds(new Long[]{0L});
        final List<SysRoleDO> sysRoleDOS1 = Arrays.asList(sysRoleDO1);
        when(mockSysRoleManager.selectRoleList(any(SysRole.class))).thenReturn(sysRoleDOS1);

        // Run the test
        final List<SysRole> result = sysRoleServiceImplUnderTest.selectRolesByUserId(0L);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( 1,result.size());
        verify(mockSysRoleManager, times(1)).selectRoleList(any());
    }

    @Test
    public void testSelectRolesByUserId_SysRoleManagerSelectRolesByUserIdReturnsNoItems() {
        // Setup
        when(mockSysRoleManager.selectRolesByUserId(0L)).thenReturn(Collections.emptyList());

        // Configure SysRoleManager.selectRoleList(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        final List<SysRoleDO> sysRoleDOS = Arrays.asList(sysRoleDO);
        when(mockSysRoleManager.selectRoleList(any(SysRole.class))).thenReturn(sysRoleDOS);

        // Run the test
        final List<SysRole> result = sysRoleServiceImplUnderTest.selectRolesByUserId(0L);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockSysRoleManager, times(1)).selectRoleList(any());
    }

    @Test
    public void testSelectRolesByUserId_SysRoleManagerSelectRoleListReturnsNoItems() {
        // Setup
        // Configure SysRoleManager.selectRolesByUserId(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        final List<SysRoleDO> sysRoleDOS = Arrays.asList(sysRoleDO);
        when(mockSysRoleManager.selectRolesByUserId(0L)).thenReturn(sysRoleDOS);

        when(mockSysRoleManager.selectRoleList(any(SysRole.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysRole> result = sysRoleServiceImplUnderTest.selectRolesByUserId(0L);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals(Collections.emptyList(), result);
        verify(mockSysRoleManager, times(1)).selectRoleList(any());
    }

    @Test
    public void testSelectRoleAll() {
        // Setup
        // Configure SysRoleManager.selectRoleList(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        final List<SysRoleDO> sysRoleDOS = Arrays.asList(sysRoleDO);
        when(mockSysRoleManager.selectRoleList(any(SysRole.class))).thenReturn(sysRoleDOS);

        // Run the test
        final List<SysRole> result = sysRoleServiceImplUnderTest.selectRoleAll();

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockSysRoleManager, times(1)).selectRoleList(any());
    }

    @Test
    public void testSelectRoleAll_SysRoleManagerReturnsNoItems() {
        // Setup
        when(mockSysRoleManager.selectRoleList(any(SysRole.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysRole> result = sysRoleServiceImplUnderTest.selectRoleAll();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectRoleById() {
        // Setup
        // Configure SysRoleManager.selectRoleById(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        when(mockSysRoleManager.selectRoleById(0L)).thenReturn(sysRoleDO);

        // Run the test
        final SysRole result = sysRoleServiceImplUnderTest.selectRoleById(0L);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals(sysRoleDO.getRoleId(), result.getRoleId());
        verify(mockSysRoleManager, times(1)).selectRoleById(any());
    }

    @Test
    public void testDeleteRoleById() {
        // Setup
        when(mockSysRoleManager.deleteRoleById(0L)).thenReturn(1);

        // Run the test
        final boolean result = sysRoleServiceImplUnderTest.deleteRoleById(0L);

        // Verify the results
        assertTrue(result);
    }

    @Test
    public void testDeleteRoleByIds() {
        // Setup
        // Configure SysRoleManager.selectRoleById(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        when(mockSysRoleManager.selectRoleById(0L)).thenReturn(sysRoleDO);

        when(mockSysUserRoleManager.countUserRoleByRoleId(0L)).thenReturn(0);
        when(mockSysRoleManager.deleteRoleByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysRoleServiceImplUnderTest.deleteRoleByIds("0");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteRoleByIds_ThrowsBusinessException() {
        // Setup
        // Configure SysRoleManager.selectRoleById(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        when(mockSysRoleManager.selectRoleById(0L)).thenReturn(sysRoleDO);

        when(mockSysUserRoleManager.countUserRoleByRoleId(0L)).thenReturn(1);
        when(mockSysRoleManager.deleteRoleByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        assertThrows(BusinessException.class, () -> sysRoleServiceImplUnderTest.deleteRoleByIds("0"));
    }

    @Test
    public void testInsertRole() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        when(mockSysRoleManager.insertRole(any(SysRoleDO.class))).thenReturn(0);
        when(mockSysRoleMenuManager.batchRoleMenu(Arrays.asList(new SysRoleMenuDO()))).thenReturn(0);

        // Run the test
        final int result = sysRoleServiceImplUnderTest.insertRole(role);

        // Verify the results
        assertEquals(0, result);
        verify(mockSysRoleManager).insertRole(any(SysRoleDO.class));
    }

    @Test
    public void testUpdateRole() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        when(mockSysRoleManager.updateRole(any(SysRoleDO.class))).thenReturn(0);
        when(mockSysRoleMenuManager.deleteRoleMenuByRoleId(0L)).thenReturn(0);
        when(mockSysRoleMenuManager.batchRoleMenu(Arrays.asList(new SysRoleMenuDO()))).thenReturn(0);

        // Run the test
        final int result = sysRoleServiceImplUnderTest.updateRole(role);

        // Verify the results
        assertEquals(0, result);
        verify(mockSysRoleManager).updateRole(any(SysRoleDO.class));
        verify(mockSysRoleMenuManager).deleteRoleMenuByRoleId(0L);
    }

    @Test
    public void testAuthDataScope() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        when(mockSysRoleManager.updateRole(any(SysRoleDO.class))).thenReturn(0);
        when(mockSysRoleDeptManager.deleteRoleDeptByRoleId(0L)).thenReturn(0);
        when(mockSysRoleDeptManager.batchRoleDept(Arrays.asList(new SysRoleDeptDO()))).thenReturn(0);

        // Run the test
        final int result = sysRoleServiceImplUnderTest.authDataScope(role);

        // Verify the results
        assertEquals(0, result);
        verify(mockSysRoleManager).updateRole(any(SysRoleDO.class));
        verify(mockSysRoleDeptManager).deleteRoleDeptByRoleId(0L);
    }

    @Test
    public void testInsertRoleMenu() {
        // Setup
        final SysRoleDO role = new SysRoleDO();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        when(mockSysRoleMenuManager.batchRoleMenu(Arrays.asList(new SysRoleMenuDO()))).thenReturn(0);

        // Run the test
        final int result = sysRoleServiceImplUnderTest.insertRoleMenu(role);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertRoleDept() {
        // Setup
        final SysRoleDO role = new SysRoleDO();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        when(mockSysRoleDeptManager.batchRoleDept(Arrays.asList(new SysRoleDeptDO()))).thenReturn(0);

        // Run the test
        final int result = sysRoleServiceImplUnderTest.insertRoleDept(role);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckRoleNameUnique() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        // Configure SysRoleManager.checkRoleNameUnique(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        when(mockSysRoleManager.checkRoleNameUnique("roleName")).thenReturn(sysRoleDO);

        // Run the test
        final String result = sysRoleServiceImplUnderTest.checkRoleNameUnique(role);

        // Verify the results
        assertEquals("0", result);
    }

    @Test
    public void testCheckRoleKeyUnique() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        // Configure SysRoleManager.checkRoleKeyUnique(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        when(mockSysRoleManager.checkRoleKeyUnique("roleKey")).thenReturn(sysRoleDO);

        // Run the test
        final String result = sysRoleServiceImplUnderTest.checkRoleKeyUnique(role);

        // Verify the results
        assertEquals("0", result);
    }

    @Test
    public void testCountUserRoleByRoleId() {
        // Setup
        when(mockSysUserRoleManager.countUserRoleByRoleId(0L)).thenReturn(0);

        // Run the test
        final int result = sysRoleServiceImplUnderTest.countUserRoleByRoleId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testChangeStatus() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        when(mockSysRoleManager.updateRole(any(SysRoleDO.class))).thenReturn(0);

        // Run the test
        final int result = sysRoleServiceImplUnderTest.changeStatus(role);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteAuthUser() {
        // Setup
        final SysUserRole userRole = new SysUserRole();
        userRole.setUserId(0L);
        userRole.setRoleId(0L);

        when(mockSysUserRoleManager.deleteUserRoleInfo(any(SysUserRoleDO.class))).thenReturn(0);

        // Run the test
        final int result = sysRoleServiceImplUnderTest.deleteAuthUser(userRole);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteAuthUsers() {
        // Setup
        when(mockSysUserRoleManager.deleteUserRoleInfos(eq(0L), any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysRoleServiceImplUnderTest.deleteAuthUsers(0L, "userIds");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertAuthUsers() {
        // Setup
        when(mockSysUserRoleManager.batchUserRole(Arrays.asList(new SysUserRoleDO()))).thenReturn(0);

        // Run the test
        final int result = sysRoleServiceImplUnderTest.insertAuthUsers(0L, "userIds");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectRolePage() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        // Configure SysRoleManager.selectRoleList(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        sysRoleDO.setDeptIds(new Long[]{0L});
        final List<SysRoleDO> sysRoleDOS = Arrays.asList(sysRoleDO);
        when(mockSysRoleManager.selectRoleList(any(SysRole.class))).thenReturn(sysRoleDOS);

        // Run the test
        final PageInfo<SysRole> result = sysRoleServiceImplUnderTest.selectRolePage(role);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( 1,result.getTotal());
        verify(mockSysRoleManager, times(1)).selectRoleList(any());
    }

    @Test
    public void testSelectRolePage_SysRoleManagerReturnsNoItems() {
        // Setup
        final SysRole role = new SysRole();
        role.setRoleId(0L);
        role.setDataScope("dataScope");
        role.setRoleName("roleName");
        role.setRoleKey("roleKey");
        role.setRoleSort("roleSort");
        role.setDelFlag("delFlag");
        role.setStatus("status");
        role.setFlag(false);
        role.setMenuIds(Arrays.asList(0L));
        role.setDeptIds(new Long[]{0L});

        when(mockSysRoleManager.selectRoleList(any(SysRole.class))).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<SysRole> result = sysRoleServiceImplUnderTest.selectRolePage(role);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals(Collections.emptyList(), result.getList());
        verify(mockSysRoleManager, times(1)).selectRoleList(any());
    }
}
