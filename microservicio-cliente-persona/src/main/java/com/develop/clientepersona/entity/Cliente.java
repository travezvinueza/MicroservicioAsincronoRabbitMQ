package com.develop.clientepersona.entity;

import com.develop.clientepersona.enums.GenderPerson;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.sql.Timestamp;

@Entity
@DiscriminatorValue("cliente")
@Getter
@Setter
public class Cliente extends Persona {
    private String password;
    private boolean estado;

    public Cliente(){
        super(null, null, null, null, 0, null, null, null);
    }

    public Cliente(Long id, Timestamp creationDate, String nombre, GenderPerson genderPerson, int edad, String identificacion, String direccion, String telefono, String password, boolean estado) {
        super(id, creationDate, nombre, genderPerson, edad, identificacion, direccion, telefono);
        this.password = password;
        this.estado = estado;
    }

}
