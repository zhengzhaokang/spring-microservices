package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.domain.entity.SysDictDataDO;
import com.microservices.otmp.system.mapper.SysDictDataMapper;
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

@RunWith(MockitoJUnitRunner.class)
public class SysDictDataManagerImplTest {

    @Mock
    private SysDictDataMapper mockSysDictDataMapper;

    @InjectMocks
    private SysDictDataManagerImpl sysDictDataManagerImplUnderTest;

    @Test
    public void testSelectDictDataList() {
        // Setup
        final SysDictData dictData = new SysDictData();
        dictData.setIds("ids");
        dictData.setIdsArray(new Long[]{0L});
        dictData.setDeleteFlag("deleteFlag");
        dictData.setDictCode(0L);
        dictData.setDictSort(0L);
        dictData.setDictLabel("dictLabel");
        dictData.setDictValue("dictValue");
        dictData.setDictType("dictType");
        dictData.setCssClass("cssClass");
        dictData.setListClass("listClass");

        // Configure SysDictDataMapper.selectDictDataList(...).
        final SysDictDataDO sysDictDataDO = new SysDictDataDO();
        sysDictDataDO.setIds("ids");
        sysDictDataDO.setIdsArray(new Long[]{0L});
        sysDictDataDO.setDictCode(0L);
        sysDictDataDO.setDictSort(0L);
        sysDictDataDO.setDictLabel("dictLabel");
        sysDictDataDO.setDictValue("dictValue");
        sysDictDataDO.setDictType("dictType");
        sysDictDataDO.setCssClass("cssClass");
        sysDictDataDO.setListClass("listClass");
        sysDictDataDO.setIsDefault("isDefault");
        final List<SysDictDataDO> sysDictDataDOS = Arrays.asList(sysDictDataDO);
        when(mockSysDictDataMapper.selectDictDataList(any(SysDictData.class))).thenReturn(sysDictDataDOS);

        // Run the test
        final List<SysDictDataDO> result = sysDictDataManagerImplUnderTest.selectDictDataList(dictData);

        // Verify the results
    }

    @Test
    public void testSelectDictDataList_SysDictDataMapperReturnsNoItems() {
        // Setup
        final SysDictData dictData = new SysDictData();
        dictData.setIds("ids");
        dictData.setIdsArray(new Long[]{0L});
        dictData.setDeleteFlag("deleteFlag");
        dictData.setDictCode(0L);
        dictData.setDictSort(0L);
        dictData.setDictLabel("dictLabel");
        dictData.setDictValue("dictValue");
        dictData.setDictType("dictType");
        dictData.setCssClass("cssClass");
        dictData.setListClass("listClass");

        when(mockSysDictDataMapper.selectDictDataList(any(SysDictData.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDictDataDO> result = sysDictDataManagerImplUnderTest.selectDictDataList(dictData);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectDictDataByType() {
        // Setup
        // Configure SysDictDataMapper.selectDictDataByType(...).
        final SysDictDataDO sysDictDataDO = new SysDictDataDO();
        sysDictDataDO.setIds("ids");
        sysDictDataDO.setIdsArray(new Long[]{0L});
        sysDictDataDO.setDictCode(0L);
        sysDictDataDO.setDictSort(0L);
        sysDictDataDO.setDictLabel("dictLabel");
        sysDictDataDO.setDictValue("dictValue");
        sysDictDataDO.setDictType("dictType");
        sysDictDataDO.setCssClass("cssClass");
        sysDictDataDO.setListClass("listClass");
        sysDictDataDO.setIsDefault("isDefault");
        final List<SysDictDataDO> sysDictDataDOS = Arrays.asList(sysDictDataDO);
        when(mockSysDictDataMapper.selectDictDataByType("dictType")).thenReturn(sysDictDataDOS);

        // Run the test
        final List<SysDictDataDO> result = sysDictDataManagerImplUnderTest.selectDictDataByType("dictType");

        // Verify the results
    }

    @Test
    public void testSelectDictDataByType_SysDictDataMapperReturnsNoItems() {
        // Setup
        when(mockSysDictDataMapper.selectDictDataByType("dictType")).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDictDataDO> result = sysDictDataManagerImplUnderTest.selectDictDataByType("dictType");

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectDictLabel() {
        // Setup
        when(mockSysDictDataMapper.selectDictLabel("dictType", "dictValue")).thenReturn("result");

        // Run the test
        final String result = sysDictDataManagerImplUnderTest.selectDictLabel("dictType", "dictValue");

        // Verify the results
        assertEquals("result", result);
    }

    @Test
    public void testSelectDictDataById() {
        // Setup
        // Configure SysDictDataMapper.selectDictDataById(...).
        final SysDictDataDO sysDictDataDO = new SysDictDataDO();
        sysDictDataDO.setIds("ids");
        sysDictDataDO.setIdsArray(new Long[]{0L});
        sysDictDataDO.setDictCode(0L);
        sysDictDataDO.setDictSort(0L);
        sysDictDataDO.setDictLabel("dictLabel");
        sysDictDataDO.setDictValue("dictValue");
        sysDictDataDO.setDictType("dictType");
        sysDictDataDO.setCssClass("cssClass");
        sysDictDataDO.setListClass("listClass");
        sysDictDataDO.setIsDefault("isDefault");
        when(mockSysDictDataMapper.selectDictDataById(0L)).thenReturn(sysDictDataDO);

        // Run the test
        final SysDictDataDO result = sysDictDataManagerImplUnderTest.selectDictDataById(0L);

        // Verify the results
    }

    @Test
    public void testCountDictDataByType() {
        // Setup
        when(mockSysDictDataMapper.countDictDataByType("dictType")).thenReturn(0);

        // Run the test
        final int result = sysDictDataManagerImplUnderTest.countDictDataByType("dictType");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteDictDataById() {
        // Setup
        when(mockSysDictDataMapper.deleteDictDataById(0L)).thenReturn(0);

        // Run the test
        final int result = sysDictDataManagerImplUnderTest.deleteDictDataById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteDictDataByIds() {
        // Setup
        when(mockSysDictDataMapper.deleteDictDataByIds(eq("loginName"), any(String[].class))).thenReturn(0);

        // Run the test
        final int result = sysDictDataManagerImplUnderTest.deleteDictDataByIds("loginName", new String[]{"value"});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertDictData() {
        // Setup
        final SysDictDataDO dictData = new SysDictDataDO();
        dictData.setIds("ids");
        dictData.setIdsArray(new Long[]{0L});
        dictData.setDictCode(0L);
        dictData.setDictSort(0L);
        dictData.setDictLabel("dictLabel");
        dictData.setDictValue("dictValue");
        dictData.setDictType("dictType");
        dictData.setCssClass("cssClass");
        dictData.setListClass("listClass");
        dictData.setIsDefault("isDefault");

        when(mockSysDictDataMapper.insertDictData(any(SysDictDataDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDictDataManagerImplUnderTest.insertDictData(dictData);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDictData() {
        // Setup
        final SysDictDataDO dictData = new SysDictDataDO();
        dictData.setIds("ids");
        dictData.setIdsArray(new Long[]{0L});
        dictData.setDictCode(0L);
        dictData.setDictSort(0L);
        dictData.setDictLabel("dictLabel");
        dictData.setDictValue("dictValue");
        dictData.setDictType("dictType");
        dictData.setCssClass("cssClass");
        dictData.setListClass("listClass");
        dictData.setIsDefault("isDefault");

        when(mockSysDictDataMapper.updateDictData(any(SysDictDataDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDictDataManagerImplUnderTest.updateDictData(dictData);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDictDataType() {
        // Setup
        when(mockSysDictDataMapper.updateDictDataType("oldDictType", "newDictType")).thenReturn(0);

        // Run the test
        final int result = sysDictDataManagerImplUnderTest.updateDictDataType("oldDictType", "newDictType");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteByIds() {
        // Setup
        when(mockSysDictDataMapper.deleteByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysDictDataManagerImplUnderTest.deleteByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteDictDataByDictType() {
        // Setup
        when(mockSysDictDataMapper.deleteDictDataByDictType("dictType")).thenReturn(0);

        // Run the test
        final int result = sysDictDataManagerImplUnderTest.deleteDictDataByDictType("dictType");

        // Verify the results
        assertEquals(0, result);
    }
}
