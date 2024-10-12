package com.microservices.otmp.analysis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/8/30 9:55
 */

//@PropertySource("")
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.sharding")
public class ShardingProperties {

    List<DataSourceConfig> configs;

    public List<DataSourceConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<DataSourceConfig> configs) {
        this.configs = configs;
    }
}
