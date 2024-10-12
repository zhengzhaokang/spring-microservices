package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.SysKafkaLog;
import com.microservices.otmp.system.domain.entity.SysKafkaLogDO;
import com.microservices.otmp.system.manager.SysKafkaLogManager;
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
public class SysKafkaLogServiceImplTest {

    @Mock
    private SysKafkaLogManager mockKafkaLogManager;

    @InjectMocks
    private SysKafkaLogServiceImpl sysKafkaLogServiceImplUnderTest;

    @Test
    public void testInsertKafkalog() {
        // Setup
        final SysKafkaLog operLog = new SysKafkaLog();
        operLog.setOperId(0L);
        operLog.setOffset(0L);
        operLog.setPartition(0);
        operLog.setKey("key");
        operLog.setOperIds(Arrays.asList(0L));
        operLog.setTopic("topic");
        operLog.setBusinessType(0);
        operLog.setIsSend(false);
        operLog.setSendIp("sendIp");
        operLog.setReceiveIp("receiveIp");

        // Run the test
        sysKafkaLogServiceImplUnderTest.insertKafkalog(operLog);

    }

    @Test
    public void testSelectKafkaLogList() {
        // Setup
        final SysKafkaLog operLog = new SysKafkaLog();
        operLog.setOperId(0L);
        operLog.setOffset(0L);
        operLog.setPartition(0);
        operLog.setKey("key");
        operLog.setOperIds(Arrays.asList(0L));
        operLog.setTopic("topic");
        operLog.setBusinessType(0);
        operLog.setIsSend(false);
        operLog.setSendIp("sendIp");
        operLog.setReceiveIp("receiveIp");

        final SysKafkaLog sysKafkaLog = new SysKafkaLog();
        sysKafkaLog.setOperId(0L);
        sysKafkaLog.setOffset(0L);
        sysKafkaLog.setPartition(0);
        sysKafkaLog.setKey("key");
        sysKafkaLog.setOperIds(Arrays.asList(0L));
        sysKafkaLog.setTopic("topic");
        sysKafkaLog.setBusinessType(0);
        sysKafkaLog.setIsSend(false);
        sysKafkaLog.setSendIp("sendIp");
        sysKafkaLog.setReceiveIp("receiveIp");
        final List<SysKafkaLog> expectedResult = Arrays.asList(sysKafkaLog);

        // Configure SysKafkaLogManager.selectKafkaLogList(...).
        final SysKafkaLogDO sysKafkaLogDO = new SysKafkaLogDO();
        sysKafkaLogDO.setIsError(false);
        sysKafkaLogDO.setOffset(0L);
        sysKafkaLogDO.setPartition(0);
        sysKafkaLogDO.setKey("key");
        sysKafkaLogDO.setOperId(0L);
        sysKafkaLogDO.setOperIds(Arrays.asList(0L));
        sysKafkaLogDO.setTopic("topic");
        sysKafkaLogDO.setBusinessType(0);
        sysKafkaLogDO.setMethod("method");
        sysKafkaLogDO.setOperatorType(0);
        final List<SysKafkaLogDO> sysKafkaLogDOS = Arrays.asList(sysKafkaLogDO);
        when(mockKafkaLogManager.selectKafkaLogList(new SysKafkaLog())).thenReturn(sysKafkaLogDOS);

        // Run the test
        final List<SysKafkaLog> result = sysKafkaLogServiceImplUnderTest.selectKafkaLogList(operLog);


    }

    @Test
    public void testSelectKafkaLogList_SysKafkaLogManagerReturnsNoItems() {
        // Setup
        final SysKafkaLog operLog = new SysKafkaLog();
        operLog.setOperId(0L);
        operLog.setOffset(0L);
        operLog.setPartition(0);
        operLog.setKey("key");
        operLog.setOperIds(Arrays.asList(0L));
        operLog.setTopic("topic");
        operLog.setBusinessType(0);
        operLog.setIsSend(false);
        operLog.setSendIp("sendIp");
        operLog.setReceiveIp("receiveIp");

        when(mockKafkaLogManager.selectKafkaLogList(new SysKafkaLog())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysKafkaLog> result = sysKafkaLogServiceImplUnderTest.selectKafkaLogList(operLog);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDeleteKafkaLogByIds() {
        // Setup
        when(mockKafkaLogManager.deleteKafkaLogByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = sysKafkaLogServiceImplUnderTest.deleteKafkaLogByIds("ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectKafkaLogById() {
        // Setup
        final SysKafkaLog expectedResult = new SysKafkaLog();
        expectedResult.setOperId(0L);
        expectedResult.setOffset(0L);
        expectedResult.setPartition(0);
        expectedResult.setKey("key");
        expectedResult.setOperIds(Arrays.asList(0L));
        expectedResult.setTopic("topic");
        expectedResult.setBusinessType(0);
        expectedResult.setIsSend(false);
        expectedResult.setSendIp("sendIp");
        expectedResult.setReceiveIp("receiveIp");

        // Configure SysKafkaLogManager.selectKafkaLogById(...).
        final SysKafkaLogDO sysKafkaLogDO = new SysKafkaLogDO();
        sysKafkaLogDO.setIsError(false);
        sysKafkaLogDO.setOffset(0L);
        sysKafkaLogDO.setPartition(0);
        sysKafkaLogDO.setKey("key");
        sysKafkaLogDO.setOperId(0L);
        sysKafkaLogDO.setOperIds(Arrays.asList(0L));
        sysKafkaLogDO.setTopic("topic");
        sysKafkaLogDO.setBusinessType(0);
        sysKafkaLogDO.setMethod("method");
        sysKafkaLogDO.setOperatorType(0);
        when(mockKafkaLogManager.selectKafkaLogById(0L)).thenReturn(sysKafkaLogDO);

        // Run the test
        final SysKafkaLog result = sysKafkaLogServiceImplUnderTest.selectKafkaLogById(0L);

        // Verify the results
        assertEquals(false, result.getIsError());
    }

    @Test
    public void testCleanKafkaLog() {
        // Setup
        // Run the test
        sysKafkaLogServiceImplUnderTest.cleanKafkaLog();

        // Verify the results
        verify(mockKafkaLogManager).cleanKafkaLog();
    }

    @Test
    public void testSelectKafkaLogPage() {
        // Setup
        final SysKafkaLog operLog = new SysKafkaLog();
        operLog.setOperId(0L);
        operLog.setOffset(0L);
        operLog.setPartition(0);
        operLog.setKey("key");
        operLog.setOperIds(Arrays.asList(0L));
        operLog.setTopic("topic");
        operLog.setBusinessType(0);
        operLog.setIsSend(false);
        operLog.setSendIp("sendIp");
        operLog.setReceiveIp("receiveIp");

        // Configure SysKafkaLogManager.selectKafkaLogList(...).
        final SysKafkaLogDO sysKafkaLogDO = new SysKafkaLogDO();
        sysKafkaLogDO.setIsError(false);
        sysKafkaLogDO.setOffset(0L);
        sysKafkaLogDO.setPartition(0);
        sysKafkaLogDO.setKey("key");
        sysKafkaLogDO.setOperId(0L);
        sysKafkaLogDO.setOperIds(Arrays.asList(0L));
        sysKafkaLogDO.setTopic("topic");
        sysKafkaLogDO.setBusinessType(0);
        sysKafkaLogDO.setMethod("method");
        sysKafkaLogDO.setOperatorType(0);
        final List<SysKafkaLogDO> sysKafkaLogDOS = Arrays.asList(sysKafkaLogDO);
        when(mockKafkaLogManager.selectKafkaLogList(new SysKafkaLog())).thenReturn(sysKafkaLogDOS);

        // Run the test
        final PageInfo<SysKafkaLog> result = sysKafkaLogServiceImplUnderTest.selectKafkaLogPage(operLog);

        // Verify the results
    }

    @Test
    public void testSelectKafkaLogPage_SysKafkaLogManagerReturnsNoItems() {
        // Setup
        final SysKafkaLog operLog = new SysKafkaLog();
        operLog.setOperId(0L);
        operLog.setOffset(0L);
        operLog.setPartition(0);
        operLog.setKey("key");
        operLog.setOperIds(Arrays.asList(0L));
        operLog.setTopic("topic");
        operLog.setBusinessType(0);
        operLog.setIsSend(false);
        operLog.setSendIp("sendIp");
        operLog.setReceiveIp("receiveIp");

        when(mockKafkaLogManager.selectKafkaLogList(new SysKafkaLog())).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<SysKafkaLog> result = sysKafkaLogServiceImplUnderTest.selectKafkaLogPage(operLog);

        // Verify the results
    }
}
