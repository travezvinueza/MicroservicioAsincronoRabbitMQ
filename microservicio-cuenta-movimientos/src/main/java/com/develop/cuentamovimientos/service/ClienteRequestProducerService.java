package com.develop.cuentamovimientos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteRequestProducerService {
    @Value("${spring.rabbitmq.request.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.request.routingKey}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void obtenerClientePorIdentificacion(String identification) {
        try {
            log.info("Mensage enviado: {}", identification);
            rabbitTemplate.convertAndSend(exchange, routingKey, identification);
        } catch (Exception e) {
            log.error("Error al enviar mensaje: {}", e.getMessage(), e);
        }
    }

    public void obtenerClientePorNombreCompleto(String fullName) {
        rabbitTemplate.convertAndSend(exchange, routingKey, fullName);
    }

}