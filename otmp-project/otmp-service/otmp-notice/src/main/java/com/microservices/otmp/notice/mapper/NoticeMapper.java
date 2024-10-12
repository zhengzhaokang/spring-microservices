package com.microservices.otmp.notice.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.notice.dto.NoticeDto;

import java.util.List;

public interface NoticeMapper extends BaseMapper<NoticeDto> {
    public List<NoticeDto> selectNoticeList(NoticeDto notice);
}
