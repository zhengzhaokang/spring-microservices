package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.SysLogininforDO;
import com.microservices.otmp.system.mapper.SysLogininforMapper;
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
public class SysLogininforManagerImplTest {

    @Mock
    private SysLogininforMapper mockSysLogininforMapper;

    @InjectMocks
    private SysLogininforManagerImpl sysLogininforManagerImplUnderTest;

    @Test
    public void testInsertLogininfor() {
        // Setup
        final SysLogininforDO logininfor = new SysLogininforDO();
        logininfor.setInfoId(0L);
        logininfor.setLoginName("loginName");
        logininfor.setStatus("status");
        logininfor.setIpaddr("ipaddr");
        logininfor.setLoginLocation("loginLocation");
        logininfor.setBrowser("browser");
        logininfor.setOs("os");
        logininfor.setMsg("msg");
        logininfor.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Run the test
        sysLogininforManagerImplUnderTest.insertLogininfor(logininfor);

        // Verify the results
        verify(mockSysLogininforMapper).insertLogininfor(any(SysLogininforDO.class));
    }

    @Test
    public void testSelectLogininforList() {
        // Setup
        final SysLogininforDO logininfor = new SysLogininforDO();
        logininfor.setInfoId(0L);
        logininfor.setLoginName("loginName");
        logininfor.setStatus("status");
        logininfor.setIpaddr("ipaddr");
        logininfor.setLoginLocation("loginLocation");
        logininfor.setBrowser("browser");
        logininfor.setOs("os");
        logininfor.setMsg("msg");
        logininfor.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure SysLogininforMapper.selectLogininforList(...).
        final SysLogininforDO sysLogininforDO = new SysLogininforDO();
        sysLogininforDO.setInfoId(0L);
        sysLogininforDO.setLoginName("loginName");
        sysLogininforDO.setStatus("status");
        sysLogininforDO.setIpaddr("ipaddr");
        sysLogininforDO.setLoginLocation("loginLocation");
        sysLogininforDO.setBrowser("browser");
        sysLogininforDO.setOs("os");
        sysLogininforDO.setMsg("msg");
        sysLogininforDO.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<SysLogininforDO> sysLogininforDOS = Arrays.asList(sysLogininforDO);
        when(mockSysLogininforMapper.selectLogininforList(any(SysLogininforDO.class))).thenReturn(sysLogininforDOS);

        // Run the test
        final List<SysLogininforDO> result = sysLogininforManagerImplUnderTest.selectLogininforList(logininfor);

        // Verify the results
    }

    @Test
    public void testSelectLogininforList_SysLogininforMapperReturnsNoItems() {
        // Setup
        final SysLogininforDO logininfor = new SysLogininforDO();
        logininfor.setInfoId(0L);
        logininfor.setLoginName("loginName");
        logininfor.setStatus("status");
        logininfor.setIpaddr("ipaddr");
        logininfor.setLoginLocation("loginLocation");
        logininfor.setBrowser("browser");
        logininfor.setOs("os");
        logininfor.setMsg("msg");
        logininfor.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockSysLogininforMapper.selectLogininforList(any(SysLogininforDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysLogininforDO> result = sysLogininforManagerImplUnderTest.selectLogininforList(logininfor);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDeleteLogininforByIds() {
        // Setup
        when(mockSysLogininforMapper.deleteLogininforByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = sysLogininforManagerImplUnderTest.deleteLogininforByIds(new String[]{"value"});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCleanLogininfor() {
        // Setup
        when(mockSysLogininforMapper.cleanLogininfor()).thenReturn(0);

        // Run the test
        final int result = sysLogininforManagerImplUnderTest.cleanLogininfor();

        // Verify the results
        assertEquals(0, result);
    }
}
