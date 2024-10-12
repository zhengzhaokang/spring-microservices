
package com.microservices.otmp.common.xxljob.config;


import com.microservices.otmp.common.constant.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;



/**
 * 数据权限 微服务系统中 远程访问必须携带token 需要配置
 */

/*@Configuration*/
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {

            HttpServletRequest request = attributes.getRequest();
            requestTemplate.header(Constants.TOKEN, request.getHeader(Constants.TOKEN));
        }
    }
}


