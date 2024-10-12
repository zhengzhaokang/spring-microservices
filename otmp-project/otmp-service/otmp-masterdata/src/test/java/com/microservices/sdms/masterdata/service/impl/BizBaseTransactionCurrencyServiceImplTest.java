package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.BizBaseTransactionCurrency;
import com.microservices.otmp.masterdata.mapper.BizBaseTransactionCurrencyMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseTransactionCurrencyServiceImpl;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseTransactionCurrencyServiceImplTest {

    @Mock
    private BizBaseTransactionCurrencyMapper mockBizBaseTransactionCurrencyMapper;

    @InjectMocks
    private BizBaseTransactionCurrencyServiceImpl bizBaseTransactionCurrencyServiceImplUnderTest;

    @Test
    public void testSelectBizBaseTransactionCurrencyById() {
        // Setup
        // Configure BizBaseTransactionCurrencyMapper.selectBizBaseTransactionCurrencyById(...).
        final BizBaseTransactionCurrency bizBaseTransactionCurrency = new BizBaseTransactionCurrency();
        bizBaseTransactionCurrency.setId(0L);
        bizBaseTransactionCurrency.setTransactionCurrencyCode("transactionCurrencyCode");
        bizBaseTransactionCurrency.setTransactionCurrencyName("transactionCurrencyName");
        when(mockBizBaseTransactionCurrencyMapper.selectBizBaseTransactionCurrencyById(0L)).thenReturn(bizBaseTransactionCurrency);

        // Run the test
        final BizBaseTransactionCurrency result = bizBaseTransactionCurrencyServiceImplUnderTest.selectBizBaseTransactionCurrencyById(0L);
        assertEquals(bizBaseTransactionCurrency, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseTransactionCurrencyList() {
        // Setup
        final BizBaseTransactionCurrency bizBaseTransactionCurrency = new BizBaseTransactionCurrency();
        bizBaseTransactionCurrency.setId(0L);
        bizBaseTransactionCurrency.setTransactionCurrencyCode("transactionCurrencyCode");
        bizBaseTransactionCurrency.setTransactionCurrencyName("transactionCurrencyName");

        // Configure BizBaseTransactionCurrencyMapper.selectBizBaseTransactionCurrencyList(...).
        final BizBaseTransactionCurrency bizBaseTransactionCurrency1 = new BizBaseTransactionCurrency();
        bizBaseTransactionCurrency1.setId(0L);
        bizBaseTransactionCurrency1.setTransactionCurrencyCode("transactionCurrencyCode");
        bizBaseTransactionCurrency1.setTransactionCurrencyName("transactionCurrencyName");
        final List<BizBaseTransactionCurrency> bizBaseTransactionCurrencies = Arrays.asList(bizBaseTransactionCurrency1);
        when(mockBizBaseTransactionCurrencyMapper.selectBizBaseTransactionCurrencyList(any(BizBaseTransactionCurrency.class))).thenReturn(bizBaseTransactionCurrencies);

        // Run the test
        final List<BizBaseTransactionCurrency> result = bizBaseTransactionCurrencyServiceImplUnderTest.selectBizBaseTransactionCurrencyList(bizBaseTransactionCurrency);
        assertEquals(bizBaseTransactionCurrencies, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseTransactionCurrencyList_BizBaseTransactionCurrencyMapperReturnsNoItems() {
        // Setup
        final BizBaseTransactionCurrency bizBaseTransactionCurrency = new BizBaseTransactionCurrency();
        bizBaseTransactionCurrency.setId(0L);
        bizBaseTransactionCurrency.setTransactionCurrencyCode("transactionCurrencyCode");
        bizBaseTransactionCurrency.setTransactionCurrencyName("transactionCurrencyName");

        when(mockBizBaseTransactionCurrencyMapper.selectBizBaseTransactionCurrencyList(any(BizBaseTransactionCurrency.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseTransactionCurrency> result = bizBaseTransactionCurrencyServiceImplUnderTest.selectBizBaseTransactionCurrencyList(bizBaseTransactionCurrency);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseTransactionCurrency() {
        // Setup
        final BizBaseTransactionCurrency bizBaseTransactionCurrency = new BizBaseTransactionCurrency();
        bizBaseTransactionCurrency.setId(0L);
        bizBaseTransactionCurrency.setTransactionCurrencyCode("transactionCurrencyCode");
        bizBaseTransactionCurrency.setTransactionCurrencyName("transactionCurrencyName");

        when(mockBizBaseTransactionCurrencyMapper.insertBizBaseTransactionCurrency(any(BizBaseTransactionCurrency.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseTransactionCurrencyServiceImplUnderTest.insertBizBaseTransactionCurrency(bizBaseTransactionCurrency);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseTransactionCurrency() {
        // Setup
        final BizBaseTransactionCurrency bizBaseTransactionCurrency = new BizBaseTransactionCurrency();
        bizBaseTransactionCurrency.setId(0L);
        bizBaseTransactionCurrency.setTransactionCurrencyCode("transactionCurrencyCode");
        bizBaseTransactionCurrency.setTransactionCurrencyName("transactionCurrencyName");

        when(mockBizBaseTransactionCurrencyMapper.updateBizBaseTransactionCurrency(any(BizBaseTransactionCurrency.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseTransactionCurrencyServiceImplUnderTest.updateBizBaseTransactionCurrency(bizBaseTransactionCurrency);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseTransactionCurrencyByIds() {
        // Setup
        when(mockBizBaseTransactionCurrencyMapper.deleteBizBaseTransactionCurrencyByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseTransactionCurrencyServiceImplUnderTest.deleteBizBaseTransactionCurrencyByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseTransactionCurrencyById() {
        // Setup
        when(mockBizBaseTransactionCurrencyMapper.deleteBizBaseTransactionCurrencyById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseTransactionCurrencyServiceImplUnderTest.deleteBizBaseTransactionCurrencyById(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
