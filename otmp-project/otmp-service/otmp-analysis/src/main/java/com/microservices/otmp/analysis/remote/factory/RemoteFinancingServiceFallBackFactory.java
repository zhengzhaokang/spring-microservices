package com.microservices.otmp.analysis.remote.factory;

import com.microservices.otmp.analysis.domain.vo.BatchSummaryWrapperVo;
import com.microservices.otmp.analysis.remote.RemoteFinancingService;
import com.microservices.otmp.analysis.remote.param.RemoteBatchSummaryParam;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.JsonUtil;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteFinancingServiceFallBackFactory implements FallbackFactory<RemoteFinancingService> {
    @Override
    public RemoteFinancingService create(Throwable throwable) {
        log.error("create,call remote error,msg:",throwable);
        return new RemoteFinancingService() {

            @Override
            public ResultDTO<BatchSummaryWrapperVo> getBatchSummaryData(RemoteBatchSummaryParam param) {
                log.error("remote call getBatchSummaryData error,param:{}", JsonUtil.toJSON(param));
                throw new OtmpException(throwable.getLocalizedMessage());
            }
        };
    }
}
