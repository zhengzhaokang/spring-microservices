package com.microservices.otmp.bank.kafka;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.bank.domain.dto.BankTransferInvoiceResponseInfoDTO;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.microservices.otmp.bank.common.KafkaFactory.BANK_INVOICE_TRANSFER_PRODUCER_FACTORY;

@Service
@Slf4j
public class BankInvoiceTransferKafkaSender {

    @Autowired
    private KafkaSend kafkaSend;

    @Value("${kafka.topic.bank-invoice-transfer}")
    private String bankInvoiceTransferTopic;

    /**
     * * 发送消息
     *
     * @param bankTransferInvoiceResponseInfoDTO
     */
    public void sendBankInvoiceTransferData(BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfoDTO) {
        if (null == bankTransferInvoiceResponseInfoDTO) {
            return;
        }
        String resetStr = JSON.toJSONString(bankTransferInvoiceResponseInfoDTO);
        log.info("发送BankInvoiceTransferData消息 == {}", resetStr);
        String msgKey = bankTransferInvoiceResponseInfoDTO.getId().toString();
        kafkaSend.asynSend(bankInvoiceTransferTopic, msgKey, resetStr, BANK_INVOICE_TRANSFER_PRODUCER_FACTORY, new KafkaCallback(bankInvoiceTransferTopic), false);
    }


    /**
     * * 发送消息
     *
     * @param bankTransferInvoiceResponseInfoMap
     */
    public void sendBankInvoiceTransferMapData(Map<String,Object> bankTransferInvoiceResponseInfoMap) {
        if (null == bankTransferInvoiceResponseInfoMap) {
            return;
        }
        String resetStr = JSON.toJSONString(bankTransferInvoiceResponseInfoMap);
        log.info("发送BankInvoiceTransferDataMap消息 == {}", resetStr);
        String msgKey = bankTransferInvoiceResponseInfoMap.get("transferId").toString();
        log.info("发送BankInvoiceTransferDataMap消息 msgKey== {}", msgKey);
        kafkaSend.asynSend(bankInvoiceTransferTopic, msgKey, resetStr, BANK_INVOICE_TRANSFER_PRODUCER_FACTORY, new KafkaCallback(bankInvoiceTransferTopic), false);
    }



}
