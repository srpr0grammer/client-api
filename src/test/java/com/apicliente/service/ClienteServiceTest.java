package com.apicliente.service;

import com.apicliente.model.Cliente;
import com.apicliente.repository.ClienteReposieoty;
import com.apicliente.service.exception.ObjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClienteServiceTest {

    @Mock
    private ClienteReposieoty clienteReposieoty;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void getAllSuccess() {
        var expectedCliente = getMockCliente();
        List<Cliente> expectedClients = List.of(expectedCliente);

        when(clienteReposieoty.findAll()).thenReturn(expectedClients);
        var currentExpected = clienteService.getAll();

        assertEquals(expectedClients, currentExpected);
    }

    @Test
    public void getByIdSuccess() {
        var expectedCliente = getMockCliente();

        when(clienteReposieoty.findById(1L)).thenReturn(Optional.of(expectedCliente));
        var actualCliente = clienteService.getById(1L);

        assertEquals(expectedCliente, actualCliente);
    }

    @Test
    public void getByIObjectNotFoundException() {
        when(clienteReposieoty.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> {
            clienteService.getById(1L);
        });
    }

    @Test
    public void getByEmailSuccess() {
        var expectedCliente = getMockCliente();

        when(clienteReposieoty.findByEmail(expectedCliente.getEmail())).thenReturn(Optional.of(expectedCliente));
        var actualClient = clienteService.getByEmail(expectedCliente.getEmail());

        assertEquals(expectedCliente, actualClient);
    }

    @Test
    public void updateSuccess() {
        var expectedCliente = getMockCliente();

        when(clienteReposieoty.findById(1L)).thenReturn(Optional.of(expectedCliente));
        clienteService.update(expectedCliente);

        verify(clienteReposieoty).save(expectedCliente);

    }

    @Test
    public void deleteSuccess() {
        var expectedCliente = getMockCliente();

        when(clienteReposieoty.findById(1L)).thenReturn(Optional.of(expectedCliente));
        clienteService.delete(expectedCliente.getId());

        verify(clienteReposieoty).deleteById(expectedCliente.getId());
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
}
