FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/user-service.jar /app/user-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]
