FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app
COPY . .

RUN ./mvnw clean install

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/money-manager.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
