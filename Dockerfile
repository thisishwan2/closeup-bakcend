FROM openjdk:17-alpine
WORKDIR /app
COPY ./closeUp-0.0.1-SNAPSHOT.jar ./app.jar  # ./ 없으면 안됨
ENTRYPOINT ["java", "-jar", "app.jar"]