package com.microservices.otmp.filestorage.common;

/**
 * @author qiaodf2
 */
public class KafkaTopic {

    private KafkaTopic() {
        throw new IllegalStateException("Utility class");
    }
    //*************************************生产topic********************************************
    /**
     * update pool value
     */
    public static final String MSG_REMIND = "otmp-svc.msg-remind";

}
