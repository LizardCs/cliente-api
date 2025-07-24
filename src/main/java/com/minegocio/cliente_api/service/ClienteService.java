package com.minegocio.cliente_api.service;

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
                .map(cliente -> {
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
                })
                .collect(Collectors.toList());
    }
}
