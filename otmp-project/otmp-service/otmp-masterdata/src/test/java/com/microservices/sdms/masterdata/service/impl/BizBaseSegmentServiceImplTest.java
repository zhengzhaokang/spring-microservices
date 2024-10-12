package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseSegment;
import com.microservices.otmp.masterdata.manager.BizBaseSegmentManager;
import com.microservices.otmp.masterdata.mapper.BizBaseSegmentMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseSegmentServiceImpl;
import com.microservices.otmp.system.feign.RemoteDictService;
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
public class BizBaseSegmentServiceImplTest {

    @Mock
    private BizBaseSegmentMapper mockBizBaseSegmentMapper;

    @InjectMocks
    private BizBaseSegmentServiceImpl bizBaseSegmentServiceImplUnderTest;

    @Mock
    private RemoteDictService mockRemoteDictService;

    @Mock
    private BizBaseSegmentManager mockBizBaseSegmentManager;
    @Mock
    private RedisUtils mockRedisUtils;

    @Test
    public void testSetSegmentToRedis() {
        // Setup
        // Configure BizBaseSegmentMapper.selectBizBaseSegmentList(...).
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
        segment.setCreateBy("loginName");
        segment.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        segment.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseSegment> bizBaseSegments = Arrays.asList(segment);
        when(mockBizBaseSegmentMapper.selectBizBaseSegmentList(any(BizBaseSegment.class))).thenReturn(bizBaseSegments);

        // Run the test
        bizBaseSegmentServiceImplUnderTest.setSegmentToRedis();

        // Verify the results
        verify(mockRedisUtils).set("master:biz_segments", bizBaseSegments, 31536000L);
    }

    @Test
    public void testSelectBizBaseSegmentById() {
        // Setup
        // Configure BizBaseSegmentMapper.selectBizBaseSegmentById(...).
        final BizBaseSegment bizBaseSegment = new BizBaseSegment();
        bizBaseSegment.setId(0L);
        bizBaseSegment.setSegmentCode("segmentCode");
        bizBaseSegment.setSegmentName("segmentName");
        bizBaseSegment.setSegmentLevel("segmentLevel");
        bizBaseSegment.setParentSegment("parentSegment");
        bizBaseSegment.setGpnCode("gpnCode");
        bizBaseSegment.setStatus("status");
        bizBaseSegment.setRemark("memo");
        when(mockBizBaseSegmentMapper.selectBizBaseSegmentById(0L)).thenReturn(bizBaseSegment);

        // Run the test
        final BizBaseSegment result = bizBaseSegmentServiceImplUnderTest.selectBizBaseSegmentById(0L);
        assertEquals(bizBaseSegment, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseSegmentList() {
        // Setup
        final BizBaseSegment bizBaseSegment = new BizBaseSegment();
        bizBaseSegment.setId(0L);
        bizBaseSegment.setSegmentCode("segmentCode");
        bizBaseSegment.setSegmentName("segmentName");
        bizBaseSegment.setSegmentLevel("segmentLevel");
        bizBaseSegment.setParentSegment("parentSegment");
        bizBaseSegment.setGpnCode("gpnCode");
        bizBaseSegment.setStatus("status");
        bizBaseSegment.setRemark("memo");

        // Configure BizBaseSegmentMapper.selectBizBaseSegmentList(...).
        final BizBaseSegment bizBaseSegment1 = new BizBaseSegment();
        bizBaseSegment1.setId(0L);
        bizBaseSegment1.setSegmentCode("segmentCode");
        bizBaseSegment1.setSegmentName("segmentName");
        bizBaseSegment1.setSegmentLevel("segmentLevel");
        bizBaseSegment1.setParentSegment("parentSegment");
        bizBaseSegment1.setGpnCode("gpnCode");
        bizBaseSegment1.setStatus("status");
        bizBaseSegment1.setRemark("memo");
        final List<BizBaseSegment> bizBaseSegments = Arrays.asList(bizBaseSegment1);
        when(mockBizBaseSegmentMapper.selectBizBaseSegmentList(any(BizBaseSegment.class))).thenReturn(bizBaseSegments);

        // Run the test
        final List<BizBaseSegment> result = bizBaseSegmentServiceImplUnderTest.selectBizBaseSegmentList(bizBaseSegment);
        assertEquals(bizBaseSegments, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseSegmentList_BizBaseSegmentMapperReturnsNoItems() {
        // Setup
        final BizBaseSegment bizBaseSegment = new BizBaseSegment();
        bizBaseSegment.setId(0L);
        bizBaseSegment.setSegmentCode("segmentCode");
        bizBaseSegment.setSegmentName("segmentName");
        bizBaseSegment.setSegmentLevel("segmentLevel");
        bizBaseSegment.setParentSegment("parentSegment");
        bizBaseSegment.setGpnCode("gpnCode");
        bizBaseSegment.setStatus("status");
        bizBaseSegment.setRemark("memo");

        when(mockBizBaseSegmentMapper.selectBizBaseSegmentList(any(BizBaseSegment.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseSegment> result = bizBaseSegmentServiceImplUnderTest.selectBizBaseSegmentList(bizBaseSegment);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseSegment() {
        // Setup
        final BizBaseSegment bizBaseSegment = new BizBaseSegment();
        bizBaseSegment.setId(0L);
        bizBaseSegment.setSegmentCode("segmentCode");
        bizBaseSegment.setSegmentName("segmentName");
        bizBaseSegment.setSegmentLevel("segmentLevel");
        bizBaseSegment.setParentSegment("parentSegment");
        bizBaseSegment.setGpnCode("gpnCode");
        bizBaseSegment.setStatus("status");
        bizBaseSegment.setRemark("memo");

        when(mockBizBaseSegmentMapper.insertBizBaseSegment(any(BizBaseSegment.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseSegmentServiceImplUnderTest.insertBizBaseSegment(bizBaseSegment);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseSegment() {
        // Setup
        final BizBaseSegment bizBaseSegment = new BizBaseSegment();
        bizBaseSegment.setId(0L);
        bizBaseSegment.setSegmentCode("segmentCode");
        bizBaseSegment.setSegmentName("segmentName");
        bizBaseSegment.setSegmentLevel("segmentLevel");
        bizBaseSegment.setParentSegment("parentSegment");
        bizBaseSegment.setGpnCode("gpnCode");
        bizBaseSegment.setStatus("status");
        bizBaseSegment.setRemark("memo");

        when(mockBizBaseSegmentMapper.updateBizBaseSegment(any(BizBaseSegment.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseSegmentServiceImplUnderTest.updateBizBaseSegment(bizBaseSegment);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSegmentByIds() {
        // Setup
        when(mockBizBaseSegmentMapper.updateBizBaseSegmentByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseSegmentServiceImplUnderTest.deleteBizBaseSegmentByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSegmentById() {
        // Setup
        when(mockBizBaseSegmentMapper.deleteBizBaseSegmentById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseSegmentServiceImplUnderTest.deleteBizBaseSegmentById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() {
        // Setup
        final BizBaseSegment bizBaseSegment = new BizBaseSegment();
        bizBaseSegment.setId(0L);
        bizBaseSegment.setSegmentCode("segmentCode");
        bizBaseSegment.setSegmentName("segmentName");
        bizBaseSegment.setSegmentLevel("segmentLevel");
        bizBaseSegment.setParentSegment("parentSegment");
        bizBaseSegment.setGpnCode("gpnCode");
        bizBaseSegment.setStatus("status");
        bizBaseSegment.setRemark("memo");
        final List<BizBaseSegment> bizs = Arrays.asList(bizBaseSegment);

        // Configure BizBaseSegmentMapper.selectBizBaseSegmentListCheck(...).
        final BizBaseSegment bizBaseSegment1 = new BizBaseSegment();
        bizBaseSegment1.setId(0L);
        bizBaseSegment1.setSegmentCode("segmentCode");
        bizBaseSegment1.setSegmentName("segmentName");
        bizBaseSegment1.setSegmentLevel("segmentLevel");
        bizBaseSegment1.setParentSegment("parentSegment");
        bizBaseSegment1.setGpnCode("gpnCode");
        bizBaseSegment1.setStatus("status");
        bizBaseSegment1.setRemark("memo");
        final List<BizBaseSegment> bizBaseSegments = Arrays.asList(bizBaseSegment1);
        when(mockBizBaseSegmentMapper.selectBizBaseSegmentListCheck(any(BizBaseSegment.class))).thenReturn(bizBaseSegments);

        when(mockBizBaseSegmentMapper.updateBizBaseSegment(any(BizBaseSegment.class))).thenReturn(0);


        // Run the test
        final String result = bizBaseSegmentServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockBizBaseSegmentMapper).selectBizBaseSegmentListCheck(any(BizBaseSegment.class));

    }


}
