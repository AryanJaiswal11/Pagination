package com.CodeVector.DemoTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // Activates background execution threads capability
public class DemoTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoTaskApplication.class, args);
	}

}
