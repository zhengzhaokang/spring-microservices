package com.microservices.otmp.notice.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class NoticeParamEntity {
    private String subject;
    private String content;

    private List<String> to;
    private List<String> cc;
}
