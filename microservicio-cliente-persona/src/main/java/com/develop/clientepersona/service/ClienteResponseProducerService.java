package com.develop.clientepersona.service;

import com.develop.clientepersona.dto.ClienteDTO;
import com.develop.clientepersona.entity.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClienteResponseProducerService {
    @Value("${spring.rabbitmq.response.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.response.routingKey}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public ClienteResponseProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void responseCliente(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO(cliente.getId(), cliente.getCreationDate(), cliente.getNombre(), cliente.getGenderPerson(), cliente.getEdad(), cliente.getIdentificacion(),cliente.getDireccion(),cliente.getTelefono(),cliente.getPassword(), cliente.isEstado());
        log.info(String.format("Cliente enviado %s", clienteDTO));
        rabbitTemplate.convertAndSend(exchange, routingKey, cliente);
    }
}
