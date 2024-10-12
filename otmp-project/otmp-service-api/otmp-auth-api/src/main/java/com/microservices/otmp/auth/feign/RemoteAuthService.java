package com.microservices.otmp.auth.feign;

import com.microservices.otmp.auth.feign.factory.RemoteAuthFallbackFactory;
import com.microservices.otmp.common.constant.ServiceNameConstants;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.system.domain.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "${otfp.service.auth.uri}", fallbackFactory = RemoteAuthFallbackFactory.class)
public interface RemoteAuthService {

    @PostMapping("create/token")
    public ResultDTO<Map<String, Object>> createToken(@RequestBody SysUser sysUser);
}
