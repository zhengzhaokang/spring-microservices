package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.dto.BizBaseMbgCustomerDrmTomsTofiDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseMbgCustomerDrmTomsTofiDO;
import com.microservices.otmp.masterdata.manager.IBizBaseMbgCustomerDrmTomsTofiManager;
import com.microservices.otmp.masterdata.service.impl.BizBaseMbgCustomerDrmTomsTofiServiceImpl;
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
public class BizBaseMbgCustomerDrmTomsTofiServiceImplTest {

    @Mock
    private IBizBaseMbgCustomerDrmTomsTofiManager mockBizBaseMbgCustomerDrmTomsTofiManager;

    @InjectMocks
    private BizBaseMbgCustomerDrmTomsTofiServiceImpl bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest;

    @Test
    public void testSelectBizBaseMbgCustomerDrmTomsTofiById() {
        // Setup
        final BizBaseMbgCustomerDrmTomsTofiDTO expectedResult = new BizBaseMbgCustomerDrmTomsTofiDTO();
        expectedResult.setId(0L);
        expectedResult.setBusinessGroup("businessGroup");
        expectedResult.setGeoCode("geoCode");
        expectedResult.setRegionMarketCode("regionMarketCode");
        expectedResult.setRegionMarketName("regionMarketName");
        expectedResult.setSalesOrgCode("salesOrgCode");
        expectedResult.setSalesOrgName("salesOrgName");
        expectedResult.setSalesOfficeCode("salesOfficeCode");
        expectedResult.setSalesOfficeName("salesOfficeName");
        expectedResult.setCountryCode("countryCode");
        expectedResult.setCountryName("countryName");
        expectedResult.setCustomerNumber("customerNumber");
        expectedResult.setCustomerName("customerName");
        expectedResult.setKeyAccount("keyAccount");
        expectedResult.setKeyAccountDesc("keyAccountDesc");

        // Configure IBizBaseMbgCustomerDrmTomsTofiManager.selectBizBaseMbgCustomerDrmTomsTofiById(...).
        final BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofiDO = new BizBaseMbgCustomerDrmTomsTofiDO();
        bizBaseMbgCustomerDrmTomsTofiDO.setId(0L);
        bizBaseMbgCustomerDrmTomsTofiDO.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofiDO.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofiDO.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofiDO.setCustomerNumber("customerNumber");
        bizBaseMbgCustomerDrmTomsTofiDO.setCustomerName("customerName");
        bizBaseMbgCustomerDrmTomsTofiDO.setKeyAccount("keyAccount");
        bizBaseMbgCustomerDrmTomsTofiDO.setKeyAccountDesc("keyAccountDesc");
        when(mockBizBaseMbgCustomerDrmTomsTofiManager.selectBizBaseMbgCustomerDrmTomsTofiById(0L))
                .thenReturn(bizBaseMbgCustomerDrmTomsTofiDO);

        // Run the test
        final BizBaseMbgCustomerDrmTomsTofiDTO result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.selectBizBaseMbgCustomerDrmTomsTofiById(
                0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseMbgCustomerDrmTomsTofiList() {
        // Setup
        final BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi = new BizBaseMbgCustomerDrmTomsTofiDTO();
        bizBaseMbgCustomerDrmTomsTofi.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setId(0L);
        bizBaseMbgCustomerDrmTomsTofi.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofi.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofi.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofi.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofi.setCustomerNumber("customerNumber");
//        bizBaseMbgCustomerDrmTomsTofi.setTempField("tempField");

        final BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofiDTO = new BizBaseMbgCustomerDrmTomsTofiDTO();
        bizBaseMbgCustomerDrmTomsTofiDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofiDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofiDTO.setId(0L);
        bizBaseMbgCustomerDrmTomsTofiDTO.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofiDTO.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofiDTO.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofiDTO.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofiDTO.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofiDTO.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofiDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofiDTO.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofiDTO.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofiDTO.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofiDTO.setCustomerNumber("customerNumber");
//        bizBaseMbgCustomerDrmTomsTofiDTO.setTempField("tempField");
        final List<BizBaseMbgCustomerDrmTomsTofiDTO> expectedResult = Arrays.asList(bizBaseMbgCustomerDrmTomsTofiDTO);

        // Configure IBizBaseMbgCustomerDrmTomsTofiManager.selectBizBaseMbgCustomerDrmTomsTofiList(...).
        final BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofiDO = new BizBaseMbgCustomerDrmTomsTofiDO();
        bizBaseMbgCustomerDrmTomsTofiDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofiDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofiDO.setId(0L);
        bizBaseMbgCustomerDrmTomsTofiDO.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofiDO.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofiDO.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofiDO.setCustomerNumber("customerNumber");
        final List<BizBaseMbgCustomerDrmTomsTofiDO> bizBaseMbgCustomerDrmTomsTofiDOS = Arrays.asList(
                bizBaseMbgCustomerDrmTomsTofiDO);
        when(mockBizBaseMbgCustomerDrmTomsTofiManager.selectBizBaseMbgCustomerDrmTomsTofiList(
                any(BizBaseMbgCustomerDrmTomsTofiDTO.class))).thenReturn(bizBaseMbgCustomerDrmTomsTofiDOS);

        // Run the test
        final List<BizBaseMbgCustomerDrmTomsTofiDTO> result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.selectBizBaseMbgCustomerDrmTomsTofiList(
                bizBaseMbgCustomerDrmTomsTofi);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseMbgCustomerDrmTomsTofiList_IBizBaseMbgCustomerDrmTomsTofiManagerReturnsNoItems() {
        // Setup
        final BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi = new BizBaseMbgCustomerDrmTomsTofiDTO();
        bizBaseMbgCustomerDrmTomsTofi.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setId(0L);
        bizBaseMbgCustomerDrmTomsTofi.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofi.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofi.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofi.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofi.setCustomerNumber("customerNumber");
        bizBaseMbgCustomerDrmTomsTofi.setTempField("tempField");

        when(mockBizBaseMbgCustomerDrmTomsTofiManager.selectBizBaseMbgCustomerDrmTomsTofiList(
                any(BizBaseMbgCustomerDrmTomsTofiDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseMbgCustomerDrmTomsTofiDTO> result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.selectBizBaseMbgCustomerDrmTomsTofiList(
                bizBaseMbgCustomerDrmTomsTofi);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseMbgCustomerDrmTomsTofi() {
        // Setup
        final BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi = new BizBaseMbgCustomerDrmTomsTofiDTO();
        bizBaseMbgCustomerDrmTomsTofi.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setId(0L);
        bizBaseMbgCustomerDrmTomsTofi.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofi.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofi.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofi.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofi.setCustomerNumber("customerNumber");
        bizBaseMbgCustomerDrmTomsTofi.setTempField("tempField");

//        when(mockBizBaseMbgCustomerDrmTomsTofiManager.insertBizBaseMbgCustomerDrmTomsTofi(
//                new BizBaseMbgCustomerDrmTomsTofiDO())).thenReturn(0);

        // Run the test
        final int result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.insertBizBaseMbgCustomerDrmTomsTofi(
                bizBaseMbgCustomerDrmTomsTofi);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseMbgCustomerDrmTomsTofi() {
        // Setup
        final BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi = new BizBaseMbgCustomerDrmTomsTofiDTO();
        bizBaseMbgCustomerDrmTomsTofi.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setId(0L);
        bizBaseMbgCustomerDrmTomsTofi.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofi.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofi.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofi.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofi.setCustomerNumber("customerNumber");
        bizBaseMbgCustomerDrmTomsTofi.setTempField("tempField");

//        when(mockBizBaseMbgCustomerDrmTomsTofiManager.updateBizBaseMbgCustomerDrmTomsTofi(
//                new BizBaseMbgCustomerDrmTomsTofiDO())).thenReturn(0);

        // Run the test
        final int result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.updateBizBaseMbgCustomerDrmTomsTofi(
                bizBaseMbgCustomerDrmTomsTofi);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseMbgCustomerDrmTomsTofiByIds() {
        // Setup
        when(mockBizBaseMbgCustomerDrmTomsTofiManager.deleteBizBaseMbgCustomerDrmTomsTofiByIds(
                any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.deleteBizBaseMbgCustomerDrmTomsTofiByIds(
                new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseMbgCustomerDrmTomsTofiById() {
        // Setup
        when(mockBizBaseMbgCustomerDrmTomsTofiManager.deleteBizBaseMbgCustomerDrmTomsTofiById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.deleteBizBaseMbgCustomerDrmTomsTofiById(
                0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testGetByCustomerNumber() {
        // Setup
        final BizBaseMbgCustomerDrmTomsTofiDTO expectedResult = new BizBaseMbgCustomerDrmTomsTofiDTO();
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setId(0L);
        expectedResult.setBusinessGroup("businessGroup");
        expectedResult.setGeoCode("geoCode");
        expectedResult.setRegionMarketCode("regionMarketCode");
        expectedResult.setRegionMarketName("regionMarketName");
        expectedResult.setSalesOrgCode("salesOrgCode");
        expectedResult.setSalesOrgName("salesOrgName");
        expectedResult.setSalesOfficeCode("salesOfficeCode");
        expectedResult.setSalesOfficeName("salesOfficeName");
        expectedResult.setCountryCode("countryCode");
        expectedResult.setCountryName("countryName");
        expectedResult.setCustomerNumber("customerNumber");
//        expectedResult.setTempField("tempField");

        // Configure IBizBaseMbgCustomerDrmTomsTofiManager.getByCustomerNumber(...).
        final BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofiDO = new BizBaseMbgCustomerDrmTomsTofiDO();
        bizBaseMbgCustomerDrmTomsTofiDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofiDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofiDO.setId(0L);
        bizBaseMbgCustomerDrmTomsTofiDO.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofiDO.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofiDO.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofiDO.setCustomerNumber("customerNumber");
        when(mockBizBaseMbgCustomerDrmTomsTofiManager.getByCustomerNumber(any(String.class),
                any(String.class))).thenReturn(bizBaseMbgCustomerDrmTomsTofiDO);

        // Run the test
        final BizBaseMbgCustomerDrmTomsTofiDTO result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.getByCustomerNumber(
                "customerNumber", "businessGroup");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetByCustomerNumber_IBizBaseMbgCustomerDrmTomsTofiManagerReturnsNull() {
        // Setup
        when(mockBizBaseMbgCustomerDrmTomsTofiManager.getByCustomerNumber("customerNumber",
                "businessGroup")).thenReturn(null);

        // Run the test
        final BizBaseMbgCustomerDrmTomsTofiDTO result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.getByCustomerNumber(
                "customerNumber", "businessGroup");

        // Verify the results
        assertNull(result);
    }

    @Test
    public void testGetDropDownList() {
        // Setup
        final BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi = new BizBaseMbgCustomerDrmTomsTofiDTO();
        bizBaseMbgCustomerDrmTomsTofi.setId(0L);
        bizBaseMbgCustomerDrmTomsTofi.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofi.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofi.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofi.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofi.setCustomerNumber("customerNumber");
        bizBaseMbgCustomerDrmTomsTofi.setCustomerName("customerName");
        bizBaseMbgCustomerDrmTomsTofi.setKeyAccount("keyAccount");
        bizBaseMbgCustomerDrmTomsTofi.setKeyAccountDesc("keyAccountDesc");
        bizBaseMbgCustomerDrmTomsTofi.setTempField("key_account");

        final BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofiDO = new BizBaseMbgCustomerDrmTomsTofiDO();
        bizBaseMbgCustomerDrmTomsTofiDO.setId(0L);
        bizBaseMbgCustomerDrmTomsTofiDO.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofiDO.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofiDO.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofiDO.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofiDO.setCustomerNumber("customerNumber");
        bizBaseMbgCustomerDrmTomsTofiDO.setCustomerName("customerName");
        bizBaseMbgCustomerDrmTomsTofiDO.setKeyAccount("keyAccount");
        bizBaseMbgCustomerDrmTomsTofiDO.setKeyAccountDesc("keyAccountDesc");
        final List<BizBaseMbgCustomerDrmTomsTofiDO> expectedResult = Arrays.asList(bizBaseMbgCustomerDrmTomsTofiDO);

        // Configure IBizBaseMbgCustomerDrmTomsTofiManager.getDropDownListByKeyAccount(...).
        final BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofiDO1 = new BizBaseMbgCustomerDrmTomsTofiDO();
        bizBaseMbgCustomerDrmTomsTofiDO1.setId(0L);
        bizBaseMbgCustomerDrmTomsTofiDO1.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofiDO1.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofiDO1.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofiDO1.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofiDO1.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofiDO1.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofiDO1.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofiDO1.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofiDO1.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofiDO1.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofiDO1.setCustomerNumber("customerNumber");
        bizBaseMbgCustomerDrmTomsTofiDO1.setCustomerName("customerName");
        bizBaseMbgCustomerDrmTomsTofiDO1.setKeyAccount("keyAccount");
        bizBaseMbgCustomerDrmTomsTofiDO1.setKeyAccountDesc("keyAccountDesc");
        final List<BizBaseMbgCustomerDrmTomsTofiDO> bizBaseMbgCustomerDrmTomsTofiDOS = Arrays.asList(
                bizBaseMbgCustomerDrmTomsTofiDO1);
//        when(mockBizBaseMbgCustomerDrmTomsTofiManager.getDropDownListByKeyAccount(
//                new BizBaseMbgCustomerDrmTomsTofiDTO())).thenReturn(bizBaseMbgCustomerDrmTomsTofiDOS);

        // Configure IBizBaseMbgCustomerDrmTomsTofiManager.getDropDownListByCustomerGroup(...).
        final BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofiDO2 = new BizBaseMbgCustomerDrmTomsTofiDO();
        bizBaseMbgCustomerDrmTomsTofiDO2.setId(0L);
        bizBaseMbgCustomerDrmTomsTofiDO2.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofiDO2.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofiDO2.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofiDO2.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofiDO2.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofiDO2.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofiDO2.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofiDO2.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofiDO2.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofiDO2.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofiDO2.setCustomerNumber("customerNumber");
        bizBaseMbgCustomerDrmTomsTofiDO2.setCustomerName("customerName");
        bizBaseMbgCustomerDrmTomsTofiDO2.setKeyAccount("keyAccount");
        bizBaseMbgCustomerDrmTomsTofiDO2.setKeyAccountDesc("keyAccountDesc");
        final List<BizBaseMbgCustomerDrmTomsTofiDO> bizBaseMbgCustomerDrmTomsTofiDOS1 = Arrays.asList(
                bizBaseMbgCustomerDrmTomsTofiDO2);
//        when(mockBizBaseMbgCustomerDrmTomsTofiManager.getDropDownListByCustomerGroup(
//                any(BizBaseMbgCustomerDrmTomsTofiDTO.class))).thenReturn(bizBaseMbgCustomerDrmTomsTofiDOS1);
        when(mockBizBaseMbgCustomerDrmTomsTofiManager.getDropDownListByKeyAccount(
                any(BizBaseMbgCustomerDrmTomsTofiDTO.class))).thenReturn(bizBaseMbgCustomerDrmTomsTofiDOS1);

        // Run the test
        final List<BizBaseMbgCustomerDrmTomsTofiDO> result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.getDropDownList(
                bizBaseMbgCustomerDrmTomsTofi);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetDropDownList_IBizBaseMbgCustomerDrmTomsTofiManagerGetDropDownListByKeyAccountReturnsNoItems() {
        // Setup
        final BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi = new BizBaseMbgCustomerDrmTomsTofiDTO();
        bizBaseMbgCustomerDrmTomsTofi.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setId(0L);
        bizBaseMbgCustomerDrmTomsTofi.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofi.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofi.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofi.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofi.setCustomerNumber("customerNumber");
        bizBaseMbgCustomerDrmTomsTofi.setTempField("tempField");

//        when(mockBizBaseMbgCustomerDrmTomsTofiManager.getDropDownListByKeyAccount(
//                any(BizBaseMbgCustomerDrmTomsTofiDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseMbgCustomerDrmTomsTofiDO> result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.getDropDownList(
                bizBaseMbgCustomerDrmTomsTofi);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetDropDownList_IBizBaseMbgCustomerDrmTomsTofiManagerGetDropDownListByCustomerGroupReturnsNoItems() {
        // Setup
        final BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi = new BizBaseMbgCustomerDrmTomsTofiDTO();
        bizBaseMbgCustomerDrmTomsTofi.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseMbgCustomerDrmTomsTofi.setId(0L);
        bizBaseMbgCustomerDrmTomsTofi.setBusinessGroup("businessGroup");
        bizBaseMbgCustomerDrmTomsTofi.setGeoCode("geoCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketCode("regionMarketCode");
        bizBaseMbgCustomerDrmTomsTofi.setRegionMarketName("regionMarketName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgCode("salesOrgCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOrgName("salesOrgName");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeCode("salesOfficeCode");
        bizBaseMbgCustomerDrmTomsTofi.setSalesOfficeName("salesOfficeName");
        bizBaseMbgCustomerDrmTomsTofi.setCountryCode("countryCode");
        bizBaseMbgCustomerDrmTomsTofi.setCountryName("countryName");
        bizBaseMbgCustomerDrmTomsTofi.setCustomerNumber("customerNumber");
        bizBaseMbgCustomerDrmTomsTofi.setTempField("tempField");

        when(mockBizBaseMbgCustomerDrmTomsTofiManager.getDropDownListByCustomerGroup(
                any(BizBaseMbgCustomerDrmTomsTofiDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseMbgCustomerDrmTomsTofiDO> result = bizBaseMbgCustomerDrmTomsTofiServiceImplUnderTest.getDropDownList(
                bizBaseMbgCustomerDrmTomsTofi);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
