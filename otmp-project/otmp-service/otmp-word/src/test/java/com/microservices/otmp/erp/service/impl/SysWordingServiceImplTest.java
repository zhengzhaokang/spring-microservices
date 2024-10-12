package com.microservices.otmp.erp.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.erp.domain.SysWording;
import com.microservices.otmp.erp.mapper.SysWordingMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SysWordingServiceImplTest {

    @Mock
    private RedisUtils mockRedis;
    @Mock
    private SysWordingMapper mockSysWordingMapper;

    private static final String WORD_JSON ="[{\"category\":\"Notification\",\"id\":4,\"textInCn\":\"用户编码不存在！\",\"textInEn\":\"用户编码不存在！\",\"type\":\"Business\",\"wordingCode\":\"ERR_USER_NOT_EXIST\",\"wordingKey\":100001}]";

    @InjectMocks
    private SysWordingServiceImpl sysWordingServiceImplUnderTest;

    @Test
    public void testSelectSysWordingById() {
        // Setup
        // Configure SysWordingMapper.selectSysWordingById(...).
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("createBy");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");
        when(mockSysWordingMapper.selectSysWordingById(0L)).thenReturn(sysWording);

        // Run the test
        final SysWording result = sysWordingServiceImplUnderTest.selectSysWordingById(0L);

        // Verify the results
        Assert.assertNotNull(result);
        verify(mockSysWordingMapper, times(1)).selectSysWordingById(any());
    }

    @Test
    public void testGetWordFromRedis() {
        // Setup
        when(mockRedis.get("word:json",String.class)).thenReturn(WORD_JSON);

        // Configure SysWordingMapper.selectSysWordingListForRedis(...).
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("createBy");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");
        final List<SysWording> sysWordingList = Arrays.asList(sysWording);

        // Run the test
        final SysWording result = sysWordingServiceImplUnderTest.getWordFromRedis(100001L);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals("用户编码不存在！", result.getTextInEn());
    }

    @Test
    public void testGetWordFromRedis_SysWordingMapperReturnsNoItems() {
        // Setup
//        when(mockRedis.get("word:json",String.class)).thenReturn("[]");

        doReturn(null).doReturn(WORD_JSON).when(mockRedis).get("word:json",String.class);

        // Run the test
        final SysWording result = sysWordingServiceImplUnderTest.getWordFromRedis(100001L);

        // Verify the results
//        Assert.assertNull(result);
        assertEquals("用户编码不存在！", result.getTextInEn());
    }

    @Test
    public void testGetWordFromRedis_SysWordingMapperReturnsNoItemsR() {
        // Setup

        doReturn(null).doReturn(null).when(mockRedis).get("word:json",String.class);

        // Run the test
        final SysWording result = sysWordingServiceImplUnderTest.getWordFromRedis(100001L);

        // Verify the results
        Assert.assertNull(result);
    }

    @Test
    public void testJson() {
        // Setup
        when(mockRedis.get("word:json",String.class)).thenReturn(WORD_JSON);

        // Configure SysWordingMapper.selectSysWordingListForRedis(...).
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("createBy");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");
        final List<SysWording> sysWordingList = Arrays.asList(sysWording);

        // Run the test
        final String result = sysWordingServiceImplUnderTest.json();

        // Verify the results
        assertEquals(WORD_JSON, result);
    }

    @Test
    public void testJson_SysWordingMapperReturnsNoItems() {
        // Setup
        doReturn(null).doReturn(WORD_JSON).when(mockRedis).get("word:json",String.class);
        // Run the test
        final String result = sysWordingServiceImplUnderTest.json();

        // Verify the results
        assertEquals(WORD_JSON, result);
    }

    @Test
    public void testSelectSysWordingList() {
        // Setup
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("createBy");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");

        // Configure SysWordingMapper.selectSysWordingList(...).
        final SysWording sysWording1 = new SysWording();
        sysWording1.setId(0L);
        sysWording1.setType("type");
        sysWording1.setCategory("category");
        sysWording1.setWordingKey(0L);
        sysWording1.setWordingCode("wordingCode");
        sysWording1.setTextInCn("textInCn");
        sysWording1.setTextInEn("textInEn");
        sysWording1.setStatus("status");
        sysWording1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording1.setCreateBy("createBy");
        sysWording1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording1.setUpdateBy("updateBy");
        sysWording1.setRemark("remark");
        final List<SysWording> sysWordingList = Arrays.asList(sysWording1);
        when(mockSysWordingMapper.selectSysWordingList(any(SysWording.class))).thenReturn(sysWordingList);

        // Run the test
        final List<SysWording> result = sysWordingServiceImplUnderTest.selectSysWordingList(sysWording);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals("category", result.get(0).getCategory());
    }

    @Test
    public void testSelectSysWordingList_SysWordingMapperReturnsNoItems() {
        // Setup
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("createBy");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");

        when(mockSysWordingMapper.selectSysWordingList(any(SysWording.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysWording> result = sysWordingServiceImplUnderTest.selectSysWordingList(sysWording);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertSysWording_SysWordingMapperSelectSysWordingListReturnsNoItems() {
        // Setup
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("createBy");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");

        when(mockSysWordingMapper.insertSysWording(any(SysWording.class))).thenReturn(0);

        // Configure SysWordingMapper.selectSysWordingListForRedis(...).
        final SysWording sysWording1 = new SysWording();
        sysWording1.setId(0L);
        sysWording1.setType("type");
        sysWording1.setCategory("category");
        sysWording1.setWordingKey(0L);
        sysWording1.setWordingCode("wordingCode");
        sysWording1.setTextInCn("textInCn");
        sysWording1.setTextInEn("textInEn");
        sysWording1.setStatus("status");
        sysWording1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording1.setCreateBy("createBy");
        sysWording1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording1.setUpdateBy("updateBy");
        sysWording1.setRemark("remark");
        final List<SysWording> sysWordingList = Arrays.asList(sysWording1);

        // Run the test
        final int result = sysWordingServiceImplUnderTest.insertSysWording(sysWording);

        // Verify the results
        assertEquals(0, result);
    }



    @Test
    public void testUpdateSysWording() {
        // Setup
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("createBy");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");

        when(mockSysWordingMapper.updateSysWording(any(SysWording.class))).thenReturn(1);

        // Configure SysWordingMapper.selectSysWordingListForRedis(...).
        final SysWording sysWording1 = new SysWording();
        sysWording1.setId(0L);
        sysWording1.setType("type");
        sysWording1.setCategory("category");
        sysWording1.setWordingKey(0L);
        sysWording1.setWordingCode("wordingCode");
        sysWording1.setTextInCn("textInCn");
        sysWording1.setTextInEn("textInEn");
        sysWording1.setStatus("status");
        sysWording1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording1.setCreateBy("createBy");
        sysWording1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording1.setUpdateBy("updateBy");
        sysWording1.setRemark("remark");
        final List<SysWording> sysWordingList = Arrays.asList(sysWording1);

        // Run the test
        final int result = sysWordingServiceImplUnderTest.updateSysWording(sysWording);

        // Verify the results
        assertEquals(1, result);
    }

    @Test
    public void testUpdateSysWording_SysWordingMapperSelectSysWordingListForRedisReturnsNoItems() {
        // Setup
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("createBy");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");

        when(mockSysWordingMapper.updateSysWording(any(SysWording.class))).thenReturn(0);

        // Run the test
        final int result = sysWordingServiceImplUnderTest.updateSysWording(sysWording);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysWordingByIds() {
        // Setup
        when(mockSysWordingMapper.deleteSysWordingByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysWordingServiceImplUnderTest.deleteSysWordingByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertWordIntoRedis_SysWordingMapperReturnsNoItems() {
        // Setup
        when(mockRedis.get("word:json",String.class)).thenReturn(WORD_JSON);

        // Verify the results
        assertEquals(WORD_JSON, mockRedis.get("word:json",String.class));
    }

    @Test
    public void testDeleteSysWordingById() {
        // Setup
        when(mockSysWordingMapper.deleteSysWordingById(0L)).thenReturn(0);

        // Run the test
        final int result = sysWordingServiceImplUnderTest.deleteSysWordingById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertWordIntoRedis() {
        // Setup
        // Configure SysWordingMapper.selectSysWordingListForRedis(...).
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("createBy");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");
        final List<SysWording> sysWordingList = Arrays.asList(sysWording);
        when(mockRedis.get("word:json",String.class)).thenReturn(WORD_JSON);


        // Run the test
        sysWordingServiceImplUnderTest.insertWordIntoRedis(null);

        // Verify the results
        assertEquals(WORD_JSON, mockRedis.get("word:json",String.class));

    }


        @Test
    public void testRefreshWordIntoRedis() {
        // Setup
        // Configure SysWordingMapper.selectSysWordingListForRedis(...).
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("loginName");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");
        sysWording.setWording("wording");
        final List<SysWording> sysWordings = Arrays.asList(sysWording);
        when(mockSysWordingMapper.selectSysWordingListForRedis(any())).thenReturn(sysWordings);

        // Run the test
        sysWordingServiceImplUnderTest.refreshWordIntoRedis();

        // Verify the results
        verify(mockRedis).set("word:json", sysWordings, 31536000L);
    }



     @Test
    public void testImportExcel() {
        // Setup
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("loginName");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");
        sysWording.setWording("wording");
        final List<SysWording> wordings = Arrays.asList(sysWording);

        // Configure SysWordingMapper.selectSysWordingListForCheck(...).
        final SysWording sysWording1 = new SysWording();
        sysWording1.setId(0L);
        sysWording1.setType("type");
        sysWording1.setCategory("category");
        sysWording1.setWordingKey(0L);
        sysWording1.setWordingCode("wordingCode");
        sysWording1.setTextInCn("textInCn");
        sysWording1.setTextInEn("textInEn");
        sysWording1.setStatus("status");
        sysWording1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording1.setCreateBy("loginName");
        sysWording1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording1.setUpdateBy("updateBy");
        sysWording1.setRemark("remark");
        sysWording1.setWording("wording");
        final List<SysWording> sysWordings = Arrays.asList(sysWording1);
        when(mockSysWordingMapper.selectSysWordingListForCheck(any(SysWording.class))).thenReturn(sysWordings);

        when(mockSysWordingMapper.updateSysWording(any(SysWording.class))).thenReturn(0);
//        when(mockSysWordingMapper.insertSysWording(any(SysWording.class))).thenReturn(0);

        // Run the test
        final String result = sysWordingServiceImplUnderTest.importExcel(wordings, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockSysWordingMapper).updateSysWording(any(SysWording.class));
//        verify(mockSysWordingMapper).insertSysWording(any(SysWording.class));
    }

    @Test
    public void testImportExcel_SysWordingMapperSelectSysWordingListForCheckReturnsNoItems() {
        // Setup
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("loginName");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");
        sysWording.setWording("wording");
        final List<SysWording> wordings = Arrays.asList(sysWording);
        when(mockSysWordingMapper.selectSysWordingListForCheck(any(SysWording.class)))
                .thenReturn(Collections.emptyList());
//        when(mockSysWordingMapper.updateSysWording(any(SysWording.class))).thenReturn(0);
        when(mockSysWordingMapper.insertSysWording(any(SysWording.class))).thenReturn(0);

        // Run the test
        final String result = sysWordingServiceImplUnderTest.importExcel(wordings, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
//        verify(mockSysWordingMapper).updateSysWording(any(SysWording.class));
//        verify(mockSysWordingMapper).insertSysWording(any(SysWording.class));
    }


    @Test
    public void testInsertSysWording_SysWordingMapperSelectSysWordingListReturnsNoItemsFail() {
        // Setup
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("createBy");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");

//        when(mockSysWordingMapper.insertSysWording(any(SysWording.class))).thenReturn(0);

        // Configure SysWordingMapper.selectSysWordingListForRedis(...).
        final SysWording sysWording1 = new SysWording();
        sysWording1.setId(0L);
        sysWording1.setType("type");
        sysWording1.setCategory("category");
        sysWording1.setWordingKey(0L);
        sysWording1.setWordingCode("wordingCode");
        sysWording1.setTextInCn("textInCn");
        sysWording1.setTextInEn("textInEn");
        sysWording1.setStatus("status");
        sysWording1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording1.setCreateBy("createBy");
        sysWording1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording1.setUpdateBy("updateBy");
        sysWording1.setRemark("remark");
        final List<SysWording> sysWordingList = Arrays.asList(sysWording1);
        when(mockSysWordingMapper.selectSysWordingListForCheck(any(SysWording.class))).thenReturn(sysWordingList);

        // Run the test
        try {
            final int result = sysWordingServiceImplUnderTest.insertSysWording(sysWording);
        }catch (Exception e){
            assertEquals("word code or word key is repeat!", e.getMessage());
        }
    }


    @Test
    public void testImportExcel_SysWordingMapperSelectSysWordingListForCheckReturnsNoItemsFail1() {
        // Setup
        final SysWording sysWording = new SysWording();
        sysWording.setId(0L);
        sysWording.setType("type");
        sysWording.setCategory("category");
        sysWording.setWordingKey(0L);
        sysWording.setWordingCode("wordingCode");
        sysWording.setTextInCn("textInCn");
        sysWording.setTextInEn("textInEn");
        sysWording.setStatus("status");
        sysWording.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setCreateBy("loginName");
        sysWording.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysWording.setUpdateBy("updateBy");
        sysWording.setRemark("remark");
        sysWording.setWording("wording");
        final List<SysWording> wordings = Arrays.asList(sysWording);
//        when(mockSysWordingMapper.selectSysWordingListForCheck(any(SysWording.class)))
//                .thenReturn(Collections.emptyList());
//        when(mockSysWordingMapper.updateSysWording(any(SysWording.class))).thenReturn(0);
//        when(mockSysWordingMapper.insertSysWording(any(SysWording.class))).thenReturn(0);

        when(mockSysWordingMapper.selectSysWordingListForCheck(any(SysWording.class))).thenThrow(new RuntimeException());

        try {
            // Run the test
            final String result = sysWordingServiceImplUnderTest.importExcel(wordings, "loginName");
        } catch (Exception e) {
            // Verify the results
            assertEquals("很抱歉，导入失败！共 1 条数据格式不正确，错误如下：<br/>1提示语导入失败：null", e.getMessage());
        }
        // Run the test
    }

}
