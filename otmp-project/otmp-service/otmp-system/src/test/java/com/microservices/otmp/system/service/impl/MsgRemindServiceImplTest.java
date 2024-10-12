package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.system.domain.MsgRemindDTO;
import com.microservices.otmp.system.domain.entity.MsgRemindDO;
import com.microservices.otmp.system.manager.IMsgRemindManager;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MsgRemindServiceImplTest {

    @Mock
    private RedisUtils mockRedisUtils;
    @Mock
    private KafkaSend mockKafkaSend;
    @Mock
    private IMsgRemindManager mockMsgRemindManager;

    @InjectMocks
    private MsgRemindServiceImpl msgRemindServiceImplUnderTest;

    @Test
    public void testSelectMsgRemindById() {
        // Setup
        final MsgRemindDTO expectedResult = new MsgRemindDTO();
        expectedResult.setId(0L);
        expectedResult.setMsgUuid("msgUuid");
        expectedResult.setItCodes("itCodes");
        expectedResult.setModule("module");
        expectedResult.setMsgTitle("msgTitle");
        expectedResult.setMsgInfo("msgInfo");
        expectedResult.setMsgTopic("msgTopic");
        expectedResult.setMsgType("msgType");
        expectedResult.setSysCatalog("sysCatalog");
        expectedResult.setHasRead(0);

        // Configure IMsgRemindManager.selectMsgRemindById(...).
        final MsgRemindDO msgRemindDO = new MsgRemindDO();
        msgRemindDO.setId(0L);
        msgRemindDO.setItCodes("itCodes");
        msgRemindDO.setModule("module");
        msgRemindDO.setMsgTopic("msgTopic");
        msgRemindDO.setSysCatalog("sysCatalog");
        msgRemindDO.setMsgTitle("msgTitle");
        msgRemindDO.setMsgInfo("msgInfo");
        msgRemindDO.setMsgType("msgType");
        msgRemindDO.setHasRead(0);
        msgRemindDO.setIsUpdate(0);
        when(mockMsgRemindManager.selectMsgRemindById(0L)).thenReturn(msgRemindDO);

        // Run the test
        final MsgRemindDTO result = msgRemindServiceImplUnderTest.selectMsgRemindById(0L);

        // Verify the results
        assertEquals(expectedResult.getHasRead(), result.getHasRead());
    }

    @Test
    public void testSelectMsgRemindList() {
        // Setup
        final MsgRemindDTO msgRemind = new MsgRemindDTO();
        msgRemind.setId(0L);
        msgRemind.setMsgUuid("msgUuid");
        msgRemind.setItCodes("itCodes");
        msgRemind.setModule("module");
        msgRemind.setMsgTitle("msgTitle");
        msgRemind.setMsgInfo("msgInfo");
        msgRemind.setMsgTopic("msgTopic");
        msgRemind.setMsgType("msgType");
        msgRemind.setSysCatalog("sysCatalog");
        msgRemind.setHasRead(0);

        final MsgRemindDTO msgRemindDTO = new MsgRemindDTO();
        msgRemindDTO.setId(0L);
        msgRemindDTO.setMsgUuid("msgUuid");
        msgRemindDTO.setItCodes("itCodes");
        msgRemindDTO.setModule("module");
        msgRemindDTO.setMsgTitle("msgTitle");
        msgRemindDTO.setMsgInfo("msgInfo");
        msgRemindDTO.setMsgTopic("msgTopic");
        msgRemindDTO.setMsgType("msgType");
        msgRemindDTO.setSysCatalog("sysCatalog");
        msgRemindDTO.setHasRead(0);
        final List<MsgRemindDTO> expectedResult = Arrays.asList(msgRemindDTO);

        // Configure IMsgRemindManager.selectMsgRemindList(...).
        final MsgRemindDO msgRemindDO = new MsgRemindDO();
        msgRemindDO.setId(0L);
        msgRemindDO.setItCodes("itCodes");
        msgRemindDO.setModule("module");
        msgRemindDO.setMsgTopic("msgTopic");
        msgRemindDO.setSysCatalog("sysCatalog");
        msgRemindDO.setMsgTitle("msgTitle");
        msgRemindDO.setMsgInfo("msgInfo");
        msgRemindDO.setMsgType("msgType");
        msgRemindDO.setHasRead(0);
        msgRemindDO.setIsUpdate(0);
        final List<MsgRemindDO> msgRemindDOS = Arrays.asList(msgRemindDO);
        when(mockMsgRemindManager.selectMsgRemindList(new MsgRemindDTO())).thenReturn(msgRemindDOS);

        // Run the test
        final List<MsgRemindDTO> result = msgRemindServiceImplUnderTest.selectMsgRemindList(msgRemind);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectMsgRemindList_IMsgRemindManagerReturnsNoItems() {
        // Setup
        final MsgRemindDTO msgRemind = new MsgRemindDTO();
        msgRemind.setId(0L);
        msgRemind.setMsgUuid("msgUuid");
        msgRemind.setItCodes("itCodes");
        msgRemind.setModule("module");
        msgRemind.setMsgTitle("msgTitle");
        msgRemind.setMsgInfo("msgInfo");
        msgRemind.setMsgTopic("msgTopic");
        msgRemind.setMsgType("msgType");
        msgRemind.setSysCatalog("sysCatalog");
        msgRemind.setHasRead(0);

        when(mockMsgRemindManager.selectMsgRemindList(new MsgRemindDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<MsgRemindDTO> result = msgRemindServiceImplUnderTest.selectMsgRemindList(msgRemind);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertMsgRemind() {
        // Setup
        final MsgRemindDTO msgRemind = new MsgRemindDTO();
        msgRemind.setId(0L);
        msgRemind.setMsgUuid("msgUuid");
        msgRemind.setItCodes("itCodes");
        msgRemind.setModule("module");
        msgRemind.setMsgTitle("msgTitle");
        msgRemind.setMsgInfo("msgInfo");
        msgRemind.setMsgTopic("msgTopic");
        msgRemind.setMsgType("msgType");
        msgRemind.setSysCatalog("sysCatalog");
        msgRemind.setHasRead(0);

        when(mockRedisUtils.setNx("key", "value", 0L)).thenReturn(false);

        // Run the test

        assertThrows(OtmpException.class, () -> msgRemindServiceImplUnderTest.insertMsgRemind(msgRemind));


    }

    @Test
    public void testListen() {
        // Setup
        final ConsumerRecord<?, ?> consumerRecord = new ConsumerRecord<>("topic", 0, 0L, null, null);
        when(mockMsgRemindManager.updateMsgRemind(new MsgRemindDO())).thenReturn(0);
        when(mockMsgRemindManager.insertMsgRemind(new MsgRemindDO())).thenReturn(0);

        // Run the test

        assertThrows(NullPointerException.class, () -> msgRemindServiceImplUnderTest.listen(consumerRecord));

    }

    @Test
    public void testUpdateMsgRemind() {
        // Setup
        final MsgRemindDTO msgRemindDTO = new MsgRemindDTO();
        msgRemindDTO.setId(0L);
        msgRemindDTO.setMsgUuid("msgUuid");
        msgRemindDTO.setItCodes("itCodes");
        msgRemindDTO.setModule("module");
        msgRemindDTO.setMsgTitle("msgTitle");
        msgRemindDTO.setMsgInfo("msgInfo");
        msgRemindDTO.setMsgTopic("msgTopic");
        msgRemindDTO.setMsgType("msgType");
        msgRemindDTO.setSysCatalog("sysCatalog");
        msgRemindDTO.setHasRead(0);

        // Run the test

        assertThrows(OtmpException.class, () -> msgRemindServiceImplUnderTest.updateMsgRemind(msgRemindDTO));


    }

    @Test
    public void testDeleteMsgRemindByIds() {
        // Setup
        when(mockMsgRemindManager.deleteMsgRemindByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = msgRemindServiceImplUnderTest.deleteMsgRemindByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteMsgRemindById() {
        // Setup
        when(mockMsgRemindManager.deleteMsgRemindById(0L)).thenReturn(0);

        // Run the test
        final int result = msgRemindServiceImplUnderTest.deleteMsgRemindById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testBatchUpdateMsgRemind() {
        assertThrows(OtmpException.class, () -> msgRemindServiceImplUnderTest.batchUpdateMsgRemind(null));

    }

    @Test
    public void testGetObjects() {
        assertEquals("value", MsgRemindServiceImpl.getObjects("value"));
    }
}
