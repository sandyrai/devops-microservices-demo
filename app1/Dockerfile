FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app1.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app1.jar"]
