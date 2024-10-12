package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.BizBaseDcDivisionMapping;
import com.microservices.otmp.masterdata.mapper.BizBaseDcDivisionMappingMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseDcDivisionMappingServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseDcDivisionMappingServiceImplTest {

    @Mock
    private BizBaseDcDivisionMappingMapper mockBizBaseDcDivisionMappingMapper;

    @InjectMocks
    private BizBaseDcDivisionMappingServiceImpl bizBaseDcDivisionMappingServiceImplUnderTest;

    @Test
    public void testSelectBizBaseDcDivisionMappingById() {
        // Setup
        // Configure BizBaseDcDivisionMappingMapper.selectBizBaseDcDivisionMappingById(...).
        final BizBaseDcDivisionMapping mapping = new BizBaseDcDivisionMapping();
        mapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setIds("0");
        mapping.setIdsList(Arrays.asList(0L));
        mapping.setId(0L);
        mapping.setSalesOrgCode("salesOrgCode");
        mapping.setSalesOrgName("salesOrgName");
        mapping.setSalesOfficeCode("salesOfficeCode");
        mapping.setSalesOfficeName("salesOfficeName");
        mapping.setDcCode("dcCode");
        mapping.setDcName("dcName");
        mapping.setStatus("code");
        mapping.setCreateBy("loginName");
        mapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockBizBaseDcDivisionMappingMapper.selectBizBaseDcDivisionMappingById(0L)).thenReturn(mapping);

        // Run the test
        final BizBaseDcDivisionMapping result = bizBaseDcDivisionMappingServiceImplUnderTest.selectBizBaseDcDivisionMappingById(
                0L);

        // Verify the results
        assertEquals(result, mapping);
    }

    @Test
    public void testSelectBizBaseDcDivisionMappingList() {
        // Setup
        final BizBaseDcDivisionMapping bizBaseDcDivisionMapping = new BizBaseDcDivisionMapping();
        bizBaseDcDivisionMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setIds("0");
        bizBaseDcDivisionMapping.setIdsList(Arrays.asList(0L));
        bizBaseDcDivisionMapping.setId(0L);
        bizBaseDcDivisionMapping.setSalesOrgCode("salesOrgCode");
        bizBaseDcDivisionMapping.setSalesOrgName("salesOrgName");
        bizBaseDcDivisionMapping.setSalesOfficeCode("salesOfficeCode");
        bizBaseDcDivisionMapping.setSalesOfficeName("salesOfficeName");
        bizBaseDcDivisionMapping.setDcCode("dcCode");
        bizBaseDcDivisionMapping.setDcName("dcName");
        bizBaseDcDivisionMapping.setStatus("code");
        bizBaseDcDivisionMapping.setCreateBy("loginName");
        bizBaseDcDivisionMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseDcDivisionMappingMapper.selectBizBaseDcDivisionMappingList(...).
        final BizBaseDcDivisionMapping mapping = new BizBaseDcDivisionMapping();
        mapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setIds("0");
        mapping.setIdsList(Arrays.asList(0L));
        mapping.setId(0L);
        mapping.setSalesOrgCode("salesOrgCode");
        mapping.setSalesOrgName("salesOrgName");
        mapping.setSalesOfficeCode("salesOfficeCode");
        mapping.setSalesOfficeName("salesOfficeName");
        mapping.setDcCode("dcCode");
        mapping.setDcName("dcName");
        mapping.setStatus("code");
        mapping.setCreateBy("loginName");
        mapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseDcDivisionMapping> bizBaseDcDivisionMappings = Arrays.asList(mapping);
        when(mockBizBaseDcDivisionMappingMapper.selectBizBaseDcDivisionMappingList(
                any(BizBaseDcDivisionMapping.class))).thenReturn(bizBaseDcDivisionMappings);

        // Run the test
        final List<BizBaseDcDivisionMapping> result = bizBaseDcDivisionMappingServiceImplUnderTest.selectBizBaseDcDivisionMappingList(
                bizBaseDcDivisionMapping);

        // Verify the results
        assertEquals(result, bizBaseDcDivisionMappings);
    }

    @Test
    public void testSelectBizBaseDcDivisionMappingList_BizBaseDcDivisionMappingMapperReturnsNoItems() {
        // Setup
        final BizBaseDcDivisionMapping bizBaseDcDivisionMapping = new BizBaseDcDivisionMapping();
        bizBaseDcDivisionMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setIds("0");
        bizBaseDcDivisionMapping.setIdsList(Arrays.asList(0L));
        bizBaseDcDivisionMapping.setId(0L);
        bizBaseDcDivisionMapping.setSalesOrgCode("salesOrgCode");
        bizBaseDcDivisionMapping.setSalesOrgName("salesOrgName");
        bizBaseDcDivisionMapping.setSalesOfficeCode("salesOfficeCode");
        bizBaseDcDivisionMapping.setSalesOfficeName("salesOfficeName");
        bizBaseDcDivisionMapping.setDcCode("dcCode");
        bizBaseDcDivisionMapping.setDcName("dcName");
        bizBaseDcDivisionMapping.setStatus("code");
        bizBaseDcDivisionMapping.setCreateBy("loginName");
        bizBaseDcDivisionMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseDcDivisionMappingMapper.selectBizBaseDcDivisionMappingList(
                any(BizBaseDcDivisionMapping.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseDcDivisionMapping> result = bizBaseDcDivisionMappingServiceImplUnderTest.selectBizBaseDcDivisionMappingList(
                bizBaseDcDivisionMapping);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseDcDivisionMapping() {
        // Setup
        final BizBaseDcDivisionMapping bizBaseDcDivisionMapping = new BizBaseDcDivisionMapping();
        bizBaseDcDivisionMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setIds("0");
        bizBaseDcDivisionMapping.setIdsList(Arrays.asList(0L));
        bizBaseDcDivisionMapping.setId(0L);
        bizBaseDcDivisionMapping.setSalesOrgCode("salesOrgCode");
        bizBaseDcDivisionMapping.setSalesOrgName("salesOrgName");
        bizBaseDcDivisionMapping.setSalesOfficeCode("salesOfficeCode");
        bizBaseDcDivisionMapping.setSalesOfficeName("salesOfficeName");
        bizBaseDcDivisionMapping.setDcCode("dcCode");
        bizBaseDcDivisionMapping.setDcName("dcName");
        bizBaseDcDivisionMapping.setStatus("code");
        bizBaseDcDivisionMapping.setCreateBy("loginName");
        bizBaseDcDivisionMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseDcDivisionMappingMapper.insertBizBaseDcDivisionMapping(
                any(BizBaseDcDivisionMapping.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseDcDivisionMappingServiceImplUnderTest.insertBizBaseDcDivisionMapping(
                bizBaseDcDivisionMapping);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseDcDivisionMapping() {
        // Setup
        final BizBaseDcDivisionMapping bizBaseDcDivisionMapping = new BizBaseDcDivisionMapping();
        bizBaseDcDivisionMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setIds("0");
        bizBaseDcDivisionMapping.setIdsList(Arrays.asList(0L));
        bizBaseDcDivisionMapping.setId(0L);
        bizBaseDcDivisionMapping.setSalesOrgCode("salesOrgCode");
        bizBaseDcDivisionMapping.setSalesOrgName("salesOrgName");
        bizBaseDcDivisionMapping.setSalesOfficeCode("salesOfficeCode");
        bizBaseDcDivisionMapping.setSalesOfficeName("salesOfficeName");
        bizBaseDcDivisionMapping.setDcCode("dcCode");
        bizBaseDcDivisionMapping.setDcName("dcName");
        bizBaseDcDivisionMapping.setStatus("code");
        bizBaseDcDivisionMapping.setCreateBy("loginName");
        bizBaseDcDivisionMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseDcDivisionMappingMapper.updateBizBaseDcDivisionMapping(
                any(BizBaseDcDivisionMapping.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseDcDivisionMappingServiceImplUnderTest.updateBizBaseDcDivisionMapping(
                bizBaseDcDivisionMapping);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseDcDivisionMappingByIds() {
        // Setup
        when(mockBizBaseDcDivisionMappingMapper.updateBizBaseDcDivisionMappingByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseDcDivisionMappingServiceImplUnderTest.deleteBizBaseDcDivisionMappingByIds(
                new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseDcDivisionMappingById() {
        // Setup
        when(mockBizBaseDcDivisionMappingMapper.deleteBizBaseDcDivisionMappingById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseDcDivisionMappingServiceImplUnderTest.deleteBizBaseDcDivisionMappingById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() throws Exception {
        // Setup
        final BizBaseDcDivisionMapping mapping = new BizBaseDcDivisionMapping();
        mapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setIds("0");
        mapping.setIdsList(Arrays.asList(0L));
        mapping.setId(0L);
        mapping.setSalesOrgCode("salesOrgCode");
        mapping.setSalesOrgName("salesOrgName");
        mapping.setSalesOfficeCode("salesOfficeCode");
        mapping.setSalesOfficeName("salesOfficeName");
        mapping.setDcCode("dcCode");
        mapping.setDcName("dcName");
        mapping.setStatus("code");
        mapping.setCreateBy("loginName");
        mapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseDcDivisionMapping> bizs = Arrays.asList(mapping);

        // Configure BizBaseDcDivisionMappingMapper.selectBizBaseProfitCenterListCheck(...).
        final BizBaseDcDivisionMapping mapping1 = new BizBaseDcDivisionMapping();
        mapping1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping1.setIds("0");
        mapping1.setIdsList(Arrays.asList(0L));
        mapping1.setId(0L);
        mapping1.setSalesOrgCode("salesOrgCode");
        mapping1.setSalesOrgName("salesOrgName");
        mapping1.setSalesOfficeCode("salesOfficeCode");
        mapping1.setSalesOfficeName("salesOfficeName");
        mapping1.setDcCode("dcCode");
        mapping1.setDcName("dcName");
        mapping1.setStatus("code");
        mapping1.setCreateBy("loginName");
        mapping1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping1.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping1.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseDcDivisionMapping> bizBaseDcDivisionMappings = Arrays.asList(mapping1);
        when(mockBizBaseDcDivisionMappingMapper.selectBizBaseProfitCenterListCheck(
                any(BizBaseDcDivisionMapping.class))).thenReturn(bizBaseDcDivisionMappings);

        when(mockBizBaseDcDivisionMappingMapper.updateBizBaseDcDivisionMapping(
                any(BizBaseDcDivisionMapping.class))).thenReturn(0);
//        when(mockBizBaseDcDivisionMappingMapper.insertBizBaseDcDivisionMapping(
//                any(BizBaseDcDivisionMapping.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseDcDivisionMappingServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
//        verify(mockBizBaseDcDivisionMappingMapper).updateBizBaseDcDivisionMapping(any(BizBaseDcDivisionMapping.class));
//        verify(mockBizBaseDcDivisionMappingMapper).insertBizBaseDcDivisionMapping(any(BizBaseDcDivisionMapping.class));
    }

    @Test
    public void testImportExcel_BizBaseDcDivisionMappingMapperSelectBizBaseProfitCenterListCheckReturnsNoItems() {
        // Setup
        final BizBaseDcDivisionMapping mapping = new BizBaseDcDivisionMapping();
        mapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setIds("0");
        mapping.setIdsList(Arrays.asList(0L));
        mapping.setId(0L);
        mapping.setSalesOrgCode("salesOrgCode");
        mapping.setSalesOrgName("salesOrgName");
        mapping.setSalesOfficeCode("salesOfficeCode");
        mapping.setSalesOfficeName("salesOfficeName");
        mapping.setDcCode("dcCode");
        mapping.setDcName("dcName");
        mapping.setStatus("code");
        mapping.setCreateBy("loginName");
        mapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseDcDivisionMapping> bizs = Arrays.asList(mapping);
        when(mockBizBaseDcDivisionMappingMapper.selectBizBaseProfitCenterListCheck(
                any(BizBaseDcDivisionMapping.class))).thenReturn(Collections.emptyList());
//        when(mockBizBaseDcDivisionMappingMapper.updateBizBaseDcDivisionMapping(
//                any(BizBaseDcDivisionMapping.class))).thenReturn(0);
        when(mockBizBaseDcDivisionMappingMapper.insertBizBaseDcDivisionMapping(
                any(BizBaseDcDivisionMapping.class))).thenReturn(0);

        // Run the test
        final String result = bizBaseDcDivisionMappingServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
//        verify(mockBizBaseDcDivisionMappingMapper).updateBizBaseDcDivisionMapping(any(BizBaseDcDivisionMapping.class));
//        verify(mockBizBaseDcDivisionMappingMapper).insertBizBaseDcDivisionMapping(any(BizBaseDcDivisionMapping.class));
    }

    @Test
    public void testDropDownList() {
        // Setup
        final BizBaseDcDivisionMapping bizBaseDcDivisionMapping = new BizBaseDcDivisionMapping();
        bizBaseDcDivisionMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setIds("0");
        bizBaseDcDivisionMapping.setIdsList(Arrays.asList(0L));
        bizBaseDcDivisionMapping.setId(0L);
        bizBaseDcDivisionMapping.setSalesOrgCode("salesOrgCode");
        bizBaseDcDivisionMapping.setSalesOrgName("salesOrgName");
        bizBaseDcDivisionMapping.setSalesOfficeCode("salesOfficeCode");
        bizBaseDcDivisionMapping.setSalesOfficeName("salesOfficeName");
        bizBaseDcDivisionMapping.setDcCode("dcCode");
        bizBaseDcDivisionMapping.setDcName("dcName");
        bizBaseDcDivisionMapping.setStatus("code");
        bizBaseDcDivisionMapping.setCreateBy("loginName");
        bizBaseDcDivisionMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseDcDivisionMappingMapper.selectBizBaseDcDivisionMappingList(...).
        final BizBaseDcDivisionMapping mapping = new BizBaseDcDivisionMapping();
        mapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setIds("0");
        mapping.setIdsList(Arrays.asList(0L));
        mapping.setId(0L);
        mapping.setSalesOrgCode("salesOrgCode");
        mapping.setSalesOrgName("salesOrgName");
        mapping.setSalesOfficeCode("salesOfficeCode");
        mapping.setSalesOfficeName("salesOfficeName");
        mapping.setDcCode("dcCode");
        mapping.setDcName("dcName");
        mapping.setStatus("code");
        mapping.setCreateBy("loginName");
        mapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        mapping.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseDcDivisionMapping> bizBaseDcDivisionMappings = Arrays.asList(mapping);
        when(mockBizBaseDcDivisionMappingMapper.selectBizBaseDcDivisionMappingList(
                any(BizBaseDcDivisionMapping.class))).thenReturn(bizBaseDcDivisionMappings);

        // Run the test
        final List<BizBaseDcDivisionMapping> result = bizBaseDcDivisionMappingServiceImplUnderTest.dropDownList(
                bizBaseDcDivisionMapping);

        // Verify the results
        assertEquals(result, bizBaseDcDivisionMappings);
    }

    @Test
    public void testDropDownList_BizBaseDcDivisionMappingMapperReturnsNoItems() {
        // Setup
        final BizBaseDcDivisionMapping bizBaseDcDivisionMapping = new BizBaseDcDivisionMapping();
        bizBaseDcDivisionMapping.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setIds("0");
        bizBaseDcDivisionMapping.setIdsList(Arrays.asList(0L));
        bizBaseDcDivisionMapping.setId(0L);
        bizBaseDcDivisionMapping.setSalesOrgCode("salesOrgCode");
        bizBaseDcDivisionMapping.setSalesOrgName("salesOrgName");
        bizBaseDcDivisionMapping.setSalesOfficeCode("salesOfficeCode");
        bizBaseDcDivisionMapping.setSalesOfficeName("salesOfficeName");
        bizBaseDcDivisionMapping.setDcCode("dcCode");
        bizBaseDcDivisionMapping.setDcName("dcName");
        bizBaseDcDivisionMapping.setStatus("code");
        bizBaseDcDivisionMapping.setCreateBy("loginName");
        bizBaseDcDivisionMapping.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseDcDivisionMapping.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseDcDivisionMappingMapper.selectBizBaseDcDivisionMappingList(
                any(BizBaseDcDivisionMapping.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseDcDivisionMapping> result = bizBaseDcDivisionMappingServiceImplUnderTest.dropDownList(
                bizBaseDcDivisionMapping);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
