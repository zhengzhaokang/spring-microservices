package com.microservices.otmp.masterdata.feign;

import com.microservices.otmp.common.exception.OtmpException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemotePlantCategoryWso2FallbackFactory implements FallbackFactory<RemotePlantCategoryWso2Service>{


    @Override
    public RemotePlantCategoryWso2Service create(Throwable throwable) {
        return (plantCategoryDTO, token) -> {
            throw new OtmpException(throwable.getMessage());
        };
    }
}
