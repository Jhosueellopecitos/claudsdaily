# 📝 ClaudsDaily - Aplicación de Tareas

Este proyecto es una aplicación backend sencilla de gestión de tareas construida con **Kotlin** y **Spring Boot**. Incluye un CRUD para usuarios y tareas (`Assignments`) usando una base de datos externa como Postgresql.

---

## Requisitos

- Java 21
- IntelliJ IDEA (recomendado)
- Gradle (incluido en el wrapper del proyecto)
- Postman o similar para pruebas
- Docker
- Pgadmin

## Ejecucion del proyecto

- Abre el proyecto en IntelliJ.

- Ejecutar el docker-compose primero:
docker-compose up -d

- Ejecuta la clase principal:
com.puce.claudsdaily.ClaudsDailyApplication

- El servidor se iniciará en:

```
http://localhost:8080
```

- La base de datos va a correr en el puerto 5432

- Ejecutar el siguiente comando para bajar el docker
docker-compose down

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


## Ejecucion de test

1. Abrir el proyecto en IntelliJ IDEA.
2. En el panel izquierdo (proyect) navegar hacia la ubicacion de los test
```
src/test/com.pucetec.claudsdaily/
```
4. Al entrar en el test, hacer clic derecho sobre la clase de la prueba y dar click en run "Nombredeltest"


## Acceso al Pgadmin

Puedes acceder a pgadmin para ver los datos:

```
http://localhost:5050
```

Ahora inicia sesion en el Pgadmin

- Correo: admin@local.com

- Contraseña: admin123
  
Para verificar la informacion que se ha guardado de las dos entidades utilizar:

1. En el panel izquierdo, hacer click derecho sobre servers, create y server
2. En la pestaña "General" poner un nombre como Local
3. En la pestaña **Connection**, llena los siguientes datos:
   - Host name: db
   - Port:5432
   - Username: postgres
   - Password: postgres
   - Marcar "Save Password"
4. Haz click en Save

Una vez que se conecte explora la base de datos hasta las tablas 

- Hacer click derecho en una tabla, dar click en "View/Edit Data" y despues "All Rows", ahi se desplegara la informacion de las tablas
