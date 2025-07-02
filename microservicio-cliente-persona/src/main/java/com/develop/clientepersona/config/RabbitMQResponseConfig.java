package com.develop.clientepersona.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQResponseConfig {

    @Value("${spring.rabbitmq.response.exchange}")
    private String responseExchange;

    @Value("${spring.rabbitmq.response.queue}")
    private String responseQueue;

    @Value("${spring.rabbitmq.response.routingKey}")
    private String responseRoutingKey;

    @Bean
    public DirectExchange clienteResponseExchange() {
        return new DirectExchange(responseExchange);
    }

    @Bean
    public Queue clienteResponseQueue() {
        return new Queue(responseQueue);
    }

    @Bean
    public Binding clienteResponseBinding(Queue clienteResponseQueue, DirectExchange clienteResponseExchange) {
        return BindingBuilder
                .bind(clienteResponseQueue)
                .to(clienteResponseExchange)
                .with(responseRoutingKey);
    }
}

