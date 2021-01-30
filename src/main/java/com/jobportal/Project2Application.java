package com.jobportal;

import org.apache.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Project2Application {
	
	public final static Logger log = Logger.getLogger(Project2Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Project2Application.class, args);
	}

}
