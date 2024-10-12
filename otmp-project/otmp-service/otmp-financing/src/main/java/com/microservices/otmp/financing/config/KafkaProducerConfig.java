package com.microservices.otmp.financing.config;

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

    @Autowired
    KafkaProducerYmlConfig producerConfig;

    /*
     * 新建生产消息工厂, 加addFactory即可
     * */
    @PostConstruct
    public void setKafkaTemplate() {
        addFactory(KafkaFactory.MSG_KAFKA_PRODUCE_FACTORY);
        addFactory(KafkaFactory.INVOICE_BATCH_SUBMIT_FACTORY);
        addFactory(KafkaFactory.SEND_KAFKA_PRODUCER_FACTORY);
    }

    private void addFactory(String producerFactory) {
        kafkaSend.getKafkaTemplateListMap().put(producerFactory,
                new KafkaTemplate<>(producerConfig.producerFactory(kafkaProducerYmlConfig, producerFactory)));
    }

}
