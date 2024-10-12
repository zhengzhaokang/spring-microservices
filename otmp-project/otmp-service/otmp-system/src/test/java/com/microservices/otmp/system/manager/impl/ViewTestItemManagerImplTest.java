package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.ViewTestItem;
import com.microservices.otmp.system.domain.entity.ViewTestItemDO;
import com.microservices.otmp.system.mapper.ViewTestItemMapper;
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
public class ViewTestItemManagerImplTest {

    @Mock
    private ViewTestItemMapper mockViewTestItemMapper;

    @InjectMocks
    private ViewTestItemManagerImpl viewTestItemManagerImplUnderTest;

    @Test
    public void testSelectViewTestItemById() {
        // Setup
        final ViewTestItemDO expectedResult = new ViewTestItemDO();
        expectedResult.setId(0L);
        expectedResult.setName("name");
        expectedResult.setTestName("testName");
        expectedResult.setRelation("relation");
        expectedResult.setAge("age");
        expectedResult.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setSex("sex");
        expectedResult.setDescribe("describe");
        expectedResult.setWhetherAdult("whetherAdult");

        // Configure ViewTestItemMapper.selectViewTestItemById(...).
        final ViewTestItemDO viewTestItemDO = new ViewTestItemDO();
        viewTestItemDO.setId(0L);
        viewTestItemDO.setName("name");
        viewTestItemDO.setTestName("testName");
        viewTestItemDO.setRelation("relation");
        viewTestItemDO.setAge("age");
        viewTestItemDO.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTestItemDO.setSex("sex");
        viewTestItemDO.setDescribe("describe");
        viewTestItemDO.setWhetherAdult("whetherAdult");
        when(mockViewTestItemMapper.selectViewTestItemById("name")).thenReturn(viewTestItemDO);

        // Run the test
        final ViewTestItemDO result = viewTestItemManagerImplUnderTest.selectViewTestItemById("name");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectViewTestItemList() {
        // Setup
        final ViewTestItem viewTestItem = new ViewTestItem();
        viewTestItem.setId(0L);
        viewTestItem.setName("name");
        viewTestItem.setTestName("testName");
        viewTestItem.setRelation("relation");
        viewTestItem.setAge("age");
        viewTestItem.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTestItem.setSex("sex");
        viewTestItem.setDescribe("describe");
        viewTestItem.setWhetherAdult("whetherAdult");

        final ViewTestItemDO viewTestItemDO = new ViewTestItemDO();
        viewTestItemDO.setId(0L);
        viewTestItemDO.setName("name");
        viewTestItemDO.setTestName("testName");
        viewTestItemDO.setRelation("relation");
        viewTestItemDO.setAge("age");
        viewTestItemDO.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTestItemDO.setSex("sex");
        viewTestItemDO.setDescribe("describe");
        viewTestItemDO.setWhetherAdult("whetherAdult");
        final List<ViewTestItemDO> expectedResult = Arrays.asList(viewTestItemDO);

        // Configure ViewTestItemMapper.selectViewTestItemList(...).
        final ViewTestItemDO viewTestItemDO1 = new ViewTestItemDO();
        viewTestItemDO1.setId(0L);
        viewTestItemDO1.setName("name");
        viewTestItemDO1.setTestName("testName");
        viewTestItemDO1.setRelation("relation");
        viewTestItemDO1.setAge("age");
        viewTestItemDO1.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTestItemDO1.setSex("sex");
        viewTestItemDO1.setDescribe("describe");
        viewTestItemDO1.setWhetherAdult("whetherAdult");
        final List<ViewTestItemDO> viewTestItemDOS = Arrays.asList(viewTestItemDO1);
        when(mockViewTestItemMapper.selectViewTestItemList(new ViewTestItem())).thenReturn(viewTestItemDOS);

        // Run the test
        final List<ViewTestItemDO> result = viewTestItemManagerImplUnderTest.selectViewTestItemList(viewTestItem);

        // Verify the results
    }

    @Test
    public void testSelectViewTestItemList_ViewTestItemMapperReturnsNoItems() {
        // Setup
        final ViewTestItem viewTestItem = new ViewTestItem();
        viewTestItem.setId(0L);
        viewTestItem.setName("name");
        viewTestItem.setTestName("testName");
        viewTestItem.setRelation("relation");
        viewTestItem.setAge("age");
        viewTestItem.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTestItem.setSex("sex");
        viewTestItem.setDescribe("describe");
        viewTestItem.setWhetherAdult("whetherAdult");

        when(mockViewTestItemMapper.selectViewTestItemList(new ViewTestItem())).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewTestItemDO> result = viewTestItemManagerImplUnderTest.selectViewTestItemList(viewTestItem);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertViewTestItem() {
        // Setup
        final ViewTestItemDO viewTestItem = new ViewTestItemDO();
        viewTestItem.setId(0L);
        viewTestItem.setName("name");
        viewTestItem.setTestName("testName");
        viewTestItem.setRelation("relation");
        viewTestItem.setAge("age");
        viewTestItem.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTestItem.setSex("sex");
        viewTestItem.setDescribe("describe");
        viewTestItem.setWhetherAdult("whetherAdult");

        when(mockViewTestItemMapper.insertViewTestItem(new ViewTestItemDO())).thenReturn(0);

        // Run the test
        final int result = viewTestItemManagerImplUnderTest.insertViewTestItem(viewTestItem);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateViewTestItem() {
        // Setup
        final ViewTestItemDO viewTestItem = new ViewTestItemDO();
        viewTestItem.setId(0L);
        viewTestItem.setName("name");
        viewTestItem.setTestName("testName");
        viewTestItem.setRelation("relation");
        viewTestItem.setAge("age");
        viewTestItem.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTestItem.setSex("sex");
        viewTestItem.setDescribe("describe");
        viewTestItem.setWhetherAdult("whetherAdult");

        when(mockViewTestItemMapper.updateViewTestItem(new ViewTestItemDO())).thenReturn(0);

        // Run the test
        final int result = viewTestItemManagerImplUnderTest.updateViewTestItem(viewTestItem);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewTestItemById() {
        // Setup
        when(mockViewTestItemMapper.deleteViewTestItemById("name")).thenReturn(0);

        // Run the test
        final int result = viewTestItemManagerImplUnderTest.deleteViewTestItemById("name");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewTestItemByIds() {
        // Setup
        when(mockViewTestItemMapper.deleteViewTestItemByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = viewTestItemManagerImplUnderTest.deleteViewTestItemByIds(new String[]{"value"});

        // Verify the results
        assertEquals(0, result);
    }
}
