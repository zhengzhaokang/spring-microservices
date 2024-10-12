package com.microservices.otmp.notice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoticeHistoryData {
    private Integer count;
    private List<NoticeHistoryVO> data;
}
