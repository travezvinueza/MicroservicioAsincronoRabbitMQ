package com.develop.clientepersona.dto;

import com.develop.clientepersona.enums.GenderPerson;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO implements Serializable {
    private Long id;
    @CreationTimestamp
    private Timestamp creationDate;
    @NotBlank(message = "El campo nombre no debe estar en blanco")
    private String fullName;
    @NotNull(message = "El género es obligatorio")
    private GenderPerson genderPerson;
    @Positive(message="El campo edad debe ser un valor positivo")
    @Max(value = 120, message="El campo edad debe estar en en un rango de entre 1 y 120 años")
    private int age;
    @NotBlank(message = "El numero de identificación no debe estar en blanco")
    @Size (min = 10,max = 13, message = "El campo identificacion debe tener entre 10 y 13 caracteres")
    private String identification;
    @NotBlank(message = "El campo dirección no debe estar en blanco")
    private String address;
    @NotBlank(message = "El campo teléfono no debe estar en blanco")
    private String phone;
    @NotBlank(message = "El campo password no debe estar en blanco")
    @Size (min = 8,max = 15, message = "El campo password debe tener entre 8 y 15 caracteres")
    private String password;
    private boolean state;
}
