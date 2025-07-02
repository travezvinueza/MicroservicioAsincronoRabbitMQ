package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.dto.MovimientoDTO;
import com.develop.cuentamovimientos.repository.MovimientoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReporteMovimientoService {
    private MovimientoRepository movimientoRepository;
    private ModelMapper modelMapper;

    public List<MovimientoDTO> obtenerMovimientosEntreFechasPorCuenta(LocalDate fechaInicio, LocalDate fechaFin, String numeroCuenta) {
        return movimientoRepository.obtenerMovimientosEntreFechasPorCuenta(fechaInicio, fechaFin, numeroCuenta).stream().map(
                movimiento -> modelMapper.map(movimiento,MovimientoDTO.class)).toList();
    }
}
