package com.microservices.otmp.notice.dto;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "notice_history_user")
@Data
public class NoticeHistoryUserDto {

    @Id
    private Long id;

    private Long noticeHistoryId;

    private Long userId;

    private String status;
}
