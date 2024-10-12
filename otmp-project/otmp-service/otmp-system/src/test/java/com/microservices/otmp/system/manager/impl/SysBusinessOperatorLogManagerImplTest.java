package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.dto.SysBusinessOperatorLogDTO;
import com.microservices.otmp.system.domain.entity.SysBusinessOperatorLogDO;
import com.microservices.otmp.system.mapper.SysBusinessOperatorLogMapper;
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
public class SysBusinessOperatorLogManagerImplTest {

    @Mock
    private SysBusinessOperatorLogMapper mockSysBusinessOperatorLogMapper;

    @InjectMocks
    private SysBusinessOperatorLogManagerImpl sysBusinessOperatorLogManagerImplUnderTest;

    @Test
    public void testSelectSysBusinessOperatorLogById() {
        // Setup
        final SysBusinessOperatorLogDO expectedResult = new SysBusinessOperatorLogDO();
        expectedResult.setId(0L);
        expectedResult.setOperation("operation");
        expectedResult.setOperator("operator");
        expectedResult.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setTarget("target");
        expectedResult.setDetail("detail");
        expectedResult.setModuleType("moduleType");

        // Configure SysBusinessOperatorLogMapper.selectSysBusinessOperatorLogById(...).
        final SysBusinessOperatorLogDO sysBusinessOperatorLogDO = new SysBusinessOperatorLogDO();
        sysBusinessOperatorLogDO.setId(0L);
        sysBusinessOperatorLogDO.setOperation("operation");
        sysBusinessOperatorLogDO.setOperator("operator");
        sysBusinessOperatorLogDO.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLogDO.setTarget("target");
        sysBusinessOperatorLogDO.setDetail("detail");
        sysBusinessOperatorLogDO.setModuleType("moduleType");
        when(mockSysBusinessOperatorLogMapper.selectSysBusinessOperatorLogById(0L)).thenReturn(sysBusinessOperatorLogDO);

        // Run the test
        final SysBusinessOperatorLogDO result = sysBusinessOperatorLogManagerImplUnderTest.selectSysBusinessOperatorLogById(0L);

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

        final SysBusinessOperatorLogDO sysBusinessOperatorLogDO = new SysBusinessOperatorLogDO();
        sysBusinessOperatorLogDO.setId(0L);
        sysBusinessOperatorLogDO.setOperation("operation");
        sysBusinessOperatorLogDO.setOperator("operator");
        sysBusinessOperatorLogDO.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLogDO.setTarget("target");
        sysBusinessOperatorLogDO.setDetail("detail");
        sysBusinessOperatorLogDO.setModuleType("moduleType");
        final List<SysBusinessOperatorLogDO> expectedResult = Arrays.asList(sysBusinessOperatorLogDO);

        // Configure SysBusinessOperatorLogMapper.selectSysBusinessOperatorLogList(...).
        final SysBusinessOperatorLogDO sysBusinessOperatorLogDO1 = new SysBusinessOperatorLogDO();
        sysBusinessOperatorLogDO1.setId(0L);
        sysBusinessOperatorLogDO1.setOperation("operation");
        sysBusinessOperatorLogDO1.setOperator("operator");
        sysBusinessOperatorLogDO1.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLogDO1.setTarget("target");
        sysBusinessOperatorLogDO1.setDetail("detail");
        sysBusinessOperatorLogDO1.setModuleType("moduleType");
        final List<SysBusinessOperatorLogDO> sysBusinessOperatorLogDOS = Arrays.asList(sysBusinessOperatorLogDO1);
        when(mockSysBusinessOperatorLogMapper.selectSysBusinessOperatorLogList(new SysBusinessOperatorLogDTO())).thenReturn(sysBusinessOperatorLogDOS);

        // Run the test
        final List<SysBusinessOperatorLogDO> result = sysBusinessOperatorLogManagerImplUnderTest.selectSysBusinessOperatorLogList(sysBusinessOperatorLog);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectSysBusinessOperatorLogList_SysBusinessOperatorLogMapperReturnsNoItems() {
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

        when(mockSysBusinessOperatorLogMapper.selectSysBusinessOperatorLogList(new SysBusinessOperatorLogDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysBusinessOperatorLogDO> result = sysBusinessOperatorLogManagerImplUnderTest.selectSysBusinessOperatorLogList(sysBusinessOperatorLog);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertSysBusinessOperatorLog() {
        // Setup
        final SysBusinessOperatorLogDO sysBusinessOperatorLog = new SysBusinessOperatorLogDO();
        sysBusinessOperatorLog.setId(0L);
        sysBusinessOperatorLog.setOperation("operation");
        sysBusinessOperatorLog.setOperator("operator");
        sysBusinessOperatorLog.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLog.setTarget("target");
        sysBusinessOperatorLog.setDetail("detail");
        sysBusinessOperatorLog.setModuleType("moduleType");

        when(mockSysBusinessOperatorLogMapper.insertSysBusinessOperatorLog(new SysBusinessOperatorLogDO())).thenReturn(0);

        // Run the test
        final int result = sysBusinessOperatorLogManagerImplUnderTest.insertSysBusinessOperatorLog(sysBusinessOperatorLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateSysBusinessOperatorLog() {
        // Setup
        final SysBusinessOperatorLogDO sysBusinessOperatorLog = new SysBusinessOperatorLogDO();
        sysBusinessOperatorLog.setId(0L);
        sysBusinessOperatorLog.setOperation("operation");
        sysBusinessOperatorLog.setOperator("operator");
        sysBusinessOperatorLog.setOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessOperatorLog.setTarget("target");
        sysBusinessOperatorLog.setDetail("detail");
        sysBusinessOperatorLog.setModuleType("moduleType");

        when(mockSysBusinessOperatorLogMapper.updateSysBusinessOperatorLog(new SysBusinessOperatorLogDO())).thenReturn(0);

        // Run the test
        final int result = sysBusinessOperatorLogManagerImplUnderTest.updateSysBusinessOperatorLog(sysBusinessOperatorLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessOperatorLogByIds() {
        // Setup
        when(mockSysBusinessOperatorLogMapper.deleteSysBusinessOperatorLogByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysBusinessOperatorLogManagerImplUnderTest.deleteSysBusinessOperatorLogByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessOperatorLogById() {
        // Setup
        when(mockSysBusinessOperatorLogMapper.deleteSysBusinessOperatorLogById(0L)).thenReturn(0);

        // Run the test
        final int result = sysBusinessOperatorLogManagerImplUnderTest.deleteSysBusinessOperatorLogById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessOperatorLogByOperationDate() {
        // Setup
        when(mockSysBusinessOperatorLogMapper.deleteSysBusinessOperatorLogByOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);

        // Run the test
        final int result = sysBusinessOperatorLogManagerImplUnderTest.deleteSysBusinessOperatorLogByOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
        assertEquals(0, result);
    }
}
