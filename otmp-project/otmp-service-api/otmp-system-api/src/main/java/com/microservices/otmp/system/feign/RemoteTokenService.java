package com.microservices.otmp.system.feign;

import com.microservices.otmp.system.domain.dto.LoginTokenResponse;
import com.microservices.otmp.system.domain.dto.LoginTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${otfp.service.auth.uri}")
public interface RemoteTokenService {

    @GetMapping(value = "/get/token")
    LoginTokenResponse getToken(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password);
}
