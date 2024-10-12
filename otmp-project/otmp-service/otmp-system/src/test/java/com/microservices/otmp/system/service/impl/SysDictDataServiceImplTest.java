package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.system.common.GetLoginUserUtil;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.entity.SysDictDataDO;
import com.microservices.otmp.system.manager.SysDictDataManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SysDictDataServiceImplTest {

    @Mock
    private SysDictDataManager mockSysDictDataManager;

    @Mock
    GetLoginUserUtil mockLoginUserUtil;

    @Mock
    private RedisUtils mockrRdis;

    @InjectMocks
    private SysDictDataServiceImpl sysDictDataServiceImplUnderTest;

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
        dictData.setIsDefault("isDefault");
        dictData.setStatus("status");

        // Configure SysDictDataManager.selectDictDataList(...).
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
        sysDictDataDO.setStatus("status");
        final List<SysDictDataDO> sysDictDataDOS = Arrays.asList(sysDictDataDO);
        when(mockSysDictDataManager.selectDictDataList(any(SysDictData.class))).thenReturn(sysDictDataDOS);

        // Run the test
        final List<SysDictData> result = sysDictDataServiceImplUnderTest.selectDictDataList(dictData);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( 1,result.size());
        verify(mockSysDictDataManager, times(1)).selectDictDataList(any());
    }

    @Test
    public void testSelectDictDataList_SysDictDataManagerReturnsNoItems() {
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
        dictData.setIsDefault("isDefault");
        dictData.setStatus("status");

        when(mockSysDictDataManager.selectDictDataList(any(SysDictData.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDictData> result = sysDictDataServiceImplUnderTest.selectDictDataList(dictData);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectDictDataByType() {
        // Setup
        // Configure SysDictDataManager.selectDictDataByType(...).
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
        sysDictDataDO.setStatus("status");
        final List<SysDictDataDO> sysDictDataDOS = Arrays.asList(sysDictDataDO);
        when(mockSysDictDataManager.selectDictDataByType("dictType")).thenReturn(sysDictDataDOS);

        // Run the test
        final List<SysDictData> result = sysDictDataServiceImplUnderTest.selectDictDataByType("dictType");

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( 1,result.size());
        verify(mockSysDictDataManager, times(1)).selectDictDataByType(any());
    }

    @Test
    public void testSelectDictDataByType_SysDictDataManagerReturnsNoItems() {
        // Setup
        when(mockSysDictDataManager.selectDictDataByType("dictType")).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDictData> result = sysDictDataServiceImplUnderTest.selectDictDataByType("dictType");

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectDictLabel() {
        // Setup
        when(mockSysDictDataManager.selectDictLabel("dictType", "dictValue")).thenReturn("result");

        // Run the test
        final String result = sysDictDataServiceImplUnderTest.selectDictLabel("dictType", "dictValue");

        // Verify the results
        assertEquals("result", result);
    }

    @Test
    public void testFilterDataScopeNullUser() {
        List<SysDictDataDO> dictDataDOS=new ArrayList<>();
        SysDictDataDO sysDictDataDO=new SysDictDataDO();
        sysDictDataDO.setDictCode(10l);
        sysDictDataDO.setDictLabel("test1");
        sysDictDataDO.setDictValue("test1");
        sysDictDataDO.setDictType("geo");
        SysDictDataDO sysDictDataDO1=new SysDictDataDO();
        sysDictDataDO1.setDictCode(11l);
        sysDictDataDO1.setDictLabel("test11");
        sysDictDataDO1.setDictValue("test11");
        sysDictDataDO1.setDictType("geo");
        dictDataDOS.add(sysDictDataDO);

        SysUser sysUser =new  SysUser();
        when(mockLoginUserUtil.getLoginUser()).thenReturn(sysUser);
        // Run the test
        final List<SysDictDataDO> result= sysDictDataServiceImplUnderTest.filterDataScope("geo",dictDataDOS);


    }

    @Test
    public void testFilterDataScopeBg() {
        List<SysDictDataDO> dictDataDOS=new ArrayList<>();
        SysDictDataDO sysDictDataDO=new SysDictDataDO();
        sysDictDataDO.setDictCode(10l);
        sysDictDataDO.setDictLabel("test1");
        sysDictDataDO.setDictValue("test1");
        sysDictDataDO.setDictType("business_group");
        SysDictDataDO sysDictDataDO1=new SysDictDataDO();
        sysDictDataDO1.setDictCode(11l);
        sysDictDataDO1.setDictLabel("test11");
        sysDictDataDO1.setDictValue("test11");
        sysDictDataDO1.setDictType("business_group");
        dictDataDOS.add(sysDictDataDO1);

        SysDictDataDO sysDictDataDO3=new SysDictDataDO();
        sysDictDataDO3.setDictCode(12l);
        sysDictDataDO3.setDictLabel("ISG");
        sysDictDataDO3.setDictValue("ISG");
        sysDictDataDO3.setDictType("business_group");
        dictDataDOS.add(sysDictDataDO3);

        SysUser sysUser =new  SysUser();
        sysUser.setUserName("admin");
        sysUser.setLoginName("admin");
        List< SysUserDataScope > sysUserDataScopeList=new ArrayList<>();
        SysUserDataScope sysUserDataScope=new SysUserDataScope();
        sysUserDataScope.setDataScopeKey("business_group");
        sysUserDataScope.setDataScopeValue("ISG");
        sysUserDataScopeList.add(sysUserDataScope);
        sysUser.setSysUserDataScopeList(sysUserDataScopeList);
        when(mockLoginUserUtil.getLoginUser()).thenReturn(sysUser);
        // Run the test
        final List<SysDictDataDO> result= sysDictDataServiceImplUnderTest.filterDataScope("business_group",dictDataDOS);


    }

    @Test
    public void testFilterDataScopeGeo() {
        List<SysDictDataDO> dictDataDOS=new ArrayList<>();
        SysDictDataDO sysDictDataDO=new SysDictDataDO();
        sysDictDataDO.setDictCode(10l);
        sysDictDataDO.setDictLabel("test1");
        sysDictDataDO.setDictValue("test1");
        sysDictDataDO.setDictType("geo_code");
        SysDictDataDO sysDictDataDO1=new SysDictDataDO();
        sysDictDataDO1.setDictCode(11l);
        sysDictDataDO1.setDictLabel("test11");
        sysDictDataDO1.setDictValue("test11");
        sysDictDataDO1.setDictType("geo_code");
        dictDataDOS.add(sysDictDataDO1);

        SysDictDataDO sysDictDataDO3=new SysDictDataDO();
        sysDictDataDO3.setDictCode(12l);
        sysDictDataDO3.setDictLabel("AP");
        sysDictDataDO3.setDictValue("AP");
        sysDictDataDO3.setDictType("geo_code");
        dictDataDOS.add(sysDictDataDO3);

        SysUser sysUser =new  SysUser();
        sysUser.setUserName("admin");
        sysUser.setLoginName("admin");
        List< SysUserDataScope > sysUserDataScopeList=new ArrayList<>();
        SysUserDataScope sysUserDataScope=new SysUserDataScope();
        sysUserDataScope.setDataScopeKey("geo_code");
        sysUserDataScope.setDataScopeValue("AP");
        sysUserDataScopeList.add(sysUserDataScope);
        sysUser.setSysUserDataScopeList(sysUserDataScopeList);
        when(mockLoginUserUtil.getLoginUser()).thenReturn(sysUser);
        // Run the test
        final List<SysDictDataDO> result= sysDictDataServiceImplUnderTest.filterDataScope("geo_code",dictDataDOS);


    }


    @Test
    public void testSelectDictDataById() {
        // Setup
        // Configure SysDictDataManager.selectDictDataById(...).
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
        sysDictDataDO.setStatus("status");
        when(mockSysDictDataManager.selectDictDataById(0L)).thenReturn(sysDictDataDO);

        // Run the test
        final SysDictData result = sysDictDataServiceImplUnderTest.selectDictDataById(0L);

        // Verify the results
        Assert.assertNotNull(result);
        verify(mockSysDictDataManager, times(1)).selectDictDataById(any());
    }

    @Test
    public void testDeleteDictDataById() {
        // Setup
        when(mockSysDictDataManager.deleteDictDataById(0L)).thenReturn(0);

        // Run the test
        final int result = sysDictDataServiceImplUnderTest.deleteDictDataById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteDictDataByIds() {
        // Setup
        when(mockSysDictDataManager.deleteDictDataByIds(eq("loginName"), any(String[].class))).thenReturn(0);

        // Run the test
        final int result = sysDictDataServiceImplUnderTest.deleteDictDataByIds("loginName", "ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertDictData() {
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
        dictData.setIsDefault("isDefault");
        dictData.setStatus("status");

        when(mockSysDictDataManager.insertDictData(any(SysDictDataDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDictDataServiceImplUnderTest.insertDictData(dictData);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDictData() {
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
        dictData.setIsDefault("isDefault");
        dictData.setStatus("status");

        when(mockSysDictDataManager.updateDictData(any(SysDictDataDO.class))).thenReturn(0);

        // Run the test
        final int result = sysDictDataServiceImplUnderTest.updateDictData(dictData);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectDictDataPage() {
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
        dictData.setIsDefault("isDefault");
        dictData.setStatus("status");

        // Configure SysDictDataManager.selectDictDataList(...).
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
        sysDictDataDO.setStatus("status");
        final List<SysDictDataDO> sysDictDataDOS = Arrays.asList(sysDictDataDO);
        when(mockSysDictDataManager.selectDictDataList(any(SysDictData.class))).thenReturn(sysDictDataDOS);

        // Run the test
        final PageInfo<SysDictData> result = sysDictDataServiceImplUnderTest.selectDictDataPage(dictData);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( 1, result.getTotal());
        verify(mockSysDictDataManager, times(1)).selectDictDataList(any());
    }

    @Test
    public void testSelectDictDataPage_SysDictDataManagerReturnsNoItems() {
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
        dictData.setIsDefault("isDefault");
        dictData.setStatus("status");

        when(mockSysDictDataManager.selectDictDataList(any(SysDictData.class))).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<SysDictData> result = sysDictDataServiceImplUnderTest.selectDictDataPage(dictData);

        // Verify the results
        Assert.assertNotNull(result);
        Assert.assertEquals( 0, result.getTotal());
        verify(mockSysDictDataManager, times(1)).selectDictDataList(any());
    }
}
