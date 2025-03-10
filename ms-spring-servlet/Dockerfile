# Stage 1 - Download dipendenze e compilazione
FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /opt/app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw -Dmaven.test.skip=true dependency:go-offline

COPY ./src ./src
RUN ./mvnw -Dmaven.test.skip=true clean package

# Stage 2 - Creazione immagine finale
FROM eclipse-temurin:21-jre AS final

# Impostazione timezone di default
ENV TZ="Europe/Rome"

WORKDIR /opt/app

EXPOSE 8080
EXPOSE 18080

ENV SPRING_JMX_ENABLED=false

COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar

ENTRYPOINT ["java", "-jar", "/opt/app/*.jar", "--spring.profiles.active=docker"]
