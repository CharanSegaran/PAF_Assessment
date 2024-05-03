FROM eclipse-temurin:21 AS builder

WORKDIR /app

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY src src
COPY .mvn .mvn

RUN /app/mvnw package -Dmaven.test.skip=true

FROM eclipse-temurin:21

WORKDIR /app

COPY --from=builder /app/target/batch4-0.0.1-SNAPSHOT.jar batch4charan.jar

ENV PORT=8080
EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar batch4charan.jar