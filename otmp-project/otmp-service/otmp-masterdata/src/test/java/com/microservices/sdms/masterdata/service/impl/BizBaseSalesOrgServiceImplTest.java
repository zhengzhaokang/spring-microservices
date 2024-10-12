package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOrg;
import com.microservices.otmp.masterdata.mapper.BizBaseSalesOrgMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseSalesOrgServiceImpl;
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

@RunWith(MockitoJUnitRunner.Silent.class)
public class BizBaseSalesOrgServiceImplTest {

    @Mock
    private BizBaseSalesOrgMapper mockBizBaseSalesOrgMapper;
    @Mock
    private RedisUtils redisUtils;
    @InjectMocks
    private BizBaseSalesOrgServiceImpl bizBaseSalesOrgServiceImplUnderTest;

    @Test
    public void testSelectBizBaseSalesOrgById() {
        // Setup
        // Configure BizBaseSalesOrgMapper.selectBizBaseSalesOrgById(...).
        final BizBaseSalesOrg bizBaseSalesOrg = new BizBaseSalesOrg();
        bizBaseSalesOrg.setId(0L);
        bizBaseSalesOrg.setGeoCode("geoCode");
        bizBaseSalesOrg.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrg.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrg.setSalesOrgName("salesOrgName");
        bizBaseSalesOrg.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrg.setCompanyCode("companyCode");
        bizBaseSalesOrg.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseSalesOrg.setStatus("status");
        bizBaseSalesOrg.setRemark("memo");
        when(mockBizBaseSalesOrgMapper.selectBizBaseSalesOrgById(0L)).thenReturn(bizBaseSalesOrg);

        // Run the test
        final BizBaseSalesOrg result = bizBaseSalesOrgServiceImplUnderTest.selectBizBaseSalesOrgById(0L);
        assertEquals(bizBaseSalesOrg, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseSalesOrgList() {
        // Setup
        final BizBaseSalesOrg bizBaseSalesOrg = new BizBaseSalesOrg();
        bizBaseSalesOrg.setId(0L);
        bizBaseSalesOrg.setGeoCode("geoCode");
        bizBaseSalesOrg.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrg.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrg.setSalesOrgName("salesOrgName");
        bizBaseSalesOrg.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrg.setCompanyCode("companyCode");
        bizBaseSalesOrg.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseSalesOrg.setStatus("status");
        bizBaseSalesOrg.setRemark("memo");

        // Configure BizBaseSalesOrgMapper.selectBizBaseSalesOrgList(...).
        final BizBaseSalesOrg bizBaseSalesOrg1 = new BizBaseSalesOrg();
        bizBaseSalesOrg1.setId(0L);
        bizBaseSalesOrg1.setGeoCode("geoCode");
        bizBaseSalesOrg1.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrg1.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrg1.setSalesOrgName("salesOrgName");
        bizBaseSalesOrg1.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrg1.setCompanyCode("companyCode");
        bizBaseSalesOrg1.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseSalesOrg1.setStatus("status");
        bizBaseSalesOrg1.setRemark("memo");
        final List<BizBaseSalesOrg> bizBaseSalesOrgs = Arrays.asList(bizBaseSalesOrg1);
        when(mockBizBaseSalesOrgMapper.selectBizBaseSalesOrgList(any(BizBaseSalesOrg.class))).thenReturn(bizBaseSalesOrgs);

        // Run the test
        final List<BizBaseSalesOrg> result = bizBaseSalesOrgServiceImplUnderTest.selectBizBaseSalesOrgList(bizBaseSalesOrg);
        assertEquals(bizBaseSalesOrgs, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseSalesOrgList_BizBaseSalesOrgMapperReturnsNoItems() {
        // Setup
        final BizBaseSalesOrg bizBaseSalesOrg = new BizBaseSalesOrg();
        bizBaseSalesOrg.setId(0L);
        bizBaseSalesOrg.setGeoCode("geoCode");
        bizBaseSalesOrg.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrg.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrg.setSalesOrgName("salesOrgName");
        bizBaseSalesOrg.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrg.setCompanyCode("companyCode");
        bizBaseSalesOrg.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseSalesOrg.setStatus("status");
        bizBaseSalesOrg.setRemark("memo");

        when(mockBizBaseSalesOrgMapper.selectBizBaseSalesOrgList(any(BizBaseSalesOrg.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseSalesOrg> result = bizBaseSalesOrgServiceImplUnderTest.selectBizBaseSalesOrgList(bizBaseSalesOrg);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseSalesOrg() {
        // Setup
        final BizBaseSalesOrg bizBaseSalesOrg = new BizBaseSalesOrg();
        bizBaseSalesOrg.setId(0L);
        bizBaseSalesOrg.setGeoCode("geoCode");
        bizBaseSalesOrg.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrg.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrg.setSalesOrgName("salesOrgName");
        bizBaseSalesOrg.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrg.setCompanyCode("companyCode");
        bizBaseSalesOrg.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseSalesOrg.setStatus("status");
        bizBaseSalesOrg.setRemark("memo");

        when(mockBizBaseSalesOrgMapper.insertBizBaseSalesOrg(any(BizBaseSalesOrg.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseSalesOrgServiceImplUnderTest.insertBizBaseSalesOrg(bizBaseSalesOrg);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseSalesOrg() {
        // Setup
        final BizBaseSalesOrg bizBaseSalesOrg = new BizBaseSalesOrg();
        bizBaseSalesOrg.setId(0L);
        bizBaseSalesOrg.setGeoCode("geoCode");
        bizBaseSalesOrg.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrg.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrg.setSalesOrgName("salesOrgName");
        bizBaseSalesOrg.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrg.setCompanyCode("companyCode");
        bizBaseSalesOrg.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseSalesOrg.setStatus("status");
        bizBaseSalesOrg.setRemark("memo");

        when(mockBizBaseSalesOrgMapper.updateBizBaseSalesOrg(any(BizBaseSalesOrg.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseSalesOrgServiceImplUnderTest.updateBizBaseSalesOrg(bizBaseSalesOrg);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSalesOrgByIds() {
        // Setup
        when(mockBizBaseSalesOrgMapper.updateBizBaseSalesOrgByIds(any(Long[].class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseSalesOrgServiceImplUnderTest.deleteBizBaseSalesOrgByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSalesOrgById() {
        // Setup
        when(mockBizBaseSalesOrgMapper.deleteBizBaseSalesOrgById(0L)).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseSalesOrgServiceImplUnderTest.deleteBizBaseSalesOrgById(0L);

        // Verify the results
        assertEquals(0, result);
    }

}
