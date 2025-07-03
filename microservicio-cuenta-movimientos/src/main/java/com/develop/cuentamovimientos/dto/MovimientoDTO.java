package com.develop.cuentamovimientos.dto;

import com.develop.cuentamovimientos.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MovimientoDTO {
    private Long id;
    @NotBlank(message = "El campo fecha no debe estar en blanco")
    private LocalDate fecha;
    @NotNull(message = "El tipo de transaccion es obligatorio")
    private TransactionType transactionType;
    @NotBlank(message = "El campo valor no debe estar en blanco")
    private double valor;
    private double saldo;
    @NotBlank(message = "El campo numero de cuenta no debe estar en blanco")
    private String numeroCuenta;
}