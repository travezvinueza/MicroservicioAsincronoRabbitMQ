package com.develop.clientepersona.service;

import com.develop.clientepersona.dto.ClienteDTO;
import com.develop.clientepersona.entity.Cliente;
import com.develop.clientepersona.entity.MensajeError;
import com.develop.clientepersona.exception.RecursoNoEncontradoException;
import com.develop.clientepersona.repository.ClienteRepository;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class ClienteRequestConsumerService {
    private ClienteRepository clienteRepository;
    private ClienteResponseProducerService clienteResponseService;


    @RabbitListener(queues = "${spring.rabbitmq.request.queue}", ackMode = "MANUAL")
    public void buscarCliente(ClienteDTO clienteDTO, Channel channel,
                              @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            if (clienteDTO.getIdentificacion() != null) {
                log.info("Buscando por identificación: {}", clienteDTO.getIdentificacion());
                Cliente clienteDb = clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion())
                        .orElseThrow(() -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO));
                clienteResponseService.responseCliente(clienteDb);

            } else if (clienteDTO.getNombre() != null) {
                String[] partes = clienteDTO.getNombre().trim().split(" ");
                String nombre = partes[0];

                Cliente clienteDb = clienteRepository.findByNombre(nombre)
                        .orElseThrow(() -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO));
                clienteResponseService.responseCliente(clienteDb);

            } else {
                log.warn("Petición vacía recibida");
            }

            channel.basicAck(tag, false);

        } catch (Exception e) {
            log.error("❌ Error en el consumidor: {}", e.getMessage(), e);
            try {
                channel.basicNack(tag, false, false); // ❌ No requeue
            } catch (IOException ioException) {
                log.error("❌ Error al enviar NACK: {}", ioException.getMessage(), ioException);
            }
        }
    }
}
