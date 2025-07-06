package com.develop.cuentamovimientos.dto;

import com.develop.cuentamovimientos.enums.AccountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CuentaDTO {
    private Long id;
    @NotBlank(message = "El numero de cuenta no debe estar en blanco")
    private String accountNumber;
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private AccountType accountType;
    @Min(value = 1, message = "El saldo inicial debe ser mayor a cero")
    private double initialBalance;
    private boolean state;
    @NotBlank(message = "La identificacion no debe estar en blanco")
    private String identificationClient;
    @NotBlank(message = "El nombre no debe estar en blanco")
    private String fullName;
}