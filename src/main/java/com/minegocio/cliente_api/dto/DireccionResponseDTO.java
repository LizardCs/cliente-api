package com.minegocio.cliente_api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DireccionResponseDTO {
    private Long id;
    private String provincia;
    private String ciudad;
    private String direccion;
    private boolean esMatriz;
}
