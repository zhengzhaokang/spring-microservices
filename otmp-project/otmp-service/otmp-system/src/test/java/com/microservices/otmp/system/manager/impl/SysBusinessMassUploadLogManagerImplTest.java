package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.dto.SysBusinessMassUploadLogDTO;
import com.microservices.otmp.system.domain.entity.SysBusinessMassUploadLogDO;
import com.microservices.otmp.system.mapper.SysBusinessMassUploadLogMapper;
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
public class SysBusinessMassUploadLogManagerImplTest {

    @Mock
    private SysBusinessMassUploadLogMapper mockSysBusinessMassUploadLogMapper;

    @InjectMocks
    private SysBusinessMassUploadLogManagerImpl sysBusinessMassUploadLogManagerImplUnderTest;

    @Test
    public void testSelectSysBusinessMassUploadLogById() {
        // Setup
        final SysBusinessMassUploadLogDO expectedResult = new SysBusinessMassUploadLogDO();
        expectedResult.setId(0L);
        expectedResult.setFileName("fileName");
        expectedResult.setStatus("status");
        expectedResult.setRecordCount(0);
        expectedResult.setErrorCount(0);
        expectedResult.setOperator("operator");
        expectedResult.setOperatorDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUrl("url");
        expectedResult.setModuleType("moduleType");
        expectedResult.setGeoCode("geoCode");

        // Configure SysBusinessMassUploadLogMapper.selectSysBusinessMassUploadLogById(...).
        final SysBusinessMassUploadLogDO sysBusinessMassUploadLogDO = new SysBusinessMassUploadLogDO();
        sysBusinessMassUploadLogDO.setId(0L);
        sysBusinessMassUploadLogDO.setFileName("fileName");
        sysBusinessMassUploadLogDO.setStatus("status");
        sysBusinessMassUploadLogDO.setRecordCount(0);
        sysBusinessMassUploadLogDO.setErrorCount(0);
        sysBusinessMassUploadLogDO.setOperator("operator");
        sysBusinessMassUploadLogDO.setOperatorDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessMassUploadLogDO.setUrl("url");
        sysBusinessMassUploadLogDO.setModuleType("moduleType");
        sysBusinessMassUploadLogDO.setGeoCode("geoCode");
        when(mockSysBusinessMassUploadLogMapper.selectSysBusinessMassUploadLogById(0L)).thenReturn(sysBusinessMassUploadLogDO);

        // Run the test
        final SysBusinessMassUploadLogDO result = sysBusinessMassUploadLogManagerImplUnderTest.selectSysBusinessMassUploadLogById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectSysBusinessMassUploadLogList() {
        // Setup
        final SysBusinessMassUploadLogDTO sysBusinessMassUploadLog = new SysBusinessMassUploadLogDTO();
        sysBusinessMassUploadLog.setId(0L);
        sysBusinessMassUploadLog.setFileName("fileName");
        sysBusinessMassUploadLog.setStatus("status");
        sysBusinessMassUploadLog.setRecordCount(0);
        sysBusinessMassUploadLog.setErrorCount(0);
        sysBusinessMassUploadLog.setOperator("operator");
        sysBusinessMassUploadLog.setOperatorDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessMassUploadLog.setOperatorDateStart(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessMassUploadLog.setOperatorDateEnd(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessMassUploadLog.setStartDateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        final SysBusinessMassUploadLogDO sysBusinessMassUploadLogDO = new SysBusinessMassUploadLogDO();
        sysBusinessMassUploadLogDO.setId(0L);
        sysBusinessMassUploadLogDO.setFileName("fileName");
        sysBusinessMassUploadLogDO.setStatus("status");
        sysBusinessMassUploadLogDO.setRecordCount(0);
        sysBusinessMassUploadLogDO.setErrorCount(0);
        sysBusinessMassUploadLogDO.setOperator("operator");
        sysBusinessMassUploadLogDO.setOperatorDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessMassUploadLogDO.setUrl("url");
        sysBusinessMassUploadLogDO.setModuleType("moduleType");
        sysBusinessMassUploadLogDO.setGeoCode("geoCode");
        final List<SysBusinessMassUploadLogDO> expectedResult = Arrays.asList(sysBusinessMassUploadLogDO);

        // Configure SysBusinessMassUploadLogMapper.selectSysBusinessMassUploadLogList(...).
        final SysBusinessMassUploadLogDO sysBusinessMassUploadLogDO1 = new SysBusinessMassUploadLogDO();
        sysBusinessMassUploadLogDO1.setId(0L);
        sysBusinessMassUploadLogDO1.setFileName("fileName");
        sysBusinessMassUploadLogDO1.setStatus("status");
        sysBusinessMassUploadLogDO1.setRecordCount(0);
        sysBusinessMassUploadLogDO1.setErrorCount(0);
        sysBusinessMassUploadLogDO1.setOperator("operator");
        sysBusinessMassUploadLogDO1.setOperatorDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessMassUploadLogDO1.setUrl("url");
        sysBusinessMassUploadLogDO1.setModuleType("moduleType");
        sysBusinessMassUploadLogDO1.setGeoCode("geoCode");
        final List<SysBusinessMassUploadLogDO> sysBusinessMassUploadLogDOS = Arrays.asList(sysBusinessMassUploadLogDO1);
        when(mockSysBusinessMassUploadLogMapper.selectSysBusinessMassUploadLogList(new SysBusinessMassUploadLogDTO())).thenReturn(sysBusinessMassUploadLogDOS);

        // Run the test
        final List<SysBusinessMassUploadLogDO> result = sysBusinessMassUploadLogManagerImplUnderTest.selectSysBusinessMassUploadLogList(sysBusinessMassUploadLog);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectSysBusinessMassUploadLogList_SysBusinessMassUploadLogMapperReturnsNoItems() {
        // Setup
        final SysBusinessMassUploadLogDTO sysBusinessMassUploadLog = new SysBusinessMassUploadLogDTO();
        sysBusinessMassUploadLog.setId(0L);
        sysBusinessMassUploadLog.setFileName("fileName");
        sysBusinessMassUploadLog.setStatus("status");
        sysBusinessMassUploadLog.setRecordCount(0);
        sysBusinessMassUploadLog.setErrorCount(0);
        sysBusinessMassUploadLog.setOperator("operator");
        sysBusinessMassUploadLog.setOperatorDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessMassUploadLog.setOperatorDateStart(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessMassUploadLog.setOperatorDateEnd(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessMassUploadLog.setStartDateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockSysBusinessMassUploadLogMapper.selectSysBusinessMassUploadLogList(new SysBusinessMassUploadLogDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysBusinessMassUploadLogDO> result = sysBusinessMassUploadLogManagerImplUnderTest.selectSysBusinessMassUploadLogList(sysBusinessMassUploadLog);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertSysBusinessMassUploadLog() {
        // Setup
        final SysBusinessMassUploadLogDO sysBusinessMassUploadLog = new SysBusinessMassUploadLogDO();
        sysBusinessMassUploadLog.setId(0L);
        sysBusinessMassUploadLog.setFileName("fileName");
        sysBusinessMassUploadLog.setStatus("status");
        sysBusinessMassUploadLog.setRecordCount(0);
        sysBusinessMassUploadLog.setErrorCount(0);
        sysBusinessMassUploadLog.setOperator("operator");
        sysBusinessMassUploadLog.setOperatorDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessMassUploadLog.setUrl("url");
        sysBusinessMassUploadLog.setModuleType("moduleType");
        sysBusinessMassUploadLog.setGeoCode("geoCode");

        when(mockSysBusinessMassUploadLogMapper.insertSysBusinessMassUploadLog(new SysBusinessMassUploadLogDO())).thenReturn(0);

        // Run the test
        final int result = sysBusinessMassUploadLogManagerImplUnderTest.insertSysBusinessMassUploadLog(sysBusinessMassUploadLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateSysBusinessMassUploadLog() {
        // Setup
        final SysBusinessMassUploadLogDO sysBusinessMassUploadLog = new SysBusinessMassUploadLogDO();
        sysBusinessMassUploadLog.setId(0L);
        sysBusinessMassUploadLog.setFileName("fileName");
        sysBusinessMassUploadLog.setStatus("status");
        sysBusinessMassUploadLog.setRecordCount(0);
        sysBusinessMassUploadLog.setErrorCount(0);
        sysBusinessMassUploadLog.setOperator("operator");
        sysBusinessMassUploadLog.setOperatorDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysBusinessMassUploadLog.setUrl("url");
        sysBusinessMassUploadLog.setModuleType("moduleType");
        sysBusinessMassUploadLog.setGeoCode("geoCode");

        when(mockSysBusinessMassUploadLogMapper.updateSysBusinessMassUploadLog(new SysBusinessMassUploadLogDO())).thenReturn(0);

        // Run the test
        final int result = sysBusinessMassUploadLogManagerImplUnderTest.updateSysBusinessMassUploadLog(sysBusinessMassUploadLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessMassUploadLogByIds() {
        // Setup
        when(mockSysBusinessMassUploadLogMapper.deleteSysBusinessMassUploadLogByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysBusinessMassUploadLogManagerImplUnderTest.deleteSysBusinessMassUploadLogByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessMassUploadLogById() {
        // Setup
        when(mockSysBusinessMassUploadLogMapper.deleteSysBusinessMassUploadLogById(0L)).thenReturn(0);

        // Run the test
        final int result = sysBusinessMassUploadLogManagerImplUnderTest.deleteSysBusinessMassUploadLogById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessOperatorLogByOperationDate() {
        // Setup
        when(mockSysBusinessMassUploadLogMapper.deleteSysBusinessOperatorLogByOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);

        // Run the test
        final int result = sysBusinessMassUploadLogManagerImplUnderTest.deleteSysBusinessOperatorLogByOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
        assertEquals(0, result);
    }
}
