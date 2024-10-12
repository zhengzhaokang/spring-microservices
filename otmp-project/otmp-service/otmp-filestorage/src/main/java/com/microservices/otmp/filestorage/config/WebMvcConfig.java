package com.microservices.otmp.filestorage.config;

import com.microservices.otmp.common.interceptor.LogInterceptor;
import com.microservices.otmp.filestorage.resolver.LoginUserHandlerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * MVC配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginUserHandlerResolver loginUserHandlerResolver;

    @Autowired
    private LogInterceptor logInterceptor;

//    @Autowired
//    private AuthHandlerInterceptor authHandlerInterceptor;


    /**
     * 用户登录拦截白名单
     */
    private static final String[] authInterceptorWhiteList = {"/login", "/login/slide", "/logininfor/save","/user/register", "/system/v2/api-docs",
            "/get/token", "/druid/**",
            "/swagger-resources/**", "/swagger-ui/**", "/v3/**", "/v2/**", "/error",
            "/logo.png", "/", "/js/**", "/css/**", "/assets/**", "/index.html", "/avatar2.jpg"
            , "/api/flow/img/viewProcessImg", "/api/flow/img/generateImage", "/adfs/**"};


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
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(authHandlerInterceptor).excludePathPatterns(authInterceptorWhiteList);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*")
                .exposedHeaders("access-control-allow-headers",
                        "access-control-allow-methods",
                        "access-control-allow-origin",
                        "access-control-max-age", "X-Frame-Options",
                        "Content-Disposition");

    }

}
