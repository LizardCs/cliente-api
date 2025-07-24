package com.minegocio.cliente_api.repository;

import com.minegocio.cliente_api.model.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE " +
            "LOWER(c.identificationNumber) LIKE LOWER(CONCAT('%', :param, '%')) " +
            "OR LOWER(c.names) LIKE LOWER(CONCAT('%', :param, '%'))")
    List<Cliente> buscarPorIdentificacionONombre(@Param("param") String param);

    // Verifica si existe un cliente con el mismo número de identificación
    boolean existsByIdentificationNumber(String identificationNumber);

    // También puede ser útil obtener un cliente por identificación exacta
    Optional<Cliente> findByIdentificationNumber(String identificationNumber);
}
