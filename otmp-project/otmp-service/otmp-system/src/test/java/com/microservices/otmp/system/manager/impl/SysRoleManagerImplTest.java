package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.dto.SysUserRoleListDTO;
import com.microservices.otmp.system.domain.entity.SysRoleDO;
import com.microservices.otmp.system.domain.vo.SysUserRoleListVO;
import com.microservices.otmp.system.mapper.SysRoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysRoleManagerImplTest {

    @Mock
    private SysRoleMapper mockSysRoleMapper;

    @InjectMocks
    private SysRoleManagerImpl sysRoleManagerImplUnderTest;

    @Test
    public void testSelectRoleList() {
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

        // Configure SysRoleMapper.selectRoleList(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleType("roleType");
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        final List<SysRoleDO> sysRoleDOS = Arrays.asList(sysRoleDO);
        when(mockSysRoleMapper.selectRoleList(any(SysRole.class))).thenReturn(sysRoleDOS);

        // Run the test
        final List<SysRoleDO> result = sysRoleManagerImplUnderTest.selectRoleList(role);

        // Verify the results
    }

    @Test
    public void testSelectRoleList_SysRoleMapperReturnsNoItems() {
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

        when(mockSysRoleMapper.selectRoleList(any(SysRole.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysRoleDO> result = sysRoleManagerImplUnderTest.selectRoleList(role);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectRolesByUserId() {
        // Setup
        // Configure SysRoleMapper.selectRolesByUserId(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleType("roleType");
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        final List<SysRoleDO> sysRoleDOS = Arrays.asList(sysRoleDO);
        when(mockSysRoleMapper.selectRolesByUserId(0L)).thenReturn(sysRoleDOS);

        // Run the test
        final List<SysRoleDO> result = sysRoleManagerImplUnderTest.selectRolesByUserId(0L);

        // Verify the results
    }

    @Test
    public void testSelectRolesByUserId_SysRoleMapperReturnsNoItems() {
        // Setup
        when(mockSysRoleMapper.selectRolesByUserId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysRoleDO> result = sysRoleManagerImplUnderTest.selectRolesByUserId(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectRoleById() {
        // Setup
        // Configure SysRoleMapper.selectRoleById(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleType("roleType");
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        when(mockSysRoleMapper.selectRoleById(0L)).thenReturn(sysRoleDO);

        // Run the test
        final SysRoleDO result = sysRoleManagerImplUnderTest.selectRoleById(0L);

        // Verify the results
    }

    @Test
    public void testDeleteRoleById() {
        // Setup
        when(mockSysRoleMapper.deleteRoleById(0L)).thenReturn(0);

        // Run the test
        final int result = sysRoleManagerImplUnderTest.deleteRoleById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteRoleByIds() {
        // Setup
        when(mockSysRoleMapper.deleteRoleByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysRoleManagerImplUnderTest.deleteRoleByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateRole() {
        // Setup
        final SysRoleDO role = new SysRoleDO();
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

        when(mockSysRoleMapper.updateRole(any(SysRoleDO.class))).thenReturn(0);

        // Run the test
        final int result = sysRoleManagerImplUnderTest.updateRole(role);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertRole() {
        // Setup
        final SysRoleDO role = new SysRoleDO();
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

        when(mockSysRoleMapper.insertRole(any(SysRoleDO.class))).thenReturn(0);

        // Run the test
        final int result = sysRoleManagerImplUnderTest.insertRole(role);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckRoleNameUnique() {
        // Setup
        // Configure SysRoleMapper.checkRoleNameUnique(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleType("roleType");
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        when(mockSysRoleMapper.checkRoleNameUnique("roleName")).thenReturn(sysRoleDO);

        // Run the test
        final SysRoleDO result = sysRoleManagerImplUnderTest.checkRoleNameUnique("roleName");

        // Verify the results
    }

    @Test
    public void testCheckRoleKeyUnique() {
        // Setup
        // Configure SysRoleMapper.checkRoleKeyUnique(...).
        final SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setRoleType("roleType");
        sysRoleDO.setRoleId(0L);
        sysRoleDO.setDataScope("dataScope");
        sysRoleDO.setRoleName("roleName");
        sysRoleDO.setRoleKey("roleKey");
        sysRoleDO.setRoleSort("roleSort");
        sysRoleDO.setDelFlag("delFlag");
        sysRoleDO.setStatus("status");
        sysRoleDO.setFlag(false);
        sysRoleDO.setMenuIds(Arrays.asList(0L));
        when(mockSysRoleMapper.checkRoleKeyUnique("roleKey")).thenReturn(sysRoleDO);

        // Run the test
        final SysRoleDO result = sysRoleManagerImplUnderTest.checkRoleKeyUnique("roleKey");

        // Verify the results
    }

    @Test
    public void testUserList() {
        // Setup
        final SysUserRoleListDTO sysRole = new SysUserRoleListDTO();
        sysRole.setRoleId(0L);
        sysRole.setLoginName("loginName");
        sysRole.setUserName("userName");
        sysRole.setUserIds(Arrays.asList(0L));

        final SysUserRoleListVO sysUserRoleListVO = new SysUserRoleListVO();
        sysUserRoleListVO.setLoginName("loginName");
        sysUserRoleListVO.setUserName("userName");
        sysUserRoleListVO.setStatus("status");
        sysUserRoleListVO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserRoleListVO.setRoleId(0L);
        sysUserRoleListVO.setUserId(0L);
        final List<SysUserRoleListVO> expectedResult = Arrays.asList(sysUserRoleListVO);

        // Configure SysRoleMapper.userList(...).
        final SysUserRoleListVO sysUserRoleListVO1 = new SysUserRoleListVO();
        sysUserRoleListVO1.setLoginName("loginName");
        sysUserRoleListVO1.setUserName("userName");
        sysUserRoleListVO1.setStatus("status");
        sysUserRoleListVO1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserRoleListVO1.setRoleId(0L);
        sysUserRoleListVO1.setUserId(0L);
        final List<SysUserRoleListVO> sysUserRoleListVOS = Arrays.asList(sysUserRoleListVO1);
        when(mockSysRoleMapper.userList(new SysUserRoleListDTO())).thenReturn(sysUserRoleListVOS);

        // Run the test
        final List<SysUserRoleListVO> result = sysRoleManagerImplUnderTest.userList(sysRole);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testUserList_SysRoleMapperReturnsNoItems() {
        // Setup
        final SysUserRoleListDTO sysRole = new SysUserRoleListDTO();
        sysRole.setRoleId(0L);
        sysRole.setLoginName("loginName");
        sysRole.setUserName("userName");
        sysRole.setUserIds(Arrays.asList(0L));

        when(mockSysRoleMapper.userList(new SysUserRoleListDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUserRoleListVO> result = sysRoleManagerImplUnderTest.userList(sysRole);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectAddSysRoleList() {
        // Setup
        final SysUserRoleListDTO sysRole = new SysUserRoleListDTO();
        sysRole.setRoleId(0L);
        sysRole.setLoginName("loginName");
        sysRole.setUserName("userName");
        sysRole.setUserIds(Arrays.asList(0L));

        final SysUserRoleListVO sysUserRoleListVO = new SysUserRoleListVO();
        sysUserRoleListVO.setLoginName("loginName");
        sysUserRoleListVO.setUserName("userName");
        sysUserRoleListVO.setStatus("status");
        sysUserRoleListVO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserRoleListVO.setRoleId(0L);
        sysUserRoleListVO.setUserId(0L);
        final List<SysUserRoleListVO> expectedResult = Arrays.asList(sysUserRoleListVO);

        // Configure SysRoleMapper.selectAddSysRoleList(...).
        final SysUserRoleListVO sysUserRoleListVO1 = new SysUserRoleListVO();
        sysUserRoleListVO1.setLoginName("loginName");
        sysUserRoleListVO1.setUserName("userName");
        sysUserRoleListVO1.setStatus("status");
        sysUserRoleListVO1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserRoleListVO1.setRoleId(0L);
        sysUserRoleListVO1.setUserId(0L);
        final List<SysUserRoleListVO> sysUserRoleListVOS = Arrays.asList(sysUserRoleListVO1);
        when(mockSysRoleMapper.selectAddSysRoleList(new SysUserRoleListDTO())).thenReturn(sysUserRoleListVOS);

        // Run the test
        final List<SysUserRoleListVO> result = sysRoleManagerImplUnderTest.selectAddSysRoleList(sysRole);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectAddSysRoleList_SysRoleMapperReturnsNoItems() {
        // Setup
        final SysUserRoleListDTO sysRole = new SysUserRoleListDTO();
        sysRole.setRoleId(0L);
        sysRole.setLoginName("loginName");
        sysRole.setUserName("userName");
        sysRole.setUserIds(Arrays.asList(0L));

        when(mockSysRoleMapper.selectAddSysRoleList(new SysUserRoleListDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUserRoleListVO> result = sysRoleManagerImplUnderTest.selectAddSysRoleList(sysRole);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
