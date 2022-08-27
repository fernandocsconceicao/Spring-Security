<h2 align="center">Spring Kotlin API Basic Authentication with Database</h2>

<p align="center">
  <a href="https://github.com/joaogcs/spring-kotlin-api-basic-auth-with-db/actions"><img alt="Actions Status" src="https://github.com/joaogcs/spring-kotlin-api-basic-auth-with-db/workflows/CI/badge.svg"></a>
  <a href="https://github.com/relekang/python-semantic-release"><img alt="Semantic Release" src="https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg"></a>
  <a href="https://github.com/joaogcs/spring-kotlin-api-basic-auth-with-db/blob/master/LICENSE"><img alt="GitHub" src="https://img.shields.io/github/license/joaogcs/spring-kotlin-api-basic-auth-with-db"/></a>
  <a href="https://open.vscode.dev/joaogcs/spring-kotlin-api-basic-auth-with-db"><img alt="Open in Visual Studio Code" src="https://open.vscode.dev/badges/open-in-vscode.svg"/></a>
</p>

#    

This API was developed using the stacks:

- Kotlin 1.7
- JVM 18
- Spring Boot
- Maven
- Docker
- Swagger
- jUnit5
- Mockito

# How to run

First make sure that you have installed the [docker-compose](https://docs.docker.com/compose/gettingstarted/), run
docker-compose command to build local postgresql database:

```sh
$ docker-compose -f ./docker/local/h2/docker-compose.yml up -d
```

Then run the application:

```sh
$ ./mvnw spring-boot:run
```

To access the project documentation, access the url:

```
http://localhost:8080/swagger-ui
```

### How to check API health

To check API health you must GET url (authentication needed):

```
http://localhost:8080/actuator/health
```

### How to run tests

```sh
$ ./mvnw test
```

### How to playground

You can play in localhost either by Swagger or importing postman collection.

#### Users

**Administrator username/password:** admin/pass

**Common user username/password:** user/pass
