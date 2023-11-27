FROM adoptopenjdk:17-jdk-hotspot AS build

COPY closeUp-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]