package com.develop.clientepersona.entity;

import jakarta.persistence.Column;
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
    @Column(name = "password")
    private String password;
    @Column(name = "state")
    private boolean state;
}