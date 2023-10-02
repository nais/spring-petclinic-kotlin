# PostgreSQL Database

This application has support for persistence using PostgreSQL.

## Running PostgreSQL

You can run PostgreSQL using Docker:

```bash
docker-compose up -d postgres
```

Start the application using the `postgres` profile:

```bash
SPRING_PROFILES_ACTIVE=postgres ./gradlew bootRun
```
