![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.6-6DB33F)
![Maven](https://img.shields.io/badge/Maven-3-C71A36)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1)
![Flyway Core](https://img.shields.io/badge/Flyway-Core-CC0200)
![Jakarta Validation API](https://img.shields.io/badge/Jakarta-Validation_API-FE672A)
![Java JWT](https://img.shields.io/badge/Auth0-Java_JWT-EB5424)

# Connectude API

This project was created as part of the Java Backend module at Lab365, SENAI - Florianópolis, SC. It was developed in Java with Spring Boot to manage educational resources, and uses JDK 17 and a PostgreSQL database with a specified schema.

The ConnectUde application is an educational management system that offers a RESTful API to facilitate the administration of educational institutions. It allows managing students, teachers, courses, grades, and other education-related information. Through the API, users can perform operations such as creating, updating, retrieving, and deleting data stored in the database, as well as authenticating users and controlling access to endpoints based on their roles and permissions.

## Technologies Used

Below is the Maven project configuration.

- **Spring Boot**: Framework for developing Java applications based on Spring.
- **Spring Boot Starter Data JPA**: Support for using Spring Data JPA for data persistence.
- **Spring Boot Starter Security**: Integration of Spring Security for authentication and authorization.
- **Spring Boot Starter Web**: Configuration for developing web applications with Spring MVC.
- **Spring Boot DevTools**: Development tools for automatic reload and other features during development.
- **Spring Boot Starter Data JDBC**: Support for using Spring Data JDBC.
- **PostgreSQL JDBC Driver**: For connecting to the PostgreSQL database.
- **Flyway Core**: Tool for database version control and migrations.
- **Jakarta Validation API**: Validation API for Java object validation.
- **Java JWT**: Library for creating and verifying JWT (JSON Web Tokens).
- **Lombok**: Library that simplifies the creation of Java classes by reducing boilerplate code.

## Installation

### a) Clone the Repository

```
git clone git@github.com:redabensaad/connectude.git
```

### b) Configure the Database

When starting the application, the tables are automatically created in the database, but they will be empty. Below are the queries to create the `papel` table and populate it with Enum values:

```sql
CREATE TABLE papel (
    id SERIAL PRIMARY KEY,
    nome_papel VARCHAR(20) NOT NULL
);
```

```sql
INSERT INTO papel (nome_papel) VALUES 
('ADMIN'),
('PROFESSOR'),
('PEDAGOGICO'),
('RECRUITER'),
('ALUNO');
```

### c) Configure the application.properties file

```properties
spring.application.name=connectude
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/connectude_db
spring.datasource.username=[your-password-here]
spring.datasource.password=[your-password-here]
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.flyway.baseline-on-migrate=true
spring.flyway.schemas=public
api.security.token.secret=${JWT_SECRET:my-secret-key}
```

### d) Run the application

Navigate to the project directory and run the application with the following command:

```bash
./mvnw spring-boot:run
```

The server will start at http://localhost:8080/

## Usage Step-by-step

With the server running and the database created, and after populating the `Papel` table with Enum values, all tables must be populated to test the functionality of all endpoints with their corresponding HTTP methods. Remember, the first user registration should be for an ADMIN user.

### - Registering an ADMIN User

Registering an ADMIN user is essential. Once registered via the POST `/cadastro` endpoint, the user will exist in the system.

### - Logging into the System

After registering, it is possible to log in through the POST `/login` endpoint. If the password verification is successful, a token will be provided as a response. This token must be copied and used in the request headers as a Bearer Token. From this point on, using the token is mandatory for every request, or a **403 Forbidden** status code will be returned, indicating access to the route is denied.

It is important to note that during registration, an authorization type is assigned to the user based on the `idPapel` provided. Each endpoint's access is either granted or restricted according to the user’s role. This authorization/restriction scheme based on roles is defined in the `SecurityConfigurations` file (access permissions) and the `CadastroEntity` file (authorization assignment).

### - Endpoint Sequence

The order of endpoint calls must be followed to populate the database correctly, as the tables have relationships and dependencies. For instance, creating a teacher (`Docente`) requires a `idCadastro`, meaning the user must already be registered in the system. Once the user exists, a teacher can be created by providing the user’s `idCadastro`.

The sequence of endpoints for data manipulation and persistence is as follows:

1. `/cadastro`
2. `/login`
3. `/docentes`
4. `/cursos`
5. `/materias`
6. `/turmas`
7. `/alunos`
8. `/notas`

A detailed description of each route, its HTTP method, request JSON, response JSON (or exception message) is provided later in the documentation.

## Endpoint descriptions

| Authorization                    | HTTP Method | Endpoint               | Request Body (Example)                                                                            |
|--------------------------------|-------------|------------------------|---------------------------------------------------------------------------------------------------|
| `permitAll()`                  | GET         | /cadastro              | ```{"nome": "Maria Silva", "login": "maria", "password": "1234", "idPapel": 1}```                 |
| `permitAll()`                  | POST        | /login                 | ```{"login": "maria", "password": "1234"}```                                                      | 
| `ADMIN, PEDAGOGICO, RECRUITER` | POST        | /docentes              | ```{"nome": "Maria Silva", "dataEntrada": "01-01-2001", "idCadastro": 1}```                       |
| `ADMIN, PEDAGOGICO, RECRUITER` | GET         | /docentes              |                                                                                                   |
| `ADMIN, PEDAGOGICO, RECRUITER` | GET         | /docentes/{id}         |                                                                                                   |
| `ADMIN, PEDAGOGICO, RECRUITER` | PUT         | /docentes/{id}         | ```{"nome": "Marina Silva", "dataEntrada": "05-05-2005", "idCadastro": 1}```                      |
| `ADMIN, PEDAGOGICO`            | POST        | /cursos                | ```{"nome": "Filosofia", "dataEntrada": "04-08-2019"}```                                          |
| `ADMIN, PEDAGOGICO`            | GET         | /cursos                |                                                                                                   |
| `ADMIN, PEDAGOGICO`            | GET         | /cursos/{id}           | ```{"nome": "Filosofia", "dataEntrada": "04-08-2019"}```                                          |
| `ADMIN, PEDAGOGICO`            | PUT         | /cursos/{id}           | ```{"nome": "Sistemas de Informação", "dataEntrada": "04-08-2019"}```                             |
| `ADMIN`                        | DELETE      | /cursos/{id}           |                                                                                                   |
| `ADMIN`                        | POST        | /materias              | ```{"nome": "Engenharia de Software", "dataEntrada": "04-08-2019",	"idCurso": 2}```               |
| `ADMIN`                        | GET         | /materias              |                                                                                                   |
| `ADMIN`                        | GET         | /materias/{id}         |                                                                                                   |
| `ADMIN`                        | GET         | /materias/cursos/{id}  |                                                                                                   |
| `ADMIN`                        | PUT         | /materias/{id}         | ```{"nome": "Compiladores", "dataEntrada": "04-08-2019"}```                                       |
| `ADMIN`                        | DELETE      | /materias/{id}         |                                                                                                   |
| `ADMIN, PEDAGOGICO`            | POST        | /turmas                | ```{"nome": "Turma 2022.2", "dataEntrada": "11-08-2022", "idDocente": 5, "idCurso": 4}```         |
| `ADMIN, PEDAGOGICO`            | GET         | /turmas                |                                                                                                   |
| `ADMIN, PEDAGOGICO`            | GET         | /turmas/{id}           |                                                                                                   |
| `ADMIN, PEDAGOGICO`            | PUT         | /turmas/{id}           | ```{"nome": "Turma 2024.1", "dataEntrada": "11-08-2022", "idDocente": 2, "idCurso": 2}```         |
| `ADMIN`                        | DELETE      | /turmas/{id}           |                                                                                                   |
| `ADMIN`                        | POST        | /alunos                | ```{"nome": "Aluno A", "dataNascimento": "01-01-1997", "idCadastro": 17, "idTurma": 1}```         |
| `ADMIN`                        | GET         | /alunos                |                                                                                                   |
| `ADMIN`                        | GET         | /alunos/{id}           |                                                                                                   |
| `ADMIN, ALUNO`                 | GET         | /alunos/{id}/notas     |                                                                                                   |
| `ADMIN, ALUNO`                 | GET         | /alunos/{id}/pontuacao |                                                                                                   |
| `ADMIN`                        | PUT         | /alunos/{id}           | ```{"nome": "Aluno B", "dataNascimento": "04-08-1990"}```                                         |
| `ADMIN`                        | DELETE      | /alunos/{id}           |                                                                                                   |
| `ADMIN, PROFESSOR`             | POST        | /notas                 | ```{"dataLancamento": "01-01-2021", "idAluno": 4, "idDocente": 4, "idMateria": 3, "valor": 50}``` |
| `ADMIN, PROFESSOR`             | GET         | /notas                 |                                                                                                   |
| `ADMIN, PROFESSOR`             | GET         | /notas/{id}            |                                                                                                   |
| `ADMIN, PROFESSOR, ALUNO`      | GET         | /notas/aluno/{id}      |                                                                                                   |
| `ADMIN, PROFESSOR`             | PUT         | /notas/{id}            | ```{"dataLancamento": "01-01-2001", "idAluno": 3, "idDocente": 2, "idMateria": 1, "valor": 75}``` |
| `ADMIN`                        | DELETE      | /alunos/{id}           |                                                                                                   |

### Logs

The application includes logging functionality (`@slf4j`) to display `info` and `error` messages in the terminal.
