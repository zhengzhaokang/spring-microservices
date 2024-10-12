package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.ViewUserDefTemplate;
import com.microservices.otmp.system.domain.entity.ViewUserDefTemplateDO;
import com.microservices.otmp.system.mapper.ViewUserDefTemplateMapper;
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
public class ViewUserDefTemplateManagerImplTest {

    @Mock
    private ViewUserDefTemplateMapper mockViewUserDefTemplateMapper;

    @InjectMocks
    private ViewUserDefTemplateManagerImpl viewUserDefTemplateManagerImplUnderTest;

    @Test
    public void testSelectViewUserDefTemplateById() {
        // Setup
        // Configure ViewUserDefTemplateMapper.selectViewUserDefTemplateById(...).
        final ViewUserDefTemplateDO viewUserDefTemplateDO = new ViewUserDefTemplateDO();
        viewUserDefTemplateDO.setItCode("itCode");
        viewUserDefTemplateDO.setTemplateId(0);
        when(mockViewUserDefTemplateMapper.selectViewUserDefTemplateById("updateBy")).thenReturn(viewUserDefTemplateDO);

        // Run the test
        final ViewUserDefTemplateDO result = viewUserDefTemplateManagerImplUnderTest.selectViewUserDefTemplateById("updateBy");

        // Verify the results
    }

    @Test
    public void testSelectViewUserDefTemplateList() {
        // Setup
        final ViewUserDefTemplate viewUserDefTemplate = new ViewUserDefTemplate();
        viewUserDefTemplate.setItCode("itCode");
        viewUserDefTemplate.setTemplateId(0);

        // Configure ViewUserDefTemplateMapper.selectViewUserDefTemplateList(...).
        final ViewUserDefTemplateDO viewUserDefTemplateDO = new ViewUserDefTemplateDO();
        viewUserDefTemplateDO.setItCode("itCode");
        viewUserDefTemplateDO.setTemplateId(0);
        final List<ViewUserDefTemplateDO> viewUserDefTemplateDOS = Arrays.asList(viewUserDefTemplateDO);
        when(mockViewUserDefTemplateMapper.selectViewUserDefTemplateList(any(ViewUserDefTemplate.class))).thenReturn(viewUserDefTemplateDOS);

        // Run the test
        final List<ViewUserDefTemplateDO> result = viewUserDefTemplateManagerImplUnderTest.selectViewUserDefTemplateList(viewUserDefTemplate);

        // Verify the results
    }

    @Test
    public void testSelectViewUserDefTemplateList_ViewUserDefTemplateMapperReturnsNoItems() {
        // Setup
        final ViewUserDefTemplate viewUserDefTemplate = new ViewUserDefTemplate();
        viewUserDefTemplate.setItCode("itCode");
        viewUserDefTemplate.setTemplateId(0);

        when(mockViewUserDefTemplateMapper.selectViewUserDefTemplateList(any(ViewUserDefTemplate.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewUserDefTemplateDO> result = viewUserDefTemplateManagerImplUnderTest.selectViewUserDefTemplateList(viewUserDefTemplate);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertViewUserDefTemplate() {
        // Setup
        final ViewUserDefTemplateDO viewUserDefTemplate = new ViewUserDefTemplateDO();
        viewUserDefTemplate.setItCode("itCode");
        viewUserDefTemplate.setTemplateId(0);

        when(mockViewUserDefTemplateMapper.insertViewUserDefTemplate(any(ViewUserDefTemplateDO.class))).thenReturn(0);

        // Run the test
        final int result = viewUserDefTemplateManagerImplUnderTest.insertViewUserDefTemplate(viewUserDefTemplate);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateViewUserDefTemplate() {
        // Setup
        final ViewUserDefTemplateDO viewUserDefTemplate = new ViewUserDefTemplateDO();
        viewUserDefTemplate.setItCode("itCode");
        viewUserDefTemplate.setTemplateId(0);

        when(mockViewUserDefTemplateMapper.updateViewUserDefTemplate(any(ViewUserDefTemplateDO.class))).thenReturn(0);

        // Run the test
        final int result = viewUserDefTemplateManagerImplUnderTest.updateViewUserDefTemplate(viewUserDefTemplate);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewUserDefTemplateById() {
        // Setup
        when(mockViewUserDefTemplateMapper.deleteViewUserDefTemplateById("updateBy")).thenReturn(0);

        // Run the test
        final int result = viewUserDefTemplateManagerImplUnderTest.deleteViewUserDefTemplateById("updateBy");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewUserDefTemplateByIds() {
        // Setup
        when(mockViewUserDefTemplateMapper.deleteViewUserDefTemplateByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = viewUserDefTemplateManagerImplUnderTest.deleteViewUserDefTemplateByIds(new String[]{"value"});

        // Verify the results
        assertEquals(0, result);
    }
}
