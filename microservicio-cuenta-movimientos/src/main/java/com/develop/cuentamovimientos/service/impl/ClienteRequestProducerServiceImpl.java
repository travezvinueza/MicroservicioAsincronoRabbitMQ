package com.develop.cuentamovimientos.service.impl;

import com.develop.cuentamovimientos.service.ClienteRequestProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClienteRequestProducerServiceImpl implements ClienteRequestProducerService {
    @Value("${spring.rabbitmq.request.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.request.routingKey}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public ClienteRequestProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void obtenerClientePorIdentificacion(String identificacion) {
        log.info(String.format("Mensage enviado %s", identificacion));
        rabbitTemplate.convertAndSend(exchange, routingKey, identificacion);
        log.info(String.format("Mensage enviado %s", identificacion));
    }
}
