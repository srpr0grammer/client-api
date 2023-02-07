package com.apicliente.controller;

import com.apicliente.model.Cliente;
import com.apicliente.model.dto.ClienteDTO;
import com.apicliente.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ClienteDTO> save (@RequestBody ClienteDTO dto) {
        Cliente cliente = service.save(modelMapper.map(dto, Cliente.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(cliente, ClienteDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> body =
                service.getAll().stream()
                        .map(entity -> modelMapper.map(entity, ClienteDTO.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok(body);
    }

    @GetMapping("{id}")
    public ResponseEntity<ClienteDTO> findById (@PathVariable Long id) {
        Cliente cliente = service.getById(id);
        ClienteDTO body = modelMapper.map(cliente, ClienteDTO.class);

        return ResponseEntity.ok(body);
    }

    @PutMapping("{id}")
    public ResponseEntity<ClienteDTO> update (@RequestBody ClienteDTO dto, @PathVariable Long id) {
        dto.setId(id);
        Cliente cliente  = service.update(modelMapper.map(dto, Cliente.class));

        return ResponseEntity.ok(modelMapper.map(cliente, ClienteDTO.class));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ClienteDTO> delete (@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("email/{email}")
    public ResponseEntity<ClienteDTO> findByEmail (@PathVariable String email) {
        Cliente cliente = service.getByEmail(email);
        ClienteDTO body = modelMapper.map(cliente, ClienteDTO.class);

        return ResponseEntity.ok(body);
    }
}
