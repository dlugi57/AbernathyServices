# Abernathy Services

## Overview:
Abernathy Services is a Spring Boot applications.
Abernathy is the international company that works with health clinics and private practices to screen for disease risks.

---

Abernathy Services is composed of 3 microservices:

1. **[Patient](https://github.com/dlugi57/AbernathyServices/tree/dev/Patient)** : <br/>
    - used on port 8084
    - REST API
    - CRUD of patient 
---

2. **[Notes](https://github.com/dlugi57/AbernathyServices/tree/dev/notes)**:  <br/>
   - used on port 8086
    - REST API
    - CRUD of notes
---

3. **[Report](https://github.com/dlugi57/AbernathyServices/tree/dev/Report)**:
    - used on port 8085
   - ThymLeaf intarfaces 
    - REST API
    - Generation of reports 
---


## Prerequisite to run it

- **Java** 11 
- **Gradle** 
- **Spring Boot** 
- **MySql**
- **MongoDB**
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

## Databases - SQL & NOSQL

- **Microservice Patient**: <br/>
  **data.sql** (available in *"/src/main/resources"*) contains scrypt SQL to populate patients database

- **Microservice Notes**:  <br/>
  in *"/src/main/resources"* : the file **notesData.json** contains data to import to the MongoDB 
  in *"/src/main/resources"* : the file **notesData.csv** contains data to import to the MongoDB

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
