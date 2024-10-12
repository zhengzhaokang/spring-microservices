package com.microservices.otmp.masterdata.feign;

import com.microservices.otmp.common.exception.OtmpException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteApcWso2FallbackFactory implements FallbackFactory<RemoteApcWso2Service>{


    @Override
    public RemoteApcWso2Service create(Throwable throwable) {
        return (dataIntegrateApcDTO, token) -> {
            throw new OtmpException(throwable.getMessage());
        };
    }
}
