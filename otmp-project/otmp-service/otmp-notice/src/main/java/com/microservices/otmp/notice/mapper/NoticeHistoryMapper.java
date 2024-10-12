package com.microservices.otmp.notice.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.notice.dto.NoticeHistoryDto;
import com.microservices.otmp.notice.dto.NoticeHistoryManageDto;
import com.microservices.otmp.notice.param.NoticeHistoryParam;

import java.util.List;

public interface NoticeHistoryMapper extends BaseMapper<NoticeHistoryDto> {
    List<NoticeHistoryDto> selectNoticeHistoryListByUserId(NoticeHistoryParam noticeHistoryParam);

    Integer selectNoticeHistoryCountByUserId(NoticeHistoryParam noticeHistoryParam);

    List<NoticeHistoryManageDto> selectManageNoticeHistoryList(NoticeHistoryParam noticeHistoryParam);

    Integer selectManageNoticeHistoryCount(NoticeHistoryParam noticeHistoryParam);
}
