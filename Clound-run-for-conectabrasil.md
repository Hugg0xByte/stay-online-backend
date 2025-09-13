# Cloud Run configuração

Esse doc tem como objetivo te ajudar a configurar o cloud run para a qualquer projeto.

## Visão rápida (o que vai rolar)

- Vamos criar um repositório de imagens (Artifact Registry), uma Service Account para o GitHub Actions e um workflow que:
  - faz build do JAR
  - builda a imagem Docker
  - publica no Artifact Registry
  - faz deploy no Cloud Run com --min-instances 0 (escala a zero).
- Cloud Run no modo requests-based tem 2M req/mês + 180k vCPU-s + 360k GiB-s grátis (em us-central1). Artifact Registry tem 0,5 GB/mês grátis de armazenamento.

## Pré-requisitos

- Instale o gcloud (Google Cloud CLI).

```bash
    brew install --cask gcloud-cli
```

- Instale o Docker.
- Crie/seleciona um projeto GCP e habilite billing.
- No terminal:

```bash
    # autentica no GCP.
    gcloud auth login
    # lista os projetos disponíveis.
    gcloud projects list 
    # seleciona o projeto.
    gcloud config set project <SEU_PROJETO>
```

## Configuração

### Habilite os serviços necessários

- Habilite os serviços necessários:

```bash
    gcloud services enable run.googleapis.com artifactregistry.googleapis.com
```

### Criar um repositório de imagens (Artifact Registry)

- Criar um repositório de imagens (Artifact Registry)
- Escolha us-central1 (Iowa) — ajuda a ficar no free tier.

```bash
    gcloud artifacts repositories create apps \
  --repository-format=docker \
  --location=us-central1 \
  --description="App images"
```

- Artifact Registry: `https://console.cloud.google.com/artifacts` para consultar imagens.
- Listar imagens: `gcloud artifacts docker images list us-central1-docker.pkg.dev/<SEU_PROJETO>/apps`

### Autenticar o Docker para publicar no Artifact Registry

- Autenticar o Docker para publicar no Artifact Registry:

```bash
   gcloud auth configure-docker us-central1-docker.pkg.dev --quiet
```

- (Esse comando configura o Docker a usar o gcloud como credential helper.)

### Preparar Dockerfile e .dockerignore

Na raiz do seu projeto Java (Spring Boot ou outro):

- Crie um Dockerfile.
- Crie um .dockerignore.

```dockerfile
# Build do JAR dentro da imagem (pode manter assim p/ simplificar)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /src
COPY . .
RUN ./mvnw -DskipTests package

# Runtime leve
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /src/target/*.jar /app/app.jar

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -XX:+UseSerialGC -Dserver.port=8080"
EXPOSE 8080
CMD ["java","-jar","/app/app.jar"]

```

```dockerfile
target/
.git/
.gitignore
.DS_Store
*/node_modules
*.iml
.idea/
.vscode/
```

### Testar local

```bash
mvn -DskipTests package
docker build -t stayonline:local .
docker run -p 8080:8080 stayonline:local
```

### Publicar imagem no Artifact Registry

Essa imagem é usada para testar o deploy no Cloud Run.

```bash
gcloud auth configure-docker us-central1-docker.pkg.dev

PROJECT_ID=$(gcloud config get-value project)
REGION=us-central1
REPO=apps
IMAGE_NAME=stayonline-api
TAG=amd64-$(date +%s)
IMAGE="$REGION-docker.pkg.dev/$PROJECT_ID/$REPO/$IMAGE_NAME:$TAG"

# configurar build para linux amd64
docker buildx build --platform linux/amd64 -t "$IMAGE" . --push
```

### deploy no Cloud Run

```bash
gcloud run deploy stayonline-api \
  --image "$IMAGE" \
  --region us-central1 \
  --platform managed \
  --allow-unauthenticated \
  --port 8080 \
  --cpu 1 --memory 512Mi \
  --concurrency 80 \
  --min-instances 0 \
  --max-instances 1
```


### Como consultar depois

Você pode listar e inspecionar o serviço a qualquer momento:

Listar serviços:
```bash
gcloud run services list --region us-central1
```

Vai mostrar nome, URL e status de cada serviço.
Detalhar um serviço:

```bash
gcloud run services describe stayonline-api --region us-central1
```

Mostra URL, revisões, tráfego, configurações de CPU/memória, etc.
Abrir direto no navegador:

```bash
gcloud run services browse stayonline-api --region us-central1
```
