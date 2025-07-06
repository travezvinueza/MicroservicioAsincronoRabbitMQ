package com.develop.cuentamovimientos.entity;

import com.develop.cuentamovimientos.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cuentas")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number_account", unique = true)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;
    @Column(name = "initial_balance")
    private double initialBalance;
    @Column(name = "state")
    private boolean state;
    @Column(name = "identification_client", unique = true)
    private String identificationClient;
}