package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.dto.SysBusinessOperatorLogDTO;
import com.microservices.otmp.system.domain.entity.SysBusinessOperatorLogDO;
import com.microservices.otmp.system.manager.ISysBusinessOperatorLogManager;
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
public class SysBusinessOperatorLogServiceImplTest {

    @Mock
    private ISysBusinessOperatorLogManager mockSysBusinessOperatorLogManager;

    @InjectMocks
    private SysBusinessOperatorLogServiceImpl sysBusinessOperatorLogServiceImplUnderTest;

    @Test
    public void testSelectSysBusinessOperatorLogById() {
        // Setup
        final SysBusinessOperatorLogDTO expectedResult = new SysBusinessOperatorLogDTO();
        expectedResult.setId(0L);
        expectedResult.setOperation("operation");
        expectedResult.setOperator("operator");
        expectedResult.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setTarget("target");
        expectedResult.setDetail("detail");
        expectedResult.setModuleType("moduleType");
        expectedResult.setOperatorDateStart(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setOperatorDateEnd(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure ISysBusinessOperatorLogManager.selectSysBusinessOperatorLogById(...).
        final SysBusinessOperatorLogDO sysBusinessOperatorLogDO = new SysBusinessOperatorLogDO();
        sysBusinessOperatorLogDO.setId(0L);
        sysBusinessOperatorLogDO.setOperation("operation");
        sysBusinessOperatorLogDO.setOperator("operator");
        sysBusinessOperatorLogDO.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLogDO.setTarget("target");
        sysBusinessOperatorLogDO.setDetail("detail");
        sysBusinessOperatorLogDO.setModuleType("moduleType");
        when(mockSysBusinessOperatorLogManager.selectSysBusinessOperatorLogById(0L)).thenReturn(sysBusinessOperatorLogDO);

        // Run the test
        final SysBusinessOperatorLogDTO result = sysBusinessOperatorLogServiceImplUnderTest.selectSysBusinessOperatorLogById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectSysBusinessOperatorLogList() {
        // Setup
        final SysBusinessOperatorLogDTO sysBusinessOperatorLog = new SysBusinessOperatorLogDTO();
        sysBusinessOperatorLog.setId(0L);
        sysBusinessOperatorLog.setOperation("operation");
        sysBusinessOperatorLog.setOperator("operator");
        sysBusinessOperatorLog.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLog.setTarget("target");
        sysBusinessOperatorLog.setDetail("detail");
        sysBusinessOperatorLog.setModuleType("moduleType");
        sysBusinessOperatorLog.setOperatorDateStart(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLog.setOperatorDateEnd(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure ISysBusinessOperatorLogManager.selectSysBusinessOperatorLogList(...).
        final SysBusinessOperatorLogDO sysBusinessOperatorLogDO = new SysBusinessOperatorLogDO();
        sysBusinessOperatorLogDO.setId(0L);
        sysBusinessOperatorLogDO.setOperation("operation");
        sysBusinessOperatorLogDO.setOperator("operator");
        sysBusinessOperatorLogDO.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLogDO.setTarget("target");
        sysBusinessOperatorLogDO.setDetail("detail");
        sysBusinessOperatorLogDO.setModuleType("moduleType");
        final List<SysBusinessOperatorLogDO> sysBusinessOperatorLogDOS = Arrays.asList(sysBusinessOperatorLogDO);
        when(mockSysBusinessOperatorLogManager.selectSysBusinessOperatorLogList(new SysBusinessOperatorLogDTO())).thenReturn(sysBusinessOperatorLogDOS);

        // Run the test
        final PageInfo<SysBusinessOperatorLogDTO> result = sysBusinessOperatorLogServiceImplUnderTest.selectSysBusinessOperatorLogList(sysBusinessOperatorLog);

        // Verify the results
    }

    @Test
    public void testSelectSysBusinessOperatorLogList_ISysBusinessOperatorLogManagerReturnsNoItems() {
        // Setup
        final SysBusinessOperatorLogDTO sysBusinessOperatorLog = new SysBusinessOperatorLogDTO();
        sysBusinessOperatorLog.setId(0L);
        sysBusinessOperatorLog.setOperation("operation");
        sysBusinessOperatorLog.setOperator("operator");
        sysBusinessOperatorLog.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLog.setTarget("target");
        sysBusinessOperatorLog.setDetail("detail");
        sysBusinessOperatorLog.setModuleType("moduleType");
        sysBusinessOperatorLog.setOperatorDateStart(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLog.setOperatorDateEnd(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockSysBusinessOperatorLogManager.selectSysBusinessOperatorLogList(new SysBusinessOperatorLogDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<SysBusinessOperatorLogDTO> result = sysBusinessOperatorLogServiceImplUnderTest.selectSysBusinessOperatorLogList(sysBusinessOperatorLog);

        // Verify the results
    }

    @Test
    public void testInsertSysBusinessOperatorLog() {
        // Setup
        final SysBusinessOperatorLogDTO sysBusinessOperatorLog = new SysBusinessOperatorLogDTO();
        sysBusinessOperatorLog.setId(0L);
        sysBusinessOperatorLog.setOperation("operation");
        sysBusinessOperatorLog.setOperator("operator");
        sysBusinessOperatorLog.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLog.setTarget("target");
        sysBusinessOperatorLog.setDetail("detail");
        sysBusinessOperatorLog.setModuleType("moduleType");
        sysBusinessOperatorLog.setOperatorDateStart(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLog.setOperatorDateEnd(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockSysBusinessOperatorLogManager.insertSysBusinessOperatorLog(new SysBusinessOperatorLogDO())).thenReturn(0);

        // Run the test
        final int result = sysBusinessOperatorLogServiceImplUnderTest.insertSysBusinessOperatorLog(sysBusinessOperatorLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateSysBusinessOperatorLog() {
        // Setup
        final SysBusinessOperatorLogDTO sysBusinessOperatorLog = new SysBusinessOperatorLogDTO();
        sysBusinessOperatorLog.setId(0L);
        sysBusinessOperatorLog.setOperation("operation");
        sysBusinessOperatorLog.setOperator("operator");
        sysBusinessOperatorLog.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLog.setTarget("target");
        sysBusinessOperatorLog.setDetail("detail");
        sysBusinessOperatorLog.setModuleType("moduleType");
        sysBusinessOperatorLog.setOperatorDateStart(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLog.setOperatorDateEnd(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockSysBusinessOperatorLogManager.updateSysBusinessOperatorLog(new SysBusinessOperatorLogDO())).thenReturn(0);

        // Run the test
        final int result = sysBusinessOperatorLogServiceImplUnderTest.updateSysBusinessOperatorLog(sysBusinessOperatorLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessOperatorLogByIds() {
        // Setup
        when(mockSysBusinessOperatorLogManager.deleteSysBusinessOperatorLogByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysBusinessOperatorLogServiceImplUnderTest.deleteSysBusinessOperatorLogByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessOperatorLogById() {
        // Setup
        when(mockSysBusinessOperatorLogManager.deleteSysBusinessOperatorLogById(0L)).thenReturn(0);

        // Run the test
        final int result = sysBusinessOperatorLogServiceImplUnderTest.deleteSysBusinessOperatorLogById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessOperatorLogByOperationDate() {
        // Setup
        when(mockSysBusinessOperatorLogManager.deleteSysBusinessOperatorLogByOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);

        // Run the test
        final int result = sysBusinessOperatorLogServiceImplUnderTest.deleteSysBusinessOperatorLogByOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
        assertEquals(0, result);
    }
}
