package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.system.domain.BizAsyncTaskLogDTO;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.domain.entity.BizAsyncTaskLogDO;
import com.microservices.otmp.system.manager.IBizAsyncTaskLogManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BizAsyncTaskLogServiceImplTest {

    @Mock
    private IBizAsyncTaskLogManager mockBizAsyncTaskLogManager;
    @Mock
    private RedisUtils mockRedisUtils;

    @InjectMocks
    private BizAsyncTaskLogServiceImpl bizAsyncTaskLogServiceImplUnderTest;

    @Test
    public void testSelectBizAsyncTaskLogById() {
        // Setup
        final BizAsyncTaskLogDTO expectedResult = new BizAsyncTaskLogDTO();
        expectedResult.setFullUrl("fullUrl");
        expectedResult.setStatusName("statusName");
        expectedResult.setBeginCreateTime("beginCreateTime");
        expectedResult.setEndCreateTime("endCreateTime");
        expectedResult.setId(0L);
        expectedResult.setAsyncCode("asyncCode");
        expectedResult.setModule("module");
        expectedResult.setBusinessType("businessType");
        expectedResult.setStatus(0);
        expectedResult.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure IBizAsyncTaskLogManager.selectBizAsyncTaskLogById(...).
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
        when(mockBizAsyncTaskLogManager.selectBizAsyncTaskLogById(0L)).thenReturn(bizAsyncTaskLogDO);

        // Run the test
        final BizAsyncTaskLogDTO result = bizAsyncTaskLogServiceImplUnderTest.selectBizAsyncTaskLogById(0L);

        // Verify the results
        assertEquals(expectedResult.getStatus(), result.getStatus());
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

        final BizAsyncTaskLogDTO bizAsyncTaskLogDTO = new BizAsyncTaskLogDTO();
        bizAsyncTaskLogDTO.setFullUrl("fullUrl");
        bizAsyncTaskLogDTO.setStatusName("statusName");
        bizAsyncTaskLogDTO.setBeginCreateTime("beginCreateTime");
        bizAsyncTaskLogDTO.setEndCreateTime("endCreateTime");
        bizAsyncTaskLogDTO.setId(0L);
        bizAsyncTaskLogDTO.setAsyncCode("asyncCode");
        bizAsyncTaskLogDTO.setModule("module");
        bizAsyncTaskLogDTO.setBusinessType("businessType");
        bizAsyncTaskLogDTO.setStatus(0);
        bizAsyncTaskLogDTO.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizAsyncTaskLogDTO> expectedResult = Arrays.asList(bizAsyncTaskLogDTO);

        // Configure IBizAsyncTaskLogManager.selectBizAsyncTaskLogList(...).
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
        final List<BizAsyncTaskLogDO> bizAsyncTaskLogDOS = Arrays.asList(bizAsyncTaskLogDO);
        when(mockBizAsyncTaskLogManager.selectBizAsyncTaskLogList(new BizAsyncTaskLogDTO())).thenReturn(bizAsyncTaskLogDOS);

        when(mockRedisUtils.get("h", "hk")).thenReturn("result");

        // Run the test
        final List<BizAsyncTaskLogDTO> result = bizAsyncTaskLogServiceImplUnderTest.selectBizAsyncTaskLogList(bizAsyncTaskLog);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectBizAsyncTaskLogList_IBizAsyncTaskLogManagerReturnsNoItems() {
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

        when(mockBizAsyncTaskLogManager.selectBizAsyncTaskLogList(new BizAsyncTaskLogDTO())).thenReturn(Collections.emptyList());
        when(mockRedisUtils.get("h", "hk")).thenReturn("result");

        // Run the test
        final List<BizAsyncTaskLogDTO> result = bizAsyncTaskLogServiceImplUnderTest.selectBizAsyncTaskLogList(bizAsyncTaskLog);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizAsyncTaskLog() {
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

        final BizAsyncTaskLogDTO expectedResult = new BizAsyncTaskLogDTO();
        expectedResult.setFullUrl("fullUrl");
        expectedResult.setStatusName("statusName");
        expectedResult.setBeginCreateTime("beginCreateTime");
        expectedResult.setEndCreateTime("endCreateTime");
        expectedResult.setId(0L);
        expectedResult.setAsyncCode("asyncCode");
        expectedResult.setModule("module");
        expectedResult.setBusinessType("businessType");
        expectedResult.setStatus(0);
        expectedResult.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizAsyncTaskLogManager.insertBizAsyncTaskLog(new BizAsyncTaskLogDO())).thenReturn(0);

        // Run the test
        final BizAsyncTaskLogDTO result = bizAsyncTaskLogServiceImplUnderTest.insertBizAsyncTaskLog(bizAsyncTaskLog);

        // Verify the results
        assertEquals(expectedResult, result);
      //  verify(mockBizAsyncTaskLogManager).insertBizAsyncTaskLog(new BizAsyncTaskLogDO());
    }

    @Test
    public void testUpdateBizAsyncTaskLog() {
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

        when(mockBizAsyncTaskLogManager.updateBizAsyncTaskLog(new BizAsyncTaskLogDO())).thenReturn(0);

        // Run the test
        final int result = bizAsyncTaskLogServiceImplUnderTest.updateBizAsyncTaskLog(bizAsyncTaskLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizAsyncTaskLogByIds() {
        // Setup
        when(mockBizAsyncTaskLogManager.deleteBizAsyncTaskLogByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizAsyncTaskLogServiceImplUnderTest.deleteBizAsyncTaskLogByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizAsyncTaskLogById() {
        // Setup
        when(mockBizAsyncTaskLogManager.deleteBizAsyncTaskLogById(0L)).thenReturn(0);

        // Run the test
        final int result = bizAsyncTaskLogServiceImplUnderTest.deleteBizAsyncTaskLogById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testGetProgress() {
        // Setup
        final BizAsyncTaskLogDTO bizAsyncTaskLogDTO = new BizAsyncTaskLogDTO();
        bizAsyncTaskLogDTO.setFullUrl("fullUrl");
        bizAsyncTaskLogDTO.setStatusName("statusName");
        bizAsyncTaskLogDTO.setBeginCreateTime("beginCreateTime");
        bizAsyncTaskLogDTO.setEndCreateTime("endCreateTime");
        bizAsyncTaskLogDTO.setId(0L);
        bizAsyncTaskLogDTO.setAsyncCode("asyncCode");
        bizAsyncTaskLogDTO.setModule("module");
        bizAsyncTaskLogDTO.setBusinessType("businessType");
        bizAsyncTaskLogDTO.setStatus(0);
        bizAsyncTaskLogDTO.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizAsyncTaskLogDTO> expectedResult = Arrays.asList(bizAsyncTaskLogDTO);
        when(mockRedisUtils.get("h", "hk")).thenReturn("result");

        // Configure IBizAsyncTaskLogManager.selectBizAsyncTaskLogById(...).
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
        when(mockBizAsyncTaskLogManager.selectBizAsyncTaskLogById(0L)).thenReturn(bizAsyncTaskLogDO);

        // Run the test
        final List<BizAsyncTaskLogDTO> result = bizAsyncTaskLogServiceImplUnderTest.getProgress(new Long[]{0L});

        // Verify the results
        assertEquals(expectedResult.get(0).getStatus(), result.get(0).getStatus());
    }

    @Test
    public void testDeleteAsyncTask() {
        // Setup
        when(mockBizAsyncTaskLogManager.updateBizAsyncTaskLog(new BizAsyncTaskLogDO())).thenReturn(0);

        // Run the test
        bizAsyncTaskLogServiceImplUnderTest.deleteAsyncTask(0L);

        // Verify the results
       // verify(mockBizAsyncTaskLogManager).updateBizAsyncTaskLog(new BizAsyncTaskLogDO());
    }

    @Test
    public void testBusinessTypeSelect() {
        // Setup
        // Run the test
        final List<SysDictData> result = bizAsyncTaskLogServiceImplUnderTest.businessTypeSelect();

        // Verify the results
    }

    @Test
    public void testModuleSelect() {
        // Setup
        // Run the test
        final List<SysDictData> result = bizAsyncTaskLogServiceImplUnderTest.moduleSelect();

        // Verify the results
    }

    @Test
    public void testStatusSelect() {
        // Setup
        // Run the test
        final List<SysDictData> result = bizAsyncTaskLogServiceImplUnderTest.statusSelect();

        // Verify the results
    }
}
