package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.BizBaseSalesOrgPaymentMode;
import com.microservices.otmp.masterdata.mapper.BizBaseSalesOrgPaymentModeMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseSalesOrgPaymentModeServiceImpl;
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

@RunWith(MockitoJUnitRunner.class)
public class BizBaseSalesOrgPaymentModeServiceImplTest {

    @Mock
    private BizBaseSalesOrgPaymentModeMapper mockBizBaseSalesOrgPaymentModeMapper;

    @InjectMocks
    private BizBaseSalesOrgPaymentModeServiceImpl bizBaseSalesOrgPaymentModeServiceImplUnderTest;

    @Test
    public void testSelectBizBaseSalesOrgPaymentModeById() {
        // Setup
        // Configure BizBaseSalesOrgPaymentModeMapper.selectBizBaseSalesOrgPaymentModeById(...).
        final BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode = new BizBaseSalesOrgPaymentMode();
        bizBaseSalesOrgPaymentMode.setId(0L);
        bizBaseSalesOrgPaymentMode.setGeoCode("geoCode");
        bizBaseSalesOrgPaymentMode.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgPaymentMode.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgPaymentMode.setPaymentModeCode("paymentModeCode");
        bizBaseSalesOrgPaymentMode.setIntegrateMode("integrateMode");
        bizBaseSalesOrgPaymentMode.setStatus("status");
        bizBaseSalesOrgPaymentMode.setRemark("memo");
        when(mockBizBaseSalesOrgPaymentModeMapper.selectBizBaseSalesOrgPaymentModeById(0L)).thenReturn(bizBaseSalesOrgPaymentMode);

        // Run the test
        final BizBaseSalesOrgPaymentMode result = bizBaseSalesOrgPaymentModeServiceImplUnderTest.selectBizBaseSalesOrgPaymentModeById(0L);
        assertEquals(bizBaseSalesOrgPaymentMode, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseSalesOrgPaymentModeList() {
        // Setup
        final BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode = new BizBaseSalesOrgPaymentMode();
        bizBaseSalesOrgPaymentMode.setId(0L);
        bizBaseSalesOrgPaymentMode.setGeoCode("geoCode");
        bizBaseSalesOrgPaymentMode.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgPaymentMode.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgPaymentMode.setPaymentModeCode("paymentModeCode");
        bizBaseSalesOrgPaymentMode.setIntegrateMode("integrateMode");
        bizBaseSalesOrgPaymentMode.setStatus("status");
        bizBaseSalesOrgPaymentMode.setRemark("memo");

        // Configure BizBaseSalesOrgPaymentModeMapper.selectBizBaseSalesOrgPaymentModeList(...).
        final BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode1 = new BizBaseSalesOrgPaymentMode();
        bizBaseSalesOrgPaymentMode1.setId(0L);
        bizBaseSalesOrgPaymentMode1.setGeoCode("geoCode");
        bizBaseSalesOrgPaymentMode1.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgPaymentMode1.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgPaymentMode1.setPaymentModeCode("paymentModeCode");
        bizBaseSalesOrgPaymentMode1.setIntegrateMode("integrateMode");
        bizBaseSalesOrgPaymentMode1.setStatus("status");
        bizBaseSalesOrgPaymentMode1.setRemark("memo");
        final List<BizBaseSalesOrgPaymentMode> bizBaseSalesOrgPaymentModes = Arrays.asList(bizBaseSalesOrgPaymentMode1);
        when(mockBizBaseSalesOrgPaymentModeMapper.selectBizBaseSalesOrgPaymentModeList(any(BizBaseSalesOrgPaymentMode.class))).thenReturn(bizBaseSalesOrgPaymentModes);

        // Run the test
        final List<BizBaseSalesOrgPaymentMode> result = bizBaseSalesOrgPaymentModeServiceImplUnderTest.selectBizBaseSalesOrgPaymentModeList(bizBaseSalesOrgPaymentMode);
        assertEquals(bizBaseSalesOrgPaymentModes, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseSalesOrgPaymentModeList_BizBaseSalesOrgPaymentModeMapperReturnsNoItems() {
        // Setup
        final BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode = new BizBaseSalesOrgPaymentMode();
        bizBaseSalesOrgPaymentMode.setId(0L);
        bizBaseSalesOrgPaymentMode.setGeoCode("geoCode");
        bizBaseSalesOrgPaymentMode.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgPaymentMode.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgPaymentMode.setPaymentModeCode("paymentModeCode");
        bizBaseSalesOrgPaymentMode.setIntegrateMode("integrateMode");
        bizBaseSalesOrgPaymentMode.setStatus("status");
        bizBaseSalesOrgPaymentMode.setRemark("memo");

        when(mockBizBaseSalesOrgPaymentModeMapper.selectBizBaseSalesOrgPaymentModeList(any(BizBaseSalesOrgPaymentMode.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseSalesOrgPaymentMode> result = bizBaseSalesOrgPaymentModeServiceImplUnderTest.selectBizBaseSalesOrgPaymentModeList(bizBaseSalesOrgPaymentMode);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseSalesOrgPaymentMode() {
        // Setup
        final BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode = new BizBaseSalesOrgPaymentMode();
        bizBaseSalesOrgPaymentMode.setId(0L);
        bizBaseSalesOrgPaymentMode.setGeoCode("geoCode");
        bizBaseSalesOrgPaymentMode.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgPaymentMode.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgPaymentMode.setPaymentModeCode("paymentModeCode");
        bizBaseSalesOrgPaymentMode.setIntegrateMode("integrateMode");
        bizBaseSalesOrgPaymentMode.setStatus("status");
        bizBaseSalesOrgPaymentMode.setRemark("memo");

        when(mockBizBaseSalesOrgPaymentModeMapper.insertBizBaseSalesOrgPaymentMode(any(BizBaseSalesOrgPaymentMode.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseSalesOrgPaymentModeServiceImplUnderTest.insertBizBaseSalesOrgPaymentMode(bizBaseSalesOrgPaymentMode);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseSalesOrgPaymentMode() {
        // Setup
        final BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode = new BizBaseSalesOrgPaymentMode();
        bizBaseSalesOrgPaymentMode.setId(0L);
        bizBaseSalesOrgPaymentMode.setGeoCode("geoCode");
        bizBaseSalesOrgPaymentMode.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgPaymentMode.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgPaymentMode.setPaymentModeCode("paymentModeCode");
        bizBaseSalesOrgPaymentMode.setIntegrateMode("integrateMode");
        bizBaseSalesOrgPaymentMode.setStatus("status");
        bizBaseSalesOrgPaymentMode.setRemark("memo");

        when(mockBizBaseSalesOrgPaymentModeMapper.updateBizBaseSalesOrgPaymentMode(any(BizBaseSalesOrgPaymentMode.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseSalesOrgPaymentModeServiceImplUnderTest.updateBizBaseSalesOrgPaymentMode(bizBaseSalesOrgPaymentMode);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSalesOrgPaymentModeByIds() {
        // Setup
        when(mockBizBaseSalesOrgPaymentModeMapper.updateBizBaseSalesOrgPaymentModeByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseSalesOrgPaymentModeServiceImplUnderTest.deleteBizBaseSalesOrgPaymentModeByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSalesOrgPaymentModeById() {
        // Setup
        when(mockBizBaseSalesOrgPaymentModeMapper.deleteBizBaseSalesOrgPaymentModeById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseSalesOrgPaymentModeServiceImplUnderTest.deleteBizBaseSalesOrgPaymentModeById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() {
        // Setup
        final BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode = new BizBaseSalesOrgPaymentMode();
        bizBaseSalesOrgPaymentMode.setId(0L);
        bizBaseSalesOrgPaymentMode.setGeoCode("geoCode");
        bizBaseSalesOrgPaymentMode.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgPaymentMode.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgPaymentMode.setPaymentModeCode("paymentModeCode");
        bizBaseSalesOrgPaymentMode.setIntegrateMode("integrateMode");
        bizBaseSalesOrgPaymentMode.setStatus("status");
        bizBaseSalesOrgPaymentMode.setRemark("memo");
        final List<BizBaseSalesOrgPaymentMode> bizs = Arrays.asList(bizBaseSalesOrgPaymentMode);

        // Configure BizBaseSalesOrgPaymentModeMapper.selectBizBaseSalesOrgPaymentModeListCheck(...).
        final BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode1 = new BizBaseSalesOrgPaymentMode();
        bizBaseSalesOrgPaymentMode1.setId(0L);
        bizBaseSalesOrgPaymentMode1.setGeoCode("geoCode");
        bizBaseSalesOrgPaymentMode1.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgPaymentMode1.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgPaymentMode1.setPaymentModeCode("paymentModeCode");
        bizBaseSalesOrgPaymentMode1.setIntegrateMode("integrateMode");
        bizBaseSalesOrgPaymentMode1.setStatus("status");
        bizBaseSalesOrgPaymentMode1.setRemark("memo");
        final List<BizBaseSalesOrgPaymentMode> bizBaseSalesOrgPaymentModes = Arrays.asList(bizBaseSalesOrgPaymentMode1);
        when(mockBizBaseSalesOrgPaymentModeMapper.selectBizBaseSalesOrgPaymentModeListCheck(any(BizBaseSalesOrgPaymentMode.class))).thenReturn(bizBaseSalesOrgPaymentModes);

        when(mockBizBaseSalesOrgPaymentModeMapper.updateBizBaseSalesOrgPaymentMode(any(BizBaseSalesOrgPaymentMode.class))).thenReturn(0);
        when(mockBizBaseSalesOrgPaymentModeMapper.insertBizBaseSalesOrgPaymentMode(any(BizBaseSalesOrgPaymentMode.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseSalesOrgPaymentModeServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockBizBaseSalesOrgPaymentModeMapper).updateBizBaseSalesOrgPaymentMode(any(BizBaseSalesOrgPaymentMode.class));
        verify(mockBizBaseSalesOrgPaymentModeMapper).insertBizBaseSalesOrgPaymentMode(any(BizBaseSalesOrgPaymentMode.class));
    }

    @Test
    public void testImportExcel_BizBaseSalesOrgPaymentModeMapperSelectBizBaseSalesOrgPaymentModeListCheckReturnsNoItems() {
        // Setup
        final BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode = new BizBaseSalesOrgPaymentMode();
        bizBaseSalesOrgPaymentMode.setId(0L);
        bizBaseSalesOrgPaymentMode.setGeoCode("geoCode");
        bizBaseSalesOrgPaymentMode.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgPaymentMode.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgPaymentMode.setPaymentModeCode("paymentModeCode");
        bizBaseSalesOrgPaymentMode.setIntegrateMode("integrateMode");
        bizBaseSalesOrgPaymentMode.setStatus("status");
        bizBaseSalesOrgPaymentMode.setRemark("memo");
        final List<BizBaseSalesOrgPaymentMode> bizs = Arrays.asList(bizBaseSalesOrgPaymentMode);
        when(mockBizBaseSalesOrgPaymentModeMapper.selectBizBaseSalesOrgPaymentModeListCheck(any(BizBaseSalesOrgPaymentMode.class))).thenReturn(Collections.emptyList());

        when(mockBizBaseSalesOrgPaymentModeMapper.insertBizBaseSalesOrgPaymentMode(any(BizBaseSalesOrgPaymentMode.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseSalesOrgPaymentModeServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockBizBaseSalesOrgPaymentModeMapper).selectBizBaseSalesOrgPaymentModeListCheck(any(BizBaseSalesOrgPaymentMode.class));

    }
}
