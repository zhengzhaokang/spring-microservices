package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.ViewField;
import com.microservices.otmp.system.domain.ViewFieldInfo;
import com.microservices.otmp.system.domain.entity.ViewFieldDO;
import com.microservices.otmp.system.manager.SysDictDataManager;
import com.microservices.otmp.system.mapper.ViewFieldMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ViewFieldManagerImplTest {

    @Mock
    private ViewFieldMapper mockViewFieldMapper;
    @Mock
    private SysDictDataManager mockSelectDictDataByType;

    @InjectMocks
    private ViewFieldManagerImpl viewFieldManagerImplUnderTest;

    @Test
    public void testSelectViewFieldById() {
        // Setup
        final ViewFieldDO expectedResult = new ViewFieldDO();
        expectedResult.setId(0);
        expectedResult.setPageKey("pageKey");
        expectedResult.setViewType("viewType");
        expectedResult.setDivKey("divKey");
        expectedResult.setStatus(0);
        expectedResult.setFieldJson("fieldJson");
        expectedResult.setType(0);
        expectedResult.setViewFieldCode("viewFieldCode");
        expectedResult.setTableName("tableName");
        expectedResult.setRegion("region");

        // Configure ViewFieldMapper.selectViewFieldById(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        when(mockViewFieldMapper.selectViewFieldById(0)).thenReturn(viewFieldDO);

        // Run the test
        final ViewFieldDO result = viewFieldManagerImplUnderTest.selectViewFieldById(0);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectViewFieldList() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> expectedResult = Arrays.asList(viewFieldDO);

        // Configure ViewFieldMapper.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO1);
        when(mockViewFieldMapper.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        final List<ViewFieldDO> result = viewFieldManagerImplUnderTest.selectViewFieldList(viewField);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectViewFieldList_ViewFieldMapperReturnsNoItems() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        when(mockViewFieldMapper.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewFieldDO> result = viewFieldManagerImplUnderTest.selectViewFieldList(viewField);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectViewFieldListPrecise() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        when(mockViewFieldMapper.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Run the test
        final List<Map<String, Object>> result = viewFieldManagerImplUnderTest.selectViewFieldListPrecise(map);

        // Verify the results
    }

    @Test
    public void testSelectViewFieldListPrecise_ViewFieldMapperReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        when(mockViewFieldMapper.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Collections.emptyList());

        // Run the test
        final List<Map<String, Object>> result = viewFieldManagerImplUnderTest.selectViewFieldListPrecise(map);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertViewFieldMap() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();
        when(mockViewFieldMapper.insertViewFieldMap(new HashMap<>())).thenReturn(0);

        // Run the test
        final int result = viewFieldManagerImplUnderTest.insertViewFieldMap(viewField);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertViewField() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        when(mockViewFieldMapper.insertViewField(new ViewField())).thenReturn(0);

        // Run the test
        final int result = viewFieldManagerImplUnderTest.insertViewField(viewField);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateViewField() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        when(mockViewFieldMapper.updateViewField(new ViewField())).thenReturn(0);

        // Run the test
        final int result = viewFieldManagerImplUnderTest.updateViewField(viewField);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateViewFieldMap() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();
        when(mockViewFieldMapper.updateViewFieldMap(new HashMap<>())).thenReturn(0);

        // Run the test
        final int result = viewFieldManagerImplUnderTest.updateViewFieldMap(viewField);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewFieldById() {
        // Setup
        when(mockViewFieldMapper.deleteViewFieldById(0)).thenReturn(0);

        // Run the test
        final int result = viewFieldManagerImplUnderTest.deleteViewFieldById(0);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewFieldByIds() {
        // Setup
        when(mockViewFieldMapper.deleteViewFieldByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = viewFieldManagerImplUnderTest.deleteViewFieldByIds(new String[]{"value"});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectViewFieldInfoList() {
        // Setup
        final ViewFieldInfo viewFieldInfo = new ViewFieldInfo();
        viewFieldInfo.setAttnum(0);
        viewFieldInfo.setRelname("relname");
        viewFieldInfo.setField("field");
        viewFieldInfo.setFieldType("fieldType");
        viewFieldInfo.setFieldLabel("fieldLabel");
        viewFieldInfo.setFieldTest("fieldTest");
        viewFieldInfo.setChecked(false);

        final ViewFieldInfo viewFieldInfo1 = new ViewFieldInfo();
        viewFieldInfo1.setAttnum(0);
        viewFieldInfo1.setRelname("relname");
        viewFieldInfo1.setField("field");
        viewFieldInfo1.setFieldType("fieldType");
        viewFieldInfo1.setFieldLabel("fieldLabel");
        viewFieldInfo1.setFieldTest("fieldTest");
        viewFieldInfo1.setChecked(false);
        final List<ViewFieldInfo> expectedResult = Arrays.asList(viewFieldInfo1);

        // Configure ViewFieldMapper.selectViewFieldInfoList(...).
        final ViewFieldInfo viewFieldInfo2 = new ViewFieldInfo();
        viewFieldInfo2.setAttnum(0);
        viewFieldInfo2.setRelname("relname");
        viewFieldInfo2.setField("field");
        viewFieldInfo2.setFieldType("fieldType");
        viewFieldInfo2.setFieldLabel("fieldLabel");
        viewFieldInfo2.setFieldTest("fieldTest");
        viewFieldInfo2.setChecked(false);
        final List<ViewFieldInfo> viewFieldInfos = Arrays.asList(viewFieldInfo2);
        when(mockViewFieldMapper.selectViewFieldInfoList(new ViewFieldInfo())).thenReturn(viewFieldInfos);

        // Run the test
        final List<ViewFieldInfo> result = viewFieldManagerImplUnderTest.selectViewFieldInfoList(viewFieldInfo);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectViewFieldInfoList_ViewFieldMapperReturnsNoItems() {
        // Setup
        final ViewFieldInfo viewFieldInfo = new ViewFieldInfo();
        viewFieldInfo.setAttnum(0);
        viewFieldInfo.setRelname("relname");
        viewFieldInfo.setField("field");
        viewFieldInfo.setFieldType("fieldType");
        viewFieldInfo.setFieldLabel("fieldLabel");
        viewFieldInfo.setFieldTest("fieldTest");
        viewFieldInfo.setChecked(false);

        when(mockViewFieldMapper.selectViewFieldInfoList(new ViewFieldInfo())).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewFieldInfo> result = viewFieldManagerImplUnderTest.selectViewFieldInfoList(viewFieldInfo);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectPageKeyList() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> expectedResult = Arrays.asList(viewFieldDO);

        // Configure ViewFieldMapper.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO1);
        when(mockViewFieldMapper.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        final List<ViewFieldDO> result = viewFieldManagerImplUnderTest.selectPageKeyList(viewField);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectPageKeyList_ViewFieldMapperReturnsNoItems() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        when(mockViewFieldMapper.selectPageKeyList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewFieldDO> result = viewFieldManagerImplUnderTest.selectPageKeyList(viewField);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetTableNames() {
        // Setup
        when(mockViewFieldMapper.getTableNames()).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<String> result = viewFieldManagerImplUnderTest.getTableNames();

        // Verify the results
        assertEquals(Arrays.asList("value"), result);
    }

    @Test
    public void testGetTableNames_ViewFieldMapperReturnsNoItems() {
        // Setup
        when(mockViewFieldMapper.getTableNames()).thenReturn(Collections.emptyList());

        // Run the test
        final List<String> result = viewFieldManagerImplUnderTest.getTableNames();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectViewFieldAndCondition() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        when(mockViewFieldMapper.selectViewFieldAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Run the test
        final List<Map<String, Object>> result = viewFieldManagerImplUnderTest.selectViewFieldAndCondition(map);

        // Verify the results
    }

    @Test
    public void testSelectViewFieldAndCondition_ViewFieldMapperReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        when(mockViewFieldMapper.selectViewFieldAndCondition(new HashMap<>())).thenReturn(Collections.emptyList());

        // Run the test
        final List<Map<String, Object>> result = viewFieldManagerImplUnderTest.selectViewFieldAndCondition(map);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectViewFieldAndTemplateAndCondition() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        when(mockViewFieldMapper.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Run the test
        final List<Map<String, Object>> result = viewFieldManagerImplUnderTest.selectViewFieldAndTemplateAndCondition(map);

        // Verify the results
    }

    @Test
    public void testSelectViewFieldAndTemplateAndCondition_ViewFieldMapperReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        when(mockViewFieldMapper.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Collections.emptyList());

        // Run the test
        final List<Map<String, Object>> result = viewFieldManagerImplUnderTest.selectViewFieldAndTemplateAndCondition(map);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
