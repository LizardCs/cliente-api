package com.minegocio.cliente_api.service;

import com.minegocio.cliente_api.dto.DireccionCrearDTO;
import com.minegocio.cliente_api.dto.DireccionResponseDTO;
import com.minegocio.cliente_api.model.Cliente;
import com.minegocio.cliente_api.model.Direccion;
import com.minegocio.cliente_api.repository.ClienteRepository;
import com.minegocio.cliente_api.repository.DireccionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DireccionService {

    private final DireccionRepository direccionRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public DireccionResponseDTO registrarDireccion(Long clienteId, DireccionCrearDTO dto) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Validación: No puede haber más de una matriz
        if (dto.isEsMatriz() && direccionRepository.existsByClienteIdAndEsMatrizTrue(clienteId)) {
            throw new RuntimeException("Ya existe una dirección matriz para este cliente");
        }

        Direccion direccion = Direccion.builder()
                .cliente(cliente)
                .provincia(dto.getProvincia())
                .ciudad(dto.getCiudad())
                .direccion(dto.getDireccion())
                .esMatriz(dto.isEsMatriz())
                .build();

        Direccion guardada = direccionRepository.save(direccion);

        return DireccionResponseDTO.builder()
                .id(guardada.getId())
                .provincia(guardada.getProvincia())
                .ciudad(guardada.getCiudad())
                .direccion(guardada.getDireccion())
                .esMatriz(guardada.isEsMatriz())
                .build();
    }

    @Transactional
    public List<DireccionResponseDTO> listarDireccionesAdicionales(Long clienteId) {
        List<Direccion> direcciones = direccionRepository.findByClienteIdAndEsMatrizFalse(clienteId);

        return direcciones.stream()
                .map(dir -> DireccionResponseDTO.builder()
                        .id(dir.getId())
                        .provincia(dir.getProvincia())
                        .ciudad(dir.getCiudad())
                        .direccion(dir.getDireccion())
                        .esMatriz(dir.isEsMatriz())
                        .build())
                .collect(Collectors.toList());
    }
}
