FROM openjdk:17-jdk-slim
ARG  JAR_FILE=target/*.jar
COPY ${JAR_FILE} apigatway.jar
EXPOSE 8083
ENTRYPOINT ["java" , "-jar", "/apigatway.jar"]
