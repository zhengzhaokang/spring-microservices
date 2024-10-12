package com.microservices.otmp.analysis.config;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public final class DataSourceUtil {

    private DataSourceUtil() {
    }

    public static DataSource createDataSource(final HikariProperties hikariProperties,
                                              final String driverClassName,
                                              final String jdbcUrl,
                                              final String userName,
                                              final String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return hikariProperties.dataSource(dataSource);
    }
}
