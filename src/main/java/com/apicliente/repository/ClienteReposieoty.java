package com.apicliente.repository;

import com.apicliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteReposieoty extends JpaRepository<Cliente, Long> {


    Optional<Cliente> findByEmail(String email);
}
