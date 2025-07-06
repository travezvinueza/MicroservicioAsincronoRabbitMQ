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

@Slf4j
@Service
@AllArgsConstructor
public class EstadoCuentaServiceImpl implements EstadoCuentaService {

    private final ClienteRequestProducerService clienteRequestService;
    private final ClienteResponseConsumer clienteResponse;
    private final CuentaService cuentaService;
    private final MovimientoService movimientoService;

    @Override
    public EstadoCuentaDTO obtenerEstadoCuenta(LocalDate fechaInicio, LocalDate fechaFin, String fullName)
            throws ExecutionException, InterruptedException {

        clienteRequestService.obtenerClientePorNombreCompleto(fullName);

        // Obtener el cliente desde RabbitMQ
        CompletableFuture<ClienteDTO> clienteDTOCompletableFuture = clienteResponse.obtenerClienteDTO();
        ClienteDTO clienteDTO = clienteDTOCompletableFuture.get();
        log.info("Final DTO: {}", clienteDTO);

        if (clienteDTO == null || clienteDTO.getIdentification() == null) {
            throw new RecursoNoEncontradoException("❌ Cliente no encontrado con el nombre proporcionado");
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

        // Devolver directamente el nuevo DTO sin asignarlo a una variable local
        return new EstadoCuentaDTO(clienteDTO, reporteCuentaMovimientos);
    }
}