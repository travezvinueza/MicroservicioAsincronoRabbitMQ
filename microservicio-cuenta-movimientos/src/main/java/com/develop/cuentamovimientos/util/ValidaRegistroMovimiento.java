package com.develop.cuentamovimientos.util;

import com.develop.cuentamovimientos.entity.Cuenta;
import com.develop.cuentamovimientos.entity.MensajeError;
import com.develop.cuentamovimientos.entity.Movimiento;
import com.develop.cuentamovimientos.enums.TransactionType;
import com.develop.cuentamovimientos.exception.*;
import com.develop.cuentamovimientos.repository.CuentaRepository;
import com.develop.cuentamovimientos.repository.MovimientoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
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
        Cuenta cuenta = cuentaRepository.findByAccountNumber(movimiento.getAccountNumber())
                .orElseThrow(() -> new CuentaNoEncontradaException(MensajeError.CUENTA_NO_ENCONTRADA));

        // Obtener último movimiento si existe
        double saldoActual = movimientoRepository.obtenerUltimoMovimientoPorAccountNumber(movimiento.getAccountNumber())
                .map(Movimiento::getBalance)
                .orElse(cuenta.getInitialBalance());

        // Lógica de depósito
        if (movimiento.getTransactionType() == TransactionType.DEPOSITO) {
            if (movimiento.getValue() <= 0) {
                throw new MovimientoDepositoNegativoException(MensajeError.VALOR_DEPOSITO_NO_VALIDO);
            }
            movimiento.setBalance(saldoActual + movimiento.getValue());
        }

        // Lógica de retiro
        else if (movimiento.getTransactionType() == TransactionType.RETIRO) {
            if (movimiento.getValue() <= 0) {
                throw new MovimientoRetiroPositivoException(MensajeError.VALOR_RETIRO_NO_VALIDO);
            }
            if (saldoActual < movimiento.getValue()) {
                throw new SaldoInsuficienteException(MensajeError.SALDO_INSUFICIENTE);
            }
            movimiento.setBalance(saldoActual - movimiento.getValue());
        }

        return movimiento;
    }

}