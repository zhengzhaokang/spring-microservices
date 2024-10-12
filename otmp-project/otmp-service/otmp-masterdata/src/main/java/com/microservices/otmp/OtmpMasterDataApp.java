package com.microservices.otmp;


import com.microservices.otmp.system.annotation.EnableOtmpFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Accrual Application 启动程序
 *
 * @author lovefamily
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.microservices.otmp.masterdata.mapper")
@EnableOtmpFeignClients
@EnableCaching
public class OtmpMasterDataApp {
    public static void main(String[] args) {
        SpringApplication.run(OtmpMasterDataApp.class, args).start();
    }
}
