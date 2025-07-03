package com.develop.cuentamovimientos.controller;

import com.develop.cuentamovimientos.dto.EstadoCuentaDTO;
import com.develop.cuentamovimientos.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/v1/reportes")
@AllArgsConstructor
public class EstadoCuentaController {

    private final EstadoCuentaService estadoCuentaService;

    @GetMapping
    public ResponseEntity<EstadoCuentaDTO> obtenerEstadoCuenta(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam("identificacionCliente") String identificacionCliente
    ) throws ExecutionException, InterruptedException {
        log.info("FechInicio, {}", fechaInicio);
        return new ResponseEntity<>(estadoCuentaService.obtenerEstadoCuenta(fechaInicio, fechaFin, identificacionCliente), HttpStatus.OK);
    }
}