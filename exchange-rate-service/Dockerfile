# FROM <image>[:<tag>|@<digest]
FROM --platform=linux/x86_64 gradle:8.1.1-jdk17-alpine AS builder

LABEL maintaner="Fatih Celik <celikfatih@protonmail.com>"

WORKDIR /opt/app
COPY . .
RUN gradle build

FROM --platform=linux/x86_64 eclipse-temurin:17-jdk-alpine

RUN addgroup casegroup; adduser --ingroup casegroup --disabled-password fati
USER fati

WORKDIR /home/app
COPY --from=builder /opt/app/build/libs/*.jar /home/app/exchange-rate-service.jar

ENTRYPOINT ["java", "-jar", "/home/app/exchange-rate-service.jar"]