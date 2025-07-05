package com.develop.cuentamovimientos.entity;

public class MensajeError {
    public static final String RECURSO_NO_ENCONTRADO= "Recurso no encontrado";
    public static final String VALOR_DEPOSITO_NO_VALIDO="El valor del deposito debe ser mayor a cero";
    public static final String VALOR_RETIRO_NO_VALIDO = "El valor del retiro debe ser negativo y diferente de cero";
    public static final String TIPO_TRANSACCION_NO_VALIDA = "El tipo de transaccion no es valida: valores aceptados DEPOSITO/RETIRO";
    public static final String SALDO_INSUFICIENTE="Saldo insuficiente";
    public static final String CUENTA_NO_ENCONTRADA = "Cuenta no encontrada";
    public static final String VALOR_YA_REGISTRADO = "Campo ya registrado";
    public static final String FORMATO_JSON_MALFORMADO = "Formtato JSON mal formado";
}
