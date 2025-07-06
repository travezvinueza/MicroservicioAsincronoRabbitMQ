package com.develop.clientepersona.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQResponseConfig {

    @Value("${spring.rabbitmq.request.queue}")
    private String requestQueue;

    @Value("${spring.rabbitmq.response.exchange}")
    private String responseExchange;

    @Value("${spring.rabbitmq.response.routingKey}")
    private String responseRoutingKey;

    @Value("${spring.rabbitmq.response.queue}")
    private String responseQueue;

    @Bean
    public Queue clienteRequestQueue() {
        return QueueBuilder.durable(requestQueue).build();
    }

    @Bean
    public Queue clienteResponseQueue() {
        return QueueBuilder.durable(responseQueue).build();
    }

    @Bean
    public DirectExchange clienteResponseExchange() {
        return ExchangeBuilder.directExchange(responseExchange).durable(true).build();
    }

    @Bean
    public Binding clienteResponseBinding(Queue clienteResponseQueue, DirectExchange clienteResponseExchange) {
        return BindingBuilder
                .bind(clienteResponseQueue)
                .to(clienteResponseExchange)
                .with(responseRoutingKey);
    }
}

