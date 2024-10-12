package com.microservices.sdms.masterdata.service.impl;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseProfitCenter;
import com.microservices.otmp.masterdata.mapper.BizBaseProfitCenterMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseProfitCenterServiceImpl;
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
public class BizBaseProfitCenterServiceImplTest {

    @Mock
    private BizBaseProfitCenterMapper mockBizBaseProfitCenterMapper;
    @Mock
    private RedisUtils redisUtils;
    @InjectMocks
    private BizBaseProfitCenterServiceImpl bizBaseProfitCenterServiceImplUnderTest;

    @Test
    public void testSelectBizBaseProfitCenterById() {
        // Setup
        // Configure BizBaseProfitCenterMapper.selectBizBaseProfitCenterById(...).
        final BizBaseProfitCenter bizBaseProfitCenter = new BizBaseProfitCenter();
        bizBaseProfitCenter.setId(0L);
        bizBaseProfitCenter.setRegionMarketCode("regionMarketCode");
        bizBaseProfitCenter.setSalesOrgCode("salesOrgCode");
        bizBaseProfitCenter.setSalesOfficeCode("salesOfficeCode");
        bizBaseProfitCenter.setStatus("status");
        bizBaseProfitCenter.setRemark("memo");
        bizBaseProfitCenter.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenter.setGeoCode("geo");
        bizBaseProfitCenter.setTsThinkFlag("tsThinkFlag");
        bizBaseProfitCenter.setDummyGtnMtm("dummyGtnMtm");
        when(mockBizBaseProfitCenterMapper.selectBizBaseProfitCenterById(0L)).thenReturn(bizBaseProfitCenter);

        // Run the test
        final BizBaseProfitCenter result = bizBaseProfitCenterServiceImplUnderTest.selectBizBaseProfitCenterById(0L);
        assertEquals(bizBaseProfitCenter, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseProfitCenterList() {
        // Setup
        final BizBaseProfitCenter bizBaseProfitCenter = new BizBaseProfitCenter();
        bizBaseProfitCenter.setId(0L);
        bizBaseProfitCenter.setRegionMarketCode("regionMarketCode");
        bizBaseProfitCenter.setSalesOrgCode("salesOrgCode");
        bizBaseProfitCenter.setSalesOfficeCode("salesOfficeCode");
        bizBaseProfitCenter.setStatus("status");
        bizBaseProfitCenter.setRemark("memo");
        bizBaseProfitCenter.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenter.setGeoCode("geo");
        bizBaseProfitCenter.setTsThinkFlag("tsThinkFlag");
        bizBaseProfitCenter.setDummyGtnMtm("dummyGtnMtm");

        // Configure BizBaseProfitCenterMapper.selectBizBaseProfitCenterList(...).
        final BizBaseProfitCenter bizBaseProfitCenter1 = new BizBaseProfitCenter();
        bizBaseProfitCenter1.setId(0L);
        bizBaseProfitCenter1.setRegionMarketCode("regionMarketCode");
        bizBaseProfitCenter1.setSalesOrgCode("salesOrgCode");
        bizBaseProfitCenter1.setSalesOfficeCode("salesOfficeCode");
        bizBaseProfitCenter1.setStatus("status");
        bizBaseProfitCenter1.setRemark("memo");
        bizBaseProfitCenter1.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenter1.setGeoCode("geo");
        bizBaseProfitCenter1.setTsThinkFlag("tsThinkFlag");
        bizBaseProfitCenter1.setDummyGtnMtm("dummyGtnMtm");
        final List<BizBaseProfitCenter> bizBaseProfitCenters = Arrays.asList(bizBaseProfitCenter1);
        when(mockBizBaseProfitCenterMapper.selectBizBaseProfitCenterList(any(BizBaseProfitCenter.class))).thenReturn(bizBaseProfitCenters);

        // Run the test
        final List<BizBaseProfitCenter> result = bizBaseProfitCenterServiceImplUnderTest.selectBizBaseProfitCenterList(bizBaseProfitCenter);
        assertEquals(bizBaseProfitCenters, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseProfitCenterList_BizBaseProfitCenterMapperReturnsNoItems() {
        // Setup
        final BizBaseProfitCenter bizBaseProfitCenter = new BizBaseProfitCenter();
        bizBaseProfitCenter.setId(0L);
        bizBaseProfitCenter.setRegionMarketCode("regionMarketCode");
        bizBaseProfitCenter.setSalesOrgCode("salesOrgCode");
        bizBaseProfitCenter.setSalesOfficeCode("salesOfficeCode");
        bizBaseProfitCenter.setStatus("status");
        bizBaseProfitCenter.setRemark("memo");
        bizBaseProfitCenter.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenter.setGeoCode("geo");
        bizBaseProfitCenter.setTsThinkFlag("tsThinkFlag");
        bizBaseProfitCenter.setDummyGtnMtm("dummyGtnMtm");

        when(mockBizBaseProfitCenterMapper.selectBizBaseProfitCenterList(any(BizBaseProfitCenter.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseProfitCenter> result = bizBaseProfitCenterServiceImplUnderTest.selectBizBaseProfitCenterList(bizBaseProfitCenter);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseProfitCenter() {
        // Setup
        final BizBaseProfitCenter bizBaseProfitCenter = new BizBaseProfitCenter();
        bizBaseProfitCenter.setId(0L);
        bizBaseProfitCenter.setRegionMarketCode("regionMarketCode");
        bizBaseProfitCenter.setSalesOrgCode("salesOrgCode");
        bizBaseProfitCenter.setSalesOfficeCode("salesOfficeCode");
        bizBaseProfitCenter.setStatus("status");
        bizBaseProfitCenter.setRemark("memo");
        bizBaseProfitCenter.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenter.setGeoCode("geo");
        bizBaseProfitCenter.setTsThinkFlag("tsThinkFlag");
        bizBaseProfitCenter.setDummyGtnMtm("dummyGtnMtm");

        when(mockBizBaseProfitCenterMapper.insertBizBaseProfitCenter(any(BizBaseProfitCenter.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseProfitCenterServiceImplUnderTest.insertBizBaseProfitCenter(bizBaseProfitCenter);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseProfitCenter() {
        // Setup
        final BizBaseProfitCenter bizBaseProfitCenter = new BizBaseProfitCenter();
        bizBaseProfitCenter.setId(0L);
        bizBaseProfitCenter.setRegionMarketCode("regionMarketCode");
        bizBaseProfitCenter.setSalesOrgCode("salesOrgCode");
        bizBaseProfitCenter.setSalesOfficeCode("salesOfficeCode");
        bizBaseProfitCenter.setStatus("status");
        bizBaseProfitCenter.setRemark("memo");
        bizBaseProfitCenter.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenter.setGeoCode("geo");
        bizBaseProfitCenter.setTsThinkFlag("tsThinkFlag");
        bizBaseProfitCenter.setDummyGtnMtm("dummyGtnMtm");

        when(mockBizBaseProfitCenterMapper.updateBizBaseProfitCenter(any(BizBaseProfitCenter.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseProfitCenterServiceImplUnderTest.updateBizBaseProfitCenter(bizBaseProfitCenter);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseProfitCenterByIds() {
        // Setup
        when(mockBizBaseProfitCenterMapper.updateBizBaseProfitCenterByIds(any(Long[].class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseProfitCenterServiceImplUnderTest.deleteBizBaseProfitCenterByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseProfitCenterById() {
        // Setup
        when(mockBizBaseProfitCenterMapper.deleteBizBaseProfitCenterById(0L)).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseProfitCenterServiceImplUnderTest.deleteBizBaseProfitCenterById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() {
        // Setup
        final BizBaseProfitCenter bizBaseProfitCenter = new BizBaseProfitCenter();
        bizBaseProfitCenter.setId(0L);
        bizBaseProfitCenter.setRegionMarketCode("regionMarketCode");
        bizBaseProfitCenter.setSalesOrgCode("salesOrgCode");
        bizBaseProfitCenter.setSalesOfficeCode("salesOfficeCode");
        bizBaseProfitCenter.setStatus("status");
        bizBaseProfitCenter.setRemark("memo");
        bizBaseProfitCenter.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenter.setGeoCode("geo");
        bizBaseProfitCenter.setTsThinkFlag("tsThinkFlag");
        bizBaseProfitCenter.setDummyGtnMtm("dummyGtnMtm");
        final List<BizBaseProfitCenter> bizs = Arrays.asList(bizBaseProfitCenter);

        // Configure BizBaseProfitCenterMapper.selectBizBaseProfitCenterListCheck(...).
        final BizBaseProfitCenter bizBaseProfitCenter1 = new BizBaseProfitCenter();
        bizBaseProfitCenter1.setId(0L);
        bizBaseProfitCenter1.setRegionMarketCode("regionMarketCode");
        bizBaseProfitCenter1.setSalesOrgCode("salesOrgCode");
        bizBaseProfitCenter1.setSalesOfficeCode("salesOfficeCode");
        bizBaseProfitCenter1.setStatus("status");
        bizBaseProfitCenter1.setRemark("memo");
        bizBaseProfitCenter1.setProfitCenterCode("profitCenterCode");
        bizBaseProfitCenter1.setGeoCode("geo");
        bizBaseProfitCenter1.setTsThinkFlag("tsThinkFlag");
        bizBaseProfitCenter1.setDummyGtnMtm("dummyGtnMtm");
        final List<BizBaseProfitCenter> bizBaseProfitCenters = Arrays.asList(bizBaseProfitCenter1);
        when(mockBizBaseProfitCenterMapper.selectBizBaseProfitCenterListCheck(any(BizBaseProfitCenter.class))).thenReturn(bizBaseProfitCenters);

        when(mockBizBaseProfitCenterMapper.updateBizBaseProfitCenter(any(BizBaseProfitCenter.class))).thenReturn(0);


        // Run the test
        final String result = bizBaseProfitCenterServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockBizBaseProfitCenterMapper).selectBizBaseProfitCenterListCheck(any(BizBaseProfitCenter.class));

    }


}
