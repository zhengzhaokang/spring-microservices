package com.microservices.otmp;

import com.microservices.otmp.system.annotation.EnableOtmpFeignClients;
import com.microservices.otmp.system.annotation.EnableOtmpFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动程序
 *
 * @author lovefamily
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableEurekaClient
@MapperScan("com.microservices.otmp.*.mapper")
@EnableOtmpFeignClients
@EnableAsync
public class BankAgentApp {
    public static void main(String[] args) {
        SpringApplication.run(BankAgentApp.class, args);
    }
}