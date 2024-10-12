package com.microservices.otmp.system.log.impl;


import com.microservices.otmp.system.domain.dto.SystemLogDTO;
import com.microservices.otmp.system.log.SystemLogHandler;
import com.microservices.otmp.system.log.SystemLogicFactory;
import com.microservices.otmp.system.service.ISysBusinessOperatorLogService;
import com.microservices.otmp.system.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.microservices.otmp.system.enums.SystemLogTypeEnum.SYS_BUSINESS_OPERATOR_LOG;
import static com.microservices.otmp.system.enums.SystemLogTypeEnum.SYS_LOGIN_IN_FOR;


@Component
public class SysLogininforImpl implements SystemLogHandler {

    @Autowired
    private ISysLogininforService sysLogininforService;

    @Override
    public void handleData(SystemLogDTO systemLogDTO) {
        sysLogininforService.insertLogininfor(systemLogDTO.getSysLogininfor());
    }


    @Override
    //todo 确认阶段  看this是哪个
    public void afterPropertiesSet() throws Exception {
        SystemLogicFactory.register(SYS_LOGIN_IN_FOR.getCode(), this);
    }
}
