package com.develop.clientepersona.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("client")
public class Cliente extends Persona {
    private String password;
    private boolean estado;
}