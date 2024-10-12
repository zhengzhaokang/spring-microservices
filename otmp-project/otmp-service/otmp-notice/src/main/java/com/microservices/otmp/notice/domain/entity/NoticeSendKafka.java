package com.microservices.otmp.notice.domain.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeSendKafka {
    private Map<String, String> noticeDimension;
    private Map<String, String> noticeInfo;
    private NoticeParamEntity noticeParamEntity;
    private Map<String, List<String>> sendInfo;

    private JSONObject normalParams;
    private JSONObject titleBean;

    private String failReason;
}
