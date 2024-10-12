package com.microservices.otmp.system.service;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.system.domain.microservicesIdParam;
import com.microservices.otmp.system.domain.RegisterForm;
import com.microservices.otmp.system.domain.RegisterResult;

public interface SysmicroservicesIdService {
    RegisterResult register(RegisterForm registerForm);

    ResultDTO<Object> login(microservicesIdParam microservicesIdParam);

    ResultDTO<Object> logout(microservicesIdParam microservicesIdParam);

    ResultDTO<Object> forgotPassword(microservicesIdParam microservicesIdParam);

    ResultDTO<Object> ssoAuth(microservicesIdParam microservicesIdParam);

    ResultDTO<Object> validateWebauthnSt(microservicesIdParam microservicesIdParam);

    ResultDTO<Object> validateWebauthnSSt(microservicesIdParam microservicesIdParam);

    ResultDTO<Object> changePassword();
}
