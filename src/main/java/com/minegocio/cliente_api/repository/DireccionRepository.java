package com.minegocio.cliente_api.repository;

import com.minegocio.cliente_api.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    // todas las direcciones de un cliente excepto la matriz
    List<Direccion> findByClienteIdAndEsMatrizFalse(Long clienteId);

    // verificamos si un cliente ya tiene una direccion marcada como matriz
    boolean existsByClienteIdAndEsMatrizTrue(Long clienteId);
}
