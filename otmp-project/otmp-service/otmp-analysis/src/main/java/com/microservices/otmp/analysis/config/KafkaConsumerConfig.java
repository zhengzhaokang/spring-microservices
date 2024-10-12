package com.microservices.otmp.analysis.config;

import com.microservices.otmp.common.kafka.consumer.KafkaConsumerYmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * @author guowb1
 * @date 2022/10/31 20:36
 */

@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private KafkaConsumerYmlConfig kafkaConsumerYmlConfig;

    @Bean(KafkaInfoConstant.ACCRUAL_MKT_KAFKA_CONSUMER_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaMktListenerContainerFactory() {
        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig, KafkaInfoConstant.ACCRUAL_MKT_KAFKA_CONSUMER_FACTORY);
    }

    @Bean(KafkaInfoConstant.ACCRUAL_IKT_KAFKA_CONSUMER_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaIktListenerContainerFactory() {
        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig, KafkaInfoConstant.ACCRUAL_IKT_KAFKA_CONSUMER_FACTORY);
    }

    @Bean(KafkaInfoConstant.ACCRUAL_IKT_KAFKA_CONSUMER_BATCH_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> iktBatch() {
        ConcurrentKafkaListenerContainerFactory<String, String> consumerFactory = kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig, KafkaInfoConstant.ACCRUAL_IKT_KAFKA_CONSUMER_FACTORY);
        consumerFactory.setBatchListener(true);
        return consumerFactory;
    }
}
