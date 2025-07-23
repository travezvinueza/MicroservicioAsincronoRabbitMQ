package com.develop.cuentamovimientos.controller;

import com.develop.cuentamovimientos.service.CuentaService;
import com.develop.cuentamovimientos.service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CuentaControllerTest {
    @InjectMocks
    private CuentaController cuentaController;
    @Mock
    private CuentaService cuentaService;


    @Test
    void crear() {
    }

    @Test
    void listar() {
    }

    @Test
    void obtenerPorId() {
    }

    @Test
    void eliminarPorId() {
    }

    @Test
    void actualizar() {
    }
}