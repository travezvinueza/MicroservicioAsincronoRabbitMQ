package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.entity.Cuenta;
import com.develop.cuentamovimientos.entity.MensajeError;
import com.develop.cuentamovimientos.entity.Movimiento;
import com.develop.cuentamovimientos.enums.TransactionType;
import com.develop.cuentamovimientos.exception.*;
import com.develop.cuentamovimientos.repository.CuentaRepository;
import com.develop.cuentamovimientos.repository.MovimientoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class ValidaRegistroMovimiento {
    private MovimientoRepository movimientoRepository;
    private CuentaRepository cuentaRepository;

    public Movimiento actualizarSaldoMovimiento(Movimiento movimiento) {
        // Validar tipo de transacción
        if (movimiento.getTransactionType() != TransactionType.DEPOSITO &&
                movimiento.getTransactionType() != TransactionType.RETIRO) {
            throw new TipoTransaccionNoValidaException(MensajeError.TIPO_TRANSACCION_NO_VALIDA);
        }

        // Buscar cuenta
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getNumeroCuenta())
                .orElseThrow(() -> new CuentaNoEncontradaException(MensajeError.CUENTA_NO_ENCONTRADA));

        // Obtener último movimiento si existe
        double saldoActual = movimientoRepository.obtenerUltimoMovimientoPorNumeroCuenta(movimiento.getNumeroCuenta())
                .map(Movimiento::getSaldo)
                .orElse(cuenta.getSaldoInicial());

        // Lógica de depósito
        if (movimiento.getTransactionType() == TransactionType.DEPOSITO) {
            if (movimiento.getValor() <= 0) {
                throw new MovimientoDepositoNegativoException(MensajeError.VALOR_DEPOSITO_NO_VALIDO);
            }
            movimiento.setSaldo(saldoActual + movimiento.getValor());
        }

        // Lógica de retiro
        else if (movimiento.getTransactionType() == TransactionType.RETIRO) {
            if (movimiento.getValor() <= 0) {
                throw new MovimientoRetiroPositivoException(MensajeError.VALOR_RETIRO_NO_VALIDO);
            }
            if (saldoActual < movimiento.getValor()) {
                throw new SaldoInsuficienteException(MensajeError.SALDO_INSUFICIENTE);
            }
            movimiento.setSaldo(saldoActual - movimiento.getValor());
        }

        return movimiento;
    }

}