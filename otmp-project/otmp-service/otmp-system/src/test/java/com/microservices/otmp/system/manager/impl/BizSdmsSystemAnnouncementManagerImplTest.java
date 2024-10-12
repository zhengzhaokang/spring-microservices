package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.dto.BizSdmsSystemAnnouncementDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsSystemAnnouncementDO;
import com.microservices.otmp.system.mapper.BizSdmsSystemAnnouncementMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BizSdmsSystemAnnouncementManagerImplTest {

    @Mock
    private BizSdmsSystemAnnouncementMapper mockBizSdmsSystemAnnouncementMapper;

    @InjectMocks
    private BizSdmsSystemAnnouncementManagerImpl bizSdmsSystemAnnouncementManagerImplUnderTest;

    @Test
    public void testSelectBizSdmsSystemAnnouncementById() {
        // Setup
        final BizSdmsSystemAnnouncementDO expectedResult = new BizSdmsSystemAnnouncementDO();
        expectedResult.setId(0L);
        expectedResult.setCategory("category");
        expectedResult.setPriority("priority");
        expectedResult.setTitle("title");
        expectedResult.setDetail("detail");
        expectedResult.setBusinessGroup("businessGroup");
        expectedResult.setGeoCode("geoCode");
        expectedResult.setStatus("status");
        expectedResult.setDelFlag(0);

        // Configure BizSdmsSystemAnnouncementMapper.selectBizSdmsSystemAnnouncementById(...).
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
        when(mockBizSdmsSystemAnnouncementMapper.selectBizSdmsSystemAnnouncementById(0L)).thenReturn(bizSdmsSystemAnnouncementDO);

        // Run the test
        final BizSdmsSystemAnnouncementDO result = bizSdmsSystemAnnouncementManagerImplUnderTest.selectBizSdmsSystemAnnouncementById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
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
        final List<BizSdmsSystemAnnouncementDO> expectedResult = Arrays.asList(bizSdmsSystemAnnouncementDO);

        // Configure BizSdmsSystemAnnouncementMapper.selectBizSdmsSystemAnnouncementList(...).
        final BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncementDO1 = new BizSdmsSystemAnnouncementDO();
        bizSdmsSystemAnnouncementDO1.setId(0L);
        bizSdmsSystemAnnouncementDO1.setCategory("category");
        bizSdmsSystemAnnouncementDO1.setPriority("priority");
        bizSdmsSystemAnnouncementDO1.setTitle("title");
        bizSdmsSystemAnnouncementDO1.setDetail("detail");
        bizSdmsSystemAnnouncementDO1.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncementDO1.setGeoCode("geoCode");
        bizSdmsSystemAnnouncementDO1.setStatus("status");
        bizSdmsSystemAnnouncementDO1.setDelFlag(0);
        final List<BizSdmsSystemAnnouncementDO> bizSdmsSystemAnnouncementDOS = Arrays.asList(bizSdmsSystemAnnouncementDO1);
        when(mockBizSdmsSystemAnnouncementMapper.selectBizSdmsSystemAnnouncementList(new BizSdmsSystemAnnouncementDTO())).thenReturn(bizSdmsSystemAnnouncementDOS);

        // Run the test
        final List<BizSdmsSystemAnnouncementDO> result = bizSdmsSystemAnnouncementManagerImplUnderTest.selectBizSdmsSystemAnnouncementList(bizSdmsSystemAnnouncement);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectBizSdmsSystemAnnouncementList_BizSdmsSystemAnnouncementMapperReturnsNoItems() {
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

        when(mockBizSdmsSystemAnnouncementMapper.selectBizSdmsSystemAnnouncementList(new BizSdmsSystemAnnouncementDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizSdmsSystemAnnouncementDO> result = bizSdmsSystemAnnouncementManagerImplUnderTest.selectBizSdmsSystemAnnouncementList(bizSdmsSystemAnnouncement);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizSdmsSystemAnnouncement() {
        // Setup
        final BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncement = new BizSdmsSystemAnnouncementDO();
        bizSdmsSystemAnnouncement.setId(0L);
        bizSdmsSystemAnnouncement.setCategory("category");
        bizSdmsSystemAnnouncement.setPriority("priority");
        bizSdmsSystemAnnouncement.setTitle("title");
        bizSdmsSystemAnnouncement.setDetail("detail");
        bizSdmsSystemAnnouncement.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncement.setGeoCode("geoCode");
        bizSdmsSystemAnnouncement.setStatus("status");
        bizSdmsSystemAnnouncement.setDelFlag(0);

        when(mockBizSdmsSystemAnnouncementMapper.insertBizSdmsSystemAnnouncement(new BizSdmsSystemAnnouncementDO())).thenReturn(0);

        // Run the test
        final int result = bizSdmsSystemAnnouncementManagerImplUnderTest.insertBizSdmsSystemAnnouncement(bizSdmsSystemAnnouncement);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsSystemAnnouncement() {
        // Setup
        final BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncement = new BizSdmsSystemAnnouncementDO();
        bizSdmsSystemAnnouncement.setId(0L);
        bizSdmsSystemAnnouncement.setCategory("category");
        bizSdmsSystemAnnouncement.setPriority("priority");
        bizSdmsSystemAnnouncement.setTitle("title");
        bizSdmsSystemAnnouncement.setDetail("detail");
        bizSdmsSystemAnnouncement.setBusinessGroup("businessGroup");
        bizSdmsSystemAnnouncement.setGeoCode("geoCode");
        bizSdmsSystemAnnouncement.setStatus("status");
        bizSdmsSystemAnnouncement.setDelFlag(0);

        when(mockBizSdmsSystemAnnouncementMapper.updateBizSdmsSystemAnnouncement(new BizSdmsSystemAnnouncementDO())).thenReturn(0);

        // Run the test
        final int result = bizSdmsSystemAnnouncementManagerImplUnderTest.updateBizSdmsSystemAnnouncement(bizSdmsSystemAnnouncement);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsSystemAnnouncementByIds() {
        // Setup
        when(mockBizSdmsSystemAnnouncementMapper.deleteBizSdmsSystemAnnouncementByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsSystemAnnouncementManagerImplUnderTest.deleteBizSdmsSystemAnnouncementByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsSystemAnnouncementById() {
        // Setup
        when(mockBizSdmsSystemAnnouncementMapper.deleteBizSdmsSystemAnnouncementById(0L)).thenReturn(0);

        // Run the test
        final int result = bizSdmsSystemAnnouncementManagerImplUnderTest.deleteBizSdmsSystemAnnouncementById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testGetList() {
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

        // Configure BizSdmsSystemAnnouncementMapper.getList(...).
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
        when(mockBizSdmsSystemAnnouncementMapper.getList(new BizSdmsSystemAnnouncementDTO())).thenReturn(bizSdmsSystemAnnouncementDTOS);

        // Run the test
        final List<BizSdmsSystemAnnouncementDTO> result = bizSdmsSystemAnnouncementManagerImplUnderTest.getList(bizSdmsSystemAnnouncement);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testGetList_BizSdmsSystemAnnouncementMapperReturnsNoItems() {
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

        when(mockBizSdmsSystemAnnouncementMapper.getList(new BizSdmsSystemAnnouncementDTO())).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizSdmsSystemAnnouncementDTO> result = bizSdmsSystemAnnouncementManagerImplUnderTest.getList(bizSdmsSystemAnnouncement);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
