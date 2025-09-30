package com.example.urlgeneration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@ConfigurationPropertiesScan("com.example.urlgeneration")
public class UrlGenerationApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlGenerationApplication.class, args);
	}

}
