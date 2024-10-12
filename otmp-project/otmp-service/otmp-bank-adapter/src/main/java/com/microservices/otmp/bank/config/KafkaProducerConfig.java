package com.microservices.otmp.bank.config;


import com.microservices.otmp.bank.common.KafkaFactory;
import com.microservices.otmp.common.kafka.producer.KafkaProducerYmlConfig;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;

@Configuration
public class KafkaProducerConfig {
    @Autowired
    private KafkaSend kafkaSend;
    @Autowired
    KafkaProducerYmlConfig kafkaProducerYmlConfig;

    /*
     * 新建生产消息工厂, 加addFactory即可
     * */
    @PostConstruct
    public void setKafkaTemplate() {
        addFactory(KafkaFactory.BANK_INVOICE_TRANSFER_PRODUCER_FACTORY);
        addFactory(KafkaFactory.SEND_EMAIL_KAFKA_PRODUCER_FACTORY);
        addFactory(KafkaFactory.MSG_KAFKA_PRODUCER_FACTORY);
    }

    private void addFactory(String producerFactory) {
        kafkaSend.getKafkaTemplateListMap().put(producerFactory,
                new KafkaTemplate<>(kafkaProducerYmlConfig.producerFactory(kafkaProducerYmlConfig, producerFactory)));
    }

}
