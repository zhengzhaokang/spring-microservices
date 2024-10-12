package com.microservices.otmp.auth.feign.factory;

import com.microservices.otmp.auth.feign.RemoteAuthService;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.system.domain.SysUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class RemoteAuthFallbackFactory implements FallbackFactory<RemoteAuthService> {
    @Override
    public RemoteAuthService create(Throwable throwable) {
        return new RemoteAuthService() {

            @Override
            public ResultDTO<Map<String, Object>> createToken(SysUser sysUser) {
                throw new OtmpException(throwable.getMessage());
            }
        };
    }
}
