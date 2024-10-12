package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.dto.BizBaseCompanyLocalCurrecyMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseCompanyLocalCurrecyMappingDO;
import com.microservices.otmp.masterdata.manager.IBizBaseCompanyLocalCurrecyMappingManager;
import com.microservices.otmp.masterdata.service.impl.BizBaseCompanyLocalCurrecyMappingServiceImpl;
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
public class BizBaseCompanyLocalCurrecyMappingServiceImplTest {

    @Mock
    private IBizBaseCompanyLocalCurrecyMappingManager mockBizBaseCompanyLocalCurrecyMappingManager;

    @InjectMocks
    private BizBaseCompanyLocalCurrecyMappingServiceImpl bizBaseCompanyLocalCurrecyMappingServiceImplUnderTest;

    @Test
    public void testSelectBizBaseCompanyLocalCurrecyMappingById() {
        // Setup
        final BizBaseCompanyLocalCurrecyMappingDTO expectedResult = new BizBaseCompanyLocalCurrecyMappingDTO();
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setId(0L);
        expectedResult.setCompanyCode("companyCode");
        expectedResult.setLocalCurrencyCode("localCurrencyCode");
        expectedResult.setDelFlag(0);
        expectedResult.setCompanyName("companyName");

        // Configure IBizBaseCompanyLocalCurrecyMappingManager.selectBizBaseCompanyLocalCurrecyMappingById(...).
        final BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMappingDO = new BizBaseCompanyLocalCurrecyMappingDO();
        bizBaseCompanyLocalCurrecyMappingDO.setId(0L);
        bizBaseCompanyLocalCurrecyMappingDO.setCompanyCode("companyCode");
        bizBaseCompanyLocalCurrecyMappingDO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseCompanyLocalCurrecyMappingDO.setDelFlag(0);
        bizBaseCompanyLocalCurrecyMappingDO.setCompanyName("companyName");
        when(mockBizBaseCompanyLocalCurrecyMappingManager.selectBizBaseCompanyLocalCurrecyMappingById(0L))
                .thenReturn(bizBaseCompanyLocalCurrecyMappingDO);

        // Run the test
        final BizBaseCompanyLocalCurrecyMappingDTO result = bizBaseCompanyLocalCurrecyMappingServiceImplUnderTest.selectBizBaseCompanyLocalCurrecyMappingById(
                0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseCompanyLocalCurrecyMappingList() {
        // Setup
        final BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping = new BizBaseCompanyLocalCurrecyMappingDTO();
        bizBaseCompanyLocalCurrecyMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMapping.setId(0L);
        bizBaseCompanyLocalCurrecyMapping.setCompanyCode("companyCode");
        bizBaseCompanyLocalCurrecyMapping.setLocalCurrencyCode("localCurrencyCode");
        bizBaseCompanyLocalCurrecyMapping.setDelFlag(0);
        bizBaseCompanyLocalCurrecyMapping.setCompanyName("companyName");

        final BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMappingDTO = new BizBaseCompanyLocalCurrecyMappingDTO();
        bizBaseCompanyLocalCurrecyMappingDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMappingDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMappingDTO.setId(0L);
        bizBaseCompanyLocalCurrecyMappingDTO.setCompanyCode("companyCode");
        bizBaseCompanyLocalCurrecyMappingDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseCompanyLocalCurrecyMappingDTO.setDelFlag(0);
        bizBaseCompanyLocalCurrecyMappingDTO.setCompanyName("companyName");
        final List<BizBaseCompanyLocalCurrecyMappingDTO> expectedResult = Arrays.asList(
                bizBaseCompanyLocalCurrecyMappingDTO);

        // Configure IBizBaseCompanyLocalCurrecyMappingManager.selectBizBaseCompanyLocalCurrecyMappingList(...).
        final BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMappingDO = new BizBaseCompanyLocalCurrecyMappingDO();
        bizBaseCompanyLocalCurrecyMappingDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMappingDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMappingDO.setId(0L);
        bizBaseCompanyLocalCurrecyMappingDO.setCompanyCode("companyCode");
        bizBaseCompanyLocalCurrecyMappingDO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseCompanyLocalCurrecyMappingDO.setDelFlag(0);
        bizBaseCompanyLocalCurrecyMappingDO.setCompanyName("companyName");
        final List<BizBaseCompanyLocalCurrecyMappingDO> bizBaseCompanyLocalCurrecyMappingDOS = Arrays.asList(
                bizBaseCompanyLocalCurrecyMappingDO);
        when(mockBizBaseCompanyLocalCurrecyMappingManager.selectBizBaseCompanyLocalCurrecyMappingList(
                any(BizBaseCompanyLocalCurrecyMappingDTO.class))).thenReturn(bizBaseCompanyLocalCurrecyMappingDOS);

        // Run the test
        final List<BizBaseCompanyLocalCurrecyMappingDTO> result = bizBaseCompanyLocalCurrecyMappingServiceImplUnderTest.selectBizBaseCompanyLocalCurrecyMappingList(
                bizBaseCompanyLocalCurrecyMapping);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseCompanyLocalCurrecyMappingList_IBizBaseCompanyLocalCurrecyMappingManagerReturnsNoItems() {
        // Setup
        final BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping = new BizBaseCompanyLocalCurrecyMappingDTO();
        bizBaseCompanyLocalCurrecyMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMapping.setId(0L);
        bizBaseCompanyLocalCurrecyMapping.setCompanyCode("companyCode");
        bizBaseCompanyLocalCurrecyMapping.setLocalCurrencyCode("localCurrencyCode");
        bizBaseCompanyLocalCurrecyMapping.setDelFlag(0);
        bizBaseCompanyLocalCurrecyMapping.setCompanyName("companyName");

//        when(mockBizBaseCompanyLocalCurrecyMappingManager.selectBizBaseCompanyLocalCurrecyMappingList(
//                new BizBaseCompanyLocalCurrecyMappingDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseCompanyLocalCurrecyMappingDTO> result = bizBaseCompanyLocalCurrecyMappingServiceImplUnderTest.selectBizBaseCompanyLocalCurrecyMappingList(
                bizBaseCompanyLocalCurrecyMapping);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseCompanyLocalCurrecyMapping() {
        // Setup
        final BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping = new BizBaseCompanyLocalCurrecyMappingDTO();
        bizBaseCompanyLocalCurrecyMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMapping.setId(0L);
        bizBaseCompanyLocalCurrecyMapping.setCompanyCode("companyCode");
        bizBaseCompanyLocalCurrecyMapping.setLocalCurrencyCode("localCurrencyCode");
        bizBaseCompanyLocalCurrecyMapping.setDelFlag(0);
        bizBaseCompanyLocalCurrecyMapping.setCompanyName("companyName");

//        when(mockBizBaseCompanyLocalCurrecyMappingManager.insertBizBaseCompanyLocalCurrecyMapping(
//                new BizBaseCompanyLocalCurrecyMappingDO())).thenReturn(0);

        // Run the test
        final int result = bizBaseCompanyLocalCurrecyMappingServiceImplUnderTest.insertBizBaseCompanyLocalCurrecyMapping(
                bizBaseCompanyLocalCurrecyMapping);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseCompanyLocalCurrecyMapping() {
        // Setup
        final BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping = new BizBaseCompanyLocalCurrecyMappingDTO();
        bizBaseCompanyLocalCurrecyMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMapping.setId(0L);
        bizBaseCompanyLocalCurrecyMapping.setCompanyCode("companyCode");
        bizBaseCompanyLocalCurrecyMapping.setLocalCurrencyCode("localCurrencyCode");
        bizBaseCompanyLocalCurrecyMapping.setDelFlag(0);
        bizBaseCompanyLocalCurrecyMapping.setCompanyName("companyName");

//        when(mockBizBaseCompanyLocalCurrecyMappingManager.updateBizBaseCompanyLocalCurrecyMapping(
//                new BizBaseCompanyLocalCurrecyMappingDO())).thenReturn(0);

        // Run the test
        final int result = bizBaseCompanyLocalCurrecyMappingServiceImplUnderTest.updateBizBaseCompanyLocalCurrecyMapping(
                bizBaseCompanyLocalCurrecyMapping);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseCompanyLocalCurrecyMappingByIds() {
        // Setup
        when(mockBizBaseCompanyLocalCurrecyMappingManager.deleteBizBaseCompanyLocalCurrecyMappingByIds(
                any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseCompanyLocalCurrecyMappingServiceImplUnderTest.deleteBizBaseCompanyLocalCurrecyMappingByIds(
                new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseCompanyLocalCurrecyMappingById() {
        // Setup
        when(mockBizBaseCompanyLocalCurrecyMappingManager.deleteBizBaseCompanyLocalCurrecyMappingById(0L))
                .thenReturn(0);

        // Run the test
        final int result = bizBaseCompanyLocalCurrecyMappingServiceImplUnderTest.deleteBizBaseCompanyLocalCurrecyMappingById(
                0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateByCompanyCode() {
        // Setup
        final BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping = new BizBaseCompanyLocalCurrecyMappingDTO();
        bizBaseCompanyLocalCurrecyMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseCompanyLocalCurrecyMapping.setId(0L);
        bizBaseCompanyLocalCurrecyMapping.setCompanyCode("companyCode");
        bizBaseCompanyLocalCurrecyMapping.setLocalCurrencyCode("localCurrencyCode");
        bizBaseCompanyLocalCurrecyMapping.setDelFlag(0);
        bizBaseCompanyLocalCurrecyMapping.setCompanyName("companyName");

//        when(mockBizBaseCompanyLocalCurrecyMappingManager.updateByCompanyCode(
//                new BizBaseCompanyLocalCurrecyMappingDO())).thenReturn(0);

        // Run the test
        final int result = bizBaseCompanyLocalCurrecyMappingServiceImplUnderTest.updateByCompanyCode(
                bizBaseCompanyLocalCurrecyMapping);

        // Verify the results
        assertEquals(0, result);
    }
}
