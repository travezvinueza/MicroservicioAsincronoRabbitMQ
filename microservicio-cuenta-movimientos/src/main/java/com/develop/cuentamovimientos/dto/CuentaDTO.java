package com.develop.cuentamovimientos.dto;

import com.develop.cuentamovimientos.enums.AccountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CuentaDTO {
    private Long id;
    @NotBlank(message = "El numero de cuenta no debe estar en blanco")
    private String numeroCuenta;
    private AccountType accountType;
    @Min(value = 1, message = "El saldo inicial debe ser mayor a cero")
    private double saldoInicial;
    private boolean estado;
    @NotBlank(message = "La identificacion no debe estar en blanco")
    private String identificacionCliente;
}
