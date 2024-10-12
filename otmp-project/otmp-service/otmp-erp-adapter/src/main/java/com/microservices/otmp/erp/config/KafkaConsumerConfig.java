package com.microservices.otmp.erp.config;


import com.microservices.otmp.common.kafka.consumer.KafkaConsumerYmlConfig;
import com.microservices.otmp.common.kafka.consumer.KafkaConsumerYmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
public class KafkaConsumerConfig {

    @Autowired
    KafkaConsumerYmlConfig kafkaConsumerYmlConfig;

    @Bean(KafkaFactory.SUBMISSION_KAFKA_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig,KafkaFactory.SUBMISSION_KAFKA_FACTORY);
    }

    @Bean(KafkaFactory.TRANSFER_KAFKA_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaAsyncListenerContainerFactory() {
        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig,KafkaFactory.TRANSFER_KAFKA_FACTORY);
    }

}
