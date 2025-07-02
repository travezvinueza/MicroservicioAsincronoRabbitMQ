package com.develop.cuentamovimientos.exception;

import com.develop.cuentamovimientos.entity.MensajeError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("ValidacionesErroresDTO", errors);
        return errorResponse;
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<RespuestaError> handleSaldoInsuficienteException(SaldoInsuficienteException exception,
                                                                       WebRequest webRequest
    ){
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                MensajeError.SALDO_INSUFICIENTE
        );
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CuentaNoEncontradaException.class)
    public ResponseEntity<RespuestaError> handleCuentaNoEncontradaException(CuentaNoEncontradaException exception,
                                                                           WebRequest webRequest
    ){
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                MensajeError.CUENTA_NO_ENCONTRADA
        );
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<RespuestaError> handleRecursoNoEncontradoException(RecursoNoEncontradoException exception,
                                                                            WebRequest webRequest
    ){
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                MensajeError.RECURSO_NO_ENCONTRADO
        );
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovimientoDepositoNegativoException.class)
    public ResponseEntity<RespuestaError> handleMovimientoDepositoNegativoException(MovimientoDepositoNegativoException exception,
                                                                              WebRequest webRequest
    ){
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                MensajeError.VALOR_DEPOSITO_NO_VALIDO
        );
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovimientoRetiroPositivoException.class)
    public ResponseEntity<RespuestaError> handleMovimientoRetiroPositivoException(MovimientoRetiroPositivoException exception,
                                                                                    WebRequest webRequest
    ){
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                MensajeError.VALOR_RETIRO_NO_VALIDO
        );
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RespuestaError> handleDateIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request
    ){
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                MensajeError.VALOR_YA_REGISTRADO+": " + ex.getMessage().substring(46,51)
        );
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TipoTransaccionNoValidaException.class)
    public ResponseEntity<RespuestaError> handleTransaccionNoValidaException(
            TipoTransaccionNoValidaException ex, WebRequest request
    ){
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                MensajeError.TIPO_TRANSACCION_NO_VALIDA
        );
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RespuestaError> handleJsonMalformadoException(HttpMessageNotReadableException ex, WebRequest request) {
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                MensajeError.FORMATO_JSON_MALFORMADO
        );
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

}
