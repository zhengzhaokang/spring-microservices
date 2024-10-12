package com.microservices.otmp.analysis.config;

/**
 * @author guowb1
 * @date 2022/10/31 20:41
 */
public class KafkaInfoConstant {

    private KafkaInfoConstant() {
    }

    public static final String ACCRUAL_IKT_KAFKA_PRODUCER_FACTORY = "AccrualIktKafkaProducerFactory";
    public static final String ACCRUAL_MKT_KAFKA_CONSUMER_FACTORY = "AccrualMktKafkaConsumerFactory";
    public static final String ACCRUAL_IKT_KAFKA_CONSUMER_FACTORY = "AccrualIktKafkaConsumerFactory";
    public static final String ACCRUAL_IKT_KAFKA_CONSUMER_BATCH_FACTORY = "AccrualIktKafkaConsumerBatchFactory";
}
