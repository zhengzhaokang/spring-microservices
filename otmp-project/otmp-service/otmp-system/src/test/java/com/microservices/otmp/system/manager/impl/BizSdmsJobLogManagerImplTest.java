package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.dto.BizSdmsJobLogDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsJobLogDO;
import com.microservices.otmp.system.mapper.BizSdmsJobLogMapper;
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
public class BizSdmsJobLogManagerImplTest {

    @Mock
    private BizSdmsJobLogMapper mockBizSdmsJobLogMapper;

    @InjectMocks
    private BizSdmsJobLogManagerImpl bizSdmsJobLogManagerImplUnderTest;

    @Test
    public void testSelectBizSdmsJobLogById() {
        // Setup
        final BizSdmsJobLogDO expectedResult = new BizSdmsJobLogDO();
        expectedResult.setId(0L);
        expectedResult.setGeo("geo");
        expectedResult.setExecutionTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setJobStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setJobEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setJobType("jobType");
        expectedResult.setStatus("status");
        expectedResult.setSuccessCount(0);
        expectedResult.setFailCount(0);
        expectedResult.setPoolFy("poolFy");

        // Configure BizSdmsJobLogMapper.selectBizSdmsJobLogById(...).
        final BizSdmsJobLogDO bizSdmsJobLogDO = new BizSdmsJobLogDO();
        bizSdmsJobLogDO.setId(0L);
        bizSdmsJobLogDO.setGeo("geo");
        bizSdmsJobLogDO.setExecutionTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO.setJobStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO.setJobEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO.setJobType("jobType");
        bizSdmsJobLogDO.setStatus("status");
        bizSdmsJobLogDO.setSuccessCount(0);
        bizSdmsJobLogDO.setFailCount(0);
        bizSdmsJobLogDO.setPoolFy("poolFy");
        when(mockBizSdmsJobLogMapper.selectBizSdmsJobLogById(0L)).thenReturn(bizSdmsJobLogDO);

        // Run the test
        final BizSdmsJobLogDO result = bizSdmsJobLogManagerImplUnderTest.selectBizSdmsJobLogById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizSdmsJobLogList() {
        // Setup
        final BizSdmsJobLogDTO bizSdmsJobLog = new BizSdmsJobLogDTO();
        bizSdmsJobLog.setId(0L);
        bizSdmsJobLog.setGeo("geo");
        bizSdmsJobLog.setExecutionTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobType("jobType");
        bizSdmsJobLog.setStatus("status");
        bizSdmsJobLog.setSuccessCount(0);
        bizSdmsJobLog.setFailCount(0);
        bizSdmsJobLog.setPoolFy("poolFy");

        final BizSdmsJobLogDO bizSdmsJobLogDO = new BizSdmsJobLogDO();
        bizSdmsJobLogDO.setId(0L);
        bizSdmsJobLogDO.setGeo("geo");
        bizSdmsJobLogDO.setExecutionTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO.setJobStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO.setJobEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO.setJobType("jobType");
        bizSdmsJobLogDO.setStatus("status");
        bizSdmsJobLogDO.setSuccessCount(0);
        bizSdmsJobLogDO.setFailCount(0);
        bizSdmsJobLogDO.setPoolFy("poolFy");
        final List<BizSdmsJobLogDO> expectedResult = Arrays.asList(bizSdmsJobLogDO);

        // Configure BizSdmsJobLogMapper.selectBizSdmsJobLogList(...).
        final BizSdmsJobLogDO bizSdmsJobLogDO1 = new BizSdmsJobLogDO();
        bizSdmsJobLogDO1.setId(0L);
        bizSdmsJobLogDO1.setGeo("geo");
        bizSdmsJobLogDO1.setExecutionTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO1.setJobStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO1.setJobEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO1.setJobType("jobType");
        bizSdmsJobLogDO1.setStatus("status");
        bizSdmsJobLogDO1.setSuccessCount(0);
        bizSdmsJobLogDO1.setFailCount(0);
        bizSdmsJobLogDO1.setPoolFy("poolFy");
        final List<BizSdmsJobLogDO> bizSdmsJobLogDOS = Arrays.asList(bizSdmsJobLogDO1);
        when(mockBizSdmsJobLogMapper.selectBizSdmsJobLogList(bizSdmsJobLog)).thenReturn(bizSdmsJobLogDOS);

        // Run the test
        final List<BizSdmsJobLogDO> result = bizSdmsJobLogManagerImplUnderTest.selectBizSdmsJobLogList(bizSdmsJobLog);

        // Verify the results
        assertEquals(1, result.size());
    }

    @Test
    public void testSelectBizSdmsJobLogList_BizSdmsJobLogMapperReturnsNoItems() {
        // Setup
        final BizSdmsJobLogDTO bizSdmsJobLog = new BizSdmsJobLogDTO();
        bizSdmsJobLog.setId(0L);
        bizSdmsJobLog.setGeo("geo");
        bizSdmsJobLog.setExecutionTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobType("jobType");
        bizSdmsJobLog.setStatus("status");
        bizSdmsJobLog.setSuccessCount(0);
        bizSdmsJobLog.setFailCount(0);
        bizSdmsJobLog.setPoolFy("poolFy");

        when(mockBizSdmsJobLogMapper.selectBizSdmsJobLogList(bizSdmsJobLog)).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizSdmsJobLogDO> result = bizSdmsJobLogManagerImplUnderTest.selectBizSdmsJobLogList(bizSdmsJobLog);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testInsertBizSdmsJobLog() {
        // Setup
        final BizSdmsJobLogDO bizSdmsJobLog = new BizSdmsJobLogDO();
        bizSdmsJobLog.setId(0L);
        bizSdmsJobLog.setGeo("geo");
        bizSdmsJobLog.setExecutionTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobType("jobType");
        bizSdmsJobLog.setStatus("status");
        bizSdmsJobLog.setSuccessCount(0);
        bizSdmsJobLog.setFailCount(0);
        bizSdmsJobLog.setPoolFy("poolFy");

        when(mockBizSdmsJobLogMapper.insertBizSdmsJobLog(bizSdmsJobLog)).thenReturn(1);

        // Run the test
        final int result = bizSdmsJobLogManagerImplUnderTest.insertBizSdmsJobLog(bizSdmsJobLog);

        // Verify the results
        assertEquals(1, result);
    }

    @Test
    public void testUpdateBizSdmsJobLog() {
        // Setup
        final BizSdmsJobLogDO bizSdmsJobLog = new BizSdmsJobLogDO();
        bizSdmsJobLog.setId(0L);
        bizSdmsJobLog.setGeo("geo");
        bizSdmsJobLog.setExecutionTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobType("jobType");
        bizSdmsJobLog.setStatus("status");
        bizSdmsJobLog.setSuccessCount(0);
        bizSdmsJobLog.setFailCount(0);
        bizSdmsJobLog.setPoolFy("poolFy");

        when(mockBizSdmsJobLogMapper.updateBizSdmsJobLog(bizSdmsJobLog)).thenReturn(1);

        // Run the test
        final int result = bizSdmsJobLogManagerImplUnderTest.updateBizSdmsJobLog(bizSdmsJobLog);

        // Verify the results
        assertEquals(1, result);
    }

    @Test
    public void testDeleteBizSdmsJobLogByIds() {
        // Setup
        when(mockBizSdmsJobLogMapper.deleteBizSdmsJobLogByIds(new Long[]{0L})).thenReturn(0);

        // Run the test
        final int result = bizSdmsJobLogManagerImplUnderTest.deleteBizSdmsJobLogByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsJobLogById() {
        // Setup
        when(mockBizSdmsJobLogMapper.deleteBizSdmsJobLogById(0L)).thenReturn(0);

        // Run the test
        final int result = bizSdmsJobLogManagerImplUnderTest.deleteBizSdmsJobLogById(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
