package com.develop.cuentamovimientos.entity;

import com.develop.cuentamovimientos.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cuentas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String numeroCuenta;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private double saldoInicial;
    private boolean estado;
    private String identificacionCliente;
}
