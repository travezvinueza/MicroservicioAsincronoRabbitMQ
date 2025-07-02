package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.repository.CuentaRepository;
import com.develop.cuentamovimientos.repository.MovimientoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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


//    @Test
//    void cuandoMovimientoDepositoEntoncesActualizarSaldo() {
//        //Arrange
//        String numeroCuenta="123456";
//
//        Movimiento movimiento = new Movimiento();
//        movimiento.setFecha(LocalDate.now());
//        movimiento.setTransactionType(TransactionType.DEPOSITO);
//        movimiento.setValor(20);
//        movimiento.setNumeroCuenta(numeroCuenta);
//
//        Movimiento ultimoMovimiento = new Movimiento();
//        ultimoMovimiento.setSaldo(100.0);
//
//        Cuenta cuenta = new Cuenta();
//        cuenta.setNumeroCuenta(numeroCuenta);
//        cuenta.setSaldoInicial(100.0);
//
//        when(cuentaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(Optional.of(cuenta));
//        when(movimientoRepository.obtenerUltimoMovimientoPorNumeroCuenta(movimiento.getNumeroCuenta())).thenReturn(Optional.of(ultimoMovimiento));
//
//        //Act
//        Movimiento movimientoSaldoActualizado= validaRegistroMovimiento.actualizarSaldoMovimiento(movimiento);
//
//        //Assert
//        assertNotNull(movimientoSaldoActualizado);
//        assertEquals(120.0, movimientoSaldoActualizado.getSaldo());
//
//    }
//
//    @Test
//    void cuandoMovimientoRetiroEntoncesActualizarSaldo() {
//        //Arrange
//        String numeroCuenta="123456";
//
//        Movimiento movimiento = new Movimiento();
//        movimiento.setFecha(LocalDate.now());
//        movimiento.setTransactionType(TransactionType.RETIRO);
//        movimiento.setValor(-20);
//        movimiento.setNumeroCuenta(numeroCuenta);
//
//        Movimiento ultimoMovimiento = new Movimiento();
//        ultimoMovimiento.setSaldo(100.0);
//
//        Cuenta cuenta = new Cuenta();
//        cuenta.setNumeroCuenta(numeroCuenta);
//        cuenta.setSaldoInicial(100.0);
//
//        when(cuentaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(Optional.of(cuenta));
//        when(movimientoRepository.obtenerUltimoMovimientoPorNumeroCuenta(movimiento.getNumeroCuenta())).thenReturn(Optional.of(ultimoMovimiento));
//
//        //Act
//        Movimiento movimientoSaldoActualizado= validaRegistroMovimiento.actualizarSaldoMovimiento(movimiento);
//
//        //Assert
//        assertNotNull(movimientoSaldoActualizado);
//        assertEquals(80.0, movimientoSaldoActualizado.getSaldo());
//
//    }
}