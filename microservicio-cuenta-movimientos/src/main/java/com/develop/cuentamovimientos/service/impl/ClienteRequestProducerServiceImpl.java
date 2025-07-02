package com.develop.cuentamovimientos.service.impl;

import com.develop.cuentamovimientos.service.ClienteRequestProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteRequestProducerServiceImpl implements ClienteRequestProducerService {
    @Value("${spring.rabbitmq.request.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.request.routingKey}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void obtenerClientePorIdentificacion(String identificacion) {
        log.info("Mensage enviado: {}", identificacion);
        rabbitTemplate.convertAndSend(exchange, routingKey, identificacion);
        log.info("Mensage enviado: {}", identificacion);
    }
}
