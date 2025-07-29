package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.dto.ClienteDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Getter
@Service
public class ClienteResponseConsumer {

    private CompletableFuture<ClienteDTO> clienteDTOCompletableFuture = new CompletableFuture<>();

    @RabbitListener(queues = "${spring.rabbitmq.response.queue}")
    public void recibirClienteDTO(ClienteDTO clienteDTO) {
        log.info("Cliente recibido: {}", clienteDTO);

        clienteDTOCompletableFuture.complete(clienteDTO);
        clienteDTOCompletableFuture = new CompletableFuture<>();
    }

    public CompletableFuture<ClienteDTO> obtenerClienteDTO() {
        return clienteDTOCompletableFuture;
    }
}