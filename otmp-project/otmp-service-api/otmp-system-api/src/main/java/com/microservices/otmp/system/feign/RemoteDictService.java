package com.microservices.otmp.system.feign;

import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.feign.factory.RemoteDictFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户 Feign服务层
 *
 * @Author lovefamily
 * @date 2019-05-20
 */

//因为LMP部署环境与SDMS部署环境不一致，所以只能配置域名
@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteDictFallbackFactory.class)
public interface RemoteDictService {
    @GetMapping("/dict/data/type")
    List<SysDictData> getDictSelect(@RequestParam("dictType") String dictType);
}
