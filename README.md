# Task App — API REST con Spring Boot

API REST para gestión de tareas construida con **Spring Boot 3** y **Java 25**, siguiendo una arquitectura por capas (Controller → Service → Repository) con DTOs, mapeo explícito y manejo centralizado de excepciones.

El proyecto expone un CRUD completo de tareas que es consumido por una interfaz web ejecutada en Docker.

> Proyecto de aprendizaje basado en el build [*How to Build Your First Spring Boot App*](https://www.youtube.com/watch?v=M2U4_t_PSRM) de [Devtiro](https://www.devtiro.com).

---

## Stack

| Componente | Tecnología |
|---|---|
| Lenguaje | Java 25 |
| Framework | Spring Boot 3 (Web, Data JPA, Validation) |
| Persistencia | H2 (base de datos en memoria) |
| Build | Apache Maven (vía Maven Wrapper) |
| UI | Imagen Docker provista, orquestada con Docker Compose |

---

## Arquitectura

La aplicación separa responsabilidades en capas bien definidas:

```
Cliente (UI) ──HTTP──▶ Controller ──▶ Service ──▶ Repository ──▶ H2
                            │            │
                            └── DTO ◀────┴── Entity (Mapper)
```

- **Controller** — expone los endpoints REST y traduce peticiones HTTP en llamadas al servicio. No contiene lógica de negocio.
- **Service** — concentra la lógica de negocio y las validaciones de dominio. Define su contrato mediante interfaces para mantener el desacoplamiento.
- **Repository** — interfaz de Spring Data JPA; la implementación la genera Spring en tiempo de ejecución.
- **DTO + Mapper** — las entidades JPA nunca se exponen hacia el exterior. Los DTOs definen el contrato público de la API y aíslan el modelo de datos interno de los cambios en el contrato.
- **Exception Handling** — un `@RestControllerAdvice` global traduce las excepciones de dominio en respuestas HTTP coherentes.

### Modelo de dominio

La entidad `Task` representa una tarea con título, descripción, fecha límite, estado y prioridad. Estado y prioridad se modelan como **enums** en lugar de cadenas de texto, lo que impide valores inválidos en tiempo de compilación.

---

## Requisitos previos

- JDK 25 (o el que declare el `pom.xml`)
- Docker y Docker Compose (para la interfaz web)
- No necesitas instalar Maven: el proyecto incluye el Maven Wrapper

---

## Puesta en marcha

**1. Clonar el repositorio**

```bash
git clone https://github.com/<tu-usuario>/task.git
cd task
```

**2. Levantar la API**

```bash
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

**3. Levantar la interfaz web**

```bash
docker compose up -d
```

Abre la URL que expone el contenedor (revisa el puerto en `docker-compose.yml`).

> ⚠️ Al usar H2 en memoria, **los datos se pierden al reiniciar la aplicación**. Es una decisión intencional del proyecto para reducir la infraestructura necesaria.

---

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/tasks` | Lista todas las tareas |
| `POST` | `/tasks` | Crea una tarea |
| `GET` | `/tasks/{id}` | Obtiene una tarea por su ID |
| `PUT` | `/tasks/{id}` | Actualiza una tarea existente |
| `DELETE` | `/tasks/{id}` | Elimina una tarea |

### Ejemplo — crear una tarea

```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Preparar parcial de Redes",
    "description": "Repasar modelo OSI y subnetting",
    "priority": "HIGH",
    "status": "OPEN"
  }'
```

---

## Estructura del proyecto

```
task/
├── .mvn/wrapper/          # Maven Wrapper
├── src/
│   ├── main/
│   │   ├── java/com/devtiro/task/
│   │   │   ├── controllers/    # Endpoints REST
│   │   │   ├── services/       # Lógica de negocio
│   │   │   ├── repositories/   # Acceso a datos (Spring Data JPA)
│   │   │   ├── domain/
│   │   │   │   ├── entities/   # Entidades JPA y enums
│   │   │   │   └── dto/        # Objetos de transferencia
│   │   │   ├── mappers/        # Conversión Entity ⇄ DTO
│   │   │   └── TaskApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker-compose.yml     # Interfaz web
├── mvnw / mvnw.cmd
└── pom.xml
```

---

## Ejecutar los tests

```bash
./mvnw test
```

---

## Conceptos trabajados

- Inversión de control e inyección de dependencias con Spring
- Autoconfiguración y arranque de aplicaciones con Spring Boot
- Mapeo objeto-relacional y ciclo de vida de entidades con JPA/Hibernate
- Diseño de APIs REST: recursos, verbos HTTP y códigos de estado
- Separación entre modelo de dominio y contrato público mediante DTOs
- Manejo global de excepciones y respuestas de error consistentes
- Gestión del ciclo de vida del build con Maven

---

## Licencia

Proyecto con fines educativos.