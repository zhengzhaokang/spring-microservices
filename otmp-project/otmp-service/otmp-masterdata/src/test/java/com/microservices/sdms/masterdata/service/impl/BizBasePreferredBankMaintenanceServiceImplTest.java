package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.masterdata.domain.BizBasePreferredBankMaintenance;
import com.microservices.otmp.masterdata.mapper.BizBasePreferredBankMaintenanceMapper;
import com.microservices.otmp.masterdata.service.impl.BizBasePreferredBankMaintenanceServiceImpl;
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

@RunWith(MockitoJUnitRunner.class)
public class BizBasePreferredBankMaintenanceServiceImplTest {

    @Mock
    private BizBasePreferredBankMaintenanceMapper mockBizBasePreferredBankMaintenanceMapper;

    @InjectMocks
    private BizBasePreferredBankMaintenanceServiceImpl bizBasePreferredBankMaintenanceServiceImplUnderTest;

    @Test
    public void testSelectBizBasePreferredBankMaintenanceById() {
        // Setup
        final BizBasePreferredBankMaintenance expectedResult = new BizBasePreferredBankMaintenance();
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setDeleteFlag(false);
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setVendorCode("vendorCode");
        expectedResult.setVendorName("vendorName");
        expectedResult.setBankAccount("bankAccount");
        expectedResult.setBankType("bankType");
        expectedResult.setRemark("remark");
        expectedResult.setId(0L);
        expectedResult.setGeo("geo");
        expectedResult.setCreateBy("loginName");
        expectedResult.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setIds("0");
        expectedResult.setIdsList(Arrays.asList(0L));

        // Configure BizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceById(...).
        final BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setDeleteFlag(false);
        bizBasePreferredBankMaintenance.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setVendorCode("vendorCode");
        bizBasePreferredBankMaintenance.setVendorName("vendorName");
        bizBasePreferredBankMaintenance.setBankAccount("bankAccount");
        bizBasePreferredBankMaintenance.setBankType("bankType");
        bizBasePreferredBankMaintenance.setRemark("remark");
        bizBasePreferredBankMaintenance.setId(0L);
        bizBasePreferredBankMaintenance.setGeo("geo");
        bizBasePreferredBankMaintenance.setCreateBy("loginName");
        bizBasePreferredBankMaintenance.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setIds("0");
        bizBasePreferredBankMaintenance.setIdsList(Arrays.asList(0L));
        when(mockBizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceById(0L))
                .thenReturn(bizBasePreferredBankMaintenance);

        // Run the test
        final BizBasePreferredBankMaintenance result = bizBasePreferredBankMaintenanceServiceImplUnderTest.selectBizBasePreferredBankMaintenanceById(
                0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBasePreferredBankMaintenanceList() {
        // Setup
        final BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setDeleteFlag(false);
        bizBasePreferredBankMaintenance.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setVendorCode("vendorCode");
        bizBasePreferredBankMaintenance.setVendorName("vendorName");
        bizBasePreferredBankMaintenance.setBankAccount("bankAccount");
        bizBasePreferredBankMaintenance.setBankType("bankType");
        bizBasePreferredBankMaintenance.setRemark("remark");
        bizBasePreferredBankMaintenance.setId(0L);
        bizBasePreferredBankMaintenance.setGeo("geo");
        bizBasePreferredBankMaintenance.setCreateBy("loginName");
        bizBasePreferredBankMaintenance.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setIds("0");
        bizBasePreferredBankMaintenance.setIdsList(Arrays.asList(0L));

        final BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance1 = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance1.setDeleteFlag(false);
        bizBasePreferredBankMaintenance1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance1.setVendorCode("vendorCode");
        bizBasePreferredBankMaintenance1.setVendorName("vendorName");
        bizBasePreferredBankMaintenance1.setBankAccount("bankAccount");
        bizBasePreferredBankMaintenance1.setBankType("bankType");
        bizBasePreferredBankMaintenance1.setRemark("remark");
        bizBasePreferredBankMaintenance1.setId(0L);
        bizBasePreferredBankMaintenance1.setGeo("geo");
        bizBasePreferredBankMaintenance1.setCreateBy("loginName");
        bizBasePreferredBankMaintenance1.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance1.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance1.setIds("0");
        bizBasePreferredBankMaintenance1.setIdsList(Arrays.asList(0L));
        final List<BizBasePreferredBankMaintenance> expectedResult = Arrays.asList(bizBasePreferredBankMaintenance1);

        // Configure BizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceList(...).
        final BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance2 = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance2.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance2.setDeleteFlag(false);
        bizBasePreferredBankMaintenance2.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance2.setVendorCode("vendorCode");
        bizBasePreferredBankMaintenance2.setVendorName("vendorName");
        bizBasePreferredBankMaintenance2.setBankAccount("bankAccount");
        bizBasePreferredBankMaintenance2.setBankType("bankType");
        bizBasePreferredBankMaintenance2.setRemark("remark");
        bizBasePreferredBankMaintenance2.setId(0L);
        bizBasePreferredBankMaintenance2.setGeo("geo");
        bizBasePreferredBankMaintenance2.setCreateBy("loginName");
        bizBasePreferredBankMaintenance2.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance2.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance2.setIds("0");
        bizBasePreferredBankMaintenance2.setIdsList(Arrays.asList(0L));
        final List<BizBasePreferredBankMaintenance> bizBasePreferredBankMaintenances = Arrays.asList(
                bizBasePreferredBankMaintenance2);
        when(mockBizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceList(
                any(BizBasePreferredBankMaintenance.class))).thenReturn(bizBasePreferredBankMaintenances);

        // Run the test
        final List<BizBasePreferredBankMaintenance> result = bizBasePreferredBankMaintenanceServiceImplUnderTest.selectBizBasePreferredBankMaintenanceList(
                bizBasePreferredBankMaintenance);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBasePreferredBankMaintenanceList_BizBasePreferredBankMaintenanceMapperReturnsNoItems() {
        // Setup
        final BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setDeleteFlag(false);
        bizBasePreferredBankMaintenance.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setVendorCode("vendorCode");
        bizBasePreferredBankMaintenance.setVendorName("vendorName");
        bizBasePreferredBankMaintenance.setBankAccount("bankAccount");
        bizBasePreferredBankMaintenance.setBankType("bankType");
        bizBasePreferredBankMaintenance.setRemark("remark");
        bizBasePreferredBankMaintenance.setId(0L);
        bizBasePreferredBankMaintenance.setGeo("geo");
        bizBasePreferredBankMaintenance.setCreateBy("loginName");
        bizBasePreferredBankMaintenance.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setIds("0");
        bizBasePreferredBankMaintenance.setIdsList(Arrays.asList(0L));

        when(mockBizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceList(
                any(BizBasePreferredBankMaintenance.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBasePreferredBankMaintenance> result = bizBasePreferredBankMaintenanceServiceImplUnderTest.selectBizBasePreferredBankMaintenanceList(
                bizBasePreferredBankMaintenance);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBasePreferredBankMaintenance() {
        // Setup
        final BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setDeleteFlag(false);
        bizBasePreferredBankMaintenance.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setVendorCode("vendorCode");
        bizBasePreferredBankMaintenance.setVendorName("vendorName");
        bizBasePreferredBankMaintenance.setBankAccount("bankAccount");
        bizBasePreferredBankMaintenance.setBankType("bankType");
        bizBasePreferredBankMaintenance.setRemark("remark");
        bizBasePreferredBankMaintenance.setId(0L);
        bizBasePreferredBankMaintenance.setGeo("geo");
        bizBasePreferredBankMaintenance.setCreateBy("loginName");
        bizBasePreferredBankMaintenance.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setIds("0");
        bizBasePreferredBankMaintenance.setIdsList(Arrays.asList(0L));

        when(mockBizBasePreferredBankMaintenanceMapper.insertBizBasePreferredBankMaintenance(
                any(BizBasePreferredBankMaintenance.class))).thenReturn(0);

        // Run the test
        final int result = bizBasePreferredBankMaintenanceServiceImplUnderTest.insertBizBasePreferredBankMaintenance(
                bizBasePreferredBankMaintenance);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBasePreferredBankMaintenance() {
        // Setup
        final BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setDeleteFlag(false);
        bizBasePreferredBankMaintenance.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setVendorCode("vendorCode");
        bizBasePreferredBankMaintenance.setVendorName("vendorName");
        bizBasePreferredBankMaintenance.setBankAccount("bankAccount");
        bizBasePreferredBankMaintenance.setBankType("bankType");
        bizBasePreferredBankMaintenance.setRemark("remark");
        bizBasePreferredBankMaintenance.setId(0L);
        bizBasePreferredBankMaintenance.setGeo("geo");
        bizBasePreferredBankMaintenance.setCreateBy("loginName");
        bizBasePreferredBankMaintenance.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setIds("0");
        bizBasePreferredBankMaintenance.setIdsList(Arrays.asList(0L));

        when(mockBizBasePreferredBankMaintenanceMapper.updateBizBasePreferredBankMaintenance(
                any(BizBasePreferredBankMaintenance.class))).thenReturn(0);

        // Run the test
        final int result = bizBasePreferredBankMaintenanceServiceImplUnderTest.updateBizBasePreferredBankMaintenance(
                bizBasePreferredBankMaintenance);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBasePreferredBankMaintenanceByIds() {
        // Setup
        when(mockBizBasePreferredBankMaintenanceMapper.deleteBizBasePreferredBankMaintenanceByIds(
                any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBasePreferredBankMaintenanceServiceImplUnderTest.deleteBizBasePreferredBankMaintenanceByIds(
                new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBasePreferredBankMaintenanceById() {
        // Setup
        when(mockBizBasePreferredBankMaintenanceMapper.deleteBizBasePreferredBankMaintenanceById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBasePreferredBankMaintenanceServiceImplUnderTest.deleteBizBasePreferredBankMaintenanceById(
                0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() throws Exception {
        // Setup
        final BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setDeleteFlag(false);
        bizBasePreferredBankMaintenance.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setVendorCode("vendorCode");
        bizBasePreferredBankMaintenance.setVendorName("vendorName");
        bizBasePreferredBankMaintenance.setBankAccount("bankAccount");
        bizBasePreferredBankMaintenance.setBankType("bankType");
        bizBasePreferredBankMaintenance.setRemark("remark");
        bizBasePreferredBankMaintenance.setId(0L);
        bizBasePreferredBankMaintenance.setGeo("geo");
        bizBasePreferredBankMaintenance.setCreateBy("loginName");
        bizBasePreferredBankMaintenance.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setIds("0");
        bizBasePreferredBankMaintenance.setIdsList(Arrays.asList(0L));
        final List<BizBasePreferredBankMaintenance> bizs = Arrays.asList(bizBasePreferredBankMaintenance);

        // Configure BizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceList(...).
        final BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance1 = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance1.setDeleteFlag(false);
        bizBasePreferredBankMaintenance1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance1.setVendorCode("vendorCode");
        bizBasePreferredBankMaintenance1.setVendorName("vendorName");
        bizBasePreferredBankMaintenance1.setBankAccount("bankAccount");
        bizBasePreferredBankMaintenance1.setBankType("bankType");
        bizBasePreferredBankMaintenance1.setRemark("remark");
        bizBasePreferredBankMaintenance1.setId(0L);
        bizBasePreferredBankMaintenance1.setGeo("geo");
        bizBasePreferredBankMaintenance1.setCreateBy("loginName");
        bizBasePreferredBankMaintenance1.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance1.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance1.setIds("0");
        bizBasePreferredBankMaintenance1.setIdsList(Arrays.asList(0L));
        final List<BizBasePreferredBankMaintenance> bizBasePreferredBankMaintenances = Arrays.asList(
                bizBasePreferredBankMaintenance1);
        when(mockBizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceList(
                any(BizBasePreferredBankMaintenance.class))).thenReturn(bizBasePreferredBankMaintenances);

//        when(mockBizBasePreferredBankMaintenanceMapper.checkLgvmBank(any(BizBasePreferredBankMaintenance.class)))
//                .thenReturn(0);
        when(mockBizBasePreferredBankMaintenanceMapper.insertBizBasePreferredBankMaintenance(
                any(BizBasePreferredBankMaintenance.class))).thenReturn(0);

        // Run the test
        final ResultDTO<String> result = bizBasePreferredBankMaintenanceServiceImplUnderTest.importExcel(bizs,
                "loginName");
        int i = bizBasePreferredBankMaintenanceServiceImplUnderTest.insertBizBasePreferredBankMaintenance(bizBasePreferredBankMaintenance);

        // Verify the results
//        verify(mockBizBasePreferredBankMaintenanceMapper).insertBizBasePreferredBankMaintenance(
//                new BizBasePreferredBankMaintenance());
        assertEquals(0, i);
    }

    @Test
    public void testImportExcel_BizBasePreferredBankMaintenanceMapperSelectBizBasePreferredBankMaintenanceListReturnsNoItems() {
        // Setup
        final BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setDeleteFlag(false);
        bizBasePreferredBankMaintenance.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setVendorCode("vendorCode");
        bizBasePreferredBankMaintenance.setVendorName("vendorName");
        bizBasePreferredBankMaintenance.setBankAccount("bankAccount");
        bizBasePreferredBankMaintenance.setBankType("bankType");
        bizBasePreferredBankMaintenance.setRemark("remark");
        bizBasePreferredBankMaintenance.setId(0L);
        bizBasePreferredBankMaintenance.setGeo("geo");
        bizBasePreferredBankMaintenance.setCreateBy("loginName");
        bizBasePreferredBankMaintenance.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setIds("0");
        bizBasePreferredBankMaintenance.setIdsList(Arrays.asList(0L));
        final List<BizBasePreferredBankMaintenance> bizs = Arrays.asList(bizBasePreferredBankMaintenance);
        when(mockBizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceList(
                any(BizBasePreferredBankMaintenance.class))).thenReturn(Collections.emptyList());
        when(mockBizBasePreferredBankMaintenanceMapper.checkLgvmBank(any(BizBasePreferredBankMaintenance.class)))
                .thenReturn(0);
//        when(mockBizBasePreferredBankMaintenanceMapper.insertBizBasePreferredBankMaintenance(
//                any(BizBasePreferredBankMaintenance.class))).thenReturn(0);

        // Run the test
        final ResultDTO<String> result = bizBasePreferredBankMaintenanceServiceImplUnderTest.importExcel(bizs,
                "loginName");

        // Verify the results
//        verify(mockBizBasePreferredBankMaintenanceMapper).insertBizBasePreferredBankMaintenance(
//                any(BizBasePreferredBankMaintenance.class));
    }

    @Test
    public void testCheckLgvmBank() {
        // Setup
        final BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setDeleteFlag(false);
        bizBasePreferredBankMaintenance.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setVendorCode("vendorCode");
        bizBasePreferredBankMaintenance.setVendorName("vendorName");
        bizBasePreferredBankMaintenance.setBankAccount("bankAccount");
        bizBasePreferredBankMaintenance.setBankType("bankType");
        bizBasePreferredBankMaintenance.setRemark("remark");
        bizBasePreferredBankMaintenance.setId(0L);
        bizBasePreferredBankMaintenance.setGeo("geo");
        bizBasePreferredBankMaintenance.setCreateBy("loginName");
        bizBasePreferredBankMaintenance.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBasePreferredBankMaintenance.setIds("0");
        bizBasePreferredBankMaintenance.setIdsList(Arrays.asList(0L));

        when(mockBizBasePreferredBankMaintenanceMapper.checkLgvmBank(any(BizBasePreferredBankMaintenance.class)))
                .thenReturn(0);

        // Run the test
        final int result = bizBasePreferredBankMaintenanceServiceImplUnderTest.checkLgvmBank(
                bizBasePreferredBankMaintenance);

        // Verify the results
        assertEquals(0, result);
    }
}
