package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysKafkaLog;
import com.microservices.otmp.system.domain.entity.SysKafkaLogDO;
import com.microservices.otmp.system.mapper.SysKafkaLogMapper;
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
public class SysKafkaLogManagerImplTest {

    @Mock
    private SysKafkaLogMapper mockSysKafkaLogMapper;

    @InjectMocks
    private SysKafkaLogManagerImpl sysKafkaLogManagerImplUnderTest;

    @Test
    public void testInsertKafkalog() {
        // Setup
        final SysKafkaLogDO operLog = new SysKafkaLogDO();
        operLog.setIsError(false);
        operLog.setOffset(0L);
        operLog.setPartition(0);
        operLog.setKey("key");
        operLog.setOperId(0L);
        operLog.setOperIds(Arrays.asList(0L));
        operLog.setTopic("topic");
        operLog.setBusinessType(0);
        operLog.setMethod("method");
        operLog.setOperatorType(0);

        // Run the test
        sysKafkaLogManagerImplUnderTest.insertKafkalog(operLog);

        // Verify the results
//        verify(mockSysKafkaLogMapper).insertKafkalog(new SysKafkaLogDO());
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
        final List<SysKafkaLogDO> expectedResult = Arrays.asList(sysKafkaLogDO);

        // Configure SysKafkaLogMapper.selectKafkaLogList(...).
        final SysKafkaLogDO sysKafkaLogDO1 = new SysKafkaLogDO();
        sysKafkaLogDO1.setIsError(false);
        sysKafkaLogDO1.setOffset(0L);
        sysKafkaLogDO1.setPartition(0);
        sysKafkaLogDO1.setKey("key");
        sysKafkaLogDO1.setOperId(0L);
        sysKafkaLogDO1.setOperIds(Arrays.asList(0L));
        sysKafkaLogDO1.setTopic("topic");
        sysKafkaLogDO1.setBusinessType(0);
        sysKafkaLogDO1.setMethod("method");
        sysKafkaLogDO1.setOperatorType(0);
        final List<SysKafkaLogDO> sysKafkaLogDOS = Arrays.asList(sysKafkaLogDO1);
        when(mockSysKafkaLogMapper.selectKafkaLogList(new SysKafkaLog())).thenReturn(sysKafkaLogDOS);

        // Run the test
        final List<SysKafkaLogDO> result = sysKafkaLogManagerImplUnderTest.selectKafkaLogList(operLog);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectKafkaLogList_SysKafkaLogMapperReturnsNoItems() {
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

        when(mockSysKafkaLogMapper.selectKafkaLogList(new SysKafkaLog())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysKafkaLogDO> result = sysKafkaLogManagerImplUnderTest.selectKafkaLogList(operLog);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDeleteKafkaLogByIds() {
        // Setup
        when(mockSysKafkaLogMapper.deleteKafkaLogByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = sysKafkaLogManagerImplUnderTest.deleteKafkaLogByIds(new String[]{"value"});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectKafkaLogById() {
        // Setup
        final SysKafkaLogDO expectedResult = new SysKafkaLogDO();
        expectedResult.setIsError(false);
        expectedResult.setOffset(0L);
        expectedResult.setPartition(0);
        expectedResult.setKey("key");
        expectedResult.setOperId(0L);
        expectedResult.setOperIds(Arrays.asList(0L));
        expectedResult.setTopic("topic");
        expectedResult.setBusinessType(0);
        expectedResult.setMethod("method");
        expectedResult.setOperatorType(0);

        // Configure SysKafkaLogMapper.selectKafkaLogById(...).
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
        when(mockSysKafkaLogMapper.selectKafkaLogById(0L)).thenReturn(sysKafkaLogDO);

        // Run the test
        final SysKafkaLogDO result = sysKafkaLogManagerImplUnderTest.selectKafkaLogById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCleanKafkaLog() {
        // Setup
        // Run the test
        sysKafkaLogManagerImplUnderTest.cleanKafkaLog();

        // Verify the results
        verify(mockSysKafkaLogMapper).cleanKafkaLog();
    }
}
