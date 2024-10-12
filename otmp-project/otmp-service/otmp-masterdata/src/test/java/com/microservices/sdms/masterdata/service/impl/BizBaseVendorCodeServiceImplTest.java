package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.BizBaseVendorCode;
import com.microservices.otmp.masterdata.mapper.BizBaseVendorCodeMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseVendorCodeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseVendorCodeServiceImplTest {

    @Mock
    private BizBaseVendorCodeMapper mockBizBaseVendorCodeMapper;

    @InjectMocks
    private BizBaseVendorCodeServiceImpl bizBaseVendorCodeServiceImplUnderTest;

    @Test
    public void testSelectBizBaseVendorCodeById() {
        // Setup
        // Configure BizBaseVendorCodeMapper.selectBizBaseVendorCodeById(...).
        final BizBaseVendorCode bizBaseVendorCode = new BizBaseVendorCode();
        bizBaseVendorCode.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode.setId(0L);
        bizBaseVendorCode.setBusinessGroup("businessGroup");
        bizBaseVendorCode.setVendorCode("vendorCode");
        bizBaseVendorCode.setVendorName("vendorName");
        bizBaseVendorCode.setCompanyCode("companyCode");
        bizBaseVendorCode.setStatus("status");
        when(mockBizBaseVendorCodeMapper.selectBizBaseVendorCodeById(0L)).thenReturn(bizBaseVendorCode);

        // Run the test
        final BizBaseVendorCode result = bizBaseVendorCodeServiceImplUnderTest.selectBizBaseVendorCodeById(0L);

        // Verify the results
        assertEquals(result, bizBaseVendorCode);
    }

    @Test
    public void testSelectBizBaseVendorCodeList() {
        // Setup
        final BizBaseVendorCode bizBaseVendorCode = new BizBaseVendorCode();
        bizBaseVendorCode.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode.setId(0L);
        bizBaseVendorCode.setBusinessGroup("businessGroup");
        bizBaseVendorCode.setVendorCode("vendorCode");
        bizBaseVendorCode.setVendorName("vendorName");
        bizBaseVendorCode.setCompanyCode("companyCode");
        bizBaseVendorCode.setStatus("status");

        // Configure BizBaseVendorCodeMapper.selectBizBaseVendorCodeList(...).
        final BizBaseVendorCode bizBaseVendorCode1 = new BizBaseVendorCode();
        bizBaseVendorCode1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode1.setId(0L);
        bizBaseVendorCode1.setBusinessGroup("businessGroup");
        bizBaseVendorCode1.setVendorCode("vendorCode");
        bizBaseVendorCode1.setVendorName("vendorName");
        bizBaseVendorCode1.setCompanyCode("companyCode");
        bizBaseVendorCode1.setStatus("status");
        final List<BizBaseVendorCode> bizBaseVendorCodes = Arrays.asList(bizBaseVendorCode1);
        when(mockBizBaseVendorCodeMapper.selectBizBaseVendorCodeList(any(BizBaseVendorCode.class)))
                .thenReturn(bizBaseVendorCodes);

        // Run the test
        final List<BizBaseVendorCode> result = bizBaseVendorCodeServiceImplUnderTest.selectBizBaseVendorCodeList(
                bizBaseVendorCode);

        // Verify the results
        assertEquals(result, bizBaseVendorCodes);
    }

    @Test
    public void testSelectBizBaseVendorCodeList_BizBaseVendorCodeMapperReturnsNoItems() {
        // Setup
        final BizBaseVendorCode bizBaseVendorCode = new BizBaseVendorCode();
        bizBaseVendorCode.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode.setId(0L);
        bizBaseVendorCode.setBusinessGroup("businessGroup");
        bizBaseVendorCode.setVendorCode("vendorCode");
        bizBaseVendorCode.setVendorName("vendorName");
        bizBaseVendorCode.setCompanyCode("companyCode");
        bizBaseVendorCode.setStatus("status");

        when(mockBizBaseVendorCodeMapper.selectBizBaseVendorCodeList(any(BizBaseVendorCode.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseVendorCode> result = bizBaseVendorCodeServiceImplUnderTest.selectBizBaseVendorCodeList(
                bizBaseVendorCode);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseVendorCode() {
        // Setup
        final BizBaseVendorCode bizBaseVendorCode = new BizBaseVendorCode();
        bizBaseVendorCode.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode.setId(0L);
        bizBaseVendorCode.setBusinessGroup("businessGroup");
        bizBaseVendorCode.setVendorCode("vendorCode");
        bizBaseVendorCode.setVendorName("vendorName");
        bizBaseVendorCode.setCompanyCode("companyCode");
        bizBaseVendorCode.setStatus("status");

        when(mockBizBaseVendorCodeMapper.insertBizBaseVendorCode(any(BizBaseVendorCode.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseVendorCodeServiceImplUnderTest.insertBizBaseVendorCode(bizBaseVendorCode);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseVendorCode() {
        // Setup
        final BizBaseVendorCode bizBaseVendorCode = new BizBaseVendorCode();
        bizBaseVendorCode.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorCode.setId(0L);
        bizBaseVendorCode.setBusinessGroup("businessGroup");
        bizBaseVendorCode.setVendorCode("vendorCode");
        bizBaseVendorCode.setVendorName("vendorName");
        bizBaseVendorCode.setCompanyCode("companyCode");
        bizBaseVendorCode.setStatus("status");

        when(mockBizBaseVendorCodeMapper.updateBizBaseVendorCode(any(BizBaseVendorCode.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseVendorCodeServiceImplUnderTest.updateBizBaseVendorCode(bizBaseVendorCode);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseVendorCodeByIds() {
        // Setup
        when(mockBizBaseVendorCodeMapper.deleteBizBaseVendorCodeByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseVendorCodeServiceImplUnderTest.deleteBizBaseVendorCodeByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseVendorCodeById() {
        // Setup
        when(mockBizBaseVendorCodeMapper.deleteBizBaseVendorCodeById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseVendorCodeServiceImplUnderTest.deleteBizBaseVendorCodeById(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
