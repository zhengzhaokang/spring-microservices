package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.dto.SysBusinessMassUploadLogDTO;
import com.microservices.otmp.system.domain.entity.SysBusinessMassUploadLogDO;
import com.microservices.otmp.system.manager.ISysBusinessMassUploadLogManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysBusinessMassUploadLogServiceImplTest {

    @Mock
    private ISysBusinessMassUploadLogManager mockSysBusinessMassUploadLogManager;

    @InjectMocks
    private SysBusinessMassUploadLogServiceImpl sysBusinessMassUploadLogServiceImplUnderTest;

    @Test
    public void testSelectSysBusinessMassUploadLogById() {
        // Setup
        final SysBusinessMassUploadLogDTO expectedResult = new SysBusinessMassUploadLogDTO();
        expectedResult.setId(0L);
        expectedResult.setFileName("fileName");
        expectedResult.setStatus("status");
        expectedResult.setRecordCount(0);
        expectedResult.setErrorCount(0);
        expectedResult.setOperator("operator");
        expectedResult.setOperatorDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setOperatorDateStart(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setOperatorDateEnd(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setStartDateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure ISysBusinessMassUploadLogManager.selectSysBusinessMassUploadLogById(...).
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
        when(mockSysBusinessMassUploadLogManager.selectSysBusinessMassUploadLogById(0L)).thenReturn(sysBusinessMassUploadLogDO);

        // Run the test
        final SysBusinessMassUploadLogDTO result = sysBusinessMassUploadLogServiceImplUnderTest.selectSysBusinessMassUploadLogById(0L);

        // Verify the results
        assertNotNull(result);
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

        // Configure ISysBusinessMassUploadLogManager.selectSysBusinessMassUploadLogList(...).
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
        final List<SysBusinessMassUploadLogDO> sysBusinessMassUploadLogDOS = Arrays.asList(sysBusinessMassUploadLogDO);
        when(mockSysBusinessMassUploadLogManager.selectSysBusinessMassUploadLogList(new SysBusinessMassUploadLogDTO())).thenReturn(sysBusinessMassUploadLogDOS);

        // Run the test
        final PageInfo<SysBusinessMassUploadLogDTO> result = sysBusinessMassUploadLogServiceImplUnderTest.selectSysBusinessMassUploadLogList(sysBusinessMassUploadLog);

        // Verify the results
    }

    @Test
    public void testSelectSysBusinessMassUploadLogList_ISysBusinessMassUploadLogManagerReturnsNoItems() {
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

        when(mockSysBusinessMassUploadLogManager.selectSysBusinessMassUploadLogList(new SysBusinessMassUploadLogDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<SysBusinessMassUploadLogDTO> result = sysBusinessMassUploadLogServiceImplUnderTest.selectSysBusinessMassUploadLogList(sysBusinessMassUploadLog);

        // Verify the results
    }

    @Test
    public void testInsertSysBusinessMassUploadLog() {
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

        when(mockSysBusinessMassUploadLogManager.insertSysBusinessMassUploadLog(new SysBusinessMassUploadLogDO())).thenReturn(0);

        // Run the test
        final int result = sysBusinessMassUploadLogServiceImplUnderTest.insertSysBusinessMassUploadLog(sysBusinessMassUploadLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateSysBusinessMassUploadLog() {
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

        when(mockSysBusinessMassUploadLogManager.updateSysBusinessMassUploadLog(new SysBusinessMassUploadLogDO())).thenReturn(0);

        // Run the test
        final int result = sysBusinessMassUploadLogServiceImplUnderTest.updateSysBusinessMassUploadLog(sysBusinessMassUploadLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessMassUploadLogByIds() {
        // Setup
        when(mockSysBusinessMassUploadLogManager.deleteSysBusinessMassUploadLogByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysBusinessMassUploadLogServiceImplUnderTest.deleteSysBusinessMassUploadLogByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessMassUploadLogById() {
        // Setup
        when(mockSysBusinessMassUploadLogManager.deleteSysBusinessMassUploadLogById(0L)).thenReturn(0);

        // Run the test
        final int result = sysBusinessMassUploadLogServiceImplUnderTest.deleteSysBusinessMassUploadLogById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysBusinessOperatorLogByOperationDate() {
        // Setup
        when(mockSysBusinessMassUploadLogManager.deleteSysBusinessOperatorLogByOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);

        // Run the test
        final int result = sysBusinessMassUploadLogServiceImplUnderTest.deleteSysBusinessOperatorLogByOperationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
        assertEquals(0, result);
    }
}
