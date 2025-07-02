package com.develop.clientepersona.service;

import com.develop.clientepersona.entity.Cliente;
import com.develop.clientepersona.entity.MensajeError;
import com.develop.clientepersona.exception.RecursoNoEncontradoException;
import com.develop.clientepersona.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ClienteRequestConsumerService {
    private ClienteRepository clienteRepository;
    private ClienteResponseProducerService clienteResponseService;


    @RabbitListener(queues = "${spring.rabbitmq.request.queue}")
    public void buscarCliente(String identificacion) {
        Cliente clienteDb= clienteRepository.findByIdentificacion(identificacion) .orElseThrow(
                ()-> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO.toString())
        );
        //enviar a la cola cliente response

        clienteResponseService.responseCliente(clienteDb);

        log.info(String.format("Identifiacion: %s recibida", identificacion));
        log.info("Cliente: {}", clienteDb);
    }
}
