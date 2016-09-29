package com.anahoret;

import com.anahoret.config.RootConfig;
import com.anahoret.services.InitService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = RootConfig.class)
public class AkkaTestJobApplication {

	public AkkaTestJobApplication(InitService initService) {
		initService.initialCheck();
	}

	public static void main(String[] args) {
		SpringApplication.run(AkkaTestJobApplication.class, args);
	}

}
