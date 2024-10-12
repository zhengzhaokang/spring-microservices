package com.microservices.sdms.masterdata.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.BizBaseApcDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseApcDTO;
import com.microservices.otmp.masterdata.manager.BizBaseApcManager;
import com.microservices.otmp.masterdata.service.impl.BizBaseApcServiceImpl;
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

@RunWith(MockitoJUnitRunner.Silent.class)
public class BizBaseApcServiceImplTest {

    @Mock
    private BizBaseApcManager manager;

    @InjectMocks
    private BizBaseApcServiceImpl bizBaseApcServiceImplUnderTest;
    @Mock
    private RedisUtils redisUtils;

    @Test
    public void testSelectBizBaseApcById() {
        // Setup
        final BizBaseApcDTO bizBaseApc = new BizBaseApcDTO();
        bizBaseApc.setId(0L);
        bizBaseApc.setApcCode("apcCode");
        bizBaseApc.setInternalName("internalName");
        bizBaseApc.setExternalName("externalName");
        bizBaseApc.setMtm("mtm");
        bizBaseApc.setCountryCode("countryCode");
        bizBaseApc.setStatus("status");
        bizBaseApc.setRemark("memo");
        // Configure BizBaseApcMapper.selectBizBaseApcById(...).
        final BizBaseApcDO bizBaseApc1 = new BizBaseApcDO();
        bizBaseApc1.setId(0L);
        bizBaseApc1.setApcCode("apcCode");
        bizBaseApc1.setInternalName("internalName");
        bizBaseApc1.setExternalName("externalName");
        bizBaseApc1.setMtm("mtm");
        bizBaseApc1.setCountryCode("countryCode");
        bizBaseApc1.setStatus("status");
        bizBaseApc1.setRemark("memo");
        when(manager.selectBizBaseApcById(0L)).thenReturn(bizBaseApc1);

        // Run the test
        final BizBaseApcDTO result = bizBaseApcServiceImplUnderTest.selectBizBaseApcById(0L);
        assertEquals(JSON.toJSONString(bizBaseApc), JSON.toJSONString(result));
        // Verify the results
    }

    @Test
    public void testSelectBizBaseApcList() {
        // Setup
        final BizBaseApcDTO bizBaseApc = new BizBaseApcDTO();
        bizBaseApc.setId(0L);
        bizBaseApc.setApcCode("apcCode");
        bizBaseApc.setInternalName("internalName");
        bizBaseApc.setExternalName("externalName");
        bizBaseApc.setMtm("mtm");
        bizBaseApc.setCountryCode("countryCode");
        bizBaseApc.setStatus("status");
        bizBaseApc.setRemark("memo");
        final List<BizBaseApcDTO> bizBaseApcs = Arrays.asList(bizBaseApc);

        // Configure BizBaseApcMapper.selectBizBaseApcList(...).
        final BizBaseApcDO bizBaseApc1 = new BizBaseApcDO();
        bizBaseApc1.setId(0L);
        bizBaseApc1.setApcCode("apcCode");
        bizBaseApc1.setInternalName("internalName");
        bizBaseApc1.setExternalName("externalName");
        bizBaseApc1.setMtm("mtm");
        bizBaseApc1.setCountryCode("countryCode");
        bizBaseApc1.setStatus("status");
        bizBaseApc1.setRemark("memo");
        final List<BizBaseApcDO> bizBaseApcs1 = Arrays.asList(bizBaseApc1);
        when(manager.selectBizBaseApcList(any(BizBaseApcDO.class))).thenReturn(bizBaseApcs1);

        // Run the test
        final List<BizBaseApcDTO> result = bizBaseApcServiceImplUnderTest.selectBizBaseApcList(bizBaseApc);
        assertEquals(JSON.toJSONString(bizBaseApcs), JSON.toJSONString(result));
        // Verify the results
    }

    @Test
    public void testSelectBizBaseApcList_BizBaseApcMapperReturnsNoItems() {
        // Setup
        final BizBaseApcDTO bizBaseApc = new BizBaseApcDTO();
        bizBaseApc.setId(0L);
        bizBaseApc.setApcCode("apcCode");
        bizBaseApc.setInternalName("internalName");
        bizBaseApc.setExternalName("externalName");
        bizBaseApc.setMtm("mtm");
        bizBaseApc.setCountryCode("countryCode");
        bizBaseApc.setStatus("status");
        bizBaseApc.setRemark("memo");

        when(manager.selectBizBaseApcList(any(BizBaseApcDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseApcDTO> result = bizBaseApcServiceImplUnderTest.selectBizBaseApcList(bizBaseApc);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseApc() {
        // Setup
        final BizBaseApcDTO bizBaseApc = new BizBaseApcDTO();
        bizBaseApc.setId(0L);
        bizBaseApc.setApcCode("apcCode");
        bizBaseApc.setInternalName("internalName");
        bizBaseApc.setExternalName("externalName");
        bizBaseApc.setMtm("mtm");
        bizBaseApc.setCountryCode("countryCode");
        bizBaseApc.setStatus("status");
        bizBaseApc.setRemark("memo");

        when(manager.insertBizBaseApc(any(BizBaseApcDO.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseApcServiceImplUnderTest.insertBizBaseApc(bizBaseApc);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseApc() {
        // Setup
        final BizBaseApcDTO bizBaseApc = new BizBaseApcDTO();
        bizBaseApc.setId(0L);
        bizBaseApc.setApcCode("apcCode");
        bizBaseApc.setInternalName("internalName");
        bizBaseApc.setExternalName("externalName");
        bizBaseApc.setMtm("mtm");
        bizBaseApc.setCountryCode("countryCode");
        bizBaseApc.setStatus("status");
        bizBaseApc.setRemark("memo");

        when(manager.updateBizBaseApc(any(BizBaseApcDO.class))).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseApcServiceImplUnderTest.updateBizBaseApc(bizBaseApc);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseApcByIds() {
        // Setup
        when(manager.deleteBizBaseApcByIds(any(Long[].class))).thenReturn(0);
    when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseApcServiceImplUnderTest.deleteBizBaseApcByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseApcById() {
        // Setup
        when(manager.deleteBizBaseApcById(0L)).thenReturn(0);
        when(redisUtils.get("12",String.class)).thenReturn("123213");
        // Run the test
        final int result = bizBaseApcServiceImplUnderTest.deleteBizBaseApcById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSetMtmToRedis() {
        // Setup
        final BizBaseApcDTO bizBaseApcDTO = new BizBaseApcDTO();
        bizBaseApcDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseApcDTO.setId(0L);
        bizBaseApcDTO.setApcCode("apcCode");
        bizBaseApcDTO.setInternalName("internalName");
        bizBaseApcDTO.setExternalName("externalName");
        bizBaseApcDTO.setMtm("mtm");
        bizBaseApcDTO.setCountryCode("countryCode");
        bizBaseApcDTO.setStatus("status");
        bizBaseApcDTO.setMtmDescription("mtmDescription");
        bizBaseApcDTO.setCreateBy("createBy");
        bizBaseApcDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseApcDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseApcDTO.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseApcDTO> bizBaseApcList = Arrays.asList(bizBaseApcDTO);

        // Run the test
        bizBaseApcServiceImplUnderTest.setMtmToRedis(bizBaseApcList);

        // Verify the results
        verify(redisUtils).set("master:mtm", Arrays.asList("mtm"), 31536000L);
    }

    @Test
    public void testSetApcToRedis() {
        // Setup
        final BizBaseApcDTO bizBaseApcDTO = new BizBaseApcDTO();
        bizBaseApcDTO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseApcDTO.setId(0L);
        bizBaseApcDTO.setApcCode("apcCode");
        bizBaseApcDTO.setInternalName("internalName");
        bizBaseApcDTO.setExternalName("externalName");
        bizBaseApcDTO.setMtm("mtm");
        bizBaseApcDTO.setCountryCode("countryCode");
        bizBaseApcDTO.setStatus("status");
        bizBaseApcDTO.setMtmDescription("mtmDescription");
        bizBaseApcDTO.setCreateBy("createBy");
        bizBaseApcDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseApcDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseApcDTO.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseApcDTO> bizBaseApcList = Arrays.asList(bizBaseApcDTO);

        // Run the test
        bizBaseApcServiceImplUnderTest.setApcToRedis(bizBaseApcList);

        // Verify the results
        verify(redisUtils).set("master:apc_code", Arrays.asList("apcCode"), 31536000L);
    }

    @Test
    public void testSelectBizBaseApcListByPage() {
        // Setup
        final BizBaseApcDTO bizBaseApc = new BizBaseApcDTO();
        bizBaseApc.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseApc.setId(0L);
        bizBaseApc.setApcCode("apcCode");
        bizBaseApc.setInternalName("internalName");
        bizBaseApc.setExternalName("externalName");
        bizBaseApc.setMtm("mtm");
        bizBaseApc.setCountryCode("countryCode");
        bizBaseApc.setStatus("status");
        bizBaseApc.setMtmDescription("mtmDescription");
        bizBaseApc.setCreateBy("createBy");
        bizBaseApc.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseApc.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseApc.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseApcManager.selectBizBaseApcList(...).
        final BizBaseApcDO bizBaseApcDO = new BizBaseApcDO();
        bizBaseApcDO.setId(0L);
        bizBaseApcDO.setApcCode("apcCode");
        bizBaseApcDO.setInternalName("internalName");
        bizBaseApcDO.setExternalName("externalName");
        bizBaseApcDO.setMtm("mtm");
        bizBaseApcDO.setCountryCode("countryCode");
        bizBaseApcDO.setStatus("status");
        bizBaseApcDO.setMtmDescription("mtmDescription");
        final List<BizBaseApcDO> bizBaseApcDOS = Arrays.asList(bizBaseApcDO);
        when(manager.selectBizBaseApcList(any(BizBaseApcDO.class))).thenReturn(bizBaseApcDOS);

        // Run the test
        final PageInfo<BizBaseApcDTO> result = bizBaseApcServiceImplUnderTest.selectBizBaseApcListByPage(bizBaseApc);

        // Verify the results
        assertEquals(result.getSize(), bizBaseApcDOS.size());
    }
}
