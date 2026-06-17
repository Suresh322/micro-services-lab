# Build JAR locally first:
#   cd config-server
#   .\mvnw.cmd clean package -DskipTests
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S suresh && adduser -S suresh -G suresh
COPY target/*.jar config-server.jar
USER suresh:suresh
EXPOSE 8071
ENTRYPOINT ["java", "-jar", "config-server.jar"]
