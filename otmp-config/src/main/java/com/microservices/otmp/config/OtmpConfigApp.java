package com.microservices.otmp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置中心
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class OtmpConfigApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(OtmpConfigApp.class, args);
    }
}