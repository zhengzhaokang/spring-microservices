package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.system.domain.BizSdmsReportFlowStatusSummary;
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
public class BizSdmsReportFlowStatusSummaryServiceImplTest {

    @Mock
    private com.microservices.otmp.system.manager.BizSdmsReportFlowStatusSummaryManager mockBizSdmsReportFlowStatusSummaryManager;

    @InjectMocks
    private BizSdmsReportFlowStatusSummaryServiceImpl bizSdmsReportFlowStatusSummaryServiceImplUnderTest;

    @Test
    public void testSelectBizSdmsReportFlowStatusSummaryByStatusSummaryId() {
        // Setup
        // Configure BizSdmsReportFlowStatusSummaryManager.selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(...).
        final com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummaryDO = new com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO();
        when(mockBizSdmsReportFlowStatusSummaryManager.selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(0L)).thenReturn(bizSdmsReportFlowStatusSummaryDO);

        // Run the test
        final BizSdmsReportFlowStatusSummary result = bizSdmsReportFlowStatusSummaryServiceImplUnderTest.selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(0L);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsReportFlowStatusSummaryList() {
        // Setup
        final BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary = new BizSdmsReportFlowStatusSummary();
        bizSdmsReportFlowStatusSummary.setStatusSummaryId(0L);
        bizSdmsReportFlowStatusSummary.setCategoryCode("categoryCode");
        bizSdmsReportFlowStatusSummary.setUserCode("userCode");
        bizSdmsReportFlowStatusSummary.setStatusType("statusType");
        bizSdmsReportFlowStatusSummary.setValue("value");
        bizSdmsReportFlowStatusSummary.setStatus("status");

        // Configure BizSdmsReportFlowStatusSummaryManager.selectBizSdmsReportFlowStatusSummaryList(...).
        final com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummaryDO = new com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO();
        final List<com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO> bizSdmsReportFlowStatusSummaryDOS = Arrays.asList(bizSdmsReportFlowStatusSummaryDO);
        when(mockBizSdmsReportFlowStatusSummaryManager.selectBizSdmsReportFlowStatusSummaryList(any(com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO.class))).thenReturn(bizSdmsReportFlowStatusSummaryDOS);

        // Run the test
        final List<BizSdmsReportFlowStatusSummary> result = bizSdmsReportFlowStatusSummaryServiceImplUnderTest.selectBizSdmsReportFlowStatusSummaryList(bizSdmsReportFlowStatusSummary);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsReportFlowStatusSummaryList_BizSdmsReportFlowStatusSummaryManagerReturnsNoItems() {
        // Setup
        final BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary = new BizSdmsReportFlowStatusSummary();
        bizSdmsReportFlowStatusSummary.setStatusSummaryId(0L);
        bizSdmsReportFlowStatusSummary.setCategoryCode("categoryCode");
        bizSdmsReportFlowStatusSummary.setUserCode("userCode");
        bizSdmsReportFlowStatusSummary.setStatusType("statusType");
        bizSdmsReportFlowStatusSummary.setValue("value");
        bizSdmsReportFlowStatusSummary.setStatus("status");

        when(mockBizSdmsReportFlowStatusSummaryManager.selectBizSdmsReportFlowStatusSummaryList(any(com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizSdmsReportFlowStatusSummary> result = bizSdmsReportFlowStatusSummaryServiceImplUnderTest.selectBizSdmsReportFlowStatusSummaryList(bizSdmsReportFlowStatusSummary);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizSdmsReportFlowStatusSummary() {
        // Setup
        final BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary = new BizSdmsReportFlowStatusSummary();
        bizSdmsReportFlowStatusSummary.setStatusSummaryId(0L);
        bizSdmsReportFlowStatusSummary.setCategoryCode("categoryCode");
        bizSdmsReportFlowStatusSummary.setUserCode("userCode");
        bizSdmsReportFlowStatusSummary.setStatusType("statusType");
        bizSdmsReportFlowStatusSummary.setValue("value");
        bizSdmsReportFlowStatusSummary.setStatus("status");

        when(mockBizSdmsReportFlowStatusSummaryManager.insertBizSdmsReportFlowStatusSummary(any(com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportFlowStatusSummaryServiceImplUnderTest.insertBizSdmsReportFlowStatusSummary(bizSdmsReportFlowStatusSummary);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsReportFlowStatusSummary() {
        // Setup
        final BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary = new BizSdmsReportFlowStatusSummary();
        bizSdmsReportFlowStatusSummary.setStatusSummaryId(0L);
        bizSdmsReportFlowStatusSummary.setCategoryCode("categoryCode");
        bizSdmsReportFlowStatusSummary.setUserCode("userCode");
        bizSdmsReportFlowStatusSummary.setStatusType("statusType");
        bizSdmsReportFlowStatusSummary.setValue("value");
        bizSdmsReportFlowStatusSummary.setStatus("status");

        when(mockBizSdmsReportFlowStatusSummaryManager.updateBizSdmsReportFlowStatusSummary(any(com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportFlowStatusSummaryServiceImplUnderTest.updateBizSdmsReportFlowStatusSummary(bizSdmsReportFlowStatusSummary);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds() {
        // Setup
        when(mockBizSdmsReportFlowStatusSummaryManager.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportFlowStatusSummaryServiceImplUnderTest.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsReportFlowStatusSummaryByStatusSummaryId() {
        // Setup
        when(mockBizSdmsReportFlowStatusSummaryManager.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryId(0L)).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportFlowStatusSummaryServiceImplUnderTest.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryId(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
