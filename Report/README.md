# Report Application

## Overview:

Report application manage patient report and Interface of the application

---

 **[Report](https://github.com/dlugi57/AbernathyServices/tree/dev/Report)**:
    - used on port 8085
   - ThymLeaf intarfaces 
    - REST API
    - Generation of reports 
---


## Prerequisite to run it

- **Java** 11 
- **Gradle** 
- **Spring Boot** 
- **Thymeleaf** 
- **Bootstrap**
- **Docker** 
- **Docker-Compose**
- **Feign**
- **JaCoCo** 
- **Lombok**


## Installing

1. **[Java](https://www.oracle.com/java/technologies/javase-downloads.html)**

2. **[MySql](https://dev.mysql.com/downloads/installer/)**

3. **[MongoDb](https://docs.mongodb.com/manual/administration/install-community/)**

4. **[Docker](https://docs.docker.com/docker-for-windows/)**

## To run microservice on:

- **Localhost**: get git branch local

- **Docker**: get git branch docker



## Run app

Gradle
```
gradle bootRun
```

Spring Boot
```
mvn spring-boot:run (run app)
mvn spring-boot:stop (stop app)
```

## Docker deploiement:

Use the **Dockerfile** on the every package root:

- `docker build -t name of image .`
- `docker run -d -p name of image`

To deploy all Abernathy microservices, use the **docker-compose.yml** on the package root

- `docker-compose up -d`

## API documentation

[POSTMAN](https://documenter.getpostman.com/view/11619210/TWDcGaKt)

## Testing

The app has unit tests and integration tests written. <br/>
You must launch `gradle test`
