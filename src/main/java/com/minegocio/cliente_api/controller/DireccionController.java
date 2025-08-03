package com.minegocio.cliente_api.controller;

import com.minegocio.cliente_api.dto.DireccionCrearDTO;
import com.minegocio.cliente_api.dto.DireccionResponseDTO;
import com.minegocio.cliente_api.service.DireccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")  //Conexion con Angular
@RestController
@RequestMapping("/api/direcciones")
@RequiredArgsConstructor
public class DireccionController {

    private final DireccionService direccionService;

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<DireccionResponseDTO> registrarDireccion(@PathVariable Long clienteId,
                                                                   @RequestBody DireccionCrearDTO dto) {
        DireccionResponseDTO response = direccionService.registrarDireccion(clienteId, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cliente/{clienteId}/adicionales")
    public ResponseEntity<List<DireccionResponseDTO>> listarDireccionesAdicionales(@PathVariable Long clienteId) {
        List<DireccionResponseDTO> direcciones = direccionService.listarDireccionesAdicionales(clienteId);
        return ResponseEntity.ok(direcciones);
    }
}
