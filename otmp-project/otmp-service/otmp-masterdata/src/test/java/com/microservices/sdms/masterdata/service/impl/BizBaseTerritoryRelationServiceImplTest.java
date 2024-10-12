package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSegment;
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;
import com.microservices.otmp.masterdata.mapper.BizBaseTerritoryRelationMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseTerritoryRelationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseTerritoryRelationServiceImplTest {

    @Mock
    private BizBaseTerritoryRelationMapper mockBizBaseTerritoryRelationMapper;
    @Mock
    private RedisUtils mockRedisUtils;

    @InjectMocks
    private BizBaseTerritoryRelationServiceImpl bizBaseTerritoryRelationServiceImplUnderTest;

    @Test
    public void testSetTerritoryToRedis() {
        // Setup
        // Configure BizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(...).
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");
        final List<BizBaseTerritoryRelation> bizBaseTerritoryRelations = Arrays.asList(bizBaseTerritoryRelation);
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(bizBaseTerritoryRelations);

        // Run the test
        List<BizBaseTerritoryRelation> results = bizBaseTerritoryRelationServiceImplUnderTest.selectBizBaseTerritoryRelationList(bizBaseTerritoryRelation);
        bizBaseTerritoryRelationServiceImplUnderTest.setTerritoryToRedis();

        // Verify the results
        //verify(mockRedisUtils).set("master:territory", Arrays.asList(new BizBaseTerritoryRelation()), 0L);
        assertEquals(results, bizBaseTerritoryRelations);
    }

    @Test
    public void testSetTerritoryToRedis_BizBaseTerritoryRelationMapperReturnsNoItems() {
        List<BizBaseTerritoryRelation> emptyList = Collections.emptyList();
        // Setup
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(emptyList);

        // Run the test
        List<BizBaseTerritoryRelation> results = bizBaseTerritoryRelationServiceImplUnderTest.selectBizBaseTerritoryRelationList(new BizBaseTerritoryRelation());
        bizBaseTerritoryRelationServiceImplUnderTest.setTerritoryToRedis();

        // Verify the results
        //verify(mockRedisUtils).set("master:territory", Arrays.asList(new BizBaseTerritoryRelation()), 0L);
        assertEquals(results, emptyList);
    }

    @Test
    public void testSelectBizBaseTerritoryRelationById() {
        // Setup
        // Configure BizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationById(...).
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationById(0L))
                .thenReturn(bizBaseTerritoryRelation);

        // Run the test
        final BizBaseTerritoryRelation result = bizBaseTerritoryRelationServiceImplUnderTest.selectBizBaseTerritoryRelationById(
                0L);

        // Verify the results
        assertEquals(result, bizBaseTerritoryRelation);
    }

    @Test
    public void testSelectBizBaseTerritoryRelationList() {
        // Setup
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");

        // Configure BizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(...).
        final BizBaseTerritoryRelation bizBaseTerritoryRelation1 = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation1.setId(0L);
        bizBaseTerritoryRelation1.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation1.setGeoCode("geoCode");
        bizBaseTerritoryRelation1.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation1.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation1.setTerritory("territory");
        bizBaseTerritoryRelation1.setStatus("status");
        final List<BizBaseTerritoryRelation> bizBaseTerritoryRelations = Arrays.asList(bizBaseTerritoryRelation1);
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(bizBaseTerritoryRelations);

        // Run the test
        final List<BizBaseTerritoryRelation> result = bizBaseTerritoryRelationServiceImplUnderTest.selectBizBaseTerritoryRelationList(
                bizBaseTerritoryRelation);

        // Verify the results
        assertEquals(result, bizBaseTerritoryRelations);
    }

    @Test
    public void testSelectBizBaseTerritoryRelationList_BizBaseTerritoryRelationMapperReturnsNoItems() {
        // Setup
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");

        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseTerritoryRelation> result = bizBaseTerritoryRelationServiceImplUnderTest.selectBizBaseTerritoryRelationList(
                bizBaseTerritoryRelation);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetDropDownList() {
        // Setup
        final BizBaseDropDownCondition bizBaseDropDownCondition = new BizBaseDropDownCondition();
        bizBaseDropDownCondition.setGeoCode("geoCode");
        bizBaseDropDownCondition.setBusinessGroup("businessGroup");
        bizBaseDropDownCondition.setRegionMarketCode("regionMarketCode");
        bizBaseDropDownCondition.setSalesOrgCode("salesOrgCode");
        bizBaseDropDownCondition.setSalesOrgs(new String[]{"salesOrgs"});
        bizBaseDropDownCondition.setTempField("tempField");
        bizBaseDropDownCondition.setPaymentModeCode("paymentModeCode");
        bizBaseDropDownCondition.setGtnTypeCode("gtnTypeCode");

        // Configure BizBaseTerritoryRelationMapper.getDropDownList(...).
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");
        final List<BizBaseTerritoryRelation> bizBaseTerritoryRelations = Arrays.asList(bizBaseTerritoryRelation);
        when(mockBizBaseTerritoryRelationMapper.getDropDownList(any(BizBaseDropDownCondition.class)))
                .thenReturn(bizBaseTerritoryRelations);

        // Run the test
        final List<BizBaseTerritoryRelation> result = bizBaseTerritoryRelationServiceImplUnderTest.getDropDownList(
                bizBaseDropDownCondition);

        // Verify the results
        assertEquals(result, bizBaseTerritoryRelations);
    }

    @Test
    public void testGetDropDownList_BizBaseTerritoryRelationMapperReturnsNoItems() {
        // Setup
        final BizBaseDropDownCondition bizBaseDropDownCondition = new BizBaseDropDownCondition();
        bizBaseDropDownCondition.setGeoCode("geoCode");
        bizBaseDropDownCondition.setBusinessGroup("businessGroup");
        bizBaseDropDownCondition.setRegionMarketCode("regionMarketCode");
        bizBaseDropDownCondition.setSalesOrgCode("salesOrgCode");
        bizBaseDropDownCondition.setSalesOrgs(new String[]{"salesOrgs"});
        bizBaseDropDownCondition.setTempField("tempField");
        bizBaseDropDownCondition.setPaymentModeCode("paymentModeCode");
        bizBaseDropDownCondition.setGtnTypeCode("gtnTypeCode");

        when(mockBizBaseTerritoryRelationMapper.getDropDownList(any(BizBaseDropDownCondition.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseTerritoryRelation> result = bizBaseTerritoryRelationServiceImplUnderTest.getDropDownList(
                bizBaseDropDownCondition);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseTerritoryRelation() {
        // Setup
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");

        when(mockBizBaseTerritoryRelationMapper.insertBizBaseTerritoryRelation(
                any(BizBaseTerritoryRelation.class))).thenReturn(0);

        // Configure BizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(...).
        final BizBaseTerritoryRelation bizBaseTerritoryRelation1 = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation1.setId(0L);
        bizBaseTerritoryRelation1.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation1.setGeoCode("geoCode");
        bizBaseTerritoryRelation1.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation1.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation1.setTerritory("territory");
        bizBaseTerritoryRelation1.setStatus("status");
        final List<BizBaseTerritoryRelation> bizBaseTerritoryRelations = Arrays.asList(bizBaseTerritoryRelation1);
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(bizBaseTerritoryRelations);

        // Run the test
        final int result = bizBaseTerritoryRelationServiceImplUnderTest.insertBizBaseTerritoryRelation(
                bizBaseTerritoryRelation);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:territory", Arrays.asList(new BizBaseTerritoryRelation()), 0L);
    }

    @Test
    public void testInsertBizBaseTerritoryRelation_BizBaseTerritoryRelationMapperSelectBizBaseTerritoryRelationListReturnsNoItems() {
        // Setup
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");

        when(mockBizBaseTerritoryRelationMapper.insertBizBaseTerritoryRelation(
                any(BizBaseTerritoryRelation.class))).thenReturn(0);
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseTerritoryRelationServiceImplUnderTest.insertBizBaseTerritoryRelation(
                bizBaseTerritoryRelation);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:territory", Arrays.asList(new BizBaseTerritoryRelation()), 0L);
    }

    @Test
    public void testUpdateBizBaseTerritoryRelation() {
        // Setup
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");

        when(mockBizBaseTerritoryRelationMapper.updateBizBaseTerritoryRelation(
                any(BizBaseTerritoryRelation.class))).thenReturn(0);

        // Configure BizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(...).
        final BizBaseTerritoryRelation bizBaseTerritoryRelation1 = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation1.setId(0L);
        bizBaseTerritoryRelation1.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation1.setGeoCode("geoCode");
        bizBaseTerritoryRelation1.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation1.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation1.setTerritory("territory");
        bizBaseTerritoryRelation1.setStatus("status");
        final List<BizBaseTerritoryRelation> bizBaseTerritoryRelations = Arrays.asList(bizBaseTerritoryRelation1);
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(bizBaseTerritoryRelations);

        // Run the test
        final int result = bizBaseTerritoryRelationServiceImplUnderTest.updateBizBaseTerritoryRelation(
                bizBaseTerritoryRelation);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:territory", Arrays.asList(new BizBaseTerritoryRelation()), 0L);
    }

    @Test
    public void testUpdateBizBaseTerritoryRelation_BizBaseTerritoryRelationMapperSelectBizBaseTerritoryRelationListReturnsNoItems() {
        // Setup
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");

        when(mockBizBaseTerritoryRelationMapper.updateBizBaseTerritoryRelation(
                any(BizBaseTerritoryRelation.class))).thenReturn(0);
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseTerritoryRelationServiceImplUnderTest.updateBizBaseTerritoryRelation(
                bizBaseTerritoryRelation);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:territory", Arrays.asList(new BizBaseTerritoryRelation()), 0L);
    }

    @Test
    public void testDeleteBizBaseTerritoryRelationByIds() {
        // Setup
        when(mockBizBaseTerritoryRelationMapper.deleteBizBaseTerritoryRelationByIds(any(Long[].class))).thenReturn(0);

        // Configure BizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(...).
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");
        final List<BizBaseTerritoryRelation> bizBaseTerritoryRelations = Arrays.asList(bizBaseTerritoryRelation);
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(bizBaseTerritoryRelations);

        // Run the test
        final int result = bizBaseTerritoryRelationServiceImplUnderTest.deleteBizBaseTerritoryRelationByIds(
                new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:territory", Arrays.asList(new BizBaseTerritoryRelation()), 0L);
    }

    @Test
    public void testDeleteBizBaseTerritoryRelationByIds_BizBaseTerritoryRelationMapperSelectBizBaseTerritoryRelationListReturnsNoItems() {
        // Setup
        when(mockBizBaseTerritoryRelationMapper.deleteBizBaseTerritoryRelationByIds(any(Long[].class))).thenReturn(0);
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseTerritoryRelationServiceImplUnderTest.deleteBizBaseTerritoryRelationByIds(
                new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:territory", Arrays.asList(new BizBaseTerritoryRelation()), 0L);
    }

    @Test
    public void testDeleteBizBaseTerritoryRelationById() {
        // Setup
        when(mockBizBaseTerritoryRelationMapper.deleteBizBaseTerritoryRelationById(0L)).thenReturn(0);

        // Configure BizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(...).
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");
        final List<BizBaseTerritoryRelation> bizBaseTerritoryRelations = Arrays.asList(bizBaseTerritoryRelation);
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(bizBaseTerritoryRelations);

        // Run the test
        final int result = bizBaseTerritoryRelationServiceImplUnderTest.deleteBizBaseTerritoryRelationById(0L);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:territory", Arrays.asList(new BizBaseTerritoryRelation()), 0L);
    }

    @Test
    public void testDeleteBizBaseTerritoryRelationById_BizBaseTerritoryRelationMapperSelectBizBaseTerritoryRelationListReturnsNoItems() {
        // Setup
        when(mockBizBaseTerritoryRelationMapper.deleteBizBaseTerritoryRelationById(0L)).thenReturn(0);
        when(mockBizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(
                any(BizBaseTerritoryRelation.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseTerritoryRelationServiceImplUnderTest.deleteBizBaseTerritoryRelationById(0L);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:territory", Arrays.asList(new BizBaseTerritoryRelation()), 0L);
    }

    @Test
    public void testImportExcel() throws Exception {
        // Setup
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");
        final List<BizBaseTerritoryRelation> bizs = Arrays.asList(bizBaseTerritoryRelation);

        // Configure BizBaseTerritoryRelationMapper.selectbizBaseTerritoryRelationListCheck(...).
        final BizBaseSegment segment = new BizBaseSegment();
        segment.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        segment.setIds("ids");
        segment.setIdsList(Arrays.asList(0L));
        segment.setId(0L);
        segment.setSegmentCode("segmentCode");
        segment.setSegmentName("segmentName");
        segment.setSegmentLevel("segmentLevel");
        segment.setParentSegment("parentSegment");
        segment.setGpnCode("gpnCode");
        segment.setStatus("status");
        segment.setBusinessGroup("businessGroup");
        segment.setCreateBy("createBy");
        segment.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        segment.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseSegment> bizBaseSegments = Arrays.asList(segment);
        when(mockBizBaseTerritoryRelationMapper.selectbizBaseTerritoryRelationListCheck(
                any(BizBaseTerritoryRelation.class))).thenReturn(bizBaseSegments);

        when(mockBizBaseTerritoryRelationMapper.updateBizBaseTerritoryRelation(
                any(BizBaseTerritoryRelation.class))).thenReturn(0);
        /*when(mockBizBaseTerritoryRelationMapper.insertBizBaseTerritoryRelation(
                any(BizBaseTerritoryRelation.class))).thenReturn(0);*/

        // Run the test
        final String result = bizBaseTerritoryRelationServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
//        verify(mockBizBaseTerritoryRelationMapper).updateBizBaseTerritoryRelation(any(BizBaseTerritoryRelation.class));
//        verify(mockBizBaseTerritoryRelationMapper).insertBizBaseTerritoryRelation(any(BizBaseTerritoryRelation.class));
    }

    @Test
    public void testImportExcel_BizBaseTerritoryRelationMapperSelectbizBaseTerritoryRelationListCheckReturnsNoItems() {
        // Setup
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");
        final List<BizBaseTerritoryRelation> bizs = Arrays.asList(bizBaseTerritoryRelation);
        when(mockBizBaseTerritoryRelationMapper.selectbizBaseTerritoryRelationListCheck(
                any(BizBaseTerritoryRelation.class))).thenReturn(Collections.emptyList());
//        when(mockBizBaseTerritoryRelationMapper.updateBizBaseTerritoryRelation(
//                any(BizBaseTerritoryRelation.class))).thenReturn(0);
//        when(mockBizBaseTerritoryRelationMapper.insertBizBaseTerritoryRelation(
//                any(BizBaseTerritoryRelation.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseTerritoryRelationServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
//        verify(mockBizBaseTerritoryRelationMapper).updateBizBaseTerritoryRelation(any(BizBaseTerritoryRelation.class));
//        verify(mockBizBaseTerritoryRelationMapper).insertBizBaseTerritoryRelation(any(BizBaseTerritoryRelation.class));
    }
}
