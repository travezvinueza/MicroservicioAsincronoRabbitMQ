package com.develop.cuentamovimientos.exception;

public class SaldoInsuficienteException extends RuntimeException{
    private String mensaje;
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }

}
