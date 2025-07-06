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
    private String fullName;
    private GenderPerson genderPerson;
    private int age;
    private String identification;
    private String address;
    private String phone;
    private String password;
    private boolean state;
}