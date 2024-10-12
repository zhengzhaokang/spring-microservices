package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.MsgRemindDTO;
import com.microservices.otmp.system.domain.entity.MsgRemindDO;
import com.microservices.otmp.system.mapper.MsgRemindMapper;
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
public class MsgRemindManagerImplTest {

    @Mock
    private MsgRemindMapper mockMsgRemindMapper;

    @InjectMocks
    private MsgRemindManagerImpl msgRemindManagerImplUnderTest;

    @Test
    public void testSelectMsgRemindById() {
        // Setup
        final MsgRemindDO expectedResult = new MsgRemindDO();
        expectedResult.setId(0L);
        expectedResult.setItCodes("itCodes");
        expectedResult.setModule("module");
        expectedResult.setMsgTopic("msgTopic");
        expectedResult.setSysCatalog("sysCatalog");
        expectedResult.setMsgTitle("msgTitle");
        expectedResult.setMsgInfo("msgInfo");
        expectedResult.setMsgType("msgType");
        expectedResult.setHasRead(0);
        expectedResult.setIsUpdate(0);

        // Configure MsgRemindMapper.selectMsgRemindById(...).
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
        when(mockMsgRemindMapper.selectMsgRemindById(0L)).thenReturn(msgRemindDO);

        // Run the test
        final MsgRemindDO result = msgRemindManagerImplUnderTest.selectMsgRemindById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
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
        final List<MsgRemindDO> expectedResult = Arrays.asList(msgRemindDO);
        when(mockMsgRemindMapper.selectMsgRemindListForCount(new MsgRemindDTO())).thenReturn(0);

        // Configure MsgRemindMapper.selectMsgRemindList(...).
        final MsgRemindDO msgRemindDO1 = new MsgRemindDO();
        msgRemindDO1.setId(0L);
        msgRemindDO1.setItCodes("itCodes");
        msgRemindDO1.setModule("module");
        msgRemindDO1.setMsgTopic("msgTopic");
        msgRemindDO1.setSysCatalog("sysCatalog");
        msgRemindDO1.setMsgTitle("msgTitle");
        msgRemindDO1.setMsgInfo("msgInfo");
        msgRemindDO1.setMsgType("msgType");
        msgRemindDO1.setHasRead(0);
        msgRemindDO1.setIsUpdate(0);
        final List<MsgRemindDO> msgRemindDOS = Arrays.asList(msgRemindDO1);
        when(mockMsgRemindMapper.selectMsgRemindList(new MsgRemindDTO())).thenReturn(msgRemindDOS);

        when(mockMsgRemindMapper.updateMsgRemind(new MsgRemindDO())).thenReturn(0);

        // Run the test
        final List<MsgRemindDO> result = msgRemindManagerImplUnderTest.selectMsgRemindList(msgRemind);

        // Verify the results
        assertEquals(0, result.size());
//        verify(mockMsgRemindMapper).updateMsgRemind(new MsgRemindDO());
    }

    @Test
    public void testSelectMsgRemindList_MsgRemindMapperSelectMsgRemindListReturnsNoItems() {
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

        when(mockMsgRemindMapper.selectMsgRemindListForCount(new MsgRemindDTO())).thenReturn(0);
        when(mockMsgRemindMapper.selectMsgRemindList(new MsgRemindDTO())).thenReturn(Collections.emptyList());
        when(mockMsgRemindMapper.updateMsgRemind(new MsgRemindDO())).thenReturn(0);

        // Run the test
        final List<MsgRemindDO> result = msgRemindManagerImplUnderTest.selectMsgRemindList(msgRemind);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
      //  verify(mockMsgRemindMapper).updateMsgRemind(new MsgRemindDO());
    }

    @Test
    public void testInsertMsgRemind() {
        // Setup
        final MsgRemindDO msgRemind = new MsgRemindDO();
        msgRemind.setId(0L);
        msgRemind.setItCodes("itCodes");
        msgRemind.setModule("module");
        msgRemind.setMsgTopic("msgTopic");
        msgRemind.setSysCatalog("sysCatalog");
        msgRemind.setMsgTitle("msgTitle");
        msgRemind.setMsgInfo("msgInfo");
        msgRemind.setMsgType("msgType");
        msgRemind.setHasRead(0);
        msgRemind.setIsUpdate(0);

        when(mockMsgRemindMapper.insertMsgRemind(new MsgRemindDO())).thenReturn(0);

        // Run the test
        final int result = msgRemindManagerImplUnderTest.insertMsgRemind(msgRemind);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateMsgRemind() {
        // Setup
        final MsgRemindDO msgRemind = new MsgRemindDO();
        msgRemind.setId(0L);
        msgRemind.setItCodes("itCodes");
        msgRemind.setModule("module");
        msgRemind.setMsgTopic("msgTopic");
        msgRemind.setSysCatalog("sysCatalog");
        msgRemind.setMsgTitle("msgTitle");
        msgRemind.setMsgInfo("msgInfo");
        msgRemind.setMsgType("msgType");
        msgRemind.setHasRead(0);
        msgRemind.setIsUpdate(0);

        when(mockMsgRemindMapper.updateMsgRemind(new MsgRemindDO())).thenReturn(0);

        // Run the test
        final int result = msgRemindManagerImplUnderTest.updateMsgRemind(msgRemind);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteMsgRemindByIds() {
        // Setup
        when(mockMsgRemindMapper.deleteMsgRemindByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = msgRemindManagerImplUnderTest.deleteMsgRemindByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteMsgRemindById() {
        // Setup
        when(mockMsgRemindMapper.deleteMsgRemindById(0L)).thenReturn(0);

        // Run the test
        final int result = msgRemindManagerImplUnderTest.deleteMsgRemindById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testBatchUpdateMsgRemindRead() {
        // Setup
        when(mockMsgRemindMapper.batchUpdateMsgRemindRead(Arrays.asList(0L), 0)).thenReturn(0);

        // Run the test
        final int result = msgRemindManagerImplUnderTest.batchUpdateMsgRemindRead(Arrays.asList(0L), 0);

        // Verify the results
        assertEquals(0, result);
    }
}
