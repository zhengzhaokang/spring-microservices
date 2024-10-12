package com.microservices.otmp.analysis.config;

import java.io.Serializable;

/**
 * @author guowb1
 * @description 数据源配置信息
 * @date 2022/8/31 17:27
 */
public class DataSourceConfig implements Serializable {

    /**
     * 数据源名称
     */
    private String name;
    /**
     * 数据源连接驱动
     */
    private String driverClassName;
    /**
     * 数据源连接地址
     */
    private String jdbcUrl;
    /**
     * 数据源账号
     */
    private String userName;
    /**
     * 数据源密码
     */
    private String password;
    /**
     * 是否默认数据源
     */
    private boolean defaultDataSource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDefaultDataSource() {
        return defaultDataSource;
    }

    public void setDefaultDataSource(boolean defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }
}
