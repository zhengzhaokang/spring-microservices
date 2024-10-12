package com.microservices.otmp.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class OtmpEurekaApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(OtmpEurekaApp.class, args);
    }
}