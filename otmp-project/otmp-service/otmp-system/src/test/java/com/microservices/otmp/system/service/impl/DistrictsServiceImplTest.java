package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.Districts;
import com.microservices.otmp.system.domain.entity.DistrictsDO;
import com.microservices.otmp.system.manager.DistrictsManager;
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
public class DistrictsServiceImplTest {

    @Mock
    private DistrictsManager mockDistrictsManager;

    @InjectMocks
    private DistrictsServiceImpl districtsServiceImplUnderTest;

    @Test
    public void testSelectDistrictsById() {
        // Setup
        // Configure DistrictsManager.selectDistrictsById(...).
        final Districts districts = new Districts();
        districts.setId(0);
        districts.setPid(0);
        districts.setDeep(0);
        districts.setName("name");
        districts.setPname("pname");
        districts.setPinyin("pinyin");
        districts.setPinyinShor("pinyinShor");
        districts.setExtName("extName");
        districts.setOperator("operator");
        when(mockDistrictsManager.selectDistrictsById(0)).thenReturn(districts);

        // Run the test
        final Districts result = districtsServiceImplUnderTest.selectDistrictsById(0);

        // Verify the results
    }

    @Test
    public void testSelectDistrictsList() {
        // Setup
        final Districts districts = new Districts();
        districts.setId(0);
        districts.setPid(0);
        districts.setDeep(0);
        districts.setName("name");
        districts.setPname("pname");
        districts.setPinyin("pinyin");
        districts.setPinyinShor("pinyinShor");
        districts.setExtName("extName");
        districts.setOperator("operator");

        // Configure DistrictsManager.selectDistrictsList(...).
        final Districts districts2 = new Districts();
        districts2.setId(0);
        districts2.setPid(0);
        districts2.setDeep(0);
        districts2.setName("name");
        districts2.setPname("pname");
        districts2.setPinyin("pinyin");
        districts2.setPinyinShor("pinyinShor");
        districts2.setExtName("extName");
        districts2.setOperator("operator");
        final List<Districts> districts1 = Arrays.asList(districts2);
        when(mockDistrictsManager.selectDistrictsList(any(Districts.class))).thenReturn(districts1);

        // Run the test
        final List<Districts> result = districtsServiceImplUnderTest.selectDistrictsList(districts);

        // Verify the results
    }

    @Test
    public void testSelectDistrictsList_DistrictsManagerReturnsNoItems() {
        // Setup
        final Districts districts = new Districts();
        districts.setId(0);
        districts.setPid(0);
        districts.setDeep(0);
        districts.setName("name");
        districts.setPname("pname");
        districts.setPinyin("pinyin");
        districts.setPinyinShor("pinyinShor");
        districts.setExtName("extName");
        districts.setOperator("operator");

        when(mockDistrictsManager.selectDistrictsList(any(Districts.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<Districts> result = districtsServiceImplUnderTest.selectDistrictsList(districts);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertDistricts() {
        // Setup
        final Districts districts = new Districts();
        districts.setId(0);
        districts.setPid(0);
        districts.setDeep(0);
        districts.setName("name");
        districts.setPname("pname");
        districts.setPinyin("pinyin");
        districts.setPinyinShor("pinyinShor");
        districts.setExtName("extName");
        districts.setOperator("operator");

        when(mockDistrictsManager.insertDistricts(any(DistrictsDO.class))).thenReturn(0);

        // Run the test
        final int result = districtsServiceImplUnderTest.insertDistricts(districts);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDistricts() {
        // Setup
        final Districts districts = new Districts();
        districts.setId(0);
        districts.setPid(0);
        districts.setDeep(0);
        districts.setName("name");
        districts.setPname("pname");
        districts.setPinyin("pinyin");
        districts.setPinyinShor("pinyinShor");
        districts.setExtName("extName");
        districts.setOperator("operator");

        when(mockDistrictsManager.updateDistricts(any(DistrictsDO.class))).thenReturn(0);

        // Run the test
        final int result = districtsServiceImplUnderTest.updateDistricts(districts);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteDistrictsByIds() {
        // Setup
        when(mockDistrictsManager.deleteDistrictsByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = districtsServiceImplUnderTest.deleteDistrictsByIds("ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectDistrictsPage() {
        // Setup
        final Districts districts = new Districts();
        districts.setId(0);
        districts.setPid(0);
        districts.setDeep(0);
        districts.setName("name");
        districts.setPname("pname");
        districts.setPinyin("pinyin");
        districts.setPinyinShor("pinyinShor");
        districts.setExtName("extName");
        districts.setOperator("operator");

        // Configure DistrictsManager.selectDistrictsList(...).
        final Districts districts2 = new Districts();
        districts2.setId(0);
        districts2.setPid(0);
        districts2.setDeep(0);
        districts2.setName("name");
        districts2.setPname("pname");
        districts2.setPinyin("pinyin");
        districts2.setPinyinShor("pinyinShor");
        districts2.setExtName("extName");
        districts2.setOperator("operator");
        final List<Districts> districts1 = Arrays.asList(districts2);
        when(mockDistrictsManager.selectDistrictsList(any(Districts.class))).thenReturn(districts1);

        // Run the test
        final PageInfo<Districts> result = districtsServiceImplUnderTest.selectDistrictsPage(districts);

        // Verify the results
    }

    @Test
    public void testSelectDistrictsPage_DistrictsManagerReturnsNoItems() {
        // Setup
        final Districts districts = new Districts();
        districts.setId(0);
        districts.setPid(0);
        districts.setDeep(0);
        districts.setName("name");
        districts.setPname("pname");
        districts.setPinyin("pinyin");
        districts.setPinyinShor("pinyinShor");
        districts.setExtName("extName");
        districts.setOperator("operator");

        when(mockDistrictsManager.selectDistrictsList(any(Districts.class))).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<Districts> result = districtsServiceImplUnderTest.selectDistrictsPage(districts);

        // Verify the results
    }
}
