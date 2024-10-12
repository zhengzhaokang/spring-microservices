package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysOperLog;
import com.microservices.otmp.system.domain.entity.SysOperLogDO;
import com.microservices.otmp.system.mapper.SysOperLogMapper;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysOperLogManagerImplTest {

    @Mock
    private SysOperLogMapper mockSysOperLogMapper;

    @InjectMocks
    private SysOperLogManagerImpl sysOperLogManagerImplUnderTest;

    @Test
    public void testInsertOperlog() {
        // Setup
        final SysOperLogDO operLog = new SysOperLogDO();
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

        // Run the test
        sysOperLogManagerImplUnderTest.insertOperlog(operLog);

        // Verify the results
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
        final List<SysOperLogDO> expectedResult = Arrays.asList(sysOperLogDO);

        // Configure SysOperLogMapper.selectOperLogList(...).
        final SysOperLogDO sysOperLogDO1 = new SysOperLogDO();
        sysOperLogDO1.setOperId(0L);
        sysOperLogDO1.setOperIds(Arrays.asList(0L));
        sysOperLogDO1.setTitle("title");
        sysOperLogDO1.setBusinessType(0);
        sysOperLogDO1.setMethod("method");
        sysOperLogDO1.setRequestMethod("requestMethod");
        sysOperLogDO1.setOperatorType(0);
        sysOperLogDO1.setOperName("operName");
        sysOperLogDO1.setDeptName("deptName");
        sysOperLogDO1.setOperUrl("operUrl");
        final List<SysOperLogDO> sysOperLogDOS = Arrays.asList(sysOperLogDO1);
        when(mockSysOperLogMapper.selectOperLogList(new SysOperLog())).thenReturn(sysOperLogDOS);

        // Run the test
        final List<SysOperLogDO> result = sysOperLogManagerImplUnderTest.selectOperLogList(operLog);

        // Verify the results
    }

    @Test
    public void testSelectOperLogList_SysOperLogMapperReturnsNoItems() {
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

        when(mockSysOperLogMapper.selectOperLogList(new SysOperLog())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysOperLogDO> result = sysOperLogManagerImplUnderTest.selectOperLogList(operLog);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDeleteOperLogByIds() {
        // Setup
        when(mockSysOperLogMapper.deleteOperLogByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = sysOperLogManagerImplUnderTest.deleteOperLogByIds(new String[]{"value"});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectOperLogById() {
        // Setup
        final SysOperLogDO expectedResult = new SysOperLogDO();
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

        // Configure SysOperLogMapper.selectOperLogById(...).
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
        when(mockSysOperLogMapper.selectOperLogById(0L)).thenReturn(sysOperLogDO);

        // Run the test
        final SysOperLogDO result = sysOperLogManagerImplUnderTest.selectOperLogById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCleanOperLog() {
        // Setup
        // Run the test
        sysOperLogManagerImplUnderTest.cleanOperLog();

        // Verify the results
        verify(mockSysOperLogMapper).cleanOperLog();
    }
}
