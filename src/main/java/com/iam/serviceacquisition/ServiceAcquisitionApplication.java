package com.iam.serviceacquisition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class ServiceAcquisitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAcquisitionApplication.class, args);
	}

}
