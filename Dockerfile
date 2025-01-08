FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/courierTracking-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080