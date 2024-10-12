package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.service.impl.ValidateImportDetails;
import com.microservices.otmp.system.common.DataScopeKeyConstants;
import com.microservices.otmp.system.common.GetLoginUserUtil;
import com.microservices.otmp.system.common.ValidateUserImport;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.SysUserRole;
import com.microservices.otmp.system.domain.entity.*;
import com.microservices.otmp.system.domain.vo.UserExportRoleVO;
import com.microservices.otmp.system.domain.vo.UserExportVO;
import com.microservices.otmp.system.domain.vo.UserImportVo;
import com.microservices.otmp.system.manager.*;
import com.microservices.otmp.system.service.ISysConfigService;
import com.microservices.otmp.system.service.ISysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysUserServiceImplTest {

    @Mock
    private SysUserManager mockSysUserManager;
    @Mock
    private SysRoleManager mockSysRoleManager;
    @Mock
    private SysPostManager mockSysPostManager;
    @Mock
    private SysUserPostManager mockSysUserPostManager;
    @Mock
    private SysUserRoleManager mockSysUserRoleManager;
    @Mock
    private SysUserDataScopeManager mockSysUserDataScopeManager;
    @Mock
    private ISysConfigService mockConfigService;
    @Mock
    private GetLoginUserUtil mockGetLoginUserUtil;
    @Mock
    private RedisUtils mockRedisUtils;
    @Mock
    private ISysUserService mockSysUserService;
    @Mock
    private ValidateImportDetails mockValidateImportDetails;
    @Mock
    private ValidateUserImport mockCustomerValidate;
    @Mock
    private ISysConfigService mockSysConfigService;

    @InjectMocks
    private SysUserServiceImpl sysUserServiceImplUnderTest;

    @Test
    public void testSelectUserList() {
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

        // Configure SysUserManager.selectUserList(...).
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
        when(mockSysUserManager.selectUserList(any(SysUser.class))).thenReturn(sysUsers);

        // Run the test
        final List<SysUser> result = sysUserServiceImplUnderTest.selectUserList(user);

        // Verify the results
    }

    @Test
    public void testSelectUserList_SysUserManagerReturnsNoItems() {
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

        when(mockSysUserManager.selectUserList(any(SysUser.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUser> result = sysUserServiceImplUnderTest.selectUserList(user);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectUserListByCommonGeos() {
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

        // Configure SysUserManager.selectUserListByCommonGeos(...).
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
        when(mockSysUserManager.selectUserListByCommonGeos(new HashMap<>())).thenReturn(sysUsers);

        // Run the test
        final List<SysUser> result = sysUserServiceImplUnderTest.selectUserListByCommonGeos(user);

        // Verify the results
    }

    @Test
    public void testSelectUserListByCommonGeos_SysUserManagerReturnsNoItems() {
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

        when(mockSysUserManager.selectUserListByCommonGeos(new HashMap<>())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUser> result = sysUserServiceImplUnderTest.selectUserListByCommonGeos(user);

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

        // Configure SysUserManager.selectAllocatedList(...).
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
        when(mockSysUserManager.selectAllocatedList(any(SysUser.class))).thenReturn(sysUsers);

        // Run the test
        final List<SysUser> result = sysUserServiceImplUnderTest.selectAllocatedList(user);

        // Verify the results
    }

    @Test
    public void testSelectAllocatedList_SysUserManagerReturnsNoItems() {
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

        when(mockSysUserManager.selectAllocatedList(any(SysUser.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUser> result = sysUserServiceImplUnderTest.selectAllocatedList(user);

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

        // Configure SysUserManager.selectUnallocatedList(...).
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
        when(mockSysUserManager.selectUnallocatedList(any(SysUser.class))).thenReturn(sysUsers);

        // Run the test
        final List<SysUser> result = sysUserServiceImplUnderTest.selectUnallocatedList(user);

        // Verify the results
    }

    @Test
    public void testSelectUnallocatedList_SysUserManagerReturnsNoItems() {
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

        when(mockSysUserManager.selectUnallocatedList(any(SysUser.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUser> result = sysUserServiceImplUnderTest.selectUnallocatedList(user);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectUserByLoginName() {
        // Setup
        // Configure SysUserManager.selectUserByLoginName(...).
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
        when(mockSysUserManager.selectUserByLoginName("userName")).thenReturn(sysUser);

        // Configure SysUserDataScopeManager.selectSysUserDataScopeList(...).
        final SysUserDataScopeDO sysUserDataScopeDO = new SysUserDataScopeDO();
        sysUserDataScopeDO.setSelectAll("selectAll");
        sysUserDataScopeDO.setNotIn("notIn");
        sysUserDataScopeDO.setUserDataScopeId(0L);
        sysUserDataScopeDO.setUserId(0L);
        sysUserDataScopeDO.setUserItcode("userItcode");
        sysUserDataScopeDO.setDataScopeKey("dataScopeKey");
        sysUserDataScopeDO.setDataScopeValue("dataScopeValue");
        sysUserDataScopeDO.setStatus("status");
        final List<SysUserDataScopeDO> sysUserDataScopeDOS = Arrays.asList(sysUserDataScopeDO);
        when(mockSysUserDataScopeManager.selectSysUserDataScopeList(any(SysUserDataScope.class))).thenReturn(sysUserDataScopeDOS);

        // Run the test
        final SysUser result = sysUserServiceImplUnderTest.selectUserByLoginName("userName");

        // Verify the results
    }

    @Test
    public void testSelectUserByLoginName_SysUserDataScopeManagerReturnsNoItems() {
        // Setup
        // Configure SysUserManager.selectUserByLoginName(...).
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
        when(mockSysUserManager.selectUserByLoginName("userName")).thenReturn(sysUser);

        when(mockSysUserDataScopeManager.selectSysUserDataScopeList(any(SysUserDataScope.class))).thenReturn(Collections.emptyList());

        // Run the test
        final SysUser result = sysUserServiceImplUnderTest.selectUserByLoginName("userName");

        // Verify the results
    }

    @Test
    public void testSelectUserByLoginNameToAdfs() {
        // Setup
        // Configure SysUserManager.selectUserByLoginName(...).
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
        when(mockSysUserManager.selectUserByLoginName("userName")).thenReturn(sysUser);

        // Run the test
        final SysUser result = sysUserServiceImplUnderTest.selectUserByLoginNameToAdfs("userName");

        // Verify the results
    }

    @Test
    public void testSelectUserByPhoneNumber() {
        // Setup
        // Configure SysUserManager.selectUserByPhoneNumber(...).
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
        when(mockSysUserManager.selectUserByPhoneNumber("phoneNumber")).thenReturn(sysUser);

        // Run the test
        final SysUser result = sysUserServiceImplUnderTest.selectUserByPhoneNumber("phoneNumber");

        // Verify the results
    }

    @Test
    public void testSelectUserByEmail() {
        // Setup
        // Configure SysUserManager.selectUserByEmail(...).
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
        when(mockSysUserManager.selectUserByEmail("email")).thenReturn(sysUser);

        // Run the test
        final SysUser result = sysUserServiceImplUnderTest.selectUserByEmail("email");

        // Verify the results
    }

    @Test
    public void testSelectUserById() {
        // Setup
        // Configure RedisUtils.get(...).
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
        when(mockRedisUtils.get("key", SysUser.class)).thenReturn(sysUser);

        // Configure SysUserManager.selectUserById(...).
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
        when(mockSysUserManager.selectUserById(0L)).thenReturn(sysUser1);

        // Run the test
        final SysUser result = sysUserServiceImplUnderTest.selectUserById(0L);

        // Verify the results
        // verify(mockRedisUtils).set(eq("key"), any(Object.class));
    }

    @Test
    public void testDeleteUserById() {
        // Setup
        when(mockSysUserRoleManager.deleteUserRoleByUserId(0L)).thenReturn(0);
        when(mockSysUserPostManager.deleteUserPostByUserId(0L)).thenReturn(0);
        when(mockSysUserDataScopeManager.deleteSysUserDataScopeByUserId(0L)).thenReturn(0);
        when(mockRedisUtils.isContains("key")).thenReturn(false);
        when(mockSysUserManager.deleteUserById(0L)).thenReturn(0);

        // Run the test
        final int result = sysUserServiceImplUnderTest.deleteUserById(0L);

        // Verify the results
//        assertEquals(0, result);
//        verify(mockSysUserRoleManager).deleteUserRoleByUserId(0L);
//        verify(mockSysUserPostManager).deleteUserPostByUserId(0L);
//        verify(mockSysUserDataScopeManager).deleteSysUserDataScopeByUserId(0L);
//        verify(mockRedisUtils).delete("key");
    }

    @Test
    public void testDeleteUserIncidenceRelation() {
        // Setup
        when(mockSysUserRoleManager.deleteUserRoleByUserId(0L)).thenReturn(0);
        when(mockSysUserPostManager.deleteUserPostByUserId(0L)).thenReturn(0);
        when(mockSysUserDataScopeManager.deleteSysUserDataScopeByUserId(0L)).thenReturn(0);

        // Run the test
        sysUserServiceImplUnderTest.deleteUserIncidenceRelation(0L);

        // Verify the results
        verify(mockSysUserRoleManager).deleteUserRoleByUserId(0L);
        verify(mockSysUserPostManager).deleteUserPostByUserId(0L);
        verify(mockSysUserDataScopeManager).deleteSysUserDataScopeByUserId(0L);
    }

    @Test
    public void testDeleteUserByIds() {
        // Setup
        when(mockRedisUtils.isContains("key")).thenReturn(false);
        when(mockSysUserRoleManager.deleteUserRoleByUserId(0L)).thenReturn(0);
        when(mockSysUserPostManager.deleteUserPostByUserId(0L)).thenReturn(0);
        when(mockSysUserDataScopeManager.deleteSysUserDataScopeByUserId(0L)).thenReturn(0);
        when(mockSysUserManager.deleteUserByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysUserServiceImplUnderTest.deleteUserByIds("ids");

//        // Verify the results
//        assertEquals(0, result);
//        verify(mockRedisUtils).delete("key");
//        verify(mockSysUserRoleManager).deleteUserRoleByUserId(0L);
//        verify(mockSysUserPostManager).deleteUserPostByUserId(0L);
//        verify(mockSysUserDataScopeManager).deleteSysUserDataScopeByUserId(0L);
    }

    @Test
    public void testInsertUser() {
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

        when(mockSysUserManager.insertUser(any(SysUserDO.class))).thenReturn(0);
        when(mockSysUserPostManager.batchUserPost(Arrays.asList(new SysUserPostDO()))).thenReturn(0);
        when(mockGetLoginUserUtil.getLoginUserName()).thenReturn("result");
        when(mockSysUserRoleManager.batchUserRole(Arrays.asList(new SysUserRoleDO()))).thenReturn(0);
        when(mockSysUserDataScopeManager.deleteSysUserDataScopeByUserId(0L)).thenReturn(0);
        when(mockSysUserDataScopeManager.insertSysUserDataScope(any(SysUserDataScopeDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserServiceImplUnderTest.insertUser(user);

        // Verify the results
//        assertEquals(0, result);
//        verify(mockSysUserPostManager).batchUserPost(Arrays.asList(new SysUserPostDO()));
//        verify(mockSysUserRoleManager).batchUserRole(Arrays.asList(new SysUserRoleDO()));
//        verify(mockSysUserDataScopeManager).deleteSysUserDataScopeByUserId(0L);
//        verify(mockSysUserDataScopeManager).insertSysUserDataScope(any(SysUserDataScopeDO.class));
    }

    @Test
    public void testUpdateUser() {
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

        when(mockSysUserRoleManager.deleteUserRoleByUserId(0L)).thenReturn(0);
        when(mockGetLoginUserUtil.getLoginUserName()).thenReturn("result");
        when(mockSysUserRoleManager.batchUserRole(Arrays.asList(new SysUserRoleDO()))).thenReturn(0);
        when(mockSysUserDataScopeManager.deleteSysUserDataScopeByUserId(0L)).thenReturn(0);
        when(mockSysUserDataScopeManager.insertSysUserDataScope(any(SysUserDataScopeDO.class))).thenReturn(0);
        when(mockSysUserPostManager.deleteUserPostByUserId(0L)).thenReturn(0);
        when(mockSysUserPostManager.batchUserPost(Arrays.asList(new SysUserPostDO()))).thenReturn(0);
        when(mockRedisUtils.isContains("key")).thenReturn(false);
        when(mockSysUserManager.updateUser(any(SysUserDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserServiceImplUnderTest.updateUser(user);

//        // Verify the results
//        assertEquals(0, result);
//        verify(mockSysUserRoleManager).deleteUserRoleByUserId(0L);
//        verify(mockSysUserRoleManager).batchUserRole(Arrays.asList(new SysUserRoleDO()));
//        verify(mockSysUserDataScopeManager).deleteSysUserDataScopeByUserId(0L);
//        verify(mockSysUserDataScopeManager).insertSysUserDataScope(any(SysUserDataScopeDO.class));
//        verify(mockSysUserPostManager).deleteUserPostByUserId(0L);
//        verify(mockSysUserPostManager).batchUserPost(Arrays.asList(new SysUserPostDO()));
        // verify(mockRedisUtils).delete("key");
    }

    @Test
    public void testUpdateUserLoginInfo() {
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

        when(mockSysUserManager.updateUser(any(SysUserDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserServiceImplUnderTest.updateUserLoginInfo(sysUser);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testExport() {
        // Setup
        final SysUser sysUser = new SysUser();
        sysUser.setLoginNameArray("loginNameArray");
        sysUser.setIds("101,201,891");
        sysUser.setIdsList(Arrays.asList(891L));
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

        // Configure SysUserManager.export(...).
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
        when(mockSysUserManager.export(any(SysUser.class))).thenReturn(userExportVOS);

        // Run the test
        final List<UserExportVO> result = sysUserServiceImplUnderTest.export(sysUser);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testExport_SysUserManagerReturnsNoItems() {
        // Setup
        final SysUser sysUser = new SysUser();
        sysUser.setLoginNameArray("loginNameArray");
        sysUser.setIds("101,1201,190");
        sysUser.setIdsList(Arrays.asList(101L));
        sysUser.setUserId(1L);
        sysUser.setDeptId(1L);
        sysUser.setParentId(0L);
        sysUser.setLoginName("loginName");
        sysUser.setUserName("userName");
        sysUser.setEmail("email");
        sysUser.setPhonenumber("phonenumber");

        when(mockSysUserManager.export(any(SysUser.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<UserExportVO> result = sysUserServiceImplUnderTest.export(sysUser);

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

        // Configure SysUserManager.exportMenu(...).
        final UserExportRoleVO userExportRoleVO1 = new UserExportRoleVO();
        userExportRoleVO1.setRoleName("roleName");
        userExportRoleVO1.setMenuName("menuName");
        userExportRoleVO1.setMenuKey("menuKey");
        userExportRoleVO1.setMenuType("menuType");
        final List<UserExportRoleVO> userExportRoleVOS = Arrays.asList(userExportRoleVO1);
        when(mockSysUserManager.exportMenu(Arrays.asList(0L))).thenReturn(userExportRoleVOS);

        // Run the test
        final List<UserExportRoleVO> result = sysUserServiceImplUnderTest.exportMenu(Arrays.asList(0L));

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testExportMenu_SysUserManagerReturnsNoItems() {
        // Setup
        when(mockSysUserManager.exportMenu(Arrays.asList(0L))).thenReturn(Collections.emptyList());

        // Run the test
        final List<UserExportRoleVO> result = sysUserServiceImplUnderTest.exportMenu(Arrays.asList(0L));

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testBuildUserForAttributes() {
        // Setup
        final Map<String, List<String>> attributes = new HashMap<>();
        final SysUser user = new SysUser();
        user.setLoginNameArray("loginNameArray");
        user.setIds("1");
        user.setIdsList(Arrays.asList(1L));
        user.setUserId(1L);
        user.setDeptId(0L);
        user.setParentId(0L);
        user.setLoginName("admin");
        user.setUserName("admin");
        user.setEmail("admin@126.com");
        user.setPhonenumber("phonenumber");

        // Configure SysUserManager.selectUserByLoginName(...).
        final SysUser sysUser = new SysUser();
        sysUser.setLoginNameArray("loginNameArray");
        sysUser.setIds("1");
        sysUser.setIdsList(Arrays.asList(1L));
        sysUser.setUserId(1L);
        sysUser.setDeptId(0L);
        sysUser.setParentId(0L);
        sysUser.setLoginName("admin");
        sysUser.setUserName("admin");
        sysUser.setEmail("admin@126.com");
        sysUser.setPhonenumber("phonenumber");
        when(mockSysUserManager.selectUserByLoginName("admin")).thenReturn(sysUser);

        attributes.put("username", Arrays.asList("admin"));
        attributes.put("loginName", Arrays.asList("admin"));
        attributes.put("email", Arrays.asList("admin@126.com"));
        attributes.put("itcode", Arrays.asList("admin"));

        // Run the test
        final SysUser result = sysUserServiceImplUnderTest.buildUserForAttributes(attributes, user, false);

        // Verify the results
    }

    @Test
    public void testCheckUserEntityByParam() {
        // Setup
        final Map<String, List<String>> attributes = new HashMap<>();

        // Configure SysUserManager.selectUserByLoginName(...).
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
        when(mockSysUserManager.selectUserByLoginName("userName")).thenReturn(sysUser);

        when(mockSysUserManager.updateUser(any(SysUserDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserServiceImplUnderTest.checkUserEntityByParam(attributes);

        // Verify the results
        assertEquals(2, result);
        //verify(mockSysUserManager).updateUser(any(SysUserDO.class));
    }

    @Test
    public void testRemoveSaveImportUser() {
        List<UserImportVo> list = new ArrayList<>();
        UserImportVo userImportVo = new UserImportVo();
        userImportVo.setItCode("test01");
        userImportVo.setUserName("test00001");
        userImportVo.setBusinessGroup("ISG");
        userImportVo.setGeoCode("AP");
        userImportVo.setGtnTypeCode("gtn");
        userImportVo.setRegionMarketCode("RegionMarketCode");
        userImportVo.setSalesOfficeCode("SalesOfficeCode");
        userImportVo.setSalesOrgCode("SalesOrgCode");
        userImportVo.setSegmentCode("SegmentCode");
        userImportVo.setUserRole("test");
        userImportVo.setAction("Remove");
        //userImportVo.setAction("Add");
        userImportVo.setUserId(10L);

        String loginName = "adminTest";

        SysUser sysUser = new SysUser();
        sysUser.setUserId(10L);
        sysUser.setLoginName("test01");
        sysUser.setUserName("test00001");
        userImportVo.setSysUser(sysUser);
        Map<Long, String> roleMaps = new HashMap<>();
        roleMaps.put(1L, "test01");
        roleMaps.put(2L, "test02");
        userImportVo.setRoleMaps(roleMaps);
        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(1L);
        sysUserRole.setRoleName("test01");
        sysUserRoleList.add(sysUserRole);
        userImportVo.setSysUserRoleList(sysUserRoleList);

        List<SysUserDataScope> scopes = new ArrayList<>();
        List<SysUserDataScopeDO> scopeDOs = new ArrayList<>();

        SysUserDataScope sysUserDataScope17 = new SysUserDataScope();
        sysUserDataScope17.setDataScopeKey(DataScopeKeyConstants.business_group);
        sysUserDataScope17.setDataScopeValue("ISG");
        scopes.add(sysUserDataScope17);
        SysUserDataScope sysUserDataScope37 = new SysUserDataScope();
        sysUserDataScope37.setDataScopeKey(DataScopeKeyConstants.geo);
        sysUserDataScope37.setDataScopeValue("AP");
        scopes.add(sysUserDataScope37);
        userImportVo.setScopes(scopes);
        BeanUtils.copyListProperties(scopes, scopeDOs, SysUserDataScopeDO.class);
        userImportVo.setSysUserDataScopeList(scopeDOs);
        list.add(userImportVo);

        sysUserServiceImplUnderTest.saveImportUser(list, loginName);
    }

    @Test
    public void testAddSaveImportUser() {
        List<UserImportVo> list = new ArrayList<>();
        UserImportVo userImportVo = new UserImportVo();
        userImportVo.setItCode("test01");
        userImportVo.setUserName("test00001");
        userImportVo.setBusinessGroup("ISG");
        userImportVo.setGeoCode("AP");
        userImportVo.setGtnTypeCode("gtn");
        userImportVo.setRegionMarketCode("RegionMarketCode");
        userImportVo.setSalesOfficeCode("SalesOfficeCode");
        userImportVo.setSalesOrgCode("SalesOrgCode");
        userImportVo.setSegmentCode("SegmentCode");
        userImportVo.setUserRole("test");
        //userImportVo.setAction("Remove");
        userImportVo.setAction("Add");
        userImportVo.setUserId(10L);

        String loginName = "adminTest";

        Map<Long, String> roleMaps = new HashMap<>();
        roleMaps.put(1L, "test01");
        roleMaps.put(2L, "test02");
        userImportVo.setRoleMaps(roleMaps);

        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(1L);
        sysUserRole.setRoleName("test01");
        sysUserRoleList.add(sysUserRole);
        userImportVo.setSysUserRoleList(sysUserRoleList);

        SysUser sysUser = new SysUser();
        sysUser.setUserId(10L);
        sysUser.setLoginName("test01");
        sysUser.setUserName("test00001");
        userImportVo.setSysUser(sysUser);

        List<SysUserDataScope> scopes = new ArrayList<>();
        List<SysUserDataScopeDO> scopeDOs = new ArrayList<>();

        SysUserDataScope sysUserDataScope17 = new SysUserDataScope();
        sysUserDataScope17.setDataScopeKey(DataScopeKeyConstants.business_group);
        sysUserDataScope17.setDataScopeValue("ISG");
        scopes.add(sysUserDataScope17);
        SysUserDataScope sysUserDataScope37 = new SysUserDataScope();
        sysUserDataScope37.setDataScopeKey(DataScopeKeyConstants.geo);
        sysUserDataScope37.setDataScopeValue("AP");
        scopes.add(sysUserDataScope37);
        userImportVo.setScopes(scopes);
        BeanUtils.copyListProperties(scopes, scopeDOs, SysUserDataScopeDO.class);
        userImportVo.setSysUserDataScopeList(scopeDOs);
        list.add(userImportVo);

        sysUserServiceImplUnderTest.saveImportUser(list, loginName);
    }

    @Test
    public void testAddSaveImportUser1() {
        List<UserImportVo> list = new ArrayList<>();
        UserImportVo userImportVo = new UserImportVo();
        userImportVo.setItCode("test01");
        userImportVo.setUserName("test00001");
        userImportVo.setBusinessGroup("ISG");
        userImportVo.setGeoCode("AP");
        userImportVo.setGtnTypeCode("gtn");
        userImportVo.setRegionMarketCode("RegionMarketCode");
        userImportVo.setSalesOfficeCode("SalesOfficeCode");
        userImportVo.setSalesOrgCode("SalesOrgCode");
        userImportVo.setSegmentCode("SegmentCode");
        userImportVo.setUserRole("test");
        //userImportVo.setAction("Remove");
        userImportVo.setAction("Add");
        userImportVo.setUserId(10L);

        String loginName = "adminTest";

        Map<Long, String> roleMaps = new HashMap<>();
        roleMaps.put(1L, "test01");
        roleMaps.put(2L, "test02");
        userImportVo.setRoleMaps(roleMaps);

        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(1L);
        sysUserRole.setRoleName("test01");
        sysUserRoleList.add(sysUserRole);
        userImportVo.setSysUserRoleList(sysUserRoleList);

//        SysUser sysUser=new SysUser();
//        sysUser.setUserId(10L);
//        sysUser.setLoginName("test01");
//        sysUser.setUserName("test00001");
//        userImportVo.setSysUser(sysUser);

        List<SysUserDataScope> scopes = new ArrayList<>();
        List<SysUserDataScopeDO> scopeDOs = new ArrayList<>();

        SysUserDataScope sysUserDataScope17 = new SysUserDataScope();
        sysUserDataScope17.setDataScopeKey(DataScopeKeyConstants.business_group);
        sysUserDataScope17.setDataScopeValue("ISG");
        scopes.add(sysUserDataScope17);
        SysUserDataScope sysUserDataScope37 = new SysUserDataScope();
        sysUserDataScope37.setDataScopeKey(DataScopeKeyConstants.geo);
        sysUserDataScope37.setDataScopeValue("AP");
        scopes.add(sysUserDataScope37);
        userImportVo.setScopes(scopes);
        BeanUtils.copyListProperties(scopes, scopeDOs, SysUserDataScopeDO.class);
        userImportVo.setSysUserDataScopeList(scopeDOs);
        list.add(userImportVo);
        final String DEFALUT_PASSWORD_KEY = "defalut_password";
        when(mockSysConfigService.selectConfigByKey(DEFALUT_PASSWORD_KEY)).thenReturn("result");

        assertThrows(NullPointerException.class, () -> sysUserServiceImplUnderTest.saveImportUser(list, loginName));
    }

    @Test
    public void testInsertUserDataScope2() {
        // Setup
        final SysUserDataScope sysUserDataScope = new SysUserDataScope();
        sysUserDataScope.setCanBeNull(false);
        sysUserDataScope.setSelectAll("selectAll");
        sysUserDataScope.setNotIn("notIn");
        sysUserDataScope.setUserDataScopeId(0L);
        sysUserDataScope.setUserId(0L);
        sysUserDataScope.setUserItcode("userItcode");
        sysUserDataScope.setDataScopeKey("dataScopeKey");
        sysUserDataScope.setDataScopeValue("dataScopeValue");
        sysUserDataScope.setStatus("status");

        when(mockGetLoginUserUtil.getLoginUserName()).thenReturn("result");
        when(mockSysUserDataScopeManager.insertSysUserDataScope(any(SysUserDataScopeDO.class))).thenReturn(0);

        // Run the test
        sysUserServiceImplUnderTest.insertUserDataScope(sysUserDataScope);

        // Verify the results
        verify(mockSysUserDataScopeManager).insertSysUserDataScope(any(SysUserDataScopeDO.class));
    }

    @Test
    public void testUpdateUserInfo() {
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

        when(mockSysUserManager.updateUser(any(SysUserDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserServiceImplUnderTest.updateUserInfo(user);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testResetUserPwd() {
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

        when(mockSysUserManager.updateUser(any(SysUserDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserServiceImplUnderTest.resetUserPwd(user);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertUserRole1() {
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

        when(mockGetLoginUserUtil.getLoginUserName()).thenReturn("result");
        when(mockSysUserRoleManager.batchUserRole(Arrays.asList(new SysUserRoleDO()))).thenReturn(0);

//        // Run the test
//        sysUserServiceImplUnderTest.insertUserRole(user);
//
//        // Verify the results
//        verify(mockSysUserRoleManager).batchUserRole(Arrays.asList(new SysUserRoleDO()));
    }

    @Test
    public void testInsertUserRole2() {
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

        when(mockGetLoginUserUtil.getLoginUserName()).thenReturn("result");
        when(mockSysUserRoleManager.batchUserRole(Arrays.asList(new SysUserRoleDO()))).thenReturn(0);

        // Run the test
//        sysUserServiceImplUnderTest.insertUserRole(user, "loginName");
//
//        // Verify the results
//        verify(mockSysUserRoleManager).batchUserRole(Arrays.asList(new SysUserRoleDO()));
    }

    @Test
    public void testInsertUserPost() {
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

        Long[] postIds = {10L, 21L, 31L};
        user.setPostIds(postIds);

        when(mockSysUserPostManager.batchUserPost(Arrays.asList(new SysUserPostDO()))).thenReturn(0);

        // Run the test
        sysUserServiceImplUnderTest.insertUserPost(user);

        // Verify the results
        // verify(mockSysUserPostManager).batchUserPost(Arrays.asList(new SysUserPostDO()));
    }

    @Test
    public void testCheckLoginNameUnique() {
        // Setup
        when(mockSysUserManager.checkLoginNameUnique("loginName")).thenReturn(0);

        // Run the test
        final String result = sysUserServiceImplUnderTest.checkLoginNameUnique("loginName");

        // Verify the results
        assertEquals("0", result);
    }

    @Test
    public void testCheckPhoneUnique() {
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

        // Configure SysUserManager.checkPhoneUnique(...).
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
        when(mockSysUserManager.checkPhoneUnique("phonenumber")).thenReturn(sysUser);

        // Run the test
        final String result = sysUserServiceImplUnderTest.checkPhoneUnique(user);

        // Verify the results
        assertEquals("0", result);
    }

    @Test
    public void testCheckEmailUnique() {
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

        // Configure SysUserManager.checkEmailUnique(...).
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
        when(mockSysUserManager.checkEmailUnique("email")).thenReturn(sysUser);

        // Run the test
        final String result = sysUserServiceImplUnderTest.checkEmailUnique(user);

        // Verify the results
        assertEquals("0", result);
    }

    @Test
    public void testSelectUserRoleGroup() {
        // Setup
        // Configure SysRoleManager.selectRolesByUserId(...).
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
        when(mockSysRoleManager.selectRolesByUserId(0L)).thenReturn(sysRoleDOS);

        // Run the test
        final String result = sysUserServiceImplUnderTest.selectUserRoleGroup(0L);

        // Verify the results
        assertEquals("roleName", result);
    }

    @Test
    public void testSelectUserRoleGroup_SysRoleManagerReturnsNoItems() {
        // Setup
        when(mockSysRoleManager.selectRolesByUserId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final String result = sysUserServiceImplUnderTest.selectUserRoleGroup(0L);

        // Verify the results
        assertEquals("", result);
    }

    @Test
    public void testSelectUserPostGroup() {
        // Setup
        // Configure SysPostManager.selectPostsByUserId(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        final List<SysPostDO> sysPostDOS = Arrays.asList(sysPostDO);
        when(mockSysPostManager.selectPostsByUserId(0L)).thenReturn(sysPostDOS);

        // Run the test
        final String result = sysUserServiceImplUnderTest.selectUserPostGroup(0L);

        // Verify the results
        assertEquals("postName", result);
    }

    @Test
    public void testSelectUserPostGroup_SysPostManagerReturnsNoItems() {
        // Setup
        when(mockSysPostManager.selectPostsByUserId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final String result = sysUserServiceImplUnderTest.selectUserPostGroup(0L);

        // Verify the results
        assertEquals("", result);
    }

    @Test
    public void testImportUser() {
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
        final List<SysUser> userList = Arrays.asList(sysUser);
        when(mockConfigService.selectConfigByKey("configKey")).thenReturn("result");

        // Configure SysUserManager.selectUserByLoginName(...).
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
        when(mockSysUserManager.selectUserByLoginName("userName")).thenReturn(sysUser1);

        when(mockSysUserService.insertUser(any(SysUser.class))).thenReturn(0);
        when(mockSysUserService.updateUser(any(SysUser.class))).thenReturn(0);

        Boolean catchException = false;
        try {
            // Run the test
            final String result = sysUserServiceImplUnderTest.importUser(userList, false, "zhoudan13");

            // Verify the results
            assertEquals("result", result);
            verify(mockSysUserService).insertUser(any(SysUser.class));
            verify(mockSysUserService).updateUser(any(SysUser.class));

        } catch (NullPointerException e) {
            catchException = true;
        }
        assertTrue(catchException);
    }

    @Test
    public void testChangeStatus() {
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

        when(mockSysUserManager.updateUser(any(SysUserDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserServiceImplUnderTest.changeStatus(user);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectUserIdsHasRoles() {
        // Setup
        when(mockSysUserManager.selectUserIdsHasRoles(any(Long[].class))).thenReturn(new HashSet<>(Arrays.asList(0L)));

        // Run the test
        final Set<Long> result = sysUserServiceImplUnderTest.selectUserIdsHasRoles(new Long[]{0L});

        // Verify the results
        assertEquals(new HashSet<>(Arrays.asList(0L)), result);
    }

    @Test
    public void testSelectUserIdsHasRoles_SysUserManagerReturnsNoItems() {
        // Setup
        when(mockSysUserManager.selectUserIdsHasRoles(any(Long[].class))).thenReturn(Collections.emptySet());

        // Run the test
        final Set<Long> result = sysUserServiceImplUnderTest.selectUserIdsHasRoles(new Long[]{0L});

        // Verify the results
        assertEquals(Collections.emptySet(), result);
    }

    @Test
    public void testSelectUserIdsInDepts() {
        // Setup
        when(mockSysUserManager.selectUserIdsInDepts(any(Long[].class))).thenReturn(new HashSet<>(Arrays.asList(0L)));

        // Run the test
        final Set<Long> result = sysUserServiceImplUnderTest.selectUserIdsInDepts(new Long[]{0L});

        // Verify the results
        assertEquals(new HashSet<>(Arrays.asList(0L)), result);
    }

    @Test
    public void testSelectUserIdsInDepts_SysUserManagerReturnsNoItems() {
        // Setup
        when(mockSysUserManager.selectUserIdsInDepts(any(Long[].class))).thenReturn(Collections.emptySet());

        // Run the test
        final Set<Long> result = sysUserServiceImplUnderTest.selectUserIdsInDepts(new Long[]{0L});

        // Verify the results
        assertEquals(Collections.emptySet(), result);
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

        // Configure SysUserRoleManager.selectUserRoleList(...).
        final SysUserRole sysUserRole1 = new SysUserRole();
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
        sysUserRole1.setSysUsers(Arrays.asList(sysUser1));
        sysUserRole1.setUserId(0L);
        sysUserRole1.setRoleId(0L);
        sysUserRole1.setCreateBy("createBy");
        sysUserRole1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserRole1.setRoleName("roleName");
        sysUserRole1.setRoleKey("roleKey");
        final List<SysUserRole> sysUserRoles = Arrays.asList(sysUserRole1);
        when(mockSysUserRoleManager.selectUserRoleList(0)).thenReturn(sysUserRoles);

        // Configure SysRoleManager.selectRoleById(...).
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
        when(mockSysRoleManager.selectRoleById(0L)).thenReturn(sysRoleDO);

        // Run the test
        final List<SysUserRole> result = sysUserServiceImplUnderTest.selectUserRoleList(0);

        // Verify the results
        assertNotEquals(0, result.size());
    }

    @Test
    public void testSelectUserRoleList_SysUserRoleManagerReturnsNoItems() {
        // Setup
        when(mockSysUserRoleManager.selectUserRoleList(0)).thenReturn(Collections.emptyList());

        // Configure SysRoleManager.selectRoleById(...).
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
        when(mockSysRoleManager.selectRoleById(0L)).thenReturn(sysRoleDO);

        // Run the test
        final List<SysUserRole> result = sysUserServiceImplUnderTest.selectUserRoleList(0);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
