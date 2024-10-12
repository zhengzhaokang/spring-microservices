package com.microservices.otmp.notice.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoticeHistoryParam {

    private String userId;

    private String status;

    private Integer limit;

    private Integer offset;

    private Integer noticePageSize;

    private Integer noticePageNum;

    private String noticeType;

    private String loginName;

    private String startTime;

    private String endTime;

    private String noticeHistoryId;

}
