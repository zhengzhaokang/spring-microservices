package com.microservices.otmp.system.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.http.HttpUtils;
import com.microservices.otmp.system.domain.*;
import com.microservices.otmp.system.service.SysmicroservicesIdService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysmicroservicesIdServiceImpl implements SysmicroservicesIdService {

    private static final Logger logger = LoggerFactory.getLogger(SysmicroservicesIdServiceImpl.class);

    @Autowired
    private SysLoginService sysLoginService;

    @Autowired
    private AccessTokenService tokenService;

    @Value("${microservices.id.webauthnLang}")
    private String webauthnLang;

    @Value("${microservices.id.webauthnSource}")
    private String webauthnSource;

    @Value("${microservices.id.webauthnRealm}")
    private String webauthnRealm;

    @Value("${microservices.id.webauthnState}")
    private String webauthnState;

    @Value("${microservices.id.publicKey}")
    private String publicKey;

//    @Value("${microservices.id.profileSource}")
//    private String profileSource;

    @Value("${microservices.id.userType}")
    private String userType;

    @Value("${microservices.id.serverName}")
    private String serverName;

    @Value("${microservices.id.register.webauthnCallback}")
    private String registerWebauthnCallback;

    @Value("${microservices.id.login.webauthnCallback}")
    private String loginWebauthnCallback;

    @Value("${microservices.id.logout.webauthnCallback}")
    private String logoutWebauthnCallback;

    @Value("${microservices.id.forgotPassword.webauthnCallback}")
    private String forgotPasswordWebauthnCallback;

    @Value("${microservices.id.auth.webauthnCallback}")
    private String authWebauthnCallback;

    public final static String INIT_USER_PASSWORD = "123456";

    @Override
    public RegisterResult register(RegisterForm registerInfo) {
        String registerUrl = serverName + "webauthn/accountRegister";
        Map<String, String> header = Maps.newHashMap();
        header.put("Content-Type", "application/x-www-form-urlencoded");
        Map<String, String> param = Maps.newHashMap();
        param.put("username", registerInfo.getUsername());
        param.put("webauthn_lang", webauthnLang);
        param.put("webauthn_source", webauthnSource);
        param.put("webauthn_callback", registerWebauthnCallback);
        param.put("webauthn_realm", webauthnRealm);
        param.put("webauthn_state", webauthnState);
        logger.info("###SysmicroservicesIdServiceImpl register param is {}", JSON.toJSONString(param) );
        RegisterResult registerResult = new RegisterResult();
        try {
            String source = StringUtils.lowerCase(registerInfo.getUsername().trim()) + webauthnRealm + publicKey;
            String signature = DigestUtils.sha256Hex(source);
            param.put("signature", signature);
            String result = HttpUtils.post(registerUrl, header, param);

            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject != null) {
                registerResult.setAccountId(jsonObject.getString("account_id"));
                registerResult.setInitUrl(jsonObject.getString("init_url"));
                registerResult.setCode(jsonObject.getString("Code"));
                registerResult.setTimestamp(jsonObject.getString("Timestamp"));
            }
        } catch (Exception e) {
            logger.error("SysmicroservicesIdServiceImpl register Exception is ", e);
        }
        return registerResult;
    }

    @Override
    public ResultDTO<Object> login(microservicesIdParam microservicesIdParam) {
        StringBuilder buffer = getSendUrl("uilogin", loginWebauthnCallback);
        logger.info("###SysmicroservicesIdServiceImpl login url is {}", buffer.toString());
        String result = null;
        try {
            result = HttpUtils.get(buffer.toString());
        } catch (Exception e) {
            logger.error("SysmicroservicesIdServiceImpl login Exception is ", e);
        }
        return ResultDTO.success(result);
    }

    private StringBuilder getSendUrl(String action, String callback) {
        String gatewayUrl = serverName + "webauthn/gateway";
        StringBuilder buffer = new StringBuilder();
        buffer.append(gatewayUrl);
        buffer.append("?webauthn_action=");
        buffer.append(action);
        buffer.append("&webauthn_realm=");
        buffer.append(webauthnRealm);
        buffer.append("&webauthn_callback=");
        buffer.append(callback);
        return buffer;
    }

    @Override
    public ResultDTO<Object> logout(microservicesIdParam microservicesIdParam) {
        StringBuilder buffer = getSendUrl("uilogout", logoutWebauthnCallback);
        logger.info("###SysmicroservicesIdServiceImpl logout url is {}", buffer.toString());
        String result = null;
        try {
            result = HttpUtils.get(buffer.toString());
        } catch (Exception e) {
            logger.error("SysmicroservicesIdServiceImpl logout Exception is ", e);
        }
        return ResultDTO.success(result);
    }

    @Override
    public ResultDTO<Object> forgotPassword(microservicesIdParam microservicesIdParam) {
        StringBuilder buffer = getSendUrl("forgetpassword", forgotPasswordWebauthnCallback);
        logger.info("###SysmicroservicesIdServiceImpl forgotPassword url is {}", buffer.toString());
        String result = null;
        try {
            result = HttpUtils.get(buffer.toString());
        } catch (Exception e) {
            logger.error("SysmicroservicesIdServiceImpl forgotPassword Exception is ", e);
        }
        return ResultDTO.success(result);
    }

    @Override
    public ResultDTO<Object> ssoAuth(microservicesIdParam microservicesIdParam) {
        String authUrl = serverName + "webauthn/ssoAuthn";
        StringBuilder buffer = new StringBuilder();
        buffer.append(authUrl);
//        buffer.append("?profile_source=");
//        buffer.append(profileSource);
        buffer.append("?user_type=");
        buffer.append(userType);
        buffer.append("&webauthn_callback=");
        buffer.append(logoutWebauthnCallback);
        buffer.append("&webauthn_state=");
        buffer.append(webauthnState);
        buffer.append("&webauthn_realm=");
        buffer.append(webauthnRealm);
        logger.info("###SysmicroservicesIdServiceImpl ssoAuth url is {}", buffer.toString());
        String result = null;
        try {
            result = HttpUtils.get(buffer.toString());
        } catch (Exception e) {
            logger.error("SysmicroservicesIdServiceImpl ssoAuth Exception is ", e);
        }
        return ResultDTO.success(result);
    }

    @Override
    public ResultDTO<Object> validateWebauthnSt(microservicesIdParam microservicesIdParam) {
        String validateStUrl = serverName + "webauthn/validateWebauthnSt";
        StringBuilder buffer = new StringBuilder();
        buffer.append(validateStUrl);
        buffer.append("?webauthn_st=");
        buffer.append(microservicesIdParam.getWebauthnSt());
        buffer.append("&realm=");
        buffer.append(webauthnRealm);
        logger.info("###SysmicroservicesIdServiceImpl validateWebauthnSt url is {}", buffer.toString());
        String result = null;
        try {
            result = HttpUtils.get(buffer.toString());
        } catch (Exception e) {
            logger.error("SysmicroservicesIdServiceImpl validateWebauthnSt Exception is ", e);
        }
        logger.info("###SysmicroservicesIdServiceImpl validateWebauthnSt result is {}", result);
        ValidateResult validateResult = getValidateResult(result);
        // 获取到username，创建Token
        if (StringUtils.isNotBlank(validateResult.getUsername())) {
            // 用户登录
            try {
                SysUser user = sysLoginService.login4microservicesId(validateResult.getUsername(), INIT_USER_PASSWORD);

                Map<String, Object> token = tokenService.createToken(user);
                logger.info("###SysmicroservicesIdServiceImpl validateWebauthnSt token is {}", JSON.toJSONString(token));
                return ResultDTO.success(token);
            } catch (Exception e) {
                logger.error("SysmicroservicesIdServiceImpl validateWebauthnSt Exception is ", e);
                return ResultDTO.fail(validateResult.getUsername());
            }
        } else {
            return ResultDTO.fail(validateResult.getCode());
        }
    }

    private ValidateResult getValidateResult(String result) {
        ValidateResult validateResult = new ValidateResult();
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject != null) {
            validateResult.setProfileSource(jsonObject.getString("ProfileSource"));
            validateResult.setAccountID(jsonObject.getString("AccountID"));
            validateResult.setUsername(jsonObject.getString("Username"));
            validateResult.setSst(jsonObject.getString("Sst"));
            validateResult.setUserType(jsonObject.getString("UserType"));
            validateResult.setCode(jsonObject.getString("Code"));
            validateResult.setTimestamp(jsonObject.getString("Timestamp"));
        }
        return validateResult;
    }

    @Override
    public ResultDTO<Object> validateWebauthnSSt(microservicesIdParam microservicesIdParam) {
        String validateStUrl = serverName + "webauthn/validateWebauthnSSt";
        StringBuilder buffer = new StringBuilder();
        buffer.append(validateStUrl);
        buffer.append("?sst=");
        buffer.append(microservicesIdParam.getWebauthnSst());
        logger.info("###SysmicroservicesIdServiceImpl sst url is {}", buffer.toString());
        String result = null;
        try {
            result = HttpUtils.get(buffer.toString());
        } catch (Exception e) {
            logger.error("SysmicroservicesIdServiceImpl sst Exception is ", e);
        }
        return ResultDTO.success(result);
    }

    @Override
    public ResultDTO<Object> changePassword() {
        String result = serverName + "account-page/#/";
        return ResultDTO.success(result);
    }
}
