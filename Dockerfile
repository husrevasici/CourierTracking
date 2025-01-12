FROM openjdk:17-jdk-slim
COPY target/courierTracking-0.0.1-SNAPSHOT.jar /app/courierTracking-0.0.1-SNAPSHOT.jar
WORKDIR /app
CMD ["java", "-jar", "courierTracking-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080