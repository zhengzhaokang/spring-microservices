package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.domain.Ztree;
import com.microservices.otmp.system.domain.SysDept;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.entity.SysDeptDO;
import com.microservices.otmp.system.manager.SysDeptManager;
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
public class SysDeptServiceImplTest {

    @Mock
    private SysDeptManager mockDeptManager;

    @InjectMocks
    private SysDeptServiceImpl sysDeptServiceImplUnderTest;

    @Test
    public void testSelectDeptList() {
        // Setup
        final SysDept dept = new SysDept();
        dept.setDeptId(0L);
        dept.setParentId(0L);
        dept.setAncestors("ancestors");
        dept.setDeptName("deptName");
        dept.setOrderNum("orderNum");
        dept.setLeader("leader");
        dept.setLeaderId(0L);
        dept.setPhone("phone");
        dept.setEmail("email");
        dept.setStatus("status");

        // Configure SysDeptManager.selectDeptList(...).
        final SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setDeptId(0L);
        sysDeptDO.setParentId(0L);
        sysDeptDO.setAncestors("ancestors");
        sysDeptDO.setDeptName("deptName");
        sysDeptDO.setOrderNum("orderNum");
        sysDeptDO.setLeader("leader");
        sysDeptDO.setLeaderId(0L);
        sysDeptDO.setPhone("phone");
        sysDeptDO.setEmail("email");
        sysDeptDO.setStatus("status");
        final List<SysDeptDO> sysDeptDOS = Arrays.asList(sysDeptDO);
        when(mockDeptManager.selectDeptList(any(SysDept.class))).thenReturn(sysDeptDOS);

        // Run the test
        final List<SysDept> result = sysDeptServiceImplUnderTest.selectDeptList(dept);

        // Verify the results
    }

    @Test
    public void testSelectDeptList_SysDeptManagerReturnsNoItems() {
        // Setup
        final SysDept dept = new SysDept();
        dept.setDeptId(0L);
        dept.setParentId(0L);
        dept.setAncestors("ancestors");
        dept.setDeptName("deptName");
        dept.setOrderNum("orderNum");
        dept.setLeader("leader");
        dept.setLeaderId(0L);
        dept.setPhone("phone");
        dept.setEmail("email");
        dept.setStatus("status");

        when(mockDeptManager.selectDeptList(any(SysDept.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDept> result = sysDeptServiceImplUnderTest.selectDeptList(dept);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectDeptTree() {
        // Setup
        final SysDept dept = new SysDept();
        dept.setDeptId(0L);
        dept.setParentId(0L);
        dept.setAncestors("ancestors");
        dept.setDeptName("deptName");
        dept.setOrderNum("orderNum");
        dept.setLeader("leader");
        dept.setLeaderId(0L);
        dept.setPhone("phone");
        dept.setEmail("email");
        dept.setStatus("status");

        // Configure SysDeptManager.selectDeptList(...).
        final SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setDeptId(0L);
        sysDeptDO.setParentId(0L);
        sysDeptDO.setAncestors("ancestors");
        sysDeptDO.setDeptName("deptName");
        sysDeptDO.setOrderNum("orderNum");
        sysDeptDO.setLeader("leader");
        sysDeptDO.setLeaderId(0L);
        sysDeptDO.setPhone("phone");
        sysDeptDO.setEmail("email");
        sysDeptDO.setStatus("status");
        final List<SysDeptDO> sysDeptDOS = Arrays.asList(sysDeptDO);
        when(mockDeptManager.selectDeptList(any(SysDept.class))).thenReturn(sysDeptDOS);

        // Run the test
        final List<Ztree> result = sysDeptServiceImplUnderTest.selectDeptTree(dept);

        // Verify the results
    }

    @Test
    public void testSelectDeptTree_SysDeptManagerReturnsNoItems() {
        // Setup
        final SysDept dept = new SysDept();
        dept.setDeptId(0L);
        dept.setParentId(0L);
        dept.setAncestors("ancestors");
        dept.setDeptName("deptName");
        dept.setOrderNum("orderNum");
        dept.setLeader("leader");
        dept.setLeaderId(0L);
        dept.setPhone("phone");
        dept.setEmail("email");
        dept.setStatus("status");

        when(mockDeptManager.selectDeptList(any(SysDept.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<Ztree> result = sysDeptServiceImplUnderTest.selectDeptTree(dept);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testRoleDeptTreeData() {
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

        // Configure SysDeptManager.selectDeptList(...).
        final SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setDeptId(0L);
        sysDeptDO.setParentId(0L);
        sysDeptDO.setAncestors("ancestors");
        sysDeptDO.setDeptName("deptName");
        sysDeptDO.setOrderNum("orderNum");
        sysDeptDO.setLeader("leader");
        sysDeptDO.setLeaderId(0L);
        sysDeptDO.setPhone("phone");
        sysDeptDO.setEmail("email");
        sysDeptDO.setStatus("status");
        final List<SysDeptDO> sysDeptDOS = Arrays.asList(sysDeptDO);
        when(mockDeptManager.selectDeptList(any(SysDept.class))).thenReturn(sysDeptDOS);

        when(mockDeptManager.selectRoleDeptTree(0L)).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<Ztree> result = sysDeptServiceImplUnderTest.roleDeptTreeData(role);

        // Verify the results
    }

    @Test
    public void testRoleDeptTreeData_SysDeptManagerSelectDeptListReturnsNoItems() {
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

        when(mockDeptManager.selectDeptList(any(SysDept.class))).thenReturn(Collections.emptyList());
        when(mockDeptManager.selectRoleDeptTree(0L)).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<Ztree> result = sysDeptServiceImplUnderTest.roleDeptTreeData(role);

        // Verify the results
    }

    @Test
    public void testRoleDeptTreeData_SysDeptManagerSelectRoleDeptTreeReturnsNoItems() {
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

        // Configure SysDeptManager.selectDeptList(...).
        final SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setDeptId(0L);
        sysDeptDO.setParentId(0L);
        sysDeptDO.setAncestors("ancestors");
        sysDeptDO.setDeptName("deptName");
        sysDeptDO.setOrderNum("orderNum");
        sysDeptDO.setLeader("leader");
        sysDeptDO.setLeaderId(0L);
        sysDeptDO.setPhone("phone");
        sysDeptDO.setEmail("email");
        sysDeptDO.setStatus("status");
        final List<SysDeptDO> sysDeptDOS = Arrays.asList(sysDeptDO);
        when(mockDeptManager.selectDeptList(any(SysDept.class))).thenReturn(sysDeptDOS);

        when(mockDeptManager.selectRoleDeptTree(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<Ztree> result = sysDeptServiceImplUnderTest.roleDeptTreeData(role);

        // Verify the results
    }

    @Test
    public void testInitZtree1() {
        // Setup
        final SysDept sysDept = new SysDept();
        sysDept.setDeptId(0L);
        sysDept.setParentId(0L);
        sysDept.setAncestors("ancestors");
        sysDept.setDeptName("deptName");
        sysDept.setOrderNum("orderNum");
        sysDept.setLeader("leader");
        sysDept.setLeaderId(0L);
        sysDept.setPhone("phone");
        sysDept.setEmail("email");
        sysDept.setStatus("status");
        final List<SysDept> deptList = Arrays.asList(sysDept);

        // Run the test
        final List<Ztree> result = sysDeptServiceImplUnderTest.initZtree(deptList);

        // Verify the results
    }

    @Test
    public void testInitZtree2() {
        // Setup
        final SysDept sysDept = new SysDept();
        sysDept.setDeptId(0L);
        sysDept.setParentId(0L);
        sysDept.setAncestors("ancestors");
        sysDept.setDeptName("deptName");
        sysDept.setOrderNum("orderNum");
        sysDept.setLeader("leader");
        sysDept.setLeaderId(0L);
        sysDept.setPhone("phone");
        sysDept.setEmail("email");
        sysDept.setStatus("status");
        final List<SysDept> deptList = Arrays.asList(sysDept);

        // Run the test
        final List<Ztree> result = sysDeptServiceImplUnderTest.initZtree(deptList, Arrays.asList("value"));

        // Verify the results
    }

    @Test
    public void testSelectDeptCount() {
        // Setup
        when(mockDeptManager.selectDeptCount(any(SysDeptDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDeptServiceImplUnderTest.selectDeptCount(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckDeptExistUser() {
        // Setup
        when(mockDeptManager.checkDeptExistUser(0L)).thenReturn(0);

        // Run the test
        final boolean result = sysDeptServiceImplUnderTest.checkDeptExistUser(0L);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testDeleteDeptById() {
        // Setup
        when(mockDeptManager.deleteDeptById(0L)).thenReturn(0);

        // Run the test
        final int result = sysDeptServiceImplUnderTest.deleteDeptById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertDept() {
        // Setup
        final SysDept dept = new SysDept();
        dept.setDeptId(0L);
        dept.setParentId(0L);
        dept.setAncestors("ancestors");
        dept.setDeptName("deptName");
        dept.setOrderNum("orderNum");
        dept.setLeader("leader");
        dept.setLeaderId(0L);
        dept.setPhone("phone");
        dept.setEmail("email");
        dept.setStatus("status");

        // Configure SysDeptManager.selectDeptById(...).
        final SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setDeptId(0L);
        sysDeptDO.setParentId(0L);
        sysDeptDO.setAncestors("ancestors");
        sysDeptDO.setDeptName("deptName");
        sysDeptDO.setOrderNum("orderNum");
        sysDeptDO.setLeader("leader");
        sysDeptDO.setLeaderId(0L);
        sysDeptDO.setPhone("phone");
        sysDeptDO.setEmail("email");
        sysDeptDO.setStatus("status");
        when(mockDeptManager.selectDeptById(0L)).thenReturn(sysDeptDO);

        when(mockDeptManager.insertDept(any(SysDeptDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDeptServiceImplUnderTest.insertDept(dept);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDept() {
        // Setup
        final SysDept dept = new SysDept();
        dept.setDeptId(0L);
        dept.setParentId(0L);
        dept.setAncestors("ancestors");
        dept.setDeptName("deptName");
        dept.setOrderNum("orderNum");
        dept.setLeader("leader");
        dept.setLeaderId(0L);
        dept.setPhone("phone");
        dept.setEmail("email");
        dept.setStatus("status");

        // Configure SysDeptManager.selectDeptById(...).
        final SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setDeptId(0L);
        sysDeptDO.setParentId(0L);
        sysDeptDO.setAncestors("ancestors");
        sysDeptDO.setDeptName("deptName");
        sysDeptDO.setOrderNum("orderNum");
        sysDeptDO.setLeader("leader");
        sysDeptDO.setLeaderId(0L);
        sysDeptDO.setPhone("phone");
        sysDeptDO.setEmail("email");
        sysDeptDO.setStatus("status");
        when(mockDeptManager.selectDeptById(0L)).thenReturn(sysDeptDO);

        // Configure SysDeptManager.selectChildrenDeptById(...).
        final SysDeptDO sysDeptDO1 = new SysDeptDO();
        sysDeptDO1.setDeptId(0L);
        sysDeptDO1.setParentId(0L);
        sysDeptDO1.setAncestors("ancestors");
        sysDeptDO1.setDeptName("deptName");
        sysDeptDO1.setOrderNum("orderNum");
        sysDeptDO1.setLeader("leader");
        sysDeptDO1.setLeaderId(0L);
        sysDeptDO1.setPhone("phone");
        sysDeptDO1.setEmail("email");
        sysDeptDO1.setStatus("status");
        final List<SysDeptDO> sysDeptDOS = Arrays.asList(sysDeptDO1);
        when(mockDeptManager.selectChildrenDeptById(0L)).thenReturn(sysDeptDOS);

        when(mockDeptManager.updateDeptChildren(Arrays.asList(new SysDeptDO()))).thenReturn(0);
        when(mockDeptManager.updateDept(any(SysDeptDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDeptServiceImplUnderTest.updateDept(dept);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDept_SysDeptManagerSelectChildrenDeptByIdReturnsNoItems() {
        // Setup
        final SysDept dept = new SysDept();
        dept.setDeptId(0L);
        dept.setParentId(0L);
        dept.setAncestors("ancestors");
        dept.setDeptName("deptName");
        dept.setOrderNum("orderNum");
        dept.setLeader("leader");
        dept.setLeaderId(0L);
        dept.setPhone("phone");
        dept.setEmail("email");
        dept.setStatus("status");

        // Configure SysDeptManager.selectDeptById(...).
        final SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setDeptId(0L);
        sysDeptDO.setParentId(0L);
        sysDeptDO.setAncestors("ancestors");
        sysDeptDO.setDeptName("deptName");
        sysDeptDO.setOrderNum("orderNum");
        sysDeptDO.setLeader("leader");
        sysDeptDO.setLeaderId(0L);
        sysDeptDO.setPhone("phone");
        sysDeptDO.setEmail("email");
        sysDeptDO.setStatus("status");
        when(mockDeptManager.selectDeptById(0L)).thenReturn(sysDeptDO);

        when(mockDeptManager.selectChildrenDeptById(0L)).thenReturn(Collections.emptyList());
        when(mockDeptManager.updateDeptChildren(Arrays.asList(new SysDeptDO()))).thenReturn(0);
        when(mockDeptManager.updateDept(any(SysDeptDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDeptServiceImplUnderTest.updateDept(dept);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDeptChildren() {
        // Setup
        // Configure SysDeptManager.selectChildrenDeptById(...).
        final SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setDeptId(0L);
        sysDeptDO.setParentId(0L);
        sysDeptDO.setAncestors("ancestors");
        sysDeptDO.setDeptName("deptName");
        sysDeptDO.setOrderNum("orderNum");
        sysDeptDO.setLeader("leader");
        sysDeptDO.setLeaderId(0L);
        sysDeptDO.setPhone("phone");
        sysDeptDO.setEmail("email");
        sysDeptDO.setStatus("status");
        final List<SysDeptDO> sysDeptDOS = Arrays.asList(sysDeptDO);
        when(mockDeptManager.selectChildrenDeptById(0L)).thenReturn(sysDeptDOS);

        when(mockDeptManager.updateDeptChildren(Arrays.asList(new SysDeptDO()))).thenReturn(0);

        // Run the test
        sysDeptServiceImplUnderTest.updateDeptChildren(0L, "newAncestors", "oldAncestors");

    }

    @Test
    public void testUpdateDeptChildren_SysDeptManagerSelectChildrenDeptByIdReturnsNoItems() {
        // Setup
        when(mockDeptManager.selectChildrenDeptById(0L)).thenReturn(Collections.emptyList());
        when(mockDeptManager.updateDeptChildren(Arrays.asList(new SysDeptDO()))).thenReturn(0);

        // Run the test
        sysDeptServiceImplUnderTest.updateDeptChildren(0L, "newAncestors", "oldAncestors");
    }

    @Test
    public void testSelectDeptById() {
        // Setup
        // Configure SysDeptManager.selectDeptById(...).
        final SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setDeptId(0L);
        sysDeptDO.setParentId(0L);
        sysDeptDO.setAncestors("ancestors");
        sysDeptDO.setDeptName("deptName");
        sysDeptDO.setOrderNum("orderNum");
        sysDeptDO.setLeader("leader");
        sysDeptDO.setLeaderId(0L);
        sysDeptDO.setPhone("phone");
        sysDeptDO.setEmail("email");
        sysDeptDO.setStatus("status");
        when(mockDeptManager.selectDeptById(0L)).thenReturn(sysDeptDO);

        // Run the test
        final SysDept result = sysDeptServiceImplUnderTest.selectDeptById(0L);

        // Verify the results
    }

    @Test
    public void testCheckDeptNameUnique() {
        // Setup
        final SysDept dept = new SysDept();
        dept.setDeptId(0L);
        dept.setParentId(0L);
        dept.setAncestors("ancestors");
        dept.setDeptName("deptName");
        dept.setOrderNum("orderNum");
        dept.setLeader("leader");
        dept.setLeaderId(0L);
        dept.setPhone("phone");
        dept.setEmail("email");
        dept.setStatus("status");

        // Configure SysDeptManager.checkDeptNameUnique(...).
        final SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setDeptId(0L);
        sysDeptDO.setParentId(0L);
        sysDeptDO.setAncestors("ancestors");
        sysDeptDO.setDeptName("deptName");
        sysDeptDO.setOrderNum("orderNum");
        sysDeptDO.setLeader("leader");
        sysDeptDO.setLeaderId(0L);
        sysDeptDO.setPhone("phone");
        sysDeptDO.setEmail("email");
        sysDeptDO.setStatus("status");
        when(mockDeptManager.checkDeptNameUnique("deptName", 0L)).thenReturn(sysDeptDO);

        // Run the test
        final String result = sysDeptServiceImplUnderTest.checkDeptNameUnique(dept);

        // Verify the results
        assertEquals("0", result);
    }

    @Test
    public void testRoleDeptIds() {
        // Setup
        when(mockDeptManager.selectRoleDeptIds(0L)).thenReturn(new HashSet<>(Arrays.asList("value")));

        // Run the test
        final Set<String> result = sysDeptServiceImplUnderTest.roleDeptIds(0L);

        // Verify the results
        assertEquals(new HashSet<>(Arrays.asList("value")), result);
    }

    @Test
    public void testRoleDeptIds_SysDeptManagerReturnsNoItems() {
        // Setup
        when(mockDeptManager.selectRoleDeptIds(0L)).thenReturn(Collections.emptySet());

        // Run the test
        final Set<String> result = sysDeptServiceImplUnderTest.roleDeptIds(0L);

        // Verify the results
        assertEquals(Collections.emptySet(), result);
    }

    @Test
    public void testSelectDeptPage() {
        // Setup
        final SysDept dept = new SysDept();
        dept.setDeptId(0L);
        dept.setParentId(0L);
        dept.setAncestors("ancestors");
        dept.setDeptName("deptName");
        dept.setOrderNum("orderNum");
        dept.setLeader("leader");
        dept.setLeaderId(0L);
        dept.setPhone("phone");
        dept.setEmail("email");
        dept.setStatus("status");

        // Configure SysDeptManager.selectDeptList(...).
        final SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setDeptId(0L);
        sysDeptDO.setParentId(0L);
        sysDeptDO.setAncestors("ancestors");
        sysDeptDO.setDeptName("deptName");
        sysDeptDO.setOrderNum("orderNum");
        sysDeptDO.setLeader("leader");
        sysDeptDO.setLeaderId(0L);
        sysDeptDO.setPhone("phone");
        sysDeptDO.setEmail("email");
        sysDeptDO.setStatus("status");
        final List<SysDeptDO> sysDeptDOS = Arrays.asList(sysDeptDO);
        when(mockDeptManager.selectDeptList(any(SysDept.class))).thenReturn(sysDeptDOS);

        // Run the test
        final PageInfo<SysDept> result = sysDeptServiceImplUnderTest.selectDeptPage(dept);

        // Verify the results
    }

    @Test
    public void testSelectDeptPage_SysDeptManagerReturnsNoItems() {
        // Setup
        final SysDept dept = new SysDept();
        dept.setDeptId(0L);
        dept.setParentId(0L);
        dept.setAncestors("ancestors");
        dept.setDeptName("deptName");
        dept.setOrderNum("orderNum");
        dept.setLeader("leader");
        dept.setLeaderId(0L);
        dept.setPhone("phone");
        dept.setEmail("email");
        dept.setStatus("status");

        when(mockDeptManager.selectDeptList(any(SysDept.class))).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<SysDept> result = sysDeptServiceImplUnderTest.selectDeptPage(dept);

        // Verify the results
    }
}
