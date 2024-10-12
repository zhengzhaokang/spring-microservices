package com.microservices.otmp.financing.config;

import com.microservices.otmp.common.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.microservices.otmp.common.interceptor.AuthHandlerInterceptor;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AuthHandlerInterceptor authHandlerInterceptor;

    @Autowired
    private LogInterceptor logInterceptor;

    private static final String[] authInterceptorWhiteList = {"/login", "/login/slide", "/logininfor/save","/user/register", "/system/v2/api-docs",
            "/get/token", "/druid/**",
            "/swagger-resources/**", "/swagger-ui/**", "/v3/**", "/v2/**", "/error",
            "/logo.png", "/", "/js/**", "/css/**", "/assets/**", "/index.html", "/avatar2.jpg"
            , "/api/flow/img/viewProcessImg", "/api/flow/img/generateImage", "/adfs/**", "/anchor/supplier/info/simple"};

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // 默认语言
        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
        lci.setParamName("lang");
        return lci;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
        //registry.addInterceptor(authHandlerInterceptor).excludePathPatterns(authInterceptorWhiteList);
    }
}
