package com.minegocio.cliente_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clientes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identification_type", nullable = false)
    private String identificationType;

    @Column(name = "identification_number", nullable = false, unique = true)
    private String identificationNumber;

    @Column(nullable = false)
    private String names;

    private String email;
    private String cellphone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Direccion> direcciones;
}
