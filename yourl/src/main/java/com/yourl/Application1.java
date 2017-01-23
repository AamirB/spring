package com.yourl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class })
@SpringBootApplication
public class Application1 {

	public static void main(String[] args) {
		SpringApplication.run(Application1.class, args);
	}

}