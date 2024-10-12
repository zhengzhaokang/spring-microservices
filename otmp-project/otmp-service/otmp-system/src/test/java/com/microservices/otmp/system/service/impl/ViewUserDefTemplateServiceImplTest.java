package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.system.domain.ViewUserDefTemplate;
import com.microservices.otmp.system.domain.entity.ViewUserDefTemplateDO;
import com.microservices.otmp.system.manager.ViewUserDefTemplateManager;
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
public class ViewUserDefTemplateServiceImplTest {

    @Mock
    private ViewUserDefTemplateManager mockViewUserDefTemplateManager;

    @InjectMocks
    private ViewUserDefTemplateServiceImpl viewUserDefTemplateServiceImplUnderTest;

    @Test
    public void testSelectViewUserDefTemplateById() {
        // Setup
        // Configure ViewUserDefTemplateManager.selectViewUserDefTemplateById(...).
        final ViewUserDefTemplateDO viewUserDefTemplateDO = new ViewUserDefTemplateDO();
        viewUserDefTemplateDO.setItCode("itCode");
        viewUserDefTemplateDO.setTemplateId(0);
        when(mockViewUserDefTemplateManager.selectViewUserDefTemplateById("updateBy")).thenReturn(viewUserDefTemplateDO);

        // Run the test
        final ViewUserDefTemplate result = viewUserDefTemplateServiceImplUnderTest.selectViewUserDefTemplateById("updateBy");

        // Verify the results
    }

    @Test
    public void testSelectViewUserDefTemplateList() {
        // Setup
        final ViewUserDefTemplate viewUserDefTemplate = new ViewUserDefTemplate();
        viewUserDefTemplate.setItCode("itCode");
        viewUserDefTemplate.setTemplateId(0);

        // Configure ViewUserDefTemplateManager.selectViewUserDefTemplateList(...).
        final ViewUserDefTemplateDO viewUserDefTemplateDO = new ViewUserDefTemplateDO();
        viewUserDefTemplateDO.setItCode("itCode");
        viewUserDefTemplateDO.setTemplateId(0);
        final List<ViewUserDefTemplateDO> viewUserDefTemplateDOS = Arrays.asList(viewUserDefTemplateDO);
        when(mockViewUserDefTemplateManager.selectViewUserDefTemplateList(any(ViewUserDefTemplate.class))).thenReturn(viewUserDefTemplateDOS);

        // Run the test
        final List<ViewUserDefTemplate> result = viewUserDefTemplateServiceImplUnderTest.selectViewUserDefTemplateList(viewUserDefTemplate);

        // Verify the results
    }

    @Test
    public void testSelectViewUserDefTemplateList_ViewUserDefTemplateManagerReturnsNoItems() {
        // Setup
        final ViewUserDefTemplate viewUserDefTemplate = new ViewUserDefTemplate();
        viewUserDefTemplate.setItCode("itCode");
        viewUserDefTemplate.setTemplateId(0);

        when(mockViewUserDefTemplateManager.selectViewUserDefTemplateList(any(ViewUserDefTemplate.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewUserDefTemplate> result = viewUserDefTemplateServiceImplUnderTest.selectViewUserDefTemplateList(viewUserDefTemplate);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertViewUserDefTemplate() {
        // Setup
        final ViewUserDefTemplate viewUserDefTemplate = new ViewUserDefTemplate();
        viewUserDefTemplate.setItCode("itCode");
        viewUserDefTemplate.setTemplateId(0);

        when(mockViewUserDefTemplateManager.insertViewUserDefTemplate(any(ViewUserDefTemplateDO.class))).thenReturn(0);

        // Run the test
        final int result = viewUserDefTemplateServiceImplUnderTest.insertViewUserDefTemplate(viewUserDefTemplate);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateViewUserDefTemplate() {
        // Setup
        final ViewUserDefTemplate viewUserDefTemplate = new ViewUserDefTemplate();
        viewUserDefTemplate.setItCode("itCode");
        viewUserDefTemplate.setTemplateId(0);

        when(mockViewUserDefTemplateManager.updateViewUserDefTemplate(any(ViewUserDefTemplateDO.class))).thenReturn(0);

        // Run the test
        final int result = viewUserDefTemplateServiceImplUnderTest.updateViewUserDefTemplate(viewUserDefTemplate);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewUserDefTemplateByIds() {
        // Setup
        when(mockViewUserDefTemplateManager.deleteViewUserDefTemplateByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = viewUserDefTemplateServiceImplUnderTest.deleteViewUserDefTemplateByIds("ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewUserDefTemplateById() {
        // Setup
        when(mockViewUserDefTemplateManager.deleteViewUserDefTemplateById("updateBy")).thenReturn(0);

        // Run the test
        final int result = viewUserDefTemplateServiceImplUnderTest.deleteViewUserDefTemplateById("updateBy");

        // Verify the results
        assertEquals(0, result);
    }
}
