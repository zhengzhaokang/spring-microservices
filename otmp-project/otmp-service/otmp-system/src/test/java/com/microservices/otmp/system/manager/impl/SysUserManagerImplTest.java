package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.entity.SysUserDO;
import com.microservices.otmp.system.domain.vo.UserExportRoleVO;
import com.microservices.otmp.system.domain.vo.UserExportVO;
import com.microservices.otmp.system.mapper.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SysUserManagerImplTest {

    @Mock
    private SysUserMapper mockSysUserMapper;

    @InjectMocks
    private SysUserManagerImpl sysUserManagerImplUnderTest;

    @Test
    public void testSelectUserList() {
        // Setup
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

        // Configure SysUserMapper.selectUserList(...).
        final SysUser sysUser1 = new SysUser();
        sysUser1.setLoginNameArray("loginNameArray");
        sysUser1.setIds("ids");
        sysUser1.setIdsList(Arrays.asList(0L));
        sysUser1.setUserId(0L);
        sysUser1.setDeptId(0L);
        sysUser1.setParentId(0L);
        sysUser1.setLoginName("loginName");
        sysUser1.setUserName("userName");
        sysUser1.setEmail("email");
        sysUser1.setPhonenumber("phonenumber");
        final List<SysUser> sysUsers = Arrays.asList(sysUser1);
        when(mockSysUserMapper.selectUserList(any(SysUser.class))).thenReturn(sysUsers);

        // Run the test
        final List<SysUser> result = sysUserManagerImplUnderTest.selectUserList(sysUser);

        // Verify the results
    }

    @Test
    public void testSelectUserList_SysUserMapperReturnsNoItems() {
        // Setup
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

        when(mockSysUserMapper.selectUserList(any(SysUser.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUser> result = sysUserManagerImplUnderTest.selectUserList(sysUser);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectUserListByCommonGeos() {
        // Setup
        final Map<String, Object> queryParams = new HashMap<>();

        // Configure SysUserMapper.selectUserListByCommonGeos(...).
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
        final List<SysUser> sysUsers = Arrays.asList(sysUser);
        when(mockSysUserMapper.selectUserListByCommonGeos(new HashMap<>())).thenReturn(sysUsers);

        // Run the test
        final List<SysUser> result = sysUserManagerImplUnderTest.selectUserListByCommonGeos(queryParams);

        // Verify the results
    }

    @Test
    public void testSelectUserListByCommonGeos_SysUserMapperReturnsNoItems() {
        // Setup
        final Map<String, Object> queryParams = new HashMap<>();
        when(mockSysUserMapper.selectUserListByCommonGeos(new HashMap<>())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUser> result = sysUserManagerImplUnderTest.selectUserListByCommonGeos(queryParams);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectAllocatedList() {
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

        // Configure SysUserMapper.selectAllocatedList(...).
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
        final List<SysUser> sysUsers = Arrays.asList(sysUser);
        when(mockSysUserMapper.selectAllocatedList(any(SysUser.class))).thenReturn(sysUsers);

        // Run the test
        final List<SysUser> result = sysUserManagerImplUnderTest.selectAllocatedList(user);

        // Verify the results
    }

    @Test
    public void testSelectAllocatedList_SysUserMapperReturnsNoItems() {
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

        when(mockSysUserMapper.selectAllocatedList(any(SysUser.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUser> result = sysUserManagerImplUnderTest.selectAllocatedList(user);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectUnallocatedList() {
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

        // Configure SysUserMapper.selectUnallocatedList(...).
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
        final List<SysUser> sysUsers = Arrays.asList(sysUser);
        when(mockSysUserMapper.selectUnallocatedList(any(SysUser.class))).thenReturn(sysUsers);

        // Run the test
        final List<SysUser> result = sysUserManagerImplUnderTest.selectUnallocatedList(user);

        // Verify the results
    }

    @Test
    public void testSelectUnallocatedList_SysUserMapperReturnsNoItems() {
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

        when(mockSysUserMapper.selectUnallocatedList(any(SysUser.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUser> result = sysUserManagerImplUnderTest.selectUnallocatedList(user);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectUserByLoginName() {
        // Setup
        // Configure SysUserMapper.selectUserByLoginName(...).
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
        when(mockSysUserMapper.selectUserByLoginName("userName")).thenReturn(sysUser);

        // Run the test
        final SysUser result = sysUserManagerImplUnderTest.selectUserByLoginName("userName");

        // Verify the results
    }

    @Test
    public void testSelectUserByPhoneNumber() {
        // Setup
        // Configure SysUserMapper.selectUserByPhoneNumber(...).
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
        when(mockSysUserMapper.selectUserByPhoneNumber("phoneNumber")).thenReturn(sysUser);

        // Run the test
        final SysUser result = sysUserManagerImplUnderTest.selectUserByPhoneNumber("phoneNumber");

        // Verify the results
    }

    @Test
    public void testSelectUserByEmail() {
        // Setup
        // Configure SysUserMapper.selectUserByEmail(...).
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
        when(mockSysUserMapper.selectUserByEmail("email")).thenReturn(sysUser);

        // Run the test
        final SysUser result = sysUserManagerImplUnderTest.selectUserByEmail("email");

        // Verify the results
    }

    @Test
    public void testSelectUserById() {
        // Setup
        // Configure SysUserMapper.selectUserById(...).
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
        when(mockSysUserMapper.selectUserById(0L)).thenReturn(sysUser);

        // Run the test
        final SysUser result = sysUserManagerImplUnderTest.selectUserById(0L);

        // Verify the results
    }

    @Test
    public void testDeleteUserById() {
        // Setup
        when(mockSysUserMapper.deleteUserById(0L)).thenReturn(0);

        // Run the test
        final int result = sysUserManagerImplUnderTest.deleteUserById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteUserByIds() {
        // Setup
        when(mockSysUserMapper.deleteUserByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysUserManagerImplUnderTest.deleteUserByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateUser() {
        // Setup
        final SysUserDO user = new SysUserDO();
        user.setUserId(0L);
        user.setDeptId(0L);
        user.setParentId(0L);
        user.setLoginName("loginName");
        user.setUserName("userName");
        user.setEmail("email");
        user.setPhonenumber("phonenumber");
        user.setSex("sex");
        user.setAvatar("avatar");
        user.setPassword("password");

        when(mockSysUserMapper.updateUser(any(SysUserDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserManagerImplUnderTest.updateUser(user);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertUser() {
        // Setup
        final SysUserDO user = new SysUserDO();
        user.setUserId(0L);
        user.setDeptId(0L);
        user.setParentId(0L);
        user.setLoginName("loginName");
        user.setUserName("userName");
        user.setEmail("email");
        user.setPhonenumber("phonenumber");
        user.setSex("sex");
        user.setAvatar("avatar");
        user.setPassword("password");

        when(mockSysUserMapper.insertUser(any(SysUserDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserManagerImplUnderTest.insertUser(user);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckLoginNameUnique() {
        // Setup
        when(mockSysUserMapper.checkLoginNameUnique("loginName")).thenReturn(0);

        // Run the test
        final int result = sysUserManagerImplUnderTest.checkLoginNameUnique("loginName");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckPhoneUnique() {
        // Setup
        // Configure SysUserMapper.checkPhoneUnique(...).
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
        when(mockSysUserMapper.checkPhoneUnique("phonenumber")).thenReturn(sysUser);

        // Run the test
        final SysUser result = sysUserManagerImplUnderTest.checkPhoneUnique("phonenumber");

        // Verify the results
    }

    @Test
    public void testCheckEmailUnique() {
        // Setup
        // Configure SysUserMapper.checkEmailUnique(...).
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
        when(mockSysUserMapper.checkEmailUnique("email")).thenReturn(sysUser);

        // Run the test
        final SysUser result = sysUserManagerImplUnderTest.checkEmailUnique("email");

        // Verify the results
    }

    @Test
    public void testSelectUserIdsHasRoles() {
        // Setup
        when(mockSysUserMapper.selectUserIdsHasRoles(any(Long[].class))).thenReturn(new HashSet<>(Arrays.asList(0L)));

        // Run the test
        final Set<Long> result = sysUserManagerImplUnderTest.selectUserIdsHasRoles(new Long[]{0L});

        // Verify the results
        assertEquals(new HashSet<>(Arrays.asList(0L)), result);
    }

    @Test
    public void testSelectUserIdsHasRoles_SysUserMapperReturnsNoItems() {
        // Setup
        when(mockSysUserMapper.selectUserIdsHasRoles(any(Long[].class))).thenReturn(Collections.emptySet());

        // Run the test
        final Set<Long> result = sysUserManagerImplUnderTest.selectUserIdsHasRoles(new Long[]{0L});

        // Verify the results
        assertEquals(Collections.emptySet(), result);
    }

    @Test
    public void testSelectUserIdsInDepts() {
        // Setup
        when(mockSysUserMapper.selectUserIdsInDepts(any(Long[].class))).thenReturn(new HashSet<>(Arrays.asList(0L)));

        // Run the test
        final Set<Long> result = sysUserManagerImplUnderTest.selectUserIdsInDepts(new Long[]{0L});

        // Verify the results
        assertEquals(new HashSet<>(Arrays.asList(0L)), result);
    }

    @Test
    public void testSelectUserIdsInDepts_SysUserMapperReturnsNoItems() {
        // Setup
        when(mockSysUserMapper.selectUserIdsInDepts(any(Long[].class))).thenReturn(Collections.emptySet());

        // Run the test
        final Set<Long> result = sysUserManagerImplUnderTest.selectUserIdsInDepts(new Long[]{0L});

        // Verify the results
        assertEquals(Collections.emptySet(), result);
    }

    @Test
    public void testExport() {
        // Setup
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

        final UserExportVO userExportVO = new UserExportVO();
        userExportVO.setItCode("itCode");
        userExportVO.setUserName("userName");
        userExportVO.setEmail("email");
        userExportVO.setStatus("status");
        userExportVO.setDelFlag("delFlag");
        userExportVO.setUserRole("userRole");
        userExportVO.setRoleId(0L);
        userExportVO.setUserId(0L);
        userExportVO.setBusinessGroup("businessGroup");
        userExportVO.setGeo("geo");
        final List<UserExportVO> expectedResult = Arrays.asList(userExportVO);

        // Configure SysUserMapper.export(...).
        final UserExportVO userExportVO1 = new UserExportVO();
        userExportVO1.setItCode("itCode");
        userExportVO1.setUserName("userName");
        userExportVO1.setEmail("email");
        userExportVO1.setStatus("status");
        userExportVO1.setDelFlag("delFlag");
        userExportVO1.setUserRole("userRole");
        userExportVO1.setRoleId(0L);
        userExportVO1.setUserId(0L);
        userExportVO1.setBusinessGroup("businessGroup");
        userExportVO1.setGeo("geo");
        final List<UserExportVO> userExportVOS = Arrays.asList(userExportVO1);
        when(mockSysUserMapper.export(any(SysUser.class))).thenReturn(userExportVOS);

        // Run the test
        final List<UserExportVO> result = sysUserManagerImplUnderTest.export(sysUser);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testExport_SysUserMapperReturnsNoItems() {
        // Setup
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

        when(mockSysUserMapper.export(any(SysUser.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<UserExportVO> result = sysUserManagerImplUnderTest.export(sysUser);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testExportMenu() {
        // Setup
        final UserExportRoleVO userExportRoleVO = new UserExportRoleVO();
        userExportRoleVO.setRoleName("roleName");
        userExportRoleVO.setMenuName("menuName");
        userExportRoleVO.setMenuKey("menuKey");
        userExportRoleVO.setMenuType("menuType");
        final List<UserExportRoleVO> expectedResult = Arrays.asList(userExportRoleVO);

        // Configure SysUserMapper.exportMenu(...).
        final UserExportRoleVO userExportRoleVO1 = new UserExportRoleVO();
        userExportRoleVO1.setRoleName("roleName");
        userExportRoleVO1.setMenuName("menuName");
        userExportRoleVO1.setMenuKey("menuKey");
        userExportRoleVO1.setMenuType("menuType");
        final List<UserExportRoleVO> userExportRoleVOS = Arrays.asList(userExportRoleVO1);
        when(mockSysUserMapper.exportMenu(Arrays.asList(0L))).thenReturn(userExportRoleVOS);

        // Run the test
        final List<UserExportRoleVO> result = sysUserManagerImplUnderTest.exportMenu(Arrays.asList(0L));

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testExportMenu_SysUserMapperReturnsNoItems() {
        // Setup
        when(mockSysUserMapper.exportMenu(Arrays.asList(0L))).thenReturn(Collections.emptyList());

        // Run the test
        final List<UserExportRoleVO> result = sysUserManagerImplUnderTest.exportMenu(Arrays.asList(0L));

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
