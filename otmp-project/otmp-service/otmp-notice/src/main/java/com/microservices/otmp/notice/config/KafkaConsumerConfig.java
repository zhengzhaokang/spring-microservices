package com.microservices.otmp.notice.config;


import com.microservices.otmp.common.kafka.consumer.KafkaConsumerYmlConfig;
import com.microservices.otmp.notice.common.KafkaFactory;
import com.microservices.otmp.notice.common.KafkaFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
public class KafkaConsumerConfig {

    @Autowired
    KafkaConsumerYmlConfig kafkaConsumerYmlConfig;

    @Bean(KafkaFactory.SEND_EMAIL_KAFKA_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> sendEmailKafkaFactory() {
        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig,KafkaFactory.SEND_EMAIL_KAFKA_FACTORY);
    }

    @Bean(KafkaFactory.SEND_CONSUMER_KAFKA_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> sendNoticeKafkaFactory() {
        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig,KafkaFactory.SEND_CONSUMER_KAFKA_FACTORY);
    }
}
