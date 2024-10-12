package com.microservices.otmp.bank.kafka.listener;

import cn.hutool.core.collection.CollectionUtil;
import com.microservices.otmp.bank.common.KafkaFactory;
import com.microservices.otmp.bank.common.KafkaGroup;
import com.microservices.otmp.bank.domain.req.BatchReq;
import com.microservices.otmp.bank.service.BankService;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.finance.domain.dto.FinanceBatchDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author lovefamily
 * @data 2023年10月11日 15:31:37
 * @发票提交银行KAFKA监听
 */
@Slf4j
@Component
public class BankInvoiceSubmissionListener {
    private static final String KAFKA_LISTENER_ID = "submissionInvoice";

    @Autowired
    private BankService bankService;


    //@KafkaListener(topics = "#{'${consume.bank-invoice-submission.topic}'.split(',')}")
    @KafkaListener(containerFactory = KafkaFactory.BANK_INVOICE_SUBMISSION_FACTORY,
            topics = {"${kafka.topic.bank-invoice-submission}"},
            groupId = KafkaGroup.OTFP_GROUP, id = KAFKA_LISTENER_ID,
            concurrency = "2",
            properties = {"max.poll.interval.ms:300000", "heartbeat.interval.ms:1000","auto.commit.interval.ms:1000"}
    )
    public void listen(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("收到{}的消息{}", record.topic(), record.value());
        List<FinanceBatchDTO> financeBatchDTOList = JsonUtil.toList(record.value(), FinanceBatchDTO.class);
        submitFinanceBatch(financeBatchDTOList);
        ack.acknowledge();
    }

    private void submitFinanceBatch(List<FinanceBatchDTO> financeBatchDTOList) {
        if (CollectionUtil.isNotEmpty(financeBatchDTOList)) {
            log.info("SubmissionListener FinanceBatch is Start.");
            for (FinanceBatchDTO financeBatchDTO : financeBatchDTOList) {
                BatchReq batchReq = new BatchReq();
                batchReq.setBatchNo(financeBatchDTO.getBatchNumber());
                bankService.submit(batchReq);
            }
            log.info("SubmissionListener FinanceBatch is End.");
        }

    }


}
