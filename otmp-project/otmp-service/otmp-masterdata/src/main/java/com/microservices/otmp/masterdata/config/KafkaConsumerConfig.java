package com.microservices.otmp.masterdata.config;


import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.kafka.consumer.KafkaConsumerYmlConfig;
import com.microservices.otmp.masterdata.common.KafkaFactory;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOrg;
import com.microservices.otmp.masterdata.service.IBizBaseSalesOrgService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
public class KafkaConsumerConfig {

    @Autowired
    KafkaConsumerYmlConfig kafkaConsumerYmlConfig;
    @Value("${kafka.consumer.topics.customer_general}")
    private String customerGeneral;
    @Autowired
    private IBizBaseSalesOrgService bizBaseSalesOrgService;

    @Bean(KafkaFactory.ECC_KAFKA_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig,KafkaFactory.ECC_KAFKA_FACTORY);
    }
    @Bean(KafkaFactory.ECC_LOCAL_KAFKA_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory1() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig, KafkaFactory.ECC_LOCAL_KAFKA_FACTORY);
        factory.setRecordFilterStrategy((consumerRecord) -> {
            JSONObject jsonObject = JSONObject.parseObject(consumerRecord.value());
            String topic = consumerRecord.topic();
            if (topic.equals(customerGeneral)) {
                //过滤掉中国区的customer,条件countryKey != CN
                if (!jsonObject.getString("countryKey").equals("CN")) {
                    return false;
                }
            } else {
                BizBaseSalesOrg org = new BizBaseSalesOrg();
                org.setSalesOrgCode(jsonObject.getString("salesOrganization"));
                //过滤掉中国区的customer,条件VKORG = SDMS2.0里的sales org, 其中VKORG是指salesOrganization
                if (CollectionUtils.isNotEmpty(bizBaseSalesOrgService.selectBizBaseSalesOrgList(org))) {
                    return false;
                }

            }
            //返回true将会被丢弃
            return true;
        });
        return factory;
    }
    @Bean(KafkaFactory.LGVM_KAFKA_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> lgvmKafkaListenerContainerFactory() {
        return kafkaConsumerYmlConfig.getConsumerFactory(kafkaConsumerYmlConfig,KafkaFactory.LGVM_KAFKA_FACTORY);
    }

}
