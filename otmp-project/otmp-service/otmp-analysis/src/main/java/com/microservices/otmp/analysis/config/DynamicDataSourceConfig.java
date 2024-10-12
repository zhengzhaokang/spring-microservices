package com.microservices.otmp.analysis.config;

import com.microservices.otmp.common.datasource.DynamicDataSource;
import com.microservices.otmp.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guowb1
 * @date 2022/9/26 16:23
 */

@Configuration
public class DynamicDataSourceConfig {

    @Bean
    public DataSource singleDataSource(ShardingProperties shardingProperties, HikariProperties hikariProperties) {
        List<DataSourceConfig> configs = shardingProperties.getConfigs();
        for (DataSourceConfig config : configs) {
            if (config.isDefaultDataSource()) {
                return DataSourceUtil.createDataSource(hikariProperties, config.getDriverClassName(), config.getJdbcUrl(), config.getUserName(), config.getPassword());
            }
        }
        return null;
    }

    //@Bean(name = "dynamicDataSource")
    //@Primary
    public DynamicDataSource dataSource(ShardingProperties shardingProperties, HikariProperties hikariProperties, @Qualifier(value = "shardingDataSource") DataSource shardingDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.SHARDING.name(), shardingDataSource);
        if (singleDataSource(shardingProperties, hikariProperties) != null) {
            targetDataSources.put(DataSourceType.SINGLE.name(), singleDataSource(shardingProperties, hikariProperties));
        }
        return new DynamicDataSource(shardingDataSource, targetDataSources);
    }
}
