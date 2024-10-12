package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.BizAsyncTaskLogDTO;
import com.microservices.otmp.system.domain.entity.BizAsyncTaskLogDO;
import com.microservices.otmp.system.mapper.BizAsyncTaskLogMapper;
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
public class BizAsyncTaskLogManagerImplTest {

    @Mock
    private BizAsyncTaskLogMapper mockBizAsyncTaskLogMapper;

    @InjectMocks
    private BizAsyncTaskLogManagerImpl bizAsyncTaskLogManagerImplUnderTest;

    @Test
    public void testSelectBizAsyncTaskLogById() {
        // Setup
        final BizAsyncTaskLogDO expectedResult = new BizAsyncTaskLogDO();
        expectedResult.setId(0L);
        expectedResult.setAsyncCode("asyncCode");
        expectedResult.setModule("module");
        expectedResult.setBusinessType("businessType");
        expectedResult.setStatus(0);
        expectedResult.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setProgress(0.0);
        expectedResult.setUrl("url");
        expectedResult.setUseTime(0.0);

        // Configure BizAsyncTaskLogMapper.selectBizAsyncTaskLogById(...).
        final BizAsyncTaskLogDO bizAsyncTaskLogDO = new BizAsyncTaskLogDO();
        bizAsyncTaskLogDO.setId(0L);
        bizAsyncTaskLogDO.setAsyncCode("asyncCode");
        bizAsyncTaskLogDO.setModule("module");
        bizAsyncTaskLogDO.setBusinessType("businessType");
        bizAsyncTaskLogDO.setStatus(0);
        bizAsyncTaskLogDO.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizAsyncTaskLogDO.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizAsyncTaskLogDO.setProgress(0.0);
        bizAsyncTaskLogDO.setUrl("url");
        bizAsyncTaskLogDO.setUseTime(0.0);
        when(mockBizAsyncTaskLogMapper.selectBizAsyncTaskLogById(0L)).thenReturn(bizAsyncTaskLogDO);

        // Run the test
        final BizAsyncTaskLogDO result = bizAsyncTaskLogManagerImplUnderTest.selectBizAsyncTaskLogById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizAsyncTaskLogList() {
        // Setup
        final BizAsyncTaskLogDTO bizAsyncTaskLog = new BizAsyncTaskLogDTO();
        bizAsyncTaskLog.setFullUrl("fullUrl");
        bizAsyncTaskLog.setStatusName("statusName");
        bizAsyncTaskLog.setBeginCreateTime("beginCreateTime");
        bizAsyncTaskLog.setEndCreateTime("endCreateTime");
        bizAsyncTaskLog.setId(0L);
        bizAsyncTaskLog.setAsyncCode("asyncCode");
        bizAsyncTaskLog.setModule("module");
        bizAsyncTaskLog.setBusinessType("businessType");
        bizAsyncTaskLog.setStatus(0);
        bizAsyncTaskLog.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        final BizAsyncTaskLogDO bizAsyncTaskLogDO = new BizAsyncTaskLogDO();
        bizAsyncTaskLogDO.setId(0L);
        bizAsyncTaskLogDO.setAsyncCode("asyncCode");
        bizAsyncTaskLogDO.setModule("module");
        bizAsyncTaskLogDO.setBusinessType("businessType");
        bizAsyncTaskLogDO.setStatus(0);
        bizAsyncTaskLogDO.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizAsyncTaskLogDO.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizAsyncTaskLogDO.setProgress(0.0);
        bizAsyncTaskLogDO.setUrl("url");
        bizAsyncTaskLogDO.setUseTime(0.0);
        final List<BizAsyncTaskLogDO> expectedResult = Arrays.asList(bizAsyncTaskLogDO);

        // Configure BizAsyncTaskLogMapper.selectBizAsyncTaskLogList(...).
        final BizAsyncTaskLogDO bizAsyncTaskLogDO1 = new BizAsyncTaskLogDO();
        bizAsyncTaskLogDO1.setId(0L);
        bizAsyncTaskLogDO1.setAsyncCode("asyncCode");
        bizAsyncTaskLogDO1.setModule("module");
        bizAsyncTaskLogDO1.setBusinessType("businessType");
        bizAsyncTaskLogDO1.setStatus(0);
        bizAsyncTaskLogDO1.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizAsyncTaskLogDO1.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizAsyncTaskLogDO1.setProgress(0.0);
        bizAsyncTaskLogDO1.setUrl("url");
        bizAsyncTaskLogDO1.setUseTime(0.0);
        final List<BizAsyncTaskLogDO> bizAsyncTaskLogDOS = Arrays.asList(bizAsyncTaskLogDO1);
        when(mockBizAsyncTaskLogMapper.selectBizAsyncTaskLogList(new BizAsyncTaskLogDTO())).thenReturn(bizAsyncTaskLogDOS);

        // Run the test
        final List<BizAsyncTaskLogDO> result = bizAsyncTaskLogManagerImplUnderTest.selectBizAsyncTaskLogList(bizAsyncTaskLog);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectBizAsyncTaskLogList_BizAsyncTaskLogMapperReturnsNoItems() {
        // Setup
        final BizAsyncTaskLogDTO bizAsyncTaskLog = new BizAsyncTaskLogDTO();
        bizAsyncTaskLog.setFullUrl("fullUrl");
        bizAsyncTaskLog.setStatusName("statusName");
        bizAsyncTaskLog.setBeginCreateTime("beginCreateTime");
        bizAsyncTaskLog.setEndCreateTime("endCreateTime");
        bizAsyncTaskLog.setId(0L);
        bizAsyncTaskLog.setAsyncCode("asyncCode");
        bizAsyncTaskLog.setModule("module");
        bizAsyncTaskLog.setBusinessType("businessType");
        bizAsyncTaskLog.setStatus(0);
        bizAsyncTaskLog.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizAsyncTaskLogMapper.selectBizAsyncTaskLogList(new BizAsyncTaskLogDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizAsyncTaskLogDO> result = bizAsyncTaskLogManagerImplUnderTest.selectBizAsyncTaskLogList(bizAsyncTaskLog);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizAsyncTaskLog() {
        // Setup
        final BizAsyncTaskLogDO bizAsyncTaskLog = new BizAsyncTaskLogDO();
        bizAsyncTaskLog.setId(0L);
        bizAsyncTaskLog.setAsyncCode("asyncCode");
        bizAsyncTaskLog.setModule("module");
        bizAsyncTaskLog.setBusinessType("businessType");
        bizAsyncTaskLog.setStatus(0);
        bizAsyncTaskLog.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizAsyncTaskLog.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizAsyncTaskLog.setProgress(0.0);
        bizAsyncTaskLog.setUrl("url");
        bizAsyncTaskLog.setUseTime(0.0);

        when(mockBizAsyncTaskLogMapper.insertBizAsyncTaskLog(new BizAsyncTaskLogDO())).thenReturn(0);

        // Run the test
        final int result = bizAsyncTaskLogManagerImplUnderTest.insertBizAsyncTaskLog(bizAsyncTaskLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizAsyncTaskLog() {
        // Setup
        final BizAsyncTaskLogDO bizAsyncTaskLog = new BizAsyncTaskLogDO();
        bizAsyncTaskLog.setId(0L);
        bizAsyncTaskLog.setAsyncCode("asyncCode");
        bizAsyncTaskLog.setModule("module");
        bizAsyncTaskLog.setBusinessType("businessType");
        bizAsyncTaskLog.setStatus(0);
        bizAsyncTaskLog.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizAsyncTaskLog.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizAsyncTaskLog.setProgress(0.0);
        bizAsyncTaskLog.setUrl("url");
        bizAsyncTaskLog.setUseTime(0.0);

        when(mockBizAsyncTaskLogMapper.updateBizAsyncTaskLog(new BizAsyncTaskLogDO())).thenReturn(0);

        // Run the test
        final int result = bizAsyncTaskLogManagerImplUnderTest.updateBizAsyncTaskLog(bizAsyncTaskLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizAsyncTaskLogByIds() {
        // Setup
        when(mockBizAsyncTaskLogMapper.deleteBizAsyncTaskLogByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizAsyncTaskLogManagerImplUnderTest.deleteBizAsyncTaskLogByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizAsyncTaskLogById() {
        // Setup
        when(mockBizAsyncTaskLogMapper.deleteBizAsyncTaskLogById(0L)).thenReturn(0);

        // Run the test
        final int result = bizAsyncTaskLogManagerImplUnderTest.deleteBizAsyncTaskLogById(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
