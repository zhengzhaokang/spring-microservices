package com.microservices.otmp.system.feign.factory;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.system.feign.RemoteJobLogService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteJobLogFallBackFactory implements FallbackFactory<RemoteJobLogService> {
    @Override
    public RemoteJobLogService create(Throwable cause) {
        throw new OtmpException(cause.getMessage());
    }
}
