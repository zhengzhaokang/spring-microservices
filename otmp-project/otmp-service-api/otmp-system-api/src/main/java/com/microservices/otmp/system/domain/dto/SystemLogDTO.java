package com.microservices.otmp.system.domain.dto;

import com.microservices.otmp.system.domain.BizAsyncTaskLogDTO;
import com.microservices.otmp.system.domain.SysKafkaLog;
import com.microservices.otmp.system.domain.SysLogininfor;
import com.microservices.otmp.system.domain.SysOperLog;
import lombok.Data;

@Data
public class SystemLogDTO {

    SysBusinessOperatorLogDTO sysBusinessOperatorLog;

    SysBusinessMassUploadLogDTO sysBusinessMassUploadLog;

    SysOperLog sysOperLog;

    SysKafkaLog sysKafkaLog;
    BizAsyncTaskLogDTO bizAsyncTaskLog;
    SysLogininfor sysLogininfor;

    String logType;


}
