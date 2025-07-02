package com.develop.clientepersona.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RespuestaError {
    private LocalDateTime timestamp;
    private String mensajeTecnico;
    private String path;
    private String MensajeUsuario;
}
