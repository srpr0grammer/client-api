package com.apicliente.controller;

import com.apicliente.model.Cliente;
import com.apicliente.model.dto.ClienteDTO;
import com.apicliente.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ClienteControllerTest {

    private String BASE_URL = "/clientes/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void saveSuccess() throws Exception {
        var clientDTO = getMockClienteDTO();
        var client = modelMapper.map(clientDTO, Cliente.class);

        when(clienteService.save(any(Cliente.class))).thenReturn(client);
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clientDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getAllSuccess() throws Exception {
        var client = getMockCliente();
        var clients = List.of(client);

        clients.stream().map(entity -> modelMapper.map(entity, ClienteDTO.class))
                .collect(Collectors.toList());
        when(clienteService.getAll()).thenReturn(clients);

        mockMvc.perform(get(BASE_URL)
                         .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    public void getByIdSucess() throws Exception {
        var client = getMockCliente();

        when(clienteService.getById(1L)).thenReturn(client);
        var clienteDTO = modelMapper.map(client, ClienteDTO.class);

        var result =  mockMvc.perform(get(BASE_URL + "{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var response = result.getResponse().getContentAsString();
        var responseDTO = objectMapper.readValue(response, ClienteDTO.class);

        assertEquals(clienteDTO, responseDTO);

    }

    @Test
    public void getByEmailSuccess() throws Exception {
        var client = getMockCliente();

        when(clienteService.getByEmail(client.getEmail())).thenReturn(client);
        var clienteDTO = modelMapper.map(client, ClienteDTO.class);

        var result =  mockMvc.perform(get(BASE_URL + "email/{email}", client.getEmail())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var response = result.getResponse().getContentAsString();
        var responseDTO = objectMapper.readValue(response, ClienteDTO.class);

        assertEquals(clienteDTO, responseDTO);

    }

    @Test
    public void updateSuccess() throws Exception {
        var clientDTO = getMockClienteDTO();
        var client = modelMapper.map(clientDTO, Cliente.class);

        when(clienteService.update(any(Cliente.class))).thenReturn(client);
        mockMvc.perform(put(BASE_URL + "{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clientDTO)))
                .andExpect(status().isOk());

    }

    @Test
    public void deleteSuccess() throws Exception {
        var id = 1L;
        doNothing().when(clienteService).delete(id);
        mockMvc.perform(delete(BASE_URL + "{id}", id))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).delete(id);
    }

    private Cliente getMockCliente() {
        var id = 1L;
        var client = new Cliente();
        client.setId(id);
        client.setCpf("TESTE");
        client.setNome("TESTE");
        client.setEmail("TESTE");

        return client;
    }

    private ClienteDTO getMockClienteDTO() {
        var id = 1L;
        var dto = new ClienteDTO();
        dto.setId(id);
        dto.setNome("TESTE");
        dto.setEmail("TESTE");

        return dto;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
