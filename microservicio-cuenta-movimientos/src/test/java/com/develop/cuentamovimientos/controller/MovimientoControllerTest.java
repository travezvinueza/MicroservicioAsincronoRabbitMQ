package com.develop.cuentamovimientos.controller;

import com.develop.cuentamovimientos.dto.MovimientoDTO;
import com.develop.cuentamovimientos.service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovimientoControllerTest {
    @InjectMocks
    private MovimientoController motionController;
    @Mock
    private MovimientoService motionService;

    @Test
    void crear() {
        MovimientoDTO movimientoCreado = new MovimientoDTO();
        movimientoCreado.setId(1L);

        when(motionService.crear(any(MovimientoDTO.class))).thenReturn(movimientoCreado);
        ResponseEntity<MovimientoDTO> responseEntity = motionController.crear(new MovimientoDTO());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(movimientoCreado, responseEntity.getBody());
    }

    @Test
    void listar() {
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        List<MovimientoDTO> movimientoDTOS = List.of(new MovimientoDTO(), new MovimientoDTO());
        Page<MovimientoDTO> movimientoDTOPage = new PageImpl<>(movimientoDTOS, pageable, movimientoDTOS.size());

        when(motionService.listar(pageable)).thenReturn(movimientoDTOPage);

        ResponseEntity<Page<MovimientoDTO>> response = motionController.listar(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movimientoDTOPage, response.getBody());
    }

    @Test
    void obtenerPorId() {
        MovimientoDTO movimientoExistente = new MovimientoDTO();
        movimientoExistente.setId(1L);

        when(motionService.obtenerPorId(1L)).thenReturn(movimientoExistente);
        ResponseEntity<MovimientoDTO> responseEntity = motionController.obtenerPorId(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(movimientoExistente, responseEntity.getBody());
    }

    @Test
    void eliminarPorId() {
        motionController.eliminarPorId(1L);
        verify(motionService).eliminarPorId(1L);
    }

    @Test
    void actualizar() {
        MovimientoDTO movimientoActualizado = new MovimientoDTO();
        movimientoActualizado.setId(1L);

        when(motionService.actualizar(any(MovimientoDTO.class))).thenReturn(movimientoActualizado);
        ResponseEntity<MovimientoDTO> responseEntity = motionController.actualizar(new MovimientoDTO());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(movimientoActualizado, responseEntity.getBody());
    }
}