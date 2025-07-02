package com.develop.clientepersona.controller;

import com.develop.clientepersona.repository.ClienteRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteController clienteController;

    @Before
    public void setUp() {
        clienteRepository.deleteAll();
    }


    @Test
    void crearCliente() throws Exception {

//        String clienteJson = "{\"nombre\":\"Pablo\",\"genero\":\"Masculino\",\"edad\":\"25\",  \"identificacion\":\"123\",\"direccion\":\"Loja\", \"telefono\":\"05789\", \"contrasena\":\"12345\"}";
//
//        mockMvc.perform(post("/api/v1/clientes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(clienteJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.nombre").value("Pablo"))
//                .andExpect(jsonPath("$.identificacion").value("123"));
    }
}