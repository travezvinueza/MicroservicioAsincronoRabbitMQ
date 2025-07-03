package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.dto.MovimientoClienteReporteDTO;
import com.develop.cuentamovimientos.dto.MovimientoDTO;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoService {
    MovimientoDTO crear(MovimientoDTO movimientoDTO);
    List<MovimientoDTO> listar();
    MovimientoDTO obtenerPorId(Long id);
    MovimientoDTO actualizar(MovimientoDTO movimientoDTO);
    void eliminarPorId(Long id);

    List<MovimientoClienteReporteDTO> obtenerMovimientosPorFechasYCliente(LocalDate fechaInicio, LocalDate fechaFin, String identificacion, String nombre);
}
