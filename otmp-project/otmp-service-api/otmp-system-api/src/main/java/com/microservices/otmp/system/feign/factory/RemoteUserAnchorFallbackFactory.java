package com.microservices.otmp.system.feign.factory;

import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.feign.RemoteUserAnchorService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteUserAnchorFallbackFactory implements FallbackFactory<RemoteUserAnchorService> {
    @Override
    public RemoteUserAnchorService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new RemoteUserAnchorService() {
            @Override
            public TableDataInfo selectAnchorList() {
                throw new OtmpException(throwable.getMessage());
            }
        };
    }
}
