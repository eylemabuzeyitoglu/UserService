FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/UserService-0.0.1-SNAPSHOT.jar /app/UserService-0.0.1-SNAPSHOT.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/UserService-0.0.1-SNAPSHOT.jar"]
