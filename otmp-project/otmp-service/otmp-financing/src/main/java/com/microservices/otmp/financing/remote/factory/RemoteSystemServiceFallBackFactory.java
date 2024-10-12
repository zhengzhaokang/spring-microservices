package com.microservices.otmp.financing.remote.factory;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.financing.remote.RemoteSystemService;
import com.microservices.otmp.financing.remote.result.RegisterResult;
import com.microservices.otmp.system.domain.SysUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteSystemServiceFallBackFactory implements FallbackFactory<RemoteSystemService> {
    @Override
    public RemoteSystemService create(Throwable throwable) {
        log.error("create,call remote error,msg:",throwable);
        return new RemoteSystemService() {
            @Override
            public ResultDTO<RegisterResult> registermicroservicesId(SysUser param) {
                log.error("remote call registermicroservicesId error,param:{}", JsonUtil.toJSON(param));
                throw new OtmpException(throwable.getLocalizedMessage());
            }
        };
    }
}
