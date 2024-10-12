package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.system.domain.microservicesIdParam;
import com.microservices.otmp.system.domain.RegisterForm;
import com.microservices.otmp.system.service.SysmicroservicesIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用microservicesId登录的相关操作
 */
@RestController
@RequestMapping("microservices/id")
public class SysmicroservicesIdLoginController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysmicroservicesIdLoginController.class);

    @Autowired
    private SysmicroservicesIdService sysmicroservicesIdService;

    @Value("${microservices.id.switch}")
    private Boolean microservicesIdSwitch;

    @Value("${microservices.id.redirect}")
    private String redirect;

    public static final String URL = "redirectUrl" ;

    @RequestMapping(value = "index", method = RequestMethod.POST)
    public ResultDTO<Object> login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put(URL, redirect);
        data.put("microservicesIdSwitch", microservicesIdSwitch);
        logger.info("URL is {}, microservicesIdSwitch is {}", redirect, microservicesIdSwitch);
        return ResultDTO.success(data);
    }

    @PostMapping("register")
    public ResultDTO<Object>  register(@RequestBody RegisterForm registerForm) {
        if (registerForm == null || StringUtils.isBlank(registerForm.getUsername())) {
            String warnInfo = "###SysmicroservicesIdLoginController register param is blank";
            logger.warn(warnInfo);
            return ResultDTO.fail(warnInfo);
        }
        return ResultDTO.success(sysmicroservicesIdService.register(registerForm));
    }

    @PostMapping("login")
    public ResultDTO<Object> login(@RequestBody microservicesIdParam microservicesIdParam) {
        return sysmicroservicesIdService.login(microservicesIdParam);
    }

    @PostMapping("logout")
    public ResultDTO<Object> logout(@RequestBody microservicesIdParam microservicesIdParam) {
        return sysmicroservicesIdService.logout(microservicesIdParam);
    }

    @PostMapping("ssoAuth")
    public ResultDTO<Object> ssoAuth(@RequestBody microservicesIdParam microservicesIdParam) {
        return sysmicroservicesIdService.ssoAuth(microservicesIdParam);
    }

    @PostMapping("validateWebauthnSt")
    public ResultDTO<Object> validateWebauthnSt(@RequestBody microservicesIdParam microservicesIdParam) {
        if (microservicesIdParam == null || StringUtils.isBlank(microservicesIdParam.getWebauthnSt())) {
            String warnInfo = "###SysmicroservicesIdLoginController validateWebauthnSt param is blank";
            logger.warn(warnInfo);
            return ResultDTO.fail(warnInfo);
        }
        return sysmicroservicesIdService.validateWebauthnSt(microservicesIdParam);
    }

    @PostMapping("validateWebauthnSSt")
    public ResultDTO<Object> validateWebauthnSSt(@RequestBody microservicesIdParam microservicesIdParam) {
        if (microservicesIdParam == null || StringUtils.isBlank(microservicesIdParam.getWebauthnSst())) {
            String warnInfo = "###SysmicroservicesIdLoginController validate sst param is blank";
            logger.warn(warnInfo);
            return ResultDTO.fail(warnInfo);
        }
        return sysmicroservicesIdService.validateWebauthnSSt(microservicesIdParam);
    }

    @PostMapping("changePassword")
    public ResultDTO<Object> forgotPassword(HttpServletRequest request) {
        return sysmicroservicesIdService.changePassword();
    }

}
