package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.dto.BizSdmsSystemAnnouncementDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsSystemAnnouncementDO;
import com.microservices.otmp.system.manager.IBizSdmsSystemAnnouncementManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BizSdmsSystemAnnouncementServiceImplTest {

    @Mock
    private IBizSdmsSystemAnnouncementManager mockBizSdmsSystemAnnouncementManager;

    @InjectMocks
    private BizSdmsSystemAnnouncementServiceImpl bizSdmsSystemAnnouncementServiceImplUnderTest;

    @Test
    public void testSelectBizSdmsSystemAnnouncementById() {
        // Setup
        final BizSdmsSystemAnnouncementDTO expectedResult = new BizSdmsSystemAnnouncementDTO();
        expectedResult.setId(1L);
        expectedResult.setCategory("category");
        expectedResult.setPriority("priority");
        expectedResult.setTitle("title");
        expectedResult.setDetail("detail");
        expectedResult.setBusinessGroup("businessGroup");
        expectedResult.setGeoCode("geoCode");
        expectedResult.setStatus("status");
        expectedResult.setDelFlag(0);
        expectedResult.setCreateDateStart("createDateStart");

        // Configure IBizSdmsSystemAnnouncementManager.selectBizSdmsSystemAnnouncementById(...).
        final BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncementDO = new BizSdmsSystemAnnouncementDO();
        bizSdmsSystemAnnouncementDO.setId(1L);
        bizSdmsSystemAnnouncementDO.setCategory("category");
        bizSdmsSystemAnnouncementDO.setPriority("priority");
        bizSdmsSystemAnnouncementDO.setTitle("title");
        bizSdmsSystemAnnouncementDO.setDetail("detail");
        bizSdmsSystemAnnouncementDO.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncementDO.setGeoCode("geoCode");
        bizSdmsSystemAnnouncementDO.setStatus("status");
        bizSdmsSystemAnnouncementDO.setDelFlag(0);
        when(mockBizSdmsSystemAnnouncementManager.selectBizSdmsSystemAnnouncementById(1L)).thenReturn(bizSdmsSystemAnnouncementDO);

        // Run the test
        final BizSdmsSystemAnnouncementDTO result = bizSdmsSystemAnnouncementServiceImplUnderTest.selectBizSdmsSystemAnnouncementById(1L);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    public void testSelectBizSdmsSystemAnnouncementList() {
        // Setup
        final BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement = new BizSdmsSystemAnnouncementDTO();
        bizSdmsSystemAnnouncement.setId(0L);
        bizSdmsSystemAnnouncement.setCategory("category");
        bizSdmsSystemAnnouncement.setPriority("priority");
        bizSdmsSystemAnnouncement.setTitle("title");
        bizSdmsSystemAnnouncement.setDetail("detail");
        bizSdmsSystemAnnouncement.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncement.setGeoCode("geoCode");
        bizSdmsSystemAnnouncement.setStatus("status");
        bizSdmsSystemAnnouncement.setDelFlag(0);
        bizSdmsSystemAnnouncement.setCreateDateStart("createDateStart");

        // Configure IBizSdmsSystemAnnouncementManager.selectBizSdmsSystemAnnouncementList(...).
        final BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncementDO = new BizSdmsSystemAnnouncementDO();
        bizSdmsSystemAnnouncementDO.setId(0L);
        bizSdmsSystemAnnouncementDO.setCategory("category");
        bizSdmsSystemAnnouncementDO.setPriority("priority");
        bizSdmsSystemAnnouncementDO.setTitle("title");
        bizSdmsSystemAnnouncementDO.setDetail("detail");
        bizSdmsSystemAnnouncementDO.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncementDO.setGeoCode("geoCode");
        bizSdmsSystemAnnouncementDO.setStatus("status");
        bizSdmsSystemAnnouncementDO.setDelFlag(0);
        final List<BizSdmsSystemAnnouncementDO> bizSdmsSystemAnnouncementDOS = Arrays.asList(bizSdmsSystemAnnouncementDO);
        when(mockBizSdmsSystemAnnouncementManager.selectBizSdmsSystemAnnouncementList(new BizSdmsSystemAnnouncementDTO())).thenReturn(bizSdmsSystemAnnouncementDOS);

        // Run the test
        final PageInfo<BizSdmsSystemAnnouncementDTO> result = bizSdmsSystemAnnouncementServiceImplUnderTest.selectBizSdmsSystemAnnouncementList(bizSdmsSystemAnnouncement);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsSystemAnnouncementList_IBizSdmsSystemAnnouncementManagerReturnsNoItems() {
        // Setup
        final BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement = new BizSdmsSystemAnnouncementDTO();
        bizSdmsSystemAnnouncement.setId(0L);
        bizSdmsSystemAnnouncement.setCategory("category");
        bizSdmsSystemAnnouncement.setPriority("priority");
        bizSdmsSystemAnnouncement.setTitle("title");
        bizSdmsSystemAnnouncement.setDetail("detail");
        bizSdmsSystemAnnouncement.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncement.setGeoCode("geoCode");
        bizSdmsSystemAnnouncement.setStatus("status");
        bizSdmsSystemAnnouncement.setDelFlag(0);
        bizSdmsSystemAnnouncement.setCreateDateStart("createDateStart");

        when(mockBizSdmsSystemAnnouncementManager.selectBizSdmsSystemAnnouncementList(new BizSdmsSystemAnnouncementDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<BizSdmsSystemAnnouncementDTO> result = bizSdmsSystemAnnouncementServiceImplUnderTest.selectBizSdmsSystemAnnouncementList(bizSdmsSystemAnnouncement);

        // Verify the results
    }

    @Test
    public void testInsertBizSdmsSystemAnnouncement() {
        // Setup
        final BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement = new BizSdmsSystemAnnouncementDTO();
        bizSdmsSystemAnnouncement.setId(0L);
        bizSdmsSystemAnnouncement.setCategory("category");
        bizSdmsSystemAnnouncement.setPriority("priority");
        bizSdmsSystemAnnouncement.setTitle("title");
        bizSdmsSystemAnnouncement.setDetail("detail");
        bizSdmsSystemAnnouncement.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncement.setGeoCode("geoCode");
        bizSdmsSystemAnnouncement.setStatus("status");
        bizSdmsSystemAnnouncement.setDelFlag(0);
        bizSdmsSystemAnnouncement.setCreateDateStart("createDateStart");

        when(mockBizSdmsSystemAnnouncementManager.insertBizSdmsSystemAnnouncement(new BizSdmsSystemAnnouncementDO())).thenReturn(0);

        // Run the test
        final int result = bizSdmsSystemAnnouncementServiceImplUnderTest.insertBizSdmsSystemAnnouncement(bizSdmsSystemAnnouncement);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsSystemAnnouncement() {
        // Setup
        final BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement = new BizSdmsSystemAnnouncementDTO();
        bizSdmsSystemAnnouncement.setId(0L);
        bizSdmsSystemAnnouncement.setCategory("category");
        bizSdmsSystemAnnouncement.setPriority("priority");
        bizSdmsSystemAnnouncement.setTitle("title");
        bizSdmsSystemAnnouncement.setDetail("detail");
        bizSdmsSystemAnnouncement.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncement.setGeoCode("geoCode");
        bizSdmsSystemAnnouncement.setStatus("status");
        bizSdmsSystemAnnouncement.setDelFlag(0);
        bizSdmsSystemAnnouncement.setCreateDateStart("createDateStart");

        when(mockBizSdmsSystemAnnouncementManager.updateBizSdmsSystemAnnouncement(new BizSdmsSystemAnnouncementDO())).thenReturn(0);

        // Run the test
        final int result = bizSdmsSystemAnnouncementServiceImplUnderTest.updateBizSdmsSystemAnnouncement(bizSdmsSystemAnnouncement);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsSystemAnnouncementByIds() {
        // Setup
        when(mockBizSdmsSystemAnnouncementManager.deleteBizSdmsSystemAnnouncementByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsSystemAnnouncementServiceImplUnderTest.deleteBizSdmsSystemAnnouncementByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsSystemAnnouncementById() {
        // Setup
        when(mockBizSdmsSystemAnnouncementManager.deleteBizSdmsSystemAnnouncementById(0L)).thenReturn(0);

        // Run the test
        final int result = bizSdmsSystemAnnouncementServiceImplUnderTest.deleteBizSdmsSystemAnnouncementById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSee() {
        // Setup
        final BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement = new BizSdmsSystemAnnouncementDTO();
        bizSdmsSystemAnnouncement.setId(0L);
        bizSdmsSystemAnnouncement.setCategory("category");
        bizSdmsSystemAnnouncement.setPriority("priority");
        bizSdmsSystemAnnouncement.setTitle("title");
        bizSdmsSystemAnnouncement.setDetail("detail");
        bizSdmsSystemAnnouncement.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncement.setGeoCode("geoCode");
        bizSdmsSystemAnnouncement.setStatus("status");
        bizSdmsSystemAnnouncement.setDelFlag(0);
        bizSdmsSystemAnnouncement.setCreateDateStart("createDateStart");

        final BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncementDTO = new BizSdmsSystemAnnouncementDTO();
        bizSdmsSystemAnnouncementDTO.setId(0L);
        bizSdmsSystemAnnouncementDTO.setCategory("category");
        bizSdmsSystemAnnouncementDTO.setPriority("priority");
        bizSdmsSystemAnnouncementDTO.setTitle("title");
        bizSdmsSystemAnnouncementDTO.setDetail("detail");
        bizSdmsSystemAnnouncementDTO.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncementDTO.setGeoCode("geoCode");
        bizSdmsSystemAnnouncementDTO.setStatus("status");
        bizSdmsSystemAnnouncementDTO.setDelFlag(0);
        bizSdmsSystemAnnouncementDTO.setCreateDateStart("createDateStart");
        final List<BizSdmsSystemAnnouncementDTO> expectedResult = Arrays.asList(bizSdmsSystemAnnouncementDTO);

        // Configure IBizSdmsSystemAnnouncementManager.getList(...).
        final BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncementDTO1 = new BizSdmsSystemAnnouncementDTO();
        bizSdmsSystemAnnouncementDTO1.setId(0L);
        bizSdmsSystemAnnouncementDTO1.setCategory("category");
        bizSdmsSystemAnnouncementDTO1.setPriority("priority");
        bizSdmsSystemAnnouncementDTO1.setTitle("title");
        bizSdmsSystemAnnouncementDTO1.setDetail("detail");
        bizSdmsSystemAnnouncementDTO1.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncementDTO1.setGeoCode("geoCode");
        bizSdmsSystemAnnouncementDTO1.setStatus("status");
        bizSdmsSystemAnnouncementDTO1.setDelFlag(0);
        bizSdmsSystemAnnouncementDTO1.setCreateDateStart("createDateStart");
        final List<BizSdmsSystemAnnouncementDTO> bizSdmsSystemAnnouncementDTOS = Arrays.asList(bizSdmsSystemAnnouncementDTO1);
        when(mockBizSdmsSystemAnnouncementManager.getList(new BizSdmsSystemAnnouncementDTO())).thenReturn(bizSdmsSystemAnnouncementDTOS);

        // Run the test
        final List<BizSdmsSystemAnnouncementDTO> result = bizSdmsSystemAnnouncementServiceImplUnderTest.see(bizSdmsSystemAnnouncement);

        List<BizSdmsSystemAnnouncementDTO> result1=new ArrayList<>();
        // Verify the results
        assertEquals(result1, result);
    }

    @Test
    public void testSee_IBizSdmsSystemAnnouncementManagerReturnsNoItems() {
        // Setup
        final BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement = new BizSdmsSystemAnnouncementDTO();
        bizSdmsSystemAnnouncement.setId(0L);
        bizSdmsSystemAnnouncement.setCategory("category");
        bizSdmsSystemAnnouncement.setPriority("priority");
        bizSdmsSystemAnnouncement.setTitle("title");
        bizSdmsSystemAnnouncement.setDetail("detail");
        bizSdmsSystemAnnouncement.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncement.setGeoCode("geoCode");
        bizSdmsSystemAnnouncement.setStatus("status");
        bizSdmsSystemAnnouncement.setDelFlag(0);
        bizSdmsSystemAnnouncement.setCreateDateStart("createDateStart");

        when(mockBizSdmsSystemAnnouncementManager.getList(new BizSdmsSystemAnnouncementDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizSdmsSystemAnnouncementDTO> result = bizSdmsSystemAnnouncementServiceImplUnderTest.see(bizSdmsSystemAnnouncement);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
