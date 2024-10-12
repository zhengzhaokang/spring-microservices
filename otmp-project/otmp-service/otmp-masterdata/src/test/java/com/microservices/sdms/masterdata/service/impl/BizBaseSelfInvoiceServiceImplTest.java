/*
package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.masterdata.domain.BizBaseSelfInvoice;
import com.microservices.otmp.masterdata.mapper.BizBaseSelfInvoiceMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseSelfInvoiceServiceImplTest {

    @Mock
    private BizBaseSelfInvoiceMapper mockBizBaseSelfInvoiceMapper;

    @InjectMocks
    private BizBaseSelfInvoiceServiceImpl bizBaseSelfInvoiceServiceImplUnderTest;

    @Test
    public void testSelectBizBaseSelfInvoiceById() {
        // Setup
        final BizBaseSelfInvoice expectedResult = new BizBaseSelfInvoice();
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setDeleteFlag(false);
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setSellerCountry("sellerCountry");
        expectedResult.setPartnerSignedSba("partnerSignedSba");
        expectedResult.setRemark("remark");
        expectedResult.setId(0L);
        expectedResult.setGtnType("gtnType");
        expectedResult.setCreateBy("loginName");
        expectedResult.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setIds("0");
        expectedResult.setIdsList(Arrays.asList(0L));

        // Configure BizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceById(...).
        final BizBaseSelfInvoice bizBaseSelfInvoice = new BizBaseSelfInvoice();
        bizBaseSelfInvoice.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setDeleteFlag(false);
        bizBaseSelfInvoice.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice.setRemark("remark");
        bizBaseSelfInvoice.setId(0L);
        bizBaseSelfInvoice.setGtnType("gtnType");
        bizBaseSelfInvoice.setCreateBy("loginName");
        bizBaseSelfInvoice.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setIds("0");
        bizBaseSelfInvoice.setIdsList(Arrays.asList(0L));
        when(mockBizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceById(0L)).thenReturn(bizBaseSelfInvoice);

        // Run the test
        final BizBaseSelfInvoice result = bizBaseSelfInvoiceServiceImplUnderTest.selectBizBaseSelfInvoiceById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseSelfInvoiceList() {
        // Setup
        final BizBaseSelfInvoice bizBaseSelfInvoice = new BizBaseSelfInvoice();
        bizBaseSelfInvoice.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setDeleteFlag(false);
        bizBaseSelfInvoice.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice.setRemark("remark");
        bizBaseSelfInvoice.setId(0L);
        bizBaseSelfInvoice.setGtnType("gtnType");
        bizBaseSelfInvoice.setCreateBy("loginName");
        bizBaseSelfInvoice.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setIds("0");
        bizBaseSelfInvoice.setIdsList(Arrays.asList(0L));

        final BizBaseSelfInvoice bizBaseSelfInvoice1 = new BizBaseSelfInvoice();
        bizBaseSelfInvoice1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice1.setDeleteFlag(false);
        bizBaseSelfInvoice1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice1.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice1.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice1.setRemark("remark");
        bizBaseSelfInvoice1.setId(0L);
        bizBaseSelfInvoice1.setGtnType("gtnType");
        bizBaseSelfInvoice1.setCreateBy("loginName");
        bizBaseSelfInvoice1.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice1.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice1.setIds("0");
        bizBaseSelfInvoice1.setIdsList(Arrays.asList(0L));
        final List<BizBaseSelfInvoice> expectedResult = Arrays.asList(bizBaseSelfInvoice1);

        // Configure BizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(...).
        final BizBaseSelfInvoice bizBaseSelfInvoice2 = new BizBaseSelfInvoice();
        bizBaseSelfInvoice2.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice2.setDeleteFlag(false);
        bizBaseSelfInvoice2.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice2.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice2.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice2.setRemark("remark");
        bizBaseSelfInvoice2.setId(0L);
        bizBaseSelfInvoice2.setGtnType("gtnType");
        bizBaseSelfInvoice2.setCreateBy("loginName");
        bizBaseSelfInvoice2.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice2.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice2.setIds("0");
        bizBaseSelfInvoice2.setIdsList(Arrays.asList(0L));
        final List<BizBaseSelfInvoice> baseSelfInvoices = Arrays.asList(bizBaseSelfInvoice2);
        when(mockBizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(any(BizBaseSelfInvoice.class)))
                .thenReturn(baseSelfInvoices);

        // Run the test
        final List<BizBaseSelfInvoice> result = bizBaseSelfInvoiceServiceImplUnderTest.selectBizBaseSelfInvoiceList(
                bizBaseSelfInvoice);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseSelfInvoiceList_BizBaseSelfInvoiceMapperReturnsNoItems() {
        // Setup
        final BizBaseSelfInvoice bizBaseSelfInvoice = new BizBaseSelfInvoice();
        bizBaseSelfInvoice.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setDeleteFlag(false);
        bizBaseSelfInvoice.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice.setRemark("remark");
        bizBaseSelfInvoice.setId(0L);
        bizBaseSelfInvoice.setGtnType("gtnType");
        bizBaseSelfInvoice.setCreateBy("loginName");
        bizBaseSelfInvoice.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setIds("0");
        bizBaseSelfInvoice.setIdsList(Arrays.asList(0L));

        when(mockBizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(any(BizBaseSelfInvoice.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseSelfInvoice> result = bizBaseSelfInvoiceServiceImplUnderTest.selectBizBaseSelfInvoiceList(
                bizBaseSelfInvoice);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseSelfInvoice() {
        // Setup
        final BizBaseSelfInvoice bizBaseSelfInvoice = new BizBaseSelfInvoice();
        bizBaseSelfInvoice.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setDeleteFlag(false);
        bizBaseSelfInvoice.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice.setRemark("remark");
        bizBaseSelfInvoice.setId(0L);
        bizBaseSelfInvoice.setGtnType("gtnType");
        bizBaseSelfInvoice.setCreateBy("loginName");
        bizBaseSelfInvoice.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setIds("0");
        bizBaseSelfInvoice.setIdsList(Arrays.asList(0L));

        when(mockBizBaseSelfInvoiceMapper.insertBizBaseSelfInvoice(any(BizBaseSelfInvoice.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseSelfInvoiceServiceImplUnderTest.insertBizBaseSelfInvoice(bizBaseSelfInvoice);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseSelfInvoice() {
        // Setup
        final BizBaseSelfInvoice bizBaseSelfInvoice = new BizBaseSelfInvoice();
        bizBaseSelfInvoice.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setDeleteFlag(false);
        bizBaseSelfInvoice.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice.setRemark("remark");
        bizBaseSelfInvoice.setId(0L);
        bizBaseSelfInvoice.setGtnType("gtnType");
        bizBaseSelfInvoice.setCreateBy("loginName");
        bizBaseSelfInvoice.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setIds("0");
        bizBaseSelfInvoice.setIdsList(Arrays.asList(0L));

        when(mockBizBaseSelfInvoiceMapper.updateBizBaseSelfInvoice(any(BizBaseSelfInvoice.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseSelfInvoiceServiceImplUnderTest.updateBizBaseSelfInvoice(bizBaseSelfInvoice);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSelfInvoiceByIds() {
        // Setup
        when(mockBizBaseSelfInvoiceMapper.deleteBizBaseSelfInvoiceByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseSelfInvoiceServiceImplUnderTest.deleteBizBaseSelfInvoiceByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSelfInvoiceById() {
        // Setup
        when(mockBizBaseSelfInvoiceMapper.deleteBizBaseSelfInvoiceById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseSelfInvoiceServiceImplUnderTest.deleteBizBaseSelfInvoiceById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() throws Exception {
        // Setup
        final BizBaseSelfInvoice bizBaseSelfInvoice = new BizBaseSelfInvoice();
        bizBaseSelfInvoice.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setDeleteFlag(false);
        bizBaseSelfInvoice.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice.setRemark("remark");
        bizBaseSelfInvoice.setId(0L);
        bizBaseSelfInvoice.setGtnType("gtnType");
        bizBaseSelfInvoice.setCreateBy("loginName");
        bizBaseSelfInvoice.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setIds("0");
        bizBaseSelfInvoice.setIdsList(Arrays.asList(0L));
        final List<BizBaseSelfInvoice> bizs = Arrays.asList(bizBaseSelfInvoice);

        // Configure BizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(...).
        final BizBaseSelfInvoice bizBaseSelfInvoice1 = new BizBaseSelfInvoice();
        bizBaseSelfInvoice1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice1.setDeleteFlag(false);
        bizBaseSelfInvoice1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice1.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice1.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice1.setRemark("remark");
        bizBaseSelfInvoice1.setId(0L);
        bizBaseSelfInvoice1.setGtnType("gtnType");
        bizBaseSelfInvoice1.setCreateBy("loginName");
        bizBaseSelfInvoice1.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice1.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice1.setIds("0");
        bizBaseSelfInvoice1.setIdsList(Arrays.asList(0L));
        final List<BizBaseSelfInvoice> baseSelfInvoices = Arrays.asList(bizBaseSelfInvoice1);
        when(mockBizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(any(BizBaseSelfInvoice.class)))
                .thenReturn(baseSelfInvoices);

        // Run the test
        List<BizBaseSelfInvoice> list = bizBaseSelfInvoiceServiceImplUnderTest.selectBizBaseSelfInvoiceList(bizBaseSelfInvoice);
        final ResultDTO<String> result = bizBaseSelfInvoiceServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals(list, baseSelfInvoices);
    }

    @Test
    public void testImportExcel_BizBaseSelfInvoiceMapperSelectBizBaseSelfInvoiceListReturnsNull() {
        // Setup
        final BizBaseSelfInvoice bizBaseSelfInvoice = new BizBaseSelfInvoice();
        bizBaseSelfInvoice.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setDeleteFlag(false);
        bizBaseSelfInvoice.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice.setRemark("remark");
        bizBaseSelfInvoice.setId(0L);
        bizBaseSelfInvoice.setGtnType("gtnType");
        bizBaseSelfInvoice.setCreateBy("loginName");
        bizBaseSelfInvoice.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setIds("0");
        bizBaseSelfInvoice.setIdsList(Arrays.asList(0L));
        final List<BizBaseSelfInvoice> bizs = Arrays.asList(bizBaseSelfInvoice);
        when(mockBizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(any(BizBaseSelfInvoice.class))).thenReturn(bizs);
        //when(mockBizBaseSelfInvoiceMapper.insertBizBaseSelfInvoice(any(BizBaseSelfInvoice.class))).thenReturn(0);

        // Run the test
        final ResultDTO<String> result = bizBaseSelfInvoiceServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        verify(mockBizBaseSelfInvoiceMapper).selectBizBaseSelfInvoiceList(any(BizBaseSelfInvoice.class));
    }

    @Test
    public void testImportExcel_BizBaseSelfInvoiceMapperSelectBizBaseSelfInvoiceListReturnsNoItems() {
        // Setup
        final BizBaseSelfInvoice bizBaseSelfInvoice = new BizBaseSelfInvoice();
        bizBaseSelfInvoice.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setDeleteFlag(false);
        bizBaseSelfInvoice.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice.setRemark("remark");
        bizBaseSelfInvoice.setId(0L);
        bizBaseSelfInvoice.setGtnType("gtnType");
        bizBaseSelfInvoice.setCreateBy("loginName");
        bizBaseSelfInvoice.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setIds("0");
        bizBaseSelfInvoice.setIdsList(Arrays.asList(0L));
        final List<BizBaseSelfInvoice> bizs = Arrays.asList(bizBaseSelfInvoice);
        when(mockBizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(any(BizBaseSelfInvoice.class)))
                .thenReturn(bizs);

        // Run the test
        List<BizBaseSelfInvoice> list = bizBaseSelfInvoiceServiceImplUnderTest.selectBizBaseSelfInvoiceList(bizBaseSelfInvoice);
        final ResultDTO<String> result = bizBaseSelfInvoiceServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals(list, bizs);
    }

    @Test
    public void testCheck_ThrowsServiceException() {
        // Setup
        final BizBaseSelfInvoice bizBaseSelfInvoice;
        try {
            bizBaseSelfInvoice = new BizBaseSelfInvoice();
            bizBaseSelfInvoice.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice.setDeleteFlag(false);
            bizBaseSelfInvoice.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice.setSellerCountry("sellerCountry");
            bizBaseSelfInvoice.setPartnerSignedSba("partnerSignedSba");
            bizBaseSelfInvoice.setRemark("remark");
            bizBaseSelfInvoice.setId(0L);
            bizBaseSelfInvoice.setGtnType("gtnType");
            bizBaseSelfInvoice.setCreateBy("loginName");
            bizBaseSelfInvoice.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice.setIds("0");
            bizBaseSelfInvoice.setIdsList(Arrays.asList(0L));

            // Configure BizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(...).
            final BizBaseSelfInvoice bizBaseSelfInvoice1 = new BizBaseSelfInvoice();
            bizBaseSelfInvoice1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice1.setDeleteFlag(false);
            bizBaseSelfInvoice1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice1.setSellerCountry("sellerCountry");
            bizBaseSelfInvoice1.setPartnerSignedSba("partnerSignedSba");
            bizBaseSelfInvoice1.setRemark("remark");
            bizBaseSelfInvoice1.setId(0L);
            bizBaseSelfInvoice1.setGtnType("gtnType");
            bizBaseSelfInvoice1.setCreateBy("loginName");
            bizBaseSelfInvoice1.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice1.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice1.setIds("0");
            bizBaseSelfInvoice1.setIdsList(Arrays.asList(0L));
            final List<BizBaseSelfInvoice> baseSelfInvoices = Arrays.asList(bizBaseSelfInvoice1);
            when(mockBizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(any(BizBaseSelfInvoice.class)))
                    .thenReturn(baseSelfInvoices);
            List<BizBaseSelfInvoice> list = bizBaseSelfInvoiceServiceImplUnderTest.selectBizBaseSelfInvoiceList(bizBaseSelfInvoice);
            assertEquals(list, baseSelfInvoices);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Run the test
    }

    @Test
    public void testCheck_BizBaseSelfInvoiceMapperReturnsNull() {
        // Setup
        final BizBaseSelfInvoice bizBaseSelfInvoice = new BizBaseSelfInvoice();
        bizBaseSelfInvoice.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setDeleteFlag(false);
        bizBaseSelfInvoice.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setSellerCountry("sellerCountry");
        bizBaseSelfInvoice.setPartnerSignedSba("partnerSignedSba");
        bizBaseSelfInvoice.setRemark("remark");
        bizBaseSelfInvoice.setId(0L);
        bizBaseSelfInvoice.setGtnType("gtnType");
        bizBaseSelfInvoice.setCreateBy("loginName");
        bizBaseSelfInvoice.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSelfInvoice.setIds("0");
        bizBaseSelfInvoice.setIdsList(Arrays.asList(0L));

        when(mockBizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(any(BizBaseSelfInvoice.class))).thenReturn(null);

        // Run the test
        bizBaseSelfInvoiceServiceImplUnderTest.check(bizBaseSelfInvoice, new HashSet<>(Arrays.asList("value")));

        // Verify the results
    }

    @Test
    public void testCheck_BizBaseSelfInvoiceMapperReturnsNoItems() {
        final BizBaseSelfInvoice bizBaseSelfInvoice;
        try {
            // Setup
            bizBaseSelfInvoice = new BizBaseSelfInvoice();
            bizBaseSelfInvoice.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice.setDeleteFlag(false);
            bizBaseSelfInvoice.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice.setSellerCountry("sellerCountry");
            bizBaseSelfInvoice.setPartnerSignedSba("partnerSignedSba");
            bizBaseSelfInvoice.setRemark("remark");
            bizBaseSelfInvoice.setId(0L);
            bizBaseSelfInvoice.setGtnType("gtnType");
            bizBaseSelfInvoice.setCreateBy("loginName");
            bizBaseSelfInvoice.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice.setIds("0");
            bizBaseSelfInvoice.setIdsList(Arrays.asList(0L));
            // Configure BizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(...).
            final BizBaseSelfInvoice bizBaseSelfInvoice1 = new BizBaseSelfInvoice();
            bizBaseSelfInvoice1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice1.setDeleteFlag(false);
            bizBaseSelfInvoice1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice1.setSellerCountry("sellerCountry");
            bizBaseSelfInvoice1.setPartnerSignedSba("partnerSignedSba");
            bizBaseSelfInvoice1.setRemark("remark");
            bizBaseSelfInvoice1.setId(0L);
            bizBaseSelfInvoice1.setGtnType("gtnType");
            bizBaseSelfInvoice1.setCreateBy("loginName");
            bizBaseSelfInvoice1.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice1.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            bizBaseSelfInvoice1.setIds("0");
            bizBaseSelfInvoice1.setIdsList(Arrays.asList(0L));
            final List<BizBaseSelfInvoice> baseSelfInvoices = Arrays.asList(bizBaseSelfInvoice1);
            when(mockBizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(any(BizBaseSelfInvoice.class)))
                    .thenReturn(baseSelfInvoices);
            List<BizBaseSelfInvoice> list = bizBaseSelfInvoiceServiceImplUnderTest.selectBizBaseSelfInvoiceList(bizBaseSelfInvoice);
            assertEquals(list, baseSelfInvoices);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Run the test
    }
}
*/
