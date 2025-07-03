package com.develop.clientepersona.entity;

import com.develop.clientepersona.enums.GenderPerson;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // También puedes usar JOINED
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(name = "creation_date")
    private Timestamp creationDate;
    private String nombre;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender_person")
    private GenderPerson genderPerson;
    private int edad;
    @Column(unique = true)
    private String identificacion;
    private String direccion;
    private String telefono;
}