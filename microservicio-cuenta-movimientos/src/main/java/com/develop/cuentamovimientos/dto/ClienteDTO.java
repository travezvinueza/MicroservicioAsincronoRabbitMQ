package com.develop.cuentamovimientos.dto;

import com.develop.cuentamovimientos.enums.GenderPerson;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO implements Serializable {
    private Long id;
    private LocalDateTime creationDate;
    private String fullName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private GenderPerson genderPerson;
    private int age;
    private String identification;
    private String address;
    private String phone;
    private String password;
    private boolean state;
}