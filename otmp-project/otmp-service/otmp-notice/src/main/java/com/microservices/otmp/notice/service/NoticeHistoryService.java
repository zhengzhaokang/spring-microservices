package com.microservices.otmp.notice.service;

import com.microservices.otmp.notice.param.NoticeHistoryParam;
import com.microservices.otmp.notice.vo.NoticeHistoryData;
import com.microservices.otmp.notice.vo.NoticeSendVO;
import org.springframework.web.bind.annotation.RequestBody;

public interface NoticeHistoryService {

    Integer insertNoticeHistory(NoticeSendVO noticeSendVO);

    NoticeHistoryData selectNoticeHistory(NoticeHistoryParam param);

    NoticeHistoryData selectManageNoticeHistory(NoticeHistoryParam param);

    void updateNoticeHistoryStatus(NoticeHistoryParam param);

    void batchUpdateNoticeHistoryStatus(NoticeHistoryParam param);
}
