package com.microservices.otmp.notice.manager;

import com.microservices.otmp.notice.dto.NoticeHistoryUserDto;

import java.util.List;

public interface NoticeHistoryUserManager {
    int insert(NoticeHistoryUserDto noticeHistoryUserDto);

    int update(NoticeHistoryUserDto noticeHistoryUserDto);

    List<NoticeHistoryUserDto> selectByUserId(String userId);
}
