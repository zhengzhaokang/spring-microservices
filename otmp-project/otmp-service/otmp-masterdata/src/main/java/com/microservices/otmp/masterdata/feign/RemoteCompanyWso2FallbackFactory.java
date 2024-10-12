package com.microservices.otmp.masterdata.feign;

import com.microservices.otmp.common.exception.OtmpException;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteCompanyWso2FallbackFactory implements FallbackFactory<RemoteCompanyCodeWso2Service> {
    @Override
    public RemoteCompanyCodeWso2Service create(Throwable cause) {
        return (salesOrgDTO, token) -> {
            throw  new OtmpException(cause.getMessage());
        };
    }
}
