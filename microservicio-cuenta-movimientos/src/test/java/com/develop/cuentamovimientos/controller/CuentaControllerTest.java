package com.develop.cuentamovimientos.controller;

import com.develop.cuentamovimientos.service.CuentaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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