# Use a Gradle image to build the backend
FROM gradle:8.0-jdk17 AS builder

WORKDIR /app

COPY gradle /app/gradle
COPY settings.gradle /app/
COPY build.gradle /app/

COPY src /app/src

RUN gradle test

RUN gradle build

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/backend.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "backend.jar"]
