package com.solactive.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableAsync
@SpringBootApplication
@EnableWebSecurity
public class SolactiveCodeChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolactiveCodeChallengeApplication.class, args);
	}

}
