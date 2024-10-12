package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO;
import com.microservices.otmp.system.mapper.BizSdmsReportKeyMetricsMapper;
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
public class BizSdmsReportKeyMetricsManagerImplTest {

    @Mock
    private BizSdmsReportKeyMetricsMapper mockBizSdmsReportKeyMetricsMapper;

    @InjectMocks
    private BizSdmsReportKeyMetricsManagerImpl bizSdmsReportKeyMetricsManagerImplUnderTest;

    @Test
    public void testSelectBizSdmsReportKeyMetricsByKeyMetricsId() {
        // Setup
        // Configure BizSdmsReportKeyMetricsMapper.selectBizSdmsReportKeyMetricsByKeyMetricsId(...).
        final BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetricsDO = new BizSdmsReportKeyMetricsDO();
        bizSdmsReportKeyMetricsDO.setKeyMetricsId(0L);
        bizSdmsReportKeyMetricsDO.setCategoryCode("categoryCode");
        bizSdmsReportKeyMetricsDO.setBusinessUnit("businessUnit");
        bizSdmsReportKeyMetricsDO.setGeoCode("geoCode");
        bizSdmsReportKeyMetricsDO.setMetirc("metirc");
        bizSdmsReportKeyMetricsDO.setFrequency("frequency");
        bizSdmsReportKeyMetricsDO.setUnit("unit");
        bizSdmsReportKeyMetricsDO.setValue("value");
        bizSdmsReportKeyMetricsDO.setStatus("status");
        bizSdmsReportKeyMetricsDO.setPercentageValue("percentageValue");
        when(mockBizSdmsReportKeyMetricsMapper.selectBizSdmsReportKeyMetricsByKeyMetricsId(0L)).thenReturn(bizSdmsReportKeyMetricsDO);

        // Run the test
        final BizSdmsReportKeyMetricsDO result = bizSdmsReportKeyMetricsManagerImplUnderTest.selectBizSdmsReportKeyMetricsByKeyMetricsId(0L);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsReportKeyMetricsList() {
        // Setup
        final BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetrics = new BizSdmsReportKeyMetricsDO();
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

        // Configure BizSdmsReportKeyMetricsMapper.selectBizSdmsReportKeyMetricsList(...).
        final BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetricsDO = new BizSdmsReportKeyMetricsDO();
        bizSdmsReportKeyMetricsDO.setKeyMetricsId(0L);
        bizSdmsReportKeyMetricsDO.setCategoryCode("categoryCode");
        bizSdmsReportKeyMetricsDO.setBusinessUnit("businessUnit");
        bizSdmsReportKeyMetricsDO.setGeoCode("geoCode");
        bizSdmsReportKeyMetricsDO.setMetirc("metirc");
        bizSdmsReportKeyMetricsDO.setFrequency("frequency");
        bizSdmsReportKeyMetricsDO.setUnit("unit");
        bizSdmsReportKeyMetricsDO.setValue("value");
        bizSdmsReportKeyMetricsDO.setStatus("status");
        bizSdmsReportKeyMetricsDO.setPercentageValue("percentageValue");
        final List<BizSdmsReportKeyMetricsDO> bizSdmsReportKeyMetricsDOS = Arrays.asList(bizSdmsReportKeyMetricsDO);
        when(mockBizSdmsReportKeyMetricsMapper.selectBizSdmsReportKeyMetricsList(any(BizSdmsReportKeyMetricsDO.class))).thenReturn(bizSdmsReportKeyMetricsDOS);

        // Run the test
        final List<BizSdmsReportKeyMetricsDO> result = bizSdmsReportKeyMetricsManagerImplUnderTest.selectBizSdmsReportKeyMetricsList(bizSdmsReportKeyMetrics);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsReportKeyMetricsList_BizSdmsReportKeyMetricsMapperReturnsNoItems() {
        // Setup
        final BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetrics = new BizSdmsReportKeyMetricsDO();
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

        when(mockBizSdmsReportKeyMetricsMapper.selectBizSdmsReportKeyMetricsList(any(BizSdmsReportKeyMetricsDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizSdmsReportKeyMetricsDO> result = bizSdmsReportKeyMetricsManagerImplUnderTest.selectBizSdmsReportKeyMetricsList(bizSdmsReportKeyMetrics);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizSdmsReportKeyMetrics() {
        // Setup
        final BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetrics = new BizSdmsReportKeyMetricsDO();
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

        when(mockBizSdmsReportKeyMetricsMapper.insertBizSdmsReportKeyMetrics(any(BizSdmsReportKeyMetricsDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportKeyMetricsManagerImplUnderTest.insertBizSdmsReportKeyMetrics(bizSdmsReportKeyMetrics);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsReportKeyMetrics() {
        // Setup
        final BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetrics = new BizSdmsReportKeyMetricsDO();
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

        when(mockBizSdmsReportKeyMetricsMapper.updateBizSdmsReportKeyMetrics(any(BizSdmsReportKeyMetricsDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportKeyMetricsManagerImplUnderTest.updateBizSdmsReportKeyMetrics(bizSdmsReportKeyMetrics);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsReportKeyMetricsByKeyMetricsIds() {
        // Setup
        when(mockBizSdmsReportKeyMetricsMapper.deleteBizSdmsReportKeyMetricsByKeyMetricsIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportKeyMetricsManagerImplUnderTest.deleteBizSdmsReportKeyMetricsByKeyMetricsIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsReportKeyMetricsByKeyMetricsId() {
        // Setup
        when(mockBizSdmsReportKeyMetricsMapper.deleteBizSdmsReportKeyMetricsByKeyMetricsId(0L)).thenReturn(0);

        // Run the test
        final int result = bizSdmsReportKeyMetricsManagerImplUnderTest.deleteBizSdmsReportKeyMetricsByKeyMetricsId(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
