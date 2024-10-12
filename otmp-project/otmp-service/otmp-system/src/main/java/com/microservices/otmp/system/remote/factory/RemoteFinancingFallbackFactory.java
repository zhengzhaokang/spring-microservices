package com.microservices.otmp.system.remote.factory;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.system.domain.dto.SupplierSimpleDTO;
import com.microservices.otmp.system.remote.RemoteFinancingService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteFinancingFallbackFactory implements FallbackFactory<RemoteFinancingService> {

    @Override
    public RemoteFinancingService create(Throwable throwable) {
        return new RemoteFinancingService() {
            @Override
            public ResultDTO<SupplierSimpleDTO> simple(Long id) {
                log.error("###RemoteFinancingFallbackFactory 远程调用失败:{}", throwable.getMessage());
                return null;
            }
        };
    }
}
