# stay-oline-backend (Java 21, Spring Boot 3.3) — Clean Architecture

V1 simples, **sem autenticação**, apenas a casca das APIs com Swagger e repositórios in-memory.

## Rotas
- `GET /health`
- `GET /packages`
- `POST /purchases` — body: `{ "userId": "u1", "packageId": "p60" }`
- `GET /users/{userId}/purchases`

Swagger UI: `http://localhost:8080/swagger`

## Como rodar
```bash
# Requisitos: Java 21 + Maven 3.9+
mvn spring-boot:run
```
