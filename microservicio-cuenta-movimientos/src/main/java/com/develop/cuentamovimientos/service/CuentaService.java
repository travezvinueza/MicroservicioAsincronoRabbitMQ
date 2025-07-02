package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.dto.CuentaDTO;

import java.util.List;

public interface CuentaService {

    CuentaDTO crear(CuentaDTO cuentaDTO);
    List<CuentaDTO> listar();
    CuentaDTO obtenerPorId(Long id);
    CuentaDTO actualizar(CuentaDTO cuentaDTO);
    void eliminarPorId(Long id);
    List<CuentaDTO> findByIdentificacionCliente(String identificacionCliente);
}
