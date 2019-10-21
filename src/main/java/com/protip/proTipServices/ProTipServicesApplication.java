package com.protip.proTipServices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ProTipServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProTipServicesApplication.class, args);
	}
}
