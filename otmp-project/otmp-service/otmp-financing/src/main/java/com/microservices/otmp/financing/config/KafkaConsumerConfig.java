package com.microservices.otmp.financing.config;


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

    @Bean(KafkaFactory.BANK_TRANSFER_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig,KafkaFactory.BANK_TRANSFER_FACTORY);
    }

//    @Bean(KafkaFactory.MSG_KAFKA_FACTORY)
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
//        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig,KafkaFactory.MSG_KAFKA_FACTORY);
//    }
//
//    @Bean(KafkaFactory.ASYNC_TASK_KAFKA_FACTORY)
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaAsyncListenerContainerFactory() {
//        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig,KafkaFactory.ASYNC_TASK_KAFKA_FACTORY);
//    }

//    @Bean(KafkaFactory.INVOICE_BATCH_SUBMIT_FACTORY)
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaSysLogListenerContainerFactory() {
//        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig,KafkaFactory.INVOICE_BATCH_SUBMIT_FACTORY);
//    }
}
