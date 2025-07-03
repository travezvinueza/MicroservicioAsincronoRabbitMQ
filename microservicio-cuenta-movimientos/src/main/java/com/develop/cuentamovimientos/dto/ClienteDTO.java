package com.develop.cuentamovimientos.dto;

import com.develop.cuentamovimientos.enums.GenderPerson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO implements Serializable {
    private Long id;
    private Timestamp creationDate;
    private String nombre;
    private GenderPerson genderPerson;
    private int edad;
    private String identificacion;
    private String direccion;
    private String password;
    private String telefono;
}