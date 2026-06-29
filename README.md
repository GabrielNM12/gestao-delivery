# RangoFast Delivery API

API REST para um **Sistema de Gestão de Delivery Regional**, desenvolvida pela TechSolutions para o cliente RangoFast. O sistema centraliza o cadastro de **restaurantes**, **clientes** e o gerenciamento de **pedidos**, substituindo o controle descentralizado feito via WhatsApp e planilhas.

> Projeto da disciplina de **Backend** — Engenharia de Software (4ª Fase). Atividade Baseada em Problema (ABP).

## Tecnologias

- **Java 17**
- **Spring Boot 3.3.5** (Spring Web, Spring Data JPA, Validation)
- **Banco de dados H2** (em memória)
- **springdoc-openapi** (Swagger UI)
- **Maven**

## Como rodar o projeto

Pré-requisitos: **JDK 17+** e **Maven** instalados (ou uma IDE como IntelliJ/Eclipse/VS Code com suporte a Spring Boot).

```bash
# 1. Clonar o repositório
git clone <URL_DO_REPOSITORIO>
cd gestao-delivery-regional

# 2. Executar
mvn spring-boot:run
```

Alternativamente, gere o `.jar` e execute:

```bash
mvn clean package
java -jar target/rangofast-delivery-1.0.0.jar
```

A aplicação sobe em **http://localhost:8080**. Ao iniciar, alguns dados de exemplo (restaurantes, clientes, um entregador e um pedido) são carregados automaticamente.

### Ferramentas úteis

| Recurso | URL |
|---|---|
| Swagger UI (documentação interativa) | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| Console do H2 | http://localhost:8080/h2-console |

No console do H2, use: **JDBC URL** `jdbc:h2:mem:rangofast`, **User** `sa`, senha em branco.

## Endpoints

Base URL: `/api`

### Restaurantes — `/api/restaurantes`

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/api/restaurantes` | Lista com **filtro** (`nome`, `categoria`, `ativo`), **paginação** e **ordenação** |
| GET | `/api/restaurantes/{id}` | Busca por id |
| POST | `/api/restaurantes` | Cria restaurante (201) |
| PUT | `/api/restaurantes/{id}` | Atualiza restaurante |
| DELETE | `/api/restaurantes/{id}` | Remove restaurante (204) |

Exemplo de filtro/paginação/ordenação:
```
GET /api/restaurantes?nome=pizza&categoria=PIZZARIA&ativo=true&page=0&size=5&sort=nome,asc
```

### Clientes — `/api/clientes`

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/api/clientes` | Lista com paginação e ordenação |
| GET | `/api/clientes/{id}` | Busca por id |
| POST | `/api/clientes` | Cadastra cliente (201) |
| PUT | `/api/clientes/{id}` | Atualiza cliente |
| DELETE | `/api/clientes/{id}` | Remove cliente (204) |

### Pedidos — `/api/pedidos`

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/api/pedidos` | Lista com **filtro** (`status`, `clienteId`, `restauranteId`), paginação e ordenação |
| GET | `/api/pedidos/{id}` | Busca por id |
| POST | `/api/pedidos` | Cria pedido (201) |
| PUT | `/api/pedidos/{id}` | Atualiza pedido por completo |
| PATCH | `/api/pedidos/{id}/status` | Atualiza apenas o status |
| DELETE | `/api/pedidos/{id}` | Remove pedido (204) |

### Exemplos de corpo (JSON)

**Criar restaurante** — `POST /api/restaurantes`
```json
{
  "nome": "Cantina da Nonna",
  "categoria": "ITALIANA",
  "taxaEntrega": 8.50,
  "ativo": true,
  "endereco": {
    "logradouro": "Rua Itália", "numero": "45", "bairro": "Centro",
    "cidade": "Blumenau", "estado": "SC", "cep": "89000-000"
  }
}
```

**Criar cliente** — `POST /api/clientes`
```json
{
  "nome": "Maria Silva",
  "email": "maria@email.com",
  "telefone": "47999998888",
  "endereco": {
    "logradouro": "Rua das Palmeiras", "numero": "120", "bairro": "Garcia",
    "cidade": "Blumenau", "estado": "SC", "cep": "89010-000"
  }
}
```

**Criar pedido** — `POST /api/pedidos`
```json
{
  "clienteId": 1,
  "restauranteId": 1,
  "entregadorId": 1,
  "itens": [
    { "produto": "X-Burguer", "quantidade": 2, "precoUnitario": 24.90 },
    { "produto": "Refrigerante", "quantidade": 1, "precoUnitario": 8.00 }
  ]
}
```

**Atualizar status** — `PATCH /api/pedidos/1/status`
```json
{ "status": "EM_PREPARO" }
```

Valores de `status`: `PENDENTE`, `CONFIRMADO`, `EM_PREPARO`, `SAIU_PARA_ENTREGA`, `ENTREGUE`, `CANCELADO`.
Valores de `categoria`: `LANCHES`, `PIZZARIA`, `JAPONESA`, `BRASILEIRA`, `ITALIANA`, `SAUDAVEL`, `DOCES`, `BEBIDAS`.

## Status HTTP retornados

| Código | Quando |
|---|---|
| 200 OK | Consulta/atualização bem-sucedida |
| 201 Created | Recurso criado (POST) |
| 204 No Content | Remoção bem-sucedida (DELETE) |
| 400 Bad Request | Validação de campos ou regra de negócio violada |
| 404 Not Found | Recurso inexistente |
| 500 Internal Server Error | Erro inesperado |

Todos os erros seguem um corpo padronizado (`ErrorResponse`) com `timestamp`, `status`, `error`, `message`, `path` e, quando aplicável, a lista de `detalhes`.

## Arquitetura e camadas

```
src/main/java/com/techsolutions/rangofast
├── RangoFastApplication.java      # classe principal
├── config/                        # CORS, OpenAPI, carga de dados
├── controller/                    # 3 controllers REST (CRUD completo)
├── service/                       # interfaces + classe abstrata + impl
├── repository/                    # Spring Data JPA (consultas com filtro)
├── model/                         # entidades, herança, enums, embeddable
├── dto/                           # objetos de request/response
└── exception/                     # exceções + @RestControllerAdvice
```

## Conceitos de POO aplicados

| Conceito | Onde |
|---|---|
| **Herança** | `Cliente` e `Entregador` estendem a classe abstrata `Pessoa`; impls estendem `AbstractCrudService` |
| **Classe abstrata** | `Pessoa`, `AbstractCrudService` |
| **Interface** | `Notificavel`, `CrudService`, `RestauranteService`/`ClienteService`/`PedidoService`, repositórios JPA |
| **Polimorfismo** | `getTipo()` e `gerarNotificacao()` sobrescritos por subclasse; `getRepository()` nas impls |
| **Composição** | `Pedido` contém `List<ItemPedido>`; `Endereco` embutido (`@Embedded`) |
| **Collections** | `List<ItemPedido>`, `List<Pessoa>` no `DataSeeder` |
| **Encapsulamento** | atributos privados com getters/setters em todas as entidades |
| **Sobrecarga de método** | `Pedido.calcularTotal()` e `calcularTotal(BigDecimal)` |
| **Sobrecarga de construtores** | `Pessoa`, `Restaurante`, `Pedido`, `Endereco`, `ItemPedido`, exceções |

## Recursos avançados

- **Paginação e ordenação**: via `Pageable`/`@PageableDefault` em todas as listagens.
- **Filtro**: consultas `@Query` com parâmetros opcionais em restaurantes e pedidos.
- **CORS**: configurado em `CorsConfig` para `/api/**`.
- **Tratamento de exceções**: `GlobalExceptionHandler` (`@RestControllerAdvice`) com `try-catch`/`throws` nos services e status HTTP apropriados.

---

Desenvolvido como avaliação final da disciplina de Backend — TechSolutions / RangoFast.
