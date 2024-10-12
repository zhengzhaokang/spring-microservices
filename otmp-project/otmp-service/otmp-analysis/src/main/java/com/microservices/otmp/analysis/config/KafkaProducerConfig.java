package com.microservices.otmp.analysis.config;

import com.microservices.otmp.common.kafka.producer.KafkaProducerYmlConfig;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;

/**
 * @author guowb1
 * @date 2022/11/17 11:17
 */

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
        addFactory(KafkaInfoConstant.ACCRUAL_IKT_KAFKA_PRODUCER_FACTORY);
    }


    private void addFactory(String producerFactory) {
        kafkaSend.getKafkaTemplateListMap().put(producerFactory,
                new KafkaTemplate<>(kafkaProducerYmlConfig.producerFactory(kafkaProducerYmlConfig, producerFactory)));
    }


}
