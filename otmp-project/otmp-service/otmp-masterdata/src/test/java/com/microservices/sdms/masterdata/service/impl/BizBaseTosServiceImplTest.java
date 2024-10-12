package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseTos;
import com.microservices.otmp.masterdata.mapper.BizBaseTosMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseTosServiceImpl;
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
public class BizBaseTosServiceImplTest {

    @Mock
    private BizBaseTosMapper mockBizBaseTosMapper;
    @Mock
    private RedisUtils mockRedisUtils;

    @InjectMocks
    private BizBaseTosServiceImpl bizBaseTosServiceImplUnderTest;

    @Test
    public void testSetTosToRedis() {
        // Setup
        // Configure BizBaseTosMapper.selectBizBaseTosList(...).
        final BizBaseTos tos = new BizBaseTos();
        tos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setId(0L);
        tos.setMemberId("memberId");
        tos.setEvDescription("evDescription");
        tos.setParentH1("parentH1");
        tos.setStatus("status");
        final List<BizBaseTos> bizBaseTos = Arrays.asList(tos);
        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(bizBaseTos);

        // Run the test
        List<BizBaseTos> results = bizBaseTosServiceImplUnderTest.selectBizBaseTosList(new BizBaseTos());
        bizBaseTosServiceImplUnderTest.setTosToRedis();

        // Verify the results
        //verify(mockRedisUtils).set("master:tos", Arrays.asList("value"), 0L);
        assertEquals(results, bizBaseTos);
    }

    @Test
    public void testSetTosToRedis_BizBaseTosMapperReturnsNoItems() {
        List<BizBaseTos> emptyList = Collections.emptyList();// Setup
        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(emptyList);

        // Run the test
        List<BizBaseTos> results = bizBaseTosServiceImplUnderTest.selectBizBaseTosList(new BizBaseTos());
        bizBaseTosServiceImplUnderTest.setTosToRedis();

        // Verify the results
        //verify(mockRedisUtils).set("master:tos", Arrays.asList("value"), 0L);
        assertEquals(results, emptyList);
    }

    @Test
    public void testSelectBizBaseTosById() {
        // Setup
        // Configure BizBaseTosMapper.selectBizBaseTosById(...).
        final BizBaseTos tos = new BizBaseTos();
        tos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setId(0L);
        tos.setMemberId("memberId");
        tos.setEvDescription("evDescription");
        tos.setParentH1("parentH1");
        tos.setStatus("status");
        when(mockBizBaseTosMapper.selectBizBaseTosById(0L)).thenReturn(tos);

        // Run the test
        final BizBaseTos result = bizBaseTosServiceImplUnderTest.selectBizBaseTosById(0L);

        // Verify the results
        assertEquals(result, tos);
    }

    @Test
    public void testSelectBizBaseTosList() {
        // Setup
        final BizBaseTos bizBaseTos = new BizBaseTos();
        bizBaseTos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setId(0L);
        bizBaseTos.setMemberId("memberId");
        bizBaseTos.setEvDescription("evDescription");
        bizBaseTos.setParentH1("parentH1");
        bizBaseTos.setStatus("status");

        // Configure BizBaseTosMapper.selectBizBaseTosList(...).
        final BizBaseTos tos = new BizBaseTos();
        tos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setId(0L);
        tos.setMemberId("memberId");
        tos.setEvDescription("evDescription");
        tos.setParentH1("parentH1");
        tos.setStatus("status");
        final List<BizBaseTos> bizBaseTos1 = Arrays.asList(tos);
        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(bizBaseTos1);

        // Run the test
        final List<BizBaseTos> result = bizBaseTosServiceImplUnderTest.selectBizBaseTosList(bizBaseTos);

        // Verify the results
        assertEquals(result, bizBaseTos1);
    }

    @Test
    public void testSelectBizBaseTosList_BizBaseTosMapperReturnsNoItems() {
        // Setup
        final BizBaseTos bizBaseTos = new BizBaseTos();
        bizBaseTos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setId(0L);
        bizBaseTos.setMemberId("memberId");
        bizBaseTos.setEvDescription("evDescription");
        bizBaseTos.setParentH1("parentH1");
        bizBaseTos.setStatus("status");

        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseTos> result = bizBaseTosServiceImplUnderTest.selectBizBaseTosList(bizBaseTos);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseTos() {
        // Setup
        final BizBaseTos bizBaseTos = new BizBaseTos();
        bizBaseTos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setId(0L);
        bizBaseTos.setMemberId("memberId");
        bizBaseTos.setEvDescription("evDescription");
        bizBaseTos.setParentH1("parentH1");
        bizBaseTos.setStatus("status");

        when(mockBizBaseTosMapper.insertBizBaseTos(any(BizBaseTos.class))).thenReturn(0);

        // Configure BizBaseTosMapper.selectBizBaseTosList(...).
        final BizBaseTos tos = new BizBaseTos();
        tos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setId(0L);
        tos.setMemberId("memberId");
        tos.setEvDescription("evDescription");
        tos.setParentH1("parentH1");
        tos.setStatus("status");
        final List<BizBaseTos> bizBaseTos1 = Arrays.asList(tos);
        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(bizBaseTos1);

        // Run the test
        final int result = bizBaseTosServiceImplUnderTest.insertBizBaseTos(bizBaseTos);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:tos", Arrays.asList("value"), 0L);
    }

    @Test
    public void testInsertBizBaseTos_BizBaseTosMapperSelectBizBaseTosListReturnsNoItems() {
        // Setup
        final BizBaseTos bizBaseTos = new BizBaseTos();
        bizBaseTos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setId(0L);
        bizBaseTos.setMemberId("memberId");
        bizBaseTos.setEvDescription("evDescription");
        bizBaseTos.setParentH1("parentH1");
        bizBaseTos.setStatus("status");

        when(mockBizBaseTosMapper.insertBizBaseTos(any(BizBaseTos.class))).thenReturn(0);
        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseTosServiceImplUnderTest.insertBizBaseTos(bizBaseTos);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:tos", Arrays.asList("value"), 0L);
    }

    @Test
    public void testUpdateBizBaseTos() {
        // Setup
        final BizBaseTos bizBaseTos = new BizBaseTos();
        bizBaseTos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setId(0L);
        bizBaseTos.setMemberId("memberId");
        bizBaseTos.setEvDescription("evDescription");
        bizBaseTos.setParentH1("parentH1");
        bizBaseTos.setStatus("status");

        when(mockBizBaseTosMapper.updateBizBaseTos(any(BizBaseTos.class))).thenReturn(0);

        // Configure BizBaseTosMapper.selectBizBaseTosList(...).
        final BizBaseTos tos = new BizBaseTos();
        tos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setId(0L);
        tos.setMemberId("memberId");
        tos.setEvDescription("evDescription");
        tos.setParentH1("parentH1");
        tos.setStatus("status");
        final List<BizBaseTos> bizBaseTos1 = Arrays.asList(tos);
        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(bizBaseTos1);

        // Run the test
        final int result = bizBaseTosServiceImplUnderTest.updateBizBaseTos(bizBaseTos);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:tos", Arrays.asList("value"), 0L);
    }

    @Test
    public void testUpdateBizBaseTos_BizBaseTosMapperSelectBizBaseTosListReturnsNoItems() {
        // Setup
        final BizBaseTos bizBaseTos = new BizBaseTos();
        bizBaseTos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseTos.setId(0L);
        bizBaseTos.setMemberId("memberId");
        bizBaseTos.setEvDescription("evDescription");
        bizBaseTos.setParentH1("parentH1");
        bizBaseTos.setStatus("status");

        when(mockBizBaseTosMapper.updateBizBaseTos(any(BizBaseTos.class))).thenReturn(0);
        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseTosServiceImplUnderTest.updateBizBaseTos(bizBaseTos);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:tos", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseTosByIds() {
        // Setup
        when(mockBizBaseTosMapper.deleteBizBaseTosByIds(any(Long[].class))).thenReturn(0);

        // Configure BizBaseTosMapper.selectBizBaseTosList(...).
        final BizBaseTos tos = new BizBaseTos();
        tos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setId(0L);
        tos.setMemberId("memberId");
        tos.setEvDescription("evDescription");
        tos.setParentH1("parentH1");
        tos.setStatus("status");
        final List<BizBaseTos> bizBaseTos = Arrays.asList(tos);
        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(bizBaseTos);

        // Run the test
        final int result = bizBaseTosServiceImplUnderTest.deleteBizBaseTosByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:tos", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseTosByIds_BizBaseTosMapperSelectBizBaseTosListReturnsNoItems() {
        // Setup
        when(mockBizBaseTosMapper.deleteBizBaseTosByIds(any(Long[].class))).thenReturn(0);
        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseTosServiceImplUnderTest.deleteBizBaseTosByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:tos", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseTosById() {
        // Setup
        when(mockBizBaseTosMapper.deleteBizBaseTosById(0L)).thenReturn(0);

        // Configure BizBaseTosMapper.selectBizBaseTosList(...).
        final BizBaseTos tos = new BizBaseTos();
        tos.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setRunTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        tos.setId(0L);
        tos.setMemberId("memberId");
        tos.setEvDescription("evDescription");
        tos.setParentH1("parentH1");
        tos.setStatus("status");
        final List<BizBaseTos> bizBaseTos = Arrays.asList(tos);
        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(bizBaseTos);

        // Run the test
        final int result = bizBaseTosServiceImplUnderTest.deleteBizBaseTosById(0L);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:tos", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseTosById_BizBaseTosMapperSelectBizBaseTosListReturnsNoItems() {
        // Setup
        when(mockBizBaseTosMapper.deleteBizBaseTosById(0L)).thenReturn(0);
        when(mockBizBaseTosMapper.selectBizBaseTosList(any(BizBaseTos.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseTosServiceImplUnderTest.deleteBizBaseTosById(0L);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:tos", Arrays.asList("value"), 0L);
    }

    @Test
    public void testImportExcel() throws Exception {
        assertNull(bizBaseTosServiceImplUnderTest.importExcel(Arrays.asList(new BizBaseTos()), "loginName"));
    }
}
