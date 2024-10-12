package com.microservices.otmp;

import com.microservices.otmp.system.annotation.EnableOtmpFeignClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动程序
 *
 * @author lovefamily
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableOtmpFeignClients
@EnableCaching
@MapperScan("com.microservices.otmp.*.mapper")
@Slf4j
public class OtmpSystemApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        try {
            SpringApplication.run(OtmpSystemApp.class, args);
        } catch (Exception exception) {
            log.error("com.microservices.otmp.OtmpSystemApp=====>启动失败,失败原因为:", exception);
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OtmpSystemApp.class);
    }
}