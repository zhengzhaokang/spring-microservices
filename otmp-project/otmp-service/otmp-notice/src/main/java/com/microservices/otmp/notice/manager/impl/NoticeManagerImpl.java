package com.microservices.otmp.notice.manager.impl;

import com.microservices.otmp.notice.common.NoticeConstant;
import com.microservices.otmp.notice.dto.NoticeDto;
import com.microservices.otmp.notice.manager.NoticeManager;
import com.microservices.otmp.notice.mapper.NoticeMapper;
import com.microservices.otmp.notice.manager.NoticeManager;
import com.microservices.otmp.notice.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class NoticeManagerImpl implements NoticeManager {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public NoticeDto selectNoticeById(Long noticeId) {
        return noticeMapper.selectByPrimaryKey(noticeId);
    }

    @Override
    public List<NoticeDto> selectNoticeList(NoticeDto notice) {
        return noticeMapper.selectNoticeList(notice);
    }

    @Override
    public int insertNotice(NoticeDto notice) {
        noticeMapper.insert(notice);
        return 1;
    }

    @Override
    public int updateNotice(NoticeDto notice) {
        if (notice.getNoticeId() == null) {
            return 0;
        }
        notice.setStatus(NoticeConstant.STATUS_ENABLE);
        noticeMapper.updateByPrimaryKeySelective(notice);
        return 1;
    }

    @Override
    public int deleteNoticeByIds(String[] noticeIds) {
        for (String noticeId : noticeIds) {
            NoticeDto noticeDto = new NoticeDto();
            noticeDto.setNoticeId(Long.parseLong(noticeId));
            noticeDto.setStatus(NoticeConstant.STATUS_DISABLE);
            noticeDto.setUpdateTime(new Date());
            noticeMapper.updateByPrimaryKeySelective(noticeDto);
        }
        return 1;
    }
}
