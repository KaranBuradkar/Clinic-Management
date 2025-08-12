package com.clinic.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicManagementApplication {

	private static final Logger log = LoggerFactory.getLogger(ClinicManagementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ClinicManagementApplication.class, args);
		System.out.println();
		System.out.println("Application Starts Executing.....");

		log.error("This is the error?");
		log.warn("This is the WARN?");
		log.info("This is the Info?");
		log.debug("This is the Debug?");
		log.trace("This is the Trace?");
	}

}
