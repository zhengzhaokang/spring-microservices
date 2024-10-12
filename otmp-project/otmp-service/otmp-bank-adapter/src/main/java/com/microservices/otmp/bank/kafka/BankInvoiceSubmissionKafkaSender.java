package com.microservices.otmp.bank.kafka;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.bank.domain.dto.BankTransferInvoiceResponseInfoDTO;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.microservices.otmp.bank.common.KafkaFactory.BANK_INVOICE_TRANSFER_PRODUCER_FACTORY;

@Service
@Slf4j
public class BankInvoiceSubmissionKafkaSender {

    @Autowired
    private KafkaSend kafkaSend;

    @Value("${kafka.topic.bank-invoice-submission}")
    private String bankInvoiceSubmissionTopic;

    /**
     * * 发送消息
     *
     * @param bankTransferInvoiceResponseInfoDTO
     */
    public void sendBankInvoiceSubmissionData(BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfoDTO) {
        if (null == bankTransferInvoiceResponseInfoDTO) {
            return;
        }
        String resetStr = JSON.toJSONString(bankTransferInvoiceResponseInfoDTO);
        log.info("Test 发送BankInvoiceSubmissionData消息 == {}", resetStr);
        String msgKey = bankTransferInvoiceResponseInfoDTO.getId().toString();
        kafkaSend.asynSend(bankInvoiceSubmissionTopic, msgKey, resetStr, BANK_INVOICE_TRANSFER_PRODUCER_FACTORY, new KafkaCallback(bankInvoiceSubmissionTopic), false);
    }


}
