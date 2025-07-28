FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /app/target/football_manager-1.0.jar football_manager-1.0.jar
ENTRYPOINT ["java", "-jar", "football_manager-1.0.jar"]