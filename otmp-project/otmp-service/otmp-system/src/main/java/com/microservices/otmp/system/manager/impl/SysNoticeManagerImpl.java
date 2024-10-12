package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysNotice;
import com.microservices.otmp.system.domain.entity.SysNoticeDO;
import com.microservices.otmp.system.manager.SysNoticeManager;
import com.microservices.otmp.system.mapper.SysNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:21
 */

@Service
public class SysNoticeManagerImpl implements SysNoticeManager {

    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    @Override
    public SysNoticeDO selectNoticeById(Long noticeId) {
        return sysNoticeMapper.selectNoticeById(noticeId);
    }

    @Override
    public List<SysNoticeDO> selectNoticeList(SysNotice notice) {
        return sysNoticeMapper.selectNoticeList(notice);
    }

    @Override
    public int insertNotice(SysNoticeDO notice) {
        return sysNoticeMapper.insertNotice(notice);
    }

    @Override
    public int updateNotice(SysNoticeDO notice) {
        return sysNoticeMapper.updateNotice(notice);
    }

    @Override
    public int deleteNoticeByIds(String[] noticeIds) {
        return sysNoticeMapper.deleteNoticeByIds(noticeIds);
    }
}
