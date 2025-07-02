package com.develop.cuentamovimientos.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RespuestaError {
    private LocalDateTime fechaError;
    private String mensajeTecnico;
    private String path;
    private String mensajeUsuario;
}
