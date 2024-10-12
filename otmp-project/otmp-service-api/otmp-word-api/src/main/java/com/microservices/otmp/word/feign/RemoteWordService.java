package com.microservices.otmp.word.feign;

import com.microservices.otmp.word.domain.SysWording;
import com.microservices.otmp.word.feign.factory.RemoteWordFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户 Feign服务层
 *
 * @Author lovefamily
 * @date 2019-05-20
 */

//因为LMP部署环境与SDMS部署环境不一致，所以只能配置域名
@FeignClient(name = "${otfp.service.word.uri}", fallbackFactory = RemoteWordFallbackFactory.class)
public interface RemoteWordService {
    @GetMapping("/maintenance/getWordFromRedis")
    public SysWording getWordFromRedis(@RequestParam("wordingKey") Long wordingKey);

}
