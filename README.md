# RangoFast Delivery

## 1. Introdução

O presente projeto consiste no desenvolvimento de uma API REST para gerenciamento de um sistema de delivery de alimentos, denominada **RangoFast Delivery**. A aplicação foi desenvolvida utilizando o ecossistema Spring Boot, com o objetivo de aplicar conceitos relacionados ao desenvolvimento de aplicações distribuídas, persistência de dados, documentação de serviços e boas práticas de engenharia de software.

A API permite o gerenciamento de informações referentes a clientes, estabelecimentos, produtos e pedidos, servindo como base para sistemas de comércio eletrônico voltados ao setor alimentício.

## 2. Objetivos

### 2.1 Objetivo Geral

Desenvolver uma API REST capaz de fornecer serviços para gerenciamento de operações de um sistema de delivery de alimentos.

### 2.2 Objetivos Específicos

- Implementar operações de cadastro, consulta, atualização e exclusão de entidades do sistema;
- Aplicar conceitos de persistência de dados utilizando JPA e Hibernate;
- Disponibilizar documentação interativa da API por meio do Swagger/OpenAPI;
- Utilizar banco de dados relacional em memória para fins de desenvolvimento e testes;
- Seguir uma arquitetura baseada em camadas, promovendo maior modularidade e manutenibilidade do código.

## 3. Tecnologias Utilizadas

| Tecnologia | Finalidade |
|-----------|------------|
| Java | Linguagem de programação principal |
| Spring Boot | Framework para construção da API |
| Spring Data JPA | Camada de persistência |
| Hibernate | Implementação ORM |
| H2 Database | Banco de dados em memória |
| Maven | Gerenciamento de dependências |
| Swagger/OpenAPI | Documentação dos serviços |
| Lombok | Redução de código boilerplate |

## 4. Arquitetura do Sistema

O sistema foi desenvolvido seguindo o padrão arquitetural em camadas (*Layered Architecture*), composto pelos seguintes módulos:

### Camada de Apresentação (*Controller*)

Responsável pelo recebimento das requisições HTTP e envio das respostas ao cliente.

### Camada de Serviço (*Service*)

Contém as regras de negócio da aplicação e realiza a comunicação entre controladores e repositórios.

### Camada de Persistência (*Repository*)

Responsável pela interação com o banco de dados através do Spring Data JPA.

### Camada de Modelo (*Entity*)

Representa as entidades persistidas no banco de dados.

---

## 5. Estrutura do Projeto

```
src
├── main
│   ├── java
│   │   └── br.com.rangofast
│   │       ├── controller
│   │       ├── service
│   │       ├── repository
│   │       ├── entity
│   │       └── dto
│   └── resources
│       └── application.properties
```

## 6. Banco de Dados

Durante o desenvolvimento foi utilizado o banco de dados H2, executado em memória, dispensando a necessidade de instalação de um SGBD externo.

### Acesso ao Console H2
```
http://localhost:8080/h2-console
```

Configurações padrão:

| Propriedade | Valor |
|------------|-------|
| JDBC URL | jdbc:h2:mem:rangofast |
| Usuário | sa |
| Senha | *(em branco)* |

## 7. Execução da Aplicação

### Clonando o repositório

```bash
git clone <URL_DO_REPOSITORIO>
```

### Acessando o diretório

```bash
cd gestao-delivery
```

### Executando a aplicação

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

---

## 8. Documentação da API

A documentação interativa dos serviços REST pode ser acessada através do Swagger UI.

```
http://localhost:8080/swagger-ui/index.html
```

Especificação OpenAPI:

```
http://localhost:8080/v3/api-docs
```


