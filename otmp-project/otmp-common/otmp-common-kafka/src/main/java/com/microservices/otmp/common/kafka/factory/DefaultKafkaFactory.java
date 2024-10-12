package com.microservices.otmp.common.kafka.factory;

public class DefaultKafkaFactory {

    private DefaultKafkaFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SYS_LOG_KAFKA_FACTORY = "sysLogKafkaFactory";

}
