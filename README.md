# 🐾 Animal API

Esta API é responsável por gerenciar animais e associá-los a pessoas já cadastradas via outra API protegida por JWT.

## 🔐 Autenticação

Todos os endpoints `/api/animais/**` exigem autenticação JWT.

### Exemplo de uso com Postman

1. Obtenha um token JWT através da `auth-api`
2. No Postman, insira o token no header da requisição:

```
Authorization: Bearer SEU_TOKEN_AQUI
```

## 🚀 Endpoints

| Método | Endpoint                                 | Descrição                                       |
|--------|------------------------------------------|--------------------------------------------------|
| GET    | `/api/animais`                           | Lista todos os animais                           |
| GET    | `/api/animais/{id}`                      | Busca animal por ID                              |
| POST   | `/api/animais`                           | Cadastra um novo animal                          |
| PUT    | `/api/animais/{id}`                      | Atualiza animal por ID                           |
| DELETE | `/api/animais/{id}`                      | Deleta animal por ID                             |
| PATCH  | `/api/animais/{idAnimal}/associar-pessoa/{idPessoa}` | Associa uma pessoa a um animal        |
| GET    | `/api/animais/{id}/com-pessoa`           | Busca animal com dados da pessoa associada       |
| GET    | `/api/animais/teste-pessoa`              | Testa integração com API de pessoas              |

## ⚙️ application.properties

```
spring.application.name=animal-api
server.port=8085

spring.data.mongodb.uri=mongodb://localhost:27017/animal_db
```

## 🛠 Tecnologias

- Spring Boot
- MongoDB
- JWT (Integração com auth-api)
- Comunicação REST com outras APIs (como api-pessoas)

---

Feito por Matheus Maximiano Lourenço