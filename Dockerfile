FROM gradle:8.9-jdk21 AS build

WORKDIR /home/gradle/project

COPY settings.gradle.kts settings.gradle.kts

COPY . .

RUN gradle api:build

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /home/gradle/project/api/build/libs/*.jar /app/api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/api.jar"]