package com.microservices.otmp.system.feign.factory;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.system.feign.RemoteBusinessOperatorLogService;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.system.feign.RemoteBusinessOperatorLogService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteBusinessOperatorLogFallbackFactory implements FallbackFactory<RemoteBusinessOperatorLogService>
{

    @Override
    public RemoteBusinessOperatorLogService create(Throwable throwable) {
        throw new OtmpException(throwable.getMessage());
    }
}
