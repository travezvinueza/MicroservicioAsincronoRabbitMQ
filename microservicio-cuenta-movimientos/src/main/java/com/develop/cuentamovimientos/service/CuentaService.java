package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.dto.CuentaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CuentaService {

    CuentaDTO crear(CuentaDTO cuentaDTO);
    Page<CuentaDTO> listar(Pageable pageable);
    CuentaDTO obtenerPorId(Long id);
    CuentaDTO actualizar(CuentaDTO cuentaDTO);
    void eliminarPorId(Long id);
    List<CuentaDTO> findByIdentificacionCliente(String identificationClient);
}