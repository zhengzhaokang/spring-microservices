package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.dto.BizBaseSalesOfficeProfitCenterMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseSalesOfficeProfitCenterMappingDO;
import com.microservices.otmp.masterdata.manager.IBizBaseSalesOfficeProfitCenterMappingManager;
import com.microservices.otmp.masterdata.service.impl.BizBaseSalesOfficeProfitCenterMappingServiceImpl;
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
public class BizBaseSalesOfficeProfitCenterMappingServiceImplTest {

    @Mock
    private IBizBaseSalesOfficeProfitCenterMappingManager mockBizBaseSalesOfficeProfitCenterMappingManager;

    @InjectMocks
    private BizBaseSalesOfficeProfitCenterMappingServiceImpl bizBaseSalesOfficeProfitCenterMappingServiceImplUnderTest;

    @Test
    public void testSelectBizBaseSalesOfficeProfitCenterMappingById() {
        // Setup
        final BizBaseSalesOfficeProfitCenterMappingDTO expectedResult = new BizBaseSalesOfficeProfitCenterMappingDTO();
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setId(0L);
        expectedResult.setSalesOfficeCode("salesOfficeCode");
        expectedResult.setProfitCenterCode("profitCenterCode");
        expectedResult.setDelFlag(0);
        expectedResult.setSalesOrgCode("salesOrgCode");

        // Configure IBizBaseSalesOfficeProfitCenterMappingManager.selectBizBaseSalesOfficeProfitCenterMappingById(...).
        final BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMappingDO = new BizBaseSalesOfficeProfitCenterMappingDO();
        bizBaseSalesOfficeProfitCenterMappingDO.setId(0L);
        bizBaseSalesOfficeProfitCenterMappingDO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeProfitCenterMappingDO.setProfitCenterCode("profitCenterCode");
        bizBaseSalesOfficeProfitCenterMappingDO.setDelFlag(0);
        bizBaseSalesOfficeProfitCenterMappingDO.setSalesOrgCode("salesOrgCode");
        when(mockBizBaseSalesOfficeProfitCenterMappingManager.selectBizBaseSalesOfficeProfitCenterMappingById(
                0L)).thenReturn(bizBaseSalesOfficeProfitCenterMappingDO);

        // Run the test
        final BizBaseSalesOfficeProfitCenterMappingDTO result = bizBaseSalesOfficeProfitCenterMappingServiceImplUnderTest.selectBizBaseSalesOfficeProfitCenterMappingById(
                0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseSalesOfficeProfitCenterMappingList() {
        // Setup
        final BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping = new BizBaseSalesOfficeProfitCenterMappingDTO();
        bizBaseSalesOfficeProfitCenterMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMapping.setId(0L);
        bizBaseSalesOfficeProfitCenterMapping.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeProfitCenterMapping.setProfitCenterCode("profitCenterCode");
        bizBaseSalesOfficeProfitCenterMapping.setDelFlag(0);
        bizBaseSalesOfficeProfitCenterMapping.setSalesOrgCode("salesOrgCode");

        final BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMappingDTO = new BizBaseSalesOfficeProfitCenterMappingDTO();
        bizBaseSalesOfficeProfitCenterMappingDTO.setCreateTime(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMappingDTO.setUpdateTime(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMappingDTO.setId(0L);
        bizBaseSalesOfficeProfitCenterMappingDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeProfitCenterMappingDTO.setProfitCenterCode("profitCenterCode");
        bizBaseSalesOfficeProfitCenterMappingDTO.setDelFlag(0);
        bizBaseSalesOfficeProfitCenterMappingDTO.setSalesOrgCode("salesOrgCode");
        final List<BizBaseSalesOfficeProfitCenterMappingDTO> expectedResult = Arrays.asList(
                bizBaseSalesOfficeProfitCenterMappingDTO);

        // Configure IBizBaseSalesOfficeProfitCenterMappingManager.selectBizBaseSalesOfficeProfitCenterMappingList(...).
        final BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMappingDO = new BizBaseSalesOfficeProfitCenterMappingDO();
        bizBaseSalesOfficeProfitCenterMappingDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMappingDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMappingDO.setId(0L);
        bizBaseSalesOfficeProfitCenterMappingDO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeProfitCenterMappingDO.setProfitCenterCode("profitCenterCode");
        bizBaseSalesOfficeProfitCenterMappingDO.setDelFlag(0);
        bizBaseSalesOfficeProfitCenterMappingDO.setSalesOrgCode("salesOrgCode");
        final List<BizBaseSalesOfficeProfitCenterMappingDO> bizBaseSalesOfficeProfitCenterMappingDOS = Arrays.asList(
                bizBaseSalesOfficeProfitCenterMappingDO);
        when(mockBizBaseSalesOfficeProfitCenterMappingManager.selectBizBaseSalesOfficeProfitCenterMappingList(
                any(BizBaseSalesOfficeProfitCenterMappingDTO.class))).thenReturn(bizBaseSalesOfficeProfitCenterMappingDOS);

        // Run the test
        final List<BizBaseSalesOfficeProfitCenterMappingDTO> result = bizBaseSalesOfficeProfitCenterMappingServiceImplUnderTest.selectBizBaseSalesOfficeProfitCenterMappingList(
                bizBaseSalesOfficeProfitCenterMapping);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseSalesOfficeProfitCenterMappingList_IBizBaseSalesOfficeProfitCenterMappingManagerReturnsNoItems() {
        // Setup
        final BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping = new BizBaseSalesOfficeProfitCenterMappingDTO();
        bizBaseSalesOfficeProfitCenterMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMapping.setId(0L);
        bizBaseSalesOfficeProfitCenterMapping.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeProfitCenterMapping.setProfitCenterCode("profitCenterCode");
        bizBaseSalesOfficeProfitCenterMapping.setDelFlag(0);
        bizBaseSalesOfficeProfitCenterMapping.setSalesOrgCode("salesOrgCode");

//        when(mockBizBaseSalesOfficeProfitCenterMappingManager.selectBizBaseSalesOfficeProfitCenterMappingList(
//                new BizBaseSalesOfficeProfitCenterMappingDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseSalesOfficeProfitCenterMappingDTO> result = bizBaseSalesOfficeProfitCenterMappingServiceImplUnderTest.selectBizBaseSalesOfficeProfitCenterMappingList(
                bizBaseSalesOfficeProfitCenterMapping);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseSalesOfficeProfitCenterMapping() {
        // Setup
        final BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping = new BizBaseSalesOfficeProfitCenterMappingDTO();
        bizBaseSalesOfficeProfitCenterMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMapping.setId(0L);
        bizBaseSalesOfficeProfitCenterMapping.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeProfitCenterMapping.setProfitCenterCode("profitCenterCode");
        bizBaseSalesOfficeProfitCenterMapping.setDelFlag(0);
        bizBaseSalesOfficeProfitCenterMapping.setSalesOrgCode("salesOrgCode");

//        when(mockBizBaseSalesOfficeProfitCenterMappingManager.insertBizBaseSalesOfficeProfitCenterMapping(
//                new BizBaseSalesOfficeProfitCenterMappingDO())).thenReturn(0);

        // Run the test
        final int result = bizBaseSalesOfficeProfitCenterMappingServiceImplUnderTest.insertBizBaseSalesOfficeProfitCenterMapping(
                bizBaseSalesOfficeProfitCenterMapping);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseSalesOfficeProfitCenterMapping() {
        // Setup
        final BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping = new BizBaseSalesOfficeProfitCenterMappingDTO();
        bizBaseSalesOfficeProfitCenterMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMapping.setId(0L);
        bizBaseSalesOfficeProfitCenterMapping.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeProfitCenterMapping.setProfitCenterCode("profitCenterCode");
        bizBaseSalesOfficeProfitCenterMapping.setDelFlag(0);
        bizBaseSalesOfficeProfitCenterMapping.setSalesOrgCode("salesOrgCode");

//        when(mockBizBaseSalesOfficeProfitCenterMappingManager.updateBizBaseSalesOfficeProfitCenterMapping(
//                new BizBaseSalesOfficeProfitCenterMappingDO())).thenReturn(0);

        // Run the test
        final int result = bizBaseSalesOfficeProfitCenterMappingServiceImplUnderTest.updateBizBaseSalesOfficeProfitCenterMapping(
                bizBaseSalesOfficeProfitCenterMapping);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSalesOfficeProfitCenterMappingByIds() {
        // Setup
        when(mockBizBaseSalesOfficeProfitCenterMappingManager.deleteBizBaseSalesOfficeProfitCenterMappingByIds(
                any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseSalesOfficeProfitCenterMappingServiceImplUnderTest.deleteBizBaseSalesOfficeProfitCenterMappingByIds(
                new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSalesOfficeProfitCenterMappingById() {
        // Setup
        when(mockBizBaseSalesOfficeProfitCenterMappingManager.deleteBizBaseSalesOfficeProfitCenterMappingById(
                0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseSalesOfficeProfitCenterMappingServiceImplUnderTest.deleteBizBaseSalesOfficeProfitCenterMappingById(
                0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBySalesOffice() {
        // Setup
        final BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping = new BizBaseSalesOfficeProfitCenterMappingDTO();
        bizBaseSalesOfficeProfitCenterMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeProfitCenterMapping.setId(0L);
        bizBaseSalesOfficeProfitCenterMapping.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeProfitCenterMapping.setProfitCenterCode("profitCenterCode");
        bizBaseSalesOfficeProfitCenterMapping.setDelFlag(0);
        bizBaseSalesOfficeProfitCenterMapping.setSalesOrgCode("salesOrgCode");

//        when(mockBizBaseSalesOfficeProfitCenterMappingManager.updateBySalesOffice(
//                new BizBaseSalesOfficeProfitCenterMappingDO())).thenReturn(0);

        // Run the test
        final int result = bizBaseSalesOfficeProfitCenterMappingServiceImplUnderTest.updateBySalesOffice(
                bizBaseSalesOfficeProfitCenterMapping);

        // Verify the results
        assertEquals(0, result);
    }
}
