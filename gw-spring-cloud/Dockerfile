# Stage 1 - Download dipendenze e compilazione
FROM maven:3-eclipse-temurin-25 AS builder

WORKDIR /opt/app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY ./src ./src
RUN ./mvnw clean install

# Stage 2 - Creazione immagine finale
FROM eclipse-temurin:25-jre AS final

# Impostazione timezone di default
ENV TZ="Europe/Rome"

WORKDIR /opt/app

EXPOSE 8080
EXPOSE 18080

ENV SPRING_JMX_ENABLED=false

COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar

ENTRYPOINT ["java", "-jar", "/opt/app/*.jar", "--spring.profiles.active=docker"]
