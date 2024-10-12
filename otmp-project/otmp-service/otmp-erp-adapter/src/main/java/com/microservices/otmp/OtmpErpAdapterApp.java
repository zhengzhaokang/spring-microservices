package com.microservices.otmp;


import com.microservices.otmp.system.annotation.EnableOtmpFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * word Application 启动程序
 *
 * @author shirui3
 */
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
@EnableOtmpFeignClients
@MapperScan("com.microservices.otmp.erp.mapper")
@EnableScheduling
public class OtmpErpAdapterApp {
    public static void main(String[] args) {
        SpringApplication.run(OtmpErpAdapterApp.class, args);
    }
}
