package com.develop.clientepersona.service;

import com.develop.clientepersona.dto.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    ClienteDTO crear(ClienteDTO clienteDTO);
    Page<ClienteDTO> listar(Pageable pageable);
    ClienteDTO obtenerPorId(Long id);
    ClienteDTO actualizar(ClienteDTO clienteDTO);
    void eliminarPorId(Long id);
}
