FROM openjdk:8-jdk-stretch as builder
COPY ./ /build
WORKDIR /build
RUN ./gradlew jar

FROM openjdk:8-jre-slim
WORKDIR /mail-service
COPY --from=builder /build/build/libs/mail-service.jar .
EXPOSE 4567
ENTRYPOINT java -jar mail-service.jar
