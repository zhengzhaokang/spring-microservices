package com.microservices.otmp.system.feign.factory;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.system.feign.RemoteConfigService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteConfigFallbackFactory implements FallbackFactory<RemoteConfigService>
{
    @Override
    public RemoteConfigService create(Throwable throwable) {
        return new RemoteConfigService() {

            @Override
            public String getConfigKey(String configKey) {
                throw new OtmpException(throwable.getMessage());
            }
        };
    }/* (non-Javadoc)
  * @see feign.hystrix.FallbackFactory#create(java.lang.Throwable)
  */

}
