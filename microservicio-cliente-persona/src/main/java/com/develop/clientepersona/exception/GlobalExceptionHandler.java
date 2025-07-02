package com.develop.clientepersona.exception;

import com.develop.clientepersona.entity.MensajeError;
import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseEntity<Map<String, List<String>>> handleValidacionesErroresDTO(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("ValidacionesErroresDTO", errors);
        return errorResponse;
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RespuestaError> handleDateIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request
    ){
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                MensajeError.VALOR_YA_REGISTRADO + ex.getMessage().substring(46,56)
        );
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CedulaInvalidaException.class)
    public ResponseEntity<RespuestaError> handleCedulaInvalidaException(
            CedulaInvalidaException ex, WebRequest request
    ){
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                MensajeError.IDENTIFICACION_NO_VALIDO
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
