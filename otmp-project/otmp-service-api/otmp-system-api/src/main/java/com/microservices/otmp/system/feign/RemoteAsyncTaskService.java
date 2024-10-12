package com.microservices.otmp.system.feign;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.RemoteResponse;
import com.microservices.otmp.system.domain.BizAsyncTaskLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 异步任务 Feign服务层
 *
 * @Author lovefamily
 * @date 2022-08-10
 */
@FeignClient(name = "${otfp.service.system.uri}")
public interface RemoteAsyncTaskService {

    @GetMapping("/bizAsyncTaskLog/list")
    RemoteResponse<BizAsyncTaskLogDTO> list(@RequestParam("asyncCode") String asyncCode, @RequestParam("businessType") String businessType, @RequestParam("status") Integer status);

    @PostMapping("/bizAsyncTaskLog/add")
    ResultDTO<BizAsyncTaskLogDTO> add(@RequestBody BizAsyncTaskLogDTO bizAsyncTaskLogDTO);

    @PostMapping("/bizAsyncTaskLog/edit")
    ResultDTO update(@RequestBody BizAsyncTaskLogDTO bizAsyncTaskLogDTO);
}
