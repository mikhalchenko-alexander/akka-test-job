package com.anahoret;

import com.anahoret.config.RootConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackageClasses = RootConfig.class)
@EnableScheduling
public class AkkaTestJobApplication  extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AkkaTestJobApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AkkaTestJobApplication.class, args);
	}

}
