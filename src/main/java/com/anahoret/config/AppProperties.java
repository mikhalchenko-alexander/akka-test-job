package com.anahoret.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.config")
@Getter
@Setter
public class AppProperties {

    private boolean userProfile;
    private int routerPoolSize;

}
