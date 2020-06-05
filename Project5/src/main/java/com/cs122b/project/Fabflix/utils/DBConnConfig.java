package com.cs122b.project.Fabflix.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DBConnConfig {
    @Bean(name="readbean")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="writebean")
    @ConfigurationProperties(prefix="spring.datasource2")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }
}
