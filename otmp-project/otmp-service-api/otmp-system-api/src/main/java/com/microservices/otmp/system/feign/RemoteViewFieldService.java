package com.microservices.otmp.system.feign;

import com.microservices.otmp.common.core.page.RemoteResponse;
import com.microservices.otmp.system.feign.factory.RemoteDictFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteDictFallbackFactory.class)
public interface RemoteViewFieldService {

    @GetMapping(value = "/field/getViewFieldByBgAndGeo", headers = {"Keep-Original=Keep-Original"})
    public Object getViewFieldByBgAndGeo(@RequestParam(value = "geo") String geo, @RequestParam(value = "bg") String businessGroup,
                                         @RequestParam(value = "pageKey") String pageKey, @RequestParam(value = "viewType") String viewType);

    @GetMapping(value = "/field/pageKeyList")
    public RemoteResponse pageKeyList(@SpringQueryMap Map<String, Object> param);

}
