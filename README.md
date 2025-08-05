# TasteManager

TasteManager é uma aplicação desenvolvida em **Java 21** utilizando o framework **Spring Boot**. O objetivo do projeto é gerenciar usuários e suas informações, incluindo funcionalidades como criação, atualização, exclusão, troca de senha e validação de login.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Maven**
- **MapStruct** (para mapeamento de DTOs)
- **Docker** (opcional, para containerização)
- **Banco de Dados SQL** (H2)

## Pré-requisitos

- **Java 21** ou superior
- **Maven**
- **Docker** (opcional)
- **H2** configurado

## Estrutura do Projeto
taste-manager/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/tastemanager/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
│       └── java/
└── pom.xml

## Configuração do Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/delainefiap/taste-manager.git
   ```

2. Gere o arquivo JAR do projeto:
   ```shell
   mvn clean package
   ```
   
3. Construa a imagem Docker:
   ```bash
   docker build -t tastemanager-app .
   ```

3. Suba a aplicação com Docker Compose:
   ```bash
   docker-compose up --build
   ```

4. Acesse a aplicação:
    - **URL base**: `http://localhost:8080`
   ```

5. Observação:
    - A aplicação utiliza o banco H2
    - Há anotações para criação da table e campos automaticamente
    - Para otimizar, á um script que insere dados iniciais no banco
    

## Endpoints da API

### Usuários (`/user`)

#### 1. Criar Usuário
- **POST** `/user/create`
- **Corpo**: `UserRequestDTO`
- **Resposta**: `UserResponseDTO`
- **Status**: `201 Created`

#### 2. Atualizar Usuário
- **PATCH** `/user/update/{id}`
- **Parâmetros**: `id` (path)
- **Corpo**: `UserUpdateRequestDTO`
- **Resposta**: String
- **Status**: `200 OK`

#### 3. Deletar Usuário
- **DELETE** `/user/delete`
- **Parâmetros**: `id` (query)
- **Status**: `200 OK`

#### 4. Trocar Senha
- **POST** `/user/change-password/{id}`
- **Parâmetros**: `id` (path)
- **Corpo**: `ChangePasswordRequestDTO`
- **Status**: `200 OK`

#### 5. Validar Login
- **POST** `/user/validate-login`
- **Parâmetros**:
    - `login` (query)
    - `password` (query)
- **Status**: `200 OK`

#### 6. Listar Usuários
- **GET** `/user/find-all`
- **Parâmetros**:
    - `page` (query)
    - `size` (query)
- **Status**: `200 OK`

### Restaurantes (`/restaurant`)

#### 1. Criar Restaurante
- **POST** `/restaurant/create`
- **Corpo**: `RestaurantRequestDTO`
- **Resposta**: `RestaurantResponseDTO`
- **Status**: `201 Created`

#### 2. Listar Restaurantes
- **GET** `/restaurant/find-all`
- **Resposta**: Lista de `RestaurantResponseDTO`
- **Status**: `200 OK`

#### 3. Buscar Restaurante por ID
- **GET** `/restaurant/find-by-id/{id}`
- **Parâmetros**: `id` (path)
- **Resposta**: `RestaurantResponseDTO`
- **Status**: `200 OK`

#### 4. Atualizar Restaurante
- **PATCH** `/restaurant/update/{id}`
- **Parâmetros**: `id` (path)
- **Corpo**: `RestaurantRequestDTO`
- **Resposta**: `RestaurantResponseDTO`
- **Status**: `200 OK`

#### 5. Deletar Restaurante
- **DELETE** `/restaurant/delete/{id}`
- **Parâmetros**: `id` (path)
- **Status**: `200 OK`

### Menu (`/menu`)

#### 1. Criar Menu
- **POST** `/menu/create/{restaurantId}`
- **Parâmetros**: `restaurantId` (path)
- **Corpo**: `MenuRequestDTO`
- **Status**: `201 Created`

#### 2. Listar Menus por Restaurante
- **GET** `/menu/find-all-by-restaurant/{restaurantId}`
- **Parâmetros**: `restaurantId` (path)
- **Resposta**: Lista de `MenuResponseDTO`
- **Status**: `200 OK`

#### 3. Listar Todos os Menus
- **GET** `/menu/find-all`
- **Parâmetros**:
    - `page` (query)
    - `size` (query)
- **Status**: `200 OK`

#### 4. Atualizar Menu
- **PUT** `/menu/update/{id}`
- **Parâmetros**: `id` (path)
- **Corpo**: `MenuItemUpdateRequestDTO`
- **Status**: `200 OK`

#### 5. Deletar Menu
- **DELETE** `/menu/delete/{menuId}`
- **Parâmetros**: `menuId` (path)
- **Status**: `200 OK`

#### 6. Deletar Item do Menu
- **DELETE** `/menu/delete-item/{menuId}/{itemId}`
- **Parâmetros**:
    - `menuId` (path)
    - `itemId` (path)
- **Status**: `200 OK`

### Tipos de Usuário (`/user-type`)

#### 1. Criar Tipo de Usuário
- **POST** `/user-type/create`
- **Corpo**: `UserTypeRequestDTO`
- **Resposta**: `UserTypeResponseDTO`
- **Status**: `201 Created`

#### 2. Listar Tipos de Usuário
- **GET** `/user-type/find-all`
- **Parâmetros**:
    - `page` (query)
    - `size` (query)
- **Status**: `200 OK`

#### 3. Atualizar Tipo de Usuário
- **PATCH** `/user-type/update/{id}`
- **Parâmetros**: `id` (path)
- **Corpo**: `UserTypeRequestDTO`
- **Resposta**: `UserTypeResponseDTO`
- **Status**: `200 OK`

#### 4. Deletar Tipo de Usuário
- **DELETE** `/user-type/delete/{id}`
- **Parâmetros**: `id` (path)
- **Status**: `200 OK`

#### 5. Buscar Tipo de Usuário por ID
- **GET** `/user-type/find-by-id/{id}`
- **Parâmetros**: `id` (path)
- **Resposta**: `UserTypeResponseDTO`
- **Status**: `200 OK`

## Testes
Para executar os testes:
```bash
mvn test
```
## Cobertura de Testes

O projeto utiliza JaCoCo para análise de cobertura de código:

1. Gerar relatório de cobertura:
   ```bash
   mvn clean verify

O relatório pode ser encontrado em:
- `target/site/jacoco/index.html`


## Documentação da API

A documentação completa da API está disponível através do Swagger UI:
- **URL**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`




## Contribuição

Contribuição
Contribuições são bem-vindas! Fique à vontade para abrir issues e pull requests

Contato:
Para mais informações, entre em contato com [Delaine] em [delaine@delaine].