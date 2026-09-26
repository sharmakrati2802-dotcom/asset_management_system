# Military Asset Management System - Spring Boot Backend

## Stack
- Java 17
- Spring Boot 3.5.6
- Spring Security + JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- Swagger/OpenAPI
- Maven

## Create database

```sql
CREATE DATABASE military_asset_db;
```

Then update `src/main/resources/application.properties` if your PostgreSQL username/password differ.

## Run

```bash
mvn clean spring-boot:run
```

Swagger:
`http://localhost:8080/swagger-ui.html`

## Seeded users

These are demo credentials only. Change them before any real deployment.

| Username | Password | Role |
|---|---|---|
| admin | Admin@123 | ADMIN |
| commander | Commander@123 | BASE_COMMANDER |
| logistics | Logistics@123 | LOGISTICS_OFFICER |

## Login

POST `/api/auth/login`

```json
{
  "username": "admin",
  "password": "Admin@123"
}
```

Use returned JWT as:

`Authorization: Bearer <token>`

## Important business rule

This initial framework demonstrates the requested architecture and transaction tracking. The dashboard currently starts `openingBalance` at zero. For production, opening balance should be calculated from all transactions before the selected `fromDate`, preferably with a stock-ledger query or a materialized/current-balance table.

## Security note

This is a project/assessment starter, not a production military deployment. Replace the demo JWT secret and credentials, use HTTPS, secure secrets, add rate limiting, stronger audit controls, database backups, and infrastructure security before any real use.
