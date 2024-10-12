package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.entity.SysUserDataScopeDO;
import com.microservices.otmp.system.mapper.SysUserDataScopeMapper;
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
public class SysUserDataScopeManagerImplTest {

    @Mock
    private SysUserDataScopeMapper mockSysUserDataScopeMapper;

    @InjectMocks
    private SysUserDataScopeManagerImpl sysUserDataScopeManagerImplUnderTest;

    @Test
    public void testSelectSysUserDataScopeByUserDataScopeId() {
        // Setup
        // Configure SysUserDataScopeMapper.selectSysUserDataScopeByUserDataScopeId(...).
        final SysUserDataScopeDO sysUserDataScopeDO = new SysUserDataScopeDO();
        sysUserDataScopeDO.setSelectAll("selectAll");
        sysUserDataScopeDO.setNotIn("notIn");
        sysUserDataScopeDO.setUserDataScopeId(0L);
        sysUserDataScopeDO.setUserId(0L);
        sysUserDataScopeDO.setUserItcode("userItcode");
        sysUserDataScopeDO.setDataScopeKey("dataScopeKey");
        sysUserDataScopeDO.setDataScopeValue("dataScopeValue");
        sysUserDataScopeDO.setStatus("status");
        when(mockSysUserDataScopeMapper.selectSysUserDataScopeByUserDataScopeId(0L)).thenReturn(sysUserDataScopeDO);

        // Run the test
        final SysUserDataScopeDO result = sysUserDataScopeManagerImplUnderTest.selectSysUserDataScopeByUserDataScopeId(0L);

        // Verify the results
    }

    @Test
    public void testSelectSysUserDataScopeList() {
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

        // Configure SysUserDataScopeMapper.selectSysUserDataScopeList(...).
        final SysUserDataScopeDO sysUserDataScopeDO = new SysUserDataScopeDO();
        sysUserDataScopeDO.setSelectAll("selectAll");
        sysUserDataScopeDO.setNotIn("notIn");
        sysUserDataScopeDO.setUserDataScopeId(0L);
        sysUserDataScopeDO.setUserId(0L);
        sysUserDataScopeDO.setUserItcode("userItcode");
        sysUserDataScopeDO.setDataScopeKey("dataScopeKey");
        sysUserDataScopeDO.setDataScopeValue("dataScopeValue");
        sysUserDataScopeDO.setStatus("status");
        final List<SysUserDataScopeDO> scopeDOS = Arrays.asList(sysUserDataScopeDO);
        when(mockSysUserDataScopeMapper.selectSysUserDataScopeList(any(SysUserDataScope.class))).thenReturn(scopeDOS);

        // Run the test
        final List<SysUserDataScopeDO> result = sysUserDataScopeManagerImplUnderTest.selectSysUserDataScopeList(sysUserDataScope);

        // Verify the results
    }

    @Test
    public void testSelectSysUserDataScopeList_SysUserDataScopeMapperReturnsNoItems() {
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

        when(mockSysUserDataScopeMapper.selectSysUserDataScopeList(any(SysUserDataScope.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUserDataScopeDO> result = sysUserDataScopeManagerImplUnderTest.selectSysUserDataScopeList(sysUserDataScope);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertSysUserDataScope() {
        // Setup
        final SysUserDataScopeDO sysUserDataScope = new SysUserDataScopeDO();
        sysUserDataScope.setSelectAll("selectAll");
        sysUserDataScope.setNotIn("notIn");
        sysUserDataScope.setUserDataScopeId(0L);
        sysUserDataScope.setUserId(0L);
        sysUserDataScope.setUserItcode("userItcode");
        sysUserDataScope.setDataScopeKey("dataScopeKey");
        sysUserDataScope.setDataScopeValue("dataScopeValue");
        sysUserDataScope.setStatus("status");

        when(mockSysUserDataScopeMapper.insertSysUserDataScope(any(SysUserDataScopeDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserDataScopeManagerImplUnderTest.insertSysUserDataScope(sysUserDataScope);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateSysUserDataScope() {
        // Setup
        final SysUserDataScopeDO sysUserDataScope = new SysUserDataScopeDO();
        sysUserDataScope.setSelectAll("selectAll");
        sysUserDataScope.setNotIn("notIn");
        sysUserDataScope.setUserDataScopeId(0L);
        sysUserDataScope.setUserId(0L);
        sysUserDataScope.setUserItcode("userItcode");
        sysUserDataScope.setDataScopeKey("dataScopeKey");
        sysUserDataScope.setDataScopeValue("dataScopeValue");
        sysUserDataScope.setStatus("status");

        when(mockSysUserDataScopeMapper.updateSysUserDataScope(any(SysUserDataScopeDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserDataScopeManagerImplUnderTest.updateSysUserDataScope(sysUserDataScope);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysUserDataScopeByUserDataScopeId() {
        // Setup
        when(mockSysUserDataScopeMapper.deleteSysUserDataScopeByUserDataScopeId(0L)).thenReturn(0);

        // Run the test
        final int result = sysUserDataScopeManagerImplUnderTest.deleteSysUserDataScopeByUserDataScopeId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysUserDataScopeByUserDataScopeIds() {
        // Setup
        when(mockSysUserDataScopeMapper.deleteSysUserDataScopeByUserDataScopeIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysUserDataScopeManagerImplUnderTest.deleteSysUserDataScopeByUserDataScopeIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testGetDataScopeByUserId() {
        // Setup
        // Configure SysUserDataScopeMapper.selectDataScopeByUserId(...).
        final SysUserDataScopeDO sysUserDataScopeDO = new SysUserDataScopeDO();
        sysUserDataScopeDO.setSelectAll("selectAll");
        sysUserDataScopeDO.setNotIn("notIn");
        sysUserDataScopeDO.setUserDataScopeId(0L);
        sysUserDataScopeDO.setUserId(0L);
        sysUserDataScopeDO.setUserItcode("userItcode");
        sysUserDataScopeDO.setDataScopeKey("dataScopeKey");
        sysUserDataScopeDO.setDataScopeValue("dataScopeValue");
        sysUserDataScopeDO.setStatus("status");
        final List<SysUserDataScopeDO> scopeDOS = Arrays.asList(sysUserDataScopeDO);
        when(mockSysUserDataScopeMapper.selectDataScopeByUserId(0)).thenReturn(scopeDOS);

        // Run the test
        final List<SysUserDataScopeDO> result = sysUserDataScopeManagerImplUnderTest.getDataScopeByUserId(0);

        // Verify the results
    }

    @Test
    public void testGetDataScopeByUserId_SysUserDataScopeMapperReturnsNoItems() {
        // Setup
        when(mockSysUserDataScopeMapper.selectDataScopeByUserId(0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUserDataScopeDO> result = sysUserDataScopeManagerImplUnderTest.getDataScopeByUserId(0);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDeleteSysUserDataScopeByUserId() {
        // Setup
        when(mockSysUserDataScopeMapper.deleteSysUserDataScopeByUserId(0L)).thenReturn(0);

        // Run the test
        final int result = sysUserDataScopeManagerImplUnderTest.deleteSysUserDataScopeByUserId(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
