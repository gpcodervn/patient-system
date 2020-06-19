package com.gpcoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
public class PatientRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientRegistrationApplication.class, args);
		log.info("Server is started at " + LocalDateTime.now());
	}

}
