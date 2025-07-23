package com.develop.cuentamovimientos.controller;

import com.develop.cuentamovimientos.dto.CuentaDTO;
import com.develop.cuentamovimientos.service.CuentaService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuentaControllerTest {
    @InjectMocks
    private CuentaController cuentaController;
    @Mock
    private CuentaService cuentaService;

    @Test
    void crear() {
        CuentaDTO cuentaCreada = new CuentaDTO();
        cuentaCreada.setId(1L);

        when(cuentaService.crear(any(CuentaDTO.class))).thenReturn(cuentaCreada);
        ResponseEntity<CuentaDTO> responseEntity = cuentaController.crear(new CuentaDTO());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(cuentaCreada, responseEntity.getBody());
    }

    @Test
    void listar() {
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        List<CuentaDTO> cuentas = List.of(new CuentaDTO(), new CuentaDTO());
        Page<CuentaDTO> cuentaPage = new PageImpl<>(cuentas, pageable, cuentas.size());

        when(cuentaService.listar(pageable)).thenReturn(cuentaPage);
        ResponseEntity<Page<CuentaDTO>> response = cuentaController.listar(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cuentaPage, response.getBody());
    }

    @Test
    void obtenerPorId() {
        CuentaDTO cuentaExistente = new CuentaDTO();
        cuentaExistente.setId(1L);

        when(cuentaService.obtenerPorId(1L)).thenReturn(cuentaExistente);
        ResponseEntity<CuentaDTO> responseEntity = cuentaController.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cuentaExistente, responseEntity.getBody());
    }

    @Test
    void eliminarPorId() {
        cuentaController.eliminarPorId(1L);
        verify(cuentaService).eliminarPorId(1L);
    }

    @Test
    void actualizar() {
        CuentaDTO cuentaActualizada = new CuentaDTO();
        cuentaActualizada.setId(1L);

        when(cuentaService.actualizar(any(CuentaDTO.class))).thenReturn(cuentaActualizada);
        ResponseEntity<CuentaDTO> responseEntity = cuentaController.actualizar(new CuentaDTO());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cuentaActualizada, responseEntity.getBody());
    }
}