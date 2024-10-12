package com.microservices.otmp.system.controller;


import com.google.common.collect.Maps;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.system.domain.LoginForm;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.service.impl.AccessTokenService;
import com.microservices.otmp.system.service.impl.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class TokenController {
    @Autowired
    private AccessTokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;

    @Value("${microservices.id.logout.url}")
    private String logoutUrl;

    @Value("${microservices.id.switch}")
    private Boolean microservicesIdSwitch;


    @PostMapping("login")
    public ResultDTO login(@RequestBody LoginForm form) {
        // 用户登录
        SysUser user = sysLoginService.login(StringUtils.trim(StringUtils.lowerCase(form.getUsername())), form.getPassword());
        // 获取登录token
        return ResultDTO.success(tokenService.createToken(user));
    }

    @PostMapping("login/slide")
    public ResultDTO loginSilde(@RequestBody LoginForm form) {
        // 用户登录
        SysUser user = sysLoginService.login(StringUtils.trim(StringUtils.lowerCase(form.getUsername())), form.getPassword());
        // 获取登录token
        return ResultDTO.success(tokenService.createToken(user));
    }

    @PostMapping("logout")
    public ResultDTO logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        SysUser user = tokenService.queryByToken(token);
        if (null != user) {
            sysLoginService.logout(user.getLoginName());
            tokenService.expireToken(user.getUserId());
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("logoutUrl", logoutUrl);
        result.put("microservicesIdSwitch", microservicesIdSwitch);//返回开关
        return ResultDTO.success(result);
    }

    @PostMapping("create/token")
    public ResultDTO<Map<String, Object>> createToken(@RequestBody SysUser sysUser) {
        return ResultDTO.success(tokenService.createToken(sysUser));
    }

}
