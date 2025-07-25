package com.develop.clientepersona.service.impl;

import com.develop.clientepersona.dto.ClienteDTO;
import com.develop.clientepersona.enums.GenderPerson;
import com.develop.clientepersona.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
class ClienteServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }


    @Test
    void crearCliente_deberiaRetornar201YClienteCreado() throws Exception {

        ClienteDTO nuevoCliente = new ClienteDTO();
        nuevoCliente.setFullName("Rivera Pablo");
        nuevoCliente.setGenderPerson(GenderPerson.MASCULINO);
        nuevoCliente.setAge(30);
        nuevoCliente.setIdentification("1722722343");
        nuevoCliente.setAddress("Loja");
        nuevoCliente.setPhone("0978564532");
        nuevoCliente.setPassword("12345678");
        nuevoCliente.setState(true);

        String clienteJson = objectMapper.writeValueAsString(nuevoCliente);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fullName", is("Rivera Pablo")))
                .andExpect(jsonPath("$.identification", is("1722722343")))
                .andExpect(jsonPath("$.genderPerson", is("MASCULINO")))
                .andExpect(jsonPath("$.age", is(30)))
                .andExpect(jsonPath("$.address", is("Loja")))
                .andExpect(jsonPath("$.phone", is("0978564532")));
    }
}