package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseGtnType;
import com.microservices.otmp.masterdata.mapper.BizBaseGtnTypeMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseGtnTypeServiceImpl;
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
public class BizBaseGtnTypeServiceImplTest {

    @Mock
    private BizBaseGtnTypeMapper mockBizBaseGtnTypeMapper;
    @Mock
    private RedisUtils redisUtils;
    @InjectMocks
    private BizBaseGtnTypeServiceImpl bizBaseGtnTypeServiceImplUnderTest;

    @Test
    public void testSelectBizBaseGtnTypeById() {
        // Setup
        // Configure BizBaseGtnTypeMapper.selectBizBaseGtnTypeById(...).
        final BizBaseGtnType bizBaseGtnType = new BizBaseGtnType();
        bizBaseGtnType.setId(0L);
        bizBaseGtnType.setGeoCode("geoCode");
        bizBaseGtnType.setGtnTypeCode("gtnTypeCode");
        bizBaseGtnType.setGtnCategoryCode("gtnCategoryCode");
        bizBaseGtnType.setCndnOrderReason("cndnOrderReason");
        bizBaseGtnType.setStatus("status");
        bizBaseGtnType.setRemark("memo");
        bizBaseGtnType.setBusinessGroup("businessGroup");
        when(mockBizBaseGtnTypeMapper.selectBizBaseGtnTypeById(0L)).thenReturn(bizBaseGtnType);

        // Run the test
        final BizBaseGtnType result = bizBaseGtnTypeServiceImplUnderTest.selectBizBaseGtnTypeById(0L);
        assertEquals(bizBaseGtnType, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseGtnTypeList() {
        // Setup
        final BizBaseGtnType bizBaseGtnType = new BizBaseGtnType();
        bizBaseGtnType.setId(0L);
        bizBaseGtnType.setGeoCode("geoCode");
        bizBaseGtnType.setGtnTypeCode("gtnTypeCode");
        bizBaseGtnType.setGtnCategoryCode("gtnCategoryCode");
        bizBaseGtnType.setCndnOrderReason("cndnOrderReason");
        bizBaseGtnType.setStatus("status");
        bizBaseGtnType.setRemark("memo");
        bizBaseGtnType.setBusinessGroup("businessGroup");

        // Configure BizBaseGtnTypeMapper.selectBizBaseGtnTypeList(...).
        final BizBaseGtnType bizBaseGtnType1 = new BizBaseGtnType();
        bizBaseGtnType1.setId(0L);
        bizBaseGtnType1.setGeoCode("geoCode");
        bizBaseGtnType1.setGtnTypeCode("gtnTypeCode");
        bizBaseGtnType1.setGtnCategoryCode("gtnCategoryCode");
        bizBaseGtnType1.setCndnOrderReason("cndnOrderReason");
        bizBaseGtnType1.setStatus("status");
        bizBaseGtnType1.setRemark("memo");
        bizBaseGtnType1.setBusinessGroup("businessGroup");
        final List<BizBaseGtnType> bizBaseGtnTypes = Arrays.asList(bizBaseGtnType1);
        when(mockBizBaseGtnTypeMapper.selectBizBaseGtnTypeList(any(BizBaseGtnType.class))).thenReturn(bizBaseGtnTypes);

        // Run the test
        final List<BizBaseGtnType> result = bizBaseGtnTypeServiceImplUnderTest.selectBizBaseGtnTypeList(bizBaseGtnType);
        assertEquals(bizBaseGtnTypes, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseGtnTypeList_BizBaseGtnTypeMapperReturnsNoItems() {
        // Setup
        final BizBaseGtnType bizBaseGtnType = new BizBaseGtnType();
        bizBaseGtnType.setId(0L);
        bizBaseGtnType.setGeoCode("geoCode");
        bizBaseGtnType.setGtnTypeCode("gtnTypeCode");
        bizBaseGtnType.setGtnCategoryCode("gtnCategoryCode");
        bizBaseGtnType.setCndnOrderReason("cndnOrderReason");
        bizBaseGtnType.setStatus("status");
        bizBaseGtnType.setRemark("memo");
        bizBaseGtnType.setBusinessGroup("businessGroup");

        when(mockBizBaseGtnTypeMapper.selectBizBaseGtnTypeList(any(BizBaseGtnType.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseGtnType> result = bizBaseGtnTypeServiceImplUnderTest.selectBizBaseGtnTypeList(bizBaseGtnType);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseGtnType() {
        // Setup
        final BizBaseGtnType bizBaseGtnType = new BizBaseGtnType();
        bizBaseGtnType.setId(0L);
        bizBaseGtnType.setGeoCode("geoCode");
        bizBaseGtnType.setGtnTypeCode("gtnTypeCode");
        bizBaseGtnType.setGtnCategoryCode("gtnCategoryCode");
        bizBaseGtnType.setCndnOrderReason("cndnOrderReason");
        bizBaseGtnType.setStatus("status");
        bizBaseGtnType.setRemark("memo");
        bizBaseGtnType.setBusinessGroup("businessGroup");

        when(mockBizBaseGtnTypeMapper.insertBizBaseGtnType(any(BizBaseGtnType.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseGtnTypeServiceImplUnderTest.insertBizBaseGtnType(bizBaseGtnType);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseGtnType() {
        // Setup
        final BizBaseGtnType bizBaseGtnType = new BizBaseGtnType();
        bizBaseGtnType.setId(0L);
        bizBaseGtnType.setGeoCode("geoCode");
        bizBaseGtnType.setGtnTypeCode("gtnTypeCode");
        bizBaseGtnType.setGtnCategoryCode("gtnCategoryCode");
        bizBaseGtnType.setCndnOrderReason("cndnOrderReason");
        bizBaseGtnType.setStatus("status");
        bizBaseGtnType.setRemark("memo");
        bizBaseGtnType.setBusinessGroup("businessGroup");

        when(mockBizBaseGtnTypeMapper.updateBizBaseGtnType(any(BizBaseGtnType.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseGtnTypeServiceImplUnderTest.updateBizBaseGtnType(bizBaseGtnType);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseGtnTypeByIds() {
        // Setup
        when(mockBizBaseGtnTypeMapper.updateBizBaseGtnTypeIds(any(Long[].class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseGtnTypeServiceImplUnderTest.deleteBizBaseGtnTypeByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseGtnTypeById() {
        // Setup
        when(mockBizBaseGtnTypeMapper.deleteBizBaseGtnTypeById(0L)).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseGtnTypeServiceImplUnderTest.deleteBizBaseGtnTypeById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() {
        // Setup
        final BizBaseGtnType bizBaseGtnType = new BizBaseGtnType();
        bizBaseGtnType.setId(0L);
        bizBaseGtnType.setGeoCode("geoCode");
        bizBaseGtnType.setGtnTypeCode("gtnTypeCode");
        bizBaseGtnType.setGtnCategoryCode("gtnCategoryCode");
        bizBaseGtnType.setCndnOrderReason("cndnOrderReason");
        bizBaseGtnType.setStatus("status");
        bizBaseGtnType.setRemark("memo");
        bizBaseGtnType.setBusinessGroup("businessGroup");
        final List<BizBaseGtnType> bizs = Arrays.asList(bizBaseGtnType);

        // Configure BizBaseGtnTypeMapper.selectBizBaseGtnTypeListCheck(...).
        final BizBaseGtnType bizBaseGtnType1 = new BizBaseGtnType();
        bizBaseGtnType1.setId(0L);
        bizBaseGtnType1.setGeoCode("geoCode");
        bizBaseGtnType1.setGtnTypeCode("gtnTypeCode");
        bizBaseGtnType1.setGtnCategoryCode("gtnCategoryCode");
        bizBaseGtnType1.setCndnOrderReason("cndnOrderReason");
        bizBaseGtnType1.setStatus("status");
        bizBaseGtnType1.setRemark("memo");
        bizBaseGtnType1.setBusinessGroup("businessGroup");
        final List<BizBaseGtnType> bizBaseGtnTypes = Arrays.asList(bizBaseGtnType1);
        when(mockBizBaseGtnTypeMapper.selectBizBaseGtnTypeListCheck(any(BizBaseGtnType.class))).thenReturn(bizBaseGtnTypes);

        when(mockBizBaseGtnTypeMapper.updateBizBaseGtnType(any(BizBaseGtnType.class))).thenReturn(0);


        // Run the test
        final String result = bizBaseGtnTypeServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        verify(mockBizBaseGtnTypeMapper).selectBizBaseGtnTypeListCheck(any(BizBaseGtnType.class));

    }

    @Test
    public void testImportExcel_BizBaseGtnTypeMapperSelectBizBaseGtnTypeListCheckReturnsNoItems() {
        // Setup
        final BizBaseGtnType bizBaseGtnType = new BizBaseGtnType();
        bizBaseGtnType.setId(0L);
        bizBaseGtnType.setGeoCode("geoCode");
        bizBaseGtnType.setGtnTypeCode("gtnTypeCode");
        bizBaseGtnType.setGtnCategoryCode("gtnCategoryCode");
        bizBaseGtnType.setCndnOrderReason("cndnOrderReason");
        bizBaseGtnType.setStatus("status");
        bizBaseGtnType.setRemark("memo");
        bizBaseGtnType.setBusinessGroup("businessGroup");
        final List<BizBaseGtnType> bizs = Arrays.asList(bizBaseGtnType);
        when(mockBizBaseGtnTypeMapper.selectBizBaseGtnTypeListCheck(any(BizBaseGtnType.class))).thenReturn(Collections.emptyList());

        when(mockBizBaseGtnTypeMapper.insertBizBaseGtnType(any(BizBaseGtnType.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseGtnTypeServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);

        verify(mockBizBaseGtnTypeMapper).insertBizBaseGtnType(any(BizBaseGtnType.class));
    }
}
