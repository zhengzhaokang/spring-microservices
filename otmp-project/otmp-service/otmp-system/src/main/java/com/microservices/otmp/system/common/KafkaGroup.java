package com.microservices.otmp.system.common;

/**
 * @author qiaodf2
 */
public class KafkaGroup {
    private KafkaGroup() {
        throw new IllegalStateException("Utility class");
    }

    //*************************************生产topic********************************************
    /**
     * update pool value
     */
    public static final String OTMP_SVC = "otmp-svc";

}
