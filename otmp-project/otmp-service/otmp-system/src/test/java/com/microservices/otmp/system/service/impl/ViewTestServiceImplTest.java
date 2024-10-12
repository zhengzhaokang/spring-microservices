package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.system.domain.ViewTest;
import com.microservices.otmp.system.domain.entity.ViewTestDO;
import com.microservices.otmp.system.manager.ViewTestManager;
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
public class ViewTestServiceImplTest {

    @Mock
    private ViewTestManager mockViewTestManager;

    @InjectMocks
    private ViewTestServiceImpl viewTestServiceImplUnderTest;

    @Test
    public void testSelectViewTestById() {
        // Setup
        // Configure ViewTestManager.selectViewTestById(...).
        final ViewTestDO viewTestDO = new ViewTestDO();
        viewTestDO.setName("name");
        viewTestDO.setAge(0);
        viewTestDO.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTestDO.setSex(0);
        viewTestDO.setSchool("school");
        viewTestDO.setClass("class_");
        viewTestDO.setProvince("province");
        viewTestDO.setCity("city");
        viewTestDO.setArea("area");
        when(mockViewTestManager.selectViewTestById("name")).thenReturn(viewTestDO);

        // Run the test
        final ViewTest result = viewTestServiceImplUnderTest.selectViewTestById("name");

        // Verify the results
    }

    @Test
    public void testSelectViewTestList() {
        // Setup
        final ViewTest viewTest = new ViewTest();
        viewTest.setName("name");
        viewTest.setAge(0);
        viewTest.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTest.setSex(0);
        viewTest.setSchool("school");
        viewTest.setClass("class_");
        viewTest.setProvince("province");
        viewTest.setCity("city");
        viewTest.setArea("area");

        // Configure ViewTestManager.selectViewTestList(...).
        final ViewTestDO viewTestDO = new ViewTestDO();
        viewTestDO.setName("name");
        viewTestDO.setAge(0);
        viewTestDO.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTestDO.setSex(0);
        viewTestDO.setSchool("school");
        viewTestDO.setClass("class_");
        viewTestDO.setProvince("province");
        viewTestDO.setCity("city");
        viewTestDO.setArea("area");
        final List<ViewTestDO> viewTestDOS = Arrays.asList(viewTestDO);
        when(mockViewTestManager.selectViewTestList(any(ViewTest.class))).thenReturn(viewTestDOS);

        // Run the test
        final List<ViewTest> result = viewTestServiceImplUnderTest.selectViewTestList(viewTest);

        // Verify the results
    }

    @Test
    public void testSelectViewTestList_ViewTestManagerReturnsNoItems() {
        // Setup
        final ViewTest viewTest = new ViewTest();
        viewTest.setName("name");
        viewTest.setAge(0);
        viewTest.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTest.setSex(0);
        viewTest.setSchool("school");
        viewTest.setClass("class_");
        viewTest.setProvince("province");
        viewTest.setCity("city");
        viewTest.setArea("area");

        when(mockViewTestManager.selectViewTestList(any(ViewTest.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewTest> result = viewTestServiceImplUnderTest.selectViewTestList(viewTest);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertViewTest() {
        // Setup
        final ViewTest viewTest = new ViewTest();
        viewTest.setName("name");
        viewTest.setAge(0);
        viewTest.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTest.setSex(0);
        viewTest.setSchool("school");
        viewTest.setClass("class_");
        viewTest.setProvince("province");
        viewTest.setCity("city");
        viewTest.setArea("area");

        when(mockViewTestManager.insertViewTest(any(ViewTestDO.class))).thenReturn(0);

        // Run the test
        final int result = viewTestServiceImplUnderTest.insertViewTest(viewTest);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateViewTest() {
        // Setup
        final ViewTest viewTest = new ViewTest();
        viewTest.setName("name");
        viewTest.setAge(0);
        viewTest.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        viewTest.setSex(0);
        viewTest.setSchool("school");
        viewTest.setClass("class_");
        viewTest.setProvince("province");
        viewTest.setCity("city");
        viewTest.setArea("area");

        when(mockViewTestManager.updateViewTest(any(ViewTestDO.class))).thenReturn(0);

        // Run the test
        final int result = viewTestServiceImplUnderTest.updateViewTest(viewTest);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewTestByIds() {
        // Setup
        when(mockViewTestManager.deleteViewTestByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = viewTestServiceImplUnderTest.deleteViewTestByIds("ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewTestById() {
        // Setup
        when(mockViewTestManager.deleteViewTestById("name")).thenReturn(0);

        // Run the test
        final int result = viewTestServiceImplUnderTest.deleteViewTestById("name");

        // Verify the results
        assertEquals(0, result);
    }
}
