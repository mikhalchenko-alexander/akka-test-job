package com.linkedin.scheduler.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.config")
@Getter
@Setter
public class AppProperties {

    private int profilePictureApiRouterPoolSize;
    private int userConnectionApiRouterPoolSize;
    private int userProfileApiRouterPoolSize;

    private int profilePictureDbRouterPoolSize;
    private int userConnectionDbRouterPoolSize;
    private int userProfileDbRouterPoolSize;

}
