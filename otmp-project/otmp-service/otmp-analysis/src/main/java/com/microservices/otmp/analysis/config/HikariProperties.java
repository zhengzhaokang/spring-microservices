package com.microservices.otmp.analysis.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author guowb1
 * @date 2022/8/19 18:57
 */

@Configuration
public class HikariProperties {
    @Value("${spring.datasource.hikari.maxPoolSize:10}")
    private int maxPoolSize;

    @Value("${spring.datasource.hikari.minIdle:10}")
    private int minIdle;

    @Value("${spring.datasource.hikari.maxLifetime:900000}")
    private int maxLifetime;

    public HikariDataSource dataSource(HikariDataSource datasource) {
        datasource.setMaximumPoolSize(maxPoolSize);
        datasource.setMinimumIdle(minIdle);
        datasource.setMaxLifetime(maxLifetime);
        return datasource;
    }
}
