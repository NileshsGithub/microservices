package com.example.person_consumer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PersonConsumerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonConsumerServiceApplication.class, args);
	}

}
