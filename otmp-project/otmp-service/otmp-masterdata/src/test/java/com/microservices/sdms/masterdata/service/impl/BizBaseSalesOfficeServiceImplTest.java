package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOffice;
import com.microservices.otmp.masterdata.mapper.BizBaseSalesOfficeMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseSalesOfficeServiceImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BizBaseSalesOfficeServiceImplTest {

    @Mock
    private BizBaseSalesOfficeMapper mockBizBaseSalesOfficeMapper;
    @Mock
    private RedisUtils redisUtils;
    @InjectMocks
    private BizBaseSalesOfficeServiceImpl bizBaseSalesOfficeServiceImplUnderTest;

    @Test
    public void testSelectBizBaseSalesOfficeById() {
        // Setup
        // Configure BizBaseSalesOfficeMapper.selectBizBaseSalesOfficeById(...).
        final BizBaseSalesOffice bizBaseSalesOffice = new BizBaseSalesOffice();
        bizBaseSalesOffice.setId(0L);
        bizBaseSalesOffice.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOffice.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOffice.setStatus("status");
        bizBaseSalesOffice.setRemark("memo");
        when(mockBizBaseSalesOfficeMapper.selectBizBaseSalesOfficeById(0L)).thenReturn(bizBaseSalesOffice);

        // Run the test
        final BizBaseSalesOffice result = bizBaseSalesOfficeServiceImplUnderTest.selectBizBaseSalesOfficeById(0L);
        assertEquals(bizBaseSalesOffice, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseSalesOfficeList() {
        // Setup
        final BizBaseSalesOffice bizBaseSalesOffice = new BizBaseSalesOffice();
        bizBaseSalesOffice.setId(0L);
        bizBaseSalesOffice.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOffice.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOffice.setStatus("status");
        bizBaseSalesOffice.setRemark("memo");


        // Configure BizBaseSalesOfficeMapper.selectBizBaseSalesOfficeList(...).
        final BizBaseSalesOffice bizBaseSalesOffice1 = new BizBaseSalesOffice();
        bizBaseSalesOffice1.setId(0L);
        bizBaseSalesOffice1.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOffice1.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOffice1.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOffice1.setStatus("status");
        bizBaseSalesOffice1.setRemark("memo");

        final List<BizBaseSalesOffice> bizBaseSalesOffices = Arrays.asList(bizBaseSalesOffice1);
        when(mockBizBaseSalesOfficeMapper.selectBizBaseSalesOfficeList(any(BizBaseSalesOffice.class))).thenReturn(bizBaseSalesOffices);

        // Run the test
        final List<BizBaseSalesOffice> result = bizBaseSalesOfficeServiceImplUnderTest.selectBizBaseSalesOfficeList(bizBaseSalesOffice);
        assertEquals(bizBaseSalesOffices, result);
        // Verify the results
    }

    @Test
    public void testSelectBizBaseSalesOfficeList_BizBaseSalesOfficeMapperReturnsNoItems() {
        // Setup
        final BizBaseSalesOffice bizBaseSalesOffice = new BizBaseSalesOffice();
        bizBaseSalesOffice.setId(0L);
        bizBaseSalesOffice.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOffice.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOffice.setStatus("status");
        bizBaseSalesOffice.setRemark("memo");


        when(mockBizBaseSalesOfficeMapper.selectBizBaseSalesOfficeList(any(BizBaseSalesOffice.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseSalesOffice> result = bizBaseSalesOfficeServiceImplUnderTest.selectBizBaseSalesOfficeList(bizBaseSalesOffice);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseSalesOffice() {
        // Setup
        final BizBaseSalesOffice bizBaseSalesOffice = new BizBaseSalesOffice();
        bizBaseSalesOffice.setId(0L);
        bizBaseSalesOffice.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOffice.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOffice.setStatus("status");
        bizBaseSalesOffice.setRemark("memo");


        when(mockBizBaseSalesOfficeMapper.insertBizBaseSalesOffice(any(BizBaseSalesOffice.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseSalesOfficeServiceImplUnderTest.insertBizBaseSalesOffice(bizBaseSalesOffice);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseSalesOffice() {
        // Setup
        final BizBaseSalesOffice bizBaseSalesOffice = new BizBaseSalesOffice();
        bizBaseSalesOffice.setId(0L);
        bizBaseSalesOffice.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOffice.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOffice.setStatus("status");
        bizBaseSalesOffice.setRemark("memo");


        when(mockBizBaseSalesOfficeMapper.updateBizBaseSalesOffice(any(BizBaseSalesOffice.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseSalesOfficeServiceImplUnderTest.updateBizBaseSalesOffice(bizBaseSalesOffice);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSalesOfficeByIds() {
        // Setup
        when(mockBizBaseSalesOfficeMapper.updateBizBaseSalesOfficeByIds(any(Long[].class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseSalesOfficeServiceImplUnderTest.deleteBizBaseSalesOfficeByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseSalesOfficeById() {
        // Setup
        when(mockBizBaseSalesOfficeMapper.deleteBizBaseSalesOfficeById(0L)).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseSalesOfficeServiceImplUnderTest.deleteBizBaseSalesOfficeById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testImportExcel() {
        // Setup
        final BizBaseSalesOffice bizBaseSalesOffice = new BizBaseSalesOffice();
        bizBaseSalesOffice.setId(0L);
        bizBaseSalesOffice.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOffice.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOffice.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOffice.setStatus("status");
        bizBaseSalesOffice.setRemark("memo");

        final List<BizBaseSalesOffice> bizs = Arrays.asList(bizBaseSalesOffice);

        // Configure BizBaseSalesOfficeMapper.selectBizBaseSalesOfficeListCheck(...).
        final BizBaseSalesOffice bizBaseSalesOffice1 = new BizBaseSalesOffice();
        bizBaseSalesOffice1.setId(0L);
        bizBaseSalesOffice1.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOffice1.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOffice1.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOffice1.setStatus("status");
        bizBaseSalesOffice1.setRemark("memo");

        final List<BizBaseSalesOffice> bizBaseSalesOffices = Arrays.asList(bizBaseSalesOffice1);
        when(mockBizBaseSalesOfficeMapper.selectBizBaseSalesOfficeListCheck(any(BizBaseSalesOffice.class))).thenReturn(bizBaseSalesOffices);

        when(mockBizBaseSalesOfficeMapper.updateBizBaseSalesOffice(any(BizBaseSalesOffice.class))).thenReturn(0);


        // Run the test
        final String result = bizBaseSalesOfficeServiceImplUnderTest.importExcel(bizs, "loginName");

        // Verify the results
        assertEquals("恭喜您，数据已全部导入成功！共 1 条，数据如下：", result);
        verify(mockBizBaseSalesOfficeMapper).selectBizBaseSalesOfficeListCheck(any(BizBaseSalesOffice.class));

    }


}
