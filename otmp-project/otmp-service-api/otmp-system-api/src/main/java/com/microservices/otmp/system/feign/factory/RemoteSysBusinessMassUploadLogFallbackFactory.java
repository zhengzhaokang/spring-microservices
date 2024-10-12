package com.microservices.otmp.system.feign.factory;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.system.feign.RemoteBusinessMassUploadLogService;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.system.feign.RemoteBusinessMassUploadLogService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteSysBusinessMassUploadLogFallbackFactory implements FallbackFactory<RemoteBusinessMassUploadLogService>
{

    @Override
    public RemoteBusinessMassUploadLogService create(Throwable throwable) {
        throw new OtmpException(throwable.getMessage());
    }
}
