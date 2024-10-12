package com.microservices.otmp;

import com.microservices.otmp.system.annotation.EnableOtmpFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动程序
 * 
 * @author lovefamily
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.microservices.otmp.*.mapper")
@EnableOtmpFeignClients
@EnableScheduling
public class OtmpEmailApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(OtmpEmailApp.class, args);
    }
}