# TasteManager

TasteManager é uma aplicação desenvolvida em **Java 21** utilizando o framework **Spring Boot**. O objetivo do projeto é
gerenciar usuários e suas informações, incluindo funcionalidades como criação, atualização, exclusão e validação de
login.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Maven**
- **MapStruct** (para mapeamento de DTOs)
- **Docker** (opcional, para containerização)
- **Banco de Dados SQL**

## Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:

- **Java 21** ou superior
- **Maven** (para gerenciamento de dependências)
- **Docker** (opcional, para execução em container)
- **PostgreSQL** ou outro banco de dados SQL configurado

## Configuração do Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/delainefiap/taste-manager.git
   ```
   
2. **Construa a imagem Docker**:
   ```bash
   docker build -t tastemanager-app . 

3. **Suba a aplicação com o Docker Compose**:
   ```bash
   docker-compose up --build
   ```
3. **Acesse a aplicação**:
 ```bash
   URL base: http://localhost:8080
 ```
## **Endpoints da API**

### **Usuários**

#### **Criar Usuário**
- **POST** `/user/create`
- **Body**:
  ```json
  {
    "name": "John Doe",
    "email": "johndoe@example.com",
    "login": "johndoe",
    "password": "123456",
    "typePerson": "restaurant_owner",
    "address": "123 Main Street"
  }
  ```
- **Resposta**: 
```String
  HTTP 201 Created
  {
    "message": "User created successfully"
  }
  ```
