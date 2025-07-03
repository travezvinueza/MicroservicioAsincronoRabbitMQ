package com.develop.cuentamovimientos.dto;

import com.develop.cuentamovimientos.enums.GenderPerson;

import java.sql.Timestamp;

public record ClienteDTO(
        Long id,
        Timestamp creationDate,
        String nombre,
        GenderPerson genderPerson,
        int edad,
        String identificacion,
        String direccion,
        String password,
        String telefono ) {

}