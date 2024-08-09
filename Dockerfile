FROM maven:3.9.6-eclipse-temurin-21-jammy AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean verify package

FROM openjdk:21
COPY --from=builder /app/target/*.jar app.jar
COPY csv_file /tmp/
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=70", "-jar","/app.jar"]
