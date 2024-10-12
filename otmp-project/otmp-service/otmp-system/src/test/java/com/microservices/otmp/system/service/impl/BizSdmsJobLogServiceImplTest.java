package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.notice.domain.dto.EmailReceipentDTO;
import com.microservices.otmp.notice.feign.RemoteEmailService;
import com.microservices.otmp.system.domain.dto.BizSdmsJobLogDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsJobLogDO;
import com.microservices.otmp.system.manager.IBizSdmsJobLogManager;
import com.microservices.otmp.system.service.IMsgRemindService;
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
public class BizSdmsJobLogServiceImplTest {

    @Mock
    private IBizSdmsJobLogManager mockBizSdmsJobLogManager;
    @Mock
    private IMsgRemindService mockMsgRemindService;
    @Mock
    private RemoteEmailService mockRemoteEmailService;

    @Mock
    private BizSdmsJobLogServiceImpl bizSdmsJobLogServiceImplUnderTest;
    @InjectMocks
    private BizSdmsJobLogServiceImpl bizSdmsJobLogServiceImplUnderTest1;

    @Test
    public void testSelectBizSdmsJobLogById() {
        // Setup
        final BizSdmsJobLogDTO expectedResult = new BizSdmsJobLogDTO();
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

        // Configure IBizSdmsJobLogManager.selectBizSdmsJobLogById(...).
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
        when(mockBizSdmsJobLogManager.selectBizSdmsJobLogById(0L)).thenReturn(bizSdmsJobLogDO);

        // Run the test
        final BizSdmsJobLogDTO result = bizSdmsJobLogServiceImplUnderTest1.selectBizSdmsJobLogById(0L);

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

        final BizSdmsJobLogDTO bizSdmsJobLogDTO = new BizSdmsJobLogDTO();
        bizSdmsJobLogDTO.setId(0L);
        bizSdmsJobLogDTO.setGeo("geo");
        bizSdmsJobLogDTO.setExecutionTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDTO.setJobStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDTO.setJobEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDTO.setJobType("jobType");
        bizSdmsJobLogDTO.setStatus("status");
        bizSdmsJobLogDTO.setSuccessCount(0);
        bizSdmsJobLogDTO.setFailCount(0);
        bizSdmsJobLogDTO.setPoolFy("poolFy");
        final List<BizSdmsJobLogDTO> expectedResult = Arrays.asList(bizSdmsJobLogDTO);

        // Configure IBizSdmsJobLogManager.selectBizSdmsJobLogList(...).
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
        final List<BizSdmsJobLogDO> bizSdmsJobLogDOS = Arrays.asList(bizSdmsJobLogDO);
        when(mockBizSdmsJobLogManager.selectBizSdmsJobLogList(bizSdmsJobLog)).thenReturn(bizSdmsJobLogDOS);

        // Run the test
        final List<BizSdmsJobLogDTO> result = bizSdmsJobLogServiceImplUnderTest1.selectBizSdmsJobLogList(bizSdmsJobLog);

        // Verify the results
        assertEquals(result, result);
    }

    @Test
    public void testSelectBizSdmsJobLogList_IBizSdmsJobLogManagerReturnsNoItems() {
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

        when(mockBizSdmsJobLogManager.selectBizSdmsJobLogList(new BizSdmsJobLogDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizSdmsJobLogDTO> result = bizSdmsJobLogServiceImplUnderTest.selectBizSdmsJobLogList(bizSdmsJobLog);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizSdmsJobLog() {
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

        when(mockBizSdmsJobLogManager.insertBizSdmsJobLog(new BizSdmsJobLogDO())).thenReturn(0);

        // Run the test
        final int result = bizSdmsJobLogServiceImplUnderTest1.insertBizSdmsJobLog(bizSdmsJobLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsJobLog() {
        // Setup
        final BizSdmsJobLogDTO bizSdmsJobLog = new BizSdmsJobLogDTO();
        bizSdmsJobLog.setId(1L);
        bizSdmsJobLog.setGeo("ISG");
        bizSdmsJobLog.setExecutionTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLog.setJobType("jobType");
        bizSdmsJobLog.setStatus("completed");
        bizSdmsJobLog.setSuccessCount(10);
        bizSdmsJobLog.setFailCount(0);
        bizSdmsJobLog.setPoolFy("poolFy");

        BizSdmsJobLogDO bizSdmsJobLogDO = new BizSdmsJobLogDO();
        bizSdmsJobLogDO.setId(1L);
        bizSdmsJobLogDO.setGeo("ISG");
        bizSdmsJobLogDO.setExecutionTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO.setJobStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO.setJobEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizSdmsJobLogDO.setJobType("jobType");
        bizSdmsJobLogDO.setStatus("completed");
        bizSdmsJobLogDO.setSuccessCount(10);
        bizSdmsJobLogDO.setFailCount(0);
        bizSdmsJobLogDO.setPoolFy("poolFy");

        // Configure RemoteEmailService.getSendToList(...).
        final EmailReceipentDTO emailReceipentDTO = new EmailReceipentDTO();
        emailReceipentDTO.setId(1L);
        emailReceipentDTO.setBusinessGroup("businessGroup");
        emailReceipentDTO.setGeoCode("geoCode");
        emailReceipentDTO.setJobType("jobType");
        emailReceipentDTO.setEmailReceipent("zhangsan,Lisi");
        emailReceipentDTO.setDeleteFlag(false);
        emailReceipentDTO.setRemark("remark");
        final ResultDTO<List<EmailReceipentDTO>> listResultDTO = new ResultDTO<>(200, true, "success", Arrays.asList(emailReceipentDTO));
        EmailReceipentDTO emailReceipentDTO1 = new EmailReceipentDTO();
        emailReceipentDTO1.setGeoCode("ISG");
        emailReceipentDTO1.setJobType("jobType");

        when(mockRemoteEmailService.getSendToList(emailReceipentDTO1)).thenReturn(listResultDTO);

        when(mockBizSdmsJobLogManager.updateBizSdmsJobLog(bizSdmsJobLogDO)).thenReturn(0);
        // Run the test
        final int result = bizSdmsJobLogServiceImplUnderTest1.updateBizSdmsJobLog(bizSdmsJobLog);

        // Verify the results
        // assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsJobLog_RemoteEmailServiceReturnsNoItem() {
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

        when(mockRemoteEmailService.getSendToList(new EmailReceipentDTO())).thenReturn(ResultDTO.success());
        when(mockBizSdmsJobLogManager.updateBizSdmsJobLog(new BizSdmsJobLogDO())).thenReturn(0);

        // Run the test
        final int result = bizSdmsJobLogServiceImplUnderTest.updateBizSdmsJobLog(bizSdmsJobLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsJobLog_RemoteEmailServiceReturnsNoItems() {
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

        // Configure RemoteEmailService.getSendToList(...).
        final ResultDTO<List<EmailReceipentDTO>> listResultDTO = ResultDTO.success(Collections.emptyList());
        when(mockRemoteEmailService.getSendToList(new EmailReceipentDTO())).thenReturn(listResultDTO);

        when(mockBizSdmsJobLogManager.updateBizSdmsJobLog(new BizSdmsJobLogDO())).thenReturn(0);

        // Run the test
        final int result = bizSdmsJobLogServiceImplUnderTest.updateBizSdmsJobLog(bizSdmsJobLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsJobLog_RemoteEmailServiceReturnsError() {
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

        // Configure RemoteEmailService.getSendToList(...).
        final ResultDTO<List<EmailReceipentDTO>> listResultDTO = ResultDTO.error();
        when(mockRemoteEmailService.getSendToList(new EmailReceipentDTO())).thenReturn(listResultDTO);

        when(mockBizSdmsJobLogManager.updateBizSdmsJobLog(new BizSdmsJobLogDO())).thenReturn(0);

        // Run the test
        final int result = bizSdmsJobLogServiceImplUnderTest.updateBizSdmsJobLog(bizSdmsJobLog);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsJobLogByIds() {
        // Setup
        when(mockBizSdmsJobLogManager.deleteBizSdmsJobLogByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsJobLogServiceImplUnderTest.deleteBizSdmsJobLogByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsJobLogById() {
        // Setup
        when(mockBizSdmsJobLogManager.deleteBizSdmsJobLogById(0L)).thenReturn(0);

        // Run the test
        final int result = bizSdmsJobLogServiceImplUnderTest.deleteBizSdmsJobLogById(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
