package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.BizBaseExchangeRate;
import com.microservices.otmp.masterdata.domain.dto.BizBaseExchangeRateDTO;
import com.microservices.otmp.masterdata.domain.dto.ExchangeRateDTO;
import com.microservices.otmp.masterdata.mapper.BizBaseExchangeRateMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseExchangeRateServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseExchangeRateServiceImplTest {

    @Mock
    private BizBaseExchangeRateMapper mockBizBaseExchangeRateMapper;

    @InjectMocks
    private BizBaseExchangeRateServiceImpl bizBaseExchangeRateServiceImplUnderTest;

    @Test
    public void testSelectBizBaseExchangeRateById() {
        // Setup
        // Configure BizBaseExchangeRateMapper.selectBizBaseExchangeRateById(...).
        final BizBaseExchangeRate bizBaseExchangeRate = new BizBaseExchangeRate();
        bizBaseExchangeRate.setId(0L);
        bizBaseExchangeRate.setCurrencyCode("currencyCode");
        bizBaseExchangeRate.setRateValue(new BigDecimal(0));
        bizBaseExchangeRate.setStatus("status");
        bizBaseExchangeRate.setRemark("memo");
        bizBaseExchangeRate.setExchangeRateType("exchangeRateType");
        when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateById(0L)).thenReturn(bizBaseExchangeRate);

        // Run the test
        final BizBaseExchangeRate result = bizBaseExchangeRateServiceImplUnderTest.selectBizBaseExchangeRateById(0L);
        assertEquals(bizBaseExchangeRate, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseExchangeRateList() {
        // Setup
        final BizBaseExchangeRate bizBaseExchangeRate = new BizBaseExchangeRate();
        bizBaseExchangeRate.setId(0L);
        bizBaseExchangeRate.setCurrencyCode("currencyCode");
        bizBaseExchangeRate.setRateValue(new BigDecimal(0));
        bizBaseExchangeRate.setStatus("status");
        bizBaseExchangeRate.setRemark("memo");
        bizBaseExchangeRate.setExchangeRateType("exchangeRateType");

        // Configure BizBaseExchangeRateMapper.selectBizBaseExchangeRateList(...).
        final BizBaseExchangeRate bizBaseExchangeRate1 = new BizBaseExchangeRate();
        bizBaseExchangeRate1.setId(0L);
        bizBaseExchangeRate1.setCurrencyCode("currencyCode");
        bizBaseExchangeRate1.setRateValue(new BigDecimal(0));
        
        bizBaseExchangeRate1.setStatus("status");
        bizBaseExchangeRate1.setRemark("memo");
        bizBaseExchangeRate1.setExchangeRateType("exchangeRateType");
        final List<BizBaseExchangeRate> bizBaseExchangeRates = Arrays.asList(bizBaseExchangeRate1);
        when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateList(any(BizBaseExchangeRate.class))).thenReturn(bizBaseExchangeRates);

        // Run the test
        final List<BizBaseExchangeRate> result = bizBaseExchangeRateServiceImplUnderTest.selectBizBaseExchangeRateList(bizBaseExchangeRate);
        assertEquals(bizBaseExchangeRates, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseExchangeRateList_BizBaseExchangeRateMapperReturnsNoItems() {
        // Setup
        final BizBaseExchangeRate bizBaseExchangeRate = new BizBaseExchangeRate();
        bizBaseExchangeRate.setId(0L);
        bizBaseExchangeRate.setCurrencyCode("currencyCode");
        bizBaseExchangeRate.setRateValue(new BigDecimal(0));
        
        bizBaseExchangeRate.setStatus("status");
        bizBaseExchangeRate.setRemark("memo");
        bizBaseExchangeRate.setExchangeRateType("exchangeRateType");

        when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateList(any(BizBaseExchangeRate.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseExchangeRate> result = bizBaseExchangeRateServiceImplUnderTest.selectBizBaseExchangeRateList(bizBaseExchangeRate);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseExchangeRate() {
        // Setup
        final BizBaseExchangeRate bizBaseExchangeRate = new BizBaseExchangeRate();
        bizBaseExchangeRate.setId(0L);
        bizBaseExchangeRate.setCurrencyCode("currencyCode");
        bizBaseExchangeRate.setRateValue(new BigDecimal(0));
        
        bizBaseExchangeRate.setStatus("status");
        bizBaseExchangeRate.setRemark("memo");
        bizBaseExchangeRate.setExchangeRateType("exchangeRateType");

        when(mockBizBaseExchangeRateMapper.insertBizBaseExchangeRate(any(BizBaseExchangeRate.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseExchangeRateServiceImplUnderTest.insertBizBaseExchangeRate(bizBaseExchangeRate);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseExchangeRate() {
        // Setup
        final BizBaseExchangeRate bizBaseExchangeRate = new BizBaseExchangeRate();
        bizBaseExchangeRate.setId(0L);
        bizBaseExchangeRate.setCurrencyCode("currencyCode");
        bizBaseExchangeRate.setRateValue(new BigDecimal(0));
        
        bizBaseExchangeRate.setStatus("status");
        bizBaseExchangeRate.setRemark("memo");
        bizBaseExchangeRate.setExchangeRateType("exchangeRateType");

        when(mockBizBaseExchangeRateMapper.updateBizBaseExchangeRate(any(BizBaseExchangeRate.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseExchangeRateServiceImplUnderTest.updateBizBaseExchangeRate(bizBaseExchangeRate);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseExchangeRateByIds() {
        // Setup
        when(mockBizBaseExchangeRateMapper.deleteBizBaseExchangeRateByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseExchangeRateServiceImplUnderTest.deleteBizBaseExchangeRateByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseExchangeRateById() {
        // Setup
        when(mockBizBaseExchangeRateMapper.deleteBizBaseExchangeRateById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseExchangeRateServiceImplUnderTest.deleteBizBaseExchangeRateById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testGetExchangeRateByRemote() throws Exception {
        // Setup
        final BizBaseExchangeRateDTO expectedResult = new BizBaseExchangeRateDTO();
        expectedResult.setCurrencyCode("fromCurrency");
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setId(0L);
        expectedResult.setIds("ids");
        expectedResult.setIdsList(Arrays.asList(0L));
        expectedResult.setRateValueStr("rateValueStr");
        expectedResult.setRateValue(new BigDecimal("1.00"));
        expectedResult.setRateToCurrency(new BigDecimal("1.00"));
        expectedResult.setRateToUsd(new BigDecimal("1.00"));
        expectedResult.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setExchangeRateType("exchangeRateType");
        expectedResult.setCreateBy("createBy");
        expectedResult.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseExchangeRateMapper.selectBizBaseExchangeRateListByRemote(...).
        final BizBaseExchangeRate exchangeRate = new BizBaseExchangeRate();
        exchangeRate.setCurrencyCode("fromCurrency");
        exchangeRate.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setExchangeRateType("M");

        final BizBaseExchangeRate exchangeRate1 = new BizBaseExchangeRate();
        exchangeRate1.setCurrencyCode("fromCurrency1");
        exchangeRate1.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate1.setExchangeRateType("M");

        final BizBaseExchangeRate exchangeRate2 = new BizBaseExchangeRate();
        exchangeRate2.setCurrencyCode("fromCurrency");
        exchangeRate2.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate2.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate2.setId(0L);
        exchangeRate2.setIds("ids");
        exchangeRate2.setIdsList(Arrays.asList(0L));
        exchangeRate2.setRateValue(new BigDecimal("1.00"));
        exchangeRate2.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate2.setRateDateStr("rateDateStr");
        exchangeRate2.setExchangeRateType("Daily");
        exchangeRate2.setCreateBy("loginName");
        exchangeRate2.setStatus("Y");
        exchangeRate2.setExchangeRateL(new BigDecimal("1.00"));
        exchangeRate2.setExchangeRateU(new BigDecimal("2.00"));
        exchangeRate2.setTargetExchangeRate(new BigDecimal("3.00"));
        final List<BizBaseExchangeRate> bizBaseExchangeRates = Arrays.asList(exchangeRate2);

        final BizBaseExchangeRate exchangeRate3 = new BizBaseExchangeRate();
        exchangeRate3.setCurrencyCode("fromCurrency1");
        exchangeRate3.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate3.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate3.setId(0L);
        exchangeRate3.setIds("ids");
        exchangeRate3.setIdsList(Arrays.asList(0L));
        exchangeRate3.setRateValue(new BigDecimal("2.00"));
        exchangeRate3.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate3.setRateDateStr("rateDateStr");
        exchangeRate3.setExchangeRateType("Daily");
        exchangeRate3.setCreateBy("loginName");
        exchangeRate3.setStatus("Y");
        exchangeRate3.setExchangeRateL(new BigDecimal("4.00"));
        exchangeRate3.setExchangeRateU(new BigDecimal("5.00"));
        exchangeRate3.setTargetExchangeRate(new BigDecimal("6.00"));
        final List<BizBaseExchangeRate> bizBaseExchangeRates1 = Arrays.asList(exchangeRate3);
        when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateListByRemote(exchangeRate))
                .thenReturn(bizBaseExchangeRates);
        when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateListByRemote(exchangeRate1))
                .thenReturn(bizBaseExchangeRates1);

        // Run the test
        final BizBaseExchangeRateDTO result = bizBaseExchangeRateServiceImplUnderTest.getExchangeRateByRemote(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "fromCurrency", "fromCurrency1");

        // Verify the results
        assertNull(result.getId());
    }

    @Test
    public void testGetExchangeRateByRemoteList() throws Exception {
        // Setup
        final ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRateDTO.setFromCurrency("fromCurrency");
        exchangeRateDTO.setToCurrency("fromCurrency");
        final List<ExchangeRateDTO> exchangeRateDTOList = Arrays.asList(exchangeRateDTO);
        final BizBaseExchangeRateDTO bizBaseExchangeRateDTO = new BizBaseExchangeRateDTO();
        bizBaseExchangeRateDTO.setCurrencyCode("fromCurrency");
        bizBaseExchangeRateDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setId(0L);
        bizBaseExchangeRateDTO.setIds("ids");
        bizBaseExchangeRateDTO.setIdsList(Arrays.asList(0L));
        bizBaseExchangeRateDTO.setRateValueStr("rateValueStr");
        bizBaseExchangeRateDTO.setRateValue(new BigDecimal("1.00"));
        bizBaseExchangeRateDTO.setRateToCurrency(new BigDecimal("1.00"));
        bizBaseExchangeRateDTO.setRateToUsd(new BigDecimal("1.00"));
        bizBaseExchangeRateDTO.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setExchangeRateType("exchangeRateType");
        bizBaseExchangeRateDTO.setCreateBy("createBy");
        bizBaseExchangeRateDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseExchangeRateDTO> expectedResult = Arrays.asList(bizBaseExchangeRateDTO);

        // Configure BizBaseExchangeRateMapper.selectBizBaseExchangeRateListByRemote(...).
        final BizBaseExchangeRate exchangeRate = new BizBaseExchangeRate();
        exchangeRate.setCurrencyCode("fromCurrency");
        exchangeRate.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setId(0L);
        exchangeRate.setIds("ids");
        exchangeRate.setIdsList(Arrays.asList(0L));
        exchangeRate.setRateValue(new BigDecimal("1.00"));
        exchangeRate.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setRateDateStr("rateDateStr");
        exchangeRate.setExchangeRateType("Daily");
        exchangeRate.setCreateBy("loginName");
        exchangeRate.setStatus("Y");
        exchangeRate.setExchangeRateL(new BigDecimal("1.00"));
        exchangeRate.setExchangeRateU(new BigDecimal("1.00"));
        exchangeRate.setTargetExchangeRate(new BigDecimal("1.00"));
        final List<BizBaseExchangeRate> bizBaseExchangeRates = Arrays.asList(exchangeRate);
        when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateListByRemote(any(BizBaseExchangeRate.class)))
                .thenReturn(bizBaseExchangeRates);

        // Configure BizBaseExchangeRateMapper.selectBizBaseExchangeRateListNow(...).
        final BizBaseExchangeRate exchangeRate1 = new BizBaseExchangeRate();
        exchangeRate1.setCurrencyCode("fromCurrency");
        exchangeRate1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate1.setId(0L);
        exchangeRate1.setIds("ids");
        exchangeRate1.setIdsList(Arrays.asList(0L));
        exchangeRate1.setRateValue(new BigDecimal("1.00"));
        exchangeRate1.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate1.setRateDateStr("rateDateStr");
        exchangeRate1.setExchangeRateType("Daily");
        exchangeRate1.setCreateBy("loginName");
        exchangeRate1.setStatus("Y");
        exchangeRate1.setExchangeRateL(new BigDecimal("1.00"));
        exchangeRate1.setExchangeRateU(new BigDecimal("1.00"));
        exchangeRate1.setTargetExchangeRate(new BigDecimal("1.00"));
        final List<BizBaseExchangeRate> bizBaseExchangeRates1 = Arrays.asList(exchangeRate1);
        //when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateListNow()).thenReturn(bizBaseExchangeRates1);

        // Run the test
        final List<BizBaseExchangeRateDTO> result = bizBaseExchangeRateServiceImplUnderTest.getExchangeRateByRemoteList(
                exchangeRateDTOList);

        // Verify the results
        assertNull(result.get(0).getId());
    }
    @Test
    public void testGetExchangeRateByRemoteList_BizBaseExchangeRateMapperSelectBizBaseExchangeRateListByRemoteReturnsNoItems() throws Exception {
        // Setup
        final ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRateDTO.setFromCurrency("fromCurrency");
        exchangeRateDTO.setToCurrency("fromCurrency");
        final List<ExchangeRateDTO> exchangeRateDTOList = new ArrayList<>();
        exchangeRateDTOList.add(exchangeRateDTO);
        exchangeRateDTOList.add(exchangeRateDTO);
        final BizBaseExchangeRateDTO bizBaseExchangeRateDTO = new BizBaseExchangeRateDTO();
        bizBaseExchangeRateDTO.setCurrencyCode("fromCurrency");
        bizBaseExchangeRateDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setId(0L);
        bizBaseExchangeRateDTO.setIds("ids");
        bizBaseExchangeRateDTO.setIdsList(Arrays.asList(0L));
        bizBaseExchangeRateDTO.setRateValueStr("rateValueStr");
        bizBaseExchangeRateDTO.setRateValue(new BigDecimal("1.00"));
        bizBaseExchangeRateDTO.setRateToCurrency(new BigDecimal("1.00"));
        bizBaseExchangeRateDTO.setRateToUsd(new BigDecimal("1.00"));
        bizBaseExchangeRateDTO.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setExchangeRateType("exchangeRateType");
        bizBaseExchangeRateDTO.setCreateBy("createBy");
        bizBaseExchangeRateDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseExchangeRateDTO> expectedResult = Arrays.asList(bizBaseExchangeRateDTO);

        // Configure BizBaseExchangeRateMapper.selectBizBaseExchangeRateListByRemote(...).
        final BizBaseExchangeRate exchangeRate = new BizBaseExchangeRate();
        exchangeRate.setCurrencyCode("fromCurrency");
        exchangeRate.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setId(0L);
        exchangeRate.setIds("ids");
        exchangeRate.setIdsList(Arrays.asList(0L));
        exchangeRate.setRateValue(new BigDecimal("1.00"));
        exchangeRate.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setRateDateStr("rateDateStr");
        exchangeRate.setExchangeRateType("Daily");
        exchangeRate.setCreateBy("loginName");
        exchangeRate.setStatus("Y");
        exchangeRate.setExchangeRateL(new BigDecimal("1.00"));
        exchangeRate.setExchangeRateU(new BigDecimal("1.00"));
        exchangeRate.setTargetExchangeRate(new BigDecimal("1.00"));
        final List<BizBaseExchangeRate> bizBaseExchangeRates = Arrays.asList(exchangeRate);
        when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateListByRemote(any(BizBaseExchangeRate.class)))
                .thenReturn(bizBaseExchangeRates);

        // Configure BizBaseExchangeRateMapper.selectBizBaseExchangeRateListNow(...).
        final BizBaseExchangeRate exchangeRate1 = new BizBaseExchangeRate();
        exchangeRate1.setCurrencyCode("fromCurrency");
        exchangeRate1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate1.setId(0L);
        exchangeRate1.setIds("ids");
        exchangeRate1.setIdsList(Arrays.asList(0L));
        exchangeRate1.setRateValue(new BigDecimal("1.00"));
        exchangeRate1.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate1.setRateDateStr("rateDateStr");
        exchangeRate1.setExchangeRateType("Daily");
        exchangeRate1.setCreateBy("loginName");
        exchangeRate1.setStatus("Y");
        exchangeRate1.setExchangeRateL(new BigDecimal("1.00"));
        exchangeRate1.setExchangeRateU(new BigDecimal("1.00"));
        exchangeRate1.setTargetExchangeRate(new BigDecimal("1.00"));
        final List<BizBaseExchangeRate> bizBaseExchangeRates1 = Arrays.asList(exchangeRate1);
        //when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateListNow()).thenReturn(bizBaseExchangeRates1);

        // Run the test
        final List<BizBaseExchangeRateDTO> result = bizBaseExchangeRateServiceImplUnderTest.getExchangeRateByRemoteList(
                exchangeRateDTOList);

        // Verify the results
        assertNull(result.get(0).getId());
    }

    @Test
    public void testGetExchangeRateByNow() throws Exception {
        // Setup
        final ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRateDTO.setFromCurrency("fromCurrency");
        exchangeRateDTO.setToCurrency("fromCurrency");
        final List<ExchangeRateDTO> exchangeRateDTOList = Arrays.asList(exchangeRateDTO);
        final BizBaseExchangeRateDTO bizBaseExchangeRateDTO = new BizBaseExchangeRateDTO();
        bizBaseExchangeRateDTO.setCurrencyCode("fromCurrency");
        bizBaseExchangeRateDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setId(0L);
        bizBaseExchangeRateDTO.setIds("ids");
        bizBaseExchangeRateDTO.setIdsList(Arrays.asList(0L));
        bizBaseExchangeRateDTO.setRateValueStr("rateValueStr");
        bizBaseExchangeRateDTO.setRateValue(new BigDecimal("1.00"));
        bizBaseExchangeRateDTO.setRateToCurrency(new BigDecimal("1.00"));
        bizBaseExchangeRateDTO.setRateToUsd(new BigDecimal("1.00"));
        bizBaseExchangeRateDTO.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setExchangeRateType("exchangeRateType");
        bizBaseExchangeRateDTO.setCreateBy("createBy");
        bizBaseExchangeRateDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseExchangeRateDTO.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseExchangeRateDTO> expectedResult = Arrays.asList(bizBaseExchangeRateDTO);

        // Configure BizBaseExchangeRateMapper.selectBizBaseExchangeRateListNow(...).
        final BizBaseExchangeRate exchangeRate = new BizBaseExchangeRate();
        exchangeRate.setCurrencyCode("fromCurrency");
        exchangeRate.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setId(0L);
        exchangeRate.setIds("ids");
        exchangeRate.setIdsList(Arrays.asList(0L));
        exchangeRate.setRateValue(new BigDecimal("1.00"));
        exchangeRate.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setRateDateStr("rateDateStr");
        exchangeRate.setExchangeRateType("Daily");
        exchangeRate.setCreateBy("loginName");
        exchangeRate.setStatus("Y");
        exchangeRate.setExchangeRateL(new BigDecimal("1.00"));
        exchangeRate.setExchangeRateU(new BigDecimal("1.00"));
        exchangeRate.setTargetExchangeRate(new BigDecimal("1.00"));
        final List<BizBaseExchangeRate> bizBaseExchangeRates = Arrays.asList(exchangeRate);
        when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateListNow()).thenReturn(bizBaseExchangeRates);

        // Run the test
        final List<BizBaseExchangeRateDTO> result = bizBaseExchangeRateServiceImplUnderTest.getExchangeRateByNow(
                exchangeRateDTOList);

        // Verify the results
        assertNull(result.get(0).getId());
    }

    @Test
    public void testImportExcel() throws Exception {
        // Setup
        final BizBaseExchangeRate exchangeRate = new BizBaseExchangeRate();
        exchangeRate.setCurrencyCode("fromCurrency");
        exchangeRate.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setId(0L);
        exchangeRate.setIds("ids");
        exchangeRate.setIdsList(Arrays.asList(0L));
        exchangeRate.setRateValue(new BigDecimal("1.00"));
        exchangeRate.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setRateDateStr("rateDateStr");
        exchangeRate.setExchangeRateType("Daily");
        exchangeRate.setCreateBy("loginName");
        exchangeRate.setStatus("Y");
        exchangeRate.setExchangeRateL(new BigDecimal("1.00"));
        exchangeRate.setExchangeRateU(new BigDecimal("1.00"));
        exchangeRate.setTargetExchangeRate(new BigDecimal("1.00"));
        final List<BizBaseExchangeRate> bizs = Arrays.asList(exchangeRate);

        // Configure BizBaseExchangeRateMapper.selectBizBaseExchangeRateListCheck(...).
        final BizBaseExchangeRate exchangeRate1 = new BizBaseExchangeRate();
        exchangeRate1.setCurrencyCode("fromCurrency");
        exchangeRate1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate1.setId(0L);
        exchangeRate1.setIds("ids");
        exchangeRate1.setIdsList(Arrays.asList(0L));
        exchangeRate1.setRateValue(new BigDecimal("1.00"));
        exchangeRate1.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate1.setRateDateStr("rateDateStr");
        exchangeRate1.setExchangeRateType("Daily");
        exchangeRate1.setCreateBy("loginName");
        exchangeRate1.setStatus("Y");
        exchangeRate1.setExchangeRateL(new BigDecimal("1.00"));
        exchangeRate1.setExchangeRateU(new BigDecimal("1.00"));
        exchangeRate1.setTargetExchangeRate(new BigDecimal("1.00"));
        final List<BizBaseExchangeRate> bizBaseExchangeRates = Arrays.asList(exchangeRate1);
        when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateListCheck(any(BizBaseExchangeRate.class)))
                .thenReturn(bizBaseExchangeRates);

        when(mockBizBaseExchangeRateMapper.updateBizBaseExchangeRate(any(BizBaseExchangeRate.class))).thenReturn(0);
        //when(mockBizBaseExchangeRateMapper.insertBizBaseExchangeRate(any(BizBaseExchangeRate.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseExchangeRateServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockBizBaseExchangeRateMapper).updateBizBaseExchangeRate(any(BizBaseExchangeRate.class));
        //verify(mockBizBaseExchangeRateMapper).insertBizBaseExchangeRate(any(BizBaseExchangeRate.class));
    }

    @Test
    public void testImportExcel_BizBaseExchangeRateMapperSelectBizBaseExchangeRateListCheckReturnsNoItems() {
        // Setup
        final BizBaseExchangeRate exchangeRate = new BizBaseExchangeRate();
        exchangeRate.setCurrencyCode("fromCurrency");
        exchangeRate.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setId(0L);
        exchangeRate.setIds("ids");
        exchangeRate.setIdsList(Arrays.asList(0L));
        exchangeRate.setRateValue(new BigDecimal("1.00"));
        exchangeRate.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        exchangeRate.setRateDateStr("rateDateStr");
        exchangeRate.setExchangeRateType("Daily");
        exchangeRate.setCreateBy("loginName");
        exchangeRate.setStatus("Y");
        exchangeRate.setExchangeRateL(new BigDecimal("1.00"));
        exchangeRate.setExchangeRateU(new BigDecimal("1.00"));
        exchangeRate.setTargetExchangeRate(new BigDecimal("1.00"));
        final List<BizBaseExchangeRate> bizs = Arrays.asList(exchangeRate);
        when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateListCheck(any(BizBaseExchangeRate.class)))
                .thenReturn(Collections.emptyList());
        //when(mockBizBaseExchangeRateMapper.updateBizBaseExchangeRate(any(BizBaseExchangeRate.class))).thenReturn(0);
        when(mockBizBaseExchangeRateMapper.insertBizBaseExchangeRate(any(BizBaseExchangeRate.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseExchangeRateServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        //verify(mockBizBaseExchangeRateMapper).updateBizBaseExchangeRate(new BizBaseExchangeRate());
        verify(mockBizBaseExchangeRateMapper).insertBizBaseExchangeRate(any(BizBaseExchangeRate.class));
    }

//    @Test
//    public void testExchangeRates() throws ParseException {
//        // Setup
//        final BizBaseExchangeRate expectedResult = new BizBaseExchangeRate();
//        expectedResult.setCurrencyCode("fromCurrency");
//        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        expectedResult.setId(0L);
//        expectedResult.setIds("ids");
//        expectedResult.setIdsList(Arrays.asList(0L));
//        expectedResult.setRateValue(new BigDecimal("1.00"));
//        expectedResult.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        expectedResult.setRateDateStr("rateDateStr");
//        expectedResult.setExchangeRateType("Daily");
//        expectedResult.setCreateBy("loginName");
//        expectedResult.setStatus("Y");
//        expectedResult.setExchangeRateL(new BigDecimal("1.00"));
//        expectedResult.setExchangeRateU(new BigDecimal("1.00"));
//        expectedResult.setTargetExchangeRate(new BigDecimal("1.00"));
//
//        // Configure BizBaseExchangeRateMapper.selectBizBaseExchangeRateList(...).
//        final BizBaseExchangeRate exchangeRate = new BizBaseExchangeRate();
//        exchangeRate.setCurrencyCode("fromCurrency");
//        exchangeRate.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        exchangeRate.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        exchangeRate.setId(0L);
//        exchangeRate.setIds("ids");
//        exchangeRate.setIdsList(Arrays.asList(0L));
//        exchangeRate.setRateValue(new BigDecimal("1.00"));
//        exchangeRate.setRateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        exchangeRate.setRateDateStr("rateDateStr");
//        exchangeRate.setExchangeRateType("Daily");
//        exchangeRate.setCreateBy("loginName");
//        exchangeRate.setStatus("Y");
//        exchangeRate.setExchangeRateL(new BigDecimal("1.00"));
//        exchangeRate.setExchangeRateU(new BigDecimal("1.00"));
//        exchangeRate.setTargetExchangeRate(new BigDecimal("1.00"));
//        final List<BizBaseExchangeRate> bizBaseExchangeRates = Arrays.asList(exchangeRate);
//        when(mockBizBaseExchangeRateMapper.selectBizBaseExchangeRateList(any(BizBaseExchangeRate.class)))
//                .thenReturn(bizBaseExchangeRates);
//
//        // Run the test
//        final BizBaseExchangeRate result = bizBaseExchangeRateServiceImplUnderTest.exchangeRates("fromCurrency",
//                "fromCurrency");
//
//        // Verify the results
//        assertEquals(expectedResult.getId(), result.getId());
//    }

}
