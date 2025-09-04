FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/SpringProject-0.0.1-SNAPSHOT.jar JobApplicationSystem.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "JobApplicationSystem.jar"]


