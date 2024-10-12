package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.ViewDataPermission;
import com.microservices.otmp.system.domain.entity.ViewDataPermissionDO;
import com.microservices.otmp.system.mapper.ViewDataPermissionMapper;
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
public class ViewDataPermissionManagerImplTest {

    @Mock
    private ViewDataPermissionMapper mockViewDataPermissionMapper;

    @InjectMocks
    private ViewDataPermissionManagerImpl viewDataPermissionManagerImplUnderTest;

    @Test
    public void testSelectViewDataPermissionById() {
        // Setup
        // Configure ViewDataPermissionMapper.selectViewDataPermissionById(...).
        final ViewDataPermissionDO viewDataPermissionDO = new ViewDataPermissionDO();
        viewDataPermissionDO.setId(0);
        viewDataPermissionDO.setItCode("itCode");
        viewDataPermissionDO.setPageKey("pageKey");
        viewDataPermissionDO.setViewType("viewType");
        viewDataPermissionDO.setGeoCode("geoCode");
        viewDataPermissionDO.setRegionCode("regionCode");
        viewDataPermissionDO.setBusinessGroup("businessGroup");
        viewDataPermissionDO.setPaymentMode("paymentMode");
        when(mockViewDataPermissionMapper.selectViewDataPermissionById(0)).thenReturn(viewDataPermissionDO);

        // Run the test
        final ViewDataPermissionDO result = viewDataPermissionManagerImplUnderTest.selectViewDataPermissionById(0);

        // Verify the results
    }

    @Test
    public void testSelectViewDataPermissionList() {
        // Setup
        final ViewDataPermission viewDataPermission = new ViewDataPermission();
        viewDataPermission.setId(0);
        viewDataPermission.setItCode("itCode");
        viewDataPermission.setPageKey("pageKey");
        viewDataPermission.setViewType("viewType");
        viewDataPermission.setGeoCode("geoCode");
        viewDataPermission.setRegionCode("regionCode");
        viewDataPermission.setBusinessGroup("businessGroup");
        viewDataPermission.setPaymentMode("paymentMode");

        // Configure ViewDataPermissionMapper.selectViewDataPermissionList(...).
        final ViewDataPermissionDO viewDataPermissionDO = new ViewDataPermissionDO();
        viewDataPermissionDO.setId(0);
        viewDataPermissionDO.setItCode("itCode");
        viewDataPermissionDO.setPageKey("pageKey");
        viewDataPermissionDO.setViewType("viewType");
        viewDataPermissionDO.setGeoCode("geoCode");
        viewDataPermissionDO.setRegionCode("regionCode");
        viewDataPermissionDO.setBusinessGroup("businessGroup");
        viewDataPermissionDO.setPaymentMode("paymentMode");
        final List<ViewDataPermissionDO> viewDataPermissionDOS = Arrays.asList(viewDataPermissionDO);
        when(mockViewDataPermissionMapper.selectViewDataPermissionList(any(ViewDataPermission.class))).thenReturn(viewDataPermissionDOS);

        // Run the test
        final List<ViewDataPermissionDO> result = viewDataPermissionManagerImplUnderTest.selectViewDataPermissionList(viewDataPermission);

        // Verify the results
    }

    @Test
    public void testSelectViewDataPermissionList_ViewDataPermissionMapperReturnsNoItems() {
        // Setup
        final ViewDataPermission viewDataPermission = new ViewDataPermission();
        viewDataPermission.setId(0);
        viewDataPermission.setItCode("itCode");
        viewDataPermission.setPageKey("pageKey");
        viewDataPermission.setViewType("viewType");
        viewDataPermission.setGeoCode("geoCode");
        viewDataPermission.setRegionCode("regionCode");
        viewDataPermission.setBusinessGroup("businessGroup");
        viewDataPermission.setPaymentMode("paymentMode");

        when(mockViewDataPermissionMapper.selectViewDataPermissionList(any(ViewDataPermission.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewDataPermissionDO> result = viewDataPermissionManagerImplUnderTest.selectViewDataPermissionList(viewDataPermission);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertViewDataPermission() {
        // Setup
        final ViewDataPermissionDO viewDataPermission = new ViewDataPermissionDO();
        viewDataPermission.setId(0);
        viewDataPermission.setItCode("itCode");
        viewDataPermission.setPageKey("pageKey");
        viewDataPermission.setViewType("viewType");
        viewDataPermission.setGeoCode("geoCode");
        viewDataPermission.setRegionCode("regionCode");
        viewDataPermission.setBusinessGroup("businessGroup");
        viewDataPermission.setPaymentMode("paymentMode");

        when(mockViewDataPermissionMapper.insertViewDataPermission(any(ViewDataPermissionDO.class))).thenReturn(0);

        // Run the test
        final int result = viewDataPermissionManagerImplUnderTest.insertViewDataPermission(viewDataPermission);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateViewDataPermission() {
        // Setup
        final ViewDataPermissionDO viewDataPermission = new ViewDataPermissionDO();
        viewDataPermission.setId(0);
        viewDataPermission.setItCode("itCode");
        viewDataPermission.setPageKey("pageKey");
        viewDataPermission.setViewType("viewType");
        viewDataPermission.setGeoCode("geoCode");
        viewDataPermission.setRegionCode("regionCode");
        viewDataPermission.setBusinessGroup("businessGroup");
        viewDataPermission.setPaymentMode("paymentMode");

        when(mockViewDataPermissionMapper.updateViewDataPermission(any(ViewDataPermissionDO.class))).thenReturn(0);

        // Run the test
        final int result = viewDataPermissionManagerImplUnderTest.updateViewDataPermission(viewDataPermission);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewDataPermissionById() {
        // Setup
        when(mockViewDataPermissionMapper.deleteViewDataPermissionById(0)).thenReturn(0);

        // Run the test
        final int result = viewDataPermissionManagerImplUnderTest.deleteViewDataPermissionById(0);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewDataPermissionByIds() {
        // Setup
        when(mockViewDataPermissionMapper.deleteViewDataPermissionByIds(any(Integer[].class))).thenReturn(0);

        // Run the test
        final int result = viewDataPermissionManagerImplUnderTest.deleteViewDataPermissionByIds(new Integer[]{0});

        // Verify the results
        assertEquals(0, result);
    }
}
