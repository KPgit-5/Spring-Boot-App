FROM eclipse-temurin:11

WORKDIR /app
COPY target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
