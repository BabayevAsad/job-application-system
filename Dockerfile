FROM openjdk:17-jdk-slim
WORKDIR /app
ARG JAR_FILE
COPY ${JAR_FILE} JobApplicationSystem.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "JobApplicationSystem.jar"]
