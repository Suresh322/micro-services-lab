# Build JAR locally first:
#   cd cards
#   .\mvnw.cmd clean package -DskipTests
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S suresh && adduser -S suresh -G suresh
COPY target/*.jar cards.jar
USER suresh:suresh
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "cards.jar"]
