package com.microservices.otmp.financing.remote.factory;

import com.microservices.otmp.common.core.domain.R;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.financing.remote.RemoteFileService;
import com.microservices.otmp.financing.remote.param.BatchUpdateParam;
import com.microservices.otmp.financing.remote.param.FileUpdateParam;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteFileServiceFallBackFactory implements FallbackFactory<RemoteFileService> {
    @Override
    public RemoteFileService create(Throwable throwable) {
        log.error("create,call remote error,msg:",throwable);
        return new RemoteFileService() {

            @Override
            public R update(FileUpdateParam param) {
                log.error("remote call update error,param:{}", JsonUtil.toJSON(param));
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<Object> batchUpdate(BatchUpdateParam param) {
                log.error("remote call batchUpdate error,param:{}", JsonUtil.toJSON(param));
                throw new OtmpException(throwable.getLocalizedMessage());
            }
        };
    }
}
