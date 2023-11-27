FROM adoptopenjdk:17-jdk-hotspot AS build

ARG JAR_FILE=/build/libs/closeUp-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java","-jar", "/app.jar"]