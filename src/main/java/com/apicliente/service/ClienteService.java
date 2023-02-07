package com.apicliente.service;

import com.apicliente.model.Cliente;
import com.apicliente.repository.ClienteReposieoty;
import com.apicliente.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteReposieoty reposieoty;

    public Cliente save(Cliente cliente) {
        return reposieoty.save(cliente);
    }

    public List<Cliente> getAll() {
        return reposieoty.findAll();
    }

    public Cliente getById(Long id) {
        Optional<Cliente> obj = reposieoty.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Id " + id + " não encontrado na base de dados"));
    }

    public Cliente update (Cliente cliente) {
        getById(cliente.getId());

        return reposieoty.save(cliente);
    }

    public void delete (Long id) {
        getById(id);
        reposieoty.deleteById(id);
    }

    public Cliente getByEmail(String email) {
        Optional<Cliente> obj = reposieoty.findByEmail(email);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Email " + email  + " não encontrado na base de dados "));
    }

}
