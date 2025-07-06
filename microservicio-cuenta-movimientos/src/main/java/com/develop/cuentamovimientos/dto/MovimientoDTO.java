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
    private LocalDate date;
    @NotNull(message = "El tipo de transaccion es obligatorio")
    private TransactionType transactionType;
    @NotBlank(message = "El campo valor no debe estar en blanco")
    private double value;
    private double balance;
    @NotBlank(message = "El campo numero de cuenta no debe estar en blanco")
    private String accountNumber;
}