package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.exception.BusinessException;
import com.microservices.otmp.system.domain.SysDictType;
import com.microservices.otmp.system.domain.entity.SysDictTypeDO;
import com.microservices.otmp.system.manager.SysDictDataManager;
import com.microservices.otmp.system.manager.SysDictTypeManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysDictTypeServiceImplTest {

    @Mock
    private SysDictTypeManager mockSysDictTypeManager;
    @Mock
    private SysDictDataManager mockSysDictDataManager;

    @InjectMocks
    private SysDictTypeServiceImpl sysDictTypeServiceImplUnderTest;

    @Test
    public void testSelectDictTypeList() {
        // Setup
        final SysDictType dictType = new SysDictType();
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});
        dictType.setDeleteFlag("deleteFlag");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");

        // Configure SysDictTypeManager.selectDictTypeList(...).
        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        final List<SysDictTypeDO> sysDictTypeDOS = Arrays.asList(sysDictTypeDO);
        when(mockSysDictTypeManager.selectDictTypeList(any(SysDictType.class))).thenReturn(sysDictTypeDOS);

        // Run the test
        final List<SysDictType> result = sysDictTypeServiceImplUnderTest.selectDictTypeList(dictType);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( 1,result.size());
        verify(mockSysDictTypeManager, times(1)).selectDictTypeList(any());
    }

    @Test
    public void testSelectDictTypeList_SysDictTypeManagerReturnsNoItems() {
        // Setup
        final SysDictType dictType = new SysDictType();
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});
        dictType.setDeleteFlag("deleteFlag");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");

        when(mockSysDictTypeManager.selectDictTypeList(any(SysDictType.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDictType> result = sysDictTypeServiceImplUnderTest.selectDictTypeList(dictType);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectDictTypeAll() {
        // Setup
        // Configure SysDictTypeManager.selectDictTypeAll(...).
        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        final List<SysDictTypeDO> sysDictTypeDOS = Arrays.asList(sysDictTypeDO);
        when(mockSysDictTypeManager.selectDictTypeAll()).thenReturn(sysDictTypeDOS);

        // Run the test
        final List<SysDictType> result = sysDictTypeServiceImplUnderTest.selectDictTypeAll();

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( 1,result.size());
        verify(mockSysDictTypeManager, times(1)).selectDictTypeAll();
    }

    @Test
    public void testSelectDictTypeAll_SysDictTypeManagerReturnsNoItems() {
        // Setup
        when(mockSysDictTypeManager.selectDictTypeAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDictType> result = sysDictTypeServiceImplUnderTest.selectDictTypeAll();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectDictTypeById() {
        // Setup
        // Configure SysDictTypeManager.selectDictTypeById(...).
        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        when(mockSysDictTypeManager.selectDictTypeById(0L)).thenReturn(sysDictTypeDO);

        // Run the test
        final SysDictType result = sysDictTypeServiceImplUnderTest.selectDictTypeById(0L);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals(sysDictTypeDO.getDictId(), result.getDictId());
        verify(mockSysDictTypeManager, times(1)).selectDictTypeById(any());
    }

    @Test
    public void testDeleteDictTypeById() {
        // Setup
        when(mockSysDictTypeManager.deleteDictTypeById(0L)).thenReturn(0);

        // Run the test
        final int result = sysDictTypeServiceImplUnderTest.deleteDictTypeById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteDictTypeByIds() {
        // Setup
        // Configure SysDictTypeManager.selectDictTypeById(...).
        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        when(mockSysDictTypeManager.selectDictTypeById(0L)).thenReturn(sysDictTypeDO);

        when(mockSysDictDataManager.countDictDataByType("dictType")).thenReturn(0);
        when(mockSysDictTypeManager.deleteDictTypeByIds(eq("loginName"), any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysDictTypeServiceImplUnderTest.deleteDictTypeByIds("loginName", "0");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteDictTypeByIds_ThrowsBusinessException() {
        // Setup
        // Configure SysDictTypeManager.selectDictTypeById(...).
        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        when(mockSysDictTypeManager.selectDictTypeById(0L)).thenReturn(sysDictTypeDO);

        when(mockSysDictDataManager.countDictDataByType("dictType")).thenReturn(1);
        when(mockSysDictTypeManager.deleteDictTypeByIds(eq("loginName"), any(Long[].class))).thenReturn(0);

        // Run the test
        assertThrows(
                BusinessException.class, () -> sysDictTypeServiceImplUnderTest.deleteDictTypeByIds("loginName", "0"));
    }

    @Test
    public void testInsertDictType() {
        // Setup
        final SysDictType dictType = new SysDictType();
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});
        dictType.setDeleteFlag("deleteFlag");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");

        when(mockSysDictTypeManager.insertDictType(any(SysDictTypeDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDictTypeServiceImplUnderTest.insertDictType(dictType);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDictType() {
        // Setup
        final SysDictType dictType = new SysDictType();
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});
        dictType.setDeleteFlag("deleteFlag");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");

        // Configure SysDictTypeManager.selectDictTypeById(...).
        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        when(mockSysDictTypeManager.selectDictTypeById(0L)).thenReturn(sysDictTypeDO);

        when(mockSysDictDataManager.updateDictDataType("dictType", "dictType")).thenReturn(0);
        when(mockSysDictTypeManager.updateDictType(any(SysDictTypeDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDictTypeServiceImplUnderTest.updateDictType(dictType);

        // Verify the results
        assertEquals(0, result);
        verify(mockSysDictDataManager).updateDictDataType("dictType", "dictType");
    }

    @Test
    public void testCheckDictTypeUnique() {
        // Setup
        final SysDictType dict = new SysDictType();
        dict.setIds("ids");
        dict.setIdsArray(new Long[]{0L});
        dict.setDeleteFlag("deleteFlag");
        dict.setShowDataStatus("showDataStatus");
        dict.setDictId(0L);
        dict.setDictName("dictName");
        dict.setDictType("dictType");
        dict.setStatus("status");

        // Configure SysDictTypeManager.checkDictTypeUnique(...).
        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        when(mockSysDictTypeManager.checkDictTypeUnique("dictType")).thenReturn(sysDictTypeDO);

        // Run the test
        final String result = sysDictTypeServiceImplUnderTest.checkDictTypeUnique(dict);

        // Verify the results
        assertEquals("0", result);
    }

    @Test
    public void testSelectDictTypePage() {
        // Setup
        final SysDictType dictType = new SysDictType();
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});
        dictType.setDeleteFlag("deleteFlag");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");

        // Configure SysDictTypeManager.selectDictTypeList(...).
        final SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        sysDictTypeDO.setIds("ids");
        sysDictTypeDO.setIdsArray(new Long[]{0L});
        sysDictTypeDO.setShowDataStatus("showDataStatus");
        sysDictTypeDO.setDictId(0L);
        sysDictTypeDO.setDictName("dictName");
        sysDictTypeDO.setDictType("dictType");
        sysDictTypeDO.setStatus("status");
        final List<SysDictTypeDO> sysDictTypeDOS = Arrays.asList(sysDictTypeDO);
        when(mockSysDictTypeManager.selectDictTypeList(any(SysDictType.class))).thenReturn(sysDictTypeDOS);

        // Run the test
        final PageInfo<SysDictType> result = sysDictTypeServiceImplUnderTest.selectDictTypePage(dictType);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( 0,result.getTotal());
        verify(mockSysDictTypeManager, times(0)).selectDictTypeList(any());
    }

    @Test
    public void testSelectDictTypePage_SysDictTypeManagerReturnsNoItems() {
        // Setup
        final SysDictType dictType = new SysDictType();
        dictType.setIds("ids");
        dictType.setIdsArray(new Long[]{0L});
        dictType.setDeleteFlag("deleteFlag");
        dictType.setShowDataStatus("showDataStatus");
        dictType.setDictId(0L);
        dictType.setDictName("dictName");
        dictType.setDictType("dictType");
        dictType.setStatus("status");

        when(mockSysDictTypeManager.selectDictTypeList(any(SysDictType.class))).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<SysDictType> result = sysDictTypeServiceImplUnderTest.selectDictTypePage(dictType);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals(Collections.emptyList(), result.getList());
        verify(mockSysDictTypeManager, times(0)).selectDictTypeList(any());
    }
}
