package com.develop.cuentamovimientos.controller;

import com.develop.cuentamovimientos.service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovimientoControllerTest {
    @InjectMocks
    private MovimientoController movimientoController;
    @Mock
    private MovimientoService movimientoService;


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