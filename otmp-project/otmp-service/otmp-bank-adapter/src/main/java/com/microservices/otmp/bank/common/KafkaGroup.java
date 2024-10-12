package com.microservices.otmp.bank.common;

/**
 * @author lovefamily
 */
public class KafkaGroup {
    private KafkaGroup() {
        throw new IllegalStateException("Utility class");
    }

    public static final String OTFP_GROUP = "otfp.bank-adapter";

}
