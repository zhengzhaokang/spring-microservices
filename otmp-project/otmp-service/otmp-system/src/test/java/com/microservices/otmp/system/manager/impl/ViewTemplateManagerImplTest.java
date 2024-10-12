package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.ViewTemplate;
import com.microservices.otmp.system.domain.entity.ViewTemplateDO;
import com.microservices.otmp.system.mapper.ViewTemplateMapper;
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
public class ViewTemplateManagerImplTest {

    @Mock
    private ViewTemplateMapper mockViewTemplateMapper;

    @InjectMocks
    private ViewTemplateManagerImpl viewTemplateManagerImplUnderTest;

    @Test
    public void testSelectViewTemplateById() {
        // Setup
        final ViewTemplateDO expectedResult = new ViewTemplateDO();
        expectedResult.setPageKey("pageKey");
        expectedResult.setViewType("viewType");
        expectedResult.setDivKey("divKey");
        expectedResult.setType(0);
        expectedResult.setParentId(0);
        expectedResult.setIsDefault(0);
        expectedResult.setFieldJson("fieldJson");
        expectedResult.setId(0);
        expectedResult.setFieldId(0);
        expectedResult.setName("name");

        // Configure ViewTemplateMapper.selectViewTemplateById(...).
        final ViewTemplateDO viewTemplateDO = new ViewTemplateDO();
        viewTemplateDO.setPageKey("pageKey");
        viewTemplateDO.setViewType("viewType");
        viewTemplateDO.setDivKey("divKey");
        viewTemplateDO.setType(0);
        viewTemplateDO.setParentId(0);
        viewTemplateDO.setIsDefault(0);
        viewTemplateDO.setFieldJson("fieldJson");
        viewTemplateDO.setId(0);
        viewTemplateDO.setFieldId(0);
        viewTemplateDO.setName("name");
        when(mockViewTemplateMapper.selectViewTemplateById(0)).thenReturn(viewTemplateDO);

        // Run the test
        final ViewTemplateDO result = viewTemplateManagerImplUnderTest.selectViewTemplateById(0);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectViewTemplateList() {
        // Setup
        final ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("pageKey");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("fieldJson");
        viewTemplate.setId(0);
        viewTemplate.setFieldId(0);
        viewTemplate.setName("name");

        final ViewTemplateDO viewTemplateDO = new ViewTemplateDO();
        viewTemplateDO.setPageKey("pageKey");
        viewTemplateDO.setViewType("viewType");
        viewTemplateDO.setDivKey("divKey");
        viewTemplateDO.setType(0);
        viewTemplateDO.setParentId(0);
        viewTemplateDO.setIsDefault(0);
        viewTemplateDO.setFieldJson("fieldJson");
        viewTemplateDO.setId(0);
        viewTemplateDO.setFieldId(0);
        viewTemplateDO.setName("name");
        final List<ViewTemplateDO> expectedResult = Arrays.asList(viewTemplateDO);

        // Configure ViewTemplateMapper.selectViewTemplateList(...).
        final ViewTemplateDO viewTemplateDO1 = new ViewTemplateDO();
        viewTemplateDO1.setPageKey("pageKey");
        viewTemplateDO1.setViewType("viewType");
        viewTemplateDO1.setDivKey("divKey");
        viewTemplateDO1.setType(0);
        viewTemplateDO1.setParentId(0);
        viewTemplateDO1.setIsDefault(0);
        viewTemplateDO1.setFieldJson("fieldJson");
        viewTemplateDO1.setId(0);
        viewTemplateDO1.setFieldId(0);
        viewTemplateDO1.setName("name");
        final List<ViewTemplateDO> viewTemplateDOS = Arrays.asList(viewTemplateDO1);
        when(mockViewTemplateMapper.selectViewTemplateList(new ViewTemplate())).thenReturn(viewTemplateDOS);

        // Run the test
        final List<ViewTemplateDO> result = viewTemplateManagerImplUnderTest.selectViewTemplateList(viewTemplate);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectViewTemplateList_ViewTemplateMapperReturnsNoItems() {
        // Setup
        final ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("pageKey");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("fieldJson");
        viewTemplate.setId(0);
        viewTemplate.setFieldId(0);
        viewTemplate.setName("name");

        when(mockViewTemplateMapper.selectViewTemplateList(new ViewTemplate())).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewTemplateDO> result = viewTemplateManagerImplUnderTest.selectViewTemplateList(viewTemplate);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertViewTemplate() {
        // Setup
        final ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("pageKey");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("fieldJson");
        viewTemplate.setId(0);
        viewTemplate.setFieldId(0);
        viewTemplate.setName("name");

        when(mockViewTemplateMapper.insertViewTemplate(new ViewTemplate())).thenReturn(0);

        // Run the test
        final int result = viewTemplateManagerImplUnderTest.insertViewTemplate(viewTemplate);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateViewTemplate() {
        // Setup
        final ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("pageKey");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("fieldJson");
        viewTemplate.setId(0);
        viewTemplate.setFieldId(0);
        viewTemplate.setName("name");

        when(mockViewTemplateMapper.updateViewTemplate(new ViewTemplate())).thenReturn(0);

        // Run the test
        final int result = viewTemplateManagerImplUnderTest.updateViewTemplate(viewTemplate);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewTemplateById() {
        // Setup
        when(mockViewTemplateMapper.deleteViewTemplateById(0)).thenReturn(0);

        // Run the test
        final int result = viewTemplateManagerImplUnderTest.deleteViewTemplateById(0);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewTemplateByIds() {
        // Setup
        when(mockViewTemplateMapper.deleteViewTemplateByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = viewTemplateManagerImplUnderTest.deleteViewTemplateByIds(new String[]{"value"});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testTemplateViewList() {
        // Setup
        final ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("pageKey");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("fieldJson");
        viewTemplate.setId(0);
        viewTemplate.setFieldId(0);
        viewTemplate.setName("name");

        final ViewTemplateDO viewTemplateDO = new ViewTemplateDO();
        viewTemplateDO.setPageKey("pageKey");
        viewTemplateDO.setViewType("viewType");
        viewTemplateDO.setDivKey("divKey");
        viewTemplateDO.setType(0);
        viewTemplateDO.setParentId(0);
        viewTemplateDO.setIsDefault(0);
        viewTemplateDO.setFieldJson("fieldJson");
        viewTemplateDO.setId(0);
        viewTemplateDO.setFieldId(0);
        viewTemplateDO.setName("name");
        final List<ViewTemplateDO> expectedResult = Arrays.asList(viewTemplateDO);

        // Configure ViewTemplateMapper.templateViewList(...).
        final ViewTemplateDO viewTemplateDO1 = new ViewTemplateDO();
        viewTemplateDO1.setPageKey("pageKey");
        viewTemplateDO1.setViewType("viewType");
        viewTemplateDO1.setDivKey("divKey");
        viewTemplateDO1.setType(0);
        viewTemplateDO1.setParentId(0);
        viewTemplateDO1.setIsDefault(0);
        viewTemplateDO1.setFieldJson("fieldJson");
        viewTemplateDO1.setId(0);
        viewTemplateDO1.setFieldId(0);
        viewTemplateDO1.setName("name");
        final List<ViewTemplateDO> viewTemplateDOS = Arrays.asList(viewTemplateDO1);
        when(mockViewTemplateMapper.templateViewList(new ViewTemplate())).thenReturn(viewTemplateDOS);

        // Run the test
        final List<ViewTemplateDO> result = viewTemplateManagerImplUnderTest.templateViewList(viewTemplate);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testTemplateViewList_ViewTemplateMapperReturnsNoItems() {
        // Setup
        final ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("pageKey");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("fieldJson");
        viewTemplate.setId(0);
        viewTemplate.setFieldId(0);
        viewTemplate.setName("name");

        when(mockViewTemplateMapper.templateViewList(new ViewTemplate())).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewTemplateDO> result = viewTemplateManagerImplUnderTest.templateViewList(viewTemplate);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDeleteTemplateGroup() {
        // Setup
        when(mockViewTemplateMapper.deleteTemplateGroup(0)).thenReturn(0);

        // Run the test
        final int result = viewTemplateManagerImplUnderTest.deleteTemplateGroup(0);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testFakeDeleteTemplateGroup() {
        // Setup
        when(mockViewTemplateMapper.fakeDeleteTemplateGroup(0)).thenReturn(0);

        // Run the test
        final int result = viewTemplateManagerImplUnderTest.fakeDeleteTemplateGroup(0);

        // Verify the results
        assertEquals(0, result);
    }
}
