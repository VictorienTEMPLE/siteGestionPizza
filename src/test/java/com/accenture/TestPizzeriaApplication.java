package com.accenture;

import org.springframework.boot.SpringApplication;

public class TestPizzeriaApplication {

	public static void main(String[] args) {
		SpringApplication.from(PizzeriaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
