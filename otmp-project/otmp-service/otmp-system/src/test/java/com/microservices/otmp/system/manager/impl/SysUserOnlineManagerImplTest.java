package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysUserOnline;
import com.microservices.otmp.system.domain.entity.SysUserOnlineDO;
import com.microservices.otmp.system.mapper.SysUserOnlineMapper;
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
public class SysUserOnlineManagerImplTest {

    @Mock
    private SysUserOnlineMapper mockSysUserOnlineMapper;

    @InjectMocks
    private SysUserOnlineManagerImpl sysUserOnlineManagerImplUnderTest;

    @Test
    public void testSelectOnlineById() {
        // Setup
        // Configure SysUserOnlineMapper.selectOnlineById(...).
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
        when(mockSysUserOnlineMapper.selectOnlineById("sessionId")).thenReturn(sysUserOnlineDO);

        // Run the test
        final SysUserOnlineDO result = sysUserOnlineManagerImplUnderTest.selectOnlineById("sessionId");

        // Verify the results
    }

    @Test
    public void testDeleteOnlineById() {
        // Setup
        when(mockSysUserOnlineMapper.deleteOnlineById("sessionId")).thenReturn(0);

        // Run the test
        final int result = sysUserOnlineManagerImplUnderTest.deleteOnlineById("sessionId");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSaveOnline() {
        // Setup
        final SysUserOnlineDO online = new SysUserOnlineDO();
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

        when(mockSysUserOnlineMapper.saveOnline(any(SysUserOnlineDO.class))).thenReturn(0);

        // Run the test
        final int result = sysUserOnlineManagerImplUnderTest.saveOnline(online);

        // Verify the results
        assertEquals(0, result);
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

        // Configure SysUserOnlineMapper.selectUserOnlineList(...).
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
        when(mockSysUserOnlineMapper.selectUserOnlineList(any(SysUserOnline.class))).thenReturn(sysUserOnlineDOS);

        // Run the test
        final List<SysUserOnlineDO> result = sysUserOnlineManagerImplUnderTest.selectUserOnlineList(userOnline);

        // Verify the results
    }

    @Test
    public void testSelectUserOnlineList_SysUserOnlineMapperReturnsNoItems() {
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

        when(mockSysUserOnlineMapper.selectUserOnlineList(any(SysUserOnline.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUserOnlineDO> result = sysUserOnlineManagerImplUnderTest.selectUserOnlineList(userOnline);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectOnlineByExpired() {
        // Setup
        // Configure SysUserOnlineMapper.selectOnlineByExpired(...).
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
        when(mockSysUserOnlineMapper.selectOnlineByExpired("lastAccessTime")).thenReturn(sysUserOnlineDOS);

        // Run the test
        final List<SysUserOnlineDO> result = sysUserOnlineManagerImplUnderTest.selectOnlineByExpired("lastAccessTime");

        // Verify the results
    }

    @Test
    public void testSelectOnlineByExpired_SysUserOnlineMapperReturnsNoItems() {
        // Setup
        when(mockSysUserOnlineMapper.selectOnlineByExpired("lastAccessTime")).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysUserOnlineDO> result = sysUserOnlineManagerImplUnderTest.selectOnlineByExpired("lastAccessTime");

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
