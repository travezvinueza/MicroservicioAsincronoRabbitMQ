package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.entity.Cuenta;
import com.develop.cuentamovimientos.entity.Movimiento;
import com.develop.cuentamovimientos.enums.TransactionType;
import com.develop.cuentamovimientos.repository.CuentaRepository;
import com.develop.cuentamovimientos.repository.MovimientoRepository;
import com.develop.cuentamovimientos.util.ValidaRegistroMovimiento;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class ValidaRegistroMovimientoTest {
    @MockBean
    private MovimientoRepository movimientoRepository;
    @MockBean
    private CuentaRepository cuentaRepository;
    @Autowired
    private ValidaRegistroMovimiento validaRegistroMovimiento;

    @Test
    void cuandoMovimientoDepositoEntoncesActualizarSaldo() {
        //Arrange
        String numeroCuenta="123456";

        Movimiento movimiento = new Movimiento();
        movimiento.setDate(LocalDate.now());
        movimiento.setTransactionType(TransactionType.DEPOSITO);
        movimiento.setValue(20);
        movimiento.setAccountNumber(numeroCuenta);

        Movimiento ultimoMovimiento = new Movimiento();
        ultimoMovimiento.setValue(100.0);

        Cuenta cuenta = new Cuenta();
        cuenta.setAccountNumber(numeroCuenta);
        cuenta.setInitialBalance(100.0);

        when(cuentaRepository.findByAccountNumber(numeroCuenta))
                .thenReturn(Optional.of(cuenta));

        when(movimientoRepository.obtenerMovimientosEntreFechasPorAccountNumber(
                any(LocalDate.class), any(LocalDate.class), eq(numeroCuenta)))
                .thenReturn(List.of(ultimoMovimiento));

        Movimiento movimientoSaldoActualizado= validaRegistroMovimiento.actualizarSaldoMovimiento(movimiento);

        assertNotNull(movimientoSaldoActualizado);
        assertEquals(120.0, movimientoSaldoActualizado.getBalance());

    }

}