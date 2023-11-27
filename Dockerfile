FROM openjdk:17-alpine
WORKDIR /closeUp
COPY closeUp-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/closeUp-0.0.1-SNAPSHOT.jar"]
