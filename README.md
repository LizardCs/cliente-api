# Cliente API - Mi Negocio

El siguiente proyecto es una API REST construida con Spring Boot para gestionar clientes y sus direcciones.

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.4
- PostgreSQL
- Spring Data JPA
- Lombok
- Git + GitHub
- Creación y edición de codigo con IntelliJ IDEA

## Estructura del proyecto

- `/src/main/java`: Código fuente Java
- `/src/main/resources`: Configuración (`application.properties`)
- `/pom.xml`: Archivo de dependencias Maven

## Uso de las API's
Principales endpoints disponibles:
Buscar clientes.
-	Método: GET
-	URL: http://localhost:8080/api/clientes/buscar?filtro={ identidad o nombre}
-	Descripción: Busca un cliente por su número de identidad o nombre

Crear nuevo cliente con dirección matriz
-	Método: POST
-	URL: /api/clientes
-	Descripción: Registra un nuevo cliente con su dirección matriz.

Editar cliente existente
-	Método: PUT
-	URL: /api/clientes/{id}
-	Descripción: Edita los datos de un cliente registrado.

Eliminar cliente
-	Método: DELETE
-	URL: /api/clientes/{id}
-	Descripción: Elimina un cliente por su ID.

Registrar dirección adicional
-	Método: POST
-	URL: /api/direcciones/cliente/{clienteId}
-	Descripción: Registra una nueva dirección adicional para un cliente.

Listar direcciones adicionales
-	Método: GET
-	URL: /api/direcciones/adicionales/{clienteId}
-	Descripción: Obtiene las direcciones adicionales registradas de un cliente.

## Instrucciones de ejecucion del proyecto

1. Clona el repositorio
2. Restaura la base de datos en PostgreSQL a traves del archivo minegocio.sql
3. Configura tu `application.properties` con los datos, conexion o puertos de tu base de datos PostgreSQL
4. Ejecuta la aplicación desde tu IDE o con `mvn spring-boot:run`
