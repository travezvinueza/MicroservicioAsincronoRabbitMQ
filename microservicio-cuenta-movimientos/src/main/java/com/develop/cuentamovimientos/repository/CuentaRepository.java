package com.develop.cuentamovimientos.repository;

import com.develop.cuentamovimientos.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    List<Cuenta> findByIdentificacionCliente(String identificacionCliente);
}
