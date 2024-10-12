package com.microservices.otmp.system.feign;

import com.microservices.otmp.system.domain.SysKafkaLog;
import com.microservices.otmp.system.domain.SysLogininfor;
import com.microservices.otmp.system.domain.SysOperLog;
import com.microservices.otmp.system.feign.factory.RemoteLogFallbackFactory;
import com.microservices.otmp.system.interceptor.FeignLogInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志Feign服务层
 *
 * @Author lovefamily
 * @date 2019-05-20
 */

//因为LMP部署环境与SDMS部署环境不一致，所以只能配置域名
@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteLogFallbackFactory.class, configuration = FeignLogInterceptor.class)
//@FeignClient(name = ServiceNameConstants.SYSTEM_SERVICE, url="http://localhost:8001", fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {
    @PostMapping("operLog/save")
    public void insertOperlog(@RequestBody SysOperLog operLog);

    @PostMapping("logininfor/save")
    public void insertLoginlog(@RequestBody SysLogininfor logininfor);

    @PostMapping("kafkaLog/save")
    public void insertKafkalog(@RequestBody SysKafkaLog operLog);
}
