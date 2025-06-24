# 📝 ClaudsDaily - Aplicación de Tareas

Este proyecto es una aplicación backend sencilla de gestión de tareas construida con **Kotlin** y **Spring Boot**. Incluye un CRUD para usuarios y tareas (`Assignments`) usando una base de datos embebida **H2**.

---

## Requisitos

- Java 21
- IntelliJ IDEA (recomendado)
- Gradle (incluido en el wrapper del proyecto)
- Postman o similar para pruebas


## Ejecucion del proyecto

- Abre el proyecto en IntelliJ.

- Ejecuta la clase principal:
com.puce.claudsdaily.ClaudsDailyApplication

- El servidor se iniciará en:
http://localhost:8080

## Pruebas de Endpoint para garantizar funcionamiento 

Si se desea realizar las pruebas referentes al funcionamiento del proyecto, ejecutar en Postman las siguientes direcciones en base a la prueba que desee realizar:

*En las secciones con {id} reemplazarlo con la id del usuario o la  tarea*

**Usuarios**

- POST /api/users - Crear usuario

- GET /api/users - Obtener todos

- GET /api/users/{id} - Por ID

- PUT /api/users/{id} - Actualizar

- DELETE /api/users/{id} - Eliminar



**Tareas (Assignments)**

- POST /api/assignments - Crear tarea (requiere user)

- GET /api/assignments - Todas las tareas

- GET /api/assignments/{id} - Por ID

- GET /api/assignments/user/{userId} - Por usuario

- PUT /api/assignments/{id} - Actualizar

- DELETE /api/assignments/{id} - Eliminar


## Acceso al H2 Database

Puedes acceder a la consola H2 para ver los datos:

- URL: http://localhost:8080/h2-console

- JDBC URL: jdbc:h2:mem:testdb

- Usuario: sa

- Contraseña: (vacía)
  
Para verificar la informacion que se ha guardado de las dos entidades utilizar:

- Select * from USERS;

- Select * from Assignments;
