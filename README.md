# stay-oline-backend (Java 21, Spring Boot 3.3) — Clean Architecture

V1 simples, **sem autenticação**, apenas a casca das APIs com Swagger e repositórios in-memory.

## Rotas

- `GET /health`
- `GET /packages`
- `POST /purchases` — body: `{ "userId": "u1", "packageId": "p60" }`
- `GET /users/{userId}/purchases`

Swagger UI: `http://localhost:8080/swagger`

## Como rodar

### Spring 

```bash
# Requisitos: Java 21 + Maven 3.9+
mvn spring-boot:run
```

### Docker

#### mvn package

```bash
mvnw -DskipTests package
```

#### docker build

```bash
docker build -t stayonline:local .
```

#### docker run

```bash
docker run -p 8080:8080 stayonline:local
```

## Se erro de certificado

Se houver erro de cerificado pegar certificado stellar.crt na raiz do projeto e adicionar no cacerts do java

```bash
keytool -import -alias stellar -file stellar.crt -keystore $JAVA_HOME/lib/security/cacerts
```

Obs: No caso do mac n utlizar o java Home se tiver usando o sdkman, pois a pasta current não tem o security, tem que colocar o caminho do java na mão

-Pode baixar pegar certificado na sua maquina também
```bash
openssl s_client -connect horizon-testnet.stellar.org:443 -showcerts
```

- Salvar o retorno em um arquivo .crt
