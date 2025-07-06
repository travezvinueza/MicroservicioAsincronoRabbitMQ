package com.develop.clientepersona.controller;

import com.develop.clientepersona.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }


//    @Test
//    void crearCliente() throws Exception {
//        String clienteJson = "{\"fullName\":\"Rivera Pablo\",\"genderPerson\":\"MASCULINO\",\"age\":\"30\",  \"identification\":\"1722722343\",\"address\":\"Loja\", \"phone\":\"0978564532\", \"password\":\"12345678\"}";
//
//        mockMvc.perform(post("/api/v1/clientes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(clienteJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.fullName").value("Rivera Pablo"))
//                .andExpect(jsonPath("$.identifitacion").value("1722722343"));
//    }
}