package com.microservices.otmp.system.feign.factory;

import feign.hystrix.FallbackFactory;

public class RemoteViewFieldFallBackFactory implements FallbackFactory {
    @Override
    public Object create(Throwable throwable) {
        return null;
    }
}
