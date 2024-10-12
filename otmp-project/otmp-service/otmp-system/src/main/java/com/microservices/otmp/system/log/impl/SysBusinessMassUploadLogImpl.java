package com.microservices.otmp.system.log.impl;


import com.microservices.otmp.system.domain.dto.SystemLogDTO;
import com.microservices.otmp.system.log.SystemLogHandler;
import com.microservices.otmp.system.log.SystemLogicFactory;
import com.microservices.otmp.system.service.ISysBusinessMassUploadLogService;
import com.microservices.otmp.system.log.SystemLogicFactory;
import com.microservices.otmp.system.service.ISysBusinessMassUploadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.microservices.otmp.system.enums.SystemLogTypeEnum.SYS_BUSINESS_MASSUPLOAD_LOG;


@Component
public class SysBusinessMassUploadLogImpl implements SystemLogHandler {

    @Autowired
    private ISysBusinessMassUploadLogService sysBusinessMassUploadLogService;

    @Override
    public void handleData(SystemLogDTO systemLogDTO) {
        sysBusinessMassUploadLogService.insertSysBusinessMassUploadLog(systemLogDTO.getSysBusinessMassUploadLog());
    }


    @Override
    //todo 确认阶段  看this是哪个
    public void afterPropertiesSet() throws Exception {
        SystemLogicFactory.register(SYS_BUSINESS_MASSUPLOAD_LOG.getCode(), this);
    }
}
