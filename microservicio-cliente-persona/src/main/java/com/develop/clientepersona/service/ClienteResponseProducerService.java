package com.develop.clientepersona.service;

import com.develop.clientepersona.dto.ClienteDTO;
import com.develop.clientepersona.entity.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteResponseProducerService {
    @Value("${spring.rabbitmq.response.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.response.routingKey}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void responseCliente(ClienteDTO clienteDTO) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, clienteDTO);
            log.info("Cliente enviado correctamente: {}", clienteDTO);
        } catch (Exception e) {
            log.error("Error al enviar cliente: {}", e.getMessage(), e);
        }
    }
}
