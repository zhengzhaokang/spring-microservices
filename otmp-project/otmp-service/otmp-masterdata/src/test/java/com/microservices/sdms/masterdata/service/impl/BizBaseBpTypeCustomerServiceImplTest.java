package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseBpTypeCustomer;
import com.microservices.otmp.masterdata.mapper.BizBaseBpTypeCustomerMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseBpTypeCustomerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseBpTypeCustomerServiceImplTest {

    @Mock
    private BizBaseBpTypeCustomerMapper mockBizBaseBpTypeCustomerMapper;
    @Mock
    private RedisUtils mockRedisUtils;

    @InjectMocks
    private BizBaseBpTypeCustomerServiceImpl bizBaseBpTypeCustomerServiceImplUnderTest;

    @Test
    public void testSetPartnerTypeToRedis() {
        // Setup
        // Configure BizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(...).
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");
        final List<BizBaseBpTypeCustomer> bizBaseBpTypeCustomers = Arrays.asList(bizBaseBpTypeCustomer);
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(bizBaseBpTypeCustomers);

        // Run the test
        bizBaseBpTypeCustomerServiceImplUnderTest.setPartnerTypeToRedis();
        List<BizBaseBpTypeCustomer> list = bizBaseBpTypeCustomerServiceImplUnderTest.selectBizBaseBpTypeCustomerList(bizBaseBpTypeCustomer);
        // Verify the results
        //verify(mockRedisUtils).set("master:partner_type", Arrays.asList("value"), 0L);
        assertEquals(list,bizBaseBpTypeCustomers);
    }

    @Test
    public void testSetPartnerTypeToRedis_BizBaseBpTypeCustomerMapperReturnsNoItems() {
        List<BizBaseBpTypeCustomer> emptyList = Collections.emptyList();
        // Setup
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(emptyList);
        List<BizBaseBpTypeCustomer> bizBaseBpTypeCustomers = bizBaseBpTypeCustomerServiceImplUnderTest.selectBizBaseBpTypeCustomerList(new BizBaseBpTypeCustomer());

        // Run the test
        bizBaseBpTypeCustomerServiceImplUnderTest.setPartnerTypeToRedis();

        // Verify the results
        //verify(mockRedisUtils).set("master:partner_type", Arrays.asList("value"), 0L);
        assertEquals(bizBaseBpTypeCustomers, emptyList);
    }

    @Test
    public void testSelectBizBaseBpTypeCustomerById() {
        // Setup
        // Configure BizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerById(...).
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerById(0L)).thenReturn(bizBaseBpTypeCustomer);

        // Run the test
        final BizBaseBpTypeCustomer result = bizBaseBpTypeCustomerServiceImplUnderTest.selectBizBaseBpTypeCustomerById(
                0L);

        // Verify the results
        assertEquals(result, bizBaseBpTypeCustomer);
    }

    @Test
    public void testSelectBizBaseBpTypeCustomerList() {
        // Setup
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");

        // Configure BizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(...).
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer1 = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer1.setId(0L);
        bizBaseBpTypeCustomer1.setBpType("bpType");
        bizBaseBpTypeCustomer1.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer1.setCustomerName("customerName");
        bizBaseBpTypeCustomer1.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer1.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer1.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer1.setStatus("status");
        final List<BizBaseBpTypeCustomer> bizBaseBpTypeCustomers = Arrays.asList(bizBaseBpTypeCustomer1);
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(bizBaseBpTypeCustomers);

        // Run the test
        final List<BizBaseBpTypeCustomer> result = bizBaseBpTypeCustomerServiceImplUnderTest.selectBizBaseBpTypeCustomerList(
                bizBaseBpTypeCustomer);

        // Verify the results
        assertEquals(result, bizBaseBpTypeCustomers);
    }

    @Test
    public void testSelectBizBaseBpTypeCustomerList_BizBaseBpTypeCustomerMapperReturnsNoItems() {
        // Setup
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");

        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseBpTypeCustomer> result = bizBaseBpTypeCustomerServiceImplUnderTest.selectBizBaseBpTypeCustomerList(
                bizBaseBpTypeCustomer);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseBpTypeCustomer() {
        // Setup
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");

        when(mockBizBaseBpTypeCustomerMapper.insertBizBaseBpTypeCustomer(any(BizBaseBpTypeCustomer.class)))
                .thenReturn(0);

        // Configure BizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(...).
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer1 = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer1.setId(0L);
        bizBaseBpTypeCustomer1.setBpType("bpType");
        bizBaseBpTypeCustomer1.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer1.setCustomerName("customerName");
        bizBaseBpTypeCustomer1.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer1.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer1.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer1.setStatus("status");
        final List<BizBaseBpTypeCustomer> bizBaseBpTypeCustomers = Arrays.asList(bizBaseBpTypeCustomer1);
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(bizBaseBpTypeCustomers);

        // Run the test
        final int result = bizBaseBpTypeCustomerServiceImplUnderTest.insertBizBaseBpTypeCustomer(bizBaseBpTypeCustomer);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:partner_type", Arrays.asList("value"), 0L);
    }

    @Test
    public void testInsertBizBaseBpTypeCustomer_BizBaseBpTypeCustomerMapperSelectBizBaseBpTypeCustomerListReturnsNoItems() {
        // Setup
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");

        when(mockBizBaseBpTypeCustomerMapper.insertBizBaseBpTypeCustomer(any(BizBaseBpTypeCustomer.class)))
                .thenReturn(0);
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseBpTypeCustomerServiceImplUnderTest.insertBizBaseBpTypeCustomer(bizBaseBpTypeCustomer);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:partner_type", Arrays.asList("value"), 0L);
    }

    @Test
    public void testUpdateBizBaseBpTypeCustomer() {
        // Setup
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");

        when(mockBizBaseBpTypeCustomerMapper.updateBizBaseBpTypeCustomer(any(BizBaseBpTypeCustomer.class)))
                .thenReturn(0);

        // Configure BizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(...).
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer1 = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer1.setId(0L);
        bizBaseBpTypeCustomer1.setBpType("bpType");
        bizBaseBpTypeCustomer1.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer1.setCustomerName("customerName");
        bizBaseBpTypeCustomer1.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer1.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer1.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer1.setStatus("status");
        final List<BizBaseBpTypeCustomer> bizBaseBpTypeCustomers = Arrays.asList(bizBaseBpTypeCustomer1);
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(bizBaseBpTypeCustomers);

        // Run the test
        final int result = bizBaseBpTypeCustomerServiceImplUnderTest.updateBizBaseBpTypeCustomer(bizBaseBpTypeCustomer);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:partner_type", Arrays.asList("value"), 0L);
    }

    @Test
    public void testUpdateBizBaseBpTypeCustomer_BizBaseBpTypeCustomerMapperSelectBizBaseBpTypeCustomerListReturnsNoItems() {
        // Setup
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");

        when(mockBizBaseBpTypeCustomerMapper.updateBizBaseBpTypeCustomer(any(BizBaseBpTypeCustomer.class)))
                .thenReturn(0);
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseBpTypeCustomerServiceImplUnderTest.updateBizBaseBpTypeCustomer(bizBaseBpTypeCustomer);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:partner_type", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseBpTypeCustomerByIds() {
        // Setup
        when(mockBizBaseBpTypeCustomerMapper.deleteBizBaseBpTypeCustomerByIds(any(Long[].class))).thenReturn(0);

        // Configure BizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(...).
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");
        final List<BizBaseBpTypeCustomer> bizBaseBpTypeCustomers = Arrays.asList(bizBaseBpTypeCustomer);
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(bizBaseBpTypeCustomers);

        // Run the test
        final int result = bizBaseBpTypeCustomerServiceImplUnderTest.deleteBizBaseBpTypeCustomerByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:partner_type", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseBpTypeCustomerByIds_BizBaseBpTypeCustomerMapperSelectBizBaseBpTypeCustomerListReturnsNoItems() {
        // Setup
        when(mockBizBaseBpTypeCustomerMapper.deleteBizBaseBpTypeCustomerByIds(any(Long[].class))).thenReturn(0);
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseBpTypeCustomerServiceImplUnderTest.deleteBizBaseBpTypeCustomerByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:partner_type", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseBpTypeCustomerById() {
        // Setup
        when(mockBizBaseBpTypeCustomerMapper.deleteBizBaseBpTypeCustomerById(0L)).thenReturn(0);

        // Configure BizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(...).
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");
        final List<BizBaseBpTypeCustomer> bizBaseBpTypeCustomers = Arrays.asList(bizBaseBpTypeCustomer);
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(bizBaseBpTypeCustomers);

        // Run the test
        final int result = bizBaseBpTypeCustomerServiceImplUnderTest.deleteBizBaseBpTypeCustomerById(0L);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:partner_type", Arrays.asList("value"), 0L);
    }

    @Test
    public void testDeleteBizBaseBpTypeCustomerById_BizBaseBpTypeCustomerMapperSelectBizBaseBpTypeCustomerListReturnsNoItems() {
        // Setup
        when(mockBizBaseBpTypeCustomerMapper.deleteBizBaseBpTypeCustomerById(0L)).thenReturn(0);
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseBpTypeCustomerServiceImplUnderTest.deleteBizBaseBpTypeCustomerById(0L);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:partner_type", Arrays.asList("value"), 0L);
    }

    @Test
    public void testImportExcel() throws Exception {
        assertNull(bizBaseBpTypeCustomerServiceImplUnderTest.importExcel(Arrays.asList(new BizBaseBpTypeCustomer()),
                "loginName"));
    }

    @Test
    public void testSelectBizBaseBpTypeCustomerListDistinctBpType() {
        // Setup
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");

        // Configure BizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(...).
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer1 = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer1.setId(0L);
        bizBaseBpTypeCustomer1.setBpType("bpType");
        bizBaseBpTypeCustomer1.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer1.setCustomerName("customerName");
        bizBaseBpTypeCustomer1.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer1.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer1.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer1.setStatus("status");
        final List<BizBaseBpTypeCustomer> bizBaseBpTypeCustomers = Arrays.asList(bizBaseBpTypeCustomer1);
        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(bizBaseBpTypeCustomers);

        // Run the test
        final List<BizBaseBpTypeCustomer> result = bizBaseBpTypeCustomerServiceImplUnderTest.selectBizBaseBpTypeCustomerListDistinctBpType(
                bizBaseBpTypeCustomer);

        // Verify the results
        assertEquals(result, bizBaseBpTypeCustomers);
    }

    @Test
    public void testSelectBizBaseBpTypeCustomerListDistinctBpType_BizBaseBpTypeCustomerMapperReturnsNoItems() {
        List<BizBaseBpTypeCustomer> emptyList = Collections.emptyList();
        // Setup
        final BizBaseBpTypeCustomer bizBaseBpTypeCustomer = new BizBaseBpTypeCustomer();
        bizBaseBpTypeCustomer.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBpTypeCustomer.setId(0L);
        bizBaseBpTypeCustomer.setBpType("bpType");
        bizBaseBpTypeCustomer.setCustomerNumber("customerNumber");
        bizBaseBpTypeCustomer.setCustomerName("customerName");
        bizBaseBpTypeCustomer.setSalesOrgCode("salesOrgCode");
        bizBaseBpTypeCustomer.setSalesOfficeCode("salesOfficeCode");
        bizBaseBpTypeCustomer.setCustomerGroup("customerGroup");
        bizBaseBpTypeCustomer.setStatus("status");

        when(mockBizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(
                any(BizBaseBpTypeCustomer.class))).thenReturn(emptyList);

        // Run the test
        final List<BizBaseBpTypeCustomer> result = bizBaseBpTypeCustomerServiceImplUnderTest.selectBizBaseBpTypeCustomerListDistinctBpType(
                bizBaseBpTypeCustomer);

        // Verify the results
        assertEquals(result, emptyList);
    }
}
