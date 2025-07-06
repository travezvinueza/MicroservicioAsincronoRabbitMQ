package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.dto.MovimientoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoService {
    MovimientoDTO crear(MovimientoDTO movimientoDTO);
    Page<MovimientoDTO> listar(Pageable pageable);
    MovimientoDTO obtenerPorId(Long id);
    MovimientoDTO actualizar(MovimientoDTO movimientoDTO);
    void eliminarPorId(Long id);
    List<MovimientoDTO> obtenerMovimientosEntreFechasPorCuenta(LocalDate fechaInicio, LocalDate fechaFin, String numeroCuenta);
}