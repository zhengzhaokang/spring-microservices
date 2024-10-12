package com.microservices.otmp;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.microservices.otmp.common.swagger.annotation.EnableCustomSwagger2;
import com.microservices.otmp.system.annotation.EnableOtmpFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableCustomSwagger2
@EnableOtmpFeignClients
@EnableDiscoveryClient
@MapperScan(value = {"com.microservices.otmp.**.mapper"})
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableScheduling
public class OtmpReportApp {
    public static void main(String[] args) {

        SpringApplication.run(OtmpReportApp.class, args);
    }
}