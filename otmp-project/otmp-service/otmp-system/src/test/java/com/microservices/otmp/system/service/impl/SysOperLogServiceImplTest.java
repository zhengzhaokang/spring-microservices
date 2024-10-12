package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.SysOperLog;
import com.microservices.otmp.system.domain.entity.SysOperLogDO;
import com.microservices.otmp.system.manager.SysOperLogManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysOperLogServiceImplTest {

    @Mock
    private SysOperLogManager mockOperLogManager;

    @InjectMocks
    private SysOperLogServiceImpl sysOperLogServiceImplUnderTest;

    @Test
    public void testInsertOperlog() {
        // Setup
        final SysOperLog operLog = new SysOperLog();
        operLog.setOperId(0L);
        operLog.setOperIds(Arrays.asList(0L));
        operLog.setTitle("title");
        operLog.setBusinessType(0);
        operLog.setMethod("method");
        operLog.setRequestMethod("requestMethod");
        operLog.setOperatorType(0);
        operLog.setOperName("operName");
        operLog.setDeptName("deptName");
        operLog.setOperUrl("operUrl");
        operLog.setOperIp("operIp");
        operLog.setOperLocation("operLocation");
        operLog.setOperParam("operParam");
        operLog.setStatus(0);
        operLog.setErrorMsg("errorMsg");

        // Run the test
        sysOperLogServiceImplUnderTest.insertOperlog(operLog);
        assertEquals(0, operLog.getStatus().intValue());

    }

    @Test
    public void testSelectOperLogList() {
        // Setup
        final SysOperLog operLog = new SysOperLog();
        operLog.setOperId(0L);
        operLog.setOperIds(Arrays.asList(0L));
        operLog.setTitle("title");
        operLog.setBusinessType(0);
        operLog.setMethod("method");
        operLog.setRequestMethod("requestMethod");
        operLog.setOperatorType(0);
        operLog.setOperName("operName");
        operLog.setDeptName("deptName");
        operLog.setOperUrl("operUrl");
        operLog.setOperIp("operIp");
        operLog.setOperLocation("operLocation");
        operLog.setOperParam("operParam");
        operLog.setStatus(0);
        operLog.setErrorMsg("errorMsg");

        final SysOperLog sysOperLog = new SysOperLog();
        sysOperLog.setOperId(0L);
        sysOperLog.setOperIds(Arrays.asList(0L));
        sysOperLog.setTitle("title");
        sysOperLog.setBusinessType(0);
        sysOperLog.setMethod("method");
        sysOperLog.setRequestMethod("requestMethod");
        sysOperLog.setOperatorType(0);
        sysOperLog.setOperName("operName");
        sysOperLog.setDeptName("deptName");
        sysOperLog.setOperUrl("operUrl");
        sysOperLog.setOperIp("operIp");
        sysOperLog.setOperLocation("operLocation");
        sysOperLog.setOperParam("operParam");
        sysOperLog.setStatus(0);
        sysOperLog.setErrorMsg("errorMsg");
        final List<SysOperLog> expectedResult = Arrays.asList(sysOperLog);

        // Configure SysOperLogManager.selectOperLogList(...).
        final SysOperLogDO sysOperLogDO = new SysOperLogDO();
        sysOperLogDO.setOperId(0L);
        sysOperLogDO.setOperIds(Arrays.asList(0L));
        sysOperLogDO.setTitle("title");
        sysOperLogDO.setBusinessType(0);
        sysOperLogDO.setMethod("method");
        sysOperLogDO.setRequestMethod("requestMethod");
        sysOperLogDO.setOperatorType(0);
        sysOperLogDO.setOperName("operName");
        sysOperLogDO.setDeptName("deptName");
        sysOperLogDO.setOperUrl("operUrl");
        sysOperLogDO.setOperIp("operIp");
        sysOperLogDO.setOperLocation("operLocation");
        sysOperLogDO.setOperParam("operParam");
        sysOperLogDO.setStatus(0);
        sysOperLogDO.setErrorMsg("errorMsg");
        final List<SysOperLogDO> sysOperLogDOS = Arrays.asList(sysOperLogDO);
        when(mockOperLogManager.selectOperLogList(new SysOperLog())).thenReturn(sysOperLogDOS);

        // Run the test
        final List<SysOperLog> result = sysOperLogServiceImplUnderTest.selectOperLogList(operLog);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDeleteOperLogByIds() {
        // Setup
        when(mockOperLogManager.deleteOperLogByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = sysOperLogServiceImplUnderTest.deleteOperLogByIds("ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectOperLogById() {
        // Setup
        final SysOperLog expectedResult = new SysOperLog();
        expectedResult.setOperId(0L);
        expectedResult.setOperIds(Arrays.asList(0L));
        expectedResult.setTitle("title");
        expectedResult.setBusinessType(0);
        expectedResult.setMethod("method");
        expectedResult.setRequestMethod("requestMethod");
        expectedResult.setOperatorType(0);
        expectedResult.setOperName("operName");
        expectedResult.setDeptName("deptName");
        expectedResult.setOperUrl("operUrl");
        expectedResult.setOperIp("operIp");
        expectedResult.setOperLocation("operLocation");
        expectedResult.setOperParam("operParam");
        expectedResult.setStatus(0);
        expectedResult.setErrorMsg("errorMsg");

        // Configure SysOperLogManager.selectOperLogById(...).
        final SysOperLogDO sysOperLogDO = new SysOperLogDO();
        sysOperLogDO.setOperId(0L);
        sysOperLogDO.setOperIds(Arrays.asList(0L));
        sysOperLogDO.setTitle("title");
        sysOperLogDO.setBusinessType(0);
        sysOperLogDO.setMethod("method");
        sysOperLogDO.setRequestMethod("requestMethod");
        sysOperLogDO.setOperatorType(0);
        sysOperLogDO.setOperName("operName");
        sysOperLogDO.setDeptName("deptName");
        sysOperLogDO.setOperUrl("operUrl");
        sysOperLogDO.setOperIp("operIp");
        sysOperLogDO.setOperLocation("operLocation");
        sysOperLogDO.setOperParam("operParam");
        sysOperLogDO.setStatus(0);
        sysOperLogDO.setErrorMsg("errorMsg");
        when(mockOperLogManager.selectOperLogById(0L)).thenReturn(sysOperLogDO);

        // Run the test
        final SysOperLog result = sysOperLogServiceImplUnderTest.selectOperLogById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCleanOperLog() {
        // Setup
        // Run the test
        sysOperLogServiceImplUnderTest.cleanOperLog();

        // Verify the results
        verify(mockOperLogManager).cleanOperLog();
    }

    @Test
    public void testSelectOperLogPage() {
        // Setup
        final SysOperLog operLog = new SysOperLog();
        operLog.setOperId(0L);
        operLog.setOperIds(Arrays.asList(0L));
        operLog.setTitle("title");
        operLog.setBusinessType(0);
        operLog.setMethod("method");
        operLog.setRequestMethod("requestMethod");
        operLog.setOperatorType(0);
        operLog.setOperName("operName");
        operLog.setDeptName("deptName");
        operLog.setOperUrl("operUrl");
        operLog.setOperIp("operIp");
        operLog.setOperLocation("operLocation");
        operLog.setOperParam("operParam");
        operLog.setStatus(0);
        operLog.setErrorMsg("errorMsg");

        // Configure SysOperLogManager.selectOperLogList(...).
        final SysOperLogDO sysOperLogDO = new SysOperLogDO();
        sysOperLogDO.setOperId(0L);
        sysOperLogDO.setOperIds(Arrays.asList(0L));
        sysOperLogDO.setTitle("title");
        sysOperLogDO.setBusinessType(0);
        sysOperLogDO.setMethod("method");
        sysOperLogDO.setRequestMethod("requestMethod");
        sysOperLogDO.setOperatorType(0);
        sysOperLogDO.setOperName("operName");
        sysOperLogDO.setDeptName("deptName");
        sysOperLogDO.setOperUrl("operUrl");
        sysOperLogDO.setOperIp("operIp");
        sysOperLogDO.setOperLocation("operLocation");
        sysOperLogDO.setOperParam("operParam");
        sysOperLogDO.setStatus(0);
        sysOperLogDO.setErrorMsg("errorMsg");
        final List<SysOperLogDO> sysOperLogDOS = Arrays.asList(sysOperLogDO);
        when(mockOperLogManager.selectOperLogList(any(SysOperLog.class))).thenReturn(sysOperLogDOS);

        // Run the test
        final PageInfo<SysOperLog> result = sysOperLogServiceImplUnderTest.selectOperLogPage(operLog);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getTotal());
    }

    @Test
    public void testSelectOperLogPage_SysOperLogManagerReturnsNoItems() {
        // Setup
        final SysOperLog operLog = new SysOperLog();
        operLog.setOperId(0L);
        operLog.setOperIds(Arrays.asList(0L));
        operLog.setTitle("title");
        operLog.setBusinessType(0);
        operLog.setMethod("method");
        operLog.setRequestMethod("requestMethod");
        operLog.setOperatorType(0);
        operLog.setOperName("operName");
        operLog.setDeptName("deptName");
        operLog.setOperUrl("operUrl");
        operLog.setOperIp("operIp");
        operLog.setOperLocation("operLocation");
        operLog.setOperParam("operParam");
        operLog.setStatus(0);
        operLog.setErrorMsg("errorMsg");

        when(mockOperLogManager.selectOperLogList(new SysOperLog())).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<SysOperLog> result = sysOperLogServiceImplUnderTest.selectOperLogPage(operLog);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals(Collections.emptyList(), result.getList());
        verify(mockOperLogManager, times(1)).selectOperLogList(any());
    }
}
