FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/rest-api-mongodb-0.0.1-SNAPSHOT.jar rest-api-mongodb.jar
ENTRYPOINT ["java","-jar","/rest-api-mongodb.jar"]
EXPOSE 8080