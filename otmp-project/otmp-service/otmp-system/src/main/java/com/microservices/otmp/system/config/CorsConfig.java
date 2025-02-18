package com.microservices.otmp.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
   @Bean
 public CorsFilter corsFilter() {
 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  CorsConfiguration corsConfiguration = new CorsConfiguration();
corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
 corsConfiguration.addAllowedHeader("*"); // 2允许任何头
 corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
 source.registerCorsConfiguration("/**", corsConfiguration);//4处理所有请求的跨域配置
return new CorsFilter(source);
}
}
