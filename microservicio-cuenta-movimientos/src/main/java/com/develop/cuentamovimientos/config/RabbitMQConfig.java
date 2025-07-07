package com.develop.cuentamovimientos.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.request.queue}")
    private String requestQueue;

    @Value("${spring.rabbitmq.request.exchange}")
    private String requestExchange;

    @Value("${spring.rabbitmq.request.routingKey}")
    private String requestRoutingKey;

    @Value("${spring.rabbitmq.response.queue}")
    private String responseQueue;

    @Bean
    public Queue clienteRequestQueue() {
        return QueueBuilder.durable(requestQueue).build();
    }

    @Bean
    public DirectExchange clienteRequestExchange() {
        return ExchangeBuilder.directExchange(requestExchange).durable(true).build();
    }

    @Bean
    public Queue clienteResponseQueue() {
        return QueueBuilder.durable(responseQueue).build();
    }

    @Bean
    public Binding clienteRequestBinding() {
        return BindingBuilder
                .bind(clienteRequestQueue())
                .to(clienteRequestExchange())
                .with(requestRoutingKey);
    }

}