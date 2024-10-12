package com.microservices.otmp.masterdata.feign;

import com.microservices.otmp.common.exception.OtmpException;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author xiaozy8
 */
@Component
public class RemoteGetDCWso2FallbackFactory implements FallbackFactory<RemoteGetDcWso2Service> {
    @Override
    public RemoteGetDcWso2Service create(Throwable cause) {
        return (salesOrgDTO, token) -> {
            throw  new OtmpException(cause.getMessage());
        };
    }
}
