package com.microservices.otmp.system.log.impl;


import com.microservices.otmp.system.domain.dto.SystemLogDTO;
import com.microservices.otmp.system.log.SystemLogHandler;
import com.microservices.otmp.system.log.SystemLogicFactory;
import com.microservices.otmp.system.service.ISysKafkaLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.microservices.otmp.system.enums.SystemLogTypeEnum.SYS_KAFKA_LOG;


@Component
public class SysKafkaLogImpl implements SystemLogHandler {
    @Autowired
    private ISysKafkaLogService sysKafkaLogService;
    @Override
    public void handleData(SystemLogDTO systemLogDTO) {
        sysKafkaLogService.insertKafkalog(systemLogDTO.getSysKafkaLog());
    }

    @Override
    //todo 确认阶段  看this是哪个
    public void afterPropertiesSet() throws Exception {
        SystemLogicFactory.register(SYS_KAFKA_LOG.getCode(), this);
    }
}
