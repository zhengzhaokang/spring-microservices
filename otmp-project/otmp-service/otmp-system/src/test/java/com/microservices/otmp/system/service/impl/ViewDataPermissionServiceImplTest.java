package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.system.domain.ViewDataPermission;
import com.microservices.otmp.system.domain.entity.ViewDataPermissionDO;
import com.microservices.otmp.system.manager.ViewDataPermissionManager;
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

@RunWith(MockitoJUnitRunner.class)
public class ViewDataPermissionServiceImplTest {

    @Mock
    private ViewDataPermissionManager mockViewDataPermissionManager;

    @InjectMocks
    private ViewDataPermissionServiceImpl viewDataPermissionServiceImplUnderTest;

    @Test
    public void testSelectViewDataPermissionById() {
        // Setup
        // Configure ViewDataPermissionManager.selectViewDataPermissionById(...).
        final ViewDataPermissionDO viewDataPermissionDO = new ViewDataPermissionDO();
        viewDataPermissionDO.setId(0);
        viewDataPermissionDO.setItCode("itCode");
        viewDataPermissionDO.setPageKey("pageKey");
        viewDataPermissionDO.setViewType("viewType");
        viewDataPermissionDO.setGeoCode("geoCode");
        viewDataPermissionDO.setRegionCode("regionCode");
        viewDataPermissionDO.setBusinessGroup("businessGroup");
        viewDataPermissionDO.setPaymentMode("paymentMode");
        when(mockViewDataPermissionManager.selectViewDataPermissionById(0)).thenReturn(viewDataPermissionDO);

        // Run the test
        final ViewDataPermission result = viewDataPermissionServiceImplUnderTest.selectViewDataPermissionById(0);

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

        // Configure ViewDataPermissionManager.selectViewDataPermissionList(...).
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
        when(mockViewDataPermissionManager.selectViewDataPermissionList(any(ViewDataPermission.class))).thenReturn(viewDataPermissionDOS);

        // Run the test
        final List<ViewDataPermission> result = viewDataPermissionServiceImplUnderTest.selectViewDataPermissionList(viewDataPermission);

        // Verify the results
    }

    @Test
    public void testSelectViewDataPermissionList_ViewDataPermissionManagerReturnsNoItems() {
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

        when(mockViewDataPermissionManager.selectViewDataPermissionList(any(ViewDataPermission.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewDataPermission> result = viewDataPermissionServiceImplUnderTest.selectViewDataPermissionList(viewDataPermission);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertViewDataPermission() {
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

        when(mockViewDataPermissionManager.insertViewDataPermission(any(ViewDataPermissionDO.class))).thenReturn(0);

        // Run the test
        final int result = viewDataPermissionServiceImplUnderTest.insertViewDataPermission(viewDataPermission);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateViewDataPermission() {
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

        when(mockViewDataPermissionManager.updateViewDataPermission(any(ViewDataPermissionDO.class))).thenReturn(0);

        // Run the test
        final int result = viewDataPermissionServiceImplUnderTest.updateViewDataPermission(viewDataPermission);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewDataPermissionByIds() {
        // Setup
        when(mockViewDataPermissionManager.deleteViewDataPermissionByIds(any(Integer[].class))).thenReturn(0);

        // Run the test
        final int result = viewDataPermissionServiceImplUnderTest.deleteViewDataPermissionByIds(new Integer[]{0});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewDataPermissionById() {
        // Setup
        when(mockViewDataPermissionManager.deleteViewDataPermissionById(0)).thenReturn(0);

        // Run the test
        final int result = viewDataPermissionServiceImplUnderTest.deleteViewDataPermissionById(0);

        // Verify the results
        assertEquals(0, result);
    }
}
