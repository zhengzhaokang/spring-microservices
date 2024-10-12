package com.microservices.otmp.system.log.impl;


import com.microservices.otmp.system.domain.dto.SystemLogDTO;
import com.microservices.otmp.system.log.SystemLogHandler;
import com.microservices.otmp.system.log.SystemLogicFactory;
import com.microservices.otmp.system.service.IBizAsyncTaskLogService;
import com.microservices.otmp.system.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.microservices.otmp.system.enums.SystemLogTypeEnum.SYS_LOGIN_IN_FOR;


@Component
public class BizAsyncTaskLogImpl implements SystemLogHandler {

    @Autowired
    private IBizAsyncTaskLogService bizAsyncTaskLogService;
    @Override
    public void handleData(SystemLogDTO systemLogDTO) {
        bizAsyncTaskLogService.insertBizAsyncTaskLog(systemLogDTO.getBizAsyncTaskLog());
    }

    @Override
    //todo 确认阶段  看this是哪个
    public void afterPropertiesSet() throws Exception {
        SystemLogicFactory.register(SYS_LOGIN_IN_FOR.getCode(), this);
    }
}
