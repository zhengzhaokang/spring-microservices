package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.ViewConditionValue;
import com.microservices.otmp.system.domain.entity.ViewConditionValueDO;
import com.microservices.otmp.system.mapper.ViewConditionValueMapper;
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
public class ViewConditionValueManagerImplTest {

    @Mock
    private ViewConditionValueMapper mockViewConditionValueMapper;

    @InjectMocks
    private ViewConditionValueManagerImpl viewConditionValueManagerImplUnderTest;

    @Test
    public void testSelectViewConditionValueById() {
        // Setup
        // Configure ViewConditionValueMapper.selectViewConditionValueById(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        when(mockViewConditionValueMapper.selectViewConditionValueById(0)).thenReturn(viewConditionValueDO);

        // Run the test
        final ViewConditionValueDO result = viewConditionValueManagerImplUnderTest.selectViewConditionValueById(0);

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

        // Configure ViewConditionValueMapper.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueMapper.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Run the test
        final List<ViewConditionValueDO> result = viewConditionValueManagerImplUnderTest.selectViewConditionValueList(viewConditionValue);

        // Verify the results
    }

    @Test
    public void testSelectViewConditionValueList_ViewConditionValueMapperReturnsNoItems() {
        // Setup
        final ViewConditionValue viewConditionValue = new ViewConditionValue();
        viewConditionValue.setId(0);
        viewConditionValue.setFieldId(0);
        viewConditionValue.setCondition("condition");
        viewConditionValue.setConditionValue("conditionValue");

        when(mockViewConditionValueMapper.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewConditionValueDO> result = viewConditionValueManagerImplUnderTest.selectViewConditionValueList(viewConditionValue);

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

        when(mockViewConditionValueMapper.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);

        // Run the test
        final int result = viewConditionValueManagerImplUnderTest.insertViewConditionValue(viewConditionValue);

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

        when(mockViewConditionValueMapper.updateViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);

        // Run the test
        final int result = viewConditionValueManagerImplUnderTest.updateViewConditionValue(viewConditionValue);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewConditionValueById() {
        // Setup
        when(mockViewConditionValueMapper.deleteViewConditionValueById(0)).thenReturn(0);

        // Run the test
        final int result = viewConditionValueManagerImplUnderTest.deleteViewConditionValueById(0);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewConditionValueByFieldIdAndCondition() {
        // Setup
        when(mockViewConditionValueMapper.deleteViewConditionValueByFieldIdAndCondition(0, "condition")).thenReturn(0);

        // Run the test
        final int result = viewConditionValueManagerImplUnderTest.deleteViewConditionValueByFieldIdAndCondition(0, "condition");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewConditionValueByIds() {
        // Setup
        when(mockViewConditionValueMapper.deleteViewConditionValueByIds(any(Integer[].class))).thenReturn(0);

        // Run the test
        final int result = viewConditionValueManagerImplUnderTest.deleteViewConditionValueByIds(new Integer[]{0});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewConditionGroup() {
        // Setup
        when(mockViewConditionValueMapper.deleteViewConditionGroup(0)).thenReturn(0);

        // Run the test
        final int result = viewConditionValueManagerImplUnderTest.deleteViewConditionGroup(0);

        // Verify the results
        assertEquals(0, result);
    }
}
