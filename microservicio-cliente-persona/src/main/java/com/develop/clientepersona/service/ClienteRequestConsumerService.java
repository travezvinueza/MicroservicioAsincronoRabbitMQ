package com.develop.clientepersona.service;

import com.develop.clientepersona.dto.ClienteDTO;
import com.develop.clientepersona.entity.Cliente;
import com.develop.clientepersona.repository.ClienteRepository;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class ClienteRequestConsumerService {
    private final ClienteRepository clienteRepository;
    private final ClienteResponseProducerService clienteResponseService;
    private final ModelMapper modelMapper;

    @RabbitListener(queues = "${spring.rabbitmq.request.queue}")
    public void buscarCliente(String input, Message message, Channel channel) throws IOException {
        try {
            log.info("Mensaje recibido: {}", input);

            Cliente clienteDb;
            if (input.matches("\\d+")) {
                log.info("Buscando por identificación...");
                clienteDb = clienteRepository.findByIdentification(input)
                        .orElse(null);
            } else {
                log.info("Buscando por nombre completo...");
                String fullName = input.trim();
                clienteDb = clienteRepository.findByFullName(fullName)
                        .orElse(null);
            }

            ClienteDTO clienteDTOResponse = (clienteDb != null)
                    ? modelMapper.map(clienteDb, ClienteDTO.class)
                    : new ClienteDTO();

            clienteResponseService.responseCliente(clienteDTOResponse);

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("Error procesando mensaje", e);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

}
