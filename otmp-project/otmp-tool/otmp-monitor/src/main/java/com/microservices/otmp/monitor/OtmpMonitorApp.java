package com.microservices.otmp.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAdminServer
@SpringBootApplication
@MapperScan("com.microservices.otmp.monitor.meter.mapper")
@EnableScheduling
public class OtmpMonitorApp {
    public static void main(String[] args) {
        SpringApplication.run(OtmpMonitorApp.class, args);
    }
}