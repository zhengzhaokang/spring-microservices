package com.microservices.sdms.masterdata.service.impl;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseComBiz;
import com.microservices.otmp.masterdata.mapper.BizBaseComBizMapper;
import com.microservices.otmp.masterdata.service.IBizBaseRegionMarketService;
import com.microservices.otmp.masterdata.service.impl.BizBaseComBizServiceImpl;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.feign.RemoteDictService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseComBizServiceImplTest {

    @Mock
    private BizBaseComBizMapper mockBizBaseComBizMapper;
    @Mock
    private RemoteDictService remoteDictService;

    @InjectMocks
    private BizBaseComBizServiceImpl bizBaseComBizServiceImplUnderTest;

    @Test
    public void testSelectBizBaseComBizById() {
        // Setup
        // Configure BizBaseComBizMapper.selectBizBaseComBizById(...).
        final BizBaseComBiz bizBaseComBiz = new BizBaseComBiz();
        bizBaseComBiz.setIds(Arrays.asList(0));
        bizBaseComBiz.setPageSize(0);
        bizBaseComBiz.setPageNum(0);
        bizBaseComBiz.setId(0);
        bizBaseComBiz.setGeoCode("geoCode");
        bizBaseComBiz.setBizTable("bizTable");
        bizBaseComBiz.setBizCode("bizCode");
        bizBaseComBiz.setBizName("bizName");
        bizBaseComBiz.setStatus("status");
        bizBaseComBiz.setRemark("memo");
        when(mockBizBaseComBizMapper.selectBizBaseComBizById(0)).thenReturn(bizBaseComBiz);

        // Run the test
        final BizBaseComBiz result = bizBaseComBizServiceImplUnderTest.selectBizBaseComBizById(0);
        assertEquals(bizBaseComBiz, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseComBizList() {
        // Setup
        final BizBaseComBiz bizBaseComBiz = new BizBaseComBiz();
        bizBaseComBiz.setIds(Arrays.asList(0));
        bizBaseComBiz.setPageSize(0);
        bizBaseComBiz.setPageNum(0);
        bizBaseComBiz.setId(0);
        bizBaseComBiz.setGeoCode("geoCode");
        bizBaseComBiz.setBizTable("bizTable");
        bizBaseComBiz.setBizCode("bizCode");
        bizBaseComBiz.setBizName("bizName");
        bizBaseComBiz.setStatus("status");
        bizBaseComBiz.setRemark("memo");

        // Configure BizBaseComBizMapper.selectBizBaseDataList(...).
        final BizBaseComBiz bizBaseComBiz1 = new BizBaseComBiz();
        bizBaseComBiz1.setIds(Arrays.asList(0));
        bizBaseComBiz1.setPageSize(0);
        bizBaseComBiz1.setPageNum(0);
        bizBaseComBiz1.setId(0);
        bizBaseComBiz1.setGeoCode("geoCode");
        bizBaseComBiz1.setBizTable("bizTable");
        bizBaseComBiz1.setBizCode("bizCode");
        bizBaseComBiz1.setBizName("bizName");
        bizBaseComBiz1.setStatus("status");
        bizBaseComBiz1.setRemark("memo");
        final List<BizBaseComBiz> bizs = Arrays.asList(bizBaseComBiz1);
        when(   remoteDictService.getDictSelect(any())).thenReturn(new ArrayList<>());
        when(mockBizBaseComBizMapper.selectBizBaseDataList(any(BizBaseComBiz.class))).thenReturn(bizs);

        // Run the test
        final List<BizBaseComBiz> result = bizBaseComBizServiceImplUnderTest.selectBizBaseComBizList(bizBaseComBiz);
        assertEquals(bizs, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseComBizList_BizBaseComBizMapperReturnsNoItems() {
        // Setup
        final BizBaseComBiz bizBaseComBiz = new BizBaseComBiz();
        bizBaseComBiz.setIds(Arrays.asList(0));
        bizBaseComBiz.setPageSize(0);
        bizBaseComBiz.setPageNum(0);
        bizBaseComBiz.setId(0);
        bizBaseComBiz.setGeoCode("geoCode");
        bizBaseComBiz.setBizTable("bizTable");
        bizBaseComBiz.setBizCode("bizCode");
        bizBaseComBiz.setBizName("bizName");
        bizBaseComBiz.setStatus("status");
        bizBaseComBiz.setRemark("memo");

        when(mockBizBaseComBizMapper.selectBizBaseDataList(any(BizBaseComBiz.class))).thenReturn(Collections.emptyList());
        when(   remoteDictService.getDictSelect(any())).thenReturn(new ArrayList<>());

        // Run the test
        final List<BizBaseComBiz> result = bizBaseComBizServiceImplUnderTest.selectBizBaseComBizList(bizBaseComBiz);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetComBiz() {
        // Setup
        // Configure BizBaseComBizMapper.getComBiz(...).
        final BizBaseComBiz bizBaseComBiz = new BizBaseComBiz();
        bizBaseComBiz.setIds(Arrays.asList(0));
        bizBaseComBiz.setPageSize(0);
        bizBaseComBiz.setPageNum(0);
        bizBaseComBiz.setId(0);
        bizBaseComBiz.setGeoCode("geoCode");
        bizBaseComBiz.setBizTable("bizTable");
        bizBaseComBiz.setBizCode("bizCode");
        bizBaseComBiz.setBizName("bizName");
        bizBaseComBiz.setStatus("status");
        bizBaseComBiz.setRemark("memo");
        final List<BizBaseComBiz> bizs = Arrays.asList(bizBaseComBiz);
        when(mockBizBaseComBizMapper.getComBiz()).thenReturn(bizs);

        // Run the test
        final List<BizBaseComBiz> result = bizBaseComBizServiceImplUnderTest.getComBiz();
        assertEquals(bizs, result);
        // Verify the results
    }

    @Test
    public void testGetComBiz_BizBaseComBizMapperReturnsNoItems() {
        // Setup
        when(mockBizBaseComBizMapper.getComBiz()).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseComBiz> result = bizBaseComBizServiceImplUnderTest.getComBiz();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseComBiz() {
        // Setup
        final BizBaseComBiz bizBaseComBiz = new BizBaseComBiz();
        bizBaseComBiz.setIds(Arrays.asList(0));
        bizBaseComBiz.setPageSize(0);
        bizBaseComBiz.setPageNum(0);
        bizBaseComBiz.setId(0);
        bizBaseComBiz.setGeoCode("geoCode");
        bizBaseComBiz.setBizTable("biz_base_bw_bu");
        bizBaseComBiz.setBizCode("bizCode");
        bizBaseComBiz.setBizName("bizName");
        bizBaseComBiz.setStatus("status");
        bizBaseComBiz.setRemark("memo");

        when(mockBizBaseComBizMapper.insertBizBaseData(any(),any(BizBaseComBiz.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseComBizServiceImplUnderTest.insertBizBaseComBiz(bizBaseComBiz);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseComBiz_BizBaseComBizMapperSelectBizBaseDataListCheckReturnsNoItems() {
        // Setup
        final BizBaseComBiz bizBaseComBiz = new BizBaseComBiz();
        bizBaseComBiz.setIds(Arrays.asList(0));
        bizBaseComBiz.setPageSize(0);
        bizBaseComBiz.setPageNum(0);
        bizBaseComBiz.setId(0);
        bizBaseComBiz.setGeoCode("geoCode");
        bizBaseComBiz.setBizTable("biz_base_bpc_bu");
        bizBaseComBiz.setBizCode("bizCode");
        bizBaseComBiz.setBizName("bizName");
        bizBaseComBiz.setStatus("status");
        bizBaseComBiz.setRemark("memo");

        when(mockBizBaseComBizMapper.selectBizBaseDataListCheck(any(),any(BizBaseComBiz.class))).thenReturn(Collections.emptyList());
        when(mockBizBaseComBizMapper.updateBizBaseData(any(),any(BizBaseComBiz.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseComBizServiceImplUnderTest.updateBizBaseComBiz(bizBaseComBiz);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseComBizByIds() {
        // Setup
        when(mockBizBaseComBizMapper.deleteBizBaseComBizByIds(any(),any(),any(String[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseComBizServiceImplUnderTest.deleteBizBaseComBizByIds("1","2","ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseComBizById() {
        // Setup
        when(mockBizBaseComBizMapper.deleteBizBaseComBizById(0)).thenReturn(0);

        // Run the test
        final int result = bizBaseComBizServiceImplUnderTest.deleteBizBaseComBizById(0);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() {
        // Setup
        final BizBaseComBiz bizBaseComBiz = new BizBaseComBiz();
        bizBaseComBiz.setIds(Arrays.asList(0));
        bizBaseComBiz.setPageSize(0);
        bizBaseComBiz.setPageNum(0);
        bizBaseComBiz.setId(0);
        bizBaseComBiz.setGeoCode("geoCode");
        bizBaseComBiz.setBizTable("bizTable");
        bizBaseComBiz.setBizCode("bizCode");
        bizBaseComBiz.setBizName("bizName");
        bizBaseComBiz.setStatus("status");
        bizBaseComBiz.setRemark("memo");
        final List<BizBaseComBiz> bizs = Arrays.asList(bizBaseComBiz);

        // Configure BizBaseComBizMapper.selectBizBaseDataListCheck(...).
        final BizBaseComBiz bizBaseComBiz1 = new BizBaseComBiz();
        bizBaseComBiz1.setIds(Arrays.asList(0));
        bizBaseComBiz1.setPageSize(0);
        bizBaseComBiz1.setPageNum(0);
        bizBaseComBiz1.setId(0);
        bizBaseComBiz1.setGeoCode("geoCode");
        bizBaseComBiz1.setBizTable("bizTable");
        bizBaseComBiz1.setBizCode("bizCode");
        bizBaseComBiz1.setBizName("bizName");
        bizBaseComBiz1.setStatus("status");
        bizBaseComBiz1.setRemark("memo");
        final List<BizBaseComBiz> bizs1 = Arrays.asList(bizBaseComBiz1);
        when(mockBizBaseComBizMapper.selectBizBaseDataList(any(BizBaseComBiz.class))).thenReturn(bizs1);

        when(mockBizBaseComBizMapper.updateBizBaseData(any(),any(BizBaseComBiz.class))).thenReturn(0);


        // Run the test
        final String result = bizBaseComBizServiceImplUnderTest.importExcel(bizs, "bizTable", "name");

        // Verify the results
         verify(mockBizBaseComBizMapper).selectBizBaseDataList(any(BizBaseComBiz.class));
    }
    @Autowired
    private IBizBaseRegionMarketService bizBaseRegionMarketService;
    @Mock
    private RedisUtils redisUtils;

    @Test
    public void testSetSingleToRedis() {
        // Setup
        final BizBaseComBiz bizBaseComBiz = new BizBaseComBiz();
        bizBaseComBiz.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseComBiz.setIds(Arrays.asList(0));
        bizBaseComBiz.setPageSize(0);
        bizBaseComBiz.setPageNum(0);
        bizBaseComBiz.setId(0);
        bizBaseComBiz.setGeoCode("geoCode");
        bizBaseComBiz.setBizTable("tableName");
        bizBaseComBiz.setBizCode("bizCode");
        bizBaseComBiz.setBizName("bizName");
        bizBaseComBiz.setStatus("status");
        bizBaseComBiz.setRemark("remark");
        bizBaseComBiz.setCreateBy("name");
        bizBaseComBiz.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseComBiz.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseComBiz.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseComBizMapper.selectBizBaseDataList(...).
        final BizBaseComBiz comBiz = new BizBaseComBiz();
        comBiz.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comBiz.setIds(Arrays.asList(0));
        comBiz.setPageSize(0);
        comBiz.setPageNum(0);
        comBiz.setId(0);
        comBiz.setGeoCode("geoCode");
        comBiz.setBizTable("tableName");
        comBiz.setBizCode("bizCode");
        comBiz.setBizName("bizName");
        comBiz.setStatus("status");
        comBiz.setRemark("remark");
        comBiz.setCreateBy("name");
        comBiz.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comBiz.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comBiz.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseComBiz> bizBaseComBizs = Arrays.asList(comBiz);
        when(mockBizBaseComBizMapper.selectBizBaseDataList(any(BizBaseComBiz.class))).thenReturn(bizBaseComBizs);

        // Configure RemoteDictService.getDictSelect(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        sysDictData1.setIsDefault("isDefault");
        sysDictData1.setStatus("status");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(remoteDictService.getDictSelect("com_biz_table")).thenReturn(sysDictData);

        // Run the test
        bizBaseComBizServiceImplUnderTest.setSingleToRedis(bizBaseComBiz, "tableName", "key");

        // Verify the results
        verify(redisUtils).set("master:key", Arrays.asList("bizCode"), 31536000L);
        assertEquals("ids", sysDictData1.getIds());
    }

}
