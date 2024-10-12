package com.microservices.otmp.system.feign.factory;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.system.domain.MsgRemindDTO;
import com.microservices.otmp.system.feign.RemoteMsgRemindService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteMsgRemindFallbackFactory implements FallbackFactory<RemoteMsgRemindService> {/* (non-Javadoc)
 * @see feign.hystrix.FallbackFactory#create(java.lang.Throwable)
 */

    @Override
    public RemoteMsgRemindService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new RemoteMsgRemindService() {

            @Override
            public TableDataInfo list(MsgRemindDTO msgRemindDTO) {
                throwable.printStackTrace();
                return null;
            }
        };
    }
}
