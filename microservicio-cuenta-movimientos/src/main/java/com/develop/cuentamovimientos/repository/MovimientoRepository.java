package com.develop.cuentamovimientos.repository;

import com.develop.cuentamovimientos.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    @Query("SELECT m FROM Movimiento m WHERE m.accountNumber = ?1 ORDER BY m.id DESC LIMIT 1")
    Optional<Movimiento> obtenerUltimoMovimientoPorAccountNumber(String accountNumber);
    @Query("SELECT m FROM Movimiento m WHERE m.date BETWEEN :fechaInicio AND :fechaFin AND m.accountNumber = :accountNumber")
    List<Movimiento> obtenerMovimientosEntreFechasPorAccountNumber(LocalDate fechaInicio, LocalDate fechaFin, String accountNumber);
}