package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.Districts;
import com.microservices.otmp.system.domain.entity.DistrictsDO;
import com.microservices.otmp.system.mapper.DistrictsMapper;
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
public class DistrictsManagerImplTest {

    @Mock
    private DistrictsMapper mockDistrictsMapper;

    @InjectMocks
    private DistrictsManagerImpl districtsManagerImplUnderTest;

    @Test
    public void testSelectDistrictsById() {
        // Setup
        // Configure DistrictsMapper.selectDistrictsById(...).
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
        when(mockDistrictsMapper.selectDistrictsById(0)).thenReturn(districts);

        // Run the test
        final Districts result = districtsManagerImplUnderTest.selectDistrictsById(0);

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

        // Configure DistrictsMapper.selectDistrictsList(...).
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
        when(mockDistrictsMapper.selectDistrictsList(any(Districts.class))).thenReturn(districts1);

        // Run the test
        final List<Districts> result = districtsManagerImplUnderTest.selectDistrictsList(districts);

        // Verify the results
    }

    @Test
    public void testSelectDistrictsList_DistrictsMapperReturnsNoItems() {
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

        when(mockDistrictsMapper.selectDistrictsList(any(Districts.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<Districts> result = districtsManagerImplUnderTest.selectDistrictsList(districts);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertDistricts() {
        // Setup
        final DistrictsDO districtsDO = new DistrictsDO();
        districtsDO.setId(0);
        districtsDO.setPid(0);
        districtsDO.setDeep(0);
        districtsDO.setName("name");
        districtsDO.setPname("pname");
        districtsDO.setPinyin("pinyin");
        districtsDO.setPinyinShor("pinyinShor");
        districtsDO.setExtName("extName");
        districtsDO.setOperator("operator");

        when(mockDistrictsMapper.insertDistricts(any(DistrictsDO.class))).thenReturn(0);

        // Run the test
        final int result = districtsManagerImplUnderTest.insertDistricts(districtsDO);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateDistricts() {
        // Setup
        final DistrictsDO districtsDO = new DistrictsDO();
        districtsDO.setId(0);
        districtsDO.setPid(0);
        districtsDO.setDeep(0);
        districtsDO.setName("name");
        districtsDO.setPname("pname");
        districtsDO.setPinyin("pinyin");
        districtsDO.setPinyinShor("pinyinShor");
        districtsDO.setExtName("extName");
        districtsDO.setOperator("operator");

        when(mockDistrictsMapper.updateDistricts(any(DistrictsDO.class))).thenReturn(0);

        // Run the test
        final int result = districtsManagerImplUnderTest.updateDistricts(districtsDO);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteDistrictsById() {
        // Setup
        when(mockDistrictsMapper.deleteDistrictsById(0)).thenReturn(0);

        // Run the test
        final int result = districtsManagerImplUnderTest.deleteDistrictsById(0);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteDistrictsByIds() {
        // Setup
        when(mockDistrictsMapper.deleteDistrictsByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = districtsManagerImplUnderTest.deleteDistrictsByIds(new String[]{"value"});

        // Verify the results
        assertEquals(0, result);
    }
}
