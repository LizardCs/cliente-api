package com.minegocio.cliente_api.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponseDTO {
    private Long id;
    private String identificationType;
    private String identificationNumber;
    private String names;
    private String email;
    private String cellphone;

    private String mainProvince;
    private String mainCity;
    private String mainAddress;
}
