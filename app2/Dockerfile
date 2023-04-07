FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app2.jar
EXPOSE 8082
ENTRYPOINT ["java", " -jar", "/app2.jar"]
