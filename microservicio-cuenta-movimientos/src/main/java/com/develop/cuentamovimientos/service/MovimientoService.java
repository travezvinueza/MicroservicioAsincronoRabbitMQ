package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.dto.MovimientoDTO;

import java.util.List;

public interface MovimientoService {
    MovimientoDTO crear(MovimientoDTO movimientoDTO);
    List<MovimientoDTO> listar();
    MovimientoDTO obtenerPorId(Long id);
    MovimientoDTO actualizar(MovimientoDTO movimientoDTO);
    void eliminarPorId(Long id);
}
