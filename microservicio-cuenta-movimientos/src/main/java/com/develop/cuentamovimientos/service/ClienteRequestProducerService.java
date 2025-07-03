package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.dto.ClienteDTO;

public interface ClienteRequestProducerService {
    void obtenerClientePorIdentificacion(ClienteDTO clienteDTO);
}
