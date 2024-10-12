package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysDictType;
import com.microservices.otmp.system.domain.entity.SysDictTypeDO;
import com.microservices.otmp.system.mapper.SysDictTypeMapper;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysDictTypeManagerImplTest {

    @Mock
    private SysDictTypeMapper mockSysDictTypeMapper;

    @InjectMocks
    private SysDictTypeManagerImpl sysDictTypeManagerImplUnderTest;

    @Test
    public void testSelectDictTypeListLike() {
        // Setup
        final SysDictType dictType = new SysDictType();
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setDeleteFlag("deleteFlag");
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});
        dictType.setDictCode("dictCode");

        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        final List<SysDictTypeDO> expectedResult = Arrays.asList(sysDictTypeDO);

        // Configure SysDictTypeMapper.selectDictTypeListLike(...).
        final SysDictTypeDO sysDictTypeDO1 = new SysDictTypeDO();
        sysDictTypeDO1.setDictId(0L);
        sysDictTypeDO1.setDictName("dictName");
        sysDictTypeDO1.setDictType("dictType");
        sysDictTypeDO1.setStatus("status");
        sysDictTypeDO1.setShowDataStatus("showDataStatus");
        sysDictTypeDO1.setIds("ids");
        sysDictTypeDO1.setIdsArray(new Long[]{0L});
        final List<SysDictTypeDO> sysDictTypeDOS = Arrays.asList(sysDictTypeDO1);
        when(mockSysDictTypeMapper.selectDictTypeListLike(new SysDictType())).thenReturn(sysDictTypeDOS);

        // Run the test
        final List<SysDictTypeDO> result = sysDictTypeManagerImplUnderTest.selectDictTypeListLike(dictType);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectDictTypeListLike_SysDictTypeMapperReturnsNoItems() {
        // Setup
        final SysDictType dictType = new SysDictType();
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setDeleteFlag("deleteFlag");
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});
        dictType.setDictCode("dictCode");

        when(mockSysDictTypeMapper.selectDictTypeListLike(new SysDictType())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDictTypeDO> result = sysDictTypeManagerImplUnderTest.selectDictTypeListLike(dictType);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectDictTypeList() {
        // Setup
        final SysDictType dictType = new SysDictType();
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setDeleteFlag("deleteFlag");
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});
        dictType.setDictCode("dictCode");

        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        final List<SysDictTypeDO> expectedResult = Arrays.asList(sysDictTypeDO);

        // Configure SysDictTypeMapper.selectDictTypeList(...).
        final SysDictTypeDO sysDictTypeDO1 = new SysDictTypeDO();
        sysDictTypeDO1.setDictId(0L);
        sysDictTypeDO1.setDictName("dictName");
        sysDictTypeDO1.setDictType("dictType");
        sysDictTypeDO1.setStatus("status");
        sysDictTypeDO1.setShowDataStatus("showDataStatus");
        sysDictTypeDO1.setIds("ids");
        sysDictTypeDO1.setIdsArray(new Long[]{0L});
        final List<SysDictTypeDO> sysDictTypeDOS = Arrays.asList(sysDictTypeDO1);
        when(mockSysDictTypeMapper.selectDictTypeList(new SysDictType())).thenReturn(sysDictTypeDOS);

        // Run the test
        final List<SysDictTypeDO> result = sysDictTypeManagerImplUnderTest.selectDictTypeList(dictType);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectDictTypeList_SysDictTypeMapperReturnsNoItems() {
        // Setup
        final SysDictType dictType = new SysDictType();
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setDeleteFlag("deleteFlag");
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});
        dictType.setDictCode("dictCode");

        when(mockSysDictTypeMapper.selectDictTypeList(new SysDictType())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDictTypeDO> result = sysDictTypeManagerImplUnderTest.selectDictTypeList(dictType);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectDictTypeAll() {
        // Setup
        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        final List<SysDictTypeDO> expectedResult = Arrays.asList(sysDictTypeDO);

        // Configure SysDictTypeMapper.selectDictTypeAll(...).
        final SysDictTypeDO sysDictTypeDO1 = new SysDictTypeDO();
        sysDictTypeDO1.setDictId(0L);
        sysDictTypeDO1.setDictName("dictName");
        sysDictTypeDO1.setDictType("dictType");
        sysDictTypeDO1.setStatus("status");
        sysDictTypeDO1.setShowDataStatus("showDataStatus");
        sysDictTypeDO1.setIds("ids");
        sysDictTypeDO1.setIdsArray(new Long[]{0L});
        final List<SysDictTypeDO> sysDictTypeDOS = Arrays.asList(sysDictTypeDO1);
        when(mockSysDictTypeMapper.selectDictTypeAll()).thenReturn(sysDictTypeDOS);

        // Run the test
        final List<SysDictTypeDO> result = sysDictTypeManagerImplUnderTest.selectDictTypeAll();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectDictTypeAll_SysDictTypeMapperReturnsNoItems() {
        // Setup
        when(mockSysDictTypeMapper.selectDictTypeAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDictTypeDO> result = sysDictTypeManagerImplUnderTest.selectDictTypeAll();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectDictTypeById() {
        // Setup
        final SysDictTypeDO expectedResult = new SysDictTypeDO();
        expectedResult.setDictId(0L);
        expectedResult.setDictName("dictName");
        expectedResult.setDictType("dictType");
        expectedResult.setStatus("status");
        expectedResult.setShowDataStatus("showDataStatus");
        expectedResult.setIds("ids");
        expectedResult.setIdsArray(new Long[]{0L});

        // Configure SysDictTypeMapper.selectDictTypeById(...).
        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        when(mockSysDictTypeMapper.selectDictTypeById(0L)).thenReturn(sysDictTypeDO);

        // Run the test
        final SysDictTypeDO result = sysDictTypeManagerImplUnderTest.selectDictTypeById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testDeleteDictTypeById() {
        // Setup
        when(mockSysDictTypeMapper.deleteDictTypeById(0L)).thenReturn(0);

        // Run the test
        final int result = sysDictTypeManagerImplUnderTest.deleteDictTypeById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteDictTypeByIds() {
        // Setup
        when(mockSysDictTypeMapper.deleteDictTypeByIds(eq("loginName"), any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysDictTypeManagerImplUnderTest.deleteDictTypeByIds("loginName", new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertDictType() {
        // Setup
        final SysDictTypeDO dictType = new SysDictTypeDO();
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});

        when(mockSysDictTypeMapper.insertDictType(new SysDictTypeDO())).thenReturn(0);

        // Run the test
        final int result = sysDictTypeManagerImplUnderTest.insertDictType(dictType);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDictType() {
        // Setup
        final SysDictTypeDO dictType = new SysDictTypeDO();
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});

        when(mockSysDictTypeMapper.updateDictType(new SysDictTypeDO())).thenReturn(0);

        // Run the test
        final int result = sysDictTypeManagerImplUnderTest.updateDictType(dictType);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckDictTypeUnique() {
        // Setup
        final SysDictTypeDO expectedResult = new SysDictTypeDO();
        expectedResult.setDictId(0L);
        expectedResult.setDictName("dictName");
        expectedResult.setDictType("dictType");
        expectedResult.setStatus("status");
        expectedResult.setShowDataStatus("showDataStatus");
        expectedResult.setIds("ids");
        expectedResult.setIdsArray(new Long[]{0L});

        // Configure SysDictTypeMapper.checkDictTypeUnique(...).
        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        when(mockSysDictTypeMapper.checkDictTypeUnique("dictType")).thenReturn(sysDictTypeDO);

        // Run the test
        final SysDictTypeDO result = sysDictTypeManagerImplUnderTest.checkDictTypeUnique("dictType");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testDeleteDictTypeByDictType() {
        // Setup
        when(mockSysDictTypeMapper.deleteDictTypeByDictType("dictType")).thenReturn(0);

        // Run the test
        final int result = sysDictTypeManagerImplUnderTest.deleteDictTypeByDictType("dictType");

        // Verify the results
        assertEquals(0, result);
    }
}
