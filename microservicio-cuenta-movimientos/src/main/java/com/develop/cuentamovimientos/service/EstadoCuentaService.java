package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.dto.EstadoCuentaDTO;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

public interface EstadoCuentaService {
    EstadoCuentaDTO obtenerEstadoCuenta(LocalDate fechaInicio, LocalDate fechaFin, String identificationClient, String fullName)
            throws ExecutionException, InterruptedException;
}