# API -> Pass-in

<img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot" /> <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=black" /> <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white" />

## Descrição

API REST desenvolvida em Java 21 e Spring Boot 3. O desenvolvimento dessa aplicação fez parte de um evento oferecido pela [Rocketseat](https://github.com/rocketseat-education/), a NLW Unite. Essa API tem como principal finalidade ajudar organizadores de eventos presencias no gerenciamento de participantes. Com ela é possível:

- Criar novos eventos - **POST /events**
- Adicionar novos participantes em um evento - **POST /events/{eventId}/attendees**
- Visualizar os detalhes de um evento - **GET /events/{eventId}**
- Checar os participantes de um evento - **GET /events/{eventId}/attendees**
- Permitir a realização de check-in do participante - **POST /attendees/{attendeeId}/check-in**
- Visualizar o crachá de um participante - **GET /attendees/{attendeeId}/badge**

## Principais tecnologias

- JAVA 21
- Spring Boot 3
- Flyway
- HyperSQL
- Swagger

## Pontos de aprendizagem

- **Organização da estrutura do projeto**, de modo a facilitar possíveis alterações e incrementos.
- **Tratamento de erros**
  - Criação de classes para erros personalizados à aplicação.
  - Criação de uma classe específica para mapeamento de erros
    específicos da aplicação.
- **Uso de DTO's**
  - Criação de _records_ que especializaram respostas e corpos de requisição.
