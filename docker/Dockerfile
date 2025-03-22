FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY target/service-content-1.0-SNAPSHOT.jar tanhu.jar
ENTRYPOINT ["java", "-jar", "tanhu.jar"]
EXPOSE 8080