package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.dto.BizBaseBwBuBgMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBwBuBgMappingDO;
import com.microservices.otmp.masterdata.manager.IBizBaseBwBuBgMappingManager;
import com.microservices.otmp.masterdata.service.impl.BizBaseBwBuBgMappingServiceImpl;
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
public class BizBaseBwBuBgMappingServiceImplTest {

    @Mock
    private IBizBaseBwBuBgMappingManager mockBizBaseBwBuBgMappingManager;

    @InjectMocks
    private BizBaseBwBuBgMappingServiceImpl bizBaseBwBuBgMappingServiceImplUnderTest;

    @Test
    public void testSelectBizBaseBwBuBgMappingById() {
        // Setup
        final BizBaseBwBuBgMappingDTO expectedResult = new BizBaseBwBuBgMappingDTO();
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setId(0L);
        expectedResult.setBwBu("bwBu");
        expectedResult.setBusinessGroup("businessGroup");
        expectedResult.setProfitCenterCode("profitCenterCode");
        expectedResult.setValidateFrom("validateFrom");
        expectedResult.setValidateTo("validateTo");
        expectedResult.setDelFlag(0);

        // Configure IBizBaseBwBuBgMappingManager.selectBizBaseBwBuBgMappingById(...).
        final BizBaseBwBuBgMappingDO bizBaseBwBuBgMappingDO = new BizBaseBwBuBgMappingDO();
        bizBaseBwBuBgMappingDO.setId(0L);
        bizBaseBwBuBgMappingDO.setBwBu("bwBu");
        bizBaseBwBuBgMappingDO.setBusinessGroup("businessGroup");
        bizBaseBwBuBgMappingDO.setProfitCenterCode("profitCenterCode");
        bizBaseBwBuBgMappingDO.setValidateFrom("validateFrom");
        bizBaseBwBuBgMappingDO.setValidateTo("validateTo");
        bizBaseBwBuBgMappingDO.setDelFlag(0);
        when(mockBizBaseBwBuBgMappingManager.selectBizBaseBwBuBgMappingById(0L)).thenReturn(bizBaseBwBuBgMappingDO);

        // Run the test
        final BizBaseBwBuBgMappingDTO result = bizBaseBwBuBgMappingServiceImplUnderTest.selectBizBaseBwBuBgMappingById(
                0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseBwBuBgMappingList() {
        // Setup
        final BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping = new BizBaseBwBuBgMappingDTO();
        bizBaseBwBuBgMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMapping.setId(0L);
        bizBaseBwBuBgMapping.setBwBu("bwBu");
        bizBaseBwBuBgMapping.setBusinessGroup("businessGroup");
        bizBaseBwBuBgMapping.setProfitCenterCode("profitCenterCode");
        bizBaseBwBuBgMapping.setValidateFrom("validateFrom");
        bizBaseBwBuBgMapping.setValidateTo("validateTo");
        bizBaseBwBuBgMapping.setDelFlag(0);

        final BizBaseBwBuBgMappingDTO bizBaseBwBuBgMappingDTO = new BizBaseBwBuBgMappingDTO();
        bizBaseBwBuBgMappingDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMappingDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMappingDTO.setId(0L);
        bizBaseBwBuBgMappingDTO.setBwBu("bwBu");
        bizBaseBwBuBgMappingDTO.setBusinessGroup("businessGroup");
        bizBaseBwBuBgMappingDTO.setProfitCenterCode("profitCenterCode");
        bizBaseBwBuBgMappingDTO.setValidateFrom("validateFrom");
        bizBaseBwBuBgMappingDTO.setValidateTo("validateTo");
        bizBaseBwBuBgMappingDTO.setDelFlag(0);
        final List<BizBaseBwBuBgMappingDTO> expectedResult = Arrays.asList(bizBaseBwBuBgMappingDTO);

        // Configure IBizBaseBwBuBgMappingManager.selectBizBaseBwBuBgMappingList(...).
        final BizBaseBwBuBgMappingDO bizBaseBwBuBgMappingDO = new BizBaseBwBuBgMappingDO();
        bizBaseBwBuBgMappingDO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMappingDO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMappingDO.setId(0L);
        bizBaseBwBuBgMappingDO.setBwBu("bwBu");
        bizBaseBwBuBgMappingDO.setBusinessGroup("businessGroup");
        bizBaseBwBuBgMappingDO.setProfitCenterCode("profitCenterCode");
        bizBaseBwBuBgMappingDO.setValidateFrom("validateFrom");
        bizBaseBwBuBgMappingDO.setValidateTo("validateTo");
        bizBaseBwBuBgMappingDO.setDelFlag(0);
        final List<BizBaseBwBuBgMappingDO> bizBaseBwBuBgMappingDOS = Arrays.asList(bizBaseBwBuBgMappingDO);
        when(mockBizBaseBwBuBgMappingManager.selectBizBaseBwBuBgMappingList(any(BizBaseBwBuBgMappingDTO.class)))
                .thenReturn(bizBaseBwBuBgMappingDOS);

        // Run the test
        final List<BizBaseBwBuBgMappingDTO> result = bizBaseBwBuBgMappingServiceImplUnderTest.selectBizBaseBwBuBgMappingList(
                bizBaseBwBuBgMapping);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseBwBuBgMappingList_IBizBaseBwBuBgMappingManagerReturnsNoItems() {
        // Setup
        final BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping = new BizBaseBwBuBgMappingDTO();
        bizBaseBwBuBgMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMapping.setId(0L);
        bizBaseBwBuBgMapping.setBwBu("bwBu");
        bizBaseBwBuBgMapping.setBusinessGroup("businessGroup");
        bizBaseBwBuBgMapping.setProfitCenterCode("profitCenterCode");
        bizBaseBwBuBgMapping.setValidateFrom("validateFrom");
        bizBaseBwBuBgMapping.setValidateTo("validateTo");
        bizBaseBwBuBgMapping.setDelFlag(0);

//        when(mockBizBaseBwBuBgMappingManager.selectBizBaseBwBuBgMappingList(new BizBaseBwBuBgMappingDTO()))
//                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseBwBuBgMappingDTO> result = bizBaseBwBuBgMappingServiceImplUnderTest.selectBizBaseBwBuBgMappingList(
                bizBaseBwBuBgMapping);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseBwBuBgMapping() {
        // Setup
        final BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping = new BizBaseBwBuBgMappingDTO();
        bizBaseBwBuBgMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMapping.setId(0L);
        bizBaseBwBuBgMapping.setBwBu("bwBu");
        bizBaseBwBuBgMapping.setBusinessGroup("businessGroup");
        bizBaseBwBuBgMapping.setProfitCenterCode("profitCenterCode");
        bizBaseBwBuBgMapping.setValidateFrom("validateFrom");
        bizBaseBwBuBgMapping.setValidateTo("validateTo");
        bizBaseBwBuBgMapping.setDelFlag(0);

//        when(mockBizBaseBwBuBgMappingManager.insertBizBaseBwBuBgMapping(new BizBaseBwBuBgMappingDO())).thenReturn(0);

        // Run the test
        final int result = bizBaseBwBuBgMappingServiceImplUnderTest.insertBizBaseBwBuBgMapping(bizBaseBwBuBgMapping);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseBwBuBgMapping() {
        // Setup
        final BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping = new BizBaseBwBuBgMappingDTO();
        bizBaseBwBuBgMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMapping.setId(0L);
        bizBaseBwBuBgMapping.setBwBu("bwBu");
        bizBaseBwBuBgMapping.setBusinessGroup("businessGroup");
        bizBaseBwBuBgMapping.setProfitCenterCode("profitCenterCode");
        bizBaseBwBuBgMapping.setValidateFrom("validateFrom");
        bizBaseBwBuBgMapping.setValidateTo("validateTo");
        bizBaseBwBuBgMapping.setDelFlag(0);

//        when(mockBizBaseBwBuBgMappingManager.updateBizBaseBwBuBgMapping(new BizBaseBwBuBgMappingDO())).thenReturn(0);

        // Run the test
        final int result = bizBaseBwBuBgMappingServiceImplUnderTest.updateBizBaseBwBuBgMapping(bizBaseBwBuBgMapping);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseBwBuBgMappingByIds() {
        // Setup
        when(mockBizBaseBwBuBgMappingManager.deleteBizBaseBwBuBgMappingByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseBwBuBgMappingServiceImplUnderTest.deleteBizBaseBwBuBgMappingByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseBwBuBgMappingById() {
        // Setup
        when(mockBizBaseBwBuBgMappingManager.deleteBizBaseBwBuBgMappingById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseBwBuBgMappingServiceImplUnderTest.deleteBizBaseBwBuBgMappingById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateByKey() {
        // Setup
        final BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping = new BizBaseBwBuBgMappingDTO();
        bizBaseBwBuBgMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseBwBuBgMapping.setId(0L);
        bizBaseBwBuBgMapping.setBwBu("bwBu");
        bizBaseBwBuBgMapping.setBusinessGroup("businessGroup");
        bizBaseBwBuBgMapping.setProfitCenterCode("profitCenterCode");
        bizBaseBwBuBgMapping.setValidateFrom("validateFrom");
        bizBaseBwBuBgMapping.setValidateTo("validateTo");
        bizBaseBwBuBgMapping.setDelFlag(0);

//        when(mockBizBaseBwBuBgMappingManager.updateByKey(new BizBaseBwBuBgMappingDO())).thenReturn(0);

        // Run the test
        final int result = bizBaseBwBuBgMappingServiceImplUnderTest.updateByKey(bizBaseBwBuBgMapping);

        // Verify the results
        assertEquals(0, result);
    }
}
