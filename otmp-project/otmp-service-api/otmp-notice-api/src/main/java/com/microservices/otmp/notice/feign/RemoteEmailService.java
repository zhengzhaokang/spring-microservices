package com.microservices.otmp.notice.feign;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.notice.domain.dto.EmailReceipentDTO;
import com.microservices.otmp.notice.domain.dto.EmailSendDTO;
import com.microservices.otmp.notice.feign.factory.RemoteEmailFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 用户 Feign服务层
 *
 * @Author lovefamily
 * @date 2019-05-20
 */

//因为LMP部署环境与SDMS部署环境不一致，所以只能配置域名
@FeignClient(name = "${otfp.service.email.uri}", fallbackFactory = RemoteEmailFallbackFactory.class)
//@FeignClient(name = ServiceNameConstants.EMAIL_SERVICE, url="http://localhost:8873", fallbackFactory = RemoteEmailFallbackFactory.class)
public interface RemoteEmailService {
    @PostMapping("/emailModule/sendEmail")
    ResultDTO<Object> sendEmail(@RequestBody EmailSendDTO emailSendHistoryRequest);

    @PostMapping("/receipent/getSendToList")
    ResultDTO<List<EmailReceipentDTO>> getSendToList(@RequestBody EmailReceipentDTO emailReceipent);

    @PostMapping("/emailModule/asynSendEmail")
    ResultDTO<Object> asynSendEmail(@RequestBody EmailSendDTO emailSendDTO);
}
