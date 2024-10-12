package com.microservices.otmp.notice.listener;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.notice.common.KafkaFactory;
import com.microservices.otmp.notice.common.KafkaGroup;
import com.microservices.otmp.notice.domain.dto.EmailSendDTO;
import com.microservices.otmp.notice.service.NoticeHistoryService;
import com.microservices.otmp.notice.service.NoticeService;
import com.microservices.otmp.notice.vo.NoticeSendVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NoticeListener {

    private static final String KAFKA_LISTENER_ID = "sendNotice";

    @Autowired
    private NoticeHistoryService noticeHistoryService;
    @KafkaListener(containerFactory = KafkaFactory.SEND_CONSUMER_KAFKA_FACTORY, topics = {"${kafka.topic.notice}"}, groupId = KafkaGroup.OTMP_SVC, id = KAFKA_LISTENER_ID)
    public void listen(ConsumerRecord<?, ?> consumerRecord, Acknowledgment ack) {
        String value = (String) consumerRecord.value();
        log.info("send notice 接收参数：{}", value);
        try{
            NoticeSendVO sendRequests = JSON.parseObject(value, NoticeSendVO.class);
            noticeHistoryService.insertNoticeHistory(sendRequests);
            //手动提交
            ack.acknowledge();
        }catch (Exception e){
            // kafkaLogListener.consumerErrorRecordLog(consumerRecord,e,ack);
            log.error("NoticeListener consumer error",e);
        }
    }
}
