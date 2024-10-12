package com.microservices.otmp.system.feign;

import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.system.domain.MsgRemindDTO;
import com.microservices.otmp.system.feign.factory.RemoteOssFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Msg Feign服务层
 *
 * @Author lovefamily
 * @date 2022-08-10
 */
@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteOssFallbackFactory.class)
public interface RemoteMsgRemindService {
    @GetMapping("/msgRemind/list")
    public TableDataInfo list(@SpringQueryMap MsgRemindDTO msgRemindDTO);

}
