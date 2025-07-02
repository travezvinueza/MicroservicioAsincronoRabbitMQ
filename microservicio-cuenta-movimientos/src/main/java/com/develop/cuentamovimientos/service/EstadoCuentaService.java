package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.dto.ClienteDTO;
import com.develop.cuentamovimientos.dto.CuentaDTO;
import com.develop.cuentamovimientos.dto.EstadoCuentaDTO;
import com.develop.cuentamovimientos.dto.ReporteCuentaMovimientoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class EstadoCuentaService {

    private ClienteRequestProducerService clienteRequestService;
    private ClienteResponseConsumer clienteResponse;
    private CuentaService cuentaService;
    private ReporteMovimientoService movimientoService;

    public EstadoCuentaDTO obtenerEstadoCuenta(LocalDate fechaInicio,LocalDate fechaFin,String identificacionCliente) throws ExecutionException, InterruptedException {
        //enviar la identificacion a rabitmq
        clienteRequestService.obtenerClientePorIdentificacion(identificacionCliente);

        //obtener el cliente desde rabittmq
        CompletableFuture<ClienteDTO> clienteDTOCompletableFuture = clienteResponse.obtenerClienteDTO();
        log.info("final dto , {}", clienteDTOCompletableFuture.get());

        //obtener las cuentas
        List<CuentaDTO> cuentasDTO = cuentaService.findByIdentificacionCliente(clienteDTOCompletableFuture.get().identificacion());

        //obtener los movimientos por cuenta
        List<ReporteCuentaMovimientoDTO> reporteCuentaMovimientos = new ArrayList<>();

        for (CuentaDTO cuentaDTO : cuentasDTO
        ) {
            ReporteCuentaMovimientoDTO reporteCuentaMovimiento = new ReporteCuentaMovimientoDTO();
            reporteCuentaMovimiento.setCuentaDTO(cuentaDTO);
            reporteCuentaMovimiento.setMovimientoDTO(
                    movimientoService.obtenerMovimientosEntreFechasPorCuenta(fechaInicio, fechaFin, cuentaDTO.getNumeroCuenta())
            );
            reporteCuentaMovimientos.add(reporteCuentaMovimiento);

        }

        EstadoCuentaDTO estadoCuentaDTO = new EstadoCuentaDTO(clienteDTOCompletableFuture.get(),
                reporteCuentaMovimientos
        );
        return estadoCuentaDTO;

    }

}
