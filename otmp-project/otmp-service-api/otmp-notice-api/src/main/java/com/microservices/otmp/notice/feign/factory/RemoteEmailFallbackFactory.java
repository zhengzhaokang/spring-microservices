package com.microservices.otmp.notice.feign.factory;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.notice.domain.dto.EmailReceipentDTO;
import com.microservices.otmp.notice.domain.dto.EmailSendDTO;
import com.microservices.otmp.notice.feign.RemoteEmailService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteEmailFallbackFactory implements FallbackFactory<RemoteEmailService> {

    @Override
    public RemoteEmailService create(Throwable throwable) {
        return new RemoteEmailService() {
            @Override
            public ResultDTO<Object> sendEmail(EmailSendDTO emailSendHistoryRequest) {
                return null;
            }

            @Override
            public ResultDTO getSendToList(EmailReceipentDTO emailSendHistoryRequest) {
                return null;
            }

            @Override
            public ResultDTO<Object> asynSendEmail(EmailSendDTO emailSendDTO) {
                return null;
            }

        };
    }
}
