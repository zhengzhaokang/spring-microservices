package com.microservices.otmp.word.feign.factory;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.word.domain.SysWording;
import com.microservices.otmp.word.feign.RemoteWordService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Component
public class RemoteWordFallbackFactory implements FallbackFactory<RemoteWordService> {

    @Override
    public RemoteWordService create(Throwable throwable) {
        return new RemoteWordService() {
            @Override
            public SysWording getWordFromRedis(@RequestParam("wordingKey") Long wordingKey){
                throw new OtmpException(throwable.getMessage());
            }
        };
    }
}
