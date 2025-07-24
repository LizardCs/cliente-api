package com.minegocio.cliente_api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class TestConexionPostgres implements CommandLineRunner {

    private final DataSource dataSource;

    public TestConexionPostgres(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Conexion con exito");
            //System.out.println("Url de conexion: " + connection.getMetaData().getURL());
            //System.out.println("Usuario conectado: " + connection.getMetaData().getUserName());
        } catch (Exception e) {
            System.err.println("Fallo de conexion:");
            e.printStackTrace();
        }
    }
}
