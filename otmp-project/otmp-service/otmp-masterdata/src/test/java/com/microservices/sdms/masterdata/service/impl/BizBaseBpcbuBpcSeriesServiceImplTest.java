package com.microservices.sdms.masterdata.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBaseBpcbuBpcSeriesDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBpcbuBpcSeriesDO;
import com.microservices.otmp.masterdata.manager.IBizBaseBpcbuBpcSeriesManager;
import com.microservices.otmp.masterdata.service.impl.BizBaseBpcbuBpcSeriesServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseBpcbuBpcSeriesServiceImplTest {

    @Mock
    private RedisUtils mockRedisUtils;
    @Mock
    private IBizBaseBpcbuBpcSeriesManager mockBizBaseBpcbuBpcSeriesManager;

    @InjectMocks
    private BizBaseBpcbuBpcSeriesServiceImpl bizBaseBpcbuBpcSeriesServiceImplUnderTest;

    @Test
    public void testSetBpcSeriesToRedis() {
        // Setup
        final BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO = new BizBaseBpcbuBpcSeriesDO();
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDO.setStatus("status");
        bizBaseBpcbuBpcSeriesDO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDO.setEndMonth("endMonth");
        final List<BizBaseBpcbuBpcSeriesDO> bizBaseBpcbuBpcSeriesDOS = Arrays.asList(bizBaseBpcbuBpcSeriesDO);

        // Run the test
        bizBaseBpcbuBpcSeriesServiceImplUnderTest.setBpcSeriesToRedis(bizBaseBpcbuBpcSeriesDOS);

        // Verify the results
        //verify(mockRedisUtils).set("master:bpc_series", Arrays.asList(new BizBaseBpcbuBpcSeriesDO()), 0L);
        assertEquals(1, bizBaseBpcbuBpcSeriesDOS.size());
    }

    @Test
    public void testSelectBizBaseBpcbuBpcSeriesById() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO expectedResult = new BizBaseBpcbuBpcSeriesDTO();
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setCreateBy("loginName");
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setId(0L);
        expectedResult.setBpcBuCode("bpcBuCode");
        expectedResult.setBpcSeriesCode("bpcSeriesCode");
        expectedResult.setStatus("code");
        expectedResult.setBusinessGroup("businessGroup");
        expectedResult.setBpcBuDescription("bpcBuDescription");
        expectedResult.setBpcSeriesDescription("bpcSeriesDescription");
        expectedResult.setEndMonth("endMonth");
        //expectedResult.setIds("0");
        //expectedResult.setIdsList(Arrays.asList(0L));
        expectedResult.setRemark("remark");
        //expectedResult.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure IBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesById(...).
        final BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO = new BizBaseBpcbuBpcSeriesDO();
        bizBaseBpcbuBpcSeriesDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDO.setStatus("code");
        bizBaseBpcbuBpcSeriesDO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDO.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDO.setIds("0");
        //bizBaseBpcbuBpcSeriesDO.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDO.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesById(0L)).thenReturn(bizBaseBpcbuBpcSeriesDO);

        // Run the test
        final BizBaseBpcbuBpcSeriesDTO result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.selectBizBaseBpcbuBpcSeriesById(
                0L);

        // Verify the results
        assertEquals(JSON.toJSONString(expectedResult), JSON.toJSONString(result));
    }

    @Test
    public void testSelectBizBaseBpcbuBpcSeriesList() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeries.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setCreateBy("loginName");
        bizBaseBpcbuBpcSeries.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setId(0L);
        bizBaseBpcbuBpcSeries.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeries.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeries.setStatus("code");
        bizBaseBpcbuBpcSeries.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeries.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeries.setBpcSeriesDescription("bpcSeriesDescription");
        //bizBaseBpcbuBpcSeries.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeries.setIds("0");
        bizBaseBpcbuBpcSeries.setIdsList(Arrays.asList(0L));
        //bizBaseBpcbuBpcSeries.setRemark("remark");
        bizBaseBpcbuBpcSeries.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure IBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesList(...).
        final BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO = new BizBaseBpcbuBpcSeriesDO();
        bizBaseBpcbuBpcSeriesDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDO.setStatus("code");
        bizBaseBpcbuBpcSeriesDO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDO.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDO.setIds("0");
        //bizBaseBpcbuBpcSeriesDO.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDO.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseBpcbuBpcSeriesDO> bizBaseBpcbuBpcSeriesDOS = Arrays.asList(bizBaseBpcbuBpcSeriesDO);
        when(mockBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesList(
                any(BizBaseBpcbuBpcSeriesDTO.class))).thenReturn(bizBaseBpcbuBpcSeriesDOS);

        // Run the test
        final PageInfo<BizBaseBpcbuBpcSeriesDTO> result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.selectBizBaseBpcbuBpcSeriesList(
                bizBaseBpcbuBpcSeries);

        // Verify the results
        //verify(mockRedisUtils).set("master:bpc_series", bizBaseBpcbuBpcSeriesDOS, 0L);
        assertEquals(result.getSize(), bizBaseBpcbuBpcSeriesDOS.size());
    }

    @Test
    public void testSelectBizBaseBpcbuBpcSeriesList_IBizBaseBpcbuBpcSeriesManagerReturnsNoItems() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeries.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setCreateBy("loginName");
        bizBaseBpcbuBpcSeries.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setId(0L);
        bizBaseBpcbuBpcSeries.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeries.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeries.setStatus("code");
        bizBaseBpcbuBpcSeries.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeries.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeries.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeries.setEndMonth("endMonth");
        bizBaseBpcbuBpcSeries.setIds("0");
        bizBaseBpcbuBpcSeries.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeries.setRemark("remark");
        bizBaseBpcbuBpcSeries.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesList(
                any(BizBaseBpcbuBpcSeriesDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<BizBaseBpcbuBpcSeriesDTO> result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.selectBizBaseBpcbuBpcSeriesList(
                bizBaseBpcbuBpcSeries);

        // Verify the results
        //verify(mockRedisUtils).set("master:bpc_series", Arrays.asList(new BizBaseBpcbuBpcSeriesDO()), 0L);
        assertEquals(0, result.getSize());
    }

    @Test
    public void testInsertBizBaseBpcbuBpcSeries() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeries.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setCreateBy("loginName");
        bizBaseBpcbuBpcSeries.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setId(0L);
        bizBaseBpcbuBpcSeries.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeries.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeries.setStatus("code");
        bizBaseBpcbuBpcSeries.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeries.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeries.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeries.setEndMonth("endMonth");
        bizBaseBpcbuBpcSeries.setIds("0");
        bizBaseBpcbuBpcSeries.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeries.setRemark("remark");
        bizBaseBpcbuBpcSeries.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseBpcbuBpcSeriesManager.insertBizBaseBpcbuBpcSeries(any(BizBaseBpcbuBpcSeriesDO.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.insertBizBaseBpcbuBpcSeries(bizBaseBpcbuBpcSeries);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseBpcbuBpcSeries() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeries.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setCreateBy("loginName");
        bizBaseBpcbuBpcSeries.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setId(0L);
        bizBaseBpcbuBpcSeries.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeries.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeries.setStatus("code");
        bizBaseBpcbuBpcSeries.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeries.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeries.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeries.setEndMonth("endMonth");
        bizBaseBpcbuBpcSeries.setIds("0");
        bizBaseBpcbuBpcSeries.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeries.setRemark("remark");
        bizBaseBpcbuBpcSeries.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseBpcbuBpcSeriesManager.updateBizBaseBpcbuBpcSeries(any(BizBaseBpcbuBpcSeriesDO.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.updateBizBaseBpcbuBpcSeries(bizBaseBpcbuBpcSeries);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseBpcbuBpcSeriesByIds() {
        // Setup
        when(mockBizBaseBpcbuBpcSeriesManager.deleteBizBaseBpcbuBpcSeriesByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.deleteBizBaseBpcbuBpcSeriesByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseBpcbuBpcSeriesById() {
        // Setup
        when(mockBizBaseBpcbuBpcSeriesManager.deleteBizBaseBpcbuBpcSeriesById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.deleteBizBaseBpcbuBpcSeriesById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testGetBpcList() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeries.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setCreateBy("loginName");
        bizBaseBpcbuBpcSeries.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setId(0L);
        bizBaseBpcbuBpcSeries.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeries.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeries.setStatus("code");
        bizBaseBpcbuBpcSeries.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeries.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeries.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeries.setEndMonth("endMonth");
        bizBaseBpcbuBpcSeries.setIds("0");
        bizBaseBpcbuBpcSeries.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeries.setRemark("remark");
        bizBaseBpcbuBpcSeries.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeriesDTO = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeriesDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDTO.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDTO.setId(0L);
        bizBaseBpcbuBpcSeriesDTO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDTO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDTO.setStatus("code");
        bizBaseBpcbuBpcSeriesDTO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDTO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDTO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDTO.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDTO.setIds("0");
        //bizBaseBpcbuBpcSeriesDTO.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDTO.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseBpcbuBpcSeriesDTO> expectedResult = Arrays.asList(bizBaseBpcbuBpcSeriesDTO);

        // Configure IBizBaseBpcbuBpcSeriesManager.getBpcList(...).
        final BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO = new BizBaseBpcbuBpcSeriesDO();
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDO.setStatus("code");
        bizBaseBpcbuBpcSeriesDO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDO.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDO.setIds("0");
        //bizBaseBpcbuBpcSeriesDO.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDO.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseBpcbuBpcSeriesDO> bizBaseBpcbuBpcSeriesDOS = Arrays.asList(bizBaseBpcbuBpcSeriesDO);
        when(mockBizBaseBpcbuBpcSeriesManager.getBpcList(any(BizBaseBpcbuBpcSeriesDTO.class)))
                .thenReturn(bizBaseBpcbuBpcSeriesDOS);

        // Run the test
        final List<BizBaseBpcbuBpcSeriesDTO> result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.getBpcList(
                bizBaseBpcbuBpcSeries);

        // Verify the results
        assertEquals(JSON.toJSONString(expectedResult), JSON.toJSONString(result));
    }

    @Test
    public void testGetBpcList_IBizBaseBpcbuBpcSeriesManagerReturnsNoItems() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeries.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setCreateBy("loginName");
        bizBaseBpcbuBpcSeries.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setId(0L);
        bizBaseBpcbuBpcSeries.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeries.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeries.setStatus("code");
        bizBaseBpcbuBpcSeries.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeries.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeries.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeries.setEndMonth("endMonth");
        bizBaseBpcbuBpcSeries.setIds("0");
        bizBaseBpcbuBpcSeries.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeries.setRemark("remark");
        bizBaseBpcbuBpcSeries.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseBpcbuBpcSeriesManager.getBpcList(any(BizBaseBpcbuBpcSeriesDTO.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseBpcbuBpcSeriesDTO> result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.getBpcList(
                bizBaseBpcbuBpcSeries);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetBpcSeriesList() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeries.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setCreateBy("loginName");
        bizBaseBpcbuBpcSeries.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setId(0L);
        bizBaseBpcbuBpcSeries.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeries.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeries.setStatus("code");
        bizBaseBpcbuBpcSeries.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeries.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeries.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeries.setEndMonth("endMonth");
        bizBaseBpcbuBpcSeries.setIds("0");
        bizBaseBpcbuBpcSeries.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeries.setRemark("remark");
        bizBaseBpcbuBpcSeries.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeriesDTO = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeriesDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDTO.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDTO.setId(0L);
        bizBaseBpcbuBpcSeriesDTO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDTO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDTO.setStatus("code");
        bizBaseBpcbuBpcSeriesDTO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDTO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDTO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDTO.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDTO.setIds("0");
        //bizBaseBpcbuBpcSeriesDTO.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDTO.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseBpcbuBpcSeriesDTO> expectedResult = Arrays.asList(bizBaseBpcbuBpcSeriesDTO);

        // Configure IBizBaseBpcbuBpcSeriesManager.getBpcSeriesList(...).
        final BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO = new BizBaseBpcbuBpcSeriesDO();
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDO.setStatus("code");
        bizBaseBpcbuBpcSeriesDO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDO.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDO.setIds("0");
        //bizBaseBpcbuBpcSeriesDO.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDO.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseBpcbuBpcSeriesDO> bizBaseBpcbuBpcSeriesDOS = Arrays.asList(bizBaseBpcbuBpcSeriesDO);
        when(mockBizBaseBpcbuBpcSeriesManager.getBpcSeriesList(any(BizBaseBpcbuBpcSeriesDTO.class)))
                .thenReturn(bizBaseBpcbuBpcSeriesDOS);

        // Run the test
        final List<BizBaseBpcbuBpcSeriesDTO> result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.getBpcSeriesList(
                bizBaseBpcbuBpcSeries);

        // Verify the results
        assertEquals(JSON.toJSONString(expectedResult), JSON.toJSONString(result));
    }

    @Test
    public void testGetBpcSeriesList_IBizBaseBpcbuBpcSeriesManagerReturnsNoItems() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeries.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setCreateBy("loginName");
        bizBaseBpcbuBpcSeries.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setId(0L);
        bizBaseBpcbuBpcSeries.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeries.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeries.setStatus("code");
        bizBaseBpcbuBpcSeries.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeries.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeries.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeries.setEndMonth("endMonth");
        bizBaseBpcbuBpcSeries.setIds("0");
        bizBaseBpcbuBpcSeries.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeries.setRemark("remark");
        bizBaseBpcbuBpcSeries.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseBpcbuBpcSeriesManager.getBpcSeriesList(any(BizBaseBpcbuBpcSeriesDTO.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseBpcbuBpcSeriesDTO> result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.getBpcSeriesList(
                bizBaseBpcbuBpcSeries);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectBizBaseBpcbuBpcSeriesListCheck() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeries.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setCreateBy("loginName");
        bizBaseBpcbuBpcSeries.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setId(0L);
        bizBaseBpcbuBpcSeries.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeries.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeries.setStatus("code");
        bizBaseBpcbuBpcSeries.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeries.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeries.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeries.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeries.setIds("0");
        //bizBaseBpcbuBpcSeries.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeries.setRemark("remark");
        //setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        final BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO = new BizBaseBpcbuBpcSeriesDO();
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDO.setStatus("code");
        bizBaseBpcbuBpcSeriesDO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDO.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDO.setIds("0");
        //bizBaseBpcbuBpcSeriesDO.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDO.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseBpcbuBpcSeriesDO> expectedResult = Arrays.asList(bizBaseBpcbuBpcSeriesDO);

        // Configure IBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesListCheck(...).
        final BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO1 = new BizBaseBpcbuBpcSeriesDO();
        bizBaseBpcbuBpcSeriesDO1.setId(0L);
        bizBaseBpcbuBpcSeriesDO1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO1.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDO1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO1.setId(0L);
        bizBaseBpcbuBpcSeriesDO1.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDO1.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDO1.setStatus("code");
        bizBaseBpcbuBpcSeriesDO1.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDO1.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDO1.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDO1.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDO1.setIds("0");
        //bizBaseBpcbuBpcSeriesDO1.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDO1.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDO1.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseBpcbuBpcSeriesDO> bizBaseBpcbuBpcSeriesDOS = Arrays.asList(bizBaseBpcbuBpcSeriesDO1);
        when(mockBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesListCheck(
                any(BizBaseBpcbuBpcSeriesDTO.class))).thenReturn(bizBaseBpcbuBpcSeriesDOS);

        // Run the test
        final List<BizBaseBpcbuBpcSeriesDO> result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.selectBizBaseBpcbuBpcSeriesListCheck(
                bizBaseBpcbuBpcSeries);

        // Verify the results
        assertEquals(JSON.toJSONString(expectedResult), JSON.toJSONString(result));
    }

    @Test
    public void testSelectBizBaseBpcbuBpcSeriesListCheck_IBizBaseBpcbuBpcSeriesManagerReturnsNoItems() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeries.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setCreateBy("loginName");
        bizBaseBpcbuBpcSeries.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeries.setId(0L);
        bizBaseBpcbuBpcSeries.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeries.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeries.setStatus("code");
        bizBaseBpcbuBpcSeries.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeries.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeries.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeries.setEndMonth("endMonth");
        bizBaseBpcbuBpcSeries.setIds("0");
        bizBaseBpcbuBpcSeries.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeries.setRemark("remark");
        bizBaseBpcbuBpcSeries.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesListCheck(
                any(BizBaseBpcbuBpcSeriesDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseBpcbuBpcSeriesDO> result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.selectBizBaseBpcbuBpcSeriesListCheck(
                bizBaseBpcbuBpcSeries);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testImportExcel() throws Exception {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeriesDTO = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeriesDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDTO.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDTO.setId(0L);
        bizBaseBpcbuBpcSeriesDTO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDTO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDTO.setStatus("code");
        bizBaseBpcbuBpcSeriesDTO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDTO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDTO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDTO.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDTO.setIds("0");
        //bizBaseBpcbuBpcSeriesDTO.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDTO.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseBpcbuBpcSeriesDTO> bizs = Arrays.asList(bizBaseBpcbuBpcSeriesDTO);

        // Configure IBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesListCheck(...).
        final BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO = new BizBaseBpcbuBpcSeriesDO();
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDO.setStatus("code");
        bizBaseBpcbuBpcSeriesDO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDO.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDO.setIds("0");
        //bizBaseBpcbuBpcSeriesDO.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDO.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseBpcbuBpcSeriesDO> bizBaseBpcbuBpcSeriesDOS = Arrays.asList(bizBaseBpcbuBpcSeriesDO);
        when(mockBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesListCheck(
                bizBaseBpcbuBpcSeriesDTO)).thenReturn(bizBaseBpcbuBpcSeriesDOS);
        //        when(mockBizBaseBpcbuBpcSeriesManager.insertBizBaseBpcbuBpcSeries(any(BizBaseBpcbuBpcSeriesDO.class))).thenReturn(0);
        // Run the test
        final ResultDTO<String> result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        verify(mockBizBaseBpcbuBpcSeriesManager).selectBizBaseBpcbuBpcSeriesListCheck(any(BizBaseBpcbuBpcSeriesDTO.class));
        //verify(mockBizBaseBpcbuBpcSeriesManager).updateBizBaseBpcbuBpcSeries(any(BizBaseBpcbuBpcSeriesDO.class));
//        verify(mockBizBaseBpcbuBpcSeriesManager).insertBizBaseBpcbuBpcSeries(any(BizBaseBpcbuBpcSeriesDO.class));
    }

    @Test
    public void testImportExcel_IBizBaseBpcbuBpcSeriesManagerSelectBizBaseBpcbuBpcSeriesListCheckReturnsNoItems() {
        // Setup
        final BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeriesDTO = new BizBaseBpcbuBpcSeriesDTO();
        bizBaseBpcbuBpcSeriesDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDTO.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDTO.setId(0L);
        bizBaseBpcbuBpcSeriesDTO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDTO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDTO.setStatus("code");
        bizBaseBpcbuBpcSeriesDTO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDTO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDTO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDTO.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDTO.setIds("0");
        //bizBaseBpcbuBpcSeriesDTO.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDTO.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseBpcbuBpcSeriesDTO> bizs = Arrays.asList(bizBaseBpcbuBpcSeriesDTO);

        // Configure IBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesListCheck(...).
        final BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO = new BizBaseBpcbuBpcSeriesDO();
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setCreateBy("loginName");
        bizBaseBpcbuBpcSeriesDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpcbuBpcSeriesDO.setId(0L);
        bizBaseBpcbuBpcSeriesDO.setBpcBuCode("bpcBuCode");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesCode("bpcSeriesCode");
        bizBaseBpcbuBpcSeriesDO.setStatus("code");
        bizBaseBpcbuBpcSeriesDO.setBusinessGroup("businessGroup");
        bizBaseBpcbuBpcSeriesDO.setBpcBuDescription("bpcBuDescription");
        bizBaseBpcbuBpcSeriesDO.setBpcSeriesDescription("bpcSeriesDescription");
        bizBaseBpcbuBpcSeriesDO.setEndMonth("endMonth");
        //bizBaseBpcbuBpcSeriesDO.setIds("0");
        //bizBaseBpcbuBpcSeriesDO.setIdsList(Arrays.asList(0L));
        bizBaseBpcbuBpcSeriesDO.setRemark("remark");
        //bizBaseBpcbuBpcSeriesDO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseBpcbuBpcSeriesDO> bizBaseBpcbuBpcSeriesDOS = Arrays.asList(bizBaseBpcbuBpcSeriesDO);
        when(mockBizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesListCheck(
                any(BizBaseBpcbuBpcSeriesDTO.class))).thenReturn(Collections.emptyList());

        //when(mockBizBaseBpcbuBpcSeriesManager.updateBizBaseBpcbuBpcSeries(any(BizBaseBpcbuBpcSeriesDO.class))).thenReturn(0);
        when(mockBizBaseBpcbuBpcSeriesManager.insertBizBaseBpcbuBpcSeries(any(BizBaseBpcbuBpcSeriesDO.class))).thenReturn(0);

        // Run the test
        final ResultDTO<String> result = bizBaseBpcbuBpcSeriesServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        verify(mockBizBaseBpcbuBpcSeriesManager, Mockito.times(2)).selectBizBaseBpcbuBpcSeriesListCheck(any(BizBaseBpcbuBpcSeriesDTO.class));
//        verify(mockBizBaseBpcbuBpcSeriesManager).updateBizBaseBpcbuBpcSeries(any(BizBaseBpcbuBpcSeriesDO.class));
        verify(mockBizBaseBpcbuBpcSeriesManager).insertBizBaseBpcbuBpcSeries(any(BizBaseBpcbuBpcSeriesDO.class));
    }
}
