package com.microservices.otmp.system.feign;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.system.domain.dto.RemoteSdmsJobLogDTO;
import com.microservices.otmp.system.feign.factory.RemoteJobLogFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteJobLogFallBackFactory.class)
public interface RemoteJobLogService {

    @PostMapping("/jobMonitor/add")
    public ResultDTO add(@RequestBody RemoteSdmsJobLogDTO bizSdmsJobLog);

    @PutMapping("/jobMonitor/edit")
    public ResultDTO edit(@RequestBody RemoteSdmsJobLogDTO bizSdmsJobLog);

    @PostMapping("/jobMonitor/getJobLogDTO")
    public ResultDTO<RemoteSdmsJobLogDTO> getSdmsJobLogDTO(@RequestBody RemoteSdmsJobLogDTO bizSdmsJobLog);

}
