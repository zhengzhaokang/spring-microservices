package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.entity.SysUserDataScopeDO;
import com.microservices.otmp.system.manager.SysUserDataScopeManager;
import org.junit.Assert;
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
public class SysUserDataScopeServiceImplTest {

    @Mock
    private SysUserDataScopeManager mockSysUserDataScopeManager;

    @InjectMocks
    private SysUserDataScopeServiceImpl sysUserDataScopeServiceImplUnderTest;

    @Test
    public void testSelectSysUserDataScopeByUserDataScopeId() {
        // Setup
        // Configure SysUserDataScopeManager.selectSysUserDataScopeByUserDataScopeId(...).
        final SysUserDataScopeDO sysUserDataScopeDO = new SysUserDataScopeDO();
        sysUserDataScopeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserDataScopeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserDataScopeDO.setUserDataScopeId(0L);
        sysUserDataScopeDO.setUserId(0L);
        sysUserDataScopeDO.setUserItcode("userItcode");
        sysUserDataScopeDO.setDataScopeKey("dataScopeKey");
        sysUserDataScopeDO.setDataScopeValue("dataScopeValue");
        sysUserDataScopeDO.setStatus("status");
        when(mockSysUserDataScopeManager.selectSysUserDataScopeByUserDataScopeId(0L)).thenReturn(sysUserDataScopeDO);

        // Run the test
        final SysUserDataScope result = sysUserDataScopeServiceImplUnderTest.selectSysUserDataScopeByUserDataScopeId(
                0L);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals(sysUserDataScopeDO.getUserId(),result.getUserId());
    }

    @Test
    public void testSelectSysUserDataScopeList() {
        // Setup
        final SysUserDataScope sysUserDataScope = new SysUserDataScope();
        sysUserDataScope.setUserDataScopeId(0L);
        sysUserDataScope.setUserId(0L);
        sysUserDataScope.setUserItcode("userItcode");
        sysUserDataScope.setDataScopeKey("dataScopeKey");
        sysUserDataScope.setDataScopeValue("dataScopeValue");
        sysUserDataScope.setStatus("status");

        // Configure SysUserDataScopeManager.selectSysUserDataScopeList(...).
        final SysUserDataScopeDO sysUserDataScopeDO = new SysUserDataScopeDO();
        sysUserDataScopeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserDataScopeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserDataScopeDO.setUserDataScopeId(0L);
        sysUserDataScopeDO.setUserId(0L);
        sysUserDataScopeDO.setUserItcode("userItcode");
        sysUserDataScopeDO.setDataScopeKey("dataScopeKey");
        sysUserDataScopeDO.setDataScopeValue("dataScopeValue");
        sysUserDataScopeDO.setStatus("status");
        final List<SysUserDataScopeDO> sysUserDataScopeDOS = Arrays.asList(sysUserDataScopeDO);
        when(mockSysUserDataScopeManager.selectSysUserDataScopeList(any(SysUserDataScope.class)))
                .thenReturn(sysUserDataScopeDOS);

        // Run the test
        final List<SysUserDataScope> result = sysUserDataScopeServiceImplUnderTest.selectSysUserDataScopeList(
                sysUserDataScope);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testSelectSysUserDataScopeList_SysUserDataScopeManagerReturnsNoItems() {
        // Setup
        final SysUserDataScope sysUserDataScope = new SysUserDataScope();
        sysUserDataScope.setUserDataScopeId(0L);
        sysUserDataScope.setUserId(0L);
        sysUserDataScope.setUserItcode("userItcode");
        sysUserDataScope.setDataScopeKey("dataScopeKey");
        sysUserDataScope.setDataScopeValue("dataScopeValue");
        sysUserDataScope.setStatus("status");

        when(mockSysUserDataScopeManager.selectSysUserDataScopeList(any(SysUserDataScope.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUserDataScope> result = sysUserDataScopeServiceImplUnderTest.selectSysUserDataScopeList(
                sysUserDataScope);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertSysUserDataScope() {
        // Setup
        final SysUserDataScope sysUserDataScope = new SysUserDataScope();
        sysUserDataScope.setUserDataScopeId(0L);
        sysUserDataScope.setUserId(0L);
        sysUserDataScope.setUserItcode("userItcode");
        sysUserDataScope.setDataScopeKey("dataScopeKey");
        sysUserDataScope.setDataScopeValue("dataScopeValue");
        sysUserDataScope.setStatus("status");

        when(mockSysUserDataScopeManager.insertSysUserDataScope(any(SysUserDataScopeDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserDataScopeServiceImplUnderTest.insertSysUserDataScope(sysUserDataScope);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateSysUserDataScope() {
        // Setup
        final SysUserDataScope sysUserDataScope = new SysUserDataScope();
        sysUserDataScope.setUserDataScopeId(0L);
        sysUserDataScope.setUserId(0L);
        sysUserDataScope.setUserItcode("userItcode");
        sysUserDataScope.setDataScopeKey("dataScopeKey");
        sysUserDataScope.setDataScopeValue("dataScopeValue");
        sysUserDataScope.setStatus("status");

        when(mockSysUserDataScopeManager.updateSysUserDataScope(any(SysUserDataScopeDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserDataScopeServiceImplUnderTest.updateSysUserDataScope(sysUserDataScope);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysUserDataScopeByUserDataScopeIds() {
        // Setup
        when(mockSysUserDataScopeManager.deleteSysUserDataScopeByUserDataScopeIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysUserDataScopeServiceImplUnderTest.deleteSysUserDataScopeByUserDataScopeIds(
                new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysUserDataScopeByUserDataScopeId() {
        // Setup
        when(mockSysUserDataScopeManager.deleteSysUserDataScopeByUserDataScopeId(0L)).thenReturn(0);

        // Run the test
        final int result = sysUserDataScopeServiceImplUnderTest.deleteSysUserDataScopeByUserDataScopeId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectSysUserDataScopePage() {
        // Setup
        final SysUserDataScope sysUserDataScope = new SysUserDataScope();
        sysUserDataScope.setUserDataScopeId(0L);
        sysUserDataScope.setUserId(0L);
        sysUserDataScope.setUserItcode("userItcode");
        sysUserDataScope.setDataScopeKey("dataScopeKey");
        sysUserDataScope.setDataScopeValue("dataScopeValue");
        sysUserDataScope.setStatus("status");

        // Configure SysUserDataScopeManager.selectSysUserDataScopeList(...).
        final SysUserDataScopeDO sysUserDataScopeDO = new SysUserDataScopeDO();
        sysUserDataScopeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserDataScopeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserDataScopeDO.setUserDataScopeId(0L);
        sysUserDataScopeDO.setUserId(0L);
        sysUserDataScopeDO.setUserItcode("userItcode");
        sysUserDataScopeDO.setDataScopeKey("dataScopeKey");
        sysUserDataScopeDO.setDataScopeValue("dataScopeValue");
        sysUserDataScopeDO.setStatus("status");
        final List<SysUserDataScopeDO> sysUserDataScopeDOS = Arrays.asList(sysUserDataScopeDO);
        when(mockSysUserDataScopeManager.selectSysUserDataScopeList(any(SysUserDataScope.class)))
                .thenReturn(sysUserDataScopeDOS);

        // Run the test
        final PageInfo<SysUserDataScope> result = sysUserDataScopeServiceImplUnderTest.selectSysUserDataScopePage(
                sysUserDataScope);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( 1,result.getTotal());
    }

    @Test
    public void testSelectSysUserDataScopePage_SysUserDataScopeManagerReturnsNoItems() {
        // Setup
        final SysUserDataScope sysUserDataScope = new SysUserDataScope();
        sysUserDataScope.setUserDataScopeId(0L);
        sysUserDataScope.setUserId(0L);
        sysUserDataScope.setUserItcode("userItcode");
        sysUserDataScope.setDataScopeKey("dataScopeKey");
        sysUserDataScope.setDataScopeValue("dataScopeValue");
        sysUserDataScope.setStatus("status");

        when(mockSysUserDataScopeManager.selectSysUserDataScopeList(any(SysUserDataScope.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<SysUserDataScope> result = sysUserDataScopeServiceImplUnderTest.selectSysUserDataScopePage(
                sysUserDataScope);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( Collections.emptyList(),result.getList());
    }
}
