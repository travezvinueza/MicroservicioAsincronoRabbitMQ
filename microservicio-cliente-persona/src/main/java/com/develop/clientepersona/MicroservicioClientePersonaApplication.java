package com.develop.clientepersona;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit
@SpringBootApplication
public class MicroservicioClientePersonaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioClientePersonaApplication.class, args);
	}
}