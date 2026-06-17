# Build JAR locally first:
#   cd loans
#   .\mvnw.cmd clean package -DskipTests
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S suresh && adduser -S suresh -G suresh
COPY target/*.jar loans.jar
USER suresh:suresh
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "loans.jar"]
