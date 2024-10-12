package com.microservices.otmp.system.feign.factory;

import com.microservices.otmp.system.domain.SysKafkaLog;
import com.microservices.otmp.system.domain.SysLogininfor;
import com.microservices.otmp.system.domain.SysOperLog;
import com.microservices.otmp.system.feign.RemoteLogService;
import com.microservices.otmp.system.domain.SysKafkaLog;
import com.microservices.otmp.system.domain.SysLogininfor;
import com.microservices.otmp.system.domain.SysOperLog;
import com.microservices.otmp.system.feign.RemoteLogService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService>
{
    @Override
    public RemoteLogService create(Throwable throwable)
    {
        log.error(throwable.getMessage());
        return new RemoteLogService()
        {
            @Override
            public void insertOperlog(SysOperLog operLog)
            {
            }

            @Override
            public void insertLoginlog(SysLogininfor logininfor)
            {
            }

            @Override
            public void insertKafkalog(SysKafkaLog operLog)
            {
            }
        };
    }
}
