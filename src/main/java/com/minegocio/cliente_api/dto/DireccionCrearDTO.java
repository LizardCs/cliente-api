package com.minegocio.cliente_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DireccionCrearDTO {
    private String provincia;
    private String ciudad;
    private String direccion;
    private boolean esMatriz;
}
