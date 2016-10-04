package com.linkedin.scheduler.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("db.config")
@Getter
@Setter
public class DatabaseProperties {

    private String driverClass;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    private long idleConnectionTestPeriodInMinutes;
    private long idleMaxAgeInMinutes;
    private long maxConnectionsPerPartition;
    private int minConnectionsPerPartition;
    private int partitionCount;
    private int acquireIncrement;
    private int statementsCacheSize;

}
