# Stage 1: Build the JAR using Maven
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Run the JAR with a lightweight JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S suresh && adduser -S suresh -G suresh
USER suresh:suresh
COPY --from=builder /app/target/*.jar account-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "account-service.jar"]
