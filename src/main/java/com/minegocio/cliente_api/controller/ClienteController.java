package com.minegocio.cliente_api.controller;

import com.minegocio.cliente_api.dto.ClienteResponseDTO;
import com.minegocio.cliente_api.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/buscar")
    public List<ClienteResponseDTO> buscarClientes(@RequestParam String filtro) {
        return clienteService.buscarClientes(filtro);
    }
}
