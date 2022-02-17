package com.govi.batch.springbatchlearning;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.govi.batch.springbatchlearning"})
public class SpringbatchLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchLearningApplication.class, args);
	}

}
