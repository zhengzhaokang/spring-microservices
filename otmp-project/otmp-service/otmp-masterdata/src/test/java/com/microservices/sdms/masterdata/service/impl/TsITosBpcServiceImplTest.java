package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.dto.TsITosBpcDTO;
import com.microservices.otmp.masterdata.domain.entity.TsITosBpcDO;
import com.microservices.otmp.masterdata.manager.ITsITosBpcManager;
import com.microservices.otmp.masterdata.service.impl.TsITosBpcServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TsITosBpcServiceImplTest {

    @Mock
    private ITsITosBpcManager mockTsITosBpcManager;

    @InjectMocks
    private TsITosBpcServiceImpl tsITosBpcServiceImplUnderTest;

    @Test
    public void testSelectTsITosBpcById() {
        // Setup
        final TsITosBpcDTO expectedResult = new TsITosBpcDTO();
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setId(0L);
        expectedResult.setMemberId("memberId");
        expectedResult.setEvDescription("evDescription");
        expectedResult.setParentH1("parentH1");
        expectedResult.setStatus("status");

        // Configure ITsITosBpcManager.selectTsITosBpcById(...).
        final TsITosBpcDO tsITosBpcDO = new TsITosBpcDO();
        tsITosBpcDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpcDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpcDO.setId(0L);
        tsITosBpcDO.setMemberId("memberId");
        tsITosBpcDO.setEvDescription("evDescription");
        tsITosBpcDO.setParentH1("parentH1");
        tsITosBpcDO.setStatus("status");
        when(mockTsITosBpcManager.selectTsITosBpcById(0L)).thenReturn(tsITosBpcDO);

        // Run the test
        final TsITosBpcDTO result = tsITosBpcServiceImplUnderTest.selectTsITosBpcById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectTsITosBpcList() {
        // Setup
        final TsITosBpcDTO tsITosBpc = new TsITosBpcDTO();
        tsITosBpc.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpc.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpc.setId(0L);
        tsITosBpc.setMemberId("memberId");
        tsITosBpc.setEvDescription("evDescription");
        tsITosBpc.setParentH1("parentH1");
        tsITosBpc.setStatus("status");

        final TsITosBpcDTO tsITosBpcDTO = new TsITosBpcDTO();
        tsITosBpcDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpcDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpcDTO.setId(0L);
        tsITosBpcDTO.setMemberId("memberId");
        tsITosBpcDTO.setEvDescription("evDescription");
        tsITosBpcDTO.setParentH1("parentH1");
        tsITosBpcDTO.setStatus("status");
        final List<TsITosBpcDTO> expectedResult = Arrays.asList(tsITosBpcDTO);

        // Configure ITsITosBpcManager.selectTsITosBpcList(...).
        final TsITosBpcDO tsITosBpcDO = new TsITosBpcDO();
        tsITosBpcDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpcDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpcDO.setId(0L);
        tsITosBpcDO.setMemberId("memberId");
        tsITosBpcDO.setEvDescription("evDescription");
        tsITosBpcDO.setParentH1("parentH1");
        tsITosBpcDO.setStatus("status");
        final List<TsITosBpcDO> tsITosBpcDOS = Arrays.asList(tsITosBpcDO);
        when(mockTsITosBpcManager.selectTsITosBpcList(any(TsITosBpcDTO.class))).thenReturn(tsITosBpcDOS);

        // Run the test
        final List<TsITosBpcDTO> result = tsITosBpcServiceImplUnderTest.selectTsITosBpcList(tsITosBpc);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectTsITosBpcList_ITsITosBpcManagerReturnsNoItems() {
        // Setup
        final TsITosBpcDTO tsITosBpc = new TsITosBpcDTO();
        tsITosBpc.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpc.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpc.setId(0L);
        tsITosBpc.setMemberId("memberId");
        tsITosBpc.setEvDescription("evDescription");
        tsITosBpc.setParentH1("parentH1");
        tsITosBpc.setStatus("status");

        when(mockTsITosBpcManager.selectTsITosBpcList(any(TsITosBpcDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<TsITosBpcDTO> result = tsITosBpcServiceImplUnderTest.selectTsITosBpcList(tsITosBpc);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertTsITosBpc() {
        // Setup
        final TsITosBpcDTO tsITosBpc = new TsITosBpcDTO();
        tsITosBpc.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpc.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpc.setId(0L);
        tsITosBpc.setMemberId("memberId");
        tsITosBpc.setEvDescription("evDescription");
        tsITosBpc.setParentH1("parentH1");
        tsITosBpc.setStatus("status");

        when(mockTsITosBpcManager.insertTsITosBpc(any(TsITosBpcDO.class))).thenReturn(0);

        // Run the test
        final int result = tsITosBpcServiceImplUnderTest.insertTsITosBpc(tsITosBpc);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateTsITosBpc() {
        // Setup
        final TsITosBpcDTO tsITosBpc = new TsITosBpcDTO();
        tsITosBpc.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpc.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tsITosBpc.setId(0L);
        tsITosBpc.setMemberId("memberId");
        tsITosBpc.setEvDescription("evDescription");
        tsITosBpc.setParentH1("parentH1");
        tsITosBpc.setStatus("status");

        when(mockTsITosBpcManager.updateTsITosBpc(any(TsITosBpcDO.class))).thenReturn(0);

        // Run the test
        final int result = tsITosBpcServiceImplUnderTest.updateTsITosBpc(tsITosBpc);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteTsITosBpcByIds() {
        // Setup
        when(mockTsITosBpcManager.deleteTsITosBpcByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = tsITosBpcServiceImplUnderTest.deleteTsITosBpcByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteTsITosBpcById() {
        // Setup
        when(mockTsITosBpcManager.deleteTsITosBpcById(0L)).thenReturn(0);

        // Run the test
        final int result = tsITosBpcServiceImplUnderTest.deleteTsITosBpcById(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
