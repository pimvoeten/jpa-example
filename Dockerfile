FROM openjdk:13-jdk-alpine

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ARG JAR_FILE=target/jpa-example.jar

COPY ${JAR_FILE} app.jar
COPY ./config/*.yml /

ENTRYPOINT ["java","-jar","/app.jar"]
