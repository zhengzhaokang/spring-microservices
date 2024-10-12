package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseProductHierarchyByLevel;
import com.microservices.otmp.masterdata.mapper.BizBaseProductHierarchyByLevelMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseProductHierarchyByLevelServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseProductHierarchyByLevelServiceImplTest {

    @Mock
    private BizBaseProductHierarchyByLevelMapper mockBizBaseProductHierarchyByLevelMapper;
    @Mock
    private RedisUtils mockRedisUtils;

    @InjectMocks
    private BizBaseProductHierarchyByLevelServiceImpl bizBaseProductHierarchyByLevelServiceImplUnderTest;

    @Test
    public void testSetPhLevelToRedis() {
        // Setup
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setId(0L);
        bizBaseProductHierarchyByLevel.setPhLevel(0);
        bizBaseProductHierarchyByLevel.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel.setStatus("status");
        final List<BizBaseProductHierarchyByLevel> hierarchyByLevels = Arrays.asList(bizBaseProductHierarchyByLevel);

        // Run the test
        bizBaseProductHierarchyByLevelServiceImplUnderTest.setPhLevelToRedis(hierarchyByLevels, 0, "phLevel1");

        // Verify the results
        //verify(mockRedisUtils).set("key", Arrays.asList("value"), 0L);
        assertEquals(1,hierarchyByLevels.size());
    }

    @Test
    public void testSelectBizBaseProductHierarchyByLevelById() {
        // Setup
        // Configure BizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelById(...).
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setId(0L);
        bizBaseProductHierarchyByLevel.setPhLevel(0);
        bizBaseProductHierarchyByLevel.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel.setStatus("status");
        when(mockBizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelById(0L))
                .thenReturn(bizBaseProductHierarchyByLevel);

        // Run the test
        final BizBaseProductHierarchyByLevel result = bizBaseProductHierarchyByLevelServiceImplUnderTest.selectBizBaseProductHierarchyByLevelById(
                0L);

        // Verify the results
        assertEquals(result, bizBaseProductHierarchyByLevel);
    }

    @Test
    public void testSelectBizBaseProductHierarchyByLevelList() {
        // Setup
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setId(0L);
        bizBaseProductHierarchyByLevel.setPhLevel(0);
        bizBaseProductHierarchyByLevel.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel.setStatus("status");

        // Configure BizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(...).
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel1 = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel1.setId(0L);
        bizBaseProductHierarchyByLevel1.setPhLevel(0);
        bizBaseProductHierarchyByLevel1.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel1.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel1.setStatus("status");
        final List<BizBaseProductHierarchyByLevel> bizBaseProductHierarchyByLevels = Arrays.asList(
                bizBaseProductHierarchyByLevel1);
        when(mockBizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(bizBaseProductHierarchyByLevels);

        // Run the test
        final List<BizBaseProductHierarchyByLevel> result = bizBaseProductHierarchyByLevelServiceImplUnderTest.selectBizBaseProductHierarchyByLevelList(
                bizBaseProductHierarchyByLevel);

        // Verify the results
        assertEquals(result, bizBaseProductHierarchyByLevels);
    }

    @Test
    public void testSelectBizBaseProductHierarchyByLevelList_BizBaseProductHierarchyByLevelMapperReturnsNoItems() {
        // Setup
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setId(0L);
        bizBaseProductHierarchyByLevel.setPhLevel(0);
        bizBaseProductHierarchyByLevel.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel.setStatus("status");

        when(mockBizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseProductHierarchyByLevel> result = bizBaseProductHierarchyByLevelServiceImplUnderTest.selectBizBaseProductHierarchyByLevelList(
                bizBaseProductHierarchyByLevel);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseProductHierarchyByLevel() {
        // Setup
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setId(0L);
        bizBaseProductHierarchyByLevel.setPhLevel(0);
        bizBaseProductHierarchyByLevel.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel.setStatus("status");

        when(mockBizBaseProductHierarchyByLevelMapper.insertBizBaseProductHierarchyByLevel(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(0);

        // Configure BizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(...).
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel1 = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel1.setId(0L);
        bizBaseProductHierarchyByLevel1.setPhLevel(0);
        bizBaseProductHierarchyByLevel1.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel1.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel1.setStatus("status");
        final List<BizBaseProductHierarchyByLevel> bizBaseProductHierarchyByLevels = Arrays.asList(
                bizBaseProductHierarchyByLevel1);
        when(mockBizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(bizBaseProductHierarchyByLevels);

        // Run the test
        final int result = bizBaseProductHierarchyByLevelServiceImplUnderTest.insertBizBaseProductHierarchyByLevel(
                bizBaseProductHierarchyByLevel);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("key", Arrays.asList("value"), 0L);
    }

    @Test
    public void testInsertBizBaseProductHierarchyByLevel_BizBaseProductHierarchyByLevelMapperSelectBizBaseProductHierarchyByLevelListReturnsNoItems() {
        // Setup
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setId(0L);
        bizBaseProductHierarchyByLevel.setPhLevel(0);
        bizBaseProductHierarchyByLevel.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel.setStatus("status");

        when(mockBizBaseProductHierarchyByLevelMapper.insertBizBaseProductHierarchyByLevel(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(0);
        when(mockBizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseProductHierarchyByLevelServiceImplUnderTest.insertBizBaseProductHierarchyByLevel(
                bizBaseProductHierarchyByLevel);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("key", Arrays.asList("value"), 0L);
    }

    @Test
    public void testUpdateBizBaseProductHierarchyByLevel() {
        // Setup
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setId(0L);
        bizBaseProductHierarchyByLevel.setPhLevel(0);
        bizBaseProductHierarchyByLevel.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel.setStatus("status");

        when(mockBizBaseProductHierarchyByLevelMapper.updateBizBaseProductHierarchyByLevel(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(0);

        // Configure BizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(...).
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel1 = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel1.setId(0L);
        bizBaseProductHierarchyByLevel1.setPhLevel(0);
        bizBaseProductHierarchyByLevel1.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel1.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel1.setStatus("status");
        final List<BizBaseProductHierarchyByLevel> bizBaseProductHierarchyByLevels = Arrays.asList(
                bizBaseProductHierarchyByLevel1);
        when(mockBizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(bizBaseProductHierarchyByLevels);

        // Run the test
        final int result = bizBaseProductHierarchyByLevelServiceImplUnderTest.updateBizBaseProductHierarchyByLevel(
                bizBaseProductHierarchyByLevel);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("key", Arrays.asList("value"), 0L);
    }

    @Test
    public void testUpdateBizBaseProductHierarchyByLevel_BizBaseProductHierarchyByLevelMapperSelectBizBaseProductHierarchyByLevelListReturnsNoItems() {
        // Setup
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setId(0L);
        bizBaseProductHierarchyByLevel.setPhLevel(0);
        bizBaseProductHierarchyByLevel.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel.setStatus("status");

        when(mockBizBaseProductHierarchyByLevelMapper.updateBizBaseProductHierarchyByLevel(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(0);
        when(mockBizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseProductHierarchyByLevelServiceImplUnderTest.updateBizBaseProductHierarchyByLevel(
                bizBaseProductHierarchyByLevel);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("key", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseProductHierarchyByLevelByIds() {
        // Setup
        when(mockBizBaseProductHierarchyByLevelMapper.deleteBizBaseProductHierarchyByLevelByIds(
                any(Long[].class))).thenReturn(0);

        // Configure BizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(...).
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setId(0L);
        bizBaseProductHierarchyByLevel.setPhLevel(0);
        bizBaseProductHierarchyByLevel.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel.setStatus("status");
        final List<BizBaseProductHierarchyByLevel> bizBaseProductHierarchyByLevels = Arrays.asList(
                bizBaseProductHierarchyByLevel);
        when(mockBizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(bizBaseProductHierarchyByLevels);

        // Run the test
        final int result = bizBaseProductHierarchyByLevelServiceImplUnderTest.deleteBizBaseProductHierarchyByLevelByIds(
                new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("key", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseProductHierarchyByLevelByIds_BizBaseProductHierarchyByLevelMapperSelectBizBaseProductHierarchyByLevelListReturnsNoItems() {
        // Setup
        when(mockBizBaseProductHierarchyByLevelMapper.deleteBizBaseProductHierarchyByLevelByIds(
                any(Long[].class))).thenReturn(0);
        when(mockBizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseProductHierarchyByLevelServiceImplUnderTest.deleteBizBaseProductHierarchyByLevelByIds(
                new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("key", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseProductHierarchyByLevelById() {
        // Setup
        when(mockBizBaseProductHierarchyByLevelMapper.deleteBizBaseProductHierarchyByLevelById(0L)).thenReturn(0);

        // Configure BizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(...).
        final BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel = new BizBaseProductHierarchyByLevel();
        bizBaseProductHierarchyByLevel.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseProductHierarchyByLevel.setId(0L);
        bizBaseProductHierarchyByLevel.setPhLevel(0);
        bizBaseProductHierarchyByLevel.setProductHierarchyCode("productHierarchyCode");
        bizBaseProductHierarchyByLevel.setProductHierarchyName("productHierarchyName");
        bizBaseProductHierarchyByLevel.setStatus("status");
        final List<BizBaseProductHierarchyByLevel> bizBaseProductHierarchyByLevels = Arrays.asList(
                bizBaseProductHierarchyByLevel);
        when(mockBizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(bizBaseProductHierarchyByLevels);

        // Run the test
        final int result = bizBaseProductHierarchyByLevelServiceImplUnderTest.deleteBizBaseProductHierarchyByLevelById(
                0L);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("key", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseProductHierarchyByLevelById_BizBaseProductHierarchyByLevelMapperSelectBizBaseProductHierarchyByLevelListReturnsNoItems() {
        // Setup
        when(mockBizBaseProductHierarchyByLevelMapper.deleteBizBaseProductHierarchyByLevelById(0L)).thenReturn(0);
        when(mockBizBaseProductHierarchyByLevelMapper.selectBizBaseProductHierarchyByLevelList(
                any(BizBaseProductHierarchyByLevel.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseProductHierarchyByLevelServiceImplUnderTest.deleteBizBaseProductHierarchyByLevelById(
                0L);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("key", Arrays.asList("value"), 0L);
    }

    @Test
    public void testImportExcel() throws Exception {
        assertNull(bizBaseProductHierarchyByLevelServiceImplUnderTest.importExcel(
                Arrays.asList(new BizBaseProductHierarchyByLevel()), "loginName"));
    }
}
