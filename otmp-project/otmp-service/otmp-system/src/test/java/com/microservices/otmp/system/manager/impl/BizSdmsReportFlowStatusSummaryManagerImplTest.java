package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO;
import com.microservices.otmp.system.mapper.BizSdmsReportFlowStatusSummaryMapper;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizSdmsReportFlowStatusSummaryManagerImplTest {

    @Mock
    private BizSdmsReportFlowStatusSummaryMapper mockBizSdmsReportFlowStatusSummaryMapper;

    @InjectMocks
    private BizSdmsReportFlowStatusSummaryManagerImpl bizSdmsReportFlowStatusSummaryManagerImplUnderTest;

    @Test
    public void testSelectBizSdmsReportFlowStatusSummaryByStatusSummaryId() {
        // Setup
        // Configure BizSdmsReportFlowStatusSummaryMapper.selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(...).
        final BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummaryDO = new BizSdmsReportFlowStatusSummaryDO();
        bizSdmsReportFlowStatusSummaryDO.setStatusSummaryId(0L);
        bizSdmsReportFlowStatusSummaryDO.setCategoryCode("categoryCode");
        bizSdmsReportFlowStatusSummaryDO.setUserCode("userCode");
        bizSdmsReportFlowStatusSummaryDO.setStatusType("statusType");
        bizSdmsReportFlowStatusSummaryDO.setValue("value");
        bizSdmsReportFlowStatusSummaryDO.setStatus("status");
        when(mockBizSdmsReportFlowStatusSummaryMapper.selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(0L)).thenReturn(bizSdmsReportFlowStatusSummaryDO);

        // Run the test
        final BizSdmsReportFlowStatusSummaryDO result = bizSdmsReportFlowStatusSummaryManagerImplUnderTest.selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(0L);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsReportFlowStatusSummaryList() {
        // Setup
        final BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummary = new BizSdmsReportFlowStatusSummaryDO();
        bizSdmsReportFlowStatusSummary.setStatusSummaryId(0L);
        bizSdmsReportFlowStatusSummary.setCategoryCode("categoryCode");
        bizSdmsReportFlowStatusSummary.setUserCode("userCode");
        bizSdmsReportFlowStatusSummary.setStatusType("statusType");
        bizSdmsReportFlowStatusSummary.setValue("value");
        bizSdmsReportFlowStatusSummary.setStatus("status");

        // Configure BizSdmsReportFlowStatusSummaryMapper.selectBizSdmsReportFlowStatusSummaryList(...).
        final BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummaryDO = new BizSdmsReportFlowStatusSummaryDO();
        bizSdmsReportFlowStatusSummaryDO.setStatusSummaryId(0L);
        bizSdmsReportFlowStatusSummaryDO.setCategoryCode("categoryCode");
        bizSdmsReportFlowStatusSummaryDO.setUserCode("userCode");
        bizSdmsReportFlowStatusSummaryDO.setStatusType("statusType");
        bizSdmsReportFlowStatusSummaryDO.setValue("value");
        bizSdmsReportFlowStatusSummaryDO.setStatus("status");
        final List<BizSdmsReportFlowStatusSummaryDO> bizSdmsReportFlowStatusSummaryDOS = Arrays.asList(bizSdmsReportFlowStatusSummaryDO);
        when(mockBizSdmsReportFlowStatusSummaryMapper.selectBizSdmsReportFlowStatusSummaryList(any(BizSdmsReportFlowStatusSummaryDO.class))).thenReturn(bizSdmsReportFlowStatusSummaryDOS);

        // Run the test
        final List<BizSdmsReportFlowStatusSummaryDO> result = bizSdmsReportFlowStatusSummaryManagerImplUnderTest.selectBizSdmsReportFlowStatusSummaryList(bizSdmsReportFlowStatusSummary);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsReportFlowStatusSummaryList_BizSdmsReportFlowStatusSummaryMapperReturnsNoItems() {
        // Setup
        final BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummary = new BizSdmsReportFlowStatusSummaryDO();
        bizSdmsReportFlowStatusSummary.setStatusSummaryId(0L);
        bizSdmsReportFlowStatusSummary.setCategoryCode("categoryCode");
        bizSdmsReportFlowStatusSummary.setUserCode("userCode");
        bizSdmsReportFlowStatusSummary.setStatusType("statusType");
        bizSdmsReportFlowStatusSummary.setValue("value");
        bizSdmsReportFlowStatusSummary.setStatus("status");

        when(mockBizSdmsReportFlowStatusSummaryMapper.selectBizSdmsReportFlowStatusSummaryList(any(BizSdmsReportFlowStatusSummaryDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizSdmsReportFlowStatusSummaryDO> result = bizSdmsReportFlowStatusSummaryManagerImplUnderTest.selectBizSdmsReportFlowStatusSummaryList(bizSdmsReportFlowStatusSummary);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizSdmsReportFlowStatusSummary() {
        // Setup
        final BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummary = new BizSdmsReportFlowStatusSummaryDO();
        bizSdmsReportFlowStatusSummary.setStatusSummaryId(0L);
        bizSdmsReportFlowStatusSummary.setCategoryCode("categoryCode");
        bizSdmsReportFlowStatusSummary.setUserCode("userCode");
        bizSdmsReportFlowStatusSummary.setStatusType("statusType");
        bizSdmsReportFlowStatusSummary.setValue("value");
        bizSdmsReportFlowStatusSummary.setStatus("status");

        when(mockBizSdmsReportFlowStatusSummaryMapper.insertBizSdmsReportFlowStatusSummary(any(BizSdmsReportFlowStatusSummaryDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportFlowStatusSummaryManagerImplUnderTest.insertBizSdmsReportFlowStatusSummary(bizSdmsReportFlowStatusSummary);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsReportFlowStatusSummary() {
        // Setup
        final BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummary = new BizSdmsReportFlowStatusSummaryDO();
        bizSdmsReportFlowStatusSummary.setStatusSummaryId(0L);
        bizSdmsReportFlowStatusSummary.setCategoryCode("categoryCode");
        bizSdmsReportFlowStatusSummary.setUserCode("userCode");
        bizSdmsReportFlowStatusSummary.setStatusType("statusType");
        bizSdmsReportFlowStatusSummary.setValue("value");
        bizSdmsReportFlowStatusSummary.setStatus("status");

        when(mockBizSdmsReportFlowStatusSummaryMapper.updateBizSdmsReportFlowStatusSummary(any(BizSdmsReportFlowStatusSummaryDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportFlowStatusSummaryManagerImplUnderTest.updateBizSdmsReportFlowStatusSummary(bizSdmsReportFlowStatusSummary);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds() {
        // Setup
        when(mockBizSdmsReportFlowStatusSummaryMapper.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportFlowStatusSummaryManagerImplUnderTest.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsReportFlowStatusSummaryByStatusSummaryId() {
        // Setup
        when(mockBizSdmsReportFlowStatusSummaryMapper.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryId(0L)).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportFlowStatusSummaryManagerImplUnderTest.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryId(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
