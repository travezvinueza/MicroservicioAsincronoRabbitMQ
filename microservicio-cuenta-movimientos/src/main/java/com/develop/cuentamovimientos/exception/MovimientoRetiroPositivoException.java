package com.develop.cuentamovimientos.exception;

public class MovimientoRetiroPositivoException extends RuntimeException{
    private String mensaje;

    public MovimientoRetiroPositivoException(String message) {
        super(message);
        this.mensaje = mensaje;
    }
}
