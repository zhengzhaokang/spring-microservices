package com.microservices.otmp.system.log.impl;


import com.microservices.otmp.system.domain.dto.SystemLogDTO;
import com.microservices.otmp.system.log.SystemLogHandler;
import com.microservices.otmp.system.log.SystemLogicFactory;
import com.microservices.otmp.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.microservices.otmp.system.enums.SystemLogTypeEnum.SYS_OPER_LOG;

@Component
public class SysOperLogImpl implements SystemLogHandler {

    @Autowired
    private ISysOperLogService sysOperLogService;

    @Override
    public void handleData(SystemLogDTO systemLogDTO) {
        sysOperLogService.insertOperlog(systemLogDTO.getSysOperLog());
    }


    @Override
    //todo 确认阶段  看this是哪个
    public void afterPropertiesSet() throws Exception {
        SystemLogicFactory.register(SYS_OPER_LOG.getCode(), this);
    }
}
