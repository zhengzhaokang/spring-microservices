package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.BizBaseParty3rdVendorDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseParty3rdVendorDTO;
import com.microservices.otmp.masterdata.manager.IBizBaseParty3rdVendorManager;
import com.microservices.otmp.masterdata.service.impl.BizBaseParty3rdVendorServiceImpl;
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
public class BizBaseParty3rdVendorServiceImplTest {

    @Mock
    private IBizBaseParty3rdVendorManager mockBizBaseParty3rdVendorManager;

    @InjectMocks
    private BizBaseParty3rdVendorServiceImpl bizBaseParty3rdVendorServiceImplUnderTest;

    @Test
    public void testSelectBizBaseParty3rdVendorById() {
        // Setup
        final BizBaseParty3rdVendorDTO expectedResult = new BizBaseParty3rdVendorDTO();
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setId(0L);
        expectedResult.setGeoCode("geoCode");
        expectedResult.setCountryCode("countryCode");
        expectedResult.setCountryName("countryName");
        expectedResult.setVendorCode("vendorCode");
        expectedResult.setVendorName("vendorName");
        expectedResult.setVendorCountry("vendorCountry");
        expectedResult.setBankType("bankType");
        expectedResult.setBankAccount("bankAccount");
        expectedResult.setBankName("bankName");
        expectedResult.setSwiftCode("swiftCode");

        // Configure IBizBaseParty3rdVendorManager.selectBizBaseParty3rdVendorById(...).
        final BizBaseParty3rdVendorDO bizBaseParty3rdVendorDO = new BizBaseParty3rdVendorDO();
        bizBaseParty3rdVendorDO.setId(0L);
        bizBaseParty3rdVendorDO.setGeoCode("geoCode");
        bizBaseParty3rdVendorDO.setCountryCode("countryCode");
        bizBaseParty3rdVendorDO.setCountryName("countryName");
        bizBaseParty3rdVendorDO.setVendorCode("vendorCode");
        bizBaseParty3rdVendorDO.setVendorName("vendorName");
        bizBaseParty3rdVendorDO.setVendorCountry("vendorCountry");
        bizBaseParty3rdVendorDO.setBankType("bankType");
        bizBaseParty3rdVendorDO.setBankAccount("bankAccount");
        bizBaseParty3rdVendorDO.setBankName("bankName");
        bizBaseParty3rdVendorDO.setSwiftCode("swiftCode");
        when(mockBizBaseParty3rdVendorManager.selectBizBaseParty3rdVendorById(0L)).thenReturn(bizBaseParty3rdVendorDO);

        // Run the test
        final BizBaseParty3rdVendorDTO result = bizBaseParty3rdVendorServiceImplUnderTest.selectBizBaseParty3rdVendorById(
                0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseParty3rdVendorList() {
        // Setup
        final BizBaseParty3rdVendorDTO bizBaseParty3rdVendor = new BizBaseParty3rdVendorDTO();
        bizBaseParty3rdVendor.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseParty3rdVendor.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseParty3rdVendor.setId(0L);
        bizBaseParty3rdVendor.setGeoCode("geoCode");
        bizBaseParty3rdVendor.setCountryCode("countryCode");
        bizBaseParty3rdVendor.setCountryName("countryName");
        bizBaseParty3rdVendor.setVendorCode("vendorCode");
        bizBaseParty3rdVendor.setVendorName("vendorName");
        bizBaseParty3rdVendor.setVendorCountry("vendorCountry");
        bizBaseParty3rdVendor.setBankType("bankType");
        bizBaseParty3rdVendor.setBankAccount("bankAccount");
        bizBaseParty3rdVendor.setBankName("bankName");
        bizBaseParty3rdVendor.setSwiftCode("swiftCode");

        final BizBaseParty3rdVendorDTO bizBaseParty3rdVendorDTO = new BizBaseParty3rdVendorDTO();
        bizBaseParty3rdVendorDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseParty3rdVendorDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseParty3rdVendorDTO.setId(0L);
        bizBaseParty3rdVendorDTO.setGeoCode("geoCode");
        bizBaseParty3rdVendorDTO.setCountryCode("countryCode");
        bizBaseParty3rdVendorDTO.setCountryName("countryName");
        bizBaseParty3rdVendorDTO.setVendorCode("vendorCode");
        bizBaseParty3rdVendorDTO.setVendorName("vendorName");
        bizBaseParty3rdVendorDTO.setVendorCountry("vendorCountry");
        bizBaseParty3rdVendorDTO.setBankType("bankType");
        bizBaseParty3rdVendorDTO.setBankAccount("bankAccount");
        bizBaseParty3rdVendorDTO.setBankName("bankName");
        bizBaseParty3rdVendorDTO.setSwiftCode("swiftCode");
        final List<BizBaseParty3rdVendorDTO> expectedResult = Arrays.asList(bizBaseParty3rdVendorDTO);

        // Configure IBizBaseParty3rdVendorManager.selectBizBaseParty3rdVendorList(...).
        final BizBaseParty3rdVendorDO bizBaseParty3rdVendorDO = new BizBaseParty3rdVendorDO();
        bizBaseParty3rdVendorDO.setId(0L);
        bizBaseParty3rdVendorDO.setGeoCode("geoCode");
        bizBaseParty3rdVendorDO.setCountryCode("countryCode");
        bizBaseParty3rdVendorDO.setCountryName("countryName");
        bizBaseParty3rdVendorDO.setVendorCode("vendorCode");
        bizBaseParty3rdVendorDO.setVendorName("vendorName");
        bizBaseParty3rdVendorDO.setVendorCountry("vendorCountry");
        bizBaseParty3rdVendorDO.setBankType("bankType");
        bizBaseParty3rdVendorDO.setBankAccount("bankAccount");
        bizBaseParty3rdVendorDO.setBankName("bankName");
        bizBaseParty3rdVendorDO.setSwiftCode("swiftCode");
        final List<BizBaseParty3rdVendorDO> bizBaseParty3rdVendorDOS = Arrays.asList(bizBaseParty3rdVendorDO);
        when(mockBizBaseParty3rdVendorManager.selectBizBaseParty3rdVendorList(
                any(BizBaseParty3rdVendorDTO.class))).thenReturn(bizBaseParty3rdVendorDOS);

        // Run the test
        final List<BizBaseParty3rdVendorDTO> result = bizBaseParty3rdVendorServiceImplUnderTest.selectBizBaseParty3rdVendorList(
                bizBaseParty3rdVendor);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseParty3rdVendorList_IBizBaseParty3rdVendorManagerReturnsNoItems() {
        // Setup
        final BizBaseParty3rdVendorDTO bizBaseParty3rdVendor = new BizBaseParty3rdVendorDTO();
        bizBaseParty3rdVendor.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseParty3rdVendor.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseParty3rdVendor.setId(0L);
        bizBaseParty3rdVendor.setGeoCode("geoCode");
        bizBaseParty3rdVendor.setCountryCode("countryCode");
        bizBaseParty3rdVendor.setCountryName("countryName");
        bizBaseParty3rdVendor.setVendorCode("vendorCode");
        bizBaseParty3rdVendor.setVendorName("vendorName");
        bizBaseParty3rdVendor.setVendorCountry("vendorCountry");
        bizBaseParty3rdVendor.setBankType("bankType");
        bizBaseParty3rdVendor.setBankAccount("bankAccount");
        bizBaseParty3rdVendor.setBankName("bankName");
        bizBaseParty3rdVendor.setSwiftCode("swiftCode");

        when(mockBizBaseParty3rdVendorManager.selectBizBaseParty3rdVendorList(
                any(BizBaseParty3rdVendorDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseParty3rdVendorDTO> result = bizBaseParty3rdVendorServiceImplUnderTest.selectBizBaseParty3rdVendorList(
                bizBaseParty3rdVendor);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseParty3rdVendor() {
        // Setup
        final BizBaseParty3rdVendorDTO bizBaseParty3rdVendor = new BizBaseParty3rdVendorDTO();
        bizBaseParty3rdVendor.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseParty3rdVendor.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseParty3rdVendor.setId(0L);
        bizBaseParty3rdVendor.setGeoCode("geoCode");
        bizBaseParty3rdVendor.setCountryCode("countryCode");
        bizBaseParty3rdVendor.setCountryName("countryName");
        bizBaseParty3rdVendor.setVendorCode("vendorCode");
        bizBaseParty3rdVendor.setVendorName("vendorName");
        bizBaseParty3rdVendor.setVendorCountry("vendorCountry");
        bizBaseParty3rdVendor.setBankType("bankType");
        bizBaseParty3rdVendor.setBankAccount("bankAccount");
        bizBaseParty3rdVendor.setBankName("bankName");
        bizBaseParty3rdVendor.setSwiftCode("swiftCode");

        when(mockBizBaseParty3rdVendorManager.insertBizBaseParty3rdVendor(any(BizBaseParty3rdVendorDO.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseParty3rdVendorServiceImplUnderTest.insertBizBaseParty3rdVendor(bizBaseParty3rdVendor);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseParty3rdVendor() {
        // Setup
        final BizBaseParty3rdVendorDTO bizBaseParty3rdVendor = new BizBaseParty3rdVendorDTO();
        bizBaseParty3rdVendor.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseParty3rdVendor.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseParty3rdVendor.setId(0L);
        bizBaseParty3rdVendor.setGeoCode("geoCode");
        bizBaseParty3rdVendor.setCountryCode("countryCode");
        bizBaseParty3rdVendor.setCountryName("countryName");
        bizBaseParty3rdVendor.setVendorCode("vendorCode");
        bizBaseParty3rdVendor.setVendorName("vendorName");
        bizBaseParty3rdVendor.setVendorCountry("vendorCountry");
        bizBaseParty3rdVendor.setBankType("bankType");
        bizBaseParty3rdVendor.setBankAccount("bankAccount");
        bizBaseParty3rdVendor.setBankName("bankName");
        bizBaseParty3rdVendor.setSwiftCode("swiftCode");

        when(mockBizBaseParty3rdVendorManager.updateBizBaseParty3rdVendor(any(BizBaseParty3rdVendorDO.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseParty3rdVendorServiceImplUnderTest.updateBizBaseParty3rdVendor(bizBaseParty3rdVendor);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseParty3rdVendorByIds() {
        // Setup
        when(mockBizBaseParty3rdVendorManager.deleteBizBaseParty3rdVendorByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseParty3rdVendorServiceImplUnderTest.deleteBizBaseParty3rdVendorByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseParty3rdVendorById() {
        // Setup
        when(mockBizBaseParty3rdVendorManager.deleteBizBaseParty3rdVendorById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseParty3rdVendorServiceImplUnderTest.deleteBizBaseParty3rdVendorById(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
