FROM openjdk:13-jdk-alpine as build

WORKDIR /build

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean package && \
    mkdir -p target/dependency && \
    cd target/dependency && \
    jar -xf ../jpa-example.jar

FROM openjdk:13-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/build/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY ./config /app/config

ENTRYPOINT ["java","-cp","app:app/lib/*","com.github.pimvoeten.jpa.example.ExampleApplication"]
