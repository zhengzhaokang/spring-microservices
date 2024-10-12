package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseRegionMarket;
import com.microservices.otmp.masterdata.mapper.BizBaseRegionMarketMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseRegionMarketServiceImpl;
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
public class BizBaseRegionMarketServiceImplTest {

    @Mock
    private BizBaseRegionMarketMapper mockBizBaseRegionMarketMapper;
    @Mock
    private RedisUtils redisUtils;
    @InjectMocks
    private BizBaseRegionMarketServiceImpl bizBaseRegionMarketServiceImplUnderTest;

    @Test
    public void testSelectBizBaseRegionMarketById() {
        // Setup
        // Configure BizBaseRegionMarketMapper.selectBizBaseRegionMarketById(...).
        final BizBaseRegionMarket bizBaseRegionMarket = new BizBaseRegionMarket();
        bizBaseRegionMarket.setId(0L);
        bizBaseRegionMarket.setGeoCode("geoCode");
        bizBaseRegionMarket.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarket.setRegionMarketName("regionMarketName");
        bizBaseRegionMarket.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarket.setStatus("status");
        bizBaseRegionMarket.setRemark("memo");
        when(mockBizBaseRegionMarketMapper.selectBizBaseRegionMarketById(0L)).thenReturn(bizBaseRegionMarket);

        // Run the test
        final BizBaseRegionMarket result = bizBaseRegionMarketServiceImplUnderTest.selectBizBaseRegionMarketById(0L);
        assertEquals(bizBaseRegionMarket, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseRegionMarketList() {
        // Setup
        final BizBaseRegionMarket bizBaseRegionMarket = new BizBaseRegionMarket();
        bizBaseRegionMarket.setId(0L);
        bizBaseRegionMarket.setGeoCode("geoCode");
        bizBaseRegionMarket.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarket.setRegionMarketName("regionMarketName");
        bizBaseRegionMarket.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarket.setStatus("status");
        bizBaseRegionMarket.setRemark("memo");

        // Configure BizBaseRegionMarketMapper.selectBizBaseRegionMarketList(...).
        final BizBaseRegionMarket bizBaseRegionMarket1 = new BizBaseRegionMarket();
        bizBaseRegionMarket1.setId(0L);
        bizBaseRegionMarket1.setGeoCode("geoCode");
        bizBaseRegionMarket1.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarket1.setRegionMarketName("regionMarketName");
        bizBaseRegionMarket1.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarket1.setStatus("status");
        bizBaseRegionMarket1.setRemark("memo");
        final List<BizBaseRegionMarket> bizBaseRegionMarkets = Arrays.asList(bizBaseRegionMarket1);
        when(mockBizBaseRegionMarketMapper.selectBizBaseRegionMarketList(any(BizBaseRegionMarket.class))).thenReturn(bizBaseRegionMarkets);

        // Run the test
        final List<BizBaseRegionMarket> result = bizBaseRegionMarketServiceImplUnderTest.selectBizBaseRegionMarketList(bizBaseRegionMarket);
        assertEquals(bizBaseRegionMarkets, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseRegionMarketList_BizBaseRegionMarketMapperReturnsNoItems() {
        // Setup
        final BizBaseRegionMarket bizBaseRegionMarket = new BizBaseRegionMarket();
        bizBaseRegionMarket.setId(0L);
        bizBaseRegionMarket.setGeoCode("geoCode");
        bizBaseRegionMarket.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarket.setRegionMarketName("regionMarketName");
        bizBaseRegionMarket.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarket.setStatus("status");
        bizBaseRegionMarket.setRemark("memo");

        when(mockBizBaseRegionMarketMapper.selectBizBaseRegionMarketList(any(BizBaseRegionMarket.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseRegionMarket> result = bizBaseRegionMarketServiceImplUnderTest.selectBizBaseRegionMarketList(bizBaseRegionMarket);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseRegionMarket() {
        // Setup
        final BizBaseRegionMarket bizBaseRegionMarket = new BizBaseRegionMarket();
        bizBaseRegionMarket.setId(0L);
        bizBaseRegionMarket.setGeoCode("geoCode");
        bizBaseRegionMarket.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarket.setRegionMarketName("regionMarketName");
        bizBaseRegionMarket.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarket.setStatus("status");
        bizBaseRegionMarket.setRemark("memo");

        when(mockBizBaseRegionMarketMapper.insertBizBaseRegionMarket(any(BizBaseRegionMarket.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseRegionMarketServiceImplUnderTest.insertBizBaseRegionMarket(bizBaseRegionMarket);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseRegionMarket() {
        // Setup
        final BizBaseRegionMarket bizBaseRegionMarket = new BizBaseRegionMarket();
        bizBaseRegionMarket.setId(0L);
        bizBaseRegionMarket.setGeoCode("geoCode");
        bizBaseRegionMarket.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarket.setRegionMarketName("regionMarketName");
        bizBaseRegionMarket.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarket.setStatus("status");
        bizBaseRegionMarket.setRemark("memo");

        when(mockBizBaseRegionMarketMapper.updateBizBaseRegionMarket(any(BizBaseRegionMarket.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseRegionMarketServiceImplUnderTest.updateBizBaseRegionMarket(bizBaseRegionMarket);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseRegionMarketByIds() {
        // Setup
        when(mockBizBaseRegionMarketMapper.updateBizBaseRegionMarketByIds(any(Long[].class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseRegionMarketServiceImplUnderTest.deleteBizBaseRegionMarketByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseRegionMarketById() {
        // Setup
        when(mockBizBaseRegionMarketMapper.deleteBizBaseRegionMarketById(0L)).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseRegionMarketServiceImplUnderTest.deleteBizBaseRegionMarketById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectBizBaseRegionMarketListByGeoCode() {
        // Setup
        when(mockBizBaseRegionMarketMapper.selectBizBaseRegionMarketListByGeoCode("geoCode")).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<String> result = bizBaseRegionMarketServiceImplUnderTest.selectBizBaseRegionMarketListByGeoCode("geoCode");

        // Verify the results
        assertEquals(Arrays.asList("value"), result);
    }

    @Test
    public void testSelectBizBaseRegionMarketListByGeoCode_BizBaseRegionMarketMapperReturnsNoItems() {
        // Setup
        when(mockBizBaseRegionMarketMapper.selectBizBaseRegionMarketListByGeoCode("geoCode")).thenReturn(Collections.emptyList());

        // Run the test
        final List<String> result = bizBaseRegionMarketServiceImplUnderTest.selectBizBaseRegionMarketListByGeoCode("geoCode");

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testImportExcel() {
        // Setup
        final BizBaseRegionMarket bizBaseRegionMarket = new BizBaseRegionMarket();
        bizBaseRegionMarket.setId(0L);
        bizBaseRegionMarket.setGeoCode("geoCode");
        bizBaseRegionMarket.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarket.setRegionMarketName("regionMarketName");
        bizBaseRegionMarket.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarket.setStatus("status");
        bizBaseRegionMarket.setRemark("memo");
        final List<BizBaseRegionMarket> bizs = Arrays.asList(bizBaseRegionMarket);

        // Configure BizBaseRegionMarketMapper.selectBizBaseRegionMarketListCheck(...).
        final BizBaseRegionMarket bizBaseRegionMarket1 = new BizBaseRegionMarket();
        bizBaseRegionMarket1.setId(0L);
        bizBaseRegionMarket1.setGeoCode("geoCode");
        bizBaseRegionMarket1.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarket1.setRegionMarketName("regionMarketName");
        bizBaseRegionMarket1.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarket1.setStatus("status");
        bizBaseRegionMarket1.setRemark("memo");
        final List<BizBaseRegionMarket> bizBaseRegionMarkets = Arrays.asList(bizBaseRegionMarket1);
        when(mockBizBaseRegionMarketMapper.selectBizBaseRegionMarketListCheck(any(BizBaseRegionMarket.class))).thenReturn(bizBaseRegionMarkets);

        when(mockBizBaseRegionMarketMapper.updateBizBaseRegionMarket(any(BizBaseRegionMarket.class))).thenReturn(0);


        // Run the test
        final String result = bizBaseRegionMarketServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockBizBaseRegionMarketMapper).selectBizBaseRegionMarketListCheck(any(BizBaseRegionMarket.class));

    }


}
