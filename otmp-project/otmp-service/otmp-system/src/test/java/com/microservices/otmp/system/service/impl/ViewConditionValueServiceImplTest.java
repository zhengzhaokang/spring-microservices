package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.system.domain.ViewConditionValue;
import com.microservices.otmp.system.domain.entity.ViewConditionValueDO;
import com.microservices.otmp.system.manager.ViewConditionValueManager;
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
public class ViewConditionValueServiceImplTest {

    @Mock
    private ViewConditionValueManager mockViewConditionValueManager;

    @InjectMocks
    private ViewConditionValueServiceImpl viewConditionValueServiceImplUnderTest;

    @Test
    public void testSelectViewConditionValueById() {
        // Setup
        // Configure ViewConditionValueManager.selectViewConditionValueById(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        when(mockViewConditionValueManager.selectViewConditionValueById(0)).thenReturn(viewConditionValueDO);

        // Run the test
        final ViewConditionValueDO result = viewConditionValueServiceImplUnderTest.selectViewConditionValueById(0);

        // Verify the results
    }

    @Test
    public void testSelectViewConditionValueList() {
        // Setup
        final ViewConditionValue viewConditionValue = new ViewConditionValue();
        viewConditionValue.setId(0);
        viewConditionValue.setFieldId(0);
        viewConditionValue.setCondition("condition");
        viewConditionValue.setConditionValue("conditionValue");

        // Configure ViewConditionValueManager.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueManager.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Run the test
        final List<ViewConditionValueDO> result = viewConditionValueServiceImplUnderTest.selectViewConditionValueList(viewConditionValue);

        // Verify the results
    }

    @Test
    public void testSelectViewConditionValueList_ViewConditionValueManagerReturnsNoItems() {
        // Setup
        final ViewConditionValue viewConditionValue = new ViewConditionValue();
        viewConditionValue.setId(0);
        viewConditionValue.setFieldId(0);
        viewConditionValue.setCondition("condition");
        viewConditionValue.setConditionValue("conditionValue");

        when(mockViewConditionValueManager.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewConditionValueDO> result = viewConditionValueServiceImplUnderTest.selectViewConditionValueList(viewConditionValue);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertViewConditionValue() {
        // Setup
        final ViewConditionValue viewConditionValue = new ViewConditionValue();
        viewConditionValue.setId(0);
        viewConditionValue.setFieldId(0);
        viewConditionValue.setCondition("condition");
        viewConditionValue.setConditionValue("conditionValue");

        when(mockViewConditionValueManager.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);

        // Run the test
        final int result = viewConditionValueServiceImplUnderTest.insertViewConditionValue(viewConditionValue);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateViewConditionValue() {
        // Setup
        final ViewConditionValue viewConditionValue = new ViewConditionValue();
        viewConditionValue.setId(0);
        viewConditionValue.setFieldId(0);
        viewConditionValue.setCondition("condition");
        viewConditionValue.setConditionValue("conditionValue");

        when(mockViewConditionValueManager.updateViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);

        // Run the test
        final int result = viewConditionValueServiceImplUnderTest.updateViewConditionValue(viewConditionValue);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewConditionValueByIds() {
        // Setup
        when(mockViewConditionValueManager.deleteViewConditionValueByIds(any(Integer[].class))).thenReturn(0);

        // Run the test
        final int result = viewConditionValueServiceImplUnderTest.deleteViewConditionValueByIds(new Integer[]{0});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewConditionValueById() {
        // Setup
        when(mockViewConditionValueManager.deleteViewConditionValueById(0)).thenReturn(0);

        // Run the test
        final int result = viewConditionValueServiceImplUnderTest.deleteViewConditionValueById(0);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewConditionValueByFieldIdAndCondition() {
        // Setup
        when(mockViewConditionValueManager.deleteViewConditionValueByFieldIdAndCondition(0, "condition")).thenReturn(0);

        // Run the test
        final int result = viewConditionValueServiceImplUnderTest.deleteViewConditionValueByFieldIdAndCondition(0, "condition");

        // Verify the results
        assertEquals(0, result);
    }
}
