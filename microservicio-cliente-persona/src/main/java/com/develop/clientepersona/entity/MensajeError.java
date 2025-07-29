package com.develop.clientepersona.entity;

public class MensajeError {
    public static final String RECURSO_NO_ENCONTRADO= "Recurso no encontrado";
    public static final String VALOR_DEPOSITO_NO_VALIDO="El valor del deposito debe ser mayor a cero";
    public static final String VALOR_RETIRO_NO_VALIDO = "El valor del retiro debe ser negativo y diferente de cero";
    public static final String TIPO_TRANSACCION_NO_VALIDA = "El tipo de transaccion no es valida: valores aceptados DEPOSITO/RETIRO";
    public static final String SALDO_INSUFICIENTE="Saldo insuficiente";
    public static final String CUENTA_NO_ENCONTRADA = "Cuenta no encontrada";
    public static final String VALOR_YA_REGISTRADO = "Valor ya registrado: ";
    public static final String IDENTIFICACION_NO_VALIDO = "Numero de identificacion no valido";
    public static final String FORMATO_JSON_MALFORMADO = "Formato JSON mal formado";
    public static final String GENERO_NO_VALIDO = "El campo genero debe tener valores M=masculino, F=femenino";
}
