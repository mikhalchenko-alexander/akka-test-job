package com.anahoret.config;

import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
@ComponentScan("com.anahoret.services")
public class RootConfig {

    @Bean(destroyMethod = "shutdown")
    public ActorSystem actorSystem() {
        return ActorSystem.create("ActorSystem", ConfigFactory.load());
    }

}
