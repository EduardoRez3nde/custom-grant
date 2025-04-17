# 🛡️ OAuth2 Authorization & Resource Server com Spring Security

#### Este projeto implementa um Authorization Server e um Resource Server em Java usando Spring Boot 3 e Spring Authorization Server, com suporte a:

- ✅ **Authorization Server** com Spring Authorization Server
- ✅ **Resource Server** com validação de JWT
- ✅ **OAuth2 Authorization Code Flow com PKCE**
- ✅ **JWT com assinatura RSA (RS256)**
- ✅ **refresh token**
- ✅ **Custom claims no token**
- ✅ **Configuração CORS**
- ✅ **Login com formulário**

### 🔥 Para que serve esse projeto?
- Você pode usar este projeto como base para criar sua própria infraestrutura de autenticação com OAuth2 e JWT. Ele permite que aplicações web e mobile façam login com segurança, obtenham um access token e acessem recursos protegidos com base em permissões e escopos.

### 📦 Tecnologias
- Java 21
- Spring Boot 3
- Spring Authorization Server
- Spring Security
- JWT (RS256)
- JWK Set
- PKCE
- OAuth 2.1

⚙️ Como rodar o projeto
#### 1 - Clone o repositório
```
git@github.com:EduardoRez3nde/custom-grant.git
```

#### 2 - Execute a aplicação
```
./gradlew bootRun
```
A aplicação estará disponível em: **http://localhost:8080**


### 🔐 Funcionalidades do Authorization Server

- Suporte a login com formulário (`/login`)
- Emissão de tokens JWT usando RS256
- Custom claims no token (ex: username, authorities)
- Publicação de JWK Set (`/oauth2/jwks`)
- Consentimento do usuário
- Suporte a PKCE

---

## 🔐 Funcionalidades do Resource Server

- Valida tokens JWT assinados com chave pública (via JWK Set)
- Permite acesso apenas com tokens válidos e escopos corretos
- Suporte a validação de claims personalizados

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

## 🔑 Tokens emitidos

Exemplo de token JWT emitido:

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

## 📎 Endpoints úteis

| Recurso                     | URL                          |
|----------------------------|------------------------------|
| Login                      | `/login`                     |
| Autorizar acesso           | `/oauth2/authorize`          |
| Obter token                | `/oauth2/token`              |
| JWK Set (chave pública)    | `/oauth2/jwks`               |
| Token Introspection        | `/oauth2/introspect` (opcional) |

---
## Fluxo do **Authorization Code com PKCE:**
![image](https://github.com/user-attachments/assets/3cdcf79d-7167-4892-9b78-747ce5246c99)

### 🔐 **Fluxo OAuth 2.0 com PKCE (Authorization Code Flow)**

#### ✅ Etapas de Autenticação e Autorização:

1. **`Client` gera code verifier e code challenge**  
   - O **cliente (app)** cria um **code verifier** (uma string aleatória) e deriva dele um **code challenge** (hash com SHA-256).
   - Você pode utlizar esse site **https://tonyxu-io.github.io/pkce-generator/** para gerar **code verifier** e apartir dele o **code challenge**.

2. **`Client` redireciona o navegador para o STS (Security Token Service)**  
   - O cliente redireciona o usuário (via navegador) para o STS, **incluindo o code challenge** e outras informações (como client_id, redirect_uri, etc).
     - **No Navegador**
       ```
       http://localhost:8080/oauth2/authorize?response_type=code
        &client_id=my-client
        &redirect_uri=http://localhost:8080/callback
        &scope=read write
        &code_challenge=gerado na etapa anterior
        &code_challenge_method=S256
       ```

3. **O navegador segue o redirecionamento para o STS com o code challenge**  
   - O navegador carrega a URL do STS para iniciar o processo de login/autorização.

4. **STS pergunta ao usuário: "Quem é você?"**  
   - O STS apresenta uma tela de login.

5. **Usuário se autentica no STS**  
   - O usuário fornece suas credenciais.
   - Há um usuario em memoria para teste: **configuration/profile/SecurityConfigDev**

6. **STS pergunta: "Permite que o app acesse seus dados?"**  
   - O usuário dá (ou nega) o consentimento.

7. **Usuário autoriza o cliente**  
   - Com o consentimento, o STS prossegue.

8. **STS armazena o code challenge junto com o authorization code**  
   - O STS associa o `code challenge` ao `authorization code` recém-gerado.

9. **STS redireciona o navegador com o authorization code para o domínio do client**  
   - O navegador é redirecionado de volta para a aplicação com o `authorization code`.

10. **O navegador segue o redirecionamento com o authorization code**  
   - A app recebe o `authorization code`.

### 🔄 **Troca do código por tokens:**

11. **Client envia o authorization code e o code verifier ao STS**  
   - O cliente agora solicita o token, enviando o `authorization code` e o `code verifier`.

12. **STS verifica o code verifier comparando com o code challenge armazenado**  
   - O STS valida que o `code verifier` bate com o `code challenge` originalmente enviado.

13. **STS retorna access token e refresh token**  
   - Se a verificação for bem-sucedida, os tokens são retornados ao cliente.

⚠️ Observação importante
O endpoint `/callback` foi criado apenas para fins de teste do fluxo completo.
Em ambientes reais com PKCE, a troca do code por token deve ser feita pelo cliente.
#### Exemplo: 
  ```
  POST /oauth2/token
  Content-Type: application/x-www-form-urlencoded
  Authorization: Basic base64(client_id:client_secret)

  grant_type=authorization_code
  client_id=default-client-id
  code=abc123
  redirect_uri=http://localhost:8080/callback
  code_verifier=e-ZUM9ycJVDA7icWxAwa-5CckCZMSka9tUZmLiUTRk4
  ```
#### A função callback faz automaticamente.

### ⚙️ **Acesso ao recurso:**

14. **Client usa o access token para acessar a API**  
   - O cliente faz requisições à API protegida com o `access token`.

15. **API responde com os dados protegidos**  
   - A API valida o token e retorna os dados.

## 💡 Próximos passos

- 🔜 Integrar com banco de dados PostgreSQL (via Spring Data JPA)
- 🔜 Registrar usuários
- 🔜 Interface de consentimento customizada
- 🔜 Login com provedores externos (ex: Google, GitHub)

---

## 🧑‍💻 Autor

**Eduardo Rezende**  
🐙 [@EduardoRez3nde](https://github.com/EduardoRez3nde)

---
