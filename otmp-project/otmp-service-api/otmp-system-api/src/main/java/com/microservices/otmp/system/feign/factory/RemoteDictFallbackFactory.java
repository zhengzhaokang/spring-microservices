package com.microservices.otmp.system.feign.factory;

import com.microservices.otmp.system.feign.RemoteDictService;
import com.microservices.otmp.system.feign.RemoteDictService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteDictFallbackFactory implements FallbackFactory<RemoteDictService>
{
    @Override
    public RemoteDictService create(Throwable throwable)
    {
        throwable.printStackTrace();
        throw new RuntimeException(throwable.getMessage());
    }
}
