package com.minegocio.cliente_api.service;

import com.minegocio.cliente_api.dto.ClienteCrearDTO;
import com.minegocio.cliente_api.dto.ClienteResponseDTO;
import com.minegocio.cliente_api.dto.DireccionDTO;
import com.minegocio.cliente_api.model.Cliente;
import com.minegocio.cliente_api.model.Direccion;
import com.minegocio.cliente_api.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// IMPLEMENTACION DE PRUEBAS UNITARIAS DE LOS SERVICIOS DE CLIENTES

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        System.out.println("mocks clienteService");
        MockitoAnnotations.openMocks(this);
    }

    private ClienteCrearDTO crearDtoEjemplo() {
        ClienteCrearDTO dto = new ClienteCrearDTO();
        dto.setIdentificationType("DNI");
        dto.setIdentificationNumber("1234567890");
        dto.setNames("Johan Curicho");
        dto.setEmail("jcuricho@gmail.com");
        dto.setCellphone("0983057728");
        DireccionDTO direccion = new DireccionDTO();
        direccion.setProvincia("Tungurahua");
        direccion.setCiudad("Ambato");
        direccion.setDireccion("Av. El rey");
        dto.setDireccionMatriz(direccion);
        return dto;
    }

    @Test
    void crearClientesinid() {
        System.out.println("✅ Test: Crear cliente cuando número de identificación NO existe");
        ClienteCrearDTO dto = crearDtoEjemplo();
        when(clienteRepository.existsByIdentificationNumber(dto.getIdentificationNumber())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(i -> {
            Cliente c = i.getArgument(0);
            c.setId(1L);
            return c;
        });

        ClienteResponseDTO resultado = clienteService.crearClienteConDireccion(dto);

        assertNotNull(resultado);
        assertEquals(dto.getIdentificationNumber(), resultado.getIdentificationNumber());
        System.out.println("Cliente creado con numero: " + resultado.getIdentificationNumber());
    }

    @Test
    void crearClienteconid() {
        System.out.println("Test: Crear cliente cuando número de identificación ya existe");
        ClienteCrearDTO dto = crearDtoEjemplo();
        when(clienteRepository.existsByIdentificationNumber(dto.getIdentificationNumber())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> clienteService.crearClienteConDireccion(dto));

        assertEquals("Número de identificación ya existe", exception.getMessage());
        System.out.println("No se creo el cliente porque ya existe.");
    }

    @Test
    void buscarClienteslista() {
        System.out.println("Test: Buscar clientes por nombre devuleve una lista");
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setIdentificationType("DNI");
        cliente.setIdentificationNumber("1234567890");
        cliente.setNames("Johan Curicho");
        cliente.setEmail("jcuricho@gmail.com");
        cliente.setCellphone("0983057728");

        Direccion matriz = new Direccion();
        matriz.setProvincia("Tungurahua");
        matriz.setCiudad("Ambato");
        matriz.setDireccion("Av. El rey");
        matriz.setEsMatriz(true);
        matriz.setCliente(cliente);

        cliente.setDirecciones(List.of(matriz));

        when(clienteRepository.buscarPorIdentificacionONombre("Johan")).thenReturn(List.of(cliente));

        List<ClienteResponseDTO> lista = clienteService.buscarClientes("Johan");

        assertFalse(lista.isEmpty());
        System.out.println("Se encontraron: " + lista.size() + " cliente(s).");
    }

    @Test
    void editarClienteCuandoExiste() {
        System.out.println("Test: Editar cliente cuando existe y número no duplicado");
        Long id = 1L;
        ClienteCrearDTO dto = crearDtoEjemplo();

        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(id);
        clienteExistente.setIdentificationNumber("099999999");
        clienteExistente.setDirecciones(Collections.emptyList());

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.existsByIdentificationNumber(dto.getIdentificationNumber())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));

        ClienteResponseDTO resultado = clienteService.editarCliente(id, dto);

        assertNotNull(resultado);
        System.out.println("Cliente con ID: " + id + " editado.");
    }

    @Test
    void editarClienteNoExistente() {
        System.out.println("Test: Editar cliente cuando no existe");
        Long id = 1L;
        ClienteCrearDTO dto = crearDtoEjemplo();

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.editarCliente(id, dto));

        assertEquals("Cliente no encontrado", ex.getMessage());
        System.out.println("No se pudo editar, cliente con ID " + id + " no existe.");
    }

    @Test
    void editarClienteIdendupli() {
        System.out.println("Test: Editar cliente con identificacion duplicada");
        Long id = 1L;
        ClienteCrearDTO dto = crearDtoEjemplo();

        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(id);
        clienteExistente.setIdentificationNumber("099999999");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.existsByIdentificationNumber(dto.getIdentificationNumber())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.editarCliente(id, dto));

        assertEquals("Número de identificación ya existe", ex.getMessage());
        System.out.println("No se pudo editar el cliente, número de identificación duplicado.");
    }

    @Test
    void eliminarClienteSiExiste() {
        System.out.println("Test: Eliminar cliente existente");
        Long id = 1L;

        when(clienteRepository.existsById(id)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(id);

        clienteService.eliminarCliente(id);

        verify(clienteRepository, times(1)).deleteById(id);
        System.out.println("Cliente con ID " + id + " eliminado correctamente.");
    }

    @Test
    void eliminarClienteNoExiste() {
        System.out.println("Test: Eliminar cliente que no existe");
        Long id = 1L;

        when(clienteRepository.existsById(id)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.eliminarCliente(id));

        assertEquals("Cliente no encontrado", ex.getMessage());
        System.out.println("No se pudo eliminar, cliente con ID " + id + " no existe.");
    }
}
