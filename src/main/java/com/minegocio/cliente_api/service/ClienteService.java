package com.minegocio.cliente_api.service;

import com.minegocio.cliente_api.dto.ClienteCrearDTO;
import com.minegocio.cliente_api.dto.ClienteResponseDTO;
import com.minegocio.cliente_api.model.Cliente;
import com.minegocio.cliente_api.model.Direccion;
import com.minegocio.cliente_api.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public List<ClienteResponseDTO> buscarClientes(String filtro) {
        return clienteRepository.buscarPorIdentificacionONombre(filtro)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClienteResponseDTO crearClienteConDireccion(ClienteCrearDTO dto) {
        if (clienteRepository.existsByIdentificationNumber(dto.getIdentificationNumber())) {
            throw new RuntimeException("Número de identificación ya existe");
        }

        Cliente cliente = new Cliente();
        cliente.setIdentificationType(dto.getIdentificationType());
        cliente.setIdentificationNumber(dto.getIdentificationNumber());
        cliente.setNames(dto.getNames());
        cliente.setEmail(dto.getEmail());
        cliente.setCellphone(dto.getCellphone());

        Direccion matriz = new Direccion();
        matriz.setProvincia(dto.getDireccionMatriz().getProvincia());
        matriz.setCiudad(dto.getDireccionMatriz().getCiudad());
        matriz.setDireccion(dto.getDireccionMatriz().getDireccion());
        matriz.setEsMatriz(true);
        matriz.setCliente(cliente);

        cliente.setDirecciones(List.of(matriz));

        clienteRepository.save(cliente);

        return mapToDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO editarCliente(Long id, ClienteCrearDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (!cliente.getIdentificationNumber().equals(dto.getIdentificationNumber()) &&
                clienteRepository.existsByIdentificationNumber(dto.getIdentificationNumber())) {
            throw new RuntimeException("Número de identificación ya existe");
        }

        cliente.setIdentificationType(dto.getIdentificationType());
        cliente.setIdentificationNumber(dto.getIdentificationNumber());
        cliente.setNames(dto.getNames());
        cliente.setEmail(dto.getEmail());
        cliente.setCellphone(dto.getCellphone());

        clienteRepository.save(cliente);

        return mapToDTO(cliente);
    }

    @Transactional
    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }

    private ClienteResponseDTO mapToDTO(Cliente cliente) {
        Direccion matriz = cliente.getDirecciones()
                .stream()
                .filter(Direccion::isEsMatriz)
                .findFirst()
                .orElse(null);

        return ClienteResponseDTO.builder()
                .id(cliente.getId())
                .identificationType(cliente.getIdentificationType())
                .identificationNumber(cliente.getIdentificationNumber())
                .names(cliente.getNames())
                .email(cliente.getEmail())
                .cellphone(cliente.getCellphone())
                .mainProvince(matriz != null ? matriz.getProvincia() : null)
                .mainCity(matriz != null ? matriz.getCiudad() : null)
                .mainAddress(matriz != null ? matriz.getDireccion() : null)
                .build();
    }
}
