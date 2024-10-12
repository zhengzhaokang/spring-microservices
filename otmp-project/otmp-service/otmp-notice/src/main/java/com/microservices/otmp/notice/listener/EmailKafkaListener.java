package com.microservices.otmp.notice.listener;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.notice.common.KafkaFactory;
import com.microservices.otmp.notice.common.KafkaGroup;
import com.microservices.otmp.notice.domain.dto.EmailSendDTO;
import com.microservices.otmp.notice.service.EmailSendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class EmailKafkaListener {

    private static final String KAFKA_LISTENER_ID = "sendEmail";

    @Autowired
    public EmailSendService emailSendService;
    @KafkaListener(containerFactory = KafkaFactory.SEND_EMAIL_KAFKA_FACTORY, topics = {"${kafka.topic.send-email}"}, groupId = KafkaGroup.OTMP_SVC, id = KAFKA_LISTENER_ID)
    public void listen(ConsumerRecord<?, ?> consumerRecord, Acknowledgment ack) {
        long start = System.currentTimeMillis();
        String value = (String) consumerRecord.value();
        log.info("send email 接收参数：{}", value);
        try{
            EmailSendDTO sendRequests = JSON.parseObject(value, EmailSendDTO.class);
            emailSendService.joinParameters(sendRequests);
            log.info("### EmailKafkaListener send email 耗时：{}", System.currentTimeMillis() - start);
            // 手动提交
            ack.acknowledge();
        }catch (Exception e){
//            kafkaLogListener.consumerErrorRecordLog(consumerRecord,e,ack);
            log.error("EmailKafkaListener consumer error",e);
        }
    }
}
