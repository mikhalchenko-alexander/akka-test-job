package com.linkedin.scheduler;

import com.linkedin.scheduler.config.RootConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackageClasses = RootConfig.class)
@EnableScheduling
public class LinkedInSchedulerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LinkedInSchedulerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(LinkedInSchedulerApplication.class, args);
	}

}
