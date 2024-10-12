package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseCurrency;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.domain.dto.BizBaseCurrencyDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseCurrencyDO;
import com.microservices.otmp.masterdata.manager.BizBaseCurrencyManager;
import com.microservices.otmp.masterdata.mapper.BizBaseCurrencyMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseCurrencyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseCurrencyServiceImplTest {

    @Mock
    private BizBaseCurrencyMapper mockBizBaseCurrencyMapper;
    @Mock
    private BizBaseCurrencyManager mockBizBaseCurrencyManager;
    @Mock
    private RedisUtils mockRedisUtils;

    @InjectMocks
    private BizBaseCurrencyServiceImpl bizBaseCurrencyServiceImplUnderTest;

    @Test
    public void testSetCountryToRedis() {
        // Setup
        // Configure BizBaseCurrencyMapper.selectBizBaseCurrencyList(...).
        final BizBaseCurrency currency = new BizBaseCurrency();
        currency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setIdsList(Arrays.asList(0L));
        currency.setIds("0");
        currency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        currency.setId("id");
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyDesc("currencyDesc");
        currency.setDigitsTag("digitsTag");
        currency.setStatus("status");
        currency.setDecimals(new BigDecimal("0.00"));
        currency.setCreateBy("loginName");
        currency.setRemark("remark");
        currency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCurrency> bizBaseCurrencies = Arrays.asList(currency);
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(bizBaseCurrencies);

        // Run the test
        List<BizBaseCurrency> result = bizBaseCurrencyServiceImplUnderTest.selectBizBaseCurrencyList(currency);
        bizBaseCurrencyServiceImplUnderTest.setCountryToRedis();

        // Verify the results
        //verify(mockRedisUtils).set("master:check_currency_point", new HashMap<>(), 0L);
        assertEquals(result, bizBaseCurrencies);
    }

    @Test
    public void testSetCountryToRedis_BizBaseCurrencyMapperReturnsNoItems() {
        // Setup
        List<BizBaseCurrency> emptyList = Collections.emptyList();
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(emptyList);

        // Run the test
        List<BizBaseCurrency> list = bizBaseCurrencyServiceImplUnderTest.selectBizBaseCurrencyList(new BizBaseCurrency());
        bizBaseCurrencyServiceImplUnderTest.setCountryToRedis();

        // Verify the results
        //verify(mockRedisUtils).set("master:check_currency_point", new HashMap<>(), 0L);
        assertEquals(list, emptyList);
    }

    @Test
    public void testSelectBizBaseCurrencyById() {
        // Setup
        // Configure BizBaseCurrencyMapper.selectBizBaseCurrencyById(...).
        final BizBaseCurrency currency = new BizBaseCurrency();
        currency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setIdsList(Arrays.asList(0L));
        currency.setIds("0");
        currency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        currency.setId("id");
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyDesc("currencyDesc");
        currency.setDigitsTag("digitsTag");
        currency.setStatus("status");
        currency.setDecimals(new BigDecimal("0.00"));
        currency.setCreateBy("loginName");
        currency.setRemark("remark");
        currency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyById("id")).thenReturn(currency);

        // Run the test
        final BizBaseCurrency result = bizBaseCurrencyServiceImplUnderTest.selectBizBaseCurrencyById("id");

        // Verify the results
        assertEquals(result, currency);
    }

    @Test
    public void testSelectBizBaseCurrencyList() {
        // Setup
        final BizBaseCurrency bizBaseCurrency = new BizBaseCurrency();
        bizBaseCurrency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setIdsList(Arrays.asList(0L));
        bizBaseCurrency.setIds("0");
        bizBaseCurrency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        bizBaseCurrency.setId("id");
        bizBaseCurrency.setCurrencyCode("currencyCode");
        bizBaseCurrency.setCurrencyDesc("currencyDesc");
        bizBaseCurrency.setDigitsTag("digitsTag");
        bizBaseCurrency.setStatus("status");
        bizBaseCurrency.setDecimals(new BigDecimal("0.00"));
        bizBaseCurrency.setCreateBy("loginName");
        bizBaseCurrency.setRemark("remark");
        bizBaseCurrency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseCurrencyMapper.selectBizBaseCurrencyList(...).
        final BizBaseCurrency currency = new BizBaseCurrency();
        currency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setIdsList(Arrays.asList(0L));
        currency.setIds("0");
        currency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        currency.setId("id");
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyDesc("currencyDesc");
        currency.setDigitsTag("digitsTag");
        currency.setStatus("status");
        currency.setDecimals(new BigDecimal("0.00"));
        currency.setCreateBy("loginName");
        currency.setRemark("remark");
        currency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCurrency> bizBaseCurrencies = Arrays.asList(currency);
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(bizBaseCurrencies);

        // Run the test
        final List<BizBaseCurrency> result = bizBaseCurrencyServiceImplUnderTest.selectBizBaseCurrencyList(
                bizBaseCurrency);

        // Verify the results
        assertEquals(result, bizBaseCurrencies);
    }

    @Test
    public void testSelectBizBaseCurrencyList_BizBaseCurrencyMapperReturnsNoItems() {
        // Setup
        final BizBaseCurrency bizBaseCurrency = new BizBaseCurrency();
        bizBaseCurrency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setIdsList(Arrays.asList(0L));
        bizBaseCurrency.setIds("0");
        bizBaseCurrency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        bizBaseCurrency.setId("id");
        bizBaseCurrency.setCurrencyCode("currencyCode");
        bizBaseCurrency.setCurrencyDesc("currencyDesc");
        bizBaseCurrency.setDigitsTag("digitsTag");
        bizBaseCurrency.setStatus("status");
        bizBaseCurrency.setDecimals(new BigDecimal("0.00"));
        bizBaseCurrency.setCreateBy("loginName");
        bizBaseCurrency.setRemark("remark");
        bizBaseCurrency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseCurrency> result = bizBaseCurrencyServiceImplUnderTest.selectBizBaseCurrencyList(
                bizBaseCurrency);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseCurrency() {
        // Setup
        final BizBaseCurrency bizBaseCurrency = new BizBaseCurrency();
        bizBaseCurrency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setIdsList(Arrays.asList(0L));
        bizBaseCurrency.setIds("0");
        bizBaseCurrency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        bizBaseCurrency.setId("id");
        bizBaseCurrency.setCurrencyCode("currencyCode");
        bizBaseCurrency.setCurrencyDesc("currencyDesc");
        bizBaseCurrency.setDigitsTag("digitsTag");
        bizBaseCurrency.setStatus("status");
        bizBaseCurrency.setDecimals(new BigDecimal("0.00"));
        bizBaseCurrency.setCreateBy("loginName");
        bizBaseCurrency.setRemark("remark");
        bizBaseCurrency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseCurrencyMapper.insertBizBaseCurrency(any(BizBaseCurrency.class))).thenReturn(0);

        // Configure BizBaseCurrencyMapper.selectBizBaseCurrencyList(...).
        final BizBaseCurrency currency = new BizBaseCurrency();
        currency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setIdsList(Arrays.asList(0L));
        currency.setIds("0");
        currency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        currency.setId("id");
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyDesc("currencyDesc");
        currency.setDigitsTag("digitsTag");
        currency.setStatus("status");
        currency.setDecimals(new BigDecimal("0.00"));
        currency.setCreateBy("loginName");
        currency.setRemark("remark");
        currency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCurrency> bizBaseCurrencies = Arrays.asList(currency);
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(bizBaseCurrencies);

        // Run the test
        final int result = bizBaseCurrencyServiceImplUnderTest.insertBizBaseCurrency(bizBaseCurrency);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:check_currency_point", new HashMap<>(), 0L);
    }

    @Test
    public void testInsertBizBaseCurrency_BizBaseCurrencyMapperSelectBizBaseCurrencyListReturnsNoItems() {
        // Setup
        final BizBaseCurrency bizBaseCurrency = new BizBaseCurrency();
        bizBaseCurrency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setIdsList(Arrays.asList(0L));
        bizBaseCurrency.setIds("0");
        bizBaseCurrency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        bizBaseCurrency.setId("id");
        bizBaseCurrency.setCurrencyCode("currencyCode");
        bizBaseCurrency.setCurrencyDesc("currencyDesc");
        bizBaseCurrency.setDigitsTag("digitsTag");
        bizBaseCurrency.setStatus("status");
        bizBaseCurrency.setDecimals(new BigDecimal("0.00"));
        bizBaseCurrency.setCreateBy("loginName");
        bizBaseCurrency.setRemark("remark");
        bizBaseCurrency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseCurrencyMapper.insertBizBaseCurrency(any(BizBaseCurrency.class))).thenReturn(0);
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseCurrencyServiceImplUnderTest.insertBizBaseCurrency(bizBaseCurrency);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:check_currency_point", new HashMap<>(), 0L);
    }

    @Test
    public void testUpdateBizBaseCurrency() {
        // Setup
        final BizBaseCurrency bizBaseCurrency = new BizBaseCurrency();
        bizBaseCurrency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setIdsList(Arrays.asList(0L));
        bizBaseCurrency.setIds("0");
        bizBaseCurrency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        bizBaseCurrency.setId("id");
        bizBaseCurrency.setCurrencyCode("currencyCode");
        bizBaseCurrency.setCurrencyDesc("currencyDesc");
        bizBaseCurrency.setDigitsTag("digitsTag");
        bizBaseCurrency.setStatus("status");
        bizBaseCurrency.setDecimals(new BigDecimal("0.00"));
        bizBaseCurrency.setCreateBy("loginName");
        bizBaseCurrency.setRemark("remark");
        bizBaseCurrency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseCurrencyMapper.updateBizBaseCurrency(any(BizBaseCurrency.class))).thenReturn(0);

        // Configure BizBaseCurrencyMapper.selectBizBaseCurrencyList(...).
        final BizBaseCurrency currency = new BizBaseCurrency();
        currency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setIdsList(Arrays.asList(0L));
        currency.setIds("0");
        currency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        currency.setId("id");
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyDesc("currencyDesc");
        currency.setDigitsTag("digitsTag");
        currency.setStatus("status");
        currency.setDecimals(new BigDecimal("0.00"));
        currency.setCreateBy("loginName");
        currency.setRemark("remark");
        currency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCurrency> bizBaseCurrencies = Arrays.asList(currency);
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(bizBaseCurrencies);

        // Run the test
        final int result = bizBaseCurrencyServiceImplUnderTest.updateBizBaseCurrency(bizBaseCurrency);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:check_currency_point", new HashMap<>(), 0L);
    }

    @Test
    public void testUpdateBizBaseCurrency_BizBaseCurrencyMapperSelectBizBaseCurrencyListReturnsNoItems() {
        // Setup
        final BizBaseCurrency bizBaseCurrency = new BizBaseCurrency();
        bizBaseCurrency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setIdsList(Arrays.asList(0L));
        bizBaseCurrency.setIds("0");
        bizBaseCurrency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        bizBaseCurrency.setId("id");
        bizBaseCurrency.setCurrencyCode("currencyCode");
        bizBaseCurrency.setCurrencyDesc("currencyDesc");
        bizBaseCurrency.setDigitsTag("digitsTag");
        bizBaseCurrency.setStatus("status");
        bizBaseCurrency.setDecimals(new BigDecimal("0.00"));
        bizBaseCurrency.setCreateBy("loginName");
        bizBaseCurrency.setRemark("remark");
        bizBaseCurrency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCurrency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseCurrencyMapper.updateBizBaseCurrency(any(BizBaseCurrency.class))).thenReturn(0);
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseCurrencyServiceImplUnderTest.updateBizBaseCurrency(bizBaseCurrency);

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:check_currency_point", new HashMap<>(), 0L);
    }

    @Test
    public void testDeleteBizBaseCurrencyByIds() {
        // Setup
        when(mockBizBaseCurrencyMapper.deleteBizBaseCurrencyByIds(any(String[].class))).thenReturn(0);

        // Configure BizBaseCurrencyMapper.selectBizBaseCurrencyList(...).
        final BizBaseCurrency currency = new BizBaseCurrency();
        currency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setIdsList(Arrays.asList(0L));
        currency.setIds("0");
        currency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        currency.setId("id");
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyDesc("currencyDesc");
        currency.setDigitsTag("digitsTag");
        currency.setStatus("status");
        currency.setDecimals(new BigDecimal("0.00"));
        currency.setCreateBy("loginName");
        currency.setRemark("remark");
        currency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCurrency> bizBaseCurrencies = Arrays.asList(currency);
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(bizBaseCurrencies);

        // Run the test
        final int result = bizBaseCurrencyServiceImplUnderTest.deleteBizBaseCurrencyByIds(new String[]{"0"});

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:check_currency_point", new HashMap<>(), 0L);
    }

    @Test
    public void testDeleteBizBaseCurrencyByIds_BizBaseCurrencyMapperSelectBizBaseCurrencyListReturnsNoItems() {
        // Setup
        when(mockBizBaseCurrencyMapper.deleteBizBaseCurrencyByIds(any(String[].class))).thenReturn(0);
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseCurrencyServiceImplUnderTest.deleteBizBaseCurrencyByIds(new String[]{"0"});

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:check_currency_point", new HashMap<>(), 0L);
    }

    @Test
    public void testDeleteBizBaseCurrencyById() {
        // Setup
        when(mockBizBaseCurrencyMapper.deleteBizBaseCurrencyById("id")).thenReturn(0);

        // Configure BizBaseCurrencyMapper.selectBizBaseCurrencyList(...).
        final BizBaseCurrency currency = new BizBaseCurrency();
        currency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setIdsList(Arrays.asList(0L));
        currency.setIds("0");
        currency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        currency.setId("id");
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyDesc("currencyDesc");
        currency.setDigitsTag("digitsTag");
        currency.setStatus("status");
        currency.setDecimals(new BigDecimal("0.00"));
        currency.setCreateBy("loginName");
        currency.setRemark("remark");
        currency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCurrency> bizBaseCurrencies = Arrays.asList(currency);
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(bizBaseCurrencies);

        // Run the test
        final int result = bizBaseCurrencyServiceImplUnderTest.deleteBizBaseCurrencyById("id");

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:check_currency_point", new HashMap<>(), 0L);
    }

    @Test
    public void testDeleteBizBaseCurrencyById_BizBaseCurrencyMapperSelectBizBaseCurrencyListReturnsNoItems() {
        // Setup
        when(mockBizBaseCurrencyMapper.deleteBizBaseCurrencyById("id")).thenReturn(0);
        when(mockBizBaseCurrencyMapper.selectBizBaseCurrencyList(any(BizBaseCurrency.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final int result = bizBaseCurrencyServiceImplUnderTest.deleteBizBaseCurrencyById("id");

        // Verify the results
        assertEquals(0, result);
        //verify(mockRedisUtils).set("master:check_currency_point", new HashMap<>(), 0L);
    }

    @Test
    public void testGetPoolCurrencyBySalesOrg() {
        // Setup
        // Configure BizBaseCurrencyMapper.getPoolCurrencyBySalesOrg(...).
        final BizBaseDropDownList bizBaseDropDownList = new BizBaseDropDownList();
        bizBaseDropDownList.setLabel("label");
        bizBaseDropDownList.setValue("value");
        final List<BizBaseDropDownList> bizBaseDropDownLists = Arrays.asList(bizBaseDropDownList);
        when(mockBizBaseCurrencyMapper.getPoolCurrencyBySalesOrg("salesOrgCode")).thenReturn(bizBaseDropDownLists);

        // Run the test
        final List<BizBaseDropDownList> result = bizBaseCurrencyServiceImplUnderTest.getPoolCurrencyBySalesOrg(
                "salesOrgCode");

        // Verify the results
        assertEquals(result, bizBaseDropDownLists);
    }

    @Test
    public void testGetPoolCurrencyBySalesOrg_BizBaseCurrencyMapperReturnsNoItems() {
        // Setup
        when(mockBizBaseCurrencyMapper.getPoolCurrencyBySalesOrg("salesOrgCode")).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseDropDownList> result = bizBaseCurrencyServiceImplUnderTest.getPoolCurrencyBySalesOrg(
                "salesOrgCode");

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetAllCurrency() {
        // Setup
        // Configure BizBaseCurrencyMapper.getAllCurrency(...).
        final BizBaseDropDownList bizBaseDropDownList = new BizBaseDropDownList();
        bizBaseDropDownList.setLabel("label");
        bizBaseDropDownList.setValue("value");
        final List<BizBaseDropDownList> bizBaseDropDownLists = Arrays.asList(bizBaseDropDownList);
        when(mockBizBaseCurrencyMapper.getAllCurrency()).thenReturn(bizBaseDropDownLists);

        // Run the test
        final List<BizBaseDropDownList> result = bizBaseCurrencyServiceImplUnderTest.getAllCurrency();

        // Verify the results
        assertEquals(result, bizBaseDropDownLists);
    }

    @Test
    public void testGetAllCurrency_BizBaseCurrencyMapperReturnsNoItems() {
        // Setup
        when(mockBizBaseCurrencyMapper.getAllCurrency()).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseDropDownList> result = bizBaseCurrencyServiceImplUnderTest.getAllCurrency();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectBizBaseCurrencyByCode() {
        // Setup
        // Configure BizBaseCurrencyManager.selectBizBaseCurrencyByCode(...).
        final BizBaseCurrencyDO bizBaseCurrencyDO = new BizBaseCurrencyDO();
        bizBaseCurrencyDO.setId("id");
        bizBaseCurrencyDO.setCurrencyCode("currencyCode");
        bizBaseCurrencyDO.setCurrencyDesc("currencyDesc");
        bizBaseCurrencyDO.setDigitsTag("digitsTag");
        bizBaseCurrencyDO.setStatus("status");
        bizBaseCurrencyDO.setDecimals(new BigDecimal("0.00"));
        bizBaseCurrencyDO.setRemark("remark");
        when(mockBizBaseCurrencyManager.selectBizBaseCurrencyByCode("currencyCode")).thenReturn(bizBaseCurrencyDO);

        // Run the test
        final BizBaseCurrencyDTO result = bizBaseCurrencyServiceImplUnderTest.selectBizBaseCurrencyByCode(
                "currencyCode");

        // Verify the results
        assertEquals(result.getId(),bizBaseCurrencyDO.getId());
    }

    @Test
    public void testSelectBizBaseCurrencyByCodeList() {
        // Setup
        // Configure BizBaseCurrencyManager.selectBizBaseCurrencyByCodeList(...).
        final BizBaseCurrencyDO bizBaseCurrencyDO = new BizBaseCurrencyDO();
        bizBaseCurrencyDO.setId("id");
        bizBaseCurrencyDO.setCurrencyCode("currencyCode");
        bizBaseCurrencyDO.setCurrencyDesc("currencyDesc");
        bizBaseCurrencyDO.setDigitsTag("digitsTag");
        bizBaseCurrencyDO.setStatus("status");
        bizBaseCurrencyDO.setDecimals(new BigDecimal("0.00"));
        bizBaseCurrencyDO.setRemark("remark");
        final List<BizBaseCurrencyDO> bizBaseCurrencyDOS = Arrays.asList(bizBaseCurrencyDO);
        when(mockBizBaseCurrencyManager.selectBizBaseCurrencyByCodeList(Arrays.asList("value")))
                .thenReturn(bizBaseCurrencyDOS);

        // Run the test
        final List<BizBaseCurrencyDTO> result = bizBaseCurrencyServiceImplUnderTest.selectBizBaseCurrencyByCodeList(
                Arrays.asList("value"));

        // Verify the results
        assertEquals(result.size(), bizBaseCurrencyDOS.size());
    }

    @Test
    public void testSelectBizBaseCurrencyByCodeList_BizBaseCurrencyManagerReturnsNoItems() {
        // Setup
        when(mockBizBaseCurrencyManager.selectBizBaseCurrencyByCodeList(Arrays.asList("value")))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseCurrencyDTO> result = bizBaseCurrencyServiceImplUnderTest.selectBizBaseCurrencyByCodeList(
                Arrays.asList("value"));

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testImportExcel() throws Exception {
        // Setup
        final BizBaseCurrency currency = new BizBaseCurrency();
        currency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setIdsList(Arrays.asList(0L));
        currency.setIds("0");
        currency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        currency.setId("id");
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyDesc("currencyDesc");
        currency.setDigitsTag("digitsTag");
        currency.setStatus("status");
        currency.setDecimals(new BigDecimal("0.00"));
        currency.setCreateBy("loginName");
        currency.setRemark("remark");
        currency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCurrency> bizBaseCurrencies = Arrays.asList(currency);

        // Configure BizBaseCurrencyMapper.selectBaseCurrencyListCheck(...).
        final BizBaseCustomer customer = new BizBaseCustomer();
        customer.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        customer.setIds("0");
        customer.setIdsList(Arrays.asList(0L));
        customer.setId(0L);
        customer.setCustomerNumber("customerNumber");
        customer.setCustomerName("customerName");
        customer.setSalesOrgCode("salesOrgCode");
        customer.setSalesOrgName("salesOrgName");
        customer.setSalesOfficeCode("salesOfficeCode");
        customer.setSalesOfficeName("salesOfficeName");
        customer.setDivisionCode("divisionCode");
        customer.setDivisionName("divisionName");
        customer.setTierType("tierType");
        customer.setStatus("status");
        customer.setCustomerAccountGroup("customerAccountGroup");
        final List<BizBaseCustomer> bizBaseCustomers = Arrays.asList(customer);
        when(mockBizBaseCurrencyMapper.selectBaseCurrencyListCheck(any(BizBaseCurrency.class)))
                .thenReturn(bizBaseCustomers);

        when(mockBizBaseCurrencyMapper.updateBizBaseCurrency(any(BizBaseCurrency.class))).thenReturn(0);
//        when(mockBizBaseCurrencyMapper.insertBizBaseCurrency(any(BizBaseCurrency.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseCurrencyServiceImplUnderTest.importExcel(bizBaseCurrencies, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
//        verify(mockBizBaseCurrencyMapper).updateBizBaseCurrency(any(BizBaseCurrency.class));
//        verify(mockBizBaseCurrencyMapper).insertBizBaseCurrency(any(BizBaseCurrency.class));
    }

    @Test
    public void testImportExcel_BizBaseCurrencyMapperSelectBaseCurrencyListCheckReturnsNoItems() {
        // Setup
        final BizBaseCurrency currency = new BizBaseCurrency();
        currency.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setIdsList(Arrays.asList(0L));
        currency.setIds("0");
        currency.setCurrencyCodeList(new String[]{"currencyCodeList"});
        currency.setId("id");
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyDesc("currencyDesc");
        currency.setDigitsTag("digitsTag");
        currency.setStatus("status");
        currency.setDecimals(new BigDecimal("0.00"));
        currency.setCreateBy("loginName");
        currency.setRemark("remark");
        currency.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        currency.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseCurrency> bizBaseCurrencies = Arrays.asList(currency);
        when(mockBizBaseCurrencyMapper.selectBaseCurrencyListCheck(any(BizBaseCurrency.class)))
                .thenReturn(Collections.emptyList());
//        when(mockBizBaseCurrencyMapper.updateBizBaseCurrency(any(BizBaseCurrency.class))).thenReturn(0);
        when(mockBizBaseCurrencyMapper.insertBizBaseCurrency(any(BizBaseCurrency.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseCurrencyServiceImplUnderTest.importExcel(bizBaseCurrencies, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
//        verify(mockBizBaseCurrencyMapper).updateBizBaseCurrency(any(BizBaseCurrency.class));
//        verify(mockBizBaseCurrencyMapper).insertBizBaseCurrency(any(BizBaseCurrency.class));
    }
}
