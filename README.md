# 📝 ClaudsDaily - Aplicación de Tareas

Este proyecto es una aplicación backend sencilla de gestión de tareas construida con **Kotlin** y **Spring Boot**. Incluye un CRUD para usuarios y tareas (`Assignments`) usando una base de datos embebida **H2**.

---

## Requisitos

- Java 21
- IntelliJ IDEA (recomendado)
- Gradle (incluido en el wrapper del proyecto)
- Postman o similar para pruebas

---

## Ejecucion del proyecto

- Abre el proyecto en IntelliJ.

- Ejecuta la clase principal:
com.puce.claudsdaily.ClaudsDailyApplication

- El servidor se iniciará en:
http://localhost:8080

## Acceso al H2 Database

Puedes acceder a la consola H2 para ver los datos:

- URL: http://localhost:8080/h2-console

- JDBC URL: jdbc:h2:mem:testdb

- Usuario: sa

- Contraseña: (vacía)
