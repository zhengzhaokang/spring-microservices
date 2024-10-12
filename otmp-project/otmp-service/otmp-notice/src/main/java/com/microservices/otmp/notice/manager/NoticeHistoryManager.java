package com.microservices.otmp.notice.manager;

import com.microservices.otmp.notice.domain.entity.NoticeSendKafka;
import com.microservices.otmp.notice.dto.NoticeHistoryDto;

import java.util.List;

public interface NoticeHistoryManager {
    int insertNoticeHistory(NoticeHistoryDto noticeHistoryDto);

    int updateNoticeHistory(NoticeHistoryDto noticeHistoryDto);

    NoticeHistoryDto getNoticeHistory(Long id);

    int deleteNoticeHistory(List<Long> ids);

    void noticeHistoryToDb(NoticeSendKafka noticeSendKafka);
}
