package com.microservices.otmp.system.log.impl;


import com.microservices.otmp.system.domain.dto.SystemLogDTO;
import com.microservices.otmp.system.log.SystemLogHandler;
import com.microservices.otmp.system.log.SystemLogicFactory;
import com.microservices.otmp.system.service.ISysBusinessOperatorLogService;
import com.microservices.otmp.system.log.SystemLogicFactory;
import com.microservices.otmp.system.service.ISysBusinessOperatorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.microservices.otmp.system.enums.SystemLogTypeEnum.SYS_BUSINESS_OPERATOR_LOG;


@Component
public class SysBusinessOperatorLogImpl implements SystemLogHandler {

    @Autowired
    private ISysBusinessOperatorLogService sysBusinessOperatorLogService;

    @Override
    public void handleData(SystemLogDTO systemLogDTO) {
        sysBusinessOperatorLogService.insertSysBusinessOperatorLog(systemLogDTO.getSysBusinessOperatorLog());
    }


    @Override
    //todo 确认阶段  看this是哪个
    public void afterPropertiesSet() throws Exception {
        SystemLogicFactory.register(SYS_BUSINESS_OPERATOR_LOG.getCode(), this);
    }
}
