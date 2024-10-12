package com.microservices.otmp.notice.manager.impl;

import com.microservices.otmp.notice.dto.NoticeHistoryUserDto;
import com.microservices.otmp.notice.manager.NoticeHistoryUserManager;
import com.microservices.otmp.notice.mapper.NoticeHistoryUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Component
public class NoticeHistoryUserManagerImpl implements NoticeHistoryUserManager {


    @Autowired
    private NoticeHistoryUserMapper noticeHistoryUserMapper;
    @Override
    public int insert(NoticeHistoryUserDto noticeHistoryUserDto) {
        return noticeHistoryUserMapper.insert(noticeHistoryUserDto);
    }

    @Override
    public int update(NoticeHistoryUserDto noticeHistoryUserDto) {
        Example example = new Example(NoticeHistoryUserDto.class);
        example.createCriteria().andEqualTo("userId", noticeHistoryUserDto.getUserId())
                .andEqualTo("noticeHistoryId", noticeHistoryUserDto.getNoticeHistoryId());
        NoticeHistoryUserDto record = new NoticeHistoryUserDto();
        record.setStatus(noticeHistoryUserDto.getStatus());

        return noticeHistoryUserMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<NoticeHistoryUserDto> selectByUserId(String userId) {
        Example example = new Example(NoticeHistoryUserDto.class);
        example.createCriteria().andEqualTo("userId", userId);

        return noticeHistoryUserMapper.selectByExample(example);
    }
}
