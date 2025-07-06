package com.develop.cuentamovimientos.service.impl;

import com.develop.cuentamovimientos.dto.MovimientoDTO;
import com.develop.cuentamovimientos.entity.MensajeError;
import com.develop.cuentamovimientos.entity.Movimiento;
import com.develop.cuentamovimientos.exception.RecursoNoEncontradoException;
import com.develop.cuentamovimientos.repository.MovimientoRepository;
import com.develop.cuentamovimientos.service.*;
import com.develop.cuentamovimientos.util.ValidaRegistroMovimiento;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ModelMapper modelMapper;
    private final ValidaRegistroMovimiento actualizaMovimiento;


    @Override
    public MovimientoDTO crear(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = modelMapper.map(movimientoDTO, Movimiento.class);
        return modelMapper.map(movimientoRepository.save(actualizaMovimiento.actualizarSaldoMovimiento(movimiento)), MovimientoDTO.class);
    }

    @Override
    public Page<MovimientoDTO> listar(Pageable pageable) {
        Page<Movimiento> movimientos = movimientoRepository.findAll(pageable);
        return movimientos.map(movimiento -> modelMapper.map(movimiento, MovimientoDTO.class));
    }

    @Override
    public MovimientoDTO obtenerPorId(Long id) {
        Movimiento movimiento= movimientoRepository.findById(id).orElseThrow(
                ()-> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO)
        );
        return modelMapper.map(movimiento,MovimientoDTO.class);
    }

    @Override
    public MovimientoDTO actualizar(MovimientoDTO movimientoDTO) {
        Movimiento movimientoDB = modelMapper.map(obtenerPorId(movimientoDTO.getId()), Movimiento.class);

        movimientoDB.setDate(movimientoDTO.getDate());
        movimientoDB.setTransactionType(movimientoDTO.getTransactionType());
        movimientoDB.setValue(movimientoDTO.getValue());
        movimientoDB.setBalance(movimientoDTO.getBalance());

        return modelMapper.map(movimientoRepository.save(movimientoDB),MovimientoDTO.class);
    }

    @Override
    public void eliminarPorId(Long id) {
        movimientoRepository.findById(id).orElseThrow(
                ()-> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO)
        );
        movimientoRepository.deleteById(id);
    }

    @Override
    public List<MovimientoDTO> obtenerMovimientosEntreFechasPorCuenta(LocalDate fechaInicio, LocalDate fechaFin, String accountNumber) {
        return movimientoRepository.obtenerMovimientosEntreFechasPorAccountNumber(fechaInicio, fechaFin, accountNumber).stream().map(
                movimiento -> modelMapper.map(movimiento,MovimientoDTO.class)).toList();
    }

}