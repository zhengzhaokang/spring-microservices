package com.microservices.otmp.notice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeHistoryUserVO {
    private Long noticeHistoryId;

    private Long userId;

    private String status;
}
