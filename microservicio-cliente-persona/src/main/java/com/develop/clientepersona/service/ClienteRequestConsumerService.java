package com.develop.clientepersona.service;

import com.develop.clientepersona.dto.ClienteDTO;
import com.develop.clientepersona.entity.Cliente;
import com.develop.clientepersona.entity.MensajeError;
import com.develop.clientepersona.exception.RecursoNoEncontradoException;
import com.develop.clientepersona.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ClienteRequestConsumerService {
    private final ClienteRepository clienteRepository;
    private final ClienteResponseProducerService clienteResponseService;
    private final ModelMapper modelMapper;

    @RabbitListener(queues = "${spring.rabbitmq.request.queue}")
    public void buscarCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getIdentificacion() != null) {
            log.info("Buscando por identificación: {}", clienteDTO.getIdentificacion());
            Cliente clienteDb = clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion())
                    .orElseThrow(() -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO));

            ClienteDTO clienteDTOResponse = modelMapper.map(clienteDb, ClienteDTO.class);
            clienteResponseService.responseCliente(clienteDTOResponse);

        } else if (clienteDTO.getNombre() != null) {
            String[] partes = clienteDTO.getNombre().trim().split(" ");
            String nombre = partes[0];

            Cliente clienteDb = clienteRepository.findByNombre(nombre)
                    .orElseThrow(() -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO));

            ClienteDTO clienteDTOResponse = modelMapper.map(clienteDb, ClienteDTO.class);
            clienteResponseService.responseCliente(clienteDTOResponse);

        } else {
            log.warn("Petición vacía recibida");
        }
    }
}
