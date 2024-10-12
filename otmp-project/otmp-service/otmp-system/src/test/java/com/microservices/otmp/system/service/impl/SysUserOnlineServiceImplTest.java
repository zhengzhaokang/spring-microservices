package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.system.domain.SysUserOnline;
import com.microservices.otmp.system.domain.entity.SysUserOnlineDO;
import com.microservices.otmp.system.manager.SysUserOnlineManager;
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
public class SysUserOnlineServiceImplTest {

    @Mock
    private SysUserOnlineManager mockSysUserOnlineManager;

    @InjectMocks
    private SysUserOnlineServiceImpl sysUserOnlineServiceImplUnderTest;

    @Test
    public void testSelectOnlineById() {
        // Setup
        // Configure SysUserOnlineManager.selectOnlineById(...).
        final SysUserOnlineDO sysUserOnlineDO = new SysUserOnlineDO();
        sysUserOnlineDO.setSessionId("sessionId");
        sysUserOnlineDO.setDeptName("deptName");
        sysUserOnlineDO.setLoginName("loginName");
        sysUserOnlineDO.setIpaddr("ipaddr");
        sysUserOnlineDO.setLoginLocation("loginLocation");
        sysUserOnlineDO.setBrowser("browser");
        sysUserOnlineDO.setOs("os");
        sysUserOnlineDO.setStartTimestamp(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserOnlineDO.setLastAccessTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserOnlineDO.setExpireTime(0L);
        when(mockSysUserOnlineManager.selectOnlineById("sessionId")).thenReturn(sysUserOnlineDO);

        // Run the test
        final SysUserOnline result = sysUserOnlineServiceImplUnderTest.selectOnlineById("sessionId");

        // Verify the results
    }

    @Test
    public void testDeleteOnlineById() {
        // Setup
        // Configure SysUserOnlineManager.selectOnlineById(...).
        final SysUserOnlineDO sysUserOnlineDO = new SysUserOnlineDO();
        sysUserOnlineDO.setSessionId("sessionId");
        sysUserOnlineDO.setDeptName("deptName");
        sysUserOnlineDO.setLoginName("loginName");
        sysUserOnlineDO.setIpaddr("ipaddr");
        sysUserOnlineDO.setLoginLocation("loginLocation");
        sysUserOnlineDO.setBrowser("browser");
        sysUserOnlineDO.setOs("os");
        sysUserOnlineDO.setStartTimestamp(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserOnlineDO.setLastAccessTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserOnlineDO.setExpireTime(0L);
        when(mockSysUserOnlineManager.selectOnlineById("sessionId")).thenReturn(sysUserOnlineDO);

        when(mockSysUserOnlineManager.deleteOnlineById("sessionId")).thenReturn(0);

        // Run the test
        sysUserOnlineServiceImplUnderTest.deleteOnlineById("sessionId");

        // Verify the results
        verify(mockSysUserOnlineManager).deleteOnlineById("sessionId");
    }

    @Test
    public void testBatchDeleteOnline() {
        // Setup
        // Configure SysUserOnlineManager.selectOnlineById(...).
        final SysUserOnlineDO sysUserOnlineDO = new SysUserOnlineDO();
        sysUserOnlineDO.setSessionId("sessionId");
        sysUserOnlineDO.setDeptName("deptName");
        sysUserOnlineDO.setLoginName("loginName");
        sysUserOnlineDO.setIpaddr("ipaddr");
        sysUserOnlineDO.setLoginLocation("loginLocation");
        sysUserOnlineDO.setBrowser("browser");
        sysUserOnlineDO.setOs("os");
        sysUserOnlineDO.setStartTimestamp(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserOnlineDO.setLastAccessTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserOnlineDO.setExpireTime(0L);

        when(mockSysUserOnlineManager.deleteOnlineById("sessionId")).thenReturn(0);

        // Run the test
        sysUserOnlineServiceImplUnderTest.batchDeleteOnline(Arrays.asList("value"));

    }

    @Test
    public void testSaveOnline() {
        // Setup
        final SysUserOnline online = new SysUserOnline();
        online.setSessionId("sessionId");
        online.setDeptName("deptName");
        online.setLoginName("loginName");
        online.setIpaddr("ipaddr");
        online.setLoginLocation("loginLocation");
        online.setBrowser("browser");
        online.setOs("os");
        online.setStartTimestamp(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        online.setLastAccessTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        online.setExpireTime(0L);

        when(mockSysUserOnlineManager.saveOnline(any(SysUserOnlineDO.class))).thenReturn(0);

        // Run the test
        sysUserOnlineServiceImplUnderTest.saveOnline(online);

        // Verify the results
        verify(mockSysUserOnlineManager).saveOnline(any(SysUserOnlineDO.class));
    }

    @Test
    public void testSelectUserOnlineList() {
        // Setup
        final SysUserOnline userOnline = new SysUserOnline();
        userOnline.setSessionId("sessionId");
        userOnline.setDeptName("deptName");
        userOnline.setLoginName("loginName");
        userOnline.setIpaddr("ipaddr");
        userOnline.setLoginLocation("loginLocation");
        userOnline.setBrowser("browser");
        userOnline.setOs("os");
        userOnline.setStartTimestamp(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        userOnline.setLastAccessTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        userOnline.setExpireTime(0L);

        // Configure SysUserOnlineManager.selectUserOnlineList(...).
        final SysUserOnlineDO sysUserOnlineDO = new SysUserOnlineDO();
        sysUserOnlineDO.setSessionId("sessionId");
        sysUserOnlineDO.setDeptName("deptName");
        sysUserOnlineDO.setLoginName("loginName");
        sysUserOnlineDO.setIpaddr("ipaddr");
        sysUserOnlineDO.setLoginLocation("loginLocation");
        sysUserOnlineDO.setBrowser("browser");
        sysUserOnlineDO.setOs("os");
        sysUserOnlineDO.setStartTimestamp(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserOnlineDO.setLastAccessTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserOnlineDO.setExpireTime(0L);
        final List<SysUserOnlineDO> sysUserOnlineDOS = Arrays.asList(sysUserOnlineDO);
        when(mockSysUserOnlineManager.selectUserOnlineList(any(SysUserOnline.class))).thenReturn(sysUserOnlineDOS);

        // Run the test
        final List<SysUserOnline> result = sysUserOnlineServiceImplUnderTest.selectUserOnlineList(userOnline);

        // Verify the results
    }

    @Test
    public void testSelectUserOnlineList_SysUserOnlineManagerReturnsNoItems() {
        // Setup
        final SysUserOnline userOnline = new SysUserOnline();
        userOnline.setSessionId("sessionId");
        userOnline.setDeptName("deptName");
        userOnline.setLoginName("loginName");
        userOnline.setIpaddr("ipaddr");
        userOnline.setLoginLocation("loginLocation");
        userOnline.setBrowser("browser");
        userOnline.setOs("os");
        userOnline.setStartTimestamp(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        userOnline.setLastAccessTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        userOnline.setExpireTime(0L);

        when(mockSysUserOnlineManager.selectUserOnlineList(any(SysUserOnline.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUserOnline> result = sysUserOnlineServiceImplUnderTest.selectUserOnlineList(userOnline);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testForceLogout() {
        // Setup
        when(mockSysUserOnlineManager.deleteOnlineById("sessionId")).thenReturn(0);

        // Run the test
        sysUserOnlineServiceImplUnderTest.forceLogout("sessionId");

        // Verify the results
        verify(mockSysUserOnlineManager).deleteOnlineById("sessionId");
    }

    @Test
    public void testSelectOnlineByExpired() {
        // Setup
        // Configure SysUserOnlineManager.selectOnlineByExpired(...).
        final SysUserOnlineDO sysUserOnlineDO = new SysUserOnlineDO();
        sysUserOnlineDO.setSessionId("sessionId");
        sysUserOnlineDO.setDeptName("deptName");
        sysUserOnlineDO.setLoginName("loginName");
        sysUserOnlineDO.setIpaddr("ipaddr");
        sysUserOnlineDO.setLoginLocation("loginLocation");
        sysUserOnlineDO.setBrowser("browser");
        sysUserOnlineDO.setOs("os");
        sysUserOnlineDO.setStartTimestamp(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserOnlineDO.setLastAccessTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        sysUserOnlineDO.setExpireTime(0L);
        final List<SysUserOnlineDO> sysUserOnlineDOS = Arrays.asList(sysUserOnlineDO);
        when(mockSysUserOnlineManager.selectOnlineByExpired("lastAccessTime")).thenReturn(sysUserOnlineDOS);

        // Run the test
        final List<SysUserOnline> result = sysUserOnlineServiceImplUnderTest.selectOnlineByExpired(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
    }

    @Test
    public void testSelectOnlineByExpired_SysUserOnlineManagerReturnsNoItems() {
        // Setup

        // Run the test
        final List<SysUserOnline> result = sysUserOnlineServiceImplUnderTest.selectOnlineByExpired(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
