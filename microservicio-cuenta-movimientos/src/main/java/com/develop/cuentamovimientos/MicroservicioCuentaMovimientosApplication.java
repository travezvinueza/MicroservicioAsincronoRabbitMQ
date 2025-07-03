package com.develop.cuentamovimientos;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit
@SpringBootApplication
public class MicroservicioCuentaMovimientosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioCuentaMovimientosApplication.class, args);
	}
}