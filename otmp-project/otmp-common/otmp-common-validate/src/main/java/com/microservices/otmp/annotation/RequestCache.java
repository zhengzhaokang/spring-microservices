package com.microservices.otmp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 场景：
 * 一次请求的缓存
 * 相同的key 避免多次远程调用或是多次查表
 * @author daihuaicai
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestCache {
    /**
     * 缓存的key
     * @return
     */
    String cacheKey() default "";

    /**
     * 缓存大小
     * @return
     */
    int  cacheSize() default 50;
}
