package com.develop.cuentamovimientos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoClienteReporteDTO {
    private LocalDate fecha;
    private String cliente;
    private String tipoTransaccion;
    private String numeroCuenta;
    private String tipo;
    private double saldoInicial;
    private boolean estado;
    private double movimiento;
    private double saldoDisponible;
}

