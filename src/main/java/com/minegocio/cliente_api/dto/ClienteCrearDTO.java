
package com.minegocio.cliente_api.dto;

import lombok.Data;

@Data
public class ClienteCrearDTO {
    private String identificationType;
    private String identificationNumber;
    private String names;
    private String email;
    private String cellphone;

    private DireccionDTO direccionMatriz;
}
