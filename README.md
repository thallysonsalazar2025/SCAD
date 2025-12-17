# 🚀 SCAD - Servidor de Autorização e API

## 🎯 Proposta do Projeto

Este projeto foi desenvolvido como uma ferramenta de estudo para explorar e mapear as tecnologias do ecossistema Spring, desde os conceitos básicos até os mais avançados. O objetivo é criar um **Servidor de Autorização** completo utilizando **Spring Authorization Server**, juntamente com uma API de recursos protegida, servindo como um repositório de referência para boas práticas, soluções de problemas comuns e arquitetura de segurança com Spring Boot.

A ideia é que este projeto evolua continuamente, incorporando novos padrões e tecnologias à medida que o aprendizado avança.

---

## 🛠️ Tecnologias Principais

*   **Backend:**
    *   [Java 21](https://www.oracle.com/java/technologies/javase/21-relnote-issues.html)
    *   [Spring Boot 3](https://spring.io/projects/spring-boot): Framework principal para criação da aplicação.
    *   [Spring Web](https://docs.spring.io/spring-framework/reference/web/webmvc.html): Para a criação de endpoints REST.
    *   [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Para a persistência de dados de forma simplificada.
    *   [Spring Security 6](https://spring.io/projects/spring-security): Para gerenciamento de autenticação e autorização.
    *   [Spring Authorization Server](https://spring.io/projects/spring-authorization-server): Para implementar um servidor OAuth 2.1 e OpenID Connect 1.0.
    *   [Spring Validation](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.validation): Para validações de dados de entrada.
*   **Banco de Dados:**
    *   [MySQL](https://www.mysql.com/): Banco de dados relacional para persistência dos dados.
*   **API e Documentação:**
    *   [OpenAPI 3 (Swagger)](https://swagger.io/specification/): Para a especificação e documentação da API (Abordagem *API-First*).
    *   [OpenAPI Generator](https://openapi-generator.tech/): Ferramenta para gerar o código base da API (models e interfaces) a partir da especificação.
*   **Mapeamento de Objetos:**
    *   [MapStruct](https://mapstruct.org/): Para a conversão automática e performática entre Entidades JPA e DTOs.
*   **Build e Dependências:**
    *   [Maven](https://maven.apache.org/): Gerenciador de dependências e build do projeto.

*   **Protocolos de Segurança:**
    *   [OAuth 2.1](https://oauth.net/2.1/): Framework de autorização.
    *   [OpenID Connect 1.0](https://openid.net/connect/): Camada de identidade sobre o OAuth 2.0.
    *   [JWT (JSON Web Token)](https://jwt.io/): Formato dos tokens de acesso.
---

## 🧠 Conceitos Abordados

Este projeto serve como um guia prático para os seguintes conceitos:

### Nível Básico

*   **Criação de uma API RESTful:** Utilização de anotações como `@RestController`, `@GetMapping`, `@PostMapping`, etc.
*   **Injeção de Dependências:** Uso do mecanismo principal do Spring para gerenciar componentes (`@Service`, `@Autowired`, etc.).
*   **Padrão Repository:** Abstração da camada de acesso a dados com Spring Data JPA.
*   **Operações CRUD:** Implementação das operações básicas de Criação, Leitura, Atualização e Deleção.

### Nível Intermediário

*   **Abordagem API-First:** Definição do contrato da API em um arquivo `openapi.yaml`.
*   **Geração de Código com OpenAPI Generator:** Automação da criação de DTOs e interfaces.
*   **Padrão DTO (Data Transfer Object):** Separação entre Entidades e objetos de transferência de dados.
*   **Mapeamento com MapStruct:** Conversão eficiente e segura entre Entidades e DTOs.
*   **Tratamento de Exceções Centralizado:** Uso de `@ControllerAdvice` para respostas de erro padronizadas.
*   **Validações Customizadas:** Implementação de validadores para regras de negócio.

### Boas Práticas e Padrões Avançados

*   **Separação de Responsabilidades:** Arquitetura em camadas bem definidas:
    1.  `Controller/Delegate`: Orquestra o fluxo HTTP.
    2.  `Service`: Contém a lógica de negócio pura.
    3.  `Repository`: Lida com a persistência de dados.
*   **Resolução de Dependências Cíclicas em DTOs:**
    *   **Problema:** Serialização de entidades com relacionamentos bidirecionais (ex: `Autor` <-> `Livro`) causa `StackOverflowError`.
    *   **Solução Aplicada:** Criação de DTOs específicos para cada contexto, quebrando o ciclo de chamadas.
*   **Uso de Qualifiers no MapStruct (`@Named`):** Utilizado para instruir o MapStruct sobre qual método de mapeamento usar em cenários complexos.

---

## 🔐 Fluxo de Autenticação (Authorization Code)

O fluxo principal implementado é o `Authorization Code`, que é o mais seguro e completo para aplicações que possuem um backend. O diagrama abaixo ilustra a interação entre o usuário, a aplicação cliente, o servidor de autorização e o provedor de identidade (Google).

!Fluxo de Autenticação
![fluxo de autenticação.jpeg](../../Users/Thallyson/Pictures/fluxo%20de%20autentica%C3%A7%C3%A3o.jpeg)

*Observação: O caminho da imagem foi atualizado para refletir a localização recomendada dentro do projeto.*

### Explicação do Fluxo

1.  **Início (Usuário)**: O usuário tenta acessar um recurso protegido na Aplicação Cliente ou clica em um botão "Login".

2.  **Redirecionamento para o Servidor de Autorização**: A Aplicação Cliente redireciona o navegador do usuário para o endpoint `/oauth2/authorize` do nosso Servidor de Autorização (SCAD), enviando parâmetros como `client_id`, `redirect_uri`, `scope` e `response_type=code`.

3.  **Autenticação do Usuário**: O Servidor de Autorização exibe uma página de login. O usuário pode se autenticar de duas formas:
    -   **Formulário**: Inserindo seu usuário e senha.
    -   **Login Social**: Clicando em "Login com Google". Neste caso, o usuário é redirecionado para a página de autenticação do Google.

4.  **Retorno do Google (se aplicável)**: Após o login no Google, o usuário é redirecionado de volta para o Servidor de Autorização com um código temporário do Google. O servidor valida esse código, obtém as informações do usuário e cria uma sessão autenticada.

5.  **Geração do Código de Autorização**: Com o usuário autenticado, o Servidor de Autorização gera o seu próprio **código de autorização** (`code`) e redireciona o navegador do usuário para a `redirect_uri` que foi informada no passo 2. O código é enviado como um parâmetro na URL.

6.  **Troca do Código pelo Token**: A Aplicação Cliente, ao receber a requisição em sua `redirect_uri`, extrai o `code` da URL. Em seguida, o backend da Aplicação Cliente faz uma chamada direta (servidor-para-servidor) para o endpoint `/oauth2/token` do Servidor de Autorização. Nessa chamada, ele envia:
    -   O `code` recebido.
    -   O `grant_type` como `authorization_code`.
    -   Suas credenciais (`client_id` e `client_secret`) via `Basic Auth`.

7.  **Emissão do Token**: O Servidor de Autorização valida todas as informações e, se tudo estiver correto, retorna um **Access Token** (JWT) para a Aplicação Cliente.

8.  **Acesso à API Protegida**: A Aplicação Cliente pode, finalmente, usar o Access Token para fazer requisições aos endpoints protegidos da API (ex: `/api/clients`), enviando o token no cabeçalho `Authorization: Bearer <token>`.

---

## ⚙️ Como Executar o Projeto

1.  **Pré-requisitos:**
    *   JDK 21 ou superior instalado.
    *   Maven 3.9 ou superior instalado.
    *   MySQL Server em execução.

2.  **Clone o repositório:**
    ```sh
    git clone <url-do-seu-repositorio>
    cd scad
    ```
3.  **Configure as variáveis de ambiente:**
    No arquivo `src/main/resources/application.yaml`, certifique-se de que as variáveis de ambiente para o banco de dados e para as credenciais do Google (`GOOGLE_CLIENT_ID` e `GOOGLE_CLIENT_SECRET`) estejam configuradas corretamente.

4.  **Compile e instale as dependências:**
    O plugin `openapi-generator` será executado automaticamente para gerar as classes da API.
    ```sh
    ./mvnw clean install
    ```

5.  **Execute a aplicação:**
    ```sh
    ./mvnw spring-boot:run
    ```

6.  **Acesse a aplicação:**
    O servidor estará disponível em `http://localhost:8080/api/v1`. A documentação da API (Swagger UI) pode ser acessada em `http://localhost:8080/api/v1/swagger-ui.html`
    
---

## 💡 Endpoints Principais

*   **Authorization Server**:
    *   `GET /api/v1/oauth2/authorize`: Inicia o fluxo de autorização.
    *   `POST /api/v1/oauth2/token`: Troca um código ou credenciais por um token.
*   **API de Exemplo**:
    *   `POST /api/v1/clients`: Cria um novo cliente OAuth2 (requer autenticação e role `ADMIN`).