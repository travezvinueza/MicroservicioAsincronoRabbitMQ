package com.develop.clientepersona.service;

import com.develop.clientepersona.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {
    ClienteDTO crear(ClienteDTO clienteDTO);
    List<ClienteDTO> listar();
    ClienteDTO obtenerPorId(Long id);
    ClienteDTO actualizar(ClienteDTO clienteDTO);
    void eliminarPorId(Long id);
}
