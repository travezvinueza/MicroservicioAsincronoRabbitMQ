package com.develop.clientepersona.repository;

import com.develop.clientepersona.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNombre(String nombre);
    Optional<Cliente> findByIdentificacion(String identifiacion);
}
