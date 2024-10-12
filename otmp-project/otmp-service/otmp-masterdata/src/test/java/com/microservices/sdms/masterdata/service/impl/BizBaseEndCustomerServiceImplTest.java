package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseEndCustomer;
import com.microservices.otmp.masterdata.mapper.BizBaseEndCustomerMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseEndCustomerServiceImpl;
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
public class BizBaseEndCustomerServiceImplTest {

    @Mock
    private BizBaseEndCustomerMapper mockBizBaseEndCustomerMapper;
    @Mock
    private RedisUtils redisUtils;
    @InjectMocks
    private BizBaseEndCustomerServiceImpl bizBaseEndCustomerServiceImplUnderTest;

    @Test
    public void testSelectBizBaseEndCustomerById() {
        // Setup
        // Configure BizBaseEndCustomerMapper.selectBizBaseEndCustomerById(...).
        final BizBaseEndCustomer bizBaseEndCustomer = new BizBaseEndCustomer();
        bizBaseEndCustomer.setId(0L);
        bizBaseEndCustomer.setEndCustomerId("endCustomerId");
        bizBaseEndCustomer.setEndCustomerName("endCustomerName");
        bizBaseEndCustomer.setRegionMarketCode("regionMarketCode");
        bizBaseEndCustomer.setCountryCode("country");
        bizBaseEndCustomer.setStatus("status");
        bizBaseEndCustomer.setRemark("memo");
        when(mockBizBaseEndCustomerMapper.selectBizBaseEndCustomerById(0L)).thenReturn(bizBaseEndCustomer);

        // Run the test
        final BizBaseEndCustomer result = bizBaseEndCustomerServiceImplUnderTest.selectBizBaseEndCustomerById(0L);
        assertEquals(bizBaseEndCustomer, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseEndCustomerList() {
        // Setup
        final BizBaseEndCustomer bizBaseEndCustomer = new BizBaseEndCustomer();
        bizBaseEndCustomer.setId(0L);
        bizBaseEndCustomer.setEndCustomerId("endCustomerId");
        bizBaseEndCustomer.setEndCustomerName("endCustomerName");
        bizBaseEndCustomer.setRegionMarketCode("regionMarketCode");
        bizBaseEndCustomer.setCountryCode("country");
        bizBaseEndCustomer.setStatus("status");
        bizBaseEndCustomer.setRemark("memo");

        // Configure BizBaseEndCustomerMapper.selectBizBaseEndCustomerList(...).
        final BizBaseEndCustomer bizBaseEndCustomer1 = new BizBaseEndCustomer();
        bizBaseEndCustomer1.setId(0L);
        bizBaseEndCustomer1.setEndCustomerId("endCustomerId");
        bizBaseEndCustomer1.setEndCustomerName("endCustomerName");
        bizBaseEndCustomer1.setRegionMarketCode("regionMarketCode");
        bizBaseEndCustomer1.setCountryCode("country");
        bizBaseEndCustomer1.setStatus("status");
        bizBaseEndCustomer1.setRemark("memo");
        final List<BizBaseEndCustomer> bizBaseEndCustomers = Arrays.asList(bizBaseEndCustomer1);
        when(mockBizBaseEndCustomerMapper.selectBizBaseEndCustomerList(any(BizBaseEndCustomer.class))).thenReturn(bizBaseEndCustomers);

        // Run the test
        final List<BizBaseEndCustomer> result = bizBaseEndCustomerServiceImplUnderTest.selectBizBaseEndCustomerList(bizBaseEndCustomer);
        assertEquals(bizBaseEndCustomers, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseEndCustomerList_BizBaseEndCustomerMapperReturnsNoItems() {
        // Setup
        final BizBaseEndCustomer bizBaseEndCustomer = new BizBaseEndCustomer();
        bizBaseEndCustomer.setId(0L);
        bizBaseEndCustomer.setEndCustomerId("endCustomerId");
        bizBaseEndCustomer.setEndCustomerName("endCustomerName");
        bizBaseEndCustomer.setRegionMarketCode("regionMarketCode");
        bizBaseEndCustomer.setCountryCode("country");
        bizBaseEndCustomer.setStatus("status");
        bizBaseEndCustomer.setRemark("memo");

        when(mockBizBaseEndCustomerMapper.selectBizBaseEndCustomerList(any(BizBaseEndCustomer.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseEndCustomer> result = bizBaseEndCustomerServiceImplUnderTest.selectBizBaseEndCustomerList(bizBaseEndCustomer);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseEndCustomer() {
        // Setup
        final BizBaseEndCustomer bizBaseEndCustomer = new BizBaseEndCustomer();
        bizBaseEndCustomer.setId(0L);
        bizBaseEndCustomer.setEndCustomerId("endCustomerId");
        bizBaseEndCustomer.setEndCustomerName("endCustomerName");
        bizBaseEndCustomer.setRegionMarketCode("regionMarketCode");
        bizBaseEndCustomer.setCountryCode("country");
        bizBaseEndCustomer.setStatus("status");
        bizBaseEndCustomer.setRemark("memo");

        when(mockBizBaseEndCustomerMapper.insertBizBaseEndCustomer(any(BizBaseEndCustomer.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseEndCustomerServiceImplUnderTest.insertBizBaseEndCustomer(bizBaseEndCustomer);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseEndCustomer() {
        // Setup
        final BizBaseEndCustomer bizBaseEndCustomer = new BizBaseEndCustomer();
        bizBaseEndCustomer.setId(0L);
        bizBaseEndCustomer.setEndCustomerId("endCustomerId");
        bizBaseEndCustomer.setEndCustomerName("endCustomerName");
        bizBaseEndCustomer.setRegionMarketCode("regionMarketCode");
        bizBaseEndCustomer.setCountryCode("country");
        bizBaseEndCustomer.setStatus("status");
        bizBaseEndCustomer.setRemark("memo");

        when(mockBizBaseEndCustomerMapper.updateBizBaseEndCustomer(any(BizBaseEndCustomer.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseEndCustomerServiceImplUnderTest.updateBizBaseEndCustomer(bizBaseEndCustomer);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseEndCustomerByIds() {
        // Setup
        when(mockBizBaseEndCustomerMapper.updateBizBaseEndCustomerByIds(any(Long[].class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseEndCustomerServiceImplUnderTest.deleteBizBaseEndCustomerByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseEndCustomerById() {
        // Setup
        when(mockBizBaseEndCustomerMapper.deleteBizBaseEndCustomerById(0L)).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseEndCustomerServiceImplUnderTest.deleteBizBaseEndCustomerById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() {
        // Setup
        final BizBaseEndCustomer bizBaseEndCustomer = new BizBaseEndCustomer();
        bizBaseEndCustomer.setId(0L);
        bizBaseEndCustomer.setEndCustomerId("endCustomerId");
        bizBaseEndCustomer.setEndCustomerName("endCustomerName");
        bizBaseEndCustomer.setRegionMarketCode("regionMarketCode");
        bizBaseEndCustomer.setCountryCode("country");
        bizBaseEndCustomer.setStatus("status");
        bizBaseEndCustomer.setRemark("memo");
        final List<BizBaseEndCustomer> bizs = Arrays.asList(bizBaseEndCustomer);

        // Configure BizBaseEndCustomerMapper.selectBizBaseEndCustomerListCheck(...).
        final BizBaseEndCustomer bizBaseEndCustomer1 = new BizBaseEndCustomer();
        bizBaseEndCustomer1.setId(0L);
        bizBaseEndCustomer1.setEndCustomerId("endCustomerId");
        bizBaseEndCustomer1.setEndCustomerName("endCustomerName");
        bizBaseEndCustomer1.setRegionMarketCode("regionMarketCode");
        bizBaseEndCustomer1.setCountryCode("country");
        bizBaseEndCustomer1.setStatus("status");
        bizBaseEndCustomer1.setRemark("memo");
        final List<BizBaseEndCustomer> bizBaseEndCustomers = Arrays.asList(bizBaseEndCustomer1);
        when(mockBizBaseEndCustomerMapper.selectBizBaseEndCustomerListCheck(any(BizBaseEndCustomer.class))).thenReturn(bizBaseEndCustomers);

        when(mockBizBaseEndCustomerMapper.updateBizBaseEndCustomer(any(BizBaseEndCustomer.class))).thenReturn(0);


        // Run the test
        final String result = bizBaseEndCustomerServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockBizBaseEndCustomerMapper).updateBizBaseEndCustomer(any(BizBaseEndCustomer.class));
    }

    @Test
    public void testGetDropDownList() {
        // Setup
        final BizBaseEndCustomer bizBaseEndCustomer = new BizBaseEndCustomer();
        bizBaseEndCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseEndCustomer.setIds("ids");
        bizBaseEndCustomer.setIdsList(Arrays.asList(0L));
        bizBaseEndCustomer.setId(0L);
        bizBaseEndCustomer.setEndCustomerId("endCustomerId");
        bizBaseEndCustomer.setEndCustomerName("endCustomerName");
        bizBaseEndCustomer.setRegionMarketCode("regionMarketCode");
        bizBaseEndCustomer.setRegionMarketName("regionMarketName");
        bizBaseEndCustomer.setCountryCode("countryCode");
        bizBaseEndCustomer.setCountryName("countryName");
        bizBaseEndCustomer.setStatus("status");
        bizBaseEndCustomer.setCreateBy("loginName");
        bizBaseEndCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseEndCustomer.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseEndCustomer.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseEndCustomerMapper.getDropDownList(...).
        final BizBaseEndCustomer customer = new BizBaseEndCustomer();
        customer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setIds("ids");
        customer.setIdsList(Arrays.asList(0L));
        customer.setId(0L);
        customer.setEndCustomerId("endCustomerId");
        customer.setEndCustomerName("endCustomerName");
        customer.setRegionMarketCode("regionMarketCode");
        customer.setRegionMarketName("regionMarketName");
        customer.setCountryCode("countryCode");
        customer.setCountryName("countryName");
        customer.setStatus("status");
        customer.setCreateBy("loginName");
        customer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseEndCustomer> bizBaseEndCustomers = Arrays.asList(customer);
        when(mockBizBaseEndCustomerMapper.getDropDownList(any(BizBaseEndCustomer.class)))
                .thenReturn(bizBaseEndCustomers);

        // Run the test
        final List<BizBaseEndCustomer> result = bizBaseEndCustomerServiceImplUnderTest.getDropDownList(
                bizBaseEndCustomer);

        // Verify the results
        assertEquals(result.size(), bizBaseEndCustomers.size());
    }

    @Test
    public void testRemoveCache() {
        // Setup
        // Configure BizBaseEndCustomerMapper.selectBizBaseEndCustomerById(...).
        final BizBaseEndCustomer customer = new BizBaseEndCustomer();
        customer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setIds("ids");
        customer.setIdsList(Arrays.asList(0L));
        customer.setId(0L);
        customer.setEndCustomerId("endCustomerId");
        customer.setEndCustomerName("endCustomerName");
        customer.setRegionMarketCode("regionMarketCode");
        customer.setRegionMarketName("regionMarketName");
        customer.setCountryCode("countryCode");
        customer.setCountryName("countryName");
        customer.setStatus("status");
        customer.setCreateBy("loginName");
        customer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockBizBaseEndCustomerMapper.selectBizBaseEndCustomerById(0L)).thenReturn(customer);

        // Run the test
        final BizBaseEndCustomer result = bizBaseEndCustomerServiceImplUnderTest.removeCache(0L);

        // Verify the results
        assertEquals(result, customer);
    }

}
