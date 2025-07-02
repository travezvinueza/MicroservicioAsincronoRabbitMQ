package com.develop.clientepersona.exception;

public class CedulaInvalidaException extends  RuntimeException{
    private String mensaje;

    public CedulaInvalidaException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }
}
