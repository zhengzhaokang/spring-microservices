package com.microservices.otmp.system.feign;

import com.microservices.otmp.system.domain.SysDept;
import com.microservices.otmp.system.feign.factory.RemoteDeptFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户 Feign服务层
 * 
 * @Author lovefamily
 * @date 2019-05-20
 */
@FeignClient(name = "${otfp.service.system.uri}", fallbackFactory = RemoteDeptFallbackFactory.class)
public interface RemoteDeptService
{
    @GetMapping("dept/get/{deptId}")
    public SysDept selectSysDeptByDeptId(@PathVariable("deptId") long deptId);
}
