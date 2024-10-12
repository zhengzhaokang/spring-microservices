package com.microservices.otmp;


import com.microservices.otmp.system.annotation.EnableOtmpFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * word Application 启动程序
 *
 * @author shirui3
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.microservices.otmp.word.mapper")
@EnableOtmpFeignClients
public class OtmpWordApp {
    public static void main(String[] args) {
        SpringApplication.run(OtmpWordApp.class, args);
    }
}
