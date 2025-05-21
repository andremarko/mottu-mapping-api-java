# REST API For Mottu Mapping - Depot Management

# Indice

- [Sobre o Mottu Mapping](#sobre-o-mottu-mapping)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Dependências instaladas](#dependências-instaladas)
- [Endpoints - /api/[entidade]](#endpoints---apientidade)
  - [Entidade Moto](#entidade-moto)
    - [Criar uma nova moto](#criar-uma-nova-moto)
    - [Listar motos com filtros opcionais](#listar-motos-com-filtros-opcionais)
  - [Entidade Model (modelo da moto)](#entidade-model-modelo-da-moto)
  - [Entidade MotoYard (Pátio)](#entidade-motoyard-pátio)
  - [Entidade Sector (Setor)](#entidade-sector-setor)
- [Tabela de Endpoints](#tabela-de-endpoints)


## Sobre o _Mottu Mapping_

## Estrutura do projeto
Aplicação backend desenvolvida com Spring Boot (Java) e arquitetura Maven. Expõe endpoints REST para operações CRUD relacionadas ao gerenciamento de motos e setorização de pátios, como parte de um sistema de visão computacional. Persistência em banco de dados Oracle.
### Diretórios, camadas e arquivos do projeto
```graphql
├── src/
└── main/
    ├── java/
    │   └── com/
    │       └── mottu/
    │           └── mapping/
    │               └── api/
    │                   ├── MottuMappingApiApplication.java       # Classe principal Spring Boot
    │                   │
    │                   ├── controller/           # Camada de controle (endpoints REST)
    │                   │   ├── ModelController.java
    │                   │   ├── MotoController.java
    │                   │   ├── MotoYardController.java
    │                   │   └── SectorController.java
    │                   │
    │                   ├── dto/                  # Objetos de transferência de dados
    │                   │   ├── request/
    │                   │   │   ├── ModelRequestDTO.java
    │                   │   │   ├── MotoRequestDTO.java
    │                   │   │   ├── MotoYardRequestDTO.java
    │                   │   │   └── SectorRequestDTO.java
    │                   │   │
    │                   │   └── response/
    │                   │       ├── ModelResponseDTO.java
    │                   │       ├── MotoResponseDTO.java
    │                   │       ├── MotoYardResponseDTO.java
    │                   │       └── SectorResponseDTO.java
    │                   │
    │                   ├── exception/            # Tratamento de exceções personalizadas
    │                   │   ├── ApiExceptionHandler.java
    │                   │   ├── ModelNotFoundException.java
    │                   │   ├── MotoNotFoundException.java
    │                   │   ├── MotoYardNotFoundException.java
    │                   │   └── SectorNotFoundException.java
    │                   │
    │                   ├── model/                # Entidades JPA (mapeamento com o banco)
    │                   │   ├── Model.java
    │                   │   ├── Moto.java
    │                   │   ├── MotoYard.java
    │                   │   └── Sector.java
    │                   │
    │                   ├── repository/           # Interfaces JPA para acesso ao banco
    │                   │   ├── ModelRepository.java
    │                   │   ├── MotoRepository.java
    │                   │   ├── MotoYardRepository.java
    │                   │   └── SectorRepository.java
    │                   │
    │                   └── service/              # Camada de serviço (lógica de negócio)
    │                       ├── ModelService.java
    │                       ├── MotoService.java
    │                       ├── MotoYardService.java
    │                       └── SectorService.java
    │
    └── resources/
        └── application.properties                # Configurações da aplicação (DB, portas, etc.)

```
## Dependências instaladas
``` xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <dependency>
        <groupId>com.oracle.database.jdbc</groupId>
        <artifactId>ojdbc11</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```
- `spring-boot-starter-data-jpa`: Suporte a banco de dados via JPA/Hibernate.
- `spring-boot-devtools`: Hot reload para facilitar o desenvolvimento.
- `ojdbc11`: Driver JDBC para conectar ao banco Oracle.
- `lombok`: Gera automaticamente getters, setters e construtores.
- `spring-boot-starter-test`: Ferramentas para testes automatizados.
- `spring-boot-starter-validation`: Validação de dados via anotações.
- `spring-boot-starter-cache`: Suporte a cache para melhorar desempenho.
- `spring-boot-starter-web`: Suporte para APIs REST e servidor embutido.
- `spring-boot-starter-actuator`: Monitoramento e métricas da aplicação.
---
# Endpoints - /api/`[entidade]`
## Entidade Moto
### Criar uma nova moto
- **URL**: `/api/motos`
- **Método**: POST
- **Descrição**: Cria uma nova moto.
- **Body (JSON)**:
```json
{
  "plate": "ABC1234",
  "coordinates": "23.5505,-46.6333",
  "modelId": 1,
  "sectorId": 1
}
```
- **Exemplo cURL**:
```bash
curl -X POST "http://localhost:8080/api/motos" \
     -H "Content-Type: application/json" \
     -d '{
           "plate": "ABC1234",
           "coordinates": "23.5505,-46.6333",
           "modelId": 1,
           "sectorId": 1
         }'
```
### Listar motos com filtros opcionais
- **URL**: `/api/motos`
- **Método**: GET
- **Descrição**: Retorna uma lista paginada de motos, podendo filtrar por placa, setor e ordenar os resultados.
- **Parâmetros de Query (opcionais)**:
  - `plate` — filtra motos pela placa (exemplo: `/api/motos?plate=ABC1234`)
  - `sectorId` — filtra motos pelo setor (exemplo: `/api/motos?sectorId=1`)
  - `page` — número da página (exemplo: `/api/motos?page=0`)
  - `size` — tamanho da página (exemplo: `/api/motos?size=10`)
  - `sort` — ordenação dos resultados (exemplo: `/api/motos?sort=plate,asc` ou `/api/motos?sort=plate,desc`)
  
- **Exemplo cURL:**
``` bash
curl -X GET "http://localhost:8080/api/motos?plate=ABC1234&sectorId=1&page=0&size=10&sort=plate,asc"
```
### Buscar moto por ID
- **URL**: `/api/motos/{id}`
- **Método**: GET
- **Descrição**: Retorna os dados da moto pelo ID.

- **Exemplo cURL**:
``` bash
curl -X GET "http://localhost:8080/api/motos/1"
```
### Atualizar moto por ID
- **URL**: `/api/motos/{id}`
- **Método**: PUT
- **Descrição**: Atualiza os dados da moto pelo ID.
- **Body (JSON)**:
```json
{
  "plate": "XYZ9876",
  "coordinates": "23.4567,-46.7890",
  "modelId": 2,
  "sectorId": 3
}
```
- **Exemplo cURL:**
``` bash
curl -X PUT "http://localhost:8080/api/motos/1" \
     -H "Content-Type: application/json" \
     -d '{
           "plate": "XYZ9876",
           "coordinates": "23.4567,-46.7890",
           "modelId": 2,
           "sectorId": 3
         }'
```
### Deletar moto por ID
- **URL**: `/api/motos/{id}`
- **Método**: DELETE
- **Descrição**: Remove a moto pelo ID. Retorna status HTTP 204 (No Content) em caso de sucesso.

- **Exemplo cURL:**
```bash
curl -X DELETE "http://localhost:8080/api/motos/1"
```
## Entidade Model (modelo da moto)
### Criar um novo modelo de moto
- **URL**: `/api/models`
- **Método**: POST  
- **Descrição**: Cria um novo modelo de moto.
- **Body (JSON)**:
```json
{
  "modelName": "Mottu Pop"
}
```
- **Exemplo cURL**:
```bash
  curl -X POST "http://localhost:8080/api/models" \
     -H "Content-Type: application/json" \
     -d '{
           "modelName": "Honda CG 160"
         }'
```
### Listar modelos de moto
- **URL**: `/api/models`
- **Método**: GET
- **Descrição**: Retorna uma lista de todos os modelos.
- **Exemplo cURL**:
```bash
curl -X GET "http://localhost:8080/api/models"
```
### Buscar modelo de moto por ID
- **URL**: `/api/models/{id}`
- **Método**: GET
- **Descrição**: Retorna modelo pelo Id

- **Exemplo cURL:**
``` bash
curl -X GET "http://localhost:8080/api/models/1"
```
### Atualizar modelo por ID
- **URL**: `/api/models/{id}`
- **Método**: PUT
- **Descrição**: Atualiza os dados do modelo pelo ID.
- **Body (JSON)**:
```json
{
  "modelName": "Mottu Pop"
}
```
- **Exemplo cURL**:
``` bash
curl -X PUT "http://localhost:8080/api/models/1" \
     -H "Content-Type: application/json" \
     -d '{
           "modelName": "Mottu Pop"
         }'
```
### Deletar modelo por ID
- **URL**: `/api/models/{id}`
- **Método**: DELETE
- **Descrição**: Remove um modelo pelo ID. Retorna HTTP 204 (No Content) em caso de sucesso.

- **Exemplo cURL:**
```bash
curl -X DELETE "http://localhost:8080/api/models/1"
```
## Entidade MotoYard (Pátio)
### Criar um novo MotoYard 
- **URL**: `/api/motoyards`
- **Método**: POST
- **Descrição**: Cria um novo MotoYard.
- **Body (JSON)**:
```json
{
  "description": "Pátio Central",
  "capacity": 100
}
```
-**Exemplo cURL**:
``` bash
curl -X POST "http://localhost:8080/api/motoyards" \
     -H "Content-Type: application/json" \
     -d '{
           "description": "Pátio Central",
           "capacity": 100
         }'
```
### Listar todos os MotoYards
- **URL**: ` /api/motoyards`
- **Método**: GET
- **Descrição**: Retorna uma lista de todos os MotoYards.
- **Exemplo cURL**:
```bash
curl -X GET "http://localhost:8080/api/motoyards"
```
### Buscar MotoYard por ID
- **URL**: `/api/motoyards/{id}`  
- **Método**: GET  
- **Descrição**: Retorna os dados do MotoYard pelo ID.
- **Exemplo cURL**:
```bash
curl -X GET "http://localhost:8080/api/motoyards/1"
```
### Atualizar MotoYard por ID
- **URL**: `/api/motoyards/{id}`  
- **Método**: PUT  
- **Descrição**: Atualiza os dados do MotoYard pelo ID.
- **Body (JSON)**:
```json
{
  "description": "Pátio Atualizado",
  "capacity": 120
}
```
- **Exemplo cURL**:
``` bash
curl -X POST "http://localhost:8080/api/motoyards" \
     -H "Content-Type: application/json" \
     -d '{
           "description": "Pátio Atualizado",
           "capacity": 100
         }'
```
### Deletar MotoYard por ID
- **URL**: /api/motoyards/{id}
- **Método**: DELETE
- **Descrição**: Remove o MotoYard pelo ID. Retorna HTTP 204 (No Content) em caso de sucesso.
- **Exemplo cURL**:
``` bash
curl -X DELETE "http://localhost:8080/api/motoyards/1"
```
## Entidade Sector (Setor)
### Criar novo Sector
-**URL**: `/api/sectors`  
- **Método**: POST  
- **Descrição**: Cria um novo setor vinculado a um MotoYard.
- **Body (JSON)**:
```json
{
  "yardId": 1,
  "name": "Manutenção",
  "description": "Setor de manutenção",
  "colorRgb": "#FFA500",
  "colorName": "orange"
}
```
- **Exemplo cURL**:
```bash
curl -X POST "http://localhost:8080/api/sectors" \
     -H "Content-Type: application/json" \
     -d '{
            "yardId": 1,
            "name": "Manutenção",
            "description": "Setor de manutenção",
            "colorRgb": "#FFA500",
            "colorName": "orange"
         }'
```
### Listar todos os Sectors
- **URL**: `/api/sectors`  
- **Método**: GET  
- **Descrição**: Retorna uma lista de todos os setores.

- **Exemplo cURL**:
```bash
curl -X GET "http://localhost:8080/api/sectors"
```
## Buscar Sector por ID
**URL**: `/api/sectors/{id}`  
**Método**: GET  
**Descrição**: Retorna os dados de um setor específico pelo ID.

**Exemplo cURL**:
```bash
curl -X GET "http://localhost:8080/api/sectors/1"
```
### Atualizar Sector por ID
-**URL**: `/api/sectors/{id}`  
- **Método**: PUT  
- **Descrição**: Atualiza os dados de um setor existente.
- **Body (JSON)**:
```json
{ 
  "yardId": 1,
  "name": "Motos liberadas",
  "description": "Setor de motos liberadas",
  "colorRgb": "#00FF00",
  "colorName": "green"
}
```
- **Exemplo cURL**:
```bash
curl -X PUT "http://localhost:8080/api/sectors/1" \
     -H "Content-Type: application/json" \
     -d '{
           "yardId": 1,
           "name": "Motos liberadas",
           "description": "Setor de motos liberadas",
           "colorRgb": "#00FF00",
           "colorName": "green"
         }'
```
### Deletar Sector por ID
- **URL**: `/api/sectors/{id}`  
- **Método**: DELETE  
- **Descrição**: Remove o setor pelo ID. Retorna HTTP 204 (No Content) em caso de sucesso.
- **Exemplo cURL**:
```bash
curl -X DELETE "http://localhost:8080/api/sectors/1"
```
--

## Tabela de Endpoints

| Entidade       | Operação                | Método | URL                      | Descrição                             |
|----------------|------------------------|--------|--------------------------|-------------------------------------|
| **Moto**       | Criar moto             | POST   | `/api/motos`             | Cria uma nova moto                   |
|                | Listar motos           | GET    | `/api/motos`             | Lista motos com filtros e paginação |
|                | Buscar moto por ID     | GET    | `/api/motos/{id}`        | Retorna moto pelo ID                 |
|                | Atualizar moto por ID  | PUT    | `/api/motos/{id}`        | Atualiza dados da moto               |
|                | Deletar moto por ID    | DELETE | `/api/motos/{id}`        | Remove moto pelo ID                  |
| **Model**      | Criar modelo           | POST   | `/api/models`            | Cria um novo modelo                  |
|                | Listar modelos         | GET    | `/api/models`            | Lista todos os modelos               |
|                | Buscar modelo por ID   | GET    | `/api/models/{id}`       | Retorna modelo pelo ID               |
|                | Atualizar modelo por ID| PUT    | `/api/models/{id}`       | Atualiza dados do modelo             |
|                | Deletar modelo por ID  | DELETE | `/api/models/{id}`       | Remove modelo pelo ID                |
| **MotoYard**   | Criar pátio            | POST   | `/api/motoyards`         | Cria um novo pátio                  |
|                | Listar pátios          | GET    | `/api/motoyards`         | Lista todos os pátios                |
|                | Buscar pátio por ID    | GET    | `/api/motoyards/{id}`    | Retorna pátio pelo ID                |
|                | Atualizar pátio por ID | PUT    | `/api/motoyards/{id}`    | Atualiza dados do pátio              |
|                | Deletar pátio por ID   | DELETE | `/api/motoyards/{id}`    | Remove pátio pelo ID                 |
| **Sector**     | Criar setor            | POST   | `/api/sectors`           | Cria um novo setor vinculado a pátio|
|                | Listar setores         | GET    | `/api/sectors`           | Lista todos os setores               |
|                | Buscar setor por ID    | GET    | `/api/sectors/{id}`      | Retorna setor pelo ID                |
|                | Atualizar setor por ID | PUT    | `/api/sectors/{id}`      | Atualiza dados do setor              |
|                | Deletar setor por ID   | DELETE | `/api/sectors/{id}`      | Remove setor pelo ID                 |

# Modelo Relacional (MER)
<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/7fda10a0-d06f-4ddc-82cf-542877e11494" alt="relational_mottu_mapping" />
</div>
