package com.microservices.otmp.masterdata.common;

/**
 * @author qiaodf2
 */
public class KafkaFactory {
    private KafkaFactory() {
    }

    //此处需要和yml配置 server下的list里的kafkafactory值一致
    public static final String ECC_KAFKA_FACTORY = "eccKafkaFactory";
    public static final String ECC_LOCAL_KAFKA_FACTORY = "eccLocalKafkaFactory";
    public static final String LGVM_KAFKA_FACTORY = "lgvmKafkaFactory";


}
