package com.develop.cuentamovimientos.controller;

import com.develop.cuentamovimientos.dto.CuentaDTO;
import com.develop.cuentamovimientos.enums.AccountType;
import com.develop.cuentamovimientos.service.CuentaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CuentaControllerTest {
    @InjectMocks
    private CuentaController cuentaController;
    @Mock
    private CuentaService cuentaService;


    @Test
    void crear() {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setId(1L);
        cuentaDTO.setAccountNumber("222222");
        cuentaDTO.setAccountType(AccountType.AHORROS);
        cuentaDTO.setInitialBalance(100);
        cuentaDTO.setState(true);
        cuentaDTO.setIdentificationClient("1722722343");

        when(cuentaService.crear(any(CuentaDTO.class))).thenReturn(cuentaDTO);

        ResponseEntity<CuentaDTO> response = cuentaController.crear(cuentaDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cuentaDTO, response.getBody());
    }

    @Test
    void listar() {
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        CuentaDTO cuenta = new CuentaDTO();
        cuenta.setId(1L);

        Page<CuentaDTO> cuentaPage = new PageImpl<>(List.of(cuenta));
        when(cuentaService.listar(pageable)).thenReturn(cuentaPage);

        ResponseEntity<Page<CuentaDTO>> response = cuentaController.listar(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    void obtenerPorId() {
        Long id = 1L;
        CuentaDTO cuenta = new CuentaDTO();
        cuenta.setId(id);

        when(cuentaService.obtenerPorId(id)).thenReturn(cuenta);

        ResponseEntity<CuentaDTO> response = cuentaController.obtenerPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void eliminarPorId() {
        Long id = 1L;
        doNothing().when(cuentaService).eliminarPorId(id);

        cuentaController.eliminarPorId(id);

        verify(cuentaService, times(1)).eliminarPorId(id);
    }

    @Test
    void actualizar() {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setId(1L);
        cuentaDTO.setAccountNumber("999999");

        when(cuentaService.actualizar(any(CuentaDTO.class))).thenReturn(cuentaDTO);

        ResponseEntity<CuentaDTO> response = cuentaController.actualizar(cuentaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(cuentaDTO.getAccountNumber(), response.getBody().getAccountNumber());
    }
}