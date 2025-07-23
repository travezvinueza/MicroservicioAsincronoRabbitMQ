package com.develop.cuentamovimientos.service.impl;

import com.develop.cuentamovimientos.dto.ClienteDTO;
import com.develop.cuentamovimientos.dto.CuentaDTO;
import com.develop.cuentamovimientos.dto.EstadoCuentaDTO;
import com.develop.cuentamovimientos.dto.ReporteCuentaMovimientoDTO;
import com.develop.cuentamovimientos.exception.RecursoNoEncontradoException;
import com.develop.cuentamovimientos.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@AllArgsConstructor
public class EstadoCuentaServiceImpl implements EstadoCuentaService {

    private final ClienteRequestProducerService clienteRequestService;
    private final ClienteResponseConsumer clienteResponse;
    private final CuentaService cuentaService;
    private final MovimientoService movimientoService;

    @Override
    public EstadoCuentaDTO obtenerEstadoCuenta(LocalDate fechaInicio, LocalDate fechaFin, String identification)
            throws ExecutionException, InterruptedException {

        clienteRequestService.obtenerClientePorIdentificacion(identification);

        // Obtener el cliente desde RabbitMQ
        CompletableFuture<ClienteDTO> clienteDTOCompletableFuture = clienteResponse.obtenerClienteDTO();
        ClienteDTO clienteDTO;
        try {
            clienteDTO = clienteDTOCompletableFuture.get(5, TimeUnit.SECONDS); // ✅ evita bloqueo
        }  catch (Exception e) {
            throw new RecursoNoEncontradoException("❌ Error al obtener cliente: " + e.getMessage());
        }

        // Obtener las cuentas
        List<CuentaDTO> cuentasDTO = cuentaService.findByIdentificacionCliente(clienteDTO.getIdentification());

        // Obtener los movimientos por cuenta
        List<ReporteCuentaMovimientoDTO> reporteCuentaMovimientos = new ArrayList<>();
        for (CuentaDTO cuentaDTO : cuentasDTO) {
            ReporteCuentaMovimientoDTO reporteCuentaMovimiento = new ReporteCuentaMovimientoDTO();
            reporteCuentaMovimiento.setCuentaDTO(cuentaDTO);
            reporteCuentaMovimiento.setMovimientoDTO(
                    movimientoService.obtenerMovimientosEntreFechasPorCuenta(fechaInicio, fechaFin, cuentaDTO.getAccountNumber())
            );
            reporteCuentaMovimientos.add(reporteCuentaMovimiento);
        }

        return new EstadoCuentaDTO(clienteDTO, reporteCuentaMovimientos);
    }
}