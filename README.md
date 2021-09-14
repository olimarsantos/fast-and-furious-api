# Fast and Furiuos API
## Description

The Project was developed with the objective of simulating a small cinema, which only plays movies from the Fast & Furious franchise, and integrates with [omdbapi](http://www.omdbapi.com/). It contains just a backend developed in [Kotlin](https://kotlinlang.org/) using the [Spring Boot](https://spring.io/projects/spring-boot) framework. This project uses the [commits pattern](https://www.conventionalcommits.org/en/v1.0.0/).

## Features
- An endpoint to create a movie catalog
- An endpoint to return a movie catalog
- An endpoint to update movie catalog's showing times and prices
- An endpoint to fetch movie schedules
- An endpoint to fetch summary details about a movie
- An endpoint to fetch complete information about a movie
- An endpoint to provide a review about a movie
- An endpoint to consult movie reviews
- An endpoint for saving a list of movies

Note: As the purpose of the application is to integrate with a third-party API, the main function to search for information about a movie will first try to search the information directly from omdbapi, but if a connection problem occurs, the application will try to search the information directly from the database of data. That's why the movie saving feature was created.

## Tech

Some of the technologies used in this project.

- [Java SDK 11](https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html) - JDK that the application was built.
- [Kotlin 1.5.21](https://kotlinlang.org/docs/whatsnew15.html) - Programming language that the application was built.
- [Spring Boot](https://spring.io/projects/spring-boot) - Base framework on which the application was built.
- [PostgreSQL Database](https://www.postgresql.org) -  	PostgreSQL is a powerful, open source object-relational database system.
- [Docker](https://www.docker.com/) - Container tool.
- [Docker Compose](https://docs.docker.com/compose/) - Compose is a tool for defining and running multi-container Docker applications.
- [JUnit 5](https://junit.org/junit5/) - Used to perform backend API testing tests.
- [Jacoco](https://www.eclemma.org/jacoco/) - Tool used for monitoring code coverage of tests.

## Architecture
This application was designed based on DDD (Domain Driven Design) in order to facilitate understanding by separating the layers and facilitating the healthy growth of the application, following the best practices of the community, and extracting some advantages from the Kotlin language to avoid boilerplate code.

## Installation

The API requires:
- [JRE 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html/)  or above.
- [Maven 3x](https://maven.apache.org/ref/3.6.3/) or above.
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

### Database Creation
For all options it is necessary to create the database. From the project's [root directory](https://github.com/olimarsantos/fast-and-furious-api), run the commands below.
```sh
docker-compose up
```
### Application
**IMPORTANT** Before carrying out the project compilation, it is necessary to provide the *API Key* for the application's integration with the [OMDb](http://www.omdbapi.com/), just add its Key directly in the project's configuration file [applicatiom.properties](https://github.com/olimarsantos/fast-and-furious-api/blob/main/src/main/resources/application.properties) in the **api.apiKey** property. After that run the following commands:

```sh
NOTE: At the root of folder, run the following commands

# Build Maven
mvn clean install

# Execution 
NOTE: By default the system is configured to go up on port 8080
java -jar target/fast-and-furious-api-1.0.jar
```

### Swagger
The API documentation can be accessed by Swagger, at the link `http://localhost:8080/swagger-ui.html`.

### Postman Collection
I created a Postman collection to make this easier and do some testing, at the link: `https://www.postman.com/collections/fd3f3170f6d786e88b40`
