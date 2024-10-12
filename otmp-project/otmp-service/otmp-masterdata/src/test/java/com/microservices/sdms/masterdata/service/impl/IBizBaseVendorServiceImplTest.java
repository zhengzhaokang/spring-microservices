package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.BizBaseVendor;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseVendorDO;
import com.microservices.otmp.masterdata.manager.IBizBaseVendorManager;
import com.microservices.otmp.masterdata.mapper.BizBaseVendorMapper;
import com.microservices.otmp.masterdata.service.impl.IBizBaseVendorServiceImpl;
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
public class IBizBaseVendorServiceImplTest {

    @Mock
    private BizBaseVendorMapper mockBizBaseVendorMapper;
    @Mock
    private IBizBaseVendorManager mockBizBaseVendorManager;

    @InjectMocks
    private IBizBaseVendorServiceImpl iBizBaseVendorServiceImplUnderTest;

    @Test
    public void testSelectBizBaseVendorById() {
        // Setup
        final BizBaseVendorDTO expectedResult = new BizBaseVendorDTO();
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setStatus("status");
        expectedResult.setBankType("bankType");
        expectedResult.setBankAccount("bankAccount");
        expectedResult.setBankNumber("bankNumber");
        expectedResult.setBankName("bankName");
        expectedResult.setBankCountry("bankCountry");
        expectedResult.setId(0L);
        expectedResult.setVendorCode("vendorCode");
        expectedResult.setVendorName("vendorName");
        expectedResult.setVendorCountry("vendorCountry");
        expectedResult.setState("state");
        expectedResult.setCity("city");
        expectedResult.setVatNumber("vatNumber");
        expectedResult.setCustomerId("customerId");
        expectedResult.setIndiaGstin("indiaGstin");

        // Configure IBizBaseVendorManager.selectBizBaseVendorById(...).
        final BizBaseVendorDO bizBaseVendorDO = new BizBaseVendorDO();
        bizBaseVendorDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorDO.setStatus("status");
        bizBaseVendorDO.setBankType("bankType");
        bizBaseVendorDO.setBankAccount("bankAccount");
        bizBaseVendorDO.setBankNumber("bankNumber");
        bizBaseVendorDO.setBankName("bankName");
        bizBaseVendorDO.setBankCountry("bankCountry");
        bizBaseVendorDO.setId(0L);
        bizBaseVendorDO.setVendorCode("vendorCode");
        bizBaseVendorDO.setVendorName("vendorName");
        bizBaseVendorDO.setVendorCountry("vendorCountry");
        bizBaseVendorDO.setState("state");
        bizBaseVendorDO.setCity("city");
        bizBaseVendorDO.setVatNumber("vatNumber");
        bizBaseVendorDO.setCustomerId("customerId");
        bizBaseVendorDO.setIndiaGstin("indiaGstin");
        when(mockBizBaseVendorManager.selectBizBaseVendorById(0L)).thenReturn(bizBaseVendorDO);

        // Run the test
        final BizBaseVendorDTO result = iBizBaseVendorServiceImplUnderTest.selectBizBaseVendorById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseVendorList() {
        // Setup
        final BizBaseVendorDTO bizBaseVendor = new BizBaseVendorDTO();
        bizBaseVendor.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setStatus("status");
        bizBaseVendor.setBankType("bankType");
        bizBaseVendor.setBankAccount("bankAccount");
        bizBaseVendor.setBankNumber("bankNumber");
        bizBaseVendor.setBankName("bankName");
        bizBaseVendor.setBankCountry("bankCountry");
        bizBaseVendor.setId(0L);
        bizBaseVendor.setVendorCode("vendorCode");
        bizBaseVendor.setVendorName("vendorName");
        bizBaseVendor.setVendorCountry("vendorCountry");
        bizBaseVendor.setState("state");
        bizBaseVendor.setCity("city");
        bizBaseVendor.setVatNumber("vatNumber");
        bizBaseVendor.setCustomerId("customerId");
        bizBaseVendor.setIndiaGstin("indiaGstin");
        bizBaseVendor.setVatNumber("vatNumber");

        final BizBaseVendorDTO bizBaseVendorDTO = new BizBaseVendorDTO();
        bizBaseVendorDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorDTO.setStatus("status");
        bizBaseVendorDTO.setBankType("bankType");
        bizBaseVendorDTO.setBankAccount("bankAccount");
        bizBaseVendorDTO.setBankNumber("bankNumber");
        bizBaseVendorDTO.setBankName("bankName");
        bizBaseVendorDTO.setBankCountry("bankCountry");
        bizBaseVendorDTO.setId(0L);
        bizBaseVendorDTO.setVendorCode("vendorCode");
        bizBaseVendorDTO.setVendorName("vendorName");
        bizBaseVendorDTO.setVendorCountry("vendorCountry");
        bizBaseVendorDTO.setState("state");
        bizBaseVendorDTO.setCity("city");
        bizBaseVendorDTO.setVatNumber("vatNumber");
        bizBaseVendorDTO.setCustomerId("customerId");
        bizBaseVendorDTO.setIndiaGstin("indiaGstin");
        bizBaseVendorDTO.setVatNumber("vatNumber");
        final List<BizBaseVendorDTO> expectedResult = Arrays.asList(bizBaseVendorDTO);

        // Configure IBizBaseVendorManager.selectBizBaseVendorList(...).
        final BizBaseVendorDO bizBaseVendorDO = new BizBaseVendorDO();
        bizBaseVendorDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorDO.setStatus("status");
        bizBaseVendorDO.setBankType("bankType");
        bizBaseVendorDO.setBankAccount("bankAccount");
        bizBaseVendorDO.setBankNumber("bankNumber");
        bizBaseVendorDO.setBankName("bankName");
        bizBaseVendorDO.setBankCountry("bankCountry");
        bizBaseVendorDO.setId(0L);
        bizBaseVendorDO.setVendorCode("vendorCode");
        bizBaseVendorDO.setVendorName("vendorName");
        bizBaseVendorDO.setVendorCountry("vendorCountry");
        bizBaseVendorDO.setState("state");
        bizBaseVendorDO.setCity("city");
        bizBaseVendorDO.setVatNumber("vatNumber");
        bizBaseVendorDO.setCustomerId("customerId");
        bizBaseVendorDO.setIndiaGstin("indiaGstin");
        bizBaseVendorDO.setVatNumber("vatNumber");
        final List<BizBaseVendorDO> bizBaseVendorDOS = Arrays.asList(bizBaseVendorDO);
        when(mockBizBaseVendorManager.selectBizBaseVendorList(any(BizBaseVendorDTO.class))).thenReturn(bizBaseVendorDOS);

        // Run the test
        final List<BizBaseVendorDTO> result = iBizBaseVendorServiceImplUnderTest.selectBizBaseVendorList(bizBaseVendor);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseVendorList_IBizBaseVendorManagerReturnsNoItems() {
        // Setup
        final BizBaseVendorDTO bizBaseVendor = new BizBaseVendorDTO();
        bizBaseVendor.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setStatus("status");
        bizBaseVendor.setBankType("bankType");
        bizBaseVendor.setBankAccount("bankAccount");
        bizBaseVendor.setBankNumber("bankNumber");
        bizBaseVendor.setBankName("bankName");
        bizBaseVendor.setBankCountry("bankCountry");
        bizBaseVendor.setId(0L);
        bizBaseVendor.setVendorCode("vendorCode");
        bizBaseVendor.setVendorName("vendorName");
        bizBaseVendor.setVendorCountry("vendorCountry");
        bizBaseVendor.setState("state");
        bizBaseVendor.setCity("city");
        bizBaseVendor.setVatNumber("vatNumber");

        when(mockBizBaseVendorManager.selectBizBaseVendorList(any(BizBaseVendorDTO.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseVendorDTO> result = iBizBaseVendorServiceImplUnderTest.selectBizBaseVendorList(bizBaseVendor);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseVendor() {
        // Setup
        final BizBaseVendorDTO bizBaseVendor = new BizBaseVendorDTO();
        bizBaseVendor.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setStatus("status");
        bizBaseVendor.setBankType("bankType");
        bizBaseVendor.setBankAccount("bankAccount");
        bizBaseVendor.setBankNumber("bankNumber");
        bizBaseVendor.setBankName("bankName");
        bizBaseVendor.setBankCountry("bankCountry");
        bizBaseVendor.setId(0L);
        bizBaseVendor.setVendorCode("vendorCode");
        bizBaseVendor.setVendorName("vendorName");
        bizBaseVendor.setVendorCountry("vendorCountry");
        bizBaseVendor.setState("state");
        bizBaseVendor.setCity("city");
        bizBaseVendor.setVatNumber("vatNumber");

        when(mockBizBaseVendorManager.insertBizBaseVendor(any(BizBaseVendorDO.class))).thenReturn(0);

        // Run the test
        final int result = iBizBaseVendorServiceImplUnderTest.insertBizBaseVendor(bizBaseVendor);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseVendor() {
        // Setup
        final BizBaseVendorDTO bizBaseVendor = new BizBaseVendorDTO();
        bizBaseVendor.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setStatus("status");
        bizBaseVendor.setBankType("bankType");
        bizBaseVendor.setBankAccount("bankAccount");
        bizBaseVendor.setBankNumber("bankNumber");
        bizBaseVendor.setBankName("bankName");
        bizBaseVendor.setBankCountry("bankCountry");
        bizBaseVendor.setId(0L);
        bizBaseVendor.setVendorCode("vendorCode");
        bizBaseVendor.setVendorName("vendorName");
        bizBaseVendor.setVendorCountry("vendorCountry");
        bizBaseVendor.setState("state");
        bizBaseVendor.setCity("city");
        bizBaseVendor.setVatNumber("vatNumber");

        when(mockBizBaseVendorManager.updateBizBaseVendor(any(BizBaseVendorDO.class))).thenReturn(0);

        // Run the test
        final int result = iBizBaseVendorServiceImplUnderTest.updateBizBaseVendor(bizBaseVendor);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseVendorByIds() {
        // Setup
        when(mockBizBaseVendorManager.deleteBizBaseVendorByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = iBizBaseVendorServiceImplUnderTest.deleteBizBaseVendorByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseVendorById() {
        // Setup
        when(mockBizBaseVendorManager.deleteBizBaseVendorById(0L)).thenReturn(0);

        // Run the test
        final int result = iBizBaseVendorServiceImplUnderTest.deleteBizBaseVendorById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertVendorLgvmKafka() {
        // Setup
        final BizBaseVendor testLgvmdsKafka = new BizBaseVendor();
        testLgvmdsKafka.setVendorName("vendorName");
        testLgvmdsKafka.setId(0);
        testLgvmdsKafka.setVendorCode("vendorCode");
        testLgvmdsKafka.setVendorCountry("vendorCountry");
        testLgvmdsKafka.setState("state");
        testLgvmdsKafka.setCity("city");
        testLgvmdsKafka.setVatNumber("vatNumber");
        testLgvmdsKafka.setAdditionalVendorCategory("additionalVendorCategory");
        testLgvmdsKafka.setPrimaryVendorCategory("primaryVendorCategory");
        testLgvmdsKafka.setTransactionVendorCategories("transactionVendorCategories");
        testLgvmdsKafka.setCustomerId("customerId");
        testLgvmdsKafka.setCompanyCode("companyCode");
        testLgvmdsKafka.setIndiaGstin("indiaGstin");
        testLgvmdsKafka.setTaxNumber1("taxNumber1");
        testLgvmdsKafka.setTaxNumber2("taxNumber2");

        when(mockBizBaseVendorMapper.insertVendorLgvmKafka(any(BizBaseVendor.class))).thenReturn(0);

        // Run the test
        final int result = iBizBaseVendorServiceImplUnderTest.insertVendorLgvmKafka(testLgvmdsKafka);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectCountByVendorCode() {
        // Setup
        when(mockBizBaseVendorMapper.selectCountByVendorCode("vendorCode", "companyCode")).thenReturn(0);

        // Run the test
        final int result = iBizBaseVendorServiceImplUnderTest.selectCountByVendorCode("vendorCode", "companyCode");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateVendor() {
        // Setup
        final BizBaseVendor testLgvmdsKafka = new BizBaseVendor();
        testLgvmdsKafka.setVendorName("vendorName");
        testLgvmdsKafka.setId(0);
        testLgvmdsKafka.setVendorCode("vendorCode");
        testLgvmdsKafka.setVendorCountry("vendorCountry");
        testLgvmdsKafka.setState("state");
        testLgvmdsKafka.setCity("city");
        testLgvmdsKafka.setVatNumber("vatNumber");
        testLgvmdsKafka.setAdditionalVendorCategory("additionalVendorCategory");
        testLgvmdsKafka.setPrimaryVendorCategory("primaryVendorCategory");
        testLgvmdsKafka.setTransactionVendorCategories("transactionVendorCategories");
        testLgvmdsKafka.setCustomerId("customerId");
        testLgvmdsKafka.setCompanyCode("companyCode");
        testLgvmdsKafka.setIndiaGstin("indiaGstin");
        testLgvmdsKafka.setTaxNumber1("taxNumber1");
        testLgvmdsKafka.setTaxNumber2("taxNumber2");

        when(mockBizBaseVendorMapper.updateVendor(any(BizBaseVendor.class))).thenReturn(0);

        // Run the test
        final int result = iBizBaseVendorServiceImplUnderTest.updateVendor(testLgvmdsKafka);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testGetVendorAndBankInfoList() {
        // Setup
        final BizBaseVendorDTO bizBaseVendor = new BizBaseVendorDTO();
        bizBaseVendor.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setStatus("status");
        bizBaseVendor.setBankType("bankType");
        bizBaseVendor.setBankAccount("bankAccount");
        bizBaseVendor.setBankNumber("bankNumber");
        bizBaseVendor.setBankName("bankName");
        bizBaseVendor.setBankCountry("bankCountry");
        bizBaseVendor.setId(0L);
        bizBaseVendor.setVendorCode("vendorCode");
        bizBaseVendor.setVendorName("vendorName");
        bizBaseVendor.setVendorCountry("vendorCountry");
        bizBaseVendor.setState("state");
        bizBaseVendor.setCity("city");
        bizBaseVendor.setVatNumber("vatNumber");

        final BizBaseVendorDTO bizBaseVendorDTO = new BizBaseVendorDTO();
        bizBaseVendorDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorDTO.setStatus("status");
        bizBaseVendorDTO.setBankType("bankType");
        bizBaseVendorDTO.setBankAccount("bankAccount");
        bizBaseVendorDTO.setBankNumber("bankNumber");
        bizBaseVendorDTO.setBankName("bankName");
        bizBaseVendorDTO.setBankCountry("bankCountry");
        bizBaseVendorDTO.setId(0L);
        bizBaseVendorDTO.setVendorCode("vendorCode");
        bizBaseVendorDTO.setVendorName("vendorName");
        bizBaseVendorDTO.setVendorCountry("vendorCountry");
        bizBaseVendorDTO.setState("state");
        bizBaseVendorDTO.setCity("city");
        bizBaseVendorDTO.setVatNumber("vatNumber");
        final List<BizBaseVendorDTO> expectedResult = Arrays.asList(bizBaseVendorDTO);

        // Configure BizBaseVendorMapper.getVendorAndBankInfoList(...).
        final BizBaseVendorDTO bizBaseVendorDTO1 = new BizBaseVendorDTO();
        bizBaseVendorDTO1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorDTO1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorDTO1.setStatus("status");
        bizBaseVendorDTO1.setBankType("bankType");
        bizBaseVendorDTO1.setBankAccount("bankAccount");
        bizBaseVendorDTO1.setBankNumber("bankNumber");
        bizBaseVendorDTO1.setBankName("bankName");
        bizBaseVendorDTO1.setBankCountry("bankCountry");
        bizBaseVendorDTO1.setId(0L);
        bizBaseVendorDTO1.setVendorCode("vendorCode");
        bizBaseVendorDTO1.setVendorName("vendorName");
        bizBaseVendorDTO1.setVendorCountry("vendorCountry");
        bizBaseVendorDTO1.setState("state");
        bizBaseVendorDTO1.setCity("city");
        bizBaseVendorDTO1.setVatNumber("vatNumber");
        final List<BizBaseVendorDTO> bizBaseVendorDTOS = Arrays.asList(bizBaseVendorDTO1);
        when(mockBizBaseVendorMapper.getVendorAndBankInfoList(any(BizBaseVendorDTO.class))).thenReturn(bizBaseVendorDTOS);

        // Run the test
        final List<BizBaseVendorDTO> result = iBizBaseVendorServiceImplUnderTest.getVendorAndBankInfoList(
                bizBaseVendor);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetVendorAndBankInfoList_BizBaseVendorMapperReturnsNoItems() {
        // Setup
        final BizBaseVendorDTO bizBaseVendor = new BizBaseVendorDTO();
        bizBaseVendor.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendor.setStatus("status");
        bizBaseVendor.setBankType("bankType");
        bizBaseVendor.setBankAccount("bankAccount");
        bizBaseVendor.setBankNumber("bankNumber");
        bizBaseVendor.setBankName("bankName");
        bizBaseVendor.setBankCountry("bankCountry");
        bizBaseVendor.setId(0L);
        bizBaseVendor.setVendorCode("vendorCode");
        bizBaseVendor.setVendorName("vendorName");
        bizBaseVendor.setVendorCountry("vendorCountry");
        bizBaseVendor.setState("state");
        bizBaseVendor.setCity("city");
        bizBaseVendor.setVatNumber("vatNumber");

        when(mockBizBaseVendorMapper.getVendorAndBankInfoList(any(BizBaseVendorDTO.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseVendorDTO> result = iBizBaseVendorServiceImplUnderTest.getVendorAndBankInfoList(
                bizBaseVendor);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
