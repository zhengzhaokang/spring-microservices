package com.microservices.otmp.system.feign;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.system.domain.dto.SysBusinessMassUploadLogDTO;
import com.microservices.otmp.system.feign.factory.RemoteBusinessOperatorLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志Feign服务层
 * 
 * @Author lovefamily
 * @date 2019-05-20
 */

//因为LMP部署环境与SDMS部署环境不一致，所以只能配置域名
@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteBusinessOperatorLogFallbackFactory.class)
public interface RemoteBusinessMassUploadLogService
{
    @PostMapping("/sysBusinessMassUploadLog/save")
    public ResultDTO add(@RequestBody SysBusinessMassUploadLogDTO sysBusinessMassUploadLogDTO);

    @PutMapping("/sysBusinessMassUploadLog")
    public ResultDTO edit(@RequestBody SysBusinessMassUploadLogDTO sysBusinessMassUploadLogDTO);

}
