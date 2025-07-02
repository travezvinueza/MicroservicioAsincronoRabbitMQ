package com.develop.clientepersona.entity;

import com.develop.clientepersona.enums.GenderPerson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_persona", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private Timestamp creationDate;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private GenderPerson genderPerson;
    private int edad;
    @Column(unique = true)
    private String identificacion;
    private String direccion;
    private String telefono;
}