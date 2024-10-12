package com.microservices.otmp.system.feign;

import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.system.feign.factory.RemoteUserAnchorFallbackFactory;
import com.microservices.otmp.system.interceptor.APIQualifierInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//因为LMP部署环境与SDMS部署环境不一致，所以只能配置域名
@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteUserAnchorFallbackFactory.class, configuration = APIQualifierInterceptor.class)
public interface RemoteUserAnchorService {
    @GetMapping(value = "user/anchor/list")
    TableDataInfo selectAnchorList();

}
