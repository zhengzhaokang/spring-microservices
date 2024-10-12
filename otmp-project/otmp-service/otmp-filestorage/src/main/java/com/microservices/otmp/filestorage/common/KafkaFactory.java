package com.microservices.otmp.filestorage.common;

/**
 * @author qiaodf2
 */
public class KafkaFactory {

    private KafkaFactory() {
        throw new IllegalStateException("Utility class");
    }

    //此处需要和yml配置 server下的list里的kafkafactory值一致
    public static final String MSG_KAFKA_FACTORY = "msgKafkaFactory";
    public static final String ASYNC_TASK_KAFKA_FACTORY = "asyncTaskKafkaFactory";

    public static final String SYS_LOG_KAFKA_FACTORY = "sysLogKafkaFactory";


    /*************************************生产工厂*********************************************/

    public static final String MSG_KAFKA_PRODUCER_FACTORY = "msgKafkaProduceFactory";

}
