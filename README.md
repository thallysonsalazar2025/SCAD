# 🚀 Biblioteca Digital API

## 🎯 Proposta do Projeto

Este projeto foi desenvolvido como uma ferramenta de estudo para explorar e mapear as tecnologias do ecossistema Spring, desde os conceitos básicos até os mais avançados. O objetivo é criar uma API RESTful funcional para o gerenciamento de uma biblioteca digital, servindo como um repositório de referência para boas práticas, soluções de problemas comuns e arquitetura de software com Spring Boot.

A ideia é que este projeto evolua continuamente, incorporando novos padrões e tecnologias à medida que o aprendizado avança.

---

## 🛠️ Tecnologias Utilizadas

*   **Backend:**
    *   [Java 21](https://www.oracle.com/java/technologies/javase/21-relnote-issues.html)
    *   [Spring Boot 3](https://spring.io/projects/spring-boot): Framework principal para criação da aplicação.
    *   [Spring Web](https://docs.spring.io/spring-framework/reference/web/webmvc.html): Para a criação de endpoints REST.
    *   [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Para a persistência de dados de forma simplificada.
    *   [Spring Validation](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.validation): Para validações de dados de entrada.
*   **Banco de Dados:**
    *   [H2 Database](https://www.h2database.com/html/main.html): Banco de dados em memória para ambiente de desenvolvimento e testes.
*   **API e Documentação:**
    *   [OpenAPI 3 (Swagger)](https://swagger.io/specification/): Para a especificação e documentação da API (Abordagem *API-First*).
    *   [OpenAPI Generator](https://openapi-generator.tech/): Ferramenta para gerar o código base da API (models e interfaces) a partir da especificação.
*   **Mapeamento de Objetos:**
    *   [MapStruct](https://mapstruct.org/): Para a conversão automática e performática entre Entidades JPA e DTOs.
*   **Build e Dependências:**
    *   [Maven](https://maven.apache.org/): Gerenciador de dependências e build do projeto.

---

## 🧠 Conceitos Abordados

Este projeto serve como um guia prático para os seguintes conceitos:

### Nível Básico

*   **Criação de uma API RESTful:** Utilização de anotações como `@RestController`, `@GetMapping`, `@PostMapping`, etc.
*   **Injeção de Dependências:** Uso do mecanismo principal do Spring para gerenciar componentes (`@Service`, `@Autowired`, etc.).
*   **Padrão Repository:** Abstração da camada de acesso a dados com Spring Data JPA.
*   **Operações CRUD:** Implementação das operações básicas de Criação, Leitura, Atualização e Deleção.

### Nível Intermediário

*   **Abordagem API-First:** Definição do contrato da API em um arquivo `openapi.yaml` antes da implementação, garantindo um design consistente.
*   **Geração de Código com OpenAPI Generator:** Automação da criação de DTOs e interfaces de controller, reduzindo código boilerplate.
*   **Padrão DTO (Data Transfer Object):** Separação dos objetos de persistência (Entidades) dos objetos de transferência de dados, evitando a exposição de detalhes internos da aplicação.
*   **Mapeamento com MapStruct:** Implementação de mappers para converter Entidades em DTOs de forma eficiente e segura.
*   **Tratamento de Exceções Centralizado:** Uso de `@ControllerAdvice` e `@ExceptionHandler` para criar respostas de erro padronizadas.
*   **Validações Customizadas:** Criação de validadores para regras de negócio específicas.

### Boas Práticas e Padrões Avançados

*   **Separação de Responsabilidades:** Arquitetura em camadas bem definidas:
    1.  `Controller/Delegate`: Orquestra o fluxo HTTP.
    2.  `Service`: Contém a lógica de negócio pura.
    3.  `Repository`: Lida com a persistência de dados.
*   **Resolução de Dependências Cíclicas em DTOs:**
    *   **Problema:** A serialização de entidades com relacionamentos bidirecionais (ex: `Autor` tem uma lista de `Livros`, e `Livro` tem um `Autor`) causa um `StackOverflowError`.
    *   **Solução Aplicada:** Criação de DTOs específicos para cada contexto.
        *   `AutorResponse`: Contém a lista de livros (usado em `GET /autores/{id}`).
        *   `AutorWhithBooksResponse`: Contém apenas os dados do autor, **sem** a lista de livros (usado dentro de `BookResponse`).
        *   Isso quebra o ciclo de chamadas e é a melhor prática para modelar respostas de API complexas.
*   **Uso de Qualifiers no MapStruct (`@Named`):** Utilizado para instruir o MapStruct sobre qual método de mapeamento específico usar em cenários complexos, como na resolução da dependência cíclica.

---

## ⚙️ Como Executar o Projeto

1.  **Pré-requisitos:**
    *   JDK 21 ou superior.
    *   Maven 3.9 ou superior.

2.  **Clone o repositório:**
    ```sh
    git clone <url-do-seu-repositorio>
    cd scad
    ```

3.  **Compile e instale as dependências:**
    O plugin `openapi-generator` será executado automaticamente para gerar as classes da API.
    ```sh
    ./mvnw clean install
    ```

4.  **Execute a aplicação:**
    ```sh
    ./mvnw spring-boot:run
    ```

5.  **Acesse a documentação da API (Swagger UI):**
    Abra o seu navegador e acesse: http://localhost:8080/swagger-ui.html

---

## 💡 Exemplos de Uso (cURL)

#### Atualizar um livro (ID 1)
```sh
curl -X PUT 'http://localhost:8080/books/1' \
-H 'Content-Type: application/json' \
-d '{
  "title": "O Guia do Mochileiro das Galáxias (Edição de Colecionador)",
  "publicationYear": 1979,
  "isbn": "978-85-8041-983-5",
  "genre": "SCIENCE_FICTION",
  "authorId": 2
}'
```

#### Deletar um livro (ID 1)
```sh
curl -X DELETE 'http://localhost:8080/books/1'
```