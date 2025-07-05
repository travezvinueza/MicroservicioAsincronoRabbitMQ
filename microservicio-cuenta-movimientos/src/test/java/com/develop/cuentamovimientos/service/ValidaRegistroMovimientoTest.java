package com.develop.cuentamovimientos.service;

import com.develop.cuentamovimientos.repository.CuentaRepository;
import com.develop.cuentamovimientos.repository.MovimientoRepository;
import com.develop.cuentamovimientos.util.ValidaRegistroMovimiento;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class ValidaRegistroMovimientoTest {
    @MockBean
    private MovimientoRepository movimientoRepository;
    @MockBean
    private CuentaRepository cuentaRepository;
    @Autowired
    private ValidaRegistroMovimiento validaRegistroMovimiento;

}