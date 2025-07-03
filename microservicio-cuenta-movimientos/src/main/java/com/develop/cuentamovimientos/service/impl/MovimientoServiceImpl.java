package com.develop.cuentamovimientos.service.impl;

import com.develop.cuentamovimientos.dto.ClienteDTO;
import com.develop.cuentamovimientos.dto.CuentaDTO;
import com.develop.cuentamovimientos.dto.MovimientoClienteReporteDTO;
import com.develop.cuentamovimientos.dto.MovimientoDTO;
import com.develop.cuentamovimientos.entity.MensajeError;
import com.develop.cuentamovimientos.entity.Movimiento;
import com.develop.cuentamovimientos.exception.RecursoNoEncontradoException;
import com.develop.cuentamovimientos.repository.MovimientoRepository;
import com.develop.cuentamovimientos.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@AllArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ModelMapper modelMapper;
    private final ValidaRegistroMovimiento actualizaMovimiento;

    private final CuentaService cuentaService;
    private final ReporteMovimientoService reporteMovimientoService;
    private final ClienteRequestProducerService clienteRequestService;
    private final ClienteResponseConsumer clienteResponse;

    @Override
    public MovimientoDTO crear(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = modelMapper.map(movimientoDTO, Movimiento.class);
        return modelMapper.map(movimientoRepository.save(actualizaMovimiento.actualizarSaldoMovimiento(movimiento)), MovimientoDTO.class);
    }

    @Override
    public List<MovimientoDTO> listar() {
        return movimientoRepository.findAll().stream().map(
                movimiento -> modelMapper.map(movimiento,MovimientoDTO.class)).toList();
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

        movimientoDB.setFecha(movimientoDTO.getFecha());
        movimientoDB.setTransactionType(movimientoDTO.getTransactionType());
        movimientoDB.setValor(movimientoDTO.getValor());
        movimientoDB.setSaldo(movimientoDTO.getSaldo());

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
    public List<MovimientoClienteReporteDTO> obtenerMovimientosPorFechasYCliente(
            LocalDate fechaInicio, LocalDate fechaFin, String identificacion, String nombre) {
        try {
            // Enviar nombre al microservicio cliente-persona
            ClienteDTO clienteRequest = new ClienteDTO();
            clienteRequest.setIdentificacion(identificacion);
            clienteRequest.setNombre(nombre);
            clienteRequestService.obtenerClientePorIdentificacion(clienteRequest);

            ClienteDTO clienteDTO = clienteResponse.obtenerClienteDTO().get(5, TimeUnit.SECONDS);

            // Obtener las cuentas del cliente
            List<CuentaDTO> cuentas = cuentaService.findByIdentificacionCliente(clienteDTO.getIdentificacion());

            List<MovimientoClienteReporteDTO> resultado = new ArrayList<>();

            for (CuentaDTO cuenta : cuentas) {
                List<MovimientoDTO> movimientos = reporteMovimientoService.obtenerMovimientosEntreFechasPorCuenta(fechaInicio, fechaFin, cuenta.getNumeroCuenta());

                for (MovimientoDTO movimiento : movimientos) {
                    MovimientoClienteReporteDTO dto = new MovimientoClienteReporteDTO();
                    dto.setFecha(movimiento.getFecha());
                    dto.setCliente(clienteDTO.getNombre());
                    dto.setTipoTransaccion(movimiento.getTransactionType().toString());
                    dto.setNumeroCuenta(cuenta.getNumeroCuenta());
                    dto.setTipo(cuenta.getAccountType().toString());
                    dto.setSaldoInicial(cuenta.getSaldoInicial());
                    dto.setEstado(cuenta.isEstado());
                    dto.setMovimiento(movimiento.getValor());
                    dto.setSaldoDisponible(movimiento.getSaldo());

                    resultado.add(dto);
                }
            }

            return resultado;

        } catch (TimeoutException e) {
            log.error("⏰ Timeout esperando al microservicio cliente-persona", e);
            throw new RuntimeException("El microservicio cliente-persona no respondió a tiempo", e);
        } catch (Exception e) {
            log.error("❌ Error al obtener movimientos por cliente", e);
            throw new RuntimeException("Error al obtener movimientos del cliente", e);
        }
    }

}