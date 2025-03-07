package com.fawry.user_api.config;

import com.fawry.user_api.config.properties.DatasourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;


@Configuration
public class DBConfig {

    private final DatasourceProperties properties;

    public DBConfig(DatasourceProperties properties) {
        this.properties = properties;
    }

    @Bean("datasource_db")
    public DataSource dataSource() {
        var dataSource = new HikariDataSource();

        dataSource.setDriverClassName(properties.getDriver());
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setMaximumPoolSize(properties.getMaxPoolSize());
        dataSource.setMaxLifetime(properties.getMaxLifeTime());
        return dataSource;
    }

}
