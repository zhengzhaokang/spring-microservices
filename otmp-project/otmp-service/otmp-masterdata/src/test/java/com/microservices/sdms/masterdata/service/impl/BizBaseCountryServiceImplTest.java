package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseCountry;
import com.microservices.otmp.masterdata.mapper.BizBaseCountryMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseCountryServiceImpl;
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

@RunWith(MockitoJUnitRunner.Silent.class)
public class BizBaseCountryServiceImplTest {

    @Mock
    private BizBaseCountryMapper mockBizBaseCountryMapper;
    @Mock
    private RedisUtils redisUtils;

    @InjectMocks
    private BizBaseCountryServiceImpl bizBaseCountryServiceImplUnderTest;

    @Test
    public void testSelectBizBaseCountryById() {
        // Setup
        // Configure BizBaseCountryMapper.selectBizBaseCountryById(...).
        final BizBaseCountry bizBaseCountry = new BizBaseCountry();
        bizBaseCountry.setId(0L);
        bizBaseCountry.setCountryCode("countryCode");
        bizBaseCountry.setCountryName("countryName");
        bizBaseCountry.setSalesOrgCode("salesOrgCode");
        bizBaseCountry.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry.setBusinessGroup("businessGroup");
        bizBaseCountry.setStatus("status");
        bizBaseCountry.setRemark("memo");
        when(mockBizBaseCountryMapper.selectBizBaseCountryById(0L)).thenReturn(bizBaseCountry);

        // Run the test
        final BizBaseCountry result = bizBaseCountryServiceImplUnderTest.selectBizBaseCountryById(0L);
        assertEquals(bizBaseCountry, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseCountryList() {
        // Setup
        final BizBaseCountry bizBaseCountry = new BizBaseCountry();
        bizBaseCountry.setId(0L);
        bizBaseCountry.setCountryCode("countryCode");
        bizBaseCountry.setCountryName("countryName");
        bizBaseCountry.setSalesOrgCode("salesOrgCode");
        bizBaseCountry.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry.setBusinessGroup("businessGroup");
        bizBaseCountry.setStatus("status");
        bizBaseCountry.setRemark("memo");

        // Configure BizBaseCountryMapper.selectBizBaseCountryList(...).
        final BizBaseCountry bizBaseCountry1 = new BizBaseCountry();
        bizBaseCountry1.setId(0L);
        bizBaseCountry1.setCountryCode("countryCode");
        bizBaseCountry1.setCountryName("countryName");
        bizBaseCountry1.setSalesOrgCode("salesOrgCode");
        bizBaseCountry1.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry1.setBusinessGroup("businessGroup");
        bizBaseCountry1.setStatus("status");
        bizBaseCountry1.setRemark("memo");
        final List<BizBaseCountry> bizBaseCountries = Arrays.asList(bizBaseCountry1);
        when(mockBizBaseCountryMapper.selectBizBaseCountryList(any(BizBaseCountry.class))).thenReturn(bizBaseCountries);

        // Run the test
        final List<BizBaseCountry> result = bizBaseCountryServiceImplUnderTest.selectBizBaseCountryList(bizBaseCountry);
        assertEquals(bizBaseCountries, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseCountryList_BizBaseCountryMapperReturnsNoItems() {
        // Setup
        final BizBaseCountry bizBaseCountry = new BizBaseCountry();
        bizBaseCountry.setId(0L);
        bizBaseCountry.setCountryCode("countryCode");
        bizBaseCountry.setCountryName("countryName");
        bizBaseCountry.setSalesOrgCode("salesOrgCode");
        bizBaseCountry.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry.setBusinessGroup("businessGroup");
        bizBaseCountry.setStatus("status");
        bizBaseCountry.setRemark("memo");

        when(mockBizBaseCountryMapper.selectBizBaseCountryList(any(BizBaseCountry.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseCountry> result = bizBaseCountryServiceImplUnderTest.selectBizBaseCountryList(bizBaseCountry);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseCountry() {
        // Setup
        final BizBaseCountry bizBaseCountry = new BizBaseCountry();
        bizBaseCountry.setId(0L);
        bizBaseCountry.setCountryCode("countryCode");
        bizBaseCountry.setCountryName("countryName");
        bizBaseCountry.setSalesOrgCode("salesOrgCode");
        bizBaseCountry.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry.setBusinessGroup("businessGroup");
        bizBaseCountry.setStatus("status");
        bizBaseCountry.setRemark("memo");
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        when(mockBizBaseCountryMapper.insertBizBaseCountry(any(BizBaseCountry.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseCountryServiceImplUnderTest.insertBizBaseCountry(bizBaseCountry);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseCountry() {
        // Setup
        final BizBaseCountry bizBaseCountry = new BizBaseCountry();
        bizBaseCountry.setId(0L);
        bizBaseCountry.setCountryCode("countryCode");
        bizBaseCountry.setCountryName("countryName");
        bizBaseCountry.setSalesOrgCode("salesOrgCode");
        bizBaseCountry.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry.setBusinessGroup("businessGroup");
        bizBaseCountry.setStatus("status");
        bizBaseCountry.setRemark("memo");
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        when(mockBizBaseCountryMapper.updateBizBaseCountry(any(BizBaseCountry.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseCountryServiceImplUnderTest.updateBizBaseCountry(bizBaseCountry);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseCountryByIds() {
        // Setup
        when(mockBizBaseCountryMapper.updateBizBaseCountryByIds(any(Long[].class))).thenReturn(0);

        // Configure BizBaseCountryMapper.selectBizBaseCountryList(...).
        final BizBaseCountry bizBaseCountry = new BizBaseCountry();
        bizBaseCountry.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry.setIds("ids");
        bizBaseCountry.setIdsList(Arrays.asList(0L));
        bizBaseCountry.setId(0L);
        bizBaseCountry.setCountryCode("countryCode");
        bizBaseCountry.setCountryName("countryName");
        bizBaseCountry.setSalesOrgCode("salesOrgCode");
        bizBaseCountry.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry.setBusinessGroup("businessGroup");
        bizBaseCountry.setStatus("status");
        bizBaseCountry.setCreateBy("loginName");
        bizBaseCountry.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCountry> bizBaseCountries = Arrays.asList(bizBaseCountry);
        when(mockBizBaseCountryMapper.selectBizBaseCountryList(any(BizBaseCountry.class))).thenReturn(bizBaseCountries);

        // Run the test
        final int result = bizBaseCountryServiceImplUnderTest.deleteBizBaseCountryByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
        verify(redisUtils).set("master:bpc_country", bizBaseCountries, 31536000L);
    }

    @Test
    public void testDeleteBizBaseCountryById() {
        // Setup
        when(mockBizBaseCountryMapper.deleteBizBaseCountryById(0L)).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseCountryServiceImplUnderTest.deleteBizBaseCountryById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() {
        // Setup
        final BizBaseCountry bizBaseCountry = new BizBaseCountry();
        bizBaseCountry.setId(0L);
        bizBaseCountry.setCountryCode("countryCode");
        bizBaseCountry.setCountryName("countryName");
        bizBaseCountry.setSalesOrgCode("salesOrgCode");
        bizBaseCountry.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry.setBusinessGroup("businessGroup");
        bizBaseCountry.setStatus("status");
        bizBaseCountry.setRemark("memo");
        final List<BizBaseCountry> bizs = Arrays.asList(bizBaseCountry);

        // Configure BizBaseCountryMapper.selectBizBaseCountryListCheck(...).
        final BizBaseCountry bizBaseCountry1 = new BizBaseCountry();
        bizBaseCountry1.setId(0L);
        bizBaseCountry1.setCountryCode("countryCode");
        bizBaseCountry1.setCountryName("countryName");
        bizBaseCountry1.setSalesOrgCode("salesOrgCode");
        bizBaseCountry1.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry1.setBusinessGroup("businessGroup");
        bizBaseCountry1.setStatus("status");
        bizBaseCountry1.setRemark("memo");
        final List<BizBaseCountry> bizBaseCountries = Arrays.asList(bizBaseCountry1);
        when(mockBizBaseCountryMapper.selectBizBaseCountryListCheck(any(BizBaseCountry.class))).thenReturn(bizBaseCountries);

        when(mockBizBaseCountryMapper.updateBizBaseCountry(any(BizBaseCountry.class))).thenReturn(0);


        // Run the test
        final String result = bizBaseCountryServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockBizBaseCountryMapper).selectBizBaseCountryListCheck(any(BizBaseCountry.class));

    }

    @Test
    public void testImportExcel_BizBaseCountryMapperSelectBizBaseCountryListCheckReturnsNoItems() {
        // Setup
        final BizBaseCountry bizBaseCountry = new BizBaseCountry();
        bizBaseCountry.setId(0L);
        bizBaseCountry.setCountryCode("countryCode");
        bizBaseCountry.setCountryName("countryName");
        bizBaseCountry.setSalesOrgCode("salesOrgCode");
        bizBaseCountry.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry.setBusinessGroup("businessGroup");
        bizBaseCountry.setStatus("status");
        bizBaseCountry.setRemark("memo");
        final List<BizBaseCountry> bizs = Arrays.asList(bizBaseCountry);
        when(mockBizBaseCountryMapper.selectBizBaseCountryListCheck(any(BizBaseCountry.class))).thenReturn(Collections.emptyList());

        when(mockBizBaseCountryMapper.insertBizBaseCountry(any(BizBaseCountry.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseCountryServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockBizBaseCountryMapper).selectBizBaseCountryListCheck(any(BizBaseCountry.class));

    }

    @Test
    public void testSelectCountrylist() {
        // Setup
        final BizBaseCountry bizBaseCountry = new BizBaseCountry();
        bizBaseCountry.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry.setIds("ids");
        bizBaseCountry.setIdsList(Arrays.asList(0L));
        bizBaseCountry.setId(0L);
        bizBaseCountry.setCountryCode("countryCode");
        bizBaseCountry.setCountryName("countryName");
        bizBaseCountry.setSalesOrgCode("salesOrgCode");
        bizBaseCountry.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry.setBusinessGroup("businessGroup");
        bizBaseCountry.setStatus("status");
        bizBaseCountry.setCreateBy("loginName");
        bizBaseCountry.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseCountryMapper.selectCountrylist(...).
        final BizBaseCountry bizBaseCountry1 = new BizBaseCountry();
        bizBaseCountry1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry1.setIds("ids");
        bizBaseCountry1.setIdsList(Arrays.asList(0L));
        bizBaseCountry1.setId(0L);
        bizBaseCountry1.setCountryCode("countryCode");
        bizBaseCountry1.setCountryName("countryName");
        bizBaseCountry1.setSalesOrgCode("salesOrgCode");
        bizBaseCountry1.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry1.setBusinessGroup("businessGroup");
        bizBaseCountry1.setStatus("status");
        bizBaseCountry1.setCreateBy("loginName");
        bizBaseCountry1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry1.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry1.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCountry> bizBaseCountries = Arrays.asList(bizBaseCountry1);
        when(mockBizBaseCountryMapper.selectCountrylist(any(BizBaseCountry.class))).thenReturn(bizBaseCountries);

        // Run the test
        final List<BizBaseCountry> result = bizBaseCountryServiceImplUnderTest.selectCountrylist(bizBaseCountry);

        // Verify the results
        assertEquals(result.size(), bizBaseCountries.size());
    }

    @Test
    public void testCountrySelect() {
        // Setup
        final BizBaseCountry bizBaseCountry = new BizBaseCountry();
        bizBaseCountry.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry.setIds("ids");
        bizBaseCountry.setIdsList(Arrays.asList(0L));
        bizBaseCountry.setId(0L);
        bizBaseCountry.setCountryCode("countryCode");
        bizBaseCountry.setCountryName("countryName");
        bizBaseCountry.setSalesOrgCode("salesOrgCode");
        bizBaseCountry.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry.setBusinessGroup("businessGroup");
        bizBaseCountry.setStatus("status");
        bizBaseCountry.setCreateBy("loginName");
        bizBaseCountry.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseCountryMapper.countrySelect(...).
        final BizBaseCountry bizBaseCountry1 = new BizBaseCountry();
        bizBaseCountry1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry1.setIds("ids");
        bizBaseCountry1.setIdsList(Arrays.asList(0L));
        bizBaseCountry1.setId(0L);
        bizBaseCountry1.setCountryCode("countryCode");
        bizBaseCountry1.setCountryName("countryName");
        bizBaseCountry1.setSalesOrgCode("salesOrgCode");
        bizBaseCountry1.setSalesOfficeCode("salesOfficeCode");
        bizBaseCountry1.setBusinessGroup("businessGroup");
        bizBaseCountry1.setStatus("status");
        bizBaseCountry1.setCreateBy("loginName");
        bizBaseCountry1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry1.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCountry1.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCountry> bizBaseCountries = Arrays.asList(bizBaseCountry1);
        when(mockBizBaseCountryMapper.countrySelect(any(BizBaseCountry.class))).thenReturn(bizBaseCountries);

        // Run the test
        final List<BizBaseCountry> result = bizBaseCountryServiceImplUnderTest.countrySelect(bizBaseCountry);

        // Verify the results
        assertEquals(result.size(), bizBaseCountries.size());
    }

}
