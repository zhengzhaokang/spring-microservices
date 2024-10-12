package com.microservices.otmp.financing.remote;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.financing.remote.factory.RemoteSystemServiceFallBackFactory;
import com.microservices.otmp.financing.remote.result.RegisterResult;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.interceptor.APIQualifierInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteSystemServiceFallBackFactory.class, configuration = APIQualifierInterceptor.class)
public interface RemoteSystemService {

    /**
     * 注册microservicesId
     * @param param 使用loginName字段
     * @return 执行结果
     */
    @PostMapping(value = "/user/supplier/save")
    ResultDTO<RegisterResult> registermicroservicesId(@RequestBody SysUser param);

}
