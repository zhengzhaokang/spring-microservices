package com.microservices.otmp.analysis.remote;

import com.microservices.otmp.analysis.domain.vo.BatchSummaryWrapperVo;
import com.microservices.otmp.analysis.remote.factory.RemoteFinancingServiceFallBackFactory;
import com.microservices.otmp.analysis.remote.param.RemoteBatchSummaryParam;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.analysis.remote.factory.RemoteFinancingServiceFallBackFactory;
import com.microservices.otmp.analysis.remote.param.RemoteBatchSummaryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="${otfp.service.financing.uri}",fallbackFactory = RemoteFinancingServiceFallBackFactory.class)
public interface RemoteFinancingService {

    @PostMapping("/invoice/query/financed/batch/summary")
    ResultDTO<BatchSummaryWrapperVo> getBatchSummaryData(@RequestBody RemoteBatchSummaryParam param);

}
