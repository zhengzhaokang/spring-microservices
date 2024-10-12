package com.microservices.otmp.erp.common;

/**
 * @author lovefamily
 * @date 2022年4月7日 16:55:41
 */

public class WordBaseData {

    private WordBaseData() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ENABLE_Y = "Y";
    public static final String ENABLE_N = "Y";
    public static final String REDIS_WORD = "word:json";
}
