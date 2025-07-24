package com.minegocio.cliente_api.repository;

import com.minegocio.cliente_api.model.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE " +
            "LOWER(c.identificationNumber) LIKE LOWER(CONCAT('%', :param, '%')) " +
            "OR LOWER(c.names) LIKE LOWER(CONCAT('%', :param, '%'))")
    List<Cliente> buscarPorIdentificacionONombre(@Param("param") String param);
}
