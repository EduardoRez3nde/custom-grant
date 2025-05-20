
---
# 🛡️ OAuth2 Authorization & Resource Server com Spring Security

#### Este projeto implementa um Authorization Server e um Resource Server em Java usando Spring Boot 3 e Spring Authorization Server, com suporte a:

- ✅ **Authorization Server** com Spring Authorization Server
- ✅ **Resource Server** com validação de JWT
- ✅ **OAuth2 Authorization Code Flow com PKCE**
- ✅ **JWT com assinatura RSA (RS256)**
- ✅ **Refresh Token**
- ✅ **Custom claims no token**
- ✅ **Registro de usuário com endpoint `/register`**
- ✅ **Configuração CORS**
- ✅ **Login com formulário**
- ✅ **Integração com Apache Kafka ao emitir token via Authorization Code Flow (com PKCE)**

---

### 🔥 Para que serve esse projeto?

Você pode usar este projeto como base para criar sua própria infraestrutura de autenticação com OAuth2 e JWT. Ele permite que aplicações web e mobile façam login com segurança, obtenham um access token e acessem recursos protegidos com base em permissões e escopos.

---

### 📦 Tecnologias

- Java 21
- Spring Boot 3
- Spring Authorization Server
- Spring Security
- JWT (RS256)
- JWK Set
- PKCE
- OAuth 2.1
- Apache Kafka

---

### ⚙️ Como rodar o projeto

#### 1 - Clone o repositório
```bash
git@github.com:EduardoRez3nde/custom-grant.git
````

#### 2 - Execute a aplicação

```bash
./gradlew bootRun
```

A aplicação estará disponível em: **[http://localhost:8080](http://localhost:8080)**

---

## 🔐 Funcionalidades do Authorization Server

* Suporte a login com formulário (`/login`)
* Registro de novos usuários via `/register`
* Emissão de tokens JWT usando RS256
* Custom claims no token (ex: `username`, `authorities`)
* Publicação de JWK Set (`/oauth2/jwks`)
* Consentimento do usuário
* Suporte a PKCE
* Envio de mensagem para o Kafka após emissão de token (via `authorization_code`)

---

## 🔐 Funcionalidades do Resource Server

* Valida tokens JWT assinados com chave pública (via JWK Set)
* Permite acesso apenas com tokens válidos e escopos corretos
* Suporte a validação de claims personalizados

---

## 🌐 CORS

O CORS pode ser configurado para permitir que seu frontend (ex: React, Angular) se comunique com os servidores de autenticação e recursos:

```yaml
security:
  cors:
    allowed-origins:
      - "http://localhost:3000"
      - "http://localhost:8080"
    allowed-methods:
      - "GET"
      - "POST"
      - "PUT"
      - "DELETE"
    allowed-headers:
      - "*"
    allow-credentials: true
```

---

## 🆕 Registro de usuários

Este projeto expõe um endpoint público `/register` que permite o **cadastro de novos usuários**.

### 📥 Exemplo de chamada `POST /register`

```http
POST /register
Content-Type: application/json

{
  "username": "user@gmail.com",
  "password": "123",
  "roles": [
    {
        "authority": "ROLE_USER"
    },
    {
        "authority": "ROLE_ADMIN"
    }
  ]
}
```

Resposta:

```http
201 Created
```

---

## 📎 Endpoints úteis

| Recurso                     | URL                  |
| --------------------------- | -------------------- |
| Login                       | `/login`             |
| Registro de usuário         | `/register`          |
| Autorizar acesso            | `/oauth2/authorize`  |
| Obter token                 | `/oauth2/token`      |
| JWK Set (chave pública)     | `/oauth2/jwks`       |
| Token Introspect (opcional) | `/oauth2/introspect` |
| Callback (temporário)       | `/callback`          |

---

## Fluxo do **Authorization Code com PKCE:**

![PKCE Flow](https://github.com/user-attachments/assets/3cdcf79d-7167-4892-9b78-747ce5246c99)

### 🔐 Etapas de Autenticação e Autorização

1. **Client gera `code_verifier` e `code_challenge`**
2. **Client redireciona para o Authorization Server**
3. **Usuário faz login**
4. **Usuário consente**
5. **Authorization Server redireciona com `authorization_code`**
6. **Client troca o código por tokens usando o `code_verifier`**

### 🔄 Troca do código por tokens:

```http
POST /oauth2/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic base64(client_id:client_secret)

grant_type=authorization_code
client_id=default-client-id
code=abc123
redirect_uri=http://localhost:8080/callback
code_verifier=<verificador>
```

⚠️ **Importante:**
O endpoint `/callback` é **temporário** e serve apenas para **testar o fluxo OAuth 2.0 completo** durante o desenvolvimento.
Em produção, esse endpoint deve ser **implementado no frontend**, pois é lá que o navegador será redirecionado com o `authorization_code`.

---

## 🔑 Tokens emitidos

Exemplo de resposta do token JWT:

```json
{
  "access_token": "wwqydvqwugdvqugdvqufvwehvfhwvfw",
  "refresh_token": "jdeqwiqwnioqfnnfien",
  "scope": "read write",
  "token_type": "Bearer",
  "expires_in": 299
}
```

---

## 💡 Próximos passos

* 🔜 Integração com PostgreSQL (via Spring Data JPA)
* 🔜 Interface de consentimento customizada
* 🔜 Login com provedores externos (Google, GitHub)

---

## 🧑‍💻 Autor

**Eduardo Rezende**
🐙 [@EduardoRez3nde](https://github.com/EduardoRez3nde)
