package com.develop.cuentamovimientos.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.request.exchange}")
    private String requestExchange;

    @Value("${spring.rabbitmq.request.queue}")
    private String requestQueue;

    @Value("${spring.rabbitmq.request.routingKey}")
    private String requestRoutingKey;

    @Bean
    public DirectExchange clienteRequestExchange() {
        return new DirectExchange(requestExchange);
    }

    @Bean
    public Queue clienteRequestQueue() {
        return new Queue(requestQueue);
    }

    @Bean
    public Binding clienteRequestBinding(Queue clienteRequestQueue, DirectExchange clienteRequestExchange) {
        return BindingBuilder
                .bind(clienteRequestQueue)
                .to(clienteRequestExchange)
                .with(requestRoutingKey);
    }
}

