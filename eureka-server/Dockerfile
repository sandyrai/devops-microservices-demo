FROM openjdk:17-jdk-slim
LABEL authors="sandeep kumar"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8761

WORKDIR /app
ENTRYPOINT ["java", "-jar", "app.jar"]
