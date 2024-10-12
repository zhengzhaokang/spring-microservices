package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.masterdata.domain.dto.BizBaseProfitCenterLgapDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseProfitCenterLgapDO;
import com.microservices.otmp.masterdata.manager.IBizBaseProfitCenterLgapManager;
import com.microservices.otmp.masterdata.service.impl.BizBaseProfitCenterLgapServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseProfitCenterLgapServiceImplTest {

    @Mock
    private IBizBaseProfitCenterLgapManager mockBizBaseProfitCenterLgapManager;

    @InjectMocks
    private BizBaseProfitCenterLgapServiceImpl bizBaseProfitCenterLgapServiceImplUnderTest;

    @Test
    public void testSelectBizBaseProfitCenterLgapById() {
        // Setup
        final BizBaseProfitCenterLgapDTO expectedResult = new BizBaseProfitCenterLgapDTO();
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setId(0L);
        expectedResult.setProfitCenterCode("profitCenterCode");
        expectedResult.setProfitCenterName("profitCenterName");
        expectedResult.setStatus("Y");

        // Configure IBizBaseProfitCenterLgapManager.selectBizBaseProfitCenterLgapById(...).
        final BizBaseProfitCenterLgapDO bizBaseProfitCenterLgapDO = new BizBaseProfitCenterLgapDO();
        bizBaseProfitCenterLgapDO.setId(0L);
        bizBaseProfitCenterLgapDO.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenterLgapDO.setProfitCenterName("profitCenterName");
        bizBaseProfitCenterLgapDO.setStatus("Y");
        when(mockBizBaseProfitCenterLgapManager.selectBizBaseProfitCenterLgapById(0L))
                .thenReturn(bizBaseProfitCenterLgapDO);

        // Run the test
        final BizBaseProfitCenterLgapDTO result = bizBaseProfitCenterLgapServiceImplUnderTest.selectBizBaseProfitCenterLgapById(
                0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseProfitCenterLgapList() {
        // Setup
        final BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap = new BizBaseProfitCenterLgapDTO();
        bizBaseProfitCenterLgap.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProfitCenterLgap.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProfitCenterLgap.setId(0L);
        bizBaseProfitCenterLgap.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenterLgap.setProfitCenterName("profitCenterName");
        bizBaseProfitCenterLgap.setStatus("Y");

        final BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgapDTO = new BizBaseProfitCenterLgapDTO();
        bizBaseProfitCenterLgapDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProfitCenterLgapDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProfitCenterLgapDTO.setId(0L);
        bizBaseProfitCenterLgapDTO.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenterLgapDTO.setProfitCenterName("profitCenterName");
        bizBaseProfitCenterLgapDTO.setStatus("Y");
        final List<BizBaseProfitCenterLgapDTO> expectedResult = Arrays.asList(bizBaseProfitCenterLgapDTO);

        // Configure IBizBaseProfitCenterLgapManager.selectBizBaseProfitCenterLgapList(...).
        final BizBaseProfitCenterLgapDO bizBaseProfitCenterLgapDO = new BizBaseProfitCenterLgapDO();
        bizBaseProfitCenterLgapDO.setId(0L);
        bizBaseProfitCenterLgapDO.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenterLgapDO.setProfitCenterName("profitCenterName");
        bizBaseProfitCenterLgapDO.setStatus("Y");
        final List<BizBaseProfitCenterLgapDO> bizBaseProfitCenterLgapDOS = Arrays.asList(bizBaseProfitCenterLgapDO);
        when(mockBizBaseProfitCenterLgapManager.selectBizBaseProfitCenterLgapList(
                any(BizBaseProfitCenterLgapDTO.class))).thenReturn(bizBaseProfitCenterLgapDOS);

        // Run the test
        final List<BizBaseProfitCenterLgapDTO> result = bizBaseProfitCenterLgapServiceImplUnderTest.selectBizBaseProfitCenterLgapList(
                bizBaseProfitCenterLgap);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseProfitCenterLgapList_IBizBaseProfitCenterLgapManagerReturnsNoItems() {
        // Setup
        final BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap = new BizBaseProfitCenterLgapDTO();
        bizBaseProfitCenterLgap.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProfitCenterLgap.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProfitCenterLgap.setId(0L);
        bizBaseProfitCenterLgap.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenterLgap.setProfitCenterName("profitCenterName");
        bizBaseProfitCenterLgap.setStatus("Y");

        when(mockBizBaseProfitCenterLgapManager.selectBizBaseProfitCenterLgapList(
                any(BizBaseProfitCenterLgapDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseProfitCenterLgapDTO> result = bizBaseProfitCenterLgapServiceImplUnderTest.selectBizBaseProfitCenterLgapList(
                bizBaseProfitCenterLgap);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseProfitCenterLgap() {
        // Setup
        final BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap = new BizBaseProfitCenterLgapDTO();
        bizBaseProfitCenterLgap.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProfitCenterLgap.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProfitCenterLgap.setId(0L);
        bizBaseProfitCenterLgap.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenterLgap.setProfitCenterName("profitCenterName");
        bizBaseProfitCenterLgap.setStatus("Y");

        when(mockBizBaseProfitCenterLgapManager.insertBizBaseProfitCenterLgap(
                any(BizBaseProfitCenterLgapDO.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseProfitCenterLgapServiceImplUnderTest.insertBizBaseProfitCenterLgap(
                bizBaseProfitCenterLgap);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseProfitCenterLgap() {
        // Setup
        final BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap = new BizBaseProfitCenterLgapDTO();
        bizBaseProfitCenterLgap.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProfitCenterLgap.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProfitCenterLgap.setId(0L);
        bizBaseProfitCenterLgap.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenterLgap.setProfitCenterName("profitCenterName");
        bizBaseProfitCenterLgap.setStatus("Y");

        when(mockBizBaseProfitCenterLgapManager.updateBizBaseProfitCenterLgap(
                any(BizBaseProfitCenterLgapDO.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseProfitCenterLgapServiceImplUnderTest.updateBizBaseProfitCenterLgap(
                bizBaseProfitCenterLgap);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseProfitCenterLgapByIds() {
        // Setup
        when(mockBizBaseProfitCenterLgapManager.deleteBizBaseProfitCenterLgapByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseProfitCenterLgapServiceImplUnderTest.deleteBizBaseProfitCenterLgapByIds(
                new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseProfitCenterLgapById() {
        // Setup
        when(mockBizBaseProfitCenterLgapManager.deleteBizBaseProfitCenterLgapById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseProfitCenterLgapServiceImplUnderTest.deleteBizBaseProfitCenterLgapById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckProfitCenterCode() {
        // Setup
        // Configure IBizBaseProfitCenterLgapManager.selectBizBaseProfitCenterLgapList(...).
        final BizBaseProfitCenterLgapDO bizBaseProfitCenterLgapDO = new BizBaseProfitCenterLgapDO();
        bizBaseProfitCenterLgapDO.setId(0L);
        bizBaseProfitCenterLgapDO.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenterLgapDO.setProfitCenterName("profitCenterName");
        bizBaseProfitCenterLgapDO.setStatus("status");
        final List<BizBaseProfitCenterLgapDO> bizBaseProfitCenterLgapDOS = Arrays.asList(bizBaseProfitCenterLgapDO);
        when(mockBizBaseProfitCenterLgapManager.selectBizBaseProfitCenterLgapList(
                any(BizBaseProfitCenterLgapDTO.class))).thenReturn(bizBaseProfitCenterLgapDOS);

        // Run the test
        final ResultDTO<Object> result = bizBaseProfitCenterLgapServiceImplUnderTest.checkProfitCenterCode(
                "profitCenterCode");

        // Verify the results
    }

    @Test
    public void testCheckProfitCenterCode_IBizBaseProfitCenterLgapManagerReturnsNoItems() {
        // Setup
        when(mockBizBaseProfitCenterLgapManager.selectBizBaseProfitCenterLgapList(
                any(BizBaseProfitCenterLgapDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        assertThrows(OtmpException.class, () -> bizBaseProfitCenterLgapServiceImplUnderTest.checkProfitCenterCode(
                "profitCenterCode"));

        // Verify the results
    }
}
