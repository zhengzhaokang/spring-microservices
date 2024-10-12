package com.microservices.otmp;

import com.microservices.otmp.system.annotation.EnableOtmpFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaClient
@EnableOtmpFeignClients
@EnableCaching
@MapperScan("com.microservices.otmp.*.mapper")
@SpringBootApplication
public class OtmpFinancingApp {
    public static void main(String[] args) {
        SpringApplication.run(OtmpFinancingApp.class,args);
    }
}
