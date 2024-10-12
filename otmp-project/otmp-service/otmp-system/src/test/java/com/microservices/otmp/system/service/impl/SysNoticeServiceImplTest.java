package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.system.domain.SysNotice;
import com.microservices.otmp.system.domain.entity.SysNoticeDO;
import com.microservices.otmp.system.manager.SysNoticeManager;
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
public class SysNoticeServiceImplTest {

    @Mock
    private SysNoticeManager mockSysNoticeManager;

    @InjectMocks
    private SysNoticeServiceImpl sysNoticeServiceImplUnderTest;

    @Test
    public void testSelectNoticeById() {
        // Setup
        // Configure SysNoticeManager.selectNoticeById(...).
        final SysNoticeDO sysNoticeDO = new SysNoticeDO();
        sysNoticeDO.setNoticeId(0L);
        sysNoticeDO.setNoticeTitle("noticeTitle");
        sysNoticeDO.setNoticeType("noticeType");
        sysNoticeDO.setNoticeContent("noticeContent");
        sysNoticeDO.setStatus("status");
        when(mockSysNoticeManager.selectNoticeById(0L)).thenReturn(sysNoticeDO);

        // Run the test
        final SysNotice result = sysNoticeServiceImplUnderTest.selectNoticeById(0L);

        // Verify the results
    }

    @Test
    public void testSelectNoticeList() {
        // Setup
        final SysNotice notice = new SysNotice();
        notice.setNoticeId(0L);
        notice.setNoticeTitle("noticeTitle");
        notice.setNoticeType("noticeType");
        notice.setNoticeContent("noticeContent");
        notice.setStatus("status");

        // Configure SysNoticeManager.selectNoticeList(...).
        final SysNoticeDO sysNoticeDO = new SysNoticeDO();
        sysNoticeDO.setNoticeId(0L);
        sysNoticeDO.setNoticeTitle("noticeTitle");
        sysNoticeDO.setNoticeType("noticeType");
        sysNoticeDO.setNoticeContent("noticeContent");
        sysNoticeDO.setStatus("status");
        final List<SysNoticeDO> sysNoticeDOS = Arrays.asList(sysNoticeDO);
        when(mockSysNoticeManager.selectNoticeList(any(SysNotice.class))).thenReturn(sysNoticeDOS);

        // Run the test
        final List<SysNotice> result = sysNoticeServiceImplUnderTest.selectNoticeList(notice);

        // Verify the results
    }

    @Test
    public void testSelectNoticeList_SysNoticeManagerReturnsNoItems() {
        // Setup
        final SysNotice notice = new SysNotice();
        notice.setNoticeId(0L);
        notice.setNoticeTitle("noticeTitle");
        notice.setNoticeType("noticeType");
        notice.setNoticeContent("noticeContent");
        notice.setStatus("status");

        when(mockSysNoticeManager.selectNoticeList(any(SysNotice.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysNotice> result = sysNoticeServiceImplUnderTest.selectNoticeList(notice);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertNotice() {
        // Setup
        final SysNotice notice = new SysNotice();
        notice.setNoticeId(0L);
        notice.setNoticeTitle("noticeTitle");
        notice.setNoticeType("noticeType");
        notice.setNoticeContent("noticeContent");
        notice.setStatus("status");

        when(mockSysNoticeManager.insertNotice(any(SysNoticeDO.class))).thenReturn(0);

        // Run the test
        final int result = sysNoticeServiceImplUnderTest.insertNotice(notice);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateNotice() {
        // Setup
        final SysNotice notice = new SysNotice();
        notice.setNoticeId(0L);
        notice.setNoticeTitle("noticeTitle");
        notice.setNoticeType("noticeType");
        notice.setNoticeContent("noticeContent");
        notice.setStatus("status");

        when(mockSysNoticeManager.updateNotice(any(SysNoticeDO.class))).thenReturn(0);

        // Run the test
        final int result = sysNoticeServiceImplUnderTest.updateNotice(notice);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteNoticeByIds() {
        // Setup
        when(mockSysNoticeManager.deleteNoticeByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = sysNoticeServiceImplUnderTest.deleteNoticeByIds("ids");

        // Verify the results
        assertEquals(0, result);
    }
}
