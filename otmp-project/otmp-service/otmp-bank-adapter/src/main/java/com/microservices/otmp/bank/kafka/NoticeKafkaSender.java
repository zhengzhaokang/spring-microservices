package com.microservices.otmp.bank.kafka;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.bank.domain.dto.BankTransferInvoiceResponseInfoDTO;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.bank.common.KafkaFactory;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.microservices.otmp.bank.common.KafkaFactory.*;

@Service
@Slf4j
public class NoticeKafkaSender {

    @Autowired
    private KafkaSend kafkaSend;

    @Value("${kafka.topic.msg-remind}")
    private String msgRemindTopic;

    @Value("${kafka.topic.send-email}")
    private String sendEmailTopic;

    /**
     * * 发送消息
     *
     * @param paramMap
     */
    public void sendEmailData(Map<String, String> paramMap, List<String> sendToList, List<String> sendCcList) {
        if (null == paramMap) {
            return;
        }
        EmailSendDTO mail = new EmailSendDTO();
        mail.setTraceId(UUID.randomUUID().toString());
        mail.setCreateBy("OTFP SYSTEM BANK ADAPTER");
        mail.setMailFrom("OTFP SYSTEM BANK ADAPTER");
        String emailType = paramMap.get("emailType");
        mail.setBusinessType(paramMap.get("businessType"));
        mail.setEmailType(emailType);
        if (CollectionUtils.isNotEmpty(sendToList)) {
            mail.setSendTo(sendToList);
        }
        if (CollectionUtils.isNotEmpty(sendCcList)) {
            mail.setSendCc(sendCcList);
        }
        mail.setBean(paramMap.get("bean"));
        mail.setMailTitleBean(paramMap.get("mailTitleBean"));
        mail.setGeoCode("ALL");
        mail.setBusinessGroup("ALL");
        mail.setModule(paramMap.get("module"));
        String resetStr = JSON.toJSONString(mail);
        log.info("发送{},EmailData消息 :{}", emailType, resetStr);
        kafkaSend.asynSend(sendEmailTopic, UUID.randomUUID().toString(), resetStr, KafkaFactory.SEND_EMAIL_KAFKA_PRODUCER_FACTORY, new KafkaCallback(sendEmailTopic), false);
    }


    /**
     * * 发送消息
     *
     * @param paramMap
     */
    public void sendMsgRemindData(Map<String, String> paramMap) {
        if (null == paramMap) {
            return;
        }
        NoticeSendVO noticeSendVO = new NoticeSendVO();
        noticeSendVO.setTraceId(UUID.randomUUID().toString());
        noticeSendVO.setCreateBy("zhoudan13");
        noticeSendVO.setNoticeType("SubmitError");
        noticeSendVO.setSendTo(Arrays.asList("184", "9", "58"));
        noticeSendVO.setSendCc(Arrays.asList("184", "9", "58"));
        noticeSendVO.setTitleBean("{\"BatchNumber\":\"BNPP-1212001\"}");
        noticeSendVO.setBean("{\"BatchNumber\":\"BNPP-1212001\",\"Amount\":\"1233.11\"}");
        noticeSendVO.setUniqueId(SnowFlakeUtil.nextId() + "");
        String resetStr = JSON.toJSONString(noticeSendVO);
        log.info("发送msgRemindTData消息 == {}", resetStr);
        String msgKey = SnowFlakeUtil.nextId() + "";
        kafkaSend.asynSend(msgRemindTopic, msgKey, resetStr, KafkaFactory.MSG_KAFKA_PRODUCER_FACTORY, new KafkaCallback(msgRemindTopic), false);
    }


}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class NoticeSendVO {
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class EmailSendDTO {
    private List<String> uniqueIds;
    private String uniqueId;
    @NotNull
    private String emailType;
    private String geoCode;
    private String createBy;
    private String market;
    private String businessGroup;
    private String attachment;
    private List<String> sendTo;
    private List<String> sendCc;
    private String mailFrom;
    private String traceId;
    private String businessType;
    private String module;
    private String bean;
    private Map<String, String> tableParamsForBean;
    private String mailTitleBean;

}
