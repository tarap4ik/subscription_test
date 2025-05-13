FROM maven:3.6.3-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]