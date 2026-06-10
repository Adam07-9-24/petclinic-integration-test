# Spring Boot : Application PetClinic

## Features  

### 1.  Unit Test  --> Tag v1.0.0
### 2.  Integration Test  --> Tag v2.0.0
mvn clean test -Dspring.profiles.active=h2

---

## Entregables del laboratorio 
Prueba de webhook Jenkins

### Integrantes

- Integrante A: Calep NeyraCRUD de servicio con integración H2.
- Integrante B: Yefry Calderon — Consultas sobre datos semilla.
- Integrante C: Sebastian Salas Pruebas de integración web con MockMvc.

### Pruebas de integración realizadas

#### Integrante A — CRUD de servicio

- `testCreateOwner`: crea un dueño usando el servicio real y verifica que se asigne un ID.
- `testUpdateOwner`: crea, actualiza y vuelve a consultar desde H2 para confirmar el cambio.
- `testDeleteOwner`: crea, elimina y verifica que al buscar por ID ya no exista.

#### Integrante B — Consultas sobre datos semilla

- `testFindOwnerById`: busca el dueño con ID 1 y valida que sea George Franklin.
- `testFindByLastName`: busca dueños con apellido Davis y valida que existan Betty Davis y Harold Davis.
- `testFindByCity`: busca dueños de Madison y valida que todos los resultados pertenezcan a esa ciudad.

#### Integrante C — Pruebas de integración web

- `testFindAllOwners`: realiza GET `/owners` y valida respuesta 200 OK, JSON y lista no vacía.
- `testFindOwnerById_OK`: realiza GET `/owners/1` y valida que el apellido sea Franklin.
- `testFindOwnerById_NotFound`: realiza GET `/owners/666` y valida respuesta 404 Not Found.

### Conflictos resueltos


Se verificó que las pruebas se ejecuten correctamente con el perfil H2.

### Comando de ejecución

En Windows PowerShell o terminal de intellij

.\mvnw.cmd clean test "-Dspring.profiles.active=h2"

Resultado de pruebas:

[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.617 s -- in com.tecsup.petclinic.webs.PetControllerTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 30, Failures: 0, Errors: 0, Skipped: 0
