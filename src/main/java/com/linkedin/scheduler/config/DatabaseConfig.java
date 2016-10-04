package com.linkedin.scheduler.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DatabaseProperties.class)
public class DatabaseConfig {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(databaseProperties.getDriverClass());
        dataSource.setJdbcUrl(databaseProperties.getJdbcUrl());
        dataSource.setUsername(databaseProperties.getJdbcUsername());
        dataSource.setPassword(databaseProperties.getJdbcPassword());
        dataSource.setIdleConnectionTestPeriodInMinutes(databaseProperties.getIdleConnectionTestPeriodInMinutes());
        dataSource.setIdleMaxAgeInMinutes(databaseProperties.getIdleMaxAgeInMinutes());
        dataSource.setMaxConnectionsPerPartition(databaseProperties.getMinConnectionsPerPartition());
        dataSource.setMinConnectionsPerPartition(databaseProperties.getMinConnectionsPerPartition());
        dataSource.setPartitionCount(databaseProperties.getPartitionCount());
        dataSource.setAcquireIncrement(databaseProperties.getAcquireIncrement());
        dataSource.setStatementsCacheSize(databaseProperties.getStatementsCacheSize());
        return dataSource;
    }

}
