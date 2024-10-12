package com.microservices.sdms.masterdata.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;
import com.microservices.otmp.masterdata.domain.dto.BizBaseOrgOfficeDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseOrgOfficeDO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeNameAndCodeDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeTreeDTO;
import com.microservices.otmp.masterdata.manager.IBizBaseOrgOfficeManager;
import com.microservices.otmp.masterdata.service.impl.BizBaseOrgOfficeServiceImpl;
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
public class BizBaseOrgOfficeServiceImplTest {

    @Mock
    private IBizBaseOrgOfficeManager mockBizBaseOrgOfficeManager;

    @InjectMocks
    private BizBaseOrgOfficeServiceImpl bizBaseOrgOfficeServiceImplUnderTest;

    @Test
    public void testSelectBizBaseOrgOfficeById() {
        // Setup
        final BizBaseOrgOfficeTreeDTO expectedResult = new BizBaseOrgOfficeTreeDTO();
        expectedResult.setBusinessGroup("businessGroup");
        expectedResult.setGeoCode("geoCode");
        expectedResult.setRegionMarketCode("regionMarketCode");
        expectedResult.setRegionMarketName("regionMarketName");
        expectedResult.setTerritoryCode("territoryCode");
        expectedResult.setTerritoryName("territoryName");
        expectedResult.setSalesOrgCode("salesOrgCode");
        expectedResult.setSalesOrgName("salesOrgName");
        expectedResult.setIsDummy("isDummy");
        expectedResult.setLocalCurrencyCode("localCurrencyCode");
        expectedResult.setCompanyCode("companyCode");
        expectedResult.setAccrualCompanyCode("accrualCompanyCode");
        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDTO.setStatus("code");
        bizBaseOrgOfficeDTO.setDeleteFlag(false);
        bizBaseOrgOfficeDTO.setIds("0");
        bizBaseOrgOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDTO.setIsDummy("isDummy");
        bizBaseOrgOfficeDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDTO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDTO.setAccrualCompanyCode("accrualCompanyCode");
        expectedResult.setChildren(Arrays.asList(bizBaseOrgOfficeDTO));

        // Configure IBizBaseOrgOfficeManager.selectBizBaseOrgOfficeById(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO.setCreateBy("loginName");
        baseOrgOfficeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setUpdateBy("loginName");
        baseOrgOfficeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setId(0L);
        baseOrgOfficeDO.setBusinessGroup("businessGroup");
        baseOrgOfficeDO.setGeoCode("geoCode");
        baseOrgOfficeDO.setRegionMarketCode("countryCode");
        baseOrgOfficeDO.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO.setTerritoryCode("territoryCode");
        baseOrgOfficeDO.setTerritoryName("territoryCode");
        baseOrgOfficeDO.setCountryCode("countryCode");
        baseOrgOfficeDO.setCountryName("countryCode");
        baseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO.setStatus("code");
        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeById(0L)).thenReturn(baseOrgOfficeDO);

        // Configure IBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO1 = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO1.setCreateBy("loginName");
        baseOrgOfficeDO1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO1.setUpdateBy("loginName");
        baseOrgOfficeDO1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO1.setId(0L);
        baseOrgOfficeDO1.setBusinessGroup("businessGroup");
        baseOrgOfficeDO1.setGeoCode("geoCode");
        baseOrgOfficeDO1.setRegionMarketCode("countryCode");
        baseOrgOfficeDO1.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO1.setTerritoryCode("territoryCode");
        baseOrgOfficeDO1.setTerritoryName("territoryCode");
        baseOrgOfficeDO1.setCountryCode("countryCode");
        baseOrgOfficeDO1.setCountryName("countryCode");
        baseOrgOfficeDO1.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO1.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO1.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO1.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO1.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO1.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO1.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO1.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO1.setStatus("code");
        final List<BizBaseOrgOfficeDO> bizBaseOrgOfficeDOS = Arrays.asList(baseOrgOfficeDO1);
        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(bizBaseOrgOfficeDOS);

        // Run the test
        final BizBaseOrgOfficeTreeDTO result = bizBaseOrgOfficeServiceImplUnderTest.selectBizBaseOrgOfficeById(0L);

        // Verify the results
        assertEquals(expectedResult.getBusinessGroup(), result.getBusinessGroup());
    }

    @Test
    public void testSelectBizBaseOrgOfficeById_IBizBaseOrgOfficeManagerSelectBizBaseOrgOfficeListReturnsNoItems() {
        // Setup
        final BizBaseOrgOfficeTreeDTO expectedResult = new BizBaseOrgOfficeTreeDTO();
        expectedResult.setBusinessGroup("businessGroup");
        expectedResult.setGeoCode("geoCode");
        expectedResult.setRegionMarketCode("regionMarketCode");
        expectedResult.setRegionMarketName("regionMarketName");
        expectedResult.setTerritoryCode("territoryCode");
        expectedResult.setTerritoryName("territoryName");
        expectedResult.setSalesOrgCode("salesOrgCode");
        expectedResult.setSalesOrgName("salesOrgName");
        expectedResult.setIsDummy("isDummy");
        expectedResult.setLocalCurrencyCode("localCurrencyCode");
        expectedResult.setCompanyCode("companyCode");
        expectedResult.setAccrualCompanyCode("accrualCompanyCode");
        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDTO.setStatus("code");
        bizBaseOrgOfficeDTO.setDeleteFlag(false);
        bizBaseOrgOfficeDTO.setIds("0");
        bizBaseOrgOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDTO.setIsDummy("isDummy");
        bizBaseOrgOfficeDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDTO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDTO.setAccrualCompanyCode("accrualCompanyCode");
        expectedResult.setChildren(Arrays.asList(bizBaseOrgOfficeDTO));

        // Configure IBizBaseOrgOfficeManager.selectBizBaseOrgOfficeById(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO.setCreateBy("loginName");
        baseOrgOfficeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setUpdateBy("loginName");
        baseOrgOfficeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setId(0L);
        baseOrgOfficeDO.setBusinessGroup("businessGroup");
        baseOrgOfficeDO.setGeoCode("geoCode");
        baseOrgOfficeDO.setRegionMarketCode("countryCode");
        baseOrgOfficeDO.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO.setTerritoryCode("territoryCode");
        baseOrgOfficeDO.setTerritoryName("territoryCode");
        baseOrgOfficeDO.setCountryCode("countryCode");
        baseOrgOfficeDO.setCountryName("countryCode");
        baseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO.setStatus("code");
        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeById(0L)).thenReturn(baseOrgOfficeDO);

        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(Collections.emptyList());


        // Run the test
        final BizBaseOrgOfficeTreeDTO result = bizBaseOrgOfficeServiceImplUnderTest.selectBizBaseOrgOfficeById(0L);

        // Verify the results
        assertEquals(expectedResult.getBusinessGroup(), result.getBusinessGroup());
    }

    @Test
    public void testSelectBizBaseOrgOfficeList() {
        // Setup
        final BizBaseOrgOfficeDTO bizBaseOrgOffice = new BizBaseOrgOfficeDTO();
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setCountryCode("countryCode");
        bizBaseOrgOffice.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOffice.setStatus("code");
        bizBaseOrgOffice.setDeleteFlag(false);
        bizBaseOrgOffice.setIds("0");
        bizBaseOrgOffice.setIdsList(Arrays.asList(0L));
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");

        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setCreateBy("loginName");
        bizBaseOrgOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseOrgOfficeDTO.setUpdateBy("loginName");
        bizBaseOrgOfficeDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseOrgOfficeDTO.setId(0L);
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("countryCode");
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setCountryName("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setBpcCountryName("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDTO.setSalesOfficeName("salesOfficeCode");
        bizBaseOrgOfficeDTO.setDistributionChannelCode("distributionChannelCode");
        bizBaseOrgOfficeDTO.setDistributionChannelName("distributionChannelCode");
        bizBaseOrgOfficeDTO.setStatus("code");
        final List<BizBaseOrgOfficeDTO> expectedResult = Arrays.asList(bizBaseOrgOfficeDTO);

        // Configure IBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO.setCreateBy("loginName");
        baseOrgOfficeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setUpdateBy("loginName");
        baseOrgOfficeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setId(0L);
        baseOrgOfficeDO.setBusinessGroup("businessGroup");
        baseOrgOfficeDO.setGeoCode("geoCode");
        baseOrgOfficeDO.setRegionMarketCode("countryCode");
        baseOrgOfficeDO.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO.setTerritoryCode("territoryCode");
        baseOrgOfficeDO.setTerritoryName("territoryCode");
        baseOrgOfficeDO.setCountryCode("countryCode");
        baseOrgOfficeDO.setCountryName("countryCode");
        baseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO.setStatus("code");
        final List<BizBaseOrgOfficeDO> bizBaseOrgOfficeDOS = Arrays.asList(baseOrgOfficeDO);
        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(bizBaseOrgOfficeDOS);

        // Run the test
        final List<BizBaseOrgOfficeDTO> result = bizBaseOrgOfficeServiceImplUnderTest.selectBizBaseOrgOfficeList(
                bizBaseOrgOffice);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseOrgOfficeList_IBizBaseOrgOfficeManagerReturnsNoItems() {
        // Setup
        final BizBaseOrgOfficeDTO bizBaseOrgOffice = new BizBaseOrgOfficeDTO();
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setCountryCode("countryCode");
        bizBaseOrgOffice.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOffice.setStatus("code");
        bizBaseOrgOffice.setDeleteFlag(false);
        bizBaseOrgOffice.setIds("0");
        bizBaseOrgOffice.setIdsList(Arrays.asList(0L));
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");

        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseOrgOfficeDTO> result = bizBaseOrgOfficeServiceImplUnderTest.selectBizBaseOrgOfficeList(
                bizBaseOrgOffice);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseOrgOffice_ThrowsOtmpException() {
        // Setup
        final BizBaseOrgOfficeTreeDTO bizBaseOrgOffice = new BizBaseOrgOfficeTreeDTO();
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");
        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDTO.setStatus("code");
        bizBaseOrgOfficeDTO.setDeleteFlag(false);
//        bizBaseOrgOfficeDTO.setIds("0");
//        bizBaseOrgOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDTO.setIsDummy("isDummy");
        bizBaseOrgOfficeDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDTO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDTO.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseOrgOffice.setChildren(Arrays.asList(bizBaseOrgOfficeDTO));

        // Configure IBizBaseOrgOfficeManager.checkSalesOrg(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO.setCreateBy("loginName");
        baseOrgOfficeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setUpdateBy("loginName");
        baseOrgOfficeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setId(0L);
        baseOrgOfficeDO.setBusinessGroup("businessGroup");
        baseOrgOfficeDO.setGeoCode("geoCode");
        baseOrgOfficeDO.setRegionMarketCode("countryCode");
        baseOrgOfficeDO.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO.setTerritoryCode("territoryCode");
        baseOrgOfficeDO.setTerritoryName("territoryCode");
        baseOrgOfficeDO.setCountryCode("countryCode");
        baseOrgOfficeDO.setCountryName("countryCode");
        baseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO.setStatus("code");
        when(mockBizBaseOrgOfficeManager.checkSalesOrg(any(BizBaseOrgOfficeTreeDTO.class))).thenReturn(baseOrgOfficeDO);

        // Run the test
        assertThrows(OtmpException.class,
                () -> bizBaseOrgOfficeServiceImplUnderTest.insertBizBaseOrgOffice(bizBaseOrgOffice, "loginName"));
    }

    @Test
    public void testInsertBizBaseOrgOffice_IBizBaseOrgOfficeManagerCheckSalesOrgReturnsNull() {
        // Setup
        final BizBaseOrgOfficeTreeDTO bizBaseOrgOffice = new BizBaseOrgOfficeTreeDTO();
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");
        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDTO.setStatus("code");
        bizBaseOrgOfficeDTO.setDeleteFlag(false);
        bizBaseOrgOfficeDTO.setIds("0");
        bizBaseOrgOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDTO.setIsDummy("isDummy");
        bizBaseOrgOfficeDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDTO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDTO.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseOrgOffice.setChildren(Arrays.asList(bizBaseOrgOfficeDTO));

        when(mockBizBaseOrgOfficeManager.checkSalesOrg(any(BizBaseOrgOfficeTreeDTO.class))).thenReturn(null);

        // Configure IBizBaseOrgOfficeManager.checkSalesOffice(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO.setCreateBy("loginName");
        baseOrgOfficeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setUpdateBy("loginName");
        baseOrgOfficeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setId(0L);
        baseOrgOfficeDO.setBusinessGroup("businessGroup");
        baseOrgOfficeDO.setGeoCode("geoCode");
        baseOrgOfficeDO.setRegionMarketCode("countryCode");
        baseOrgOfficeDO.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO.setTerritoryCode("territoryCode");
        baseOrgOfficeDO.setTerritoryName("territoryCode");
        baseOrgOfficeDO.setCountryCode("countryCode");
        baseOrgOfficeDO.setCountryName("countryCode");
        baseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO.setStatus("code");
        when(mockBizBaseOrgOfficeManager.checkSalesOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(baseOrgOfficeDO);

        // Run the test
        assertThrows(OtmpException.class,
                () -> bizBaseOrgOfficeServiceImplUnderTest.insertBizBaseOrgOffice(bizBaseOrgOffice, "loginName"));
    }

    @Test
    public void testInsertBizBaseOrgOffice_IBizBaseOrgOfficeManagerCheckSalesOfficeReturnsNull() {
        // Setup
        final BizBaseOrgOfficeTreeDTO bizBaseOrgOffice = new BizBaseOrgOfficeTreeDTO();
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");
        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDTO.setStatus("code");
        bizBaseOrgOfficeDTO.setDeleteFlag(false);
        bizBaseOrgOfficeDTO.setIds("0");
        bizBaseOrgOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDTO.setIsDummy("isDummy");
        bizBaseOrgOfficeDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDTO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDTO.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseOrgOffice.setChildren(Arrays.asList(bizBaseOrgOfficeDTO));

        when(mockBizBaseOrgOfficeManager.checkSalesOrg(any(BizBaseOrgOfficeTreeDTO.class))).thenReturn(null);
        when(mockBizBaseOrgOfficeManager.checkSalesOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(null);
        when(mockBizBaseOrgOfficeManager.insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(0);

        // Run the test
        final ResultDTO<Integer> result = bizBaseOrgOfficeServiceImplUnderTest.insertBizBaseOrgOffice(bizBaseOrgOffice,
                "loginName");

        // Verify the results
        verify(mockBizBaseOrgOfficeManager).insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class));
    }

    @Test
    public void testUpdateBizBaseOrgOffice() {
        // Setup
        final BizBaseOrgOfficeTreeDTO bizBaseOrgOffice = new BizBaseOrgOfficeTreeDTO();
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");
        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDTO.setStatus("code");
        bizBaseOrgOfficeDTO.setDeleteFlag(false);
        bizBaseOrgOfficeDTO.setIds("0");
        bizBaseOrgOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDTO.setIsDummy("isDummy");
        bizBaseOrgOfficeDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDTO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDTO.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseOrgOffice.setChildren(Arrays.asList(bizBaseOrgOfficeDTO));

//        when(mockBizBaseOrgOfficeManager.getIdsBySalesOrg(any(BizBaseOrgOfficeTreeDTO.class)))
//                .thenReturn(new HashSet<>(Arrays.asList(0L)));

        // Configure IBizBaseOrgOfficeManager.checkSalesOffice(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO.setCreateBy("loginName");
        baseOrgOfficeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setUpdateBy("loginName");
        baseOrgOfficeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setId(0L);
        baseOrgOfficeDO.setBusinessGroup("businessGroup");
        baseOrgOfficeDO.setGeoCode("geoCode");
        baseOrgOfficeDO.setRegionMarketCode("countryCode");
        baseOrgOfficeDO.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO.setTerritoryCode("territoryCode");
        baseOrgOfficeDO.setTerritoryName("territoryCode");
        baseOrgOfficeDO.setCountryCode("countryCode");
        baseOrgOfficeDO.setCountryName("countryCode");
        baseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO.setStatus("code");
//        when(mockBizBaseOrgOfficeManager.checkSalesOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(baseOrgOfficeDO);
//
//        when(mockBizBaseOrgOfficeManager.insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(0);
//        when(mockBizBaseOrgOfficeManager.updateBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(0);
//        when(mockBizBaseOrgOfficeManager.deleteBizBaseOrgOfficeByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final ResultDTO<Integer> result = bizBaseOrgOfficeServiceImplUnderTest.updateBizBaseOrgOffice(bizBaseOrgOffice,
                "loginName");

        // Verify the results
//        verify(mockBizBaseOrgOfficeManager).insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class));
//        verify(mockBizBaseOrgOfficeManager).updateBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class));
//        verify(mockBizBaseOrgOfficeManager).deleteBizBaseOrgOfficeByIds(any(Long[].class));
        assertEquals(null, result.getData());
    }

    @Test
    public void testUpdateBizBaseOrgOffice_IBizBaseOrgOfficeManagerGetIdsBySalesOrgReturnsNoItems() {
        // Setup
        final BizBaseOrgOfficeTreeDTO bizBaseOrgOffice = new BizBaseOrgOfficeTreeDTO();
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");
        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode" + DateUtils.dateTime());
        bizBaseOrgOfficeDTO.setStatus("code");
        bizBaseOrgOfficeDTO.setDeleteFlag(false);
        bizBaseOrgOfficeDTO.setIds("0");
        bizBaseOrgOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDTO.setIsDummy("isDummy");
        bizBaseOrgOfficeDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDTO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDTO.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseOrgOffice.setChildren(Arrays.asList(bizBaseOrgOfficeDTO));

        //when(mockBizBaseOrgOfficeManager.getIdsBySalesOrg(any(BizBaseOrgOfficeTreeDTO.class))).thenReturn(new HashSet<>());

        // Configure IBizBaseOrgOfficeManager.checkSalesOffice(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO.setCreateBy("loginName");
        baseOrgOfficeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setUpdateBy("loginName");
        baseOrgOfficeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setId(0L);
        baseOrgOfficeDO.setBusinessGroup("businessGroup");
        baseOrgOfficeDO.setGeoCode("geoCode");
        baseOrgOfficeDO.setRegionMarketCode("countryCode");
        baseOrgOfficeDO.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO.setTerritoryCode("territoryCode");
        baseOrgOfficeDO.setTerritoryName("territoryCode");
        baseOrgOfficeDO.setCountryCode("countryCode");
        baseOrgOfficeDO.setCountryName("countryCode");
        baseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode" + DateUtils.dateTime());
        baseOrgOfficeDO.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO.setStatus("code");
        //when(mockBizBaseOrgOfficeManager.checkSalesOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(baseOrgOfficeDO);

        //when(mockBizBaseOrgOfficeManager.insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(0);
        //when(mockBizBaseOrgOfficeManager.updateBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(0);
        //when(mockBizBaseOrgOfficeManager.deleteBizBaseOrgOfficeByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final ResultDTO<Integer> result = bizBaseOrgOfficeServiceImplUnderTest.updateBizBaseOrgOffice(bizBaseOrgOffice,
                "loginName");

        // Verify the results
//        verify(mockBizBaseOrgOfficeManager).insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class));
//        verify(mockBizBaseOrgOfficeManager).updateBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class));
//        verify(mockBizBaseOrgOfficeManager).deleteBizBaseOrgOfficeByIds(any(Long[].class));
        assertEquals(null, result.getData());
    }

    @Test
    public void testUpdateBizBaseOrgOffice_IBizBaseOrgOfficeManagerCheckSalesOfficeReturnsNull() {
        // Setup
        final BizBaseOrgOfficeTreeDTO bizBaseOrgOffice = new BizBaseOrgOfficeTreeDTO();
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");
        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDTO.setStatus("code");
        bizBaseOrgOfficeDTO.setDeleteFlag(false);
        bizBaseOrgOfficeDTO.setIds("0");
        bizBaseOrgOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDTO.setIsDummy("isDummy");
        bizBaseOrgOfficeDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDTO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDTO.setAccrualCompanyCode("accrualCompanyCode");
        bizBaseOrgOffice.setChildren(Arrays.asList(bizBaseOrgOfficeDTO));

        when(mockBizBaseOrgOfficeManager.getIdsBySalesOrg(any(BizBaseOrgOfficeTreeDTO.class)))
                .thenReturn(new HashSet<>(Arrays.asList(0L)));
        when(mockBizBaseOrgOfficeManager.checkSalesOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(null);
        when(mockBizBaseOrgOfficeManager.insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(0);
        when(mockBizBaseOrgOfficeManager.deleteBizBaseOrgOfficeByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final ResultDTO<Integer> result = bizBaseOrgOfficeServiceImplUnderTest.updateBizBaseOrgOffice(bizBaseOrgOffice,
                "loginName");

        // Verify the results
        verify(mockBizBaseOrgOfficeManager).insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class));
        verify(mockBizBaseOrgOfficeManager).deleteBizBaseOrgOfficeByIds(any(Long[].class));
    }

    @Test
    public void testDeleteBizBaseOrgOfficeByIds() {
        // Setup
        when(mockBizBaseOrgOfficeManager.deleteBizBaseOrgOfficeByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseOrgOfficeServiceImplUnderTest.deleteBizBaseOrgOfficeByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseOrgOfficeById() {
        // Setup
        when(mockBizBaseOrgOfficeManager.deleteBizBaseOrgOfficeById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseOrgOfficeServiceImplUnderTest.deleteBizBaseOrgOfficeById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() throws Exception {
        // Setup
        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDTO.setStatus("code");
        bizBaseOrgOfficeDTO.setDeleteFlag(false);
//        bizBaseOrgOfficeDTO.setIds("0");
//        bizBaseOrgOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDTO.setIsDummy("isDummy");
        bizBaseOrgOfficeDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDTO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDTO.setAccrualCompanyCode("accrualCompanyCode");
        final List<BizBaseOrgOfficeDTO> bizs = Arrays.asList(bizBaseOrgOfficeDTO);

        // Configure IBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO.setCreateBy("loginName");
        baseOrgOfficeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setUpdateBy("loginName");
        baseOrgOfficeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setId(0L);
        baseOrgOfficeDO.setBusinessGroup("businessGroup");
        baseOrgOfficeDO.setGeoCode("geoCode");
        baseOrgOfficeDO.setRegionMarketCode("countryCode");
        baseOrgOfficeDO.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO.setTerritoryCode("territoryCode");
        baseOrgOfficeDO.setTerritoryName("territoryCode");
        baseOrgOfficeDO.setCountryCode("countryCode");
        baseOrgOfficeDO.setCountryName("countryCode");
        baseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO.setStatus("code");
        final List<BizBaseOrgOfficeDO> bizBaseOrgOfficeDOS = Arrays.asList(baseOrgOfficeDO);
        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(bizBaseOrgOfficeDOS);

//        when(mockBizBaseOrgOfficeManager.insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(0);

        // Run the test
        final ResultDTO result = bizBaseOrgOfficeServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        //verify(mockBizBaseOrgOfficeManager).insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class));
        assertEquals(null, result.getData());
    }

    @Test
    public void testImportExcel_IBizBaseOrgOfficeManagerSelectBizBaseOrgOfficeListReturnsNoItems() {
        // Setup
        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDTO.setStatus("code");
        bizBaseOrgOfficeDTO.setDeleteFlag(false);
        bizBaseOrgOfficeDTO.setIds("0");
        bizBaseOrgOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDTO.setIsDummy("isDummy");
        bizBaseOrgOfficeDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDTO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDTO.setAccrualCompanyCode("accrualCompanyCode");
        final List<BizBaseOrgOfficeDTO> bizs = Arrays.asList(bizBaseOrgOfficeDTO);
        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(Collections.emptyList());
        when(mockBizBaseOrgOfficeManager.insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class))).thenReturn(0);

        // Run the test
        final ResultDTO result = bizBaseOrgOfficeServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        verify(mockBizBaseOrgOfficeManager).insertBizBaseOrgOffice(any(BizBaseOrgOfficeDO.class));
    }

    @Test
    public void testGetCodeAndName() {
        // Setup
        final BizBaseOrgOfficeNameAndCodeDTO bizBaseOrgOfficeNameAndCodeDTO = new BizBaseOrgOfficeNameAndCodeDTO();
        bizBaseOrgOfficeNameAndCodeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeNameAndCodeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeNameAndCodeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeNameAndCodeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeNameAndCodeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeNameAndCodeDTO.setCountryName("countryName");
        bizBaseOrgOfficeNameAndCodeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeNameAndCodeDTO.setBpcCountryName("bpcCountryName");
        bizBaseOrgOfficeNameAndCodeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeNameAndCodeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeNameAndCodeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeNameAndCodeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseOrgOfficeNameAndCodeDTO.setDistributionChannelCode("distributionChannelCode");
        bizBaseOrgOfficeNameAndCodeDTO.setDistributionChannelName("distributionChannelName");
        final List<BizBaseOrgOfficeNameAndCodeDTO> expectedResult = Arrays.asList(bizBaseOrgOfficeNameAndCodeDTO);

        // Configure IBizBaseOrgOfficeManager.getCodeAndName(...).
        final BizBaseOrgOfficeNameAndCodeDTO bizBaseOrgOfficeNameAndCodeDTO1 = new BizBaseOrgOfficeNameAndCodeDTO();
        bizBaseOrgOfficeNameAndCodeDTO1.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeNameAndCodeDTO1.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeNameAndCodeDTO1.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeNameAndCodeDTO1.setTerritoryName("territoryName");
        bizBaseOrgOfficeNameAndCodeDTO1.setCountryCode("countryCode");
        bizBaseOrgOfficeNameAndCodeDTO1.setCountryName("countryName");
        bizBaseOrgOfficeNameAndCodeDTO1.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeNameAndCodeDTO1.setBpcCountryName("bpcCountryName");
        bizBaseOrgOfficeNameAndCodeDTO1.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeNameAndCodeDTO1.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeNameAndCodeDTO1.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeNameAndCodeDTO1.setSalesOfficeName("salesOfficeName");
        bizBaseOrgOfficeNameAndCodeDTO1.setDistributionChannelCode("distributionChannelCode");
        bizBaseOrgOfficeNameAndCodeDTO1.setDistributionChannelName("distributionChannelName");
        final List<BizBaseOrgOfficeNameAndCodeDTO> bizBaseOrgOfficeNameAndCodeDTOS = Arrays.asList(
                bizBaseOrgOfficeNameAndCodeDTO1);
        when(mockBizBaseOrgOfficeManager.getCodeAndName("type", "code")).thenReturn(bizBaseOrgOfficeNameAndCodeDTOS);

        // Run the test
        final List<BizBaseOrgOfficeNameAndCodeDTO> result = bizBaseOrgOfficeServiceImplUnderTest.getCodeAndName("type",
                "code");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetCodeAndName_IBizBaseOrgOfficeManagerReturnsNoItems() {
        // Setup
        when(mockBizBaseOrgOfficeManager.getCodeAndName("type", "code")).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseOrgOfficeNameAndCodeDTO> result = bizBaseOrgOfficeServiceImplUnderTest.getCodeAndName("type",
                "code");

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetTerritoryDropDownList() {
        // Setup
        final BizBaseDropDownCondition bizBaseDropDownCondition = new BizBaseDropDownCondition();
        bizBaseDropDownCondition.setGeoCode("geoCode");
        bizBaseDropDownCondition.setBusinessGroup("businessGroup");
        bizBaseDropDownCondition.setRegionMarketCode("regionMarketCode");
        bizBaseDropDownCondition.setSalesOrgCode("salesOrgCode");
        bizBaseDropDownCondition.setSalesOrgs(new String[]{"salesOrgs"});
        bizBaseDropDownCondition.setTempField("tempField");
        bizBaseDropDownCondition.setPaymentModeCode("paymentModeCode");
        bizBaseDropDownCondition.setGtnTypeCode("gtnTypeCode");

        // Configure IBizBaseOrgOfficeManager.getTerritoryDropDownList(...).
        final BizBaseTerritoryRelation bizBaseTerritoryRelation = new BizBaseTerritoryRelation();
        bizBaseTerritoryRelation.setId(0L);
        bizBaseTerritoryRelation.setBusinessGroup("businessGroup");
        bizBaseTerritoryRelation.setGeoCode("geoCode");
        bizBaseTerritoryRelation.setRegionMarketCode("regionMarketCode");
        bizBaseTerritoryRelation.setRegionMarketName("regionMarketName");
        bizBaseTerritoryRelation.setTerritory("territory");
        bizBaseTerritoryRelation.setStatus("status");
        final List<BizBaseTerritoryRelation> bizBaseTerritoryRelations = Arrays.asList(bizBaseTerritoryRelation);
        when(mockBizBaseOrgOfficeManager.getTerritoryDropDownList(any(BizBaseDropDownCondition.class)))
                .thenReturn(bizBaseTerritoryRelations);

        // Run the test
        final List<BizBaseTerritoryRelation> result = bizBaseOrgOfficeServiceImplUnderTest.getTerritoryDropDownList(
                bizBaseDropDownCondition);

        // Verify the results
        assertEquals(result, bizBaseTerritoryRelations);
    }

    @Test
    public void testGetTerritoryDropDownList_IBizBaseOrgOfficeManagerReturnsNoItems() {
        // Setup
        final BizBaseDropDownCondition bizBaseDropDownCondition = new BizBaseDropDownCondition();
        bizBaseDropDownCondition.setGeoCode("geoCode");
        bizBaseDropDownCondition.setBusinessGroup("businessGroup");
        bizBaseDropDownCondition.setRegionMarketCode("regionMarketCode");
        bizBaseDropDownCondition.setSalesOrgCode("salesOrgCode");
        bizBaseDropDownCondition.setSalesOrgs(new String[]{"salesOrgs"});
        bizBaseDropDownCondition.setTempField("tempField");
        bizBaseDropDownCondition.setPaymentModeCode("paymentModeCode");
        bizBaseDropDownCondition.setGtnTypeCode("gtnTypeCode");

        when(mockBizBaseOrgOfficeManager.getTerritoryDropDownList(any(BizBaseDropDownCondition.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseTerritoryRelation> result = bizBaseOrgOfficeServiceImplUnderTest.getTerritoryDropDownList(
                bizBaseDropDownCondition);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetDropDownList() {
        // Setup
        final BizBaseOrgOfficeDTO bizBaseOrgOffice = new BizBaseOrgOfficeDTO();
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setCountryCode("countryCode");
        bizBaseOrgOffice.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOffice.setStatus("code");
        bizBaseOrgOffice.setDeleteFlag(false);
//        bizBaseOrgOffice.setIds("0");
//        bizBaseOrgOffice.setIdsList(Arrays.asList(0L));
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");

        final BizBaseOrgOfficeDO bizBaseOrgOfficeDO = new BizBaseOrgOfficeDO();
        bizBaseOrgOfficeDO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDO.setGeoCode("geoCode");
        bizBaseOrgOfficeDO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDO.setCountryCode("countryCode");
        bizBaseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDO.setStatus("code");
        bizBaseOrgOfficeDO.setDeleteFlag(false);
//        bizBaseOrgOfficeDO.setIds("0");
//        bizBaseOrgOfficeDO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDO.setIsDummy("isDummy");
        bizBaseOrgOfficeDO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDO.setAccrualCompanyCode("accrualCompanyCode");
        final List<BizBaseOrgOfficeDO> bizs = Arrays.asList(bizBaseOrgOfficeDO);

        final BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        bizBaseOrgOfficeDTO.setRegionMarketName("regionMarketName");
        bizBaseOrgOfficeDTO.setTerritoryName("territoryName");
        bizBaseOrgOfficeDTO.setSalesOrgName("salesOrgName");
        bizBaseOrgOfficeDTO.setBusinessGroup("businessGroup");
        bizBaseOrgOfficeDTO.setGeoCode("geoCode");
        bizBaseOrgOfficeDTO.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOfficeDTO.setTerritoryCode("territoryCode");
        bizBaseOrgOfficeDTO.setCountryCode("countryCode");
        bizBaseOrgOfficeDTO.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOfficeDTO.setStatus("code");
        bizBaseOrgOfficeDTO.setDeleteFlag(false);
//        bizBaseOrgOfficeDTO.setIds("0");
//        bizBaseOrgOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseOrgOfficeDTO.setIsDummy("isDummy");
        bizBaseOrgOfficeDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOfficeDTO.setCompanyCode("companyCode");
        bizBaseOrgOfficeDTO.setAccrualCompanyCode("accrualCompanyCode");
        final List<BizBaseOrgOfficeDTO> expectedResult = Arrays.asList(bizBaseOrgOfficeDTO);

        when(mockBizBaseOrgOfficeManager.getDropDownList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(bizs);

        // Run the test
        final PageInfo<BizBaseOrgOfficeDTO> result = bizBaseOrgOfficeServiceImplUnderTest.getDropDownList(bizBaseOrgOffice);


        // Verify the results
        assertEquals(expectedResult.size(), result.getSize());
    }

    @Test
    public void testGetDropDownList_IBizBaseOrgOfficeManagerReturnsNoItems() {
        // Setup
        final BizBaseOrgOfficeDTO bizBaseOrgOffice = new BizBaseOrgOfficeDTO();
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setCountryCode("countryCode");
        bizBaseOrgOffice.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOffice.setStatus("code");
        bizBaseOrgOffice.setDeleteFlag(false);
        bizBaseOrgOffice.setIds("0");
        bizBaseOrgOffice.setIdsList(Arrays.asList(0L));
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");

        when(mockBizBaseOrgOfficeManager.getDropDownList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<BizBaseOrgOfficeDTO> result = bizBaseOrgOfficeServiceImplUnderTest.getDropDownList(bizBaseOrgOffice);

        // Verify the results
        assertEquals(0, result.getSize());
    }

    @Test
    public void testSelectBizBaseOrgOfficeTreeList() {
        // Setup
        final BizBaseOrgOfficeDTO bizBaseOrgOffice = new BizBaseOrgOfficeDTO();
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setCountryCode("countryCode");
        bizBaseOrgOffice.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOffice.setStatus("code");
        bizBaseOrgOffice.setDeleteFlag(false);
        bizBaseOrgOffice.setIds("0");
        bizBaseOrgOffice.setIdsList(Arrays.asList(0L));
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");

        // Configure IBizBaseOrgOfficeManager.selectBizBaseOrgOfficeTreeList(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO.setCreateBy("loginName");
        baseOrgOfficeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setUpdateBy("loginName");
        baseOrgOfficeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setId(0L);
        baseOrgOfficeDO.setBusinessGroup("businessGroup");
        baseOrgOfficeDO.setGeoCode("geoCode");
        baseOrgOfficeDO.setRegionMarketCode("countryCode");
        baseOrgOfficeDO.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO.setTerritoryCode("territoryCode");
        baseOrgOfficeDO.setTerritoryName("territoryCode");
        baseOrgOfficeDO.setCountryCode("countryCode");
        baseOrgOfficeDO.setCountryName("countryCode");
        baseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO.setStatus("code");
        final List<BizBaseOrgOfficeDO> bizBaseOrgOfficeDOS = Arrays.asList(baseOrgOfficeDO);
        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeTreeList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(bizBaseOrgOfficeDOS);

        // Configure IBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO1 = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO1.setCreateBy("loginName");
        baseOrgOfficeDO1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO1.setUpdateBy("loginName");
        baseOrgOfficeDO1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO1.setId(0L);
        baseOrgOfficeDO1.setBusinessGroup("businessGroup");
        baseOrgOfficeDO1.setGeoCode("geoCode");
        baseOrgOfficeDO1.setRegionMarketCode("countryCode");
        baseOrgOfficeDO1.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO1.setTerritoryCode("territoryCode");
        baseOrgOfficeDO1.setTerritoryName("territoryCode");
        baseOrgOfficeDO1.setCountryCode("countryCode");
        baseOrgOfficeDO1.setCountryName("countryCode");
        baseOrgOfficeDO1.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO1.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO1.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO1.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO1.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO1.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO1.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO1.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO1.setStatus("code");
        final List<BizBaseOrgOfficeDO> bizBaseOrgOfficeDOS1 = Arrays.asList(baseOrgOfficeDO1);
        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(bizBaseOrgOfficeDOS1);

        // Run the test
        final PageInfo<BizBaseOrgOfficeTreeDTO> result = bizBaseOrgOfficeServiceImplUnderTest.selectBizBaseOrgOfficeTreeList(
                bizBaseOrgOffice);

        // Verify the results
        assertEquals(result.getSize(), bizBaseOrgOfficeDOS1.size());
    }

    @Test
    public void testSelectBizBaseOrgOfficeTreeList_IBizBaseOrgOfficeManagerSelectBizBaseOrgOfficeTreeListReturnsNoItems() {
        // Setup
        final BizBaseOrgOfficeDTO bizBaseOrgOffice = new BizBaseOrgOfficeDTO();
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setCountryCode("countryCode");
        bizBaseOrgOffice.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOffice.setStatus("code");
        bizBaseOrgOffice.setDeleteFlag(false);
        bizBaseOrgOffice.setIds("0");
        bizBaseOrgOffice.setIdsList(Arrays.asList(0L));
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");
        List<BizBaseOrgOfficeDO> emptyList = Collections.emptyList();
        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeTreeList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(emptyList);

        // Run the test
        final PageInfo<BizBaseOrgOfficeTreeDTO> result = bizBaseOrgOfficeServiceImplUnderTest.selectBizBaseOrgOfficeTreeList(
                bizBaseOrgOffice);

        // Verify the results
        assertEquals(0, result.getSize());
    }

    @Test
    public void testSelectBizBaseOrgOfficeTreeList_IBizBaseOrgOfficeManagerSelectBizBaseOrgOfficeListReturnsNoItems() {
        // Setup
        final BizBaseOrgOfficeDTO bizBaseOrgOffice = new BizBaseOrgOfficeDTO();
        bizBaseOrgOffice.setRegionMarketName("regionMarketName");
        bizBaseOrgOffice.setTerritoryName("territoryName");
        bizBaseOrgOffice.setSalesOrgName("salesOrgName");
        bizBaseOrgOffice.setBusinessGroup("businessGroup");
        bizBaseOrgOffice.setGeoCode("geoCode");
        bizBaseOrgOffice.setRegionMarketCode("regionMarketCode");
        bizBaseOrgOffice.setTerritoryCode("territoryCode");
        bizBaseOrgOffice.setCountryCode("countryCode");
        bizBaseOrgOffice.setBpcCountryCode("bpcCountryCode");
        bizBaseOrgOffice.setSalesOrgCode("salesOrgCode");
        bizBaseOrgOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseOrgOffice.setStatus("code");
        bizBaseOrgOffice.setDeleteFlag(false);
        bizBaseOrgOffice.setIds("0");
        bizBaseOrgOffice.setIdsList(Arrays.asList(0L));
        bizBaseOrgOffice.setIsDummy("isDummy");
        bizBaseOrgOffice.setLocalCurrencyCode("localCurrencyCode");
        bizBaseOrgOffice.setCompanyCode("companyCode");
        bizBaseOrgOffice.setAccrualCompanyCode("accrualCompanyCode");

        // Configure IBizBaseOrgOfficeManager.selectBizBaseOrgOfficeTreeList(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO.setCreateBy("loginName");
        baseOrgOfficeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setUpdateBy("loginName");
        baseOrgOfficeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setId(0L);
        baseOrgOfficeDO.setBusinessGroup("businessGroup");
        baseOrgOfficeDO.setGeoCode("geoCode");
        baseOrgOfficeDO.setRegionMarketCode("countryCode");
        baseOrgOfficeDO.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO.setTerritoryCode("territoryCode");
        baseOrgOfficeDO.setTerritoryName("territoryCode");
        baseOrgOfficeDO.setCountryCode("countryCode");
        baseOrgOfficeDO.setCountryName("countryCode");
        baseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO.setStatus("code");
        final List<BizBaseOrgOfficeDO> bizBaseOrgOfficeDOS = Arrays.asList(baseOrgOfficeDO);
        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeTreeList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(bizBaseOrgOfficeDOS);

        when(mockBizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(any(BizBaseOrgOfficeDTO.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<BizBaseOrgOfficeTreeDTO> result = bizBaseOrgOfficeServiceImplUnderTest.selectBizBaseOrgOfficeTreeList(
                bizBaseOrgOffice);

        // Verify the results
        assertEquals(1, result.getSize());
    }

    @Test
    public void testGetByOrgOffice() {
        // Setup
        final BizBaseOrgOfficeDTO expectedResult = new BizBaseOrgOfficeDTO();
        expectedResult.setCreateBy("loginName");
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUpdateBy("loginName");
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setId(0L);
        expectedResult.setBusinessGroup("businessGroup");
        expectedResult.setGeoCode("geoCode");
        expectedResult.setRegionMarketCode("countryCode");
        expectedResult.setRegionMarketName("regionMarketCode");
        expectedResult.setTerritoryCode("territoryCode");
        expectedResult.setTerritoryName("territoryCode");
        expectedResult.setCountryCode("countryCode");
        expectedResult.setCountryName("countryCode");
        expectedResult.setBpcCountryCode("bpcCountryCode");
        expectedResult.setBpcCountryName("bpcCountryCode");
        expectedResult.setSalesOrgCode("salesOrgCode");
        expectedResult.setSalesOrgName("salesOrgCode");
        expectedResult.setSalesOfficeCode("salesOfficeCode");
        expectedResult.setSalesOfficeName("salesOfficeCode");
        expectedResult.setDistributionChannelCode("distributionChannelCode");
        expectedResult.setDistributionChannelName("distributionChannelCode");
        expectedResult.setStatus("code");

        // Configure IBizBaseOrgOfficeManager.getByOrgOffice(...).
        final BizBaseOrgOfficeDO baseOrgOfficeDO = new BizBaseOrgOfficeDO();
        baseOrgOfficeDO.setCreateBy("loginName");
        baseOrgOfficeDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setUpdateBy("loginName");
        baseOrgOfficeDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        baseOrgOfficeDO.setId(0L);
        baseOrgOfficeDO.setBusinessGroup("businessGroup");
        baseOrgOfficeDO.setGeoCode("geoCode");
        baseOrgOfficeDO.setRegionMarketCode("countryCode");
        baseOrgOfficeDO.setRegionMarketName("regionMarketCode");
        baseOrgOfficeDO.setTerritoryCode("territoryCode");
        baseOrgOfficeDO.setTerritoryName("territoryCode");
        baseOrgOfficeDO.setCountryCode("countryCode");
        baseOrgOfficeDO.setCountryName("countryCode");
        baseOrgOfficeDO.setBpcCountryCode("bpcCountryCode");
        baseOrgOfficeDO.setBpcCountryName("bpcCountryCode");
        baseOrgOfficeDO.setSalesOrgCode("salesOrgCode");
        baseOrgOfficeDO.setSalesOrgName("salesOrgCode");
        baseOrgOfficeDO.setSalesOfficeCode("salesOfficeCode");
        baseOrgOfficeDO.setSalesOfficeName("salesOfficeCode");
        baseOrgOfficeDO.setDistributionChannelCode("distributionChannelCode");
        baseOrgOfficeDO.setDistributionChannelName("distributionChannelCode");
        baseOrgOfficeDO.setStatus("code");
        when(mockBizBaseOrgOfficeManager.getByOrgOffice("salesOrgCode", "salesOfficeCode")).thenReturn(baseOrgOfficeDO);

        // Run the test
        final BizBaseOrgOfficeDTO result = bizBaseOrgOfficeServiceImplUnderTest.getByOrgOffice("salesOrgCode",
                "salesOfficeCode");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
