package com.microservices.otmp.financing.listener;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.common.constant.StateConstants;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.financing.config.KafkaFactory;
import com.microservices.otmp.financing.domain.dto.BankTransferDto;
import com.microservices.otmp.financing.mapper.InvoiceSubmitRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@Component
public class BankTransferListener {

    private static final String KAFKA_LISTENER_ID = "bankTransferListenerFinancing";
    private static final String GROUP_ID = "otfp.finance";
    private static final String STATUS_REJECTED = "Rejected";

    @Autowired
    private InvoiceSubmitRecordMapper invoiceSubmitRecordMapper;

    @KafkaListener(containerFactory = KafkaFactory.BANK_TRANSFER_FACTORY
            , topics = {"${kafka.topic.bank-transfer}"}
            , groupId = GROUP_ID
            , id = KAFKA_LISTENER_ID
            , properties = {"fetch.max.bytes:5242880"})
    public void listen(ConsumerRecord<?, ?> consumerRecord, Acknowledgment ack) {
        String value = (String) consumerRecord.value();
        log.info("bank transfer 接收参数：{}", value);
        try {
            BankTransferDto dto = JSON.parseObject(value, BankTransferDto.class);
            if(STATUS_REJECTED.equals(dto.getStatus())){
                invoiceSubmitRecordMapper.updateStatusByBatch(dto.getMicroservicesBatchNumber()
                        , StateConstants.FLAG_NORMAL_STR
                        , StateConstants.FLAG_DELETED_STR
                        , LocalDateTime.now()
                        , "system");
            }
        } catch (Exception e) {
            log.error("BankTransferListener update error,dto:{}", value);
            log.error("BankTransferListener consumer error",e);
        } finally {
            ack.acknowledge();
        }
    }

}
