# API Foro — Endpoints y ejemplos (Requests / Responses)

Este archivo recopila, de manera clara y ordenada, todos los endpoints implementados en la API de foro del proyecto, con ejemplos JSON para usar en herramientas como Insomnia/Postman o con curl.

Base: http://localhost:8080 (variable: `base_url`)
Autenticación: JWT Bearer. Obtener token con `/api/auth/login` o `/api/auth/register`. Añadir header:
Authorization: Bearer <TOKEN>

---

## 1) AUTH

### POST /api/auth/register
- Público
- Headers: `Content-Type: application/json`
- Request JSON:
{
  "username": "miguel",
  "email": "miguel@example.com",
  "password": "Secreto123"
}
- Response (200 OK) ejemplo:
{
  "token": "<JWT_TOKEN>",
  "tokenType": "Bearer"
}

### POST /api/auth/login
- Público
- Headers: `Content-Type: application/json`
- Request JSON:
{
  "username": "miguel",
  "password": "Secreto123"
}
- Response (200 OK) ejemplo:
{
  "token": "<JWT_TOKEN>",
  "tokenType": "Bearer"
}

---

## 2) TOPICS

### GET /api/topics?page=0&size=10
- Público (no auth requerido)
- Response (200 OK) — paginado (ejemplo simplificado):
{
  "content": [
    {
      "id": 11,
      "title": "Dudas Spring Boot",
      "description": "¿Cómo funciona la inyección de dependencias?",
      "author": { "id": 7, "username": "miguel", "email": "miguel@example.com" },
      "createdAt": "2026-02-06T13:30:00",
      "updatedAt": "2026-02-06T13:30:00"
    }
  ],
  "pageable": { "pageNumber": 0, "pageSize": 10 },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0
}

### POST /api/topics
- Requiere: Authorization: Bearer <TOKEN>
- Headers: `Content-Type: application/json`, `Authorization: Bearer <TOKEN>`
- Request JSON (CreateTopicDTO):
{
  "title": "Dudas Spring Boot",
  "description": "¿Cómo funciona la inyección de dependencias?"
}
- Response (201 Created)
  - Header `Location: /api/topics/{id}`
  - Body (TopicDTO) ejemplo:
{
  "id": 11,
  "title": "Dudas Spring Boot",
  "description": "¿Cómo funciona la inyección de dependencias?",
  "author": { "id": 7, "username": "miguel", "email": "miguel@example.com" },
  "createdAt": "2026-02-06T13:30:00",
  "updatedAt": "2026-02-06T13:30:00"
}

### GET /api/topics/{id}
- Público
- Response (200 OK) ejemplo:
{
  "id": 11,
  "title": "Dudas Spring Boot",
  "description": "¿Cómo funciona la inyección de dependencias?",
  "author": { "id": 7, "username": "miguel", "email": "miguel@example.com" },
  "createdAt": "2026-02-06T13:30:00",
  "updatedAt": "2026-02-06T13:30:00"
}

### PUT /api/topics/{id}
- Requiere: Authorization (solo autor)
- Headers: `Content-Type: application/json`, `Authorization: Bearer <TOKEN>`
- Request JSON (CreateTopicDTO):
{
  "title": "Dudas Spring Boot (actualizado)",
  "description": "Recursos, dudas y ejemplos"
}
- Response (200 OK) ejemplo:
{
  "id": 11,
  "title": "Dudas Spring Boot (actualizado)",
  "description": "Recursos, dudas y ejemplos",
  "author": { "id": 7, "username": "miguel", "email": "miguel@example.com" },
  "createdAt": "2026-02-06T13:30:00",
  "updatedAt": "2026-02-06T13:45:00"
}
- Errores típicos:
  - 401 Unauthorized (sin token)
  - 400 Bad Request ("Only the author can update the topic") si no eres autor
  - 404 Not Found si no existe

### DELETE /api/topics/{id}
- Requiere: Authorization (solo autor)
- Headers: `Authorization: Bearer <TOKEN>`
- Response: 204 No Content (éxito)
- Errores: 401 / 400 / 404

---

## 3) POSTS (dentro de un topic)

### GET /api/topics/{topicId}/posts?page=0&size=10
- Público
- Response (200 OK) ejemplo (paginado):
{
  "content": [
    {
      "id": 103,
      "content": "Bienvenido al foro!",
      "author": { "id": 7, "username": "miguel", "email": "miguel@example.com" },
      "topicId": 11,
      "createdAt": "2026-02-06T13:35:00",
      "updatedAt": "2026-02-06T13:35:00"
    }
  ],
  "pageable": { "pageNumber": 0, "pageSize": 10 },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0
}

### POST /api/topics/{topicId}/posts
- Requiere: Authorization: Bearer <TOKEN>
- Headers: `Content-Type: application/json`, `Authorization: Bearer <TOKEN>`
- Request JSON (CreatePostDTO):
{
  "content": "Gracias por la bienvenida, comparto mi experiencia..."
}
- Response (201 Created)
  - Header Location: /api/topics/{topicId}/posts/{id}
  - Body (PostDTO) ejemplo:
{
  "id": 103,
  "content": "Gracias por la bienvenida, comparto mi experiencia...",
  "author": { "id": 8, "username": "ana", "email": "ana@example.com" },
  "topicId": 11,
  "createdAt": "2026-02-06T13:40:00",
  "updatedAt": "2026-02-06T13:40:00"
}

### PUT /api/topics/{topicId}/posts/{postId}
- Requiere: Authorization (solo autor del post)
- Headers: `Content-Type: application/json`, `Authorization: Bearer <TOKEN>`
- Request JSON (CreatePostDTO):
{
  "content": "Edición: corregí un typo."
}
- Response (200 OK) ejemplo:
{
  "id": 103,
  "content": "Edición: corregí un typo.",
  "author": { "id": 8, "username": "ana", "email": "ana@example.com" },
  "topicId": 11,
  "createdAt": "2026-02-06T13:40:00",
  "updatedAt": "2026-02-06T13:50:00",
  "edited": true
}
- Errores: 401 / 400 / 404

### DELETE /api/topics/{topicId}/posts/{postId}
- Requiere: Authorization (solo autor)
- Headers: `Authorization: Bearer <TOKEN>`
- Response: 204 No Content

---

## Flujo de prueba (paso a paso)
1) Registrar usuario
   - POST /api/auth/register
   - Body: { "username":"miguel", "email":"miguel@example.com", "password":"Secreto123" }
   - Guardar `token` del response

2) Crear topic
   - POST /api/topics (Authorization: Bearer <token>)
   - Body: { "title":"Dudas Spring Boot", "description":"¿Cómo funciona la inyección de dependencias?" }
   - Guardar `topic_id` desde body.id o Location header

3) Crear post
   - POST /api/topics/{topic_id}/posts (Authorization: Bearer <token>)
   - Body: { "content":"Mi aporte al tema..." }
   - Guardar `post_id`

4) Editar/Borrar (usar mismo token; si no eres autor verás error de negocio)

---

## Notas adicionales
- H2 console: /h2-console (según `SecurityConfig` está permitida)
- En errores de negocio (IllegalArgumentException) la API devuelve 400 con mensaje en el body (ver `GlobalExceptionHandler`).

---

Si quieres, guardo este archivo en otra ruta o lo exporto también como `docs/api_endpoints_examples.json` para importarlo desde otras herramientas; dime si lo adapto a Postman o si quieres que además añada los curl exactos para cada request del flujo. 
