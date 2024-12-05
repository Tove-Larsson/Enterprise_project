# Use a Gradle image to build the backend
FROM gradle:8.0-jdk17 AS builder

WORKDIR /app

COPY Enterprise_project/gradle /app/gradle
COPY Enterprise_project/settings.gradle /app/
COPY Enterprise_project/build.gradle /app/

COPY Enterprise_project/src /app/src

RUN gradle test

RUN gradle build

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/backend.jar

ENTRYPOINT ["java", "-jar", "backend.jar"]
