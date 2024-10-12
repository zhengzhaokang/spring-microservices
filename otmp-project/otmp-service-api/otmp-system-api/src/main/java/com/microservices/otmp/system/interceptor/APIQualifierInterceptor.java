package com.microservices.otmp.system.interceptor;

import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.utils.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class APIQualifierInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            log.info("APIQualifierInterceptor in method");
            template.header(Constants.REQUEST_SOURCE, Constants.REQUEST_SYSTEM);
        }

}