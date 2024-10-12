package com.microservices.otmp.notice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeSendVO {
    private List<String> uniqueIds;
    private String uniqueId;
    @NotNull
    private String noticeType;

    private String createBy;
    private String remark;
    private List<String> sendTo;
    private List<String> sendCc;
    private String traceId;


    private String bean;
    private String titleBean;

}
