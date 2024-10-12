package com.microservices.otmp.financing.domain.dto;

import com.microservices.otmp.common.utils.SnowFlakeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeSendDto {

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

    public NoticeSendDto(String type,String createBy,String title,String content,List<String> sendTo,List<String> sendCc){
        this.uniqueId = UUID.randomUUID().toString();
        this.traceId = String.valueOf(SnowFlakeUtil.nextId());
        this.noticeType = type;
        this.createBy = createBy;
        this.titleBean = title;
        this.bean = content;
        this.sendTo = sendTo;
        this.sendCc = sendCc;
    }

}
