package com.minegocio.cliente_api.service;

import com.minegocio.cliente_api.dto.DireccionCrearDTO;
import com.minegocio.cliente_api.dto.DireccionResponseDTO;
import com.minegocio.cliente_api.model.Cliente;
import com.minegocio.cliente_api.model.Direccion;
import com.minegocio.cliente_api.repository.ClienteRepository;
import com.minegocio.cliente_api.repository.DireccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DireccionServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private DireccionService direccionService;

    @BeforeEach
    void setUp() {
        System.out.println("mocks direccionservice");
        MockitoAnnotations.openMocks(this);
    }

    private DireccionCrearDTO crearDireccionDTO(boolean esMatriz) {
        DireccionCrearDTO dto = new DireccionCrearDTO();
        dto.setProvincia("Tungurahua");
        dto.setCiudad("Ambato");
        dto.setDireccion("Av. El rey");
        dto.setEsMatriz(esMatriz);
        return dto;
    }

    @Test
    void registrarDireccion() {
        System.out.println("Test: Cliente existe y no hay matriz");
        Long clienteId = 1L;
        DireccionCrearDTO dto = crearDireccionDTO(true);
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(direccionRepository.existsByClienteIdAndEsMatrizTrue(clienteId)).thenReturn(false);
        when(direccionRepository.save(any(Direccion.class))).thenAnswer(invocation -> {
            Direccion d = invocation.getArgument(0);
            d.setId(10L);
            return d;
        });

        DireccionResponseDTO respuesta = direccionService.registrarDireccion(clienteId, dto);
        System.out.println("Dirección registrada: " + respuesta);

        assertNotNull(respuesta);
        assertEquals("Tungurahua", respuesta.getProvincia());
        assertTrue(respuesta.isEsMatriz());
        verify(direccionRepository, times(1)).save(any(Direccion.class));
    }

    @Test
    void registrarDireccion_ClienteNoExiste() {
        System.out.println("Test: Cliente no existe");
        Long clienteId = 2L;
        DireccionCrearDTO dto = crearDireccionDTO(false);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> direccionService.registrarDireccion(clienteId, dto));

        System.out.println("Cliente no encontrado: " + ex.getMessage());
        assertEquals("Cliente no encontrado", ex.getMessage());

        verify(direccionRepository, never()).save(any());
    }

    @Test
    void registrarDireccion_CuandoYaHayMatriz() {
        System.out.println("Test: Ya existe dirección matriz");
        Long clienteId = 3L;
        DireccionCrearDTO dto = crearDireccionDTO(true);

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(direccionRepository.existsByClienteIdAndEsMatrizTrue(clienteId)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> direccionService.registrarDireccion(clienteId, dto));

        System.out.println("Ya existe una dirección matriz para este cliente: " + ex.getMessage());
        assertEquals("Ya existe una dirección matriz para este cliente", ex.getMessage());
        verify(direccionRepository, never()).save(any());
    }

    @Test
    void listarDireccionesAdicionales() {
        System.out.println("Test: Listar direcciones adicionales devuleve la lista");
        Long clienteId = 4L;

        Direccion dir1 = new Direccion();
        dir1.setId(101L);
        dir1.setProvincia("Tungurahua");
        dir1.setCiudad("Ambato");
        dir1.setDireccion("Av. El rey");
        dir1.setEsMatriz(false);

        Direccion dir2 = new Direccion();
        dir2.setId(102L);
        dir2.setProvincia("Pichincha");
        dir2.setCiudad("Quito");
        dir2.setDireccion("Calle Secundaria");
        dir2.setEsMatriz(false);

        when(direccionRepository.findByClienteIdAndEsMatrizFalse(clienteId))
                .thenReturn(Arrays.asList(dir1, dir2));

        List<DireccionResponseDTO> resultado = direccionService.listarDireccionesAdicionales(clienteId);

        System.out.println("Direcciones: " + resultado.size());
        assertEquals(2, resultado.size());
        assertEquals("Tungurahua", resultado.get(0).getProvincia());
        assertEquals("Pichincha", resultado.get(1).getProvincia());
    }

    @Test
    void listarDireccionesAdicionales_CuandoNoHayDirecciones() {
        System.out.println("Test: No hay direcciones adicionales");
        Long clienteId = 5L;

        when(direccionRepository.findByClienteIdAndEsMatrizFalse(clienteId))
                .thenReturn(Collections.emptyList());

        List<DireccionResponseDTO> resultado = direccionService.listarDireccionesAdicionales(clienteId);

        System.out.println("Direcciones: " + resultado.size());
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
