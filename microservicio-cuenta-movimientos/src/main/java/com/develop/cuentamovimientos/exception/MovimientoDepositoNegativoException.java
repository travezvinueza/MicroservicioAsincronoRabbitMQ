package com.develop.cuentamovimientos.exception;

public class MovimientoDepositoNegativoException extends RuntimeException{
    private String mensaje;

    public MovimientoDepositoNegativoException(String message) {
        super(message);
        this.mensaje = mensaje;
    }
}
