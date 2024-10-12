package com.microservices.otmp.system.resolver;

import com.microservices.otmp.common.annotation.LoginUser;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 */
@Configuration
public class LoginUserHandlerResolver implements HandlerMethodArgumentResolver
{
    @Autowired
    private ISysUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        return parameter.getParameterType().isAssignableFrom(SysUser.class)
                && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
            NativeWebRequest nativeWebRequest, WebDataBinderFactory factory) throws Exception
    {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            return null;
        }
        String requestHeader = request.getHeader(Constants.CURRENT_ID);
        if (requestHeader == null) {
            return null;
        }
        // 获取用户ID
        Long userid = Long.valueOf(requestHeader);
        if (userid == null)
        {
            return null;
        }
        return userService.selectUserById(userid);
    }
}