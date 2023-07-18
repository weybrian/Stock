package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.example.demo", "com.example.model", "com.example.controller", "com.example.service",
		"com.example.dao" })
@EntityScan({ "com.example.demo", "com.example.model", "com.example.controller", "com.example.service",
		"com.example.dao" })
public class StockApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}

}
