package com.minegocio.cliente_api.controller;

import com.minegocio.cliente_api.dto.ClienteCrearDTO;
import com.minegocio.cliente_api.dto.ClienteResponseDTO;
import com.minegocio.cliente_api.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")  //Conexion con Angular
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/buscar")
    public List<ClienteResponseDTO> buscarClientes(@RequestParam String filtro) {
        return clienteService.buscarClientes(filtro);
    }

    // Lista de clientes
    @GetMapping
    public List<ClienteResponseDTO> listarClientes(@RequestParam(required = false, defaultValue = "") String filtro) {
        return clienteService.buscarClientes(filtro);
    }


    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody ClienteCrearDTO dto) {
        ClienteResponseDTO clienteCreado = clienteService.crearClienteConDireccion(dto);
        return ResponseEntity.ok(clienteCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> editarCliente(@PathVariable Long id, @RequestBody ClienteCrearDTO dto) {
        ClienteResponseDTO clienteEditado = clienteService.editarCliente(id, dto);
        return ResponseEntity.ok(clienteEditado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }
}
