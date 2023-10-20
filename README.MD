# Playground Authentication Service

Simple demo create token with jwt and authenticate endpoint with annotation

## Table of Contents
* [How to Run](#how-to-run)
* [Environment Setup](#environment-setup)
* [Swagger Docs](#swagger-docs)
* [Core Dependency](#core-dependency)
* [Source Links](#source-links)

## How to run

| Command               | Description        |
|-----------------------|--------------------|
| `mvn clean install`   | Build application  |
| `mvn spring-boot:run` | Run application    |

## Environment Setup

| Name    | Description                                                                                                                                            |
|---------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| Docker  | [Install Docker Engine](https://docs.docker.com/engine/install/)                                                                                       |
| MySQL   | `docker pull mysql`                                                                                                                                    |
|         | `docker volume create --name mysql-volume`                                                                                                             |
|         | `docker run -d --name mysql-docker -p 3306:3306 -e MYSQL_ROOT_PASSWORD=a5k4CooL --mount source=mysql-volume,destination=/var/lib/mysql  mysql:latest ` |
| SdkMan! | [Installation](https://sdkman.io/install)                                                                                                              |
| Java    | `sdk install java 17.0.8-oracle`                                                                                                                       |
| Maven   | `sdk install maven 3.9.5`                                                                                                                              |

## Core Dependency

[Playground Common Secure](https://github.com/LinggaAskaEdo/playground-common-secure)

## Swagger Docs

[Swagger](http://localhost:6060/swagger-ui/index.html)

## Source Links

| Name            | Link                                                                                                                                                           |
|-----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Spring Security | [Spring Boot Login example: Rest API with MySQL and JWT](https://www.bezkoder.com/spring-boot-login-example-mysql/#Configure_Spring_Security)                  |
|                 | [Spring Security Refresh Token with JWT in Spring Boot](https://www.bezkoder.com/spring-security-refresh-token/)                                               |
|                 | [Spring Boot : Custom Role - Permission Authorization using SpEL](https://dev.to/ashishrameshan/custom-role-based-permission-authorization-in-spring-boot-m7f) |
| Java Stream     | [A Guide to Java Streams in Java 8: In-Depth Tutorial With Examples](https://stackify.com/streams-guide-java-8/)                                               |
| JSON Schema     | [Json Schema Validation Starter](https://github.com/JanLoebel/json-schema-validation-starter)                                                                  |
|                 | [What is JSON Schema?](https://json-schema.org/overview/what-is-jsonschema)                                                                                    |
|                 | [Can JSON schema enums be case-insensitive?](https://stackoverflow.com/questions/26604730/can-json-schema-enums-be-case-insensitive)                           |
| JWT             | [Java JWT: JSON Web Token for Java and Android](https://github.com/jwtk/jjwt#example-jwe-dir)                                                                  |
