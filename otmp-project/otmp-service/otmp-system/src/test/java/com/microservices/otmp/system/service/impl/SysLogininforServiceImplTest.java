package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.SysLogininfor;
import com.microservices.otmp.system.domain.entity.SysLogininforDO;
import com.microservices.otmp.system.manager.SysLogininforManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SysLogininforServiceImplTest {

    @Mock
    private SysLogininforManager mockSysLogininforManager;

    @InjectMocks
    private SysLogininforServiceImpl sysLogininforServiceImplUnderTest;

    @Test
    public void testInsertLogininfor() {
        // Setup
        final SysLogininfor logininfor = new SysLogininfor();
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
        sysLogininforServiceImplUnderTest.insertLogininfor(logininfor);

        // Verify the results
        verify(mockSysLogininforManager).insertLogininfor(any(SysLogininforDO.class));
    }

    @Test
    public void testSelectLogininforList() {
        // Setup
        final SysLogininfor logininfor = new SysLogininfor();
        logininfor.setInfoId(0L);
        logininfor.setLoginName("loginName");
        logininfor.setStatus("status");
        logininfor.setIpaddr("ipaddr");
        logininfor.setLoginLocation("loginLocation");
        logininfor.setBrowser("browser");
        logininfor.setOs("os");
        logininfor.setMsg("msg");
        logininfor.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure SysLogininforManager.selectLogininforList(...).
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
        when(mockSysLogininforManager.selectLogininforList(any(SysLogininforDO.class))).thenReturn(sysLogininforDOS);

        // Run the test
        final List<SysLogininfor> result = sysLogininforServiceImplUnderTest.selectLogininforList(logininfor);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockSysLogininforManager, times(1)).selectLogininforList(any());
    }

    @Test
    public void testSelectLogininforList_SysLogininforManagerReturnsNoItems() {
        // Setup
        final SysLogininfor logininfor = new SysLogininfor();
        logininfor.setInfoId(0L);
        logininfor.setLoginName("loginName");
        logininfor.setStatus("status");
        logininfor.setIpaddr("ipaddr");
        logininfor.setLoginLocation("loginLocation");
        logininfor.setBrowser("browser");
        logininfor.setOs("os");
        logininfor.setMsg("msg");
        logininfor.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockSysLogininforManager.selectLogininforList(any(SysLogininforDO.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<SysLogininfor> result = sysLogininforServiceImplUnderTest.selectLogininforList(logininfor);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDeleteLogininforByIds() {
        // Setup
        when(mockSysLogininforManager.deleteLogininforByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = sysLogininforServiceImplUnderTest.deleteLogininforByIds("ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCleanLogininfor() {
        // Setup
        when(mockSysLogininforManager.cleanLogininfor()).thenReturn(0);

        // Run the test
        sysLogininforServiceImplUnderTest.cleanLogininfor();

        // Verify the results
        verify(mockSysLogininforManager).cleanLogininfor();
    }

    @Test
    public void testSelectLogininforPage() {
        // Setup
        final SysLogininfor logininfor = new SysLogininfor();
        logininfor.setInfoId(0L);
        logininfor.setLoginName("loginName");
        logininfor.setStatus("status");
        logininfor.setIpaddr("ipaddr");
        logininfor.setLoginLocation("loginLocation");
        logininfor.setBrowser("browser");
        logininfor.setOs("os");
        logininfor.setMsg("msg");
        logininfor.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure SysLogininforManager.selectLogininforList(...).
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
        when(mockSysLogininforManager.selectLogininforList(any(SysLogininforDO.class))).thenReturn(sysLogininforDOS);

        // Run the test
        final PageInfo<SysLogininfor> result = sysLogininforServiceImplUnderTest.selectLogininforPage(logininfor);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals(1, result.getTotal());
        verify(mockSysLogininforManager, times(1)).selectLogininforList(any());
    }

    @Test
    public void testSelectLogininforPage_SysLogininforManagerReturnsNoItems() {
        // Setup
        final SysLogininfor logininfor = new SysLogininfor();
        logininfor.setInfoId(0L);
        logininfor.setLoginName("loginName");
        logininfor.setStatus("status");
        logininfor.setIpaddr("ipaddr");
        logininfor.setLoginLocation("loginLocation");
        logininfor.setBrowser("browser");
        logininfor.setOs("os");
        logininfor.setMsg("msg");
        logininfor.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockSysLogininforManager.selectLogininforList(any(SysLogininforDO.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<SysLogininfor> result = sysLogininforServiceImplUnderTest.selectLogininforPage(logininfor);

        // Verify the results
        Assert.assertNotNull(result);
        assertEquals(Collections.emptyList(), result.getList());
        verify(mockSysLogininforManager, times(1)).selectLogininforList(any());
    }
}
