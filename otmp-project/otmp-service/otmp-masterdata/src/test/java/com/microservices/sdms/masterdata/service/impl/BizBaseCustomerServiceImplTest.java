package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;
import com.microservices.otmp.masterdata.mapper.BizBaseCustomerMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseCustomerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BizBaseCustomerServiceImplTest {

    @Mock
    private BizBaseCustomerMapper mockBizBaseCustomerMapper;
    @Mock
    private RedisUtils redisUtils;
    @Mock
    private ThreadPoolTaskExecutor mockExecutor;
    @InjectMocks
    private BizBaseCustomerServiceImpl bizBaseCustomerServiceImplUnderTest;

    @Test
    public void testSelectBizBaseCustomerById() {
        // Setup
        // Configure BizBaseCustomerMapper.selectBizBaseCustomerById(...).
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCustomerAccountGroup("customerAccountGroup");
        bizBaseCustomer.setCountryCode("country");
        when(mockBizBaseCustomerMapper.selectBizBaseCustomerById(0L)).thenReturn(bizBaseCustomer);

        // Run the test
        final BizBaseCustomer result = bizBaseCustomerServiceImplUnderTest.selectBizBaseCustomerById(0L);
        assertEquals(bizBaseCustomer, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseCustomerList() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCustomerAccountGroup("customerAccountGroup");
        bizBaseCustomer.setCountryCode("country");

        // Configure BizBaseCustomerMapper.selectBizBaseCustomerList(...).
        final BizBaseCustomer bizBaseCustomer1 = new BizBaseCustomer();
        bizBaseCustomer1.setId(0L);
        bizBaseCustomer1.setCustomerNumber("customerNumber");
        bizBaseCustomer1.setCustomerName("customerName");
        bizBaseCustomer1.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer1.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer1.setDivisionCode("divisionCode");
        bizBaseCustomer1.setTierType("tierType");
        bizBaseCustomer1.setStatus("status");
        bizBaseCustomer1.setCustomerAccountGroup("customerAccountGroup");
        bizBaseCustomer1.setCountryCode("country");
        final List<BizBaseCustomer> bizBaseCustomers = Arrays.asList(bizBaseCustomer1);
        when(mockBizBaseCustomerMapper.selectBizBaseCustomerList(any(BizBaseCustomer.class))).thenReturn(bizBaseCustomers);

        // Run the test
        final List<BizBaseCustomer> result = bizBaseCustomerServiceImplUnderTest.selectBizBaseCustomerList(bizBaseCustomer);
        assertEquals(bizBaseCustomers, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseCustomerList_BizBaseCustomerMapperReturnsNoItems() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCustomerAccountGroup("customerAccountGroup");
        bizBaseCustomer.setCountryCode("country");

        when(mockBizBaseCustomerMapper.selectBizBaseCustomerList(any(BizBaseCustomer.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseCustomer> result = bizBaseCustomerServiceImplUnderTest.selectBizBaseCustomerList(bizBaseCustomer);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseCustomer() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCustomerAccountGroup("customerAccountGroup");
        bizBaseCustomer.setCountryCode("country");

        when(mockBizBaseCustomerMapper.insertBizBaseCustomer(any(BizBaseCustomer.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseCustomerServiceImplUnderTest.insertBizBaseCustomer(bizBaseCustomer);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseCustomer() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCustomerAccountGroup("customerAccountGroup");
        bizBaseCustomer.setCountryCode("country");

        when(mockBizBaseCustomerMapper.updateBizBaseCustomer(any(BizBaseCustomer.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseCustomerServiceImplUnderTest.updateBizBaseCustomer(bizBaseCustomer);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseCustomerByIds() {
        // Setup
        when(mockBizBaseCustomerMapper.updateBizBaseCustomerByIds(any(Long[].class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseCustomerServiceImplUnderTest.deleteBizBaseCustomerByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseCustomerById() {
        // Setup
        when(mockBizBaseCustomerMapper.deleteBizBaseCustomerById(0L)).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseCustomerServiceImplUnderTest.deleteBizBaseCustomerById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCustomerAccountGroup("customerAccountGroup");
        bizBaseCustomer.setCountryCode("country");
        final List<BizBaseCustomer> bizs = Arrays.asList(bizBaseCustomer);

        // Configure BizBaseCustomerMapper.selectBizBaseCustomerListCheck(...).
        final BizBaseCustomer bizBaseCustomer1 = new BizBaseCustomer();
        bizBaseCustomer1.setId(0L);
        bizBaseCustomer1.setCustomerNumber("customerNumber");
        bizBaseCustomer1.setCustomerName("customerName");
        bizBaseCustomer1.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer1.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer1.setDivisionCode("divisionCode");
        bizBaseCustomer1.setTierType("tierType");
        bizBaseCustomer1.setStatus("status");
        bizBaseCustomer1.setCustomerAccountGroup("customerAccountGroup");
        bizBaseCustomer1.setCountryCode("country");
        final List<BizBaseCustomer> bizBaseCustomers = Arrays.asList(bizBaseCustomer1);
        when(mockBizBaseCustomerMapper.selectBizBaseCustomerListCheck(any(BizBaseCustomer.class))).thenReturn(bizBaseCustomers);

        when(mockBizBaseCustomerMapper.updateBizBaseCustomer(any(BizBaseCustomer.class))).thenReturn(0);


        // Run the test
        final String result = bizBaseCustomerServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockBizBaseCustomerMapper).selectBizBaseCustomerListCheck(any(BizBaseCustomer.class));

    }

    @Test
    public void testImportExcel_BizBaseCustomerMapperSelectBizBaseCustomerListCheckReturnsNoItems() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCustomerAccountGroup("customerAccountGroup");
        bizBaseCustomer.setCountryCode("country");
        final List<BizBaseCustomer> bizs = Arrays.asList(bizBaseCustomer);
        when(mockBizBaseCustomerMapper.selectBizBaseCustomerListCheck(any(BizBaseCustomer.class))).thenReturn(Collections.emptyList());
        when(mockBizBaseCustomerMapper.insertBizBaseCustomer(any(BizBaseCustomer.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseCustomerServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);

        verify(mockBizBaseCustomerMapper).insertBizBaseCustomer(any(BizBaseCustomer.class));
    }

    @Test
    public void testAddSoldToOrPayerToRedis() {
        // Setup
        when(redisUtils.get("s", String.class)).thenReturn("[\"result\"]");

        // Run the test
        bizBaseCustomerServiceImplUnderTest.addSoldToOrPayerToRedis("customerName", "s");

        final List<String> list = new ArrayList<>();
        list.add("result");
        list.add("customerName");
        // Verify the results
        verify(redisUtils).addList(31536000L, "s", "customerName");
    }

    @Test
    public void testInsertBizBaseCustomerByEcc() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCustomer.setIds("ids");
        bizBaseCustomer.setIdsList(Arrays.asList(0L));
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOrgName("salesOrgName");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setSalesOfficeName("salesOfficeName");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCreateBy("loginName");
        bizBaseCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final ArrayList<String> resultList = new ArrayList<>();
        resultList.add("result");
        when(mockBizBaseCustomerMapper.insertBizBaseCustomer(any(BizBaseCustomer.class))).thenReturn(0);
        when(redisUtils.getList("master:sold_to")).thenReturn(resultList);
        when(redisUtils.getList("master:payer")).thenReturn(resultList);

        // Run the test
        final int result = bizBaseCustomerServiceImplUnderTest.insertBizBaseCustomerByEcc(bizBaseCustomer);

        final List<String> list = new ArrayList<>();
        list.add("result");
        list.add("customerName");
        // Verify the results
        assertEquals(0, result);
        verify(redisUtils).addList(31536000L, "master:sold_to", "customerName");
        verify(redisUtils).addList(31536000L, "master:payer", "customerName");
    }

    @Test
    public void testUpdateBizBaseCustomerByEcc() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCustomer.setIds("ids");
        bizBaseCustomer.setIdsList(Arrays.asList(0L));
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName1");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOrgName("salesOrgName");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setSalesOfficeName("salesOfficeName");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCreateBy("loginName");
        bizBaseCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseCustomerMapper.selectBizBaseCustomerById(...).
        final BizBaseCustomer customer = new BizBaseCustomer();
        customer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setIds("ids");
        customer.setIdsList(Arrays.asList(0L));
        customer.setId(0L);
        customer.setCustomerNumber("customerNumber");
        customer.setCustomerName("customerName");
        customer.setSalesOrgCode("salesOrgCode");
        customer.setSalesOrgName("salesOrgName");
        customer.setSalesOfficeCode("salesOfficeCode");
        customer.setSalesOfficeName("salesOfficeName");
        customer.setDivisionCode("divisionCode");
        customer.setTierType("tierType");
        customer.setStatus("status");
        customer.setCreateBy("loginName");
        customer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockBizBaseCustomerMapper.selectBizBaseCustomerById(0L)).thenReturn(customer);

        when(mockBizBaseCustomerMapper.updateBizBaseCustomer(any(BizBaseCustomer.class))).thenReturn(0);
        doAnswer(invocation -> {
            ((Runnable) invocation.getArguments()[0]).run();
            return null;
        }).when(mockExecutor).execute(any(Runnable.class));

        // Configure BizBaseCustomerMapper.selectBizBaseCustomerList(...).
        final BizBaseCustomer customer1 = new BizBaseCustomer();
        customer1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer1.setIds("ids");
        customer1.setIdsList(Arrays.asList(0L));
        customer1.setId(0L);
        customer1.setCustomerNumber("customerNumber");
        customer1.setCustomerName("customerName");
        customer1.setSalesOrgCode("salesOrgCode");
        customer1.setSalesOrgName("salesOrgName");
        customer1.setSalesOfficeCode("salesOfficeCode");
        customer1.setSalesOfficeName("salesOfficeName");
        customer1.setDivisionCode("divisionCode");
        customer1.setTierType("tierType");
        customer1.setStatus("status");
        customer1.setCreateBy("loginName");
        customer1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCustomer> bizBaseCustomers = Arrays.asList(customer1);
        when(mockBizBaseCustomerMapper.selectBizBaseCustomerList(any(BizBaseCustomer.class)))
                .thenReturn(bizBaseCustomers);

        // Run the test
        final int result = bizBaseCustomerServiceImplUnderTest.updateBizBaseCustomerByEcc(bizBaseCustomer);

        // Verify the results
        assertEquals(0, result);
        verify(redisUtils).set("master:sold_to", Arrays.asList("customerNumber"), 31536000L);
        verify(redisUtils).set("master:payer", Arrays.asList("customerNumber"), 31536000L);
    }

    @Test
    public void testGetDropDownList() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCustomer.setIds("ids");
        bizBaseCustomer.setIdsList(Arrays.asList(0L));
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOrgName("salesOrgName");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setSalesOfficeName("salesOfficeName");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCreateBy("loginName");
        bizBaseCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseCustomerMapper.getDropDownList(...).
        final BizBaseCustomer customer = new BizBaseCustomer();
        customer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setIds("ids");
        customer.setIdsList(Arrays.asList(0L));
        customer.setId(0L);
        customer.setCustomerNumber("customerNumber");
        customer.setCustomerName("customerName");
        customer.setSalesOrgCode("salesOrgCode");
        customer.setSalesOrgName("salesOrgName");
        customer.setSalesOfficeCode("salesOfficeCode");
        customer.setSalesOfficeName("salesOfficeName");
        customer.setDivisionCode("divisionCode");
        customer.setTierType("tierType");
        customer.setStatus("status");
        customer.setCreateBy("loginName");
        customer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCustomer> bizBaseCustomers = Arrays.asList(customer);
        when(mockBizBaseCustomerMapper.getDropDownList(any(BizBaseCustomer.class))).thenReturn(bizBaseCustomers);

        // Run the test
        final List<BizBaseCustomer> result = bizBaseCustomerServiceImplUnderTest.getDropDownList(bizBaseCustomer);

        // Verify the results
        assertEquals(1, result.size());
    }

    @Test
    public void testGetDropDownList_BizBaseCustomerMapperReturnsNoItems() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCustomer.setIds("ids");
        bizBaseCustomer.setIdsList(Arrays.asList(0L));
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOrgName("salesOrgName");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setSalesOfficeName("salesOfficeName");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCreateBy("loginName");
        bizBaseCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseCustomerMapper.getDropDownList(any(BizBaseCustomer.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseCustomer> result = bizBaseCustomerServiceImplUnderTest.getDropDownList(bizBaseCustomer);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetCustomerInfo() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCustomer.setIds("ids");
        bizBaseCustomer.setIdsList(Arrays.asList(0L));
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOrgName("salesOrgName");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setSalesOfficeName("salesOfficeName");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCreateBy("loginName");
        bizBaseCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseCustomerMapper.selectBizBaseCustomerList(...).
        final BizBaseCustomer customer = new BizBaseCustomer();
        customer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setIds("ids");
        customer.setIdsList(Arrays.asList(0L));
        customer.setId(0L);
        customer.setCustomerNumber("customerNumber");
        customer.setCustomerName("customerName");
        customer.setSalesOrgCode("salesOrgCode");
        customer.setSalesOrgName("salesOrgName");
        customer.setSalesOfficeCode("salesOfficeCode");
        customer.setSalesOfficeName("salesOfficeName");
        customer.setDivisionCode("divisionCode");
        customer.setTierType("tierType");
        customer.setStatus("status");
        customer.setCreateBy("loginName");
        customer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCustomer> bizBaseCustomers = Arrays.asList(customer);
        when(mockBizBaseCustomerMapper.selectBizBaseCustomerList(any(BizBaseCustomer.class)))
                .thenReturn(bizBaseCustomers);

        // Run the test
        final BizBaseCustomer result = bizBaseCustomerServiceImplUnderTest.getCustomerInfo(bizBaseCustomer);

        // Verify the results
        assertEquals(customer.getId(), result.getId());
    }

    @Test
    public void testGetCustomer() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCustomer.setIds("ids");
        bizBaseCustomer.setIdsList(Arrays.asList(0L));
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOrgName("salesOrgName");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setSalesOfficeName("salesOfficeName");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCreateBy("loginName");
        bizBaseCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseCustomerMapper.selectBizBaseCustomerList(...).
        final BizBaseCustomer customer = new BizBaseCustomer();
        customer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setIds("ids");
        customer.setIdsList(Arrays.asList(0L));
        customer.setId(0L);
        customer.setCustomerNumber("customerNumber");
        customer.setCustomerName("customerName");
        customer.setSalesOrgCode("salesOrgCode");
        customer.setSalesOrgName("salesOrgName");
        customer.setSalesOfficeCode("salesOfficeCode");
        customer.setSalesOfficeName("salesOfficeName");
        customer.setDivisionCode("divisionCode");
        customer.setTierType("tierType");
        customer.setStatus("status");
        customer.setCreateBy("loginName");
        customer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCustomer> bizBaseCustomers = Arrays.asList(customer);
        when(mockBizBaseCustomerMapper.selectBizBaseCustomerList(any(BizBaseCustomer.class)))
                .thenReturn(bizBaseCustomers);

        // Run the test
        final BizBaseCustomer result = bizBaseCustomerServiceImplUnderTest.getCustomer(bizBaseCustomer);

        // Verify the results
        assertEquals(customer.getId(), result.getId());
    }

    @Test
    public void testGetCustomer_BizBaseCustomerMapperReturnsNull() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCustomer.setIds("ids");
        bizBaseCustomer.setIdsList(Arrays.asList(0L));
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOrgName("salesOrgName");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setSalesOfficeName("salesOfficeName");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCreateBy("loginName");
        bizBaseCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseCustomerMapper.selectBizBaseCustomerList(any(BizBaseCustomer.class))).thenReturn(null);

        // Run the test
        final BizBaseCustomer result = bizBaseCustomerServiceImplUnderTest.getCustomer(bizBaseCustomer);

        // Verify the results
        assertNull(result);
    }

    @Test
    public void testGetCustomer_BizBaseCustomerMapperReturnsNoItems() {
        // Setup
        final BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCustomer.setIds("ids");
        bizBaseCustomer.setIdsList(Arrays.asList(0L));
        bizBaseCustomer.setId(0L);
        bizBaseCustomer.setCustomerNumber("customerNumber");
        bizBaseCustomer.setCustomerName("customerName");
        bizBaseCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseCustomer.setSalesOrgName("salesOrgName");
        bizBaseCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseCustomer.setSalesOfficeName("salesOfficeName");
        bizBaseCustomer.setDivisionCode("divisionCode");
        bizBaseCustomer.setTierType("tierType");
        bizBaseCustomer.setStatus("status");
        bizBaseCustomer.setCreateBy("loginName");
        bizBaseCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseCustomerMapper.selectBizBaseCustomerList(any(BizBaseCustomer.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final BizBaseCustomer result = bizBaseCustomerServiceImplUnderTest.getCustomer(bizBaseCustomer);

        // Verify the results
        assertNull(result);
    }

    @Test
    public void testRemoveCache() {
        // Setup
        // Configure BizBaseCustomerMapper.selectBizBaseCustomerById(...).
        final BizBaseCustomer customer = new BizBaseCustomer();
        customer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setIds("ids");
        customer.setIdsList(Arrays.asList(0L));
        customer.setId(0L);
        customer.setCustomerNumber("customerNumber");
        customer.setCustomerName("customerName");
        customer.setSalesOrgCode("salesOrgCode");
        customer.setSalesOrgName("salesOrgName");
        customer.setSalesOfficeCode("salesOfficeCode");
        customer.setSalesOfficeName("salesOfficeName");
        customer.setDivisionCode("divisionCode");
        customer.setTierType("tierType");
        customer.setStatus("status");
        customer.setCreateBy("loginName");
        customer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockBizBaseCustomerMapper.selectBizBaseCustomerById(0L)).thenReturn(customer);

        // Run the test
        final BizBaseCustomer result = bizBaseCustomerServiceImplUnderTest.removeCache(0L);

        // Verify the results
        assertEquals(customer.getId(), result.getId());
    }
}
