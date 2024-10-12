package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.system.domain.BizSdmsReportKeyMetrics;
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
public class BizSdmsReportKeyMetricsServiceImplTest {

    @Mock
    private com.microservices.otmp.system.manager.BizSdmsReportKeyMetricsManager mockBizSdmsReportKeyMetricsManager;

    @InjectMocks
    private BizSdmsReportKeyMetricsServiceImpl bizSdmsReportKeyMetricsServiceImplUnderTest;

    @Test
    public void testSelectBizSdmsReportKeyMetricsByKeyMetricsId() {
        // Setup
        // Configure BizSdmsReportKeyMetricsManager.selectBizSdmsReportKeyMetricsByKeyMetricsId(...).
        final com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetricsDO = new com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO();
        when(mockBizSdmsReportKeyMetricsManager.selectBizSdmsReportKeyMetricsByKeyMetricsId(0L)).thenReturn(bizSdmsReportKeyMetricsDO);

        // Run the test
        final BizSdmsReportKeyMetrics result = bizSdmsReportKeyMetricsServiceImplUnderTest.selectBizSdmsReportKeyMetricsByKeyMetricsId(0L);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsReportKeyMetricsList() {
        // Setup
        final BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics = new BizSdmsReportKeyMetrics();
        bizSdmsReportKeyMetrics.setKeyMetricsId(0L);
        bizSdmsReportKeyMetrics.setCategoryCode("categoryCode");
        bizSdmsReportKeyMetrics.setBusinessUnit("businessUnit");
        bizSdmsReportKeyMetrics.setGeoCode("geoCode");
        bizSdmsReportKeyMetrics.setMetirc("metirc");
        bizSdmsReportKeyMetrics.setFrequency("frequency");
        bizSdmsReportKeyMetrics.setUnit("unit");
        bizSdmsReportKeyMetrics.setValue("value");
        bizSdmsReportKeyMetrics.setStatus("status");
        bizSdmsReportKeyMetrics.setPercentageValue("percentageValue");

        // Configure BizSdmsReportKeyMetricsManager.selectBizSdmsReportKeyMetricsList(...).
        final com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetricsDO = new com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO();
        final List<com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO> bizSdmsReportKeyMetricsDOS = Arrays.asList(bizSdmsReportKeyMetricsDO);
        when(mockBizSdmsReportKeyMetricsManager.selectBizSdmsReportKeyMetricsList(any(com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO.class))).thenReturn(bizSdmsReportKeyMetricsDOS);

        // Run the test
        final List<BizSdmsReportKeyMetrics> result = bizSdmsReportKeyMetricsServiceImplUnderTest.selectBizSdmsReportKeyMetricsList(bizSdmsReportKeyMetrics);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsReportKeyMetricsList_BizSdmsReportKeyMetricsManagerReturnsNoItems() {
        // Setup
        final BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics = new BizSdmsReportKeyMetrics();
        bizSdmsReportKeyMetrics.setKeyMetricsId(0L);
        bizSdmsReportKeyMetrics.setCategoryCode("categoryCode");
        bizSdmsReportKeyMetrics.setBusinessUnit("businessUnit");
        bizSdmsReportKeyMetrics.setGeoCode("geoCode");
        bizSdmsReportKeyMetrics.setMetirc("metirc");
        bizSdmsReportKeyMetrics.setFrequency("frequency");
        bizSdmsReportKeyMetrics.setUnit("unit");
        bizSdmsReportKeyMetrics.setValue("value");
        bizSdmsReportKeyMetrics.setStatus("status");
        bizSdmsReportKeyMetrics.setPercentageValue("percentageValue");

        when(mockBizSdmsReportKeyMetricsManager.selectBizSdmsReportKeyMetricsList(any(com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizSdmsReportKeyMetrics> result = bizSdmsReportKeyMetricsServiceImplUnderTest.selectBizSdmsReportKeyMetricsList(bizSdmsReportKeyMetrics);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizSdmsReportKeyMetrics() {
        // Setup
        final BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics = new BizSdmsReportKeyMetrics();
        bizSdmsReportKeyMetrics.setKeyMetricsId(0L);
        bizSdmsReportKeyMetrics.setCategoryCode("categoryCode");
        bizSdmsReportKeyMetrics.setBusinessUnit("businessUnit");
        bizSdmsReportKeyMetrics.setGeoCode("geoCode");
        bizSdmsReportKeyMetrics.setMetirc("metirc");
        bizSdmsReportKeyMetrics.setFrequency("frequency");
        bizSdmsReportKeyMetrics.setUnit("unit");
        bizSdmsReportKeyMetrics.setValue("value");
        bizSdmsReportKeyMetrics.setStatus("status");
        bizSdmsReportKeyMetrics.setPercentageValue("percentageValue");

        when(mockBizSdmsReportKeyMetricsManager.insertBizSdmsReportKeyMetrics(any(com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportKeyMetricsServiceImplUnderTest.insertBizSdmsReportKeyMetrics(bizSdmsReportKeyMetrics);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsReportKeyMetrics() {
        // Setup
        final BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics = new BizSdmsReportKeyMetrics();
        bizSdmsReportKeyMetrics.setKeyMetricsId(0L);
        bizSdmsReportKeyMetrics.setCategoryCode("categoryCode");
        bizSdmsReportKeyMetrics.setBusinessUnit("businessUnit");
        bizSdmsReportKeyMetrics.setGeoCode("geoCode");
        bizSdmsReportKeyMetrics.setMetirc("metirc");
        bizSdmsReportKeyMetrics.setFrequency("frequency");
        bizSdmsReportKeyMetrics.setUnit("unit");
        bizSdmsReportKeyMetrics.setValue("value");
        bizSdmsReportKeyMetrics.setStatus("status");
        bizSdmsReportKeyMetrics.setPercentageValue("percentageValue");

        when(mockBizSdmsReportKeyMetricsManager.updateBizSdmsReportKeyMetrics(any(com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportKeyMetricsServiceImplUnderTest.updateBizSdmsReportKeyMetrics(bizSdmsReportKeyMetrics);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsReportKeyMetricsByKeyMetricsIds() {
        // Setup
        when(mockBizSdmsReportKeyMetricsManager.deleteBizSdmsReportKeyMetricsByKeyMetricsIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportKeyMetricsServiceImplUnderTest.deleteBizSdmsReportKeyMetricsByKeyMetricsIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsReportKeyMetricsByKeyMetricsId() {
        // Setup
        when(mockBizSdmsReportKeyMetricsManager.deleteBizSdmsReportKeyMetricsByKeyMetricsId(0L)).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportKeyMetricsServiceImplUnderTest.deleteBizSdmsReportKeyMetricsByKeyMetricsId(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
