package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysNotice;
import com.microservices.otmp.system.domain.entity.SysNoticeDO;
import com.microservices.otmp.system.mapper.SysNoticeMapper;
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
public class SysNoticeManagerImplTest {

    @Mock
    private SysNoticeMapper mockSysNoticeMapper;

    @InjectMocks
    private SysNoticeManagerImpl sysNoticeManagerImplUnderTest;

    @Test
    public void testSelectNoticeById() {
        // Setup
        // Configure SysNoticeMapper.selectNoticeById(...).
        final SysNoticeDO sysNoticeDO = new SysNoticeDO();
        sysNoticeDO.setNoticeId(0L);
        sysNoticeDO.setNoticeTitle("noticeTitle");
        sysNoticeDO.setNoticeType("noticeType");
        sysNoticeDO.setNoticeContent("noticeContent");
        sysNoticeDO.setStatus("status");
        when(mockSysNoticeMapper.selectNoticeById(0L)).thenReturn(sysNoticeDO);

        // Run the test
        final SysNoticeDO result = sysNoticeManagerImplUnderTest.selectNoticeById(0L);

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

        // Configure SysNoticeMapper.selectNoticeList(...).
        final SysNoticeDO sysNoticeDO = new SysNoticeDO();
        sysNoticeDO.setNoticeId(0L);
        sysNoticeDO.setNoticeTitle("noticeTitle");
        sysNoticeDO.setNoticeType("noticeType");
        sysNoticeDO.setNoticeContent("noticeContent");
        sysNoticeDO.setStatus("status");
        final List<SysNoticeDO> sysNoticeDOS = Arrays.asList(sysNoticeDO);
        when(mockSysNoticeMapper.selectNoticeList(any(SysNotice.class))).thenReturn(sysNoticeDOS);

        // Run the test
        final List<SysNoticeDO> result = sysNoticeManagerImplUnderTest.selectNoticeList(notice);

        // Verify the results
    }

    @Test
    public void testSelectNoticeList_SysNoticeMapperReturnsNoItems() {
        // Setup
        final SysNotice notice = new SysNotice();
        notice.setNoticeId(0L);
        notice.setNoticeTitle("noticeTitle");
        notice.setNoticeType("noticeType");
        notice.setNoticeContent("noticeContent");
        notice.setStatus("status");

        when(mockSysNoticeMapper.selectNoticeList(any(SysNotice.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysNoticeDO> result = sysNoticeManagerImplUnderTest.selectNoticeList(notice);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertNotice() {
        // Setup
        final SysNoticeDO notice = new SysNoticeDO();
        notice.setNoticeId(0L);
        notice.setNoticeTitle("noticeTitle");
        notice.setNoticeType("noticeType");
        notice.setNoticeContent("noticeContent");
        notice.setStatus("status");

        when(mockSysNoticeMapper.insertNotice(any(SysNoticeDO.class))).thenReturn(0);

        // Run the test
        final int result = sysNoticeManagerImplUnderTest.insertNotice(notice);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateNotice() {
        // Setup
        final SysNoticeDO notice = new SysNoticeDO();
        notice.setNoticeId(0L);
        notice.setNoticeTitle("noticeTitle");
        notice.setNoticeType("noticeType");
        notice.setNoticeContent("noticeContent");
        notice.setStatus("status");

        when(mockSysNoticeMapper.updateNotice(any(SysNoticeDO.class))).thenReturn(0);

        // Run the test
        final int result = sysNoticeManagerImplUnderTest.updateNotice(notice);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteNoticeByIds() {
        // Setup
        when(mockSysNoticeMapper.deleteNoticeByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = sysNoticeManagerImplUnderTest.deleteNoticeByIds(new String[]{"value"});

        // Verify the results
        assertEquals(0, result);
    }
}
