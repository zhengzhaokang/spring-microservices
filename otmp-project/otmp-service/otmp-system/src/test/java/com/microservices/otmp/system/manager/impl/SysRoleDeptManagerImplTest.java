package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.SysRoleDeptDO;
import com.microservices.otmp.system.mapper.SysRoleDeptMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysRoleDeptManagerImplTest {

    @Mock
    private SysRoleDeptMapper mockSysRoleDeptMapper;

    @InjectMocks
    private SysRoleDeptManagerImpl sysRoleDeptManagerImplUnderTest;

    @Test
    public void testDeleteRoleDeptByRoleId() {
        // Setup
        when(mockSysRoleDeptMapper.deleteRoleDeptByRoleId(0L)).thenReturn(0);

        // Run the test
        final int result = sysRoleDeptManagerImplUnderTest.deleteRoleDeptByRoleId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteRoleDept() {
        // Setup
        when(mockSysRoleDeptMapper.deleteRoleDept(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysRoleDeptManagerImplUnderTest.deleteRoleDept(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectCountRoleDeptByDeptId() {
        // Setup
        when(mockSysRoleDeptMapper.selectCountRoleDeptByDeptId(0L)).thenReturn(0);

        // Run the test
        final int result = sysRoleDeptManagerImplUnderTest.selectCountRoleDeptByDeptId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testBatchRoleDept() {
        // Setup
        final SysRoleDeptDO sysRoleDeptDO = new SysRoleDeptDO();
        sysRoleDeptDO.setRoleId(0L);
        sysRoleDeptDO.setDeptId(0L);
        final List<SysRoleDeptDO> roleDeptList = Arrays.asList(sysRoleDeptDO);
        when(mockSysRoleDeptMapper.batchRoleDept(Arrays.asList(new SysRoleDeptDO()))).thenReturn(0);

        // Run the test
        final int result = sysRoleDeptManagerImplUnderTest.batchRoleDept(roleDeptList);

        // Verify the results
        assertEquals(0, result);
    }
}
