package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysDept;
import com.microservices.otmp.system.domain.entity.SysDeptDO;
import com.microservices.otmp.system.mapper.SysDeptMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysDeptManagerImplTest {

    @Mock
    private SysDeptMapper mockSysDeptMapper;

    @InjectMocks
    private SysDeptManagerImpl sysDeptManagerImplUnderTest;

    @Test
    public void testSelectDeptCount() {
        // Setup
        final SysDeptDO dept = new SysDeptDO();
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

        when(mockSysDeptMapper.selectDeptCount(any(SysDeptDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDeptManagerImplUnderTest.selectDeptCount(dept);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckDeptExistUser() {
        // Setup
        when(mockSysDeptMapper.checkDeptExistUser(0L)).thenReturn(0);

        // Run the test
        final int result = sysDeptManagerImplUnderTest.checkDeptExistUser(0L);

        // Verify the results
        assertEquals(0, result);
    }

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

        // Configure SysDeptMapper.selectDeptList(...).
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
        when(mockSysDeptMapper.selectDeptList(any(SysDept.class))).thenReturn(sysDeptDOS);

        // Run the test
        final List<SysDeptDO> result = sysDeptManagerImplUnderTest.selectDeptList(dept);

        // Verify the results
    }

    @Test
    public void testSelectDeptList_SysDeptMapperReturnsNoItems() {
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

        when(mockSysDeptMapper.selectDeptList(any(SysDept.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDeptDO> result = sysDeptManagerImplUnderTest.selectDeptList(dept);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDeleteDeptById() {
        // Setup
        when(mockSysDeptMapper.deleteDeptById(0L)).thenReturn(0);

        // Run the test
        final int result = sysDeptManagerImplUnderTest.deleteDeptById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertDept() {
        // Setup
        final SysDeptDO dept = new SysDeptDO();
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

        when(mockSysDeptMapper.insertDept(any(SysDeptDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDeptManagerImplUnderTest.insertDept(dept);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDept() {
        // Setup
        final SysDeptDO dept = new SysDeptDO();
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

        when(mockSysDeptMapper.updateDept(any(SysDeptDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDeptManagerImplUnderTest.updateDept(dept);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDeptChildren() {
        // Setup
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
        final List<SysDeptDO> depts = Arrays.asList(sysDeptDO);
        when(mockSysDeptMapper.updateDeptChildren(Arrays.asList(new SysDeptDO()))).thenReturn(0);

        // Run the test
        final int result = sysDeptManagerImplUnderTest.updateDeptChildren(depts);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectDeptById() {
        // Setup
        // Configure SysDeptMapper.selectDeptById(...).
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
        when(mockSysDeptMapper.selectDeptById(0L)).thenReturn(sysDeptDO);

        // Run the test
        final SysDeptDO result = sysDeptManagerImplUnderTest.selectDeptById(0L);

        // Verify the results
    }

    @Test
    public void testCheckDeptNameUnique() {
        // Setup
        // Configure SysDeptMapper.checkDeptNameUnique(...).
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
        when(mockSysDeptMapper.checkDeptNameUnique("deptName", 0L)).thenReturn(sysDeptDO);

        // Run the test
        final SysDeptDO result = sysDeptManagerImplUnderTest.checkDeptNameUnique("deptName", 0L);

        // Verify the results
    }

    @Test
    public void testSelectRoleDeptTree() {
        // Setup
        when(mockSysDeptMapper.selectRoleDeptTree(0L)).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<String> result = sysDeptManagerImplUnderTest.selectRoleDeptTree(0L);

        // Verify the results
        assertEquals(Arrays.asList("value"), result);
    }

    @Test
    public void testSelectRoleDeptTree_SysDeptMapperReturnsNoItems() {
        // Setup
        when(mockSysDeptMapper.selectRoleDeptTree(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<String> result = sysDeptManagerImplUnderTest.selectRoleDeptTree(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testUpdateDeptStatus() {
        // Setup
        final SysDeptDO dept = new SysDeptDO();
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

        // Run the test
        sysDeptManagerImplUnderTest.updateDeptStatus(dept);

        // Verify the results
        verify(mockSysDeptMapper).updateDeptStatus(any(SysDeptDO.class));
    }

    @Test
    public void testSelectChildrenDeptById() {
        // Setup
        // Configure SysDeptMapper.selectChildrenDeptById(...).
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
        when(mockSysDeptMapper.selectChildrenDeptById(0L)).thenReturn(sysDeptDOS);

        // Run the test
        final List<SysDeptDO> result = sysDeptManagerImplUnderTest.selectChildrenDeptById(0L);

        // Verify the results
    }

    @Test
    public void testSelectChildrenDeptById_SysDeptMapperReturnsNoItems() {
        // Setup
        when(mockSysDeptMapper.selectChildrenDeptById(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDeptDO> result = sysDeptManagerImplUnderTest.selectChildrenDeptById(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectRoleDeptIds() {
        // Setup
        when(mockSysDeptMapper.selectRoleDeptIds(0L)).thenReturn(new HashSet<>(Arrays.asList("value")));

        // Run the test
        final Set<String> result = sysDeptManagerImplUnderTest.selectRoleDeptIds(0L);

        // Verify the results
        assertEquals(new HashSet<>(Arrays.asList("value")), result);
    }

    @Test
    public void testSelectRoleDeptIds_SysDeptMapperReturnsNoItems() {
        // Setup
        when(mockSysDeptMapper.selectRoleDeptIds(0L)).thenReturn(Collections.emptySet());

        // Run the test
        final Set<String> result = sysDeptManagerImplUnderTest.selectRoleDeptIds(0L);

        // Verify the results
        assertEquals(Collections.emptySet(), result);
    }
}
