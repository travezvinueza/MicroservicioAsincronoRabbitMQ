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
    @Column(name = "full_name")
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender_person")
    private GenderPerson genderPerson;
    @Column(name = "age")
    private int age;
    @Column(name = "identification", unique = true)
    private String identification;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
}